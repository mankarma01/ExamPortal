package com.logical.examportal.service.impl;

import com.logical.examportal.entity.Admin;
import com.logical.examportal.model.response.GenericResponse;
import com.logical.examportal.model.response.MessageResponse;
import com.logical.examportal.repository.AdminRepository;
import com.logical.examportal.service.AdminService;
import com.logical.examportal.service.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    AdminRepository adminRepository;

    private final BCryptPasswordEncoder passwordEncoder;


    public AdminServiceImpl(BCryptPasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public ResponseEntity<?> login(String username, String password) {

       Optional<Admin> admin = adminRepository.findByUsernameAndPassword(username, password);
       if(admin.isPresent()){
           System.out.println("User login successfully.");
           return new ResponseEntity<>( new GenericResponse<>(true, "Login successfully", admin), HttpStatus.OK);
       }
        System.out.println("Authorization failed.");
       return new ResponseEntity<>(new MessageResponse(false, "login failed username or password is incorrect."), HttpStatus.NOT_FOUND);
    }


    private String getCurrentUsername() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof CustomUserDetails) {
            return ((CustomUserDetails) principal).getUsername();
        } else {
            return principal.toString(); // Fallback case
        }
    }

    @Override
    public ResponseEntity<?> updateAdminPassword(Admin admin) {

        String username = getCurrentUsername();

        // Fetch the admin record from the database
        Optional<Admin> adminData = adminRepository.findByUsername(username);
        if (adminData.isEmpty()) {
            return new ResponseEntity<>(new MessageResponse(false, "User not found."), HttpStatus.OK);
        }

        Admin existingAdmin = adminData.get();

        existingAdmin.setPassword(passwordEncoder.encode(admin.getNewPassword()));
        adminRepository.save(existingAdmin);

        return new ResponseEntity<>(new MessageResponse(true, "Password updated successfully."), HttpStatus.OK);
    }

}
