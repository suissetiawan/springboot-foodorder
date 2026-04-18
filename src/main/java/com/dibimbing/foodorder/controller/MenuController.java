package com.dibimbing.foodorder.controller;

import com.dibimbing.foodorder.dto.MenuRequest;
import com.dibimbing.foodorder.entity.Menu;
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
    public ResponseEntity<List<Menu>> getAllMenus() {
        return ResponseEntity.ok(menuService.getAllMenus());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Menu> getMenuById(@PathVariable Long id) {
        return ResponseEntity.ok(menuService.getMenuById(id));
    }

    @PostMapping
    public ResponseEntity<Menu> createMenu(
            @Valid @RequestBody MenuRequest request
    ) {
        return ResponseEntity.ok(menuService.createMenu(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Menu> updateMenu(
            @PathVariable Long id,
            @Valid @RequestBody MenuRequest request
    ) {
        return ResponseEntity.ok(menuService.updateMenu(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMenu(@PathVariable Long id) {
        menuService.deleteMenu(id);
        return ResponseEntity.noContent().build();
    }
}
