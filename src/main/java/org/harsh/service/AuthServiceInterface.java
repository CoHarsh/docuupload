package org.harsh.service;

import org.harsh.dto.UserDTO;
import org.harsh.model.User;

public interface AuthServiceInterface {
    public UserDTO registerUser(UserDTO user);
    public UserDTO loginUser(String email, String password);

}
