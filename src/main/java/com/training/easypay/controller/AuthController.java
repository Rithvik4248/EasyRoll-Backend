package com.training.easypay.controller;

import com.training.easypay.model.Designation;
import com.training.easypay.model.Employee;
import com.training.easypay.model.EmployeeStatus;
import com.training.easypay.payload.JwtResponse;
import com.training.easypay.payload.LoginRequest;
import com.training.easypay.payload.MessageResponse;
import com.training.easypay.payload.SignupRequest;
import com.training.easypay.repositories.EmployeeRepository;
import com.training.easypay.security.jwt.JwtUtils;
import com.training.easypay.security.services.UserDetailsImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        String designation = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .findFirst().orElse(null);

        return ResponseEntity.ok(new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(), // This is the email
                userDetails.getUsername(),
                designation));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
        if (employeeRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email is already in use!"));
        }

        Designation designation;
        try {
            designation = Designation.valueOf(signUpRequest.getDesignation().toUpperCase());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Invalid designation!"));
        }

        // Using the @AllArgsConstructor from Lombok to ensure all fields are set.
        // This is the most direct way to create the entity and should be the most reliable.
        Employee employee = new Employee(
                null, // id is auto-generated
                signUpRequest.getFirstName(),
                signUpRequest.getLastName(),
                signUpRequest.getEmail(),
                encoder.encode(signUpRequest.getPassword()),
                signUpRequest.getPhone(),
                designation,
                signUpRequest.getSalary(),
                EmployeeStatus.ACTIVE // Explicitly set the default status
        );

        employeeRepository.save(employee);

        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }
}
