package com.hien.service;

import java.util.List;

import com.hien.entity.User;

public interface UserService {
	List<User> findAll();

	User findByName(String username);

	User findById(int id);

	User save(User user);

	User findByEmail(String email);

	void update(User user);

	void deleteById(int id);

	User findByEmailAndPassword(String email, String password);

	public int countUser();
}
