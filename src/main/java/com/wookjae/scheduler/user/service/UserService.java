package com.wookjae.scheduler.user.service;

import com.wookjae.scheduler.user.dto.CreateUserRequest;
import com.wookjae.scheduler.user.dto.CreateUserResponse;
import com.wookjae.scheduler.user.dto.DeleteUserRequest;
import com.wookjae.scheduler.user.dto.GetUserResponse;
import com.wookjae.scheduler.user.dto.UpdateUserRequest;
import com.wookjae.scheduler.user.dto.UpdateUserResponse;
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
    public CreateUserResponse save(CreateUserRequest request) {
        User user = new User(
            request.getName(),
            request.getEmail()
        );

        User savedUser = userRepository.save(user);
        return new CreateUserResponse(
            savedUser.getId(),
            savedUser.getName(),
            savedUser.getEmail(),
            savedUser.getCreatedAt(),
            savedUser.getModifiedAt()
        );
    }

    @Transactional(readOnly = true)
    public List<GetUserResponse> findAll() {
        List<User> users = userRepository.findAll();
        return users.stream()
            .map(user -> new GetUserResponse(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getCreatedAt(),
                user.getModifiedAt()
            )).toList();
    }

    @Transactional(readOnly = true)
    public GetUserResponse findOne(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(
            () -> new IllegalStateException("없는 유저입니다."));

        return new GetUserResponse(
            user.getId(),
            user.getName(),
            user.getEmail(),
            user.getCreatedAt(),
            user.getModifiedAt()
        );
    }

    @Transactional
    public UpdateUserResponse update(Long userId, UpdateUserRequest request) {
        User user = userRepository.findById(userId).orElseThrow(
            () -> new IllegalStateException("없는 유저입니다."));

        user.update(
            request.getName());

        return new UpdateUserResponse(
            user.getId(),
            user.getName(),
            user.getEmail(),
            user.getCreatedAt(),
            user.getModifiedAt()
        );
    }

    @Transactional
    public void delete(Long userId, DeleteUserRequest request) {
        User user = userRepository.findById(userId).orElseThrow(
            () -> new IllegalStateException("없는 유저입니다.")
        );

        if (request.getEmail() == null || !request.getEmail().equals(user.getEmail())) {
            throw new IllegalStateException("이메일이 같지 않습니다.");
        }
        userRepository.delete(user);
    }
}