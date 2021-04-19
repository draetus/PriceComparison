package br.com.univali.pricecomparison.api.user.service;

import java.util.Optional;

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
	
	public Optional<User> findById(Long id) {
		return userRepository.findById(id);
	}

}
