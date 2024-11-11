package com.apuliadigitalmaker.gestionalespese.user;

public class UserRequestDTO {

    private String username;
    private String password;
    private Integer userId;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getEmployeeId() {
        return userId;
    }

    public void setEmployeeId(Integer employeeId) {
        this.userId = employeeId;
    }
}
