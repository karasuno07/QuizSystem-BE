package com.fsoft.quizsystem.controller;

import com.fsoft.quizsystem.object.dto.filter.UserFilter;
import com.fsoft.quizsystem.object.dto.mapper.UserMapper;
import com.fsoft.quizsystem.object.dto.request.UserRequest;
import com.fsoft.quizsystem.object.dto.response.UserResponse;
import com.fsoft.quizsystem.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;
import java.util.Set;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController implements SecuredBearerTokenController {

    private final UserService userService;
    private final UserMapper userMapper;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping(value = "/find-all")
    ResponseEntity<?> getAllUsersWithFilter(@RequestBody Optional<UserFilter> filter) {
        Page<UserResponse> responses = userService.findAllUsers(filter.orElse(new UserFilter()))
                                                  .map(userMapper::entityToUserResponse);
        return ResponseEntity.ok(responses);
    }

    @PreAuthorize("isAuthenticated() AND authentication.principal.id == #id OR hasRole('ROLE_ADMIN')")
    @GetMapping(value = "/{id}")
    ResponseEntity<?> getUserById(@PathVariable("id") Long id) {
        UserResponse response = userMapper.entityToUserResponse(userService.findUserById(id));

        return ResponseEntity.ok(response);
    }

    @GetMapping(value = "/validate-username/{username}")
    ResponseEntity<?> validateUsername(@PathVariable("username") String username) {
        boolean response = userService.validateConcurrentUsername(username);

        return ResponseEntity.ok(response);
    }

    @GetMapping(value = "/validate-email/{email}")
    ResponseEntity<?> validateEmail(@PathVariable("email") String email) {
        boolean response = userService.validateConcurrentEmail(email);

        return ResponseEntity.ok(response);
    }

    @PostMapping
    ResponseEntity<?> createUser(@ModelAttribute @Valid UserRequest body) {
        UserResponse response = userMapper.entityToUserResponse(userService.createUser(body));

        return ResponseEntity.ok(response);
    }

    @PreAuthorize("isAuthenticated() AND authentication.principal.id == #id")
    @PutMapping(value = "/{id}")
    ResponseEntity<?> updateUser(@PathVariable Long id, @ModelAttribute @Valid UserRequest body) {
        UserResponse response = userMapper.entityToUserResponse(userService.updateUser(id, body));

        return ResponseEntity.ok(response);
    }

    @PreAuthorize("isAuthenticated() AND authentication.principal.id == #id")
    @PutMapping(value = "/{id}/favorite-categories")
    ResponseEntity<?> updateUserFavoriteCategories(@PathVariable Long id,
                                                   @RequestBody Set<Long> categoryIds) {
        userService.updateUserFavoriteCategories(id, categoryIds);

        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PatchMapping(value = "/{id}/deactivate")
    ResponseEntity<?> deactivateUser(@PathVariable Long id) {
        userService.deactivateUser(id);

        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PatchMapping(value = "/{id}/activate")
    ResponseEntity<?> activateUser(@PathVariable Long id) {
        userService.activateUser(id);

        return ResponseEntity.ok().build();
    }

    @PreAuthorize("isAuthenticated() AND authentication.principal.id == #id")
    @PatchMapping(value = "/{id}/subscribe-notification")
    ResponseEntity<?> subscribeEmailNotification(@PathVariable Long id) {
        userService.subscribeNotification(id);

        return ResponseEntity.ok().build();
    }

    @PreAuthorize("isAuthenticated() AND authentication.principal.id == #id")
    @PatchMapping(value = "/{id}/unsubscribe-notification")
    ResponseEntity<?> unsubscribeEmailNotification(@PathVariable Long id) {
        userService.unsubscribeNotification(id);

        return ResponseEntity.ok().build();
    }
}
