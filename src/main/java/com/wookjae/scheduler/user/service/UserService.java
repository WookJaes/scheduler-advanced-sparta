package com.wookjae.scheduler.user.service;

import com.wookjae.scheduler.global.auth.SessionUser;
import com.wookjae.scheduler.global.config.PasswordEncoder;
import com.wookjae.scheduler.global.exception.*;
import com.wookjae.scheduler.user.dto.UserDeleteRequest;
import com.wookjae.scheduler.user.dto.UserGetResponse;
import com.wookjae.scheduler.user.dto.UserLoginRequest;
import com.wookjae.scheduler.user.dto.UserSignUpRequest;
import com.wookjae.scheduler.user.dto.UserSignUpResponse;
import com.wookjae.scheduler.user.dto.UserUpdateRequest;
import com.wookjae.scheduler.user.dto.UserUpdateResponse;
import com.wookjae.scheduler.user.entity.User;
import com.wookjae.scheduler.user.repository.UserRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public UserSignUpResponse signup(UserSignUpRequest request) {
        validateDuplicateEmail(request.getEmail());
        String encodedPassword = passwordEncoder.encode(request.getPassword());

        User user = new User(request.getName(), request.getEmail(), encodedPassword);
        User savedUser = userRepository.save(user);
        return UserSignUpResponse.from(savedUser);
    }

    @Transactional(readOnly = true)
    public SessionUser login(UserLoginRequest request) {
        User user = findUserByEmail(request.getEmail());
        validatePassword(request.getPassword(), user.getPassword(), "이메일 또는 비밀번호가 올바르지 않습니다.");
        return new SessionUser(user.getId(), user.getName(), user.getEmail());
    }

    @Transactional(readOnly = true)
    public List<UserGetResponse> findAll() {
        return userRepository.findAllByDeletedFalse().stream()
            .map(UserGetResponse::from)
            .toList();
    }

    @Transactional(readOnly = true)
    public UserGetResponse findOne(Long userId) {
        User user = findUserById(userId);
        return UserGetResponse.from(user);
    }

    @Transactional
    public UserUpdateResponse update(SessionUser sessionUser, UserUpdateRequest request) {
        validateLogin(sessionUser);
        User user = findUserById(sessionUser.getId());
        validatePassword(request.getPassword(), user.getPassword(), "비밀번호가 일치하지 않습니다.");

        user.update(request.getName());
        return UserUpdateResponse.from(user);
    }

    @Transactional
    public void delete(SessionUser sessionUser, UserDeleteRequest request) {
        validateLogin(sessionUser);
        User user = findUserById(sessionUser.getId());
        validatePassword(request.getPassword(), user.getPassword(), "비밀번호가 일치하지 않습니다.");
        user.softDelete();
    }

    private void validateDuplicateEmail(String email) {
        if (userRepository.existsByEmailAndDeletedFalse(email)) {
            throw new DuplicateEmailException("이미 사용 중인 이메일입니다.");
        }
    }

    private User findUserByEmail(String email) {
        return userRepository.findByEmailAndDeletedFalse(email).orElseThrow(
            () -> new UnauthorizedException("이메일 또는 비밀번호가 올바르지 않습니다."));
    }

    private User findUserById(Long userId) {
        return userRepository.findByIdAndDeletedFalse(userId).orElseThrow(
            () -> new UserNotFoundException("사용자를 찾을 수 없습니다.")
        );
    }

    private void validatePassword(String rawPassword, String encodedPassword, String message) {
        if (!passwordEncoder.matches(rawPassword, encodedPassword)) {
            throw new UnauthorizedException(message);
        }
    }

    private void validateLogin(SessionUser sessionUser) {
        if (sessionUser == null) {
            throw new UnauthorizedException("로그인이 필요합니다.");
        }
    }
}