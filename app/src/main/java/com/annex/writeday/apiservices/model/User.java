package com.annex.writeday.apiservices.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

public class User extends RealmObject {
    @PrimaryKey
    private String username;

    @Required
    private String userId;
    private String phone;
    private String friends;
    private String profileImage;
    private String email;
    private String nickname;
    private String role;
    private String friedStatus;

    public User() {
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setFriends(String friends) {
        this.friends = friends;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setFriedStatus(String friedStatus) {
        this.friedStatus = friedStatus;
    }

    public void setUsername(String username) {
        this.username = username;
    }





    public String getUserId() {
        return userId;
    }

    public String getPhone() {
        return phone;
    }

    public String getFriends() {
        return friends;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public String getEmail() {
        return email;
    }

    public String getNickname() {
        return this.nickname;
    }

    public String getRole() {
        return this.role;
    }

    public String getFriedStatus() {
        return friedStatus;
    }

    public String getUsername() {
        return this.username;
    }

}