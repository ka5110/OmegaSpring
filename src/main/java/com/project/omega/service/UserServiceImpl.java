package com.project.omega.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.omega.bean.dao.Industry;
import com.project.omega.bean.dao.User;
import com.project.omega.exceptions.NoRecordsFoundException;
import com.project.omega.exceptions.UserNotFoundException;
import com.project.omega.helper.Constant;
import com.project.omega.repository.IndustryRepository;
import com.project.omega.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    IndustryRepository industryRepository;

    @Autowired
    ObjectMapper objectMapper;

    public List<User> getAllUsers() throws NoRecordsFoundException {
        List<User> users = (List) userRepository.findAll();
        if(users.isEmpty()) {
             throw new NoRecordsFoundException(Constant.ERROR_NO_RECORDS);
        }
        return users;
    }

    public User getUserById(Long id) throws UserNotFoundException {
        Optional<User> user = userRepository.findById(id);
        if(!user.isPresent()) {
            throw new UserNotFoundException(Constant.ERROR_USER_NOT_FOUND + id);
        }
        return user.get();
    }

    public User deleteUserById(Long id) throws UserNotFoundException {
        Optional<User> user = userRepository.findById(id);
        if(!user.isPresent()) {
            throw new UserNotFoundException(Constant.ERROR_USER_NOT_FOUND + id);
        }
        userRepository.deleteById(id);
        return user.get();
    }

    public User updateUserById(Long id, User userDetails) throws Exception {
        Optional<User> user = userRepository.findById(id);
        if(!user.isPresent()) {
            throw new UserNotFoundException(Constant.ERROR_USER_NOT_FOUND + id);
        }
        User u = new User.UserBuilder()
                .setId(id)
                .setEmail(userDetails.getEmail())
                .setPassword(userDetails.getPassword())
                .build();

        userRepository.save(u);
        return userDetails;
    }

    public User updateUserByIdForIndustry(Long id, String updateIndustry) throws Exception {
        Optional<User> user = userRepository.findById(id);
        Industry industry = industryRepository.findByName(updateIndustry).get();
        if(!user.isPresent()) {
            throw new UserNotFoundException(Constant.ERROR_USER_NOT_FOUND + id);
        }
        user.get().setIndustry(industry);
        userRepository.save(user.get());
        return user.get();
    }

    public List<User> getUsersFromIndustry(String name) throws NoRecordsFoundException {
        Optional<Industry> industry = industryRepository.findByName(name);
        if(!industry.isPresent()) {
            throw new NoRecordsFoundException(Constant.ERROR_NO_RECORDS);
        }
        Long industryId = industry.get().getId();
        List<User> users = userRepository.findAllByIndustry(industryId);
        return users;
    }
}
