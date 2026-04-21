package com.wookjae.scheduler.user.service;

import com.wookjae.scheduler.user.dto.SessionUser;
import com.wookjae.scheduler.user.dto.UserLoginRequest;
import com.wookjae.scheduler.user.dto.UserSignUpRequest;
import com.wookjae.scheduler.user.dto.UserSignUpResponse;
import com.wookjae.scheduler.user.dto.UserDeleteRequest;
import com.wookjae.scheduler.user.dto.UserGetResponse;
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

    @Transactional
    public UserSignUpResponse signup(UserSignUpRequest request) {
        User user = new User(
            request.getName(),
            request.getEmail(),
            request.getPassword()
        );

        User savedUser = userRepository.save(user);
        return new UserSignUpResponse(
            savedUser.getId(),
            savedUser.getName(),
            savedUser.getEmail(),
            savedUser.getCreatedAt(),
            savedUser.getModifiedAt()
        );
    }

    @Transactional(readOnly = true)
    public SessionUser login(UserLoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail()).orElseThrow(
            () -> new IllegalStateException("이메일 또는 비밀번호가 올바르지 않습니다."));

        if (!request.getPassword().equals(user.getPassword())) {
            throw new IllegalStateException("이메일 또는 비밀번호가 올바르지 않습니다.");
        }

        return new SessionUser(
            user.getId(),
            user.getName(),
            user.getEmail()
        );
    }

    @Transactional(readOnly = true)
    public List<UserGetResponse> findAll() {
        List<User> users = userRepository.findAll();
        return users.stream()
            .map(user -> new UserGetResponse(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getCreatedAt(),
                user.getModifiedAt()
            )).toList();
    }

    @Transactional(readOnly = true)
    public UserGetResponse findOne(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(
            () -> new IllegalStateException("없는 유저입니다."));

        return new UserGetResponse(
            user.getId(),
            user.getName(),
            user.getEmail(),
            user.getCreatedAt(),
            user.getModifiedAt()
        );
    }

    @Transactional
    public UserUpdateResponse update(SessionUser sessionUser, UserUpdateRequest request) {
        validateLogin(sessionUser);

        User user = userRepository.findById(sessionUser.getId()).orElseThrow(
            () -> new IllegalStateException("존재하지 않는 유저입니다."));

        if (!request.getPassword().equals(user.getPassword())) {
            throw new IllegalStateException("비밀번호가 일치하지 않습니다.");
        }

        user.update(request.getName());
        return new UserUpdateResponse(
            user.getId(),
            user.getName(),
            user.getEmail(),
            user.getCreatedAt(),
            user.getModifiedAt()
        );
    }

    @Transactional
    public void delete(SessionUser sessionUser, UserDeleteRequest request) {
        validateLogin(sessionUser);

        User user = userRepository.findById(sessionUser.getId()).orElseThrow(
            () -> new IllegalStateException("존재하지 않는 유저입니다.")
        );

        if (!request.getEmail().equals(user.getEmail())) {
            throw new IllegalStateException("이메일이 같지 않습니다.");
        }

        if (!request.getPassword().equals(user.getPassword())) {
            throw new IllegalStateException("비밀번호가 일치하지 않습니다.");
        }

        userRepository.delete(user);
    }

    private void validateLogin(SessionUser sessionUser) {
        if (sessionUser == null) {
            throw new IllegalStateException("로그인이 필요합니다.");
        }
    }
}