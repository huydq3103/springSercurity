package com.managedrink.services.implement;

import com.managedrink.dto.UserDTO;
import com.managedrink.entity.RoleEntity;
import com.managedrink.entity.UserEntity;
import com.managedrink.jwt.JwtUtil;
import com.managedrink.ldap.LdapAuthenticationService;
import com.managedrink.payload.request.LoginRequest;
import com.managedrink.payload.request.SignUpRequest;
import com.managedrink.payload.response.JwtResponse;
import com.managedrink.repository.RoleRepository;
import com.managedrink.repository.UserRepository;
import com.managedrink.services.IUserService;
import com.managedrink.until.mapper.UserMapper;
import com.managedrink.until.validate.ValidationUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

/**
 * Class: UserServiceImpl
 * Author: ACER
 * Date: 7/23/2024
 * Description: [Your description here]
 */
@Service
@Slf4j
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;


    /**
     * Đăng ký người dùng mới.
     *
     * @param signUpRequest yêu cầu đăng ký người dùng.
     * @return thông tin người dùng sau khi đăng ký.
     */
    @Override
    public UserDTO register(SignUpRequest signUpRequest) {

        ValidationUtils.validateObject(signUpRequest);

        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            throw new RuntimeException("Error: Username is already taken!");
        }

        UserEntity user = new UserEntity();

        // Gán role mặc định cho user mới
        RoleEntity userRole = roleRepository.findByName("ROLE_USER")
                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));


        user.setUsername(signUpRequest.getUsername());
        user.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));

        user.setRoles(Collections.singleton(userRole));

        userRepository.save(user);

        return UserMapper.ConvertEntityToDTO(user);
    }

    /**
     * Xác thực người dùng và tạo token JWT.
     *
     * @param loginRequest yêu cầu đăng nhập của người dùng.
     * @return phản hồi chứa token JWT.
     */

    @Autowired
    private LdapAuthenticationService ldapAuthenticationService;

    public JwtResponse authenticateUser(LoginRequest loginRequest) {
        ValidationUtils.validateObject(loginRequest);
        boolean isLdap = false;
        UserDetails userDetails;

        // them moi
        if (loginRequest.isLdap()) {
            // Xác thực LDAP
            userDetails = ldapAuthenticationService.authenticateLdapUser(
                    loginRequest.getUsername(), loginRequest.getPassword());
            isLdap = true;
        } else {
            // Xác thực thông thường
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getUsername(),
                            loginRequest.getPassword()
                    )
            );
            userDetails = (UserDetails) authentication.getPrincipal();
        }

        // Tạo JWT token
        String jwt = jwtUtil.generateToken(userDetails, isLdap);

        // Đặt xác thực vào SecurityContext
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authToken);

        return new JwtResponse(jwt);
    }
}
