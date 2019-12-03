package com.hien.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hien.entity.User;
import com.hien.service.JwtService;
import com.hien.service.UserService;

@RestController
@RequestMapping(value = "api/v1/users")
public class UserController {

	@Autowired
	private JwtService jwtService;

	@Autowired
	private UserService userService;

	@GetMapping(value = { "", "/" })
	public ResponseEntity<List<User>> getAllUsers() {
		List<User> users = userService.findAll();
		if (users.isEmpty()) {
			return new ResponseEntity<List<User>>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<User>>(users, HttpStatus.OK);
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<User> getUserById(@PathVariable int id) {
		User user = userService.findById(id);
		if (user != null) {
			return new ResponseEntity<User>(user, HttpStatus.OK);
		}
		return new ResponseEntity<User>(HttpStatus.NO_CONTENT);
	}

	@PostMapping
	public ResponseEntity<User> createUser(@RequestBody User user) {
		if (userService.findByEmail(user.getEmail()) != null) {
			System.out.println("A User with name " + user.getName() + " already exist");
			return new ResponseEntity<User>(user, HttpStatus.CONFLICT);
		}
		userService.save(user);
		return new ResponseEntity<User>(user, HttpStatus.CREATED);
	}

	@PutMapping(value = "/{id}")
	public ResponseEntity<Void> updateUserById(@RequestBody User user, @PathVariable int id) {
		if (userService.findById(id) == null) {
			System.out.println("A User with name " + user.getName() + " dont exist");
			return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
		}
		userService.update(user);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> deleteUserById(@PathVariable int id) {
		if (userService.findById(id) == null) {
			System.out.println("A User with id " + id + " dont exist");
			return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
		}
		userService.deleteById(id);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}

	@PostMapping(value = "/login")
	public ResponseEntity<String> login(HttpServletRequest request, @RequestBody User u) {
		String result = "";
		HttpStatus httpStatus = null;
		System.out.println("user 1 : " + u.toString());
		try {
			User user = userService.findByEmailAndPassword(u.getEmail(), u.getPassword());
			System.out.println("user 2 : " + user.toString());
			result = jwtService.generateToken(user.getName());
			httpStatus = HttpStatus.OK;
		} catch (Exception ex) {
			result = "error : " + ex.toString();
			httpStatus = HttpStatus.BAD_REQUEST;
		}
		return new ResponseEntity<String>(result, httpStatus);
	}

}
