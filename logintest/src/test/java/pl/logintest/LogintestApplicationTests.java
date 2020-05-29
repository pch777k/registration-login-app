package pl.logintest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import pl.logintest.config.service.AppUserService;
import pl.logintest.model.AppUser;

@SpringBootTest
class LogintestApplicationTests {

	@Autowired
	private AppUserService appUserService;
	
	@Test
	void contextLoads() {
	}

	@Test
	public void simpleRegistration() {
		AppUser appUser = new AppUser("Leo","Messi","USER");
		appUserService.addUser(appUser);
		assertNotNull(appUserService.findAppUserByUsername("Leo"));
	}
	

}
