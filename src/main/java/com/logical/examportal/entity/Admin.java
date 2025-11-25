package com.logical.examportal.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Admin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long adminId;
    private String username;
    private String password;
    @Column(nullable = false)
    private String role;

    @Transient
    private String newPassword;
    @Transient
    private String renewPassword;

}
