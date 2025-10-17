package org.harsh.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
public class UserDTO {
    private String id;
    private String email;
    private String password;
}
