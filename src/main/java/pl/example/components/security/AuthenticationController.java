package pl.example.components.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import pl.example.components.security.jwt.model.AuthenticationRequest;
import pl.example.components.security.jwt.model.AuthenticationResponse;
import pl.example.components.security.jwt.util.JwtUtil;

@RestController
public class AuthenticationController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtUtil jwtTokenUtil;

	@Autowired
	private CustomUserDetailsService userDetailsService;
	
	@PostMapping("/account/login")
	public ResponseEntity<?> createAuthenticationToken(
			@RequestBody AuthenticationRequest authenticationRequest) throws Exception {
		
		try {
			authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(
							authenticationRequest.getEmailUser(), authenticationRequest.getPassword())
			);
		}
		catch (BadCredentialsException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, 
					"Incorrect email or password");
		}

		final UserDetails userDetails = userDetailsService
				.loadUserByUsername(authenticationRequest.getEmailUser());
		
		final String jwtToken = jwtTokenUtil.generateToken(userDetails);
		
		return ResponseEntity.ok(new AuthenticationResponse(jwtToken, userDetails.getAuthorities()));
	}
}
