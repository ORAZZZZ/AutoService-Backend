package com.oraz.saken.demo.service;

import com.oraz.saken.demo.dto.user.UserProfileRequest;
import com.oraz.saken.demo.dto.user.UserProfileResponse;
import com.oraz.saken.demo.entity.User;
import com.oraz.saken.demo.entity.UserProfile;
import com.oraz.saken.demo.exception.NotFoundException;
import com.oraz.saken.demo.repository.UserProfileRepository;
import com.oraz.saken.demo.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserService {
    private final UserRepository userRepository;
    private final UserProfileRepository userProfileRepository;

    public UserService(UserRepository userRepository, UserProfileRepository userProfileRepository) {
        this.userRepository = userRepository;
        this.userProfileRepository = userProfileRepository;
    }

    @Transactional(readOnly = true)
    public UserProfileResponse getMyProfile(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("Пользователь не найден"));

        Set<String> roles = user.getRoles().stream()
                .map(role -> role.getName())
                .collect(Collectors.toSet());

        UserProfile userProfile = user.getProfile();
        UserProfileResponse profileResponse = new UserProfileResponse(user.getId(), user.getName(), user.getSurname(), user.getEmail(), roles);

        if (userProfile != null) {
            profileResponse.setPhoneNumber(userProfile.getPhoneNumber());
            profileResponse.setAddress(userProfile.getAddress());
            profileResponse.setBirthDate(userProfile.getBirthDate());
        }

        return profileResponse;
    }

    public void updateMyProfile(String email, UserProfileRequest request) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("Пользователь не найден"));

        UserProfile userProfile = user.getProfile();

        if (userProfile == null) {
            userProfile = new UserProfile();

            user.setProfile(userProfile);
            userProfile.setUser(user);
        }

        userProfile.setPhoneNumber(request.getPhoneNumber());
        userProfile.setAddress(request.getAddress());
        userProfile.setBirthDate(request.getBirthDate());

        userProfileRepository.save(userProfile);
    }
}
