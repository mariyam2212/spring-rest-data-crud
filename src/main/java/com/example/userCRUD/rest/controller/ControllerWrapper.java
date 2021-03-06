/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.userCRUD.rest.controller;

import com.example.userCRUD.constants.All_Constants;
import static com.example.userCRUD.constants.All_Constants.NO_USER_WITH_THIS_ID;
import com.example.userCRUD.rest.beanspack.MainUserBean;
import com.example.userCRUD.bases.Output_Base;
import com.example.userCRUD.rest.model.UserInfo;
import com.example.userCRUD.rest.model.User_Output;
import javax.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import com.example.userCRUD.rest.repository.UserRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author fatima mariyam
 */
@RestController
@RequestMapping(path = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
public class ControllerWrapper implements All_Constants {
    
    @Autowired
    private UserRepository userRepository;

    //Get all users
    @RequestMapping(method = RequestMethod.GET, path = "/users")
    public User_Output getUsers(@RequestParam(required = false) String name, HttpServletRequest request) {
        
        List<UserInfo> user_list = new ArrayList<>();
        User_Output output = new User_Output();
        if (name == null) {
            userRepository.findAll().forEach(user_list::add);
        } else {
            userRepository.findByNameContaining(name).forEach(user_list::add);
        }
        
        if (user_list.isEmpty()) {
            output.setMessage(NO_USER_LIST);
        } else {
            output.setUsers(user_list);
        }
        return output;
        
    }

    //Get user by id
    @RequestMapping(method = RequestMethod.GET, path = "/users/{id}")
    public UserInfo getUserById(@PathVariable int id, HttpServletRequest request) {
        UserInfo out = new UserInfo();
        Optional<UserInfo> output = userRepository.findById(id);
        if (output.isPresent()) {
            return output.get();
        } else {
            out.setMessage(NO_USER_WITH_THIS_ID);
        }
        return out;
    }
    
    @RequestMapping(method = RequestMethod.POST, path = "/adduser")
    public Output_Base postAddUser(@RequestBody UserInfo input, HttpServletRequest request) {
        
        MainUserBean mainUserBean = new MainUserBean();
        Output_Base output = mainUserBean.addUser(input, userRepository);
        return output;
        
    }

    //Update UserInfo
    @RequestMapping(method = RequestMethod.PUT, path = "/updateuser/{id}")
    public Output_Base putUpdateUser(@PathVariable int id, @RequestBody UserInfo input, HttpServletRequest request) {
        
        MainUserBean mainUserBean = new MainUserBean();
        Output_Base output = mainUserBean.updateUser(id, input, userRepository);
        return output;
    }

    //Delete User
    @RequestMapping(method = RequestMethod.DELETE, path = "/deleteuser/{id}")
    public Output_Base putDeleteUser(@PathVariable int id, HttpServletRequest request) {
        
        MainUserBean mainUserBean = new MainUserBean();
        Output_Base outMssg = mainUserBean.deleteUser(id, userRepository);
        return outMssg;
        
    }

    //Delete all Users
    @RequestMapping(method = RequestMethod.DELETE, path = "/deleteuser")
    public Output_Base putDeleteAllUser(HttpServletRequest request) {
        
        MainUserBean mainUserBean = new MainUserBean();
        Output_Base outMssg = mainUserBean.deleteAllUser(userRepository);
        return outMssg;
        
    }

}
