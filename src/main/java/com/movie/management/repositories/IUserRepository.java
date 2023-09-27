package com.movie.management.repositories;

import com.movie.management.entities.UserEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface IUserRepository extends CrudRepository<UserEntity, Long> {

    public Optional<UserEntity> findByUsername(String username);
}
