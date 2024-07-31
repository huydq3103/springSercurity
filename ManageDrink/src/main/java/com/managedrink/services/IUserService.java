package com.managedrink.services;

import com.managedrink.dto.UserDTO;
import com.managedrink.payload.request.LoginRequest;
import com.managedrink.payload.request.SignUpRequest;
import com.managedrink.payload.response.JwtResponse;


public interface IUserService {

    /**
     * Đăng ký người dùng mới.
     *
     * @param signUpRequest yêu cầu đăng ký người dùng.
     * @return thông tin người dùng sau khi đăng ký.
     */
    UserDTO register(SignUpRequest signUpRequest);

    /**
     * Xác thực người dùng và tạo token JWT.
     *
     * @param loginRequest yêu cầu đăng nhập của người dùng.
     * @return phản hồi chứa token JWT.
     */
    JwtResponse authenticateUser(LoginRequest loginRequest);
}
