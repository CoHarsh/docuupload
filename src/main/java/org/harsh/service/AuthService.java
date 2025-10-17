package org.harsh.service;


import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.harsh.dto.UserDTO;
import org.harsh.model.User;
import org.harsh.repository.UserMongoRepository;

@Slf4j
@ApplicationScoped
@NoArgsConstructor
public class AuthService implements AuthServiceInterface {

    @Inject
    UserMongoRepository userMongoRepository;

    public UserDTO registerUser(UserDTO userPayload) {
        //check if user already exists
        userMongoRepository.find("email",userPayload.getEmail())
                .firstResultOptional()
                .ifPresent(u -> {
                    throw new RuntimeException("User with email " + userPayload.getEmail() + " already exists");
                });

        User user = User.builder()
                .email(userPayload.getEmail())
                .password(userPayload.getPassword())
                .build();
        userMongoRepository.persist(user);
        User savedUser = userMongoRepository.find("email",userPayload.getEmail()).firstResult();
        userPayload.setId(savedUser.getIdAsString());
        return userPayload;
    }

    public UserDTO loginUser(String email, String password) {
        User dbUser = userMongoRepository.find("email",email)
                .firstResultOptional()
                .orElseThrow(() -> new RuntimeException("User with email " + email + " not found"));

        if(!dbUser.getPassword().equals(password)){
            throw new RuntimeException("Invalid password");
        }

        return UserDTO.builder()
                .id(dbUser.getIdAsString())
                .email(dbUser.getEmail())
                .build();
    }
    
}
