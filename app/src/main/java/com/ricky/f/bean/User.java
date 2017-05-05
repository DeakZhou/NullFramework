package com.ricky.f.bean;

import java.io.Serializable;

/**
 * Created by Deak on 16/10/15.
 */

public class User implements Serializable {

    private String identity;
    private String account;
    private int type;
    private int gender;
    private String nickname;
    private String avatar;
    private String token;
    private long expireDate;

    private boolean isLogin;

    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public long getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(long expireDate) {
        this.expireDate = expireDate;
    }

    public boolean isLogin() {
        return isLogin;
    }

    public void setLogin(boolean login) {
        isLogin = login;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (type != user.type) return false;
        if (gender != user.gender) return false;
        if (expireDate != user.expireDate) return false;
        if (isLogin != user.isLogin) return false;
        if (identity != null ? !identity.equals(user.identity) : user.identity != null)
            return false;
        if (account != null ? !account.equals(user.account) : user.account != null) return false;
        if (nickname != null ? !nickname.equals(user.nickname) : user.nickname != null)
            return false;
        if (avatar != null ? !avatar.equals(user.avatar) : user.avatar != null) return false;
        return token != null ? token.equals(user.token) : user.token == null;

    }

    @Override
    public int hashCode() {
        int result = identity != null ? identity.hashCode() : 0;
        result = 31 * result + (account != null ? account.hashCode() : 0);
        result = 31 * result + type;
        result = 31 * result + gender;
        result = 31 * result + (nickname != null ? nickname.hashCode() : 0);
        result = 31 * result + (avatar != null ? avatar.hashCode() : 0);
        result = 31 * result + (token != null ? token.hashCode() : 0);
        result = 31 * result + (int) (expireDate ^ (expireDate >>> 32));
        result = 31 * result + (isLogin ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        return "User{" +
                "identity='" + identity + '\'' +
                ", account='" + account + '\'' +
                ", type=" + type +
                ", gender=" + gender +
                ", nickname='" + nickname + '\'' +
                ", avatar='" + avatar + '\'' +
                ", token='" + token + '\'' +
                ", expireDate=" + expireDate +
                ", isLogin=" + isLogin +
                '}';
    }
}
