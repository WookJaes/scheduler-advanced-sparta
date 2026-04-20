package com.wookjae.scheduler.user.controller;

import com.wookjae.scheduler.user.dto.CreateUserRequest;
import com.wookjae.scheduler.user.dto.CreateUserResponse;
import com.wookjae.scheduler.user.dto.DeleteUserRequest;
import com.wookjae.scheduler.user.dto.GetUserResponse;
import com.wookjae.scheduler.user.dto.UpdateUserRequest;
import com.wookjae.scheduler.user.dto.UpdateUserResponse;
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

    @PostMapping("/users")
    public ResponseEntity<CreateUserResponse> create(
        @RequestBody CreateUserRequest request
    ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.save(request));
    }

    @GetMapping("/users")
    public ResponseEntity<List<GetUserResponse>> getAll() {
        return ResponseEntity.status(HttpStatus.OK).body(userService.findAll());
    }

    @GetMapping("/users/{userId}")
    public ResponseEntity<GetUserResponse> getOne(
        @PathVariable Long userId
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.findOne(userId));
    }

    @PutMapping("/users/{userId}")
    public ResponseEntity<UpdateUserResponse> update(
        @PathVariable Long userId,
        @Valid @RequestBody UpdateUserRequest request
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.update(userId, request));
    }

    @DeleteMapping("/users/{userId}")
    ResponseEntity<Void> delete(
        @PathVariable Long userId,
        @Valid @RequestBody DeleteUserRequest request
    ) {
        userService.delete(userId, request);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}