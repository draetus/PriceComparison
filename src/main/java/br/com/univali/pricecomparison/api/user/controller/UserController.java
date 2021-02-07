package br.com.univali.pricecomparison.api.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.univali.pricecomparison.api.user.model.User;
import br.com.univali.pricecomparison.api.user.model.DTO.UserRequest;
import br.com.univali.pricecomparison.api.user.model.DTO.UserResponse;
import br.com.univali.pricecomparison.api.user.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@Transactional
	@PostMapping
	public ResponseEntity<UserResponse> save(@RequestBody UserRequest userRequest){
		User user = userRequest.generateModel();
		user = userService.save(user);
		UserResponse userResponse = user.generateResponse();
		return new ResponseEntity<>(userResponse, HttpStatus.OK);
	}

}
