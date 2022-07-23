package com.nt.rookies.asset.services;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.nt.rookies.asset.entities.UserEntity;
import com.nt.rookies.asset.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class JwtUserDetailsService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserEntity user = userRepository.findByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
//		String ROLE_PREFIX = "ROLE_";
		List<String> roleList = new ArrayList<>();
		roleList.add(user.getType());
		List<GrantedAuthority> authorities = roleList.stream()
				.map(role -> new SimpleGrantedAuthority(role.toUpperCase()))
				.collect(Collectors.toList());
		return new User(username, user.getPassword(), authorities);
	}

}
