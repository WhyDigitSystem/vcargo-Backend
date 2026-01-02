package com.efit.savaari.security;

import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.efit.savaari.entity.UserVO;
import com.efit.savaari.repo.UserRepo;


@Service
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

	@Autowired
	UserRepo userRepository;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		UserVO user = userRepository.findByUserName(email);
		if (ObjectUtils.isEmpty(user)) {
			throw new UsernameNotFoundException("User not found with email : " + email);
		}
		return UserPrincipal.create(user);
	}

	public UserDetails loadUserById(Long id) {
		UserVO user = userRepository.findById(id)
				.orElseThrow(() -> new UsernameNotFoundException("User not found with ID : " + id));
		return UserPrincipal.create(user);
	}

}