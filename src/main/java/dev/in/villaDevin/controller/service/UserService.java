package dev.in.villaDevin.controller.service;

import java.sql.SQLException;
import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dev.in.villaDevin.model.Resident;
import dev.in.villaDevin.model.Role;
import dev.in.villaDevin.model.User;
import dev.in.villaDevin.model.repository.UserRepository;
import dev.in.villaDevin.security.UserUtils;
import dev.in.villaDevin.to.UserTO;

@Service
public class UserService {
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final UserRoleService userRoleService;

    public UserService(UserRepository repository, PasswordEncoder passwordEncoder, UserRoleService userRoleService) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
        this.userRoleService = userRoleService;
    }

    
    public UserTO create(Resident resident, String email, String password, Set<String> roleNames) throws Exception {
        validate(resident, email, password);

        final String maskedPassword = passwordEncoder.encode(password);
        

        final User newUser = new User(email, maskedPassword, resident.getId() );
        

        repository.save(newUser);
        try {
            userRoleService.create(newUser.getId(), roleNames);
        } catch(Exception e) {
            this.delete(newUser.getId());
            throw e;
        }
        return new UserTO(newUser.getId(), newUser.getEmail(), newUser.getPassword(), newUser.getResidentId(), roleNames);
    }

 
    public void delete(Long id) throws Exception {
    	User user = repository.findOneByResidentId(id).orElseThrow();
    	userRoleService.deleteByUserId(user.getId());
        repository.deleteByResidentId(id);
    }

    public UserTO getUser(String email) throws Exception {
        final User user = repository.findOneByEmail(email).orElseThrow();
        final Set<String> roles = userRoleService.getRolesNamesByUserId(user.getId());

        return new UserTO(user.getId(), user.getEmail(), user.getPassword(), user.getResidentId(), roles);
    }

    public UserTO getByResidentId(Long residentId) throws Exception {
        if(residentId == null) {
            throw new Exception("Invalid argument.ResidentId cannot be null");
        }
        final Optional<User> optionalUser = repository.findOneByResidentId(residentId);

        if(optionalUser.isEmpty()) {
            throw new Exception("No user found for resident with id " + residentId);
        }

        final User user = optionalUser.get();
        final Set<String> roles = userRoleService.getRolesNamesByUserId(user.getId());
        return new UserTO(user.getId(), user.getEmail(), user.getPassword(), user.getResidentId(), roles);
    }

    public void updatePassword(String email, String newPassword) throws Exception {
        validate(email, newPassword);
        final User user = repository.findOneByEmail(email).orElseThrow();
        user.setPassword(newPassword);
    }

    private void validate(Resident resident, String username, String password) throws Exception {
        if (resident == null) {
            throw new IllegalArgumentException("Invalid resident reference");
        }

        validate(username, password);
    }

    private void validate(String username, String password) throws Exception {

        if(!UserUtils.isValidUsername(username)) {
            throw new IllegalArgumentException("Invalid username. Must be a valid email");
        }

        if(!UserUtils.isValidPassword(password)) {
            throw new IllegalArgumentException("Invalid password. \n" +
                    "Must have 8+ characters containing:\n" +
                    "● 1+ Uppercase\n" +
                    "● 1+ Lowercase\n" +
                    "● 1+ Special character\n" +
                    "● 1+ Number\n");
        }
    }
}
