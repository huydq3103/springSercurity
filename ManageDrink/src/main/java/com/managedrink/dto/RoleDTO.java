package com.managedrink.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RoleDTO {
    private Long id;
    private String name;
    private Set<PermissionDTO> permissions;

}
