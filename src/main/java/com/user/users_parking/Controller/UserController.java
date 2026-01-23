package com.user.users_parking.Controller;

import com.user.users_parking.Dto.Response.ResponseFindUserByEmail;
import com.user.users_parking.Services.UserServices;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserServices userServices;

    public UserController(UserServices userServices) {
        this.userServices = userServices;
    }

    @GetMapping("/find/{email}")
    public ResponseEntity<ResponseFindUserByEmail> findUserByEmail(@PathVariable String email) {
        ResponseFindUserByEmail users = userServices.findByEmail(email);
        if (users.nome() == null && users.email() == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        return ResponseEntity.status(HttpStatus.OK).body(users);
    }
}
