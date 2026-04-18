package com.dibimbing.foodorder.controller;

import com.dibimbing.foodorder.dto.BaseResponse;
import com.dibimbing.foodorder.dto.MenuDTO;
import com.dibimbing.foodorder.service.MenuService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/menus")
@RequiredArgsConstructor
public class MenuController {

    private final MenuService menuService;

    @GetMapping
    public ResponseEntity<BaseResponse<List<MenuDTO.MenuResponse>>> getAllMenus() {
        return ResponseEntity.ok(
                BaseResponse.<List<MenuDTO.MenuResponse>>builder()
                        .message("Menus retrieved successfully")
                        .data(menuService.getAllMenus())
                        .build()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<BaseResponse<MenuDTO.MenuResponse>> getMenuById(@PathVariable Long id) {
        return ResponseEntity.ok(
                BaseResponse.<MenuDTO.MenuResponse>builder()
                        .message("Menu retrieved successfully")
                        .data(menuService.getMenuById(id))
                        .build()
        );
    }

    @PostMapping
    public ResponseEntity<BaseResponse<MenuDTO.MenuResponse>> createMenu(
            @Valid @RequestBody MenuDTO.MenuRequest request
    ) {
        return ResponseEntity.ok(
                BaseResponse.<MenuDTO.MenuResponse>builder()
                        .message("Menu created successfully")
                        .data(menuService.createMenu(request))
                        .build()
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<BaseResponse<MenuDTO.MenuResponse>> updateMenu(
            @PathVariable Long id,
            @Valid @RequestBody MenuDTO.MenuRequest request
    ) {
        return ResponseEntity.ok(
                BaseResponse.<MenuDTO.MenuResponse>builder()
                        .message("Menu updated successfully")
                        .data(menuService.updateMenu(id, request))
                        .build()
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BaseResponse<Void>> deleteMenu(@PathVariable Long id) {
        menuService.deleteMenu(id);
        return ResponseEntity.ok(
                BaseResponse.<Void>builder()
                        .message("Menu deleted successfully")
                        .build()
        );
    }
}
