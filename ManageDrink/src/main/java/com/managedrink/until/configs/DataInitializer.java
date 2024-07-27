package com.managedrink.until.configs;

import com.managedrink.entity.PermissionEntity;
import com.managedrink.entity.RoleEntity;
import com.managedrink.exception.NotFoundException;
import com.managedrink.services.implement.PermissionServiceImpl;
import com.managedrink.services.implement.RoleServiceImpl;
import com.managedrink.until.enums.PermissionEnum;
import com.managedrink.until.enums.RolePermissionEnum;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import java.util.HashSet;
import java.util.Set;
/**
 * Lớp này chịu trách nhiệm khởi tạo dữ liệu trong cơ sở dữ liệu khi ứng dụng khởi động.
 * Điều này bao gồm việc thiết lập các quyền và vai trò dựa trên các enum đã được định nghĩa trước.
 */
@Configuration
public class DataInitializer {

    @Autowired
    private PermissionServiceImpl permissionService;

    @Autowired
    private RoleServiceImpl roleService;

    /**
     * Khởi tạo dữ liệu bằng cách cập nhật các quyền và vai trò.
     * Phương thức này được gọi sau khi bean được tạo và trong ngữ cảnh giao dịch.
     */
    @PostConstruct
    @Transactional
    public void init() {
        updatePermissions();
        updateRoles();
    }
    /**
     * Cập nhật các quyền trong cơ sở dữ liệu.
     * Thêm quyền mới nếu chưa tồn tại, hoặc cập nhật các quyền hiện có.
     */
    private void updatePermissions() {
        for (PermissionEnum permission : PermissionEnum.values()) {
            PermissionEntity permissionEntity = permissionService.findByName(permission.getName())
                    .orElse(new PermissionEntity());
            permissionEntity.setName(permission.getName());
            permissionEntity.setUrl(permission.getUrl());
            permissionEntity.setMethod(permission.getMethod());
            permissionService.savePermissions(permissionEntity);
        }
    }
    /**
     * Cập nhật các vai trò trong cơ sở dữ liệu.
     * Thêm vai trò mới nếu chưa tồn tại và liên kết chúng với các quyền tương ứng.
     *
     * @throws NotFoundException nếu bất kỳ quyền nào liên kết với vai trò không được tìm thấy trong cơ sở dữ liệu.
     */
    @Transactional
    private void updateRoles() {
        for (RolePermissionEnum rolePermission : RolePermissionEnum.values()) {
            String roleName = rolePermission.getName();
            RoleEntity roleEntity = roleService.findByName(roleName)
                    .orElse(null);

            if (roleEntity == null) {
                roleEntity = new RoleEntity();
                roleEntity.setName(roleName);
            }

            Set<PermissionEntity> newPermissions = new HashSet<>();
            if (!rolePermission.getPermissions().isEmpty()) {
                for (PermissionEnum permission : rolePermission.getPermissions()) {
                    PermissionEntity permissionEntity = permissionService.findByName(permission.getName())
                            .orElseThrow(() -> new NotFoundException("Không tìm thấy quyền: " + permission.getName()));
                    newPermissions.add(permissionEntity);
                }
            }

            roleEntity.setPermissionss(newPermissions);
            roleService.saveRole(roleEntity);
        }
    }
}