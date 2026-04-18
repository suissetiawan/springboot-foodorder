package com.dibimbing.foodorder.service;

import com.dibimbing.foodorder.dto.MenuRequest;
import com.dibimbing.foodorder.entity.Menu;
import com.dibimbing.foodorder.repository.MenuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MenuService {

    private final MenuRepository menuRepository;

    public List<Menu> getAllMenus() {
        return menuRepository.findAll();
    }

    public Menu getMenuById(Long id) {
        return menuRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Menu not found"));
    }

    public Menu createMenu(MenuRequest request) {
        Menu menu = new Menu();
        return updateMenuFields(menu, request);
    }

    public Menu updateMenu(Long id, MenuRequest request) {
        Menu menu = getMenuById(id);
        return updateMenuFields(menu, request);
    }

    public void deleteMenu(Long id) {
        Menu menu = getMenuById(id);
        menuRepository.delete(menu);
    }

    private Menu updateMenuFields(Menu menu, MenuRequest request) {
        menu.setName(request.getName());
        menu.setDescription(request.getDescription());
        menu.setPrice(request.getPrice());
        menu.setCategory(request.getCategory());
        menu.setStock(request.getStock());
        menu.setImageUrl(request.getImageUrl());
        return menuRepository.save(menu);
    }
}
