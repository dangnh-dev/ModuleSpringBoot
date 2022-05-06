package com.example.demo.service;

import com.example.demo.dto.UserDTO;
import com.example.demo.entity.Provider;
import com.example.demo.entity.RoleEntity;
import com.example.demo.entity.UserEntity;
import com.example.demo.model.UserModel;
import com.example.demo.repository.IRoleRepository;
import com.example.demo.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class UserService {
    @Autowired
    IUserRepository iUserRepository;
    @Autowired
    IRoleRepository iRoleRepository;

    public void checkUserExistOauth2(String email, Provider provider){
        UserEntity existUser = iUserRepository.findByUsername(email);
        UserEntity userEntity = new UserEntity();
        if (existUser == null){
            userEntity.setUsername(email);
            userEntity.setProvider(provider);
            iUserRepository.save(userEntity);
        } else {
            if (existUser.getProvider() != provider){
                userEntity.setUsername(email);
                userEntity.setProvider(provider);
                iUserRepository.save(userEntity);
            }
        }
    }

    public boolean addNewUser(UserModel userModel){
        RoleEntity USER_ROLE = iRoleRepository.getById(2l);
        UserEntity existUser = iUserRepository.findByUsername(userModel.getUsername());
        if (existUser == null){
            UserEntity userEntity = new UserEntity();
            userEntity.setUsername(userModel.getUsername());
            userEntity.setPassword(userModel.getPassword());
            userEntity.setProvider(Provider.LOCAL);
            userEntity.setRoles(Set.of(USER_ROLE));
            iUserRepository.save(userEntity);
            return true;
        }
        return false;
    }
}
