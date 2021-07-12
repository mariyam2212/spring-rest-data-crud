/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.userCRUD.rest.repository;

import com.example.userCRUD.rest.model.UserInfo;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

/**
 *
 * @author fatima mariyam
 */

public interface UserRepository extends JpaRepository<UserInfo, Integer> {
    List<UserInfo> findByContact(String contact);
    List<UserInfo> findByName(String name);
}
