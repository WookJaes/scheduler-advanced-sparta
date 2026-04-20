package com.wookjae.scheduler.user.repository;

import com.wookjae.scheduler.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

}