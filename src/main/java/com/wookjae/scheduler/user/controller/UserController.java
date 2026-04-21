package com.wookjae.scheduler.user.controller;

import com.wookjae.scheduler.user.dto.SessionUser;
import com.wookjae.scheduler.user.dto.UserLoginRequest;
import com.wookjae.scheduler.user.dto.UserSignUpRequest;
import com.wookjae.scheduler.user.dto.UserSignUpResponse;
import com.wookjae.scheduler.user.dto.UserDeleteRequest;
import com.wookjae.scheduler.user.dto.UserGetResponse;
import com.wookjae.scheduler.user.dto.UserUpdateRequest;
import com.wookjae.scheduler.user.dto.UserUpdateResponse;
import com.wookjae.scheduler.user.service.UserService;
import jakarta.servlet.http.HttpSession;
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
import org.springframework.web.bind.annotation.SessionAttribute;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // 회원가입
    @PostMapping("/users/signup")
    public ResponseEntity<UserSignUpResponse> signup(
        @Valid @RequestBody UserSignUpRequest request
    ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.signup(request));
    }

    @PostMapping("/users/login")
    public ResponseEntity<Void> login(
        @Valid @RequestBody UserLoginRequest request, HttpSession session
    ) {
        SessionUser sessionUser = userService.login(request);
        session.setAttribute("loginUser", sessionUser);

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PostMapping("/users/logout")
    public ResponseEntity<Void> logout(
        @SessionAttribute(name = "loginUser", required = false) SessionUser sessionUser, HttpSession session
    ) {
        if (sessionUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        session.invalidate();
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
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

    @PutMapping("/users")
    public ResponseEntity<UserUpdateResponse> update(
        @SessionAttribute(name = "loginUser", required = false) SessionUser sessionUser,
        @Valid @RequestBody UserUpdateRequest request
    ) {
        if (sessionUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(userService.update(sessionUser, request));
    }

    @DeleteMapping("/users")
    public ResponseEntity<Void> delete(
        @SessionAttribute(name = "loginUser", required = false) SessionUser sessionUser,
        @Valid @RequestBody UserDeleteRequest request
    ) {
        if (sessionUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        userService.delete(sessionUser, request);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}