package com.logical.examportal.restcontroller;

import com.logical.examportal.entity.Admin;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/adminAPI")
public interface AdminController {

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestParam("username") String username, @RequestParam("password") String password);

    @GetMapping("/dashboard")
    ResponseEntity<?> getDashboardDetails();

    @PutMapping("/updatePassword")
    ResponseEntity<?> updateAdminPassword(@RequestBody Admin admin);


}