package com.sep.bank.bankservice.service.serviceImpl;

import com.sep.bank.bankservice.entity.User;
import com.sep.bank.bankservice.repository.UserRepository;
import com.sep.bank.bankservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;


    /**
     * Save new user into db.
     *
     * @param user user
     * @return save user
     */
    @Override
    public User create(User user) {
        return userRepository.save(user);
    }
}
