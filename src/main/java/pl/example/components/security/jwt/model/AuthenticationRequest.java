package pl.example.components.security.jwt.model;

import java.io.Serializable;

public class AuthenticationRequest implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String emailUser;
    private String password;
    
    public AuthenticationRequest(){}

    public AuthenticationRequest(String emailUser, String password) {
        this.setEmailUser(emailUser);
        this.setPassword(password);
    }

    public String getEmailUser() {
		return emailUser;
	}

	public void setEmailUser(String emailUser) {
		this.emailUser = emailUser;
	}

	public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}