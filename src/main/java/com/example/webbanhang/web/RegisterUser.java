package com.example.webbanhang.web;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class RegisterUser {


    @NotBlank(message = "Vui lòng nhập đầy đủ thông tin")
    @Size(min = 1, message = "Độ dài tối thiểu là 1 ký tự")
    private String username;

    @NotBlank(message = "Vui lòng nhập mật khẩu")
    @Size(min = 8, message = "Độ dài tối thiểu là 8 ký tự")
    private String password;

    @NotBlank(message = "Vui lòng nhập đầy đủ thông tin")
    private String firstName;

    @NotBlank(message = "Vui lòng nhập đầy đủ thông tin")
    private String lastName;

    @NotBlank(message = "Vui lòng nhập đầy đủ thông tin")
    @Email(message = "Email không hợp lệ")
    private String email;

    public RegisterUser() {
    }

    public RegisterUser(String username, String password, String firstName, String lastName, String email) {
        this.username = username;
        this.password = password;

        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

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


    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
