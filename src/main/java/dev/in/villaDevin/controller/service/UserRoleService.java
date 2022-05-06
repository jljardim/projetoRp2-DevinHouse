package dev.in.villaDevin.controller.service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import dev.in.villaDevin.model.Role;
import dev.in.villaDevin.model.repository.UserRoleRepository;

@Service
public class UserRoleService {

    private final UserRoleRepository repository;
    private final RoleService roleService;

    public UserRoleService(UserRoleRepository repository, RoleService roleService) {
        this.repository = repository;
        this.roleService = roleService;
    }

    public void create(Long userId, Set<String> roles) throws Exception {
        List<Long> rolesIds = roleService.getByNames(roles).stream().map(Role::getId).collect(Collectors.toList());

        if(rolesIds.isEmpty()) {
            throw new Exception("Roles not found");
        }

        this.create(userId, rolesIds);
    }

    public void create(Long userId, List<Long> roles) throws RuntimeException {
        roles.forEach(roleId -> {
            try {
                this.create(userId, roleId);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    public void create(Long userId, Long roleId) throws Exception {
        repository.store(userId, roleId);
    }

    public Set<String> getRolesNamesByUserId(Long userId) throws Exception {
        return repository.getByUserId(userId).stream()
            .map(
                userRole -> {
                    try {
                        Role role = roleService.find(userRole.getRoleId());
                        return role.getName();
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
            ).collect(Collectors.toSet())
        ;
    }

    public void deleteByUserId(Long userId) throws Exception {
        repository.deleteByUserId(userId);
    }
}
