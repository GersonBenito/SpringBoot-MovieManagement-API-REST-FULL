package com.movie.management.repository;

import com.movie.management.entity.UserEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.movie.management.entity.UserEntity;

import java.util.Optional;

@Repository
public interface IUserRepository extends CrudRepository<UserEntity, Long> {

    public Optional<UserEntity> findByUsername(String username);
}