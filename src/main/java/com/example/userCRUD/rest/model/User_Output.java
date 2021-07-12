/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.userCRUD.rest.model;

import com.example.userCRUD.bases.Output_Base;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author fatima mariyam
 */
public class User_Output extends Output_Base {
    private List<UserInfo> users = new ArrayList<>();

    public List<UserInfo> getUsers() {
        return users;
    }

    public void setUsers(List<UserInfo> users) {
        this.users = users;
    }
    
}
