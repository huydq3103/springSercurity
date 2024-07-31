package com.managedrink.services;

import com.managedrink.entity.RoleEntity;

import java.util.List;
import java.util.Optional;

public interface IRoleService {

    boolean existsByName(String name);

    RoleEntity saveRole(RoleEntity roleEntity);

    Optional<RoleEntity> findByName(String name);

    void addPermissionToRole(String roleName, String permissionName);

    void removePermissionFromRole(String roleName, String permissionName);

    List<RoleEntity> findALl();

}
