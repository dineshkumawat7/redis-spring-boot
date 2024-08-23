package com.example.redis.payload;

import lombok.*;

@Getter
@Setter
@RequiredArgsConstructor
@ToString
public class EmployeeDto {
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String password;
}
