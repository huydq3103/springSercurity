package com.managedrink.controller;

import com.managedrink.entity.PermissionEntity;
import com.managedrink.entity.RoleEntity;
import com.managedrink.payload.request.PermissionRequest;
import com.managedrink.services.implement.PermissionServiceImpl;
import com.managedrink.services.implement.RoleServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/roles")
public class RoleController {

    @Autowired
    private RoleServiceImpl roleService;

    @Autowired
    private PermissionServiceImpl permissionService;

    @GetMapping("/get-all-roles")
    public List<RoleEntity> getAllRoles() {
        return roleService.findALl();
    }

    @GetMapping("/get-all-permissions")
    public List<PermissionEntity> getAllPermissions() {
       return permissionService.getAllPermissions();
    }

    @PostMapping("/{roleName}/permissions")
    public ResponseEntity<String> addPermissionToRole
            (@PathVariable String roleName,@Valid @RequestBody PermissionRequest permissionRequest) {
        roleService.addPermissionToRole(roleName, permissionRequest.getPermissionName());
        return ResponseEntity.ok("Permission added to role");
    }

    @DeleteMapping("/{roleName}/permissions/{permissionName}")
    public ResponseEntity<String> removePermissionFromRole
            (@PathVariable String roleName,@Valid @PathVariable String permissionName) {

        roleService.removePermissionFromRole(roleName, permissionName);
        return ResponseEntity.ok("Permission removed from role");
    }
}