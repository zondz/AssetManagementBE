package com.nt.rookies.asset.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.nt.rookies.asset.entities.UserEntity;
import com.nt.rookies.asset.repository.UserRepository;

//@ExtendWith(MockitoExtension.class)

class UserServiceTest {

  @InjectMocks
  UserService userService;
  @Mock
  UserRepository userRepository;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.initMocks(this);

  }

  @Test
  void testUpdatePasswordFirstTime() {

    // input : mock data , output : response

    // given 
    UserEntity mockData = new UserEntity();
    mockData.setPassword("old password");

    UserEntity response = new UserEntity();
    response.setPassword("new password");

    // when 

    when(userRepository.save(any())).thenReturn(response);
    // then 
    assertEquals("new password", userService.updatePasswordFirstTime(mockData).getPassword());
  }

}
