package com.managedrink.dto;

import com.managedrink.entity.PermissionEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.managedrink.entity.RoleEntity;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDTO {
    private String username;
    private String password;

    private Set<PermissionDTO> permissions;
    private Set<RoleDTO> roles;
}
