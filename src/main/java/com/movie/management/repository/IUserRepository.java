package com.movie.management.repositories;

import com.movie.management.entities.UserEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IUserRepository extends CrudRepository<UserEntity, Long> {

    public Optional<UserEntity> findByUsername(String username);
}
