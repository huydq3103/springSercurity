package com.managedrink.repository;

import com.managedrink.entity.PermissionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PermissionRepository extends JpaRepository<PermissionEntity, Long> {

    boolean existsByName(String name);

    Optional<PermissionEntity> findByName(String name);


}
