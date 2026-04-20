package com.wookjae.scheduler.user.controller;

import com.wookjae.scheduler.user.dto.UserSignUpRequest;
import com.wookjae.scheduler.user.dto.UserSignUpResponse;
import com.wookjae.scheduler.user.dto.UserDeleteRequest;
import com.wookjae.scheduler.user.dto.UserGetResponse;
import com.wookjae.scheduler.user.dto.UserUpdateRequest;
import com.wookjae.scheduler.user.dto.UserUpdateResponse;
import com.wookjae.scheduler.user.service.UserService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/users/signup")
    public ResponseEntity<UserSignUpResponse> signup(
        @Valid @RequestBody UserSignUpRequest request
    ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.signup(request));
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserGetResponse>> getAll() {
        return ResponseEntity.status(HttpStatus.OK).body(userService.findAll());
    }

    @GetMapping("/users/{userId}")
    public ResponseEntity<UserGetResponse> getOne(
        @PathVariable Long userId
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.findOne(userId));
    }

    @PutMapping("/users/{userId}")
    public ResponseEntity<UserUpdateResponse> update(
        @PathVariable Long userId,
        @Valid @RequestBody UserUpdateRequest request
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.update(userId, request));
    }

    @DeleteMapping("/users/{userId}")
    ResponseEntity<Void> delete(
        @PathVariable Long userId,
        @Valid @RequestBody UserDeleteRequest request
    ) {
        userService.delete(userId, request);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}