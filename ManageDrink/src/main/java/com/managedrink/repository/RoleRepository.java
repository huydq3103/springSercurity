package com.managedrink.repository;

import com.managedrink.entity.PermissionEntity;
import com.managedrink.entity.RoleEntity;
import com.managedrink.entity.UserEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

/**
 * Class: RoleRepository
 * Author: ACER
 * Date: 7/23/2024
 * Description: [Your description here]
 */
@Repository
public interface RoleRepository extends JpaRepository<RoleEntity,Long> {

    Optional<RoleEntity> findByName(String name);

    boolean existsByName(String name);

    @Transactional
    @Query("SELECT p FROM UserEntity u " +
            "JOIN u.roles r " +
            "JOIN r.permissionss p " +
            "WHERE u.username = :username")
    Set<PermissionEntity> findPermissionsByUsername(@Param("username") String username);


}
