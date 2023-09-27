package com.movie.management.repositories;

import com.movie.management.entities.RoleEntity;
import org.springframework.data.repository.CrudRepository;

public interface IRoleRepository extends CrudRepository<RoleEntity, Long> {

}
