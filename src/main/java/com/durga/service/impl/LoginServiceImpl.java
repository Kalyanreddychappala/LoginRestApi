package com.durga.service.impl;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.durga.dto.LoginDto;
import com.durga.entity.LoginEntity;
import com.durga.repository.LoginRepository;
import com.durga.service.LoginService;
import com.durga.util.LoginUtil;
@Service
public class LoginServiceImpl implements LoginService {
	@Autowired
	private LoginRepository repo;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<LoginEntity> entity=repo.findById(username);
		LoginEntity loginEntity=entity.get();
		return new User(loginEntity.getEmail(), loginEntity.getPassword(),new ArrayList<>());
	}

	@Override
	public LoginDto loadByUserEmail(String email) {
		if(repo.findById(email).isEmpty()) {
			throw new RuntimeException("User Details not Existed: "+email);
		}
		return LoginUtil.convertEntityToDto(repo.findById(email).get());
	}

}
