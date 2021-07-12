/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.userCRED.rest.beanspack;

import com.example.userCRED.constants.All_Constants;
import static com.example.userCRED.constants.All_Constants.NO_USER_WITH_THIS_ID;
import com.example.userCRUD.bases.Output_Base;
import com.example.userCRUD.rest.model.UserInfo;
import com.example.userCRUD.rest.model.User_Output;
import com.example.userCRUD.rest.repository.UserRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author fatima mariyam
 */
public class MainUserBean implements All_Constants {

    @Autowired
    private UserRepository userRepository;

    //not in use
    List<UserInfo> arr_users = new ArrayList<>();
    List<String> cities = new ArrayList<>(List.of("Delhi", "Mumbai", "Bangalore", "Noida", "Pune"));
    List<String> contacts = new ArrayList<>(List.of("9826382917", "9998827362", "9918262909", "8756482976", "8882659037"));
    List<String> emails = new ArrayList<>(List.of("david123@gmail.com", "albertHash@gmail.com", "becky.1299@gmail.com", "mary12rose@gmail.com", "henry_cavil@gmail.com"));

    public MainUserBean() {
        //
    }

    public User_Output getUsersList() {

        User_Output outputUsers = new User_Output();
        initialiseUserList();
        outputUsers.setUsers(arr_users);
        return outputUsers;

    }

    public Optional<UserInfo> getUserById(int id) {

        for (UserInfo uI : arr_users) {
            if (uI.getId() == id) {
                return Optional.of(uI);
            }
        }
        return Optional.ofNullable(null);
    }

    public Output_Base addUser(UserInfo input, UserRepository userRepository) {
        Output_Base output = new Output_Base();
        UserInfo userToAdd = new UserInfo();
        Random rand = new Random();
        userToAdd.setName(input.getName());
        userToAdd.setContact(input.getContact());
        userToAdd.setEmail(input.getEmail());
        userToAdd.setLocation(input.getLocation());
        userToAdd.setDesignation(input.getDesignation());
        try {
            UserInfo addedUser = userRepository.save(userToAdd);

            if (addedUser != null) {
                output.setMessage(USER_ADDED);
            } else {
                output.setMessage(NO_USER_WITH_THIS_ID);
            }
        } catch (Exception ex) {
            //
        }
        return output;
    }

    public Output_Base updateUser(int id, UserInfo input, UserRepository userRepository) {
        Output_Base output = new Output_Base();
        try {
            Optional<UserInfo> userFromDb = userRepository.findById(id);
            if (userFromDb.isPresent()) {
                userFromDb.get().setId(id);
                userFromDb.get().setName(input.getName());
                userFromDb.get().setContact(input.getContact());
                userFromDb.get().setEmail(input.getEmail());
                userFromDb.get().setLocation(input.getLocation());
                userFromDb.get().setDesignation(input.getDesignation());
                UserInfo addedUser = userRepository.save(userFromDb.get());
                if (addedUser != null) {
                    output.setMessage(USER_UPDATED);
                } else {
                    output.setMessage(NO_USER_WITH_THIS_ID);
                }
            }
        } catch (Exception ex) {
            //System.out.println(ex.getMessage());
        }
        return output;
    }

    public Output_Base deleteUser(int id, UserRepository userRepository) {
        String message = "";
        Output_Base output = new Output_Base();
        Optional<UserInfo> user = userRepository.findById(id);
        if (user.isPresent()) {
            userRepository.deleteById(id);
            output.setMessage(USER_REMOVED);
        } else {
            output.setMessage(NO_USER_WITH_THIS_ID);
        }
        return output;
    }

    public void initialiseUserList() {
        for (int i = 0; i < 5; i++) {
            UserInfo objU = new UserInfo(i, "User_" + i, emails.get(i), contacts.get(i), cities.get(i), "SDE_" + i);
            arr_users.add(objU);
        }
    }

    public int chechIfEmailAlreadyAdded(UserInfo input) {

        List<String> email_list = arr_users.stream().map(user -> user.getEmail()).collect(Collectors.toList());
        if (email_list.contains(input.getEmail())) {
            return -1;
        }
        return 0;

    }

    public int getMaxId() {
        return arr_users.stream().map(user -> user.getId()).max(Integer::compare).get();
    }

    public Optional<Integer> checkAndGetIdPosition(int id) {
        for (int i = 0; i < arr_users.size(); i++) {
            UserInfo user = arr_users.get(id);
            if (user.getId() == id) {
                return Optional.of(i);
            }
        }
        return Optional.ofNullable(null);
    }
}
