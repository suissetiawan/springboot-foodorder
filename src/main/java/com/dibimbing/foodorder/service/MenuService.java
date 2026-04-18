package com.dibimbing.foodorder.service;

import com.dibimbing.foodorder.dto.MenuDTO;
import com.dibimbing.foodorder.entity.Menu;
import com.dibimbing.foodorder.exception.ResourceNotFoundException;
import com.dibimbing.foodorder.repository.MenuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MenuService {

    private final MenuRepository menuRepository;

    public List<MenuDTO.MenuResponse> getAllMenus() {
        List<Menu> menus = menuRepository.findAll();
        if (menus.isEmpty()) {
            throw new ResourceNotFoundException("Menu is empty");
        }
        return menus.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public MenuDTO.MenuResponse getMenuById(Long id) {
        Menu menu = menuRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Menu not found"));
        return mapToResponse(menu);
    }

    public MenuDTO.MenuResponse createMenu(MenuDTO.MenuRequest request) {
        menuRepository.findByName(request.getName()).ifPresent(menu -> {
            throw new ResourceNotFoundException("Menu '" + request.getName() + "' already exists");
        });

        Menu menu = new Menu();
        return saveMenu(menu, request);
    }

    public MenuDTO.MenuResponse updateMenu(Long id, MenuDTO.MenuRequest request) {
        Menu menu = menuRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Menu not found"));
        return saveMenu(menu, request);
    }

    public void deleteMenu(Long id) {
        menuRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Menu not found"));
        menuRepository.deleteById(id);
    }

    private MenuDTO.MenuResponse saveMenu(Menu menu, MenuDTO.MenuRequest request) {
        menu.setName(request.getName());
        menu.setDescription(request.getDescription());
        menu.setPrice(request.getPrice());
        menu.setCategory(request.getCategory());
        menu.setStock(request.getStock());
        menu.setImageUrl(request.getImageUrl());
        return mapToResponse(menuRepository.save(menu));
    }

    private MenuDTO.MenuResponse mapToResponse(Menu menu) {
        return MenuDTO.MenuResponse.builder()
                .id(menu.getId())
                .name(menu.getName())
                .description(menu.getDescription())
                .price(menu.getPrice())
                .category(menu.getCategory())
                .stock(menu.getStock())
                .imageUrl(menu.getImageUrl())
                .createdAt(menu.getCreatedAt())
                .updatedAt(menu.getUpdatedAt())
                .build();
    }
}
