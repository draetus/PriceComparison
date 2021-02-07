package br.com.univali.pricecomparison.api.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.univali.pricecomparison.api.user.model.User;
import br.com.univali.pricecomparison.api.user.repository.UserRepository;

@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	public User save(User user) {
		return userRepository.save(user);
	}

}
