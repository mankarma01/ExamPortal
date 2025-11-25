package com.logical.examportal.restcontroller.impl;

import com.logical.examportal.entity.Admin;
import com.logical.examportal.model.response.MessageResponse;
import com.logical.examportal.restcontroller.AdminController;
import com.logical.examportal.service.AdminService;
import com.logical.examportal.service.DashBoardService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AdminControllerImpl implements AdminController {

    private final Logger logger = LoggerFactory.getLogger(AdminControllerImpl.class);

    @Autowired
    AdminService adminService;

    @Autowired
    DashBoardService dashBoardService;


    @Override
    public ResponseEntity<?> login(String username, String password) {
        try {
            return adminService.login(username, password);
        } catch (Exception e) {
            logger.info(e.toString());
            return new ResponseEntity<>(
                    new MessageResponse(false,
                            "Something went wrong...Don't worry we are figuring out what went wrong...!"),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<?> getDashboardDetails() {
        try {
            return dashBoardService.getDashBoardInfo();
        } catch (Exception e) {
            logger.info(e.toString());
            return new ResponseEntity<>(
                    new MessageResponse(false,
                            "Something went wrong...Don't worry we are figuring out what went wrong...!"),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<?> updateAdminPassword(Admin admin) {
        try {
            return adminService.updateAdminPassword(admin);
        } catch (Exception e) {
            logger.info(e.toString());
            return new ResponseEntity<>(
                    new MessageResponse(false,
                            "Something went wrong...Don't worry we are figuring out what went wrong...!"),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
