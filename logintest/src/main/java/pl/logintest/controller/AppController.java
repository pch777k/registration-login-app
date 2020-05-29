package pl.logintest.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.servlet.ModelAndView;

import pl.logintest.config.service.AppUserService;
import pl.logintest.model.AppUser;



@Controller
@RequestMapping("/")
public class AppController {
	
	
	private AppUserService appUserService;
	
	private PasswordEncoder passwordEncoder;
	
	public AppController(AppUserService appUserService, PasswordEncoder passwordEncoder) {
		this.appUserService = appUserService;
		this.passwordEncoder = passwordEncoder;
	}
	
	@GetMapping("home")
	public String homePage() {
		return "home";
	}
//	// Login
//		@RequestMapping(value="/login", method=RequestMethod.GET)
//		public ModelAndView displayLogin(ModelAndView modelAndView, User user) {
//			modelAndView.addObject("user", user);
//			modelAndView.setViewName("login");
//			return modelAndView;
//		}
//
	// Login	
	@GetMapping({"login","index",""})
	public String loginPage() {
		return "login";
	}
	
	@PostMapping("login")
		public ModelAndView loginUser(ModelAndView modelAndView, AppUser appUser) {
			
			AppUser existingAppUser = appUserService.findAppUserByUsername(appUser.getUsername());
			System.out.println(existingAppUser);
			if(existingAppUser != null) {
				// use passwordEncoder.matches to compare raw password with encrypted password

				if (passwordEncoder.matches(appUser.getPassword(), existingAppUser.getPassword())){
					// successfully logged in
					modelAndView.addObject("message", "Hello " + appUser.getUsername() + "!");
					modelAndView.setViewName("home");
				} else {
					// wrong password
					modelAndView.addObject("message", "Incorrect password. Try again.");
					modelAndView.setViewName("login");
				}
			} else {	
				modelAndView.addObject("message", "The user " +  appUser.getUsername() + " does not exist!");
				modelAndView.setViewName("login");

			} 
			
			return modelAndView;
		}
	

	// Registration
		@GetMapping("register")
		public ModelAndView registrationForm(ModelAndView modelAndView, AppUser appUser) {
			modelAndView.addObject("appUser", appUser);
			modelAndView.setViewName("register");
			return modelAndView;
		}
//	@GetMapping("register")
//    public String registerPage(Model model) {
//        model.addAttribute("user", new AppUser());
//        return "register";
//    }
	
//    @PostMapping("register")
//    public String register(@Valid @ModelAttribute("appUser") AppUser appUser, BindingResult bindingResult) {
////    	System.out.println(appUser.getUsername());
////    	AppUser existUser = appUserService.findAppUserByUsername(appUser.getUsername())
////    			.orElseThrow(() -> new UsernameNotFoundException(String.format("Username %s not found!", appUser.getUsername())));
//    	
//    	System.out.println(appUser);
//    	if (bindingResult.hasErrors()) {
//			return "register";
//		}
//    	appUserService.addUser(appUser);
//    	System.out.println(appUser);
//        return "home";
//    }
    
	// Process form input data
	@PostMapping("register")
	public ModelAndView processRegistrationForm(ModelAndView modelAndView, @Valid AppUser appUser, BindingResult bindingResult, HttpServletRequest request) {
				
		// Lookup user in database by e-mail
		AppUser userExists = appUserService.findAppUserByUsername(appUser.getUsername());
		
		System.out.println(userExists);
		
		if (userExists != null) {
			modelAndView.addObject("alreadyRegisteredMessage", "Oops! Someone already has that username.");
			modelAndView.setViewName("register");
			bindingResult.reject("username");
		}
			
		if (bindingResult.hasErrors()) { 
			modelAndView.setViewName("register");		
		} else { 
		        
		    appUserService.addUser(appUser);
				
		    modelAndView.addObject("appUser", appUser);
			modelAndView.addObject("confirmationMessage", "A registration user " + appUser.getUsername() + " is succeed");
			modelAndView.setViewName("registration-success");
		}
			
		return modelAndView;
	}
    
//    @PostMapping("register")
//	public ModelAndView registerAppUser(@RequestBody AppUser appUser) {
//    	ModelAndView modelAndView = new ModelAndView();
//		AppUser existingAppUser = appUserService.findAppUserByUsername(appUser.getUsername());
//		System.out.println(existingAppUser.getUsername());
//		if(existingAppUser != null) {
//			modelAndView.addObject("message","This username already exists!");
//			modelAndView.setViewName("register");
//			
//		}
//		appUserService.addUser(appUser);
//		modelAndView.addObject("appUser", appUser);
//
//
//		modelAndView.setViewName("home");
//		
//		
//		
//		return modelAndView;
//    }
}
