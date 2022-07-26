package com.nt.rookies.asset.jwt;

import java.io.Serializable;

public class JwtResponse implements Serializable {
	private static final long serialVersionUID = 1L;

	private String username;

	private String jwtToken;

	public JwtResponse(String username, String jwttoken) {
		this.username = username;
		setJwtToken(jwttoken);
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getJwtToken() {
		return jwtToken;
	}

	public void setJwtToken(String jwttoken) {
		this.jwtToken = jwttoken;
	}

}
