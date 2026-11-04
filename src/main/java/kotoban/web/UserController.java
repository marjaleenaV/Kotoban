package kotoban.web;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import jakarta.validation.Valid;

import kotoban.domain.User;
import kotoban.domain.UserRepository;
import kotoban.domain.SignUpForm;



@Controller
public class UserController {

    private final UserRepository repository;

    public UserController(UserRepository userRepository) {
        this.repository = userRepository;
    }

    //Go to the login page
    @RequestMapping(value="/login", method = RequestMethod.GET)
    public String login() {	
        return "login";
    }

    //Open a signupform and create new form
    @RequestMapping(value = "signup", method= RequestMethod.GET)
    public String addStudent(Model model){
    	model.addAttribute("signupform", new SignUpForm());
        return "signup";
    }

    /**
     * Create new user
     * Check if user already exists & form validation
     * 
     * @param signupForm
     * @param bindingResult
     * @return
     */
    @RequestMapping(value = "saveuser", method = RequestMethod.POST)
    public String save(@Valid @ModelAttribute("signupform") SignUpForm signupForm, BindingResult bindingResult) {
    	// validation errors
        if (!bindingResult.hasErrors()) { 
            // check password match
    		if (signupForm.getPassword().equals(signupForm.getPasswordCheck())) { 		
	    		String password = signupForm.getPassword();
		    	BCryptPasswordEncoder bc = new BCryptPasswordEncoder();
		    	String hashPassword = bc.encode(password);
                
                //If password ok, create new user
		    	User newUser = new User();
		    	newUser.setPasswordHash(hashPassword);
		    	newUser.setUsername(signupForm.getUsername());
                newUser.setEmail(signupForm.getEmail());
		    	newUser.setRole("USER");
                System.out.println(">>> signup reached, password check passed");

                // Check if username already exists
		    	if (repository.findByUsername(signupForm.getUsername()) == null) { 
		    		System.out.println(">>> save reached");
                    repository.save(newUser);
		    	}
		    	else {
                    System.out.println(">>> username already exists");
	    			bindingResult.rejectValue("username", "err.username", "Username already exists");    	
	    			return "signup";		    		
		    	}
    		}
    		else {
    			bindingResult.rejectValue("passwordCheck", "err.passCheck", "Passwords does not match");    	
    			return "signup";
    		}
    	}
    	else {
    		return "signup";
    	}
    	return "redirect:/login";    	
    } 
}
