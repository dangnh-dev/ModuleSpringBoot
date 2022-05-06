package com.example.demo.model;

import com.example.demo.dto.UserDTO;
import com.example.demo.entity.Provider;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserModel {
    private Long id;
    private String username;
    private String password;
    @Enumerated(EnumType.STRING)
    private Provider provider;

    public UserModel toModel(UserDTO userDTO){
        return new UserModel().builder()
                .id(userDTO.getId())
                .username(userDTO.getUsername())
                .password(userDTO.getPassword())
                .build();
    }
}
