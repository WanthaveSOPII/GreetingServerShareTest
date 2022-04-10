package com.example.greetingserver.pojo;

import org.springframework.security.core.GrantedAuthority;

public class UserRoles implements GrantedAuthority {

    String id;
    String roleName;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    @Override
    public String getAuthority() {
        return roleName;
    }
}
