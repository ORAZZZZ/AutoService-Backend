package com.oraz.saken.demo.controller;

import com.oraz.saken.demo.dto.MessageResponse;
import com.oraz.saken.demo.dto.user.UserProfileRequest;
import com.oraz.saken.demo.dto.user.UserProfileResponse;
import com.oraz.saken.demo.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/me")
    public ResponseEntity<UserProfileResponse> getMyProfile(Principal principal) {
        String email = principal.getName();

        return ResponseEntity.ok(userService.getMyProfile(email));
    }

    @PutMapping("/me")
    public ResponseEntity<MessageResponse> updateMyProfile(@RequestBody @Valid UserProfileRequest request, Principal principal) {
        String email = principal.getName();

        userService.updateMyProfile(email, request);

        return ResponseEntity.ok(new MessageResponse("Профиль успешно обновлён"));
    }
}
