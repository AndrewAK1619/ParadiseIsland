package pl.example.components.security;

import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class AuthenticationController {

	@PostMapping("/account/login")
	@ResponseBody
	public Principal login(Principal user) {
    	return user;
	}
}
