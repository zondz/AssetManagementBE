package com.nt.rookies.asset.jwt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.nt.rookies.asset.entities.InvalidToken;
import com.nt.rookies.asset.entities.UserEntity;
import com.nt.rookies.asset.payload.JwtRequest;
import com.nt.rookies.asset.payload.JwtResponse;
import com.nt.rookies.asset.repository.InvalidTokenRepository;
import com.nt.rookies.asset.services.JwtUserDetailsService;
import com.nt.rookies.asset.services.UserService;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class JwtAuthenticationController {
  @Autowired
  private AuthenticationManager authenticationManager;
  @Autowired
  private JwtTokenUtil jwtTokenUtil;
  @Autowired
  private JwtUserDetailsService userDetailsService;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Autowired
  private UserService userService;

  @Autowired
  private InvalidTokenRepository invalidTokenRepository;

  @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
  public ResponseEntity<JwtResponse> login(@RequestBody JwtRequest account) throws Exception {
    System.out.println("account 1" + account);
    authenticate(account.getUsername(), account.getPassword());
    final UserDetails userDetails = userDetailsService.loadUserByUsername(account.getUsername());
    System.out.println("return user with role : " + userDetails);
    final String token = jwtTokenUtil.generateToken(userDetails);

    return new ResponseEntity<JwtResponse>(new JwtResponse(token), HttpStatus.OK);
  }

  @PostMapping("/UpdatePasswordFirstTime")
  public ResponseEntity<JwtResponse> updatePasswordFirstTime(@RequestBody JwtRequest account,
      @RequestHeader(name = "UpdatePasswordFirstTimeToken") String clientToken) {

    System.out.println("client send token " + clientToken);
    UserEntity entity = userService.findByUsername(account.getUsername());
    //    entity.setPass

    String newPassword = passwordEncoder.encode(account.getPassword());
    System.out.println("new encoded password : " + newPassword);

    entity.setPassword(newPassword);
    entity.setFirstLogin(false);
    UserEntity updatedEntity = userService.updatePasswordFirstTime(entity);

    final UserDetails userDetails = userDetailsService.loadUserByUsername(updatedEntity.getUsername());
    System.out.println("return user with role : " + userDetails);

    final String token = jwtTokenUtil.generateToken(userDetails);
    System.out.println("return token : " + token);

    invalidTokenRepository.save(new InvalidToken(clientToken));

    return new ResponseEntity<JwtResponse>(new JwtResponse(token), HttpStatus.OK);
  }

  private void authenticate(String username, String password) throws Exception {
    try {
      authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
      UserEntity user = userService.findByUsername(username);
      if (user.getStatus() == "DISABLED") {
	throw new DisabledException("USER_DISABLED");
      }
    } catch (DisabledException e) {
      throw new Exception("USER_DISABLED", e);
    } catch (BadCredentialsException e) {
      throw new Exception("INVALID_CREDENTIALS", e);
    }
  }

}
