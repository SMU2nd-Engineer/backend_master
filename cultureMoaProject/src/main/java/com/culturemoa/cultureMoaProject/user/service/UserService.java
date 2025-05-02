package com.culturemoa.cultureMoaProject.user.service;

import com.culturemoa.cultureMoaProject.user.dto.UserDTO;
import com.culturemoa.cultureMoaProject.user.repository.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserDAO userDAO;

    public List<UserDTO> getAllUser(int userIdx){
        return userDAO.getAllUser(userIdx);
    }
}
