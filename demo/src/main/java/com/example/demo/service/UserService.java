package com.example.demo.service;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    UserRepository userRepo;

    public void save(User user){
        userRepo.save(user);
    }
    public User findByEmail(String email) {
        return userRepo.findByEmail(email);
    }
    public User findByEmailAndPassword(String email, String password){
        return userRepo.findByEmailAndPassword(email,password);
    }
    public  User findByUserName(String userName){
        return userRepo.findByUserName(userName);
    }


}
