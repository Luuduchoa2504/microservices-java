package com.example.identity_service.mapper;
import com.example.identity_service.dto.UserDTO;
import com.example.identity_service.entity.Role;
import com.example.identity_service.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(target = "roles", source = "roles", qualifiedByName = "rolesToStringSet")
    UserDTO toDTO(User user);

    @Mapping(target = "roles", source = "roles", qualifiedByName = "stringSetToRoles")
    User toEntity(UserDTO userDTO);

    @Named("rolesToStringSet")
    default Set<String> rolesToStringSet(Set<Role> roles) {
        if (roles == null) {
            return null;
        }
        return roles.stream()
                .map(Role::name)
                .collect(Collectors.toSet());
    }

    @Named("stringSetToRoles")
    default Set<Role> stringSetToRoles(Set<String> roleNames) {
        if (roleNames == null) {
            return null;
        }
        return roleNames.stream()
                .map(Role::valueOf)
                .collect(Collectors.toSet());
    }
}
