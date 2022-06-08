package com.Help.Center.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Help.Center.Models.Users;

public interface UsersRepo extends JpaRepository<Users, Integer> {


	//Users findByuserNameAndPassword(String userName, String password);

	Users findByUserName(String username);
}
