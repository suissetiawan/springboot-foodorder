package com.dibimbing.foodorder.controller;

import com.dibimbing.foodorder.dto.BaseResponse;
import com.dibimbing.foodorder.dto.MenuDTO;
import com.dibimbing.foodorder.service.MenuService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/menu")
@RequiredArgsConstructor
public class MenuController {

    private final MenuService menuService;

    @GetMapping
    public ResponseEntity<BaseResponse<List<MenuDTO.MenuResponse>>> getAllMenus() {
        return ResponseEntity.ok(
                BaseResponse.<List<MenuDTO.MenuResponse>>builder()
                        .message("Success Get All Menus")
                        .data(menuService.getAllMenus())
                        .build());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BaseResponse<MenuDTO.MenuResponse>> getMenuById(@PathVariable Long id) {
        return ResponseEntity.ok(
                BaseResponse.<MenuDTO.MenuResponse>builder()
                        .message("Success Get Menu")
                        .data(menuService.getMenuById(id))
                        .build());
    }

    @PostMapping
    public ResponseEntity<BaseResponse<MenuDTO.MenuResponse>> createMenu(
            @Valid @RequestBody MenuDTO.MenuRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                BaseResponse.<MenuDTO.MenuResponse>builder()
                        .message("Success Create Menu")
                        .data(menuService.createMenu(request))
                        .build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<BaseResponse<MenuDTO.MenuResponse>> updateMenu(
            @PathVariable Long id,
            @Valid @RequestBody MenuDTO.MenuRequest request) {
        return ResponseEntity.ok(
                BaseResponse.<MenuDTO.MenuResponse>builder()
                        .message("Success Update Menu")
                        .data(menuService.updateMenu(id, request))
                        .build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BaseResponse<Void>> deleteMenu(@PathVariable Long id) {
        menuService.deleteMenu(id);
        return ResponseEntity.ok(
                BaseResponse.<Void>builder()
                        .message("Success Delete Menu")
                        .build());
    }
}
