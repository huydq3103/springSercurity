package com.managedrink.services.implement;

import com.managedrink.entity.PermissionEntity;
import com.managedrink.repository.PermissionRepository;
import com.managedrink.services.IPermissionService;
import com.managedrink.until.validate.ValidationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class PermissionServiceImpl implements IPermissionService {
    @Autowired
    private PermissionRepository permissionRepository;


    @Override
    public boolean existsByName(String name) {
        ValidationUtils.validateString(name);
        if (permissionRepository.existsByName(name)) {
            return true;
        }
        return false;
    }

    @Override
    public Optional<PermissionEntity> findByName(String name) {
        ValidationUtils.validateString(name);

        Optional<PermissionEntity> permission = permissionRepository.findByName(name);

        if (Objects.isNull(permission)) {
            new IllegalArgumentException("Khong tim thay quyen");
        }

        return permission;
    }

    @Override
    public List<PermissionEntity> getAllPermissions() {
        return permissionRepository.findAll();
    }

    @Override
    public void savePermissions(PermissionEntity permissions) {
        permissionRepository.save(permissions);
    }
}
