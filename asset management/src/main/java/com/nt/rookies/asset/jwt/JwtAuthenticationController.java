package com.nt.rookies.asset.jwt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.nt.rookies.asset.payload.JwtRequest;
import com.nt.rookies.asset.payload.JwtResponse;
import com.nt.rookies.asset.services.JwtUserDetailsService;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class JwtAuthenticationController {
  @Autowired
  private AuthenticationManager authenticationManager;
  @Autowired
  private JwtTokenUtil jwtTokenUtil;
  @Autowired
  private JwtUserDetailsService userDetailsService;

  @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
  public ResponseEntity<JwtResponse> login(@RequestBody JwtRequest account) throws Exception {
    System.out.println("account " + account);
    authenticate(account.getUsername(), account.getPassword());
    final UserDetails userDetails = userDetailsService.loadUserByUsername(account.getUsername());
    System.out.println("return user with role : " + userDetails);
    final String token = jwtTokenUtil.generateToken(userDetails);

    return new ResponseEntity<JwtResponse>(new JwtResponse(token), HttpStatus.OK);
  }

  private void authenticate(String username, String password) throws Exception {
    try {
      authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
    } catch (DisabledException e) {
      throw new Exception("USER_DISABLED", e);
    } catch (BadCredentialsException e) {
      throw new Exception("INVALID_CREDENTIALS", e);
    }
  }

}
