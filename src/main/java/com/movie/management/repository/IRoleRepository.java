package com.movie.management.repository;

import com.movie.management.entity.RoleEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.movie.management.entity.RoleEntity;

@Repository
public interface IRoleRepository extends CrudRepository<RoleEntity, Long> {

}