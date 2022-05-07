package com.example.greetingserver.pojo;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class User implements UserDetails {
    int id;
    String username;
    String nickname;
    int active;
    int issystem;
    byte[] icon;
    String base64Icon;
    String password;

    private List<UserRoles> roles;

    public void setPassword(String password) {
        this.password = password;
    }

    public String getBase64Icon() {
        return base64Icon;
    }

    public void setBase64Icon(String base64Icon) {
        this.base64Icon = base64Icon;
    }

    public byte[] getIcon() {
        return icon;
    }

    public void setIcon(byte[] icon) {
        this.icon = icon;
    }

    public int getIssystem() {
        return issystem;
    }

    public void setIssystem(int issystem) {
        this.issystem = issystem;
    }

    public int getId() {
        return id;
    }

    public List<UserRoles> getRoles() {
        return roles;
    }

    public void setRoles(List<UserRoles> roles) {
        this.roles = roles;
    }

    public void setId(int id) {
        this.id = id;
    }

    //返回权限
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> auths = new ArrayList<>();
        List<UserRoles> roles = this.getRoles();
        if(roles==null){
            return null;
        }
        for (UserRoles role : roles) {
            auths.add(new SimpleGrantedAuthority(role.getAuthority()));
        }
        return auths;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        //return false;
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        //return false;
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        //return false;
        return  true;
    }

    @Override
    public boolean isEnabled() {
        //return false;
        return true;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getActive() {
        return active;
    }

    public void setActive(int active) {
        this.active = active;
    }

    public boolean processIcon(){
        if(this.icon == null)
            return false;
        byte[] encodeBase64 = Base64.encodeBase64(this.icon);
        try {
            this.base64Icon = new String(encodeBase64, "UTF-8");
        } catch(Exception e){

        }
        return true;
    }
}
