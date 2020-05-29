package pl.logintest.config.service;


import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import pl.logintest.model.AppUser;
import pl.logintest.repository.AppUserRepository;


@Service
public class AppUserService {
	
	private AppUserRepository appUserRepository;
	private PasswordEncoder passwordEncoder;

	public AppUserService(AppUserRepository appUserRepository, PasswordEncoder passwordEncoder) {
		this.appUserRepository = appUserRepository;
		this.passwordEncoder = passwordEncoder;
	}

	public void addUser(AppUser appUser) {
		   appUser.setRole("USER");
		   appUser.setPassword(passwordEncoder.encode(appUser.getPassword()));
		   appUserRepository.save(appUser);
	   }
	
	public AppUser findAppUserByUsername(String username) {
		return appUserRepository.findByUsername(username);
	}
}
