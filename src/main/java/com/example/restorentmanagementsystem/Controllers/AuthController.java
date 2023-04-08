package com.example.restorentmanagementsystem.Controllers;

import com.example.restorentmanagementsystem.DTO.*;
import com.example.restorentmanagementsystem.Constants.Messages;
import com.example.restorentmanagementsystem.Models.User;
import com.example.restorentmanagementsystem.Repositories.UserDAO;
import com.example.restorentmanagementsystem.Services.UserDetailsService;
import com.example.restorentmanagementsystem.Services.UserService;
import com.example.restorentmanagementsystem.Utils.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@CrossOrigin   //allows cross-origin request.Useful because frontend and backend served from diffrent port/Domain
@RestController   // This class is a REST controller will handel incoming HTTP request
@RequestMapping("api/auth")    // base url for api en-point in this controller is api/auth
public class AuthController {

    @Autowired
    UserDAO userDAO;

    @Autowired
    UserService userService;

    @Autowired
    private JWTUtil jwtUtil;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public ResponseEntity<BasicResponseDTO<RegisterResponseDTO>> registerUser(@RequestBody RegisterRequestDTO registerRequestDTO) {
        BasicResponseDTO<RegisterResponseDTO> basicResponseDTO = new BasicResponseDTO<>();
        basicResponseDTO.setData(null);
        basicResponseDTO.setSuccess(true);
        basicResponseDTO.setMessage("Registration Successfully.");
        try {

            if (registerRequestDTO.getRole().equalsIgnoreCase("admin") && !registerRequestDTO.getMasterPassword().equals("India@123")) {
                basicResponseDTO.setMessage("Wrong master password");
                basicResponseDTO.setSuccess(false);
                return new ResponseEntity<>(basicResponseDTO, HttpStatus.OK);
            }

            Optional<RegisterResponseDTO> rr = userService.addUser(registerRequestDTO);
            if (!rr.isPresent()) {
                basicResponseDTO.setSuccess(false);
                basicResponseDTO.setMessage(Messages.userAlreadyExists);
                return new ResponseEntity<>(basicResponseDTO, HttpStatus.OK);
            }

            basicResponseDTO.setData(rr.get());
            basicResponseDTO.setSuccess(true);
            basicResponseDTO.setMessage("Registration Successfully.");
        }catch (Exception ex){
            basicResponseDTO.setSuccess(false);
            basicResponseDTO.setMessage("Exception Message : "+ex.getMessage());
        }
        return new ResponseEntity<>(basicResponseDTO, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<BasicResponseDTO<LoginResponseDTO>> login(@RequestBody LoginRequestDTO loginRequestDTO){
        BasicResponseDTO<LoginResponseDTO> result = new BasicResponseDTO<>();
        result.setSuccess(true);
        result.setMessage("Login Successfully");
        try {

            Optional<User> user = userDAO
                    .findAll()
                    .stream()
                    .filter(X->(X.getUserName().equalsIgnoreCase(loginRequestDTO.getEmail()) ||
                            X.getEmail().equalsIgnoreCase(loginRequestDTO.getEmail())) &&
                            //X.getRole().equalsIgnoreCase(loginRequestDTO.getRole()) &&
                            X.getPassword().equals(loginRequestDTO.getPassword()))
                    .findFirst();

            if(!user.isPresent()){

                 user = userDAO
                        .findAll()
                        .stream()
                        .filter(X->X.getUserName().equalsIgnoreCase(loginRequestDTO.getEmail()) &&
                                X.getRole().equalsIgnoreCase(loginRequestDTO.getRole()) &&
                                X.getPassword().equals(loginRequestDTO.getPassword()))
                        .findFirst();

                if(!user.isPresent()){

                    result.setSuccess(false);
                    result.setMessage("Login Unsuccessfull");
                    return ResponseEntity.ok(result);
                }
            }

            final UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequestDTO.getEmail());

            LoginResponseDTO data = new LoginResponseDTO();
            data.setSuccess(true);
            data.setMessage("Login Successfully");
            data.setUser(user.get());
            data.setToken(jwtUtil.generateToken(userDetails));
            result.setData(data);

        }catch (Exception ex){
            result.setSuccess(false);
            result.setMessage("Exception Message : "+ex.getMessage());
        }

        return ResponseEntity.ok(result);
    }
}
