package pl.example.components.security.jwt.model;

import java.io.Serializable;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;

public class AuthenticationResponse implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private final String jwt;
	private final Collection<? extends GrantedAuthority> authorities;
	
	public AuthenticationResponse(String jwt, Collection<? extends GrantedAuthority> authorities) {
		this.jwt = jwt;
		this.authorities = authorities;
	}

	public String getJwt() {
		return jwt;
	}
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}
}