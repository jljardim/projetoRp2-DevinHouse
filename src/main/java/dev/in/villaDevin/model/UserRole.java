package dev.in.villaDevin.model;

public class UserRole {

    private Long userId;
    private Long roleId;

    public UserRole() {
        
    }
    
    public UserRole(Long userId, Long roleId) {
        this.userId = userId;
        this.roleId = roleId;
    }

    public Long getUserId() {
        return userId;
    }

    public Long getRoleId() {
        return roleId;
    }
}
