package com.oraz.saken.demo.controller;

import com.oraz.saken.demo.dto.MessageResponse;
import com.oraz.saken.demo.dto.admin.AddRoleRequest;
import com.oraz.saken.demo.dto.admin.RemoveRoleRequest;
import com.oraz.saken.demo.dto.admin.ReplaceRolesRequest;
import com.oraz.saken.demo.service.AdminUserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/admin/users")
@PreAuthorize("hasRole('ADMIN')")
public class AdminUserController {
    private final AdminUserService adminUserService;

    public AdminUserController(AdminUserService adminUserService) {
        this.adminUserService = adminUserService;
    }

    @PostMapping("/{userId}/roles/add")
    public ResponseEntity<MessageResponse> addRole(@PathVariable Long userId, @RequestBody @Valid AddRoleRequest request) {
        adminUserService.addRoleToUser(userId, request.getRole());

        return ResponseEntity.ok(new MessageResponse("Роль пользователя успешно добавлена"));
    }

    @PostMapping("/{userId}/roles/remove")
    public ResponseEntity<MessageResponse> removeRole(@PathVariable Long userId, @RequestBody @Valid RemoveRoleRequest request) {
        adminUserService.removeRoleFromUser(userId, request.getRole());

        return ResponseEntity.ok(new MessageResponse("Роль пользователя успешно удалена"));
    }

    @PostMapping("/{userId}/roles/replace")
    public ResponseEntity<MessageResponse> replaceRoles(@PathVariable Long userId, @RequestBody @Valid ReplaceRolesRequest request) {
        adminUserService.replaceUserRoles(userId, request.getRoles());

        return ResponseEntity.ok(new MessageResponse("Роли пользователя успешно обновлены"));
    }
}
