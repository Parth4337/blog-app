package com.example.blog.payloads;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class JwtAuthRequest {
    private String username;
    private String password;
}
