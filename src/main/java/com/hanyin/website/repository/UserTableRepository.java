package com.hanyin.website.repository;

import com.hanyin.website.entity.UserTable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserTableRepository extends JpaRepository<UserTable, Integer> {

    UserTable findByUsernameAndPassword(String username, String password);
}
