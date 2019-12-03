package com.hien.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.hien.entity.User;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {
	List<User> findAll();

	User findByName(String name);

	User findById(int id);

	User save(User user);

	void deleteById(int id);

	User findByEmail(String email);

	@Transactional
	@Modifying
	@Query("UPDATE User u SET u.name = ?1, u.email = ?2, u.password = ?3 WHERE u.id = ?4")
	void update(String name, String email, String password, int id);
	
	User findByEmailAndPassword(String email, String password);

}
