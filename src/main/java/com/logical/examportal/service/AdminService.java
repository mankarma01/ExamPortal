package com.logical.examportal.service;

import com.logical.examportal.entity.Admin;
import org.springframework.http.ResponseEntity;
public interface AdminService {

    ResponseEntity<?> login(String username, String password);
    ResponseEntity<?> updateAdminPassword(Admin admin);

}
