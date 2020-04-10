package com.project.omega.service;

import com.project.omega.bean.dao.Industry;
import com.project.omega.bean.dao.User;
import com.project.omega.exceptions.DuplicateUserException;
import com.project.omega.exceptions.NoRecordsFoundException;
import com.project.omega.exceptions.UserNotFoundException;

import java.util.List;

public interface UserService {
    List<User> getAllUsers() throws NoRecordsFoundException;
    User getUserById(Long id) throws UserNotFoundException;
    User deleteUserById(Long id) throws UserNotFoundException;
    User updateUserById(Long id, User update) throws UserNotFoundException, Exception;
    User updateUserByIdForIndustry(Long id, String updateIndustry) throws Exception;
    List<User> getUsersFromIndustry(String industryName) throws NoRecordsFoundException;
}
