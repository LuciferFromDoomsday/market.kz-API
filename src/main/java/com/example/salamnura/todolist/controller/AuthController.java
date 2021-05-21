package com.example.salamnura.todolist.controller;

import com.example.salamnura.todolist.entities.Roles;
import com.example.salamnura.todolist.entities.Users;
import com.example.salamnura.todolist.jwt.JwtUtils;
import com.example.salamnura.todolist.payload.request.LoginRequest;
import com.example.salamnura.todolist.payload.request.SignupRequest;
import com.example.salamnura.todolist.payload.response.JwtResponse;
import com.example.salamnura.todolist.payload.response.MessageResponse;
import com.example.salamnura.todolist.repositories.RolesRepository;
import com.example.salamnura.todolist.repositories.UsersRepository;
import com.example.salamnura.todolist.services.impl.UserDetailsImpl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UsersRepository userRepository;

    @Autowired
    RolesRepository roleRepository;

    @Autowired
    BCryptPasswordEncoder passwordEncoder;

    @Autowired
    JwtUtils jwtUtils;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());
        List <Roles> roless=new ArrayList<>();
        Roles role= new Roles();
        for (String r: roles){
            if(roleRepository.findByRole(r).isPresent()){
                role=roleRepository.findByRole(r).orElseThrow(() -> new UsernameNotFoundException("User Not Found with roles: "));
                roless.add(role);
            }

        }
        return ResponseEntity.ok(new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getFullname(),
                userDetails.getEmail(),
                roless));
    }
    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {


        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email is already in use!"));
        }

        // Create new user's account
        Users user = new Users();
        user.setEmail(signUpRequest.getEmail());
        System.out.println(signUpRequest.getEmail());
        user.setFullName(signUpRequest.getFullname());
        user.setPassword( passwordEncoder.encode(signUpRequest.getPassword()));
        List<Roles> strRoles = signUpRequest.getRole();
        List<Roles> roles = new ArrayList<>();
        long millis=System.currentTimeMillis();
        java.sql.Date date=new java.sql.Date(millis);
        user.setRegisterDate(date);

        if (strRoles == null) {
            Roles userRole = roleRepository.findByRole("ROLE_USER")
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role.getRole()) {
                    case "ROLE_ADMIN":
                        Roles adminRole = roleRepository.findByRole("ROLE_ADMIN")
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(adminRole);

                        break;
                    case "ROLE_MODERATOR":
                        Roles modRole = roleRepository.findByRole("ROLE_MODERATOR")
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(modRole);

                        break;
                    default:
                        Roles userRole = roleRepository.findByRole("ROLE_USER")
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(userRole);
                }
            });
        }

        user.setRoles(roles);
        userRepository.save(user);

        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }
}
