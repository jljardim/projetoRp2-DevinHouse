package dev.in.villaDevin.controller.service;

import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;

import dev.in.villaDevin.model.Role;
import dev.in.villaDevin.model.repository.RoleRepository;

@Service
public class RoleService {

    private final RoleRepository repository;

    public RoleService(RoleRepository repository) {
        this.repository = repository;
    }

    public List<Role> getByNames(Set<String> names) throws Exception {
        if(names == null || names.isEmpty()) {
            throw new Exception("Must pass at least one Role");
        }

        return repository.findAllByNameIn(names);
    }


    public Role find(Long roleId) throws Exception {
        return repository.findById(roleId).orElseThrow();
    }
}
