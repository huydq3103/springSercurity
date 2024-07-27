package com.managedrink.controller;

import com.managedrink.dto.UserDTO;
import com.managedrink.payload.request.LoginRequest;
import com.managedrink.payload.request.SignUpRequest;
import com.managedrink.services.implement.UserServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller quản lý các API liên quan đến người dùng (đăng ký và đăng nhập).
 */
@RestController
@RequestMapping("/api/auth")
public class UserController {

    @Autowired
    private UserServiceImpl userService;

    /**
     * API để đăng ký người dùng mới.
     *
     * @param signUpRequest Đối tượng DTO chứa thông tin người dùng để đăng ký.
     * @return ResponseEntity chứa thông tin về người dùng đã đăng ký và HttpStatus.OK nếu thành công.
     */
    @Operation(summary = "Register new user", description = "API để đăng ký người dùng mới")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User registered successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping("/register")
    public ResponseEntity<UserDTO> registerUser(
            @Parameter(description = "Đối tượng DTO chứa thông tin người dùng để đăng ký", required = true)
           @Valid @RequestBody SignUpRequest signUpRequest) {
        UserDTO userDTO = userService.register(signUpRequest);
        return ResponseEntity.ok(userDTO);
    }

    /**
     * API để xác thực người dùng và cấp token JWT.
     *
     * @param loginRequest Đối tượng DTO chứa thông tin đăng nhập của người dùng.
     * @return ResponseEntity chứa JWT token và HttpStatus.OK nếu thành công.
     */
    @Operation(summary = "Authenticate user and generate JWT", description = "API để xác thực người dùng và cấp token JWT")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Authentication successful and JWT token generated"),
            @ApiResponse(responseCode = "400", description = "Username or password incorrect"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(
            @Parameter(description = "Đối tượng DTO chứa thông tin đăng nhập của người dùng", required = true)
            @Valid @RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok(userService.authenticateUser(loginRequest));
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request, HttpServletResponse response) {
        SecurityContextLogoutHandler logoutHandler = new SecurityContextLogoutHandler();
        logoutHandler.logout(request, response, null);
        return ResponseEntity.ok("Logout successful");
    }

}
