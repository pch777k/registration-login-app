package pl.logintest.config;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import pl.logintest.repository.AppUserRepository;

@Service
public class AppUserDetailsService implements UserDetailsService {

	private AppUserRepository appUserRepository;
	
	public AppUserDetailsService(AppUserRepository appUserRepository) {
		this.appUserRepository = appUserRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return new AppUserPrincipal(appUserRepository.findByUsername(username));
	}		  

}
