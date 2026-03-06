package com.hm.UserMicroservice.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
 
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.hm.UserMicroservice.DTO.UserDTO;
import com.hm.UserMicroservice.DTO.UserPatchDTO;
import com.hm.UserMicroservice.Exception.DuplicateResourceException;
import com.hm.UserMicroservice.Exception.UserNotFoundException;
import com.hm.UserMicroservice.entity.User;
import com.hm.UserMicroservice.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
class UserServiceTest{


    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private UserServiceImplementation userService;
    private User user;
    private UserDTO userDTO;
    private UserPatchDTO userPatchDTO;
@BeforeEach 
void setup(){
        user=new User();
            user.setId(1L);
            user.setName("arpitha");
            user.setEmail("arpitha@gmail.com");
            user.setAge(24); 
            user.setPassword("encodedPassword");
        userDTO =new UserDTO("Arpitha", "arpitha@gmail.com", 24, "arp@123");
            userPatchDTO=new UserPatchDTO();

        }
 
    @Test
    void testUserService() {

        //Arrange -prepare the data
       
         
        //Act
         when(userRepository.existsByEmail(userDTO.getEmail())).thenReturn(false); 
    
        
       when(userRepository.save(any(User.class))).thenReturn(user);//Use any() when you care about the action, not the exact object.
       User result=userService.createUserFromDTO(userDTO);
       assertEquals("arpitha", result.getName());
       assertEquals("arpitha@gmail.com", result.getEmail());
       assertEquals(24, result.getAge());
       verify(userRepository).existsByEmail(userDTO.getEmail());
       verify(userRepository).save(any(User.class));
    }

    @Test
    void shouldThrowExceptionWhenEmailAlreadyExists(){
        
        when(userRepository.existsByEmail(userDTO.getEmail())).thenReturn(true);
        DuplicateResourceException exception =assertThrows(DuplicateResourceException.class, ()->userService.createUserFromDTO(userDTO));
        
        assertEquals("Email Already exists", exception.getMessage());

        verify(userRepository).existsByEmail(userDTO.getEmail());
        verify(userRepository,never()).save(any());
    
    
    }
    // 1. getAllUsers - success
   
   
    @Test
    void testGetAllUsers(){
       
            List<User> users=List.of(user);
            when(userRepository.findAll()).thenReturn(users);
            List<User> result=userService.getAllUsers();
            assertEquals(1, result.size());
            verify(userRepository).findAll();
    }

  // 2. getAllUsers - empty list
  @Test
  void testGetAllUsersEmpty(){
    when(userRepository.findAll()).thenReturn(Collections.EMPTY_LIST);
    List<User>users=userService.getAllUsers();
    assertTrue(users.isEmpty());
  }
// 3. getUserById - success
@Test
void testGetUserById(){
 when(userRepository.findById(1L)).thenReturn(Optional.of(user));
 User result=userService.getUserById(1L);
 assertEquals("arpitha", result.getName());
 assertEquals("arpitha@gmail.com", result.getEmail());
 assertEquals(24, result.getAge());
 verify(userRepository).findById(1L);//Ensure service called repository findById(1L)
    
}

//4.getUserById - throws exception
@Test
void testGetUserByIdException(){
    when(userRepository.findById(2L)).thenReturn(Optional.empty());
    assertThrows(UserNotFoundException.class, ()->userService.getUserById(2L));
    verify(userRepository).findById(2L);

}


//update age only 
@Test
void testUpdateuserAgeOnly(){
userPatchDTO.setAge(30);
when(userRepository.findById(1L)).thenReturn(Optional.of(user));
when(userRepository.save(any(User.class))).thenReturn(user);

User result=userService.updateUser(userPatchDTO, 1L);
assertEquals(30, result.getAge());
verify(userRepository).findById(1L);
verify(userRepository).save(any(User.class));

}

//Invalid age
@Test
void updateUser_invalidAgeThrowsException(){
    userPatchDTO.setAge(10);
    when(userRepository.findById(1L)).thenReturn(Optional.of(user));
    assertThrows(IllegalArgumentException.class, ()->userService.updateUser(userPatchDTO, 1L));
    verify(userRepository).findById(1L);
    verify(userRepository,never()).save(any(User.class));
}   
//Invalid Email format

@Test
void updateUser_shouldThrow_whenEmailInvalid(){
    userPatchDTO.setEmail("wrongEmail");
    when(userRepository.findById(1L)).thenReturn(Optional.of(user));
    assertThrows(IllegalArgumentException.class, ()->userService.updateUser(userPatchDTO,1L));
    verify(userRepository).findById(1L);
 verify(userRepository,never()).save(any(User.class));

}

//Dupliacre Email
@Test
void updateUser_shouldThrow_whenEmailDuplicate(){
    userPatchDTO.setEmail("arpitha@gmail.com");
    when(userRepository.findById(1L)).thenReturn(Optional.of(user));
    when(userRepository.existsByEmail("arpitha@gmail.com")).thenReturn(true);
    assertThrows(DuplicateResourceException.class, ()->userService.updateUser(userPatchDTO, 1L));
     verify(userRepository).findById(1L);
      verify(userRepository).existsByEmail("arpitha@gmail.com");
       verify(userRepository,never()).save(any(User.class));

}

//Blank name
@Test
void updateUser_shouldThrow_whenNameBlank(){
    userPatchDTO.setName(" ");
    when(userRepository.findById(1L)).thenReturn(Optional.of(user));
    assertThrows(IllegalArgumentException.class, ()->userService.updateUser(userPatchDTO, 1L));
    verify(userRepository).findById(1L); 
    verify(userRepository,never()).save(any(User.class));
}
@Test
void updateUser_Email_Only(){
userPatchDTO.setEmail("new@gmail.com");
when(userRepository.findById(1L)).thenReturn(Optional.of(user));
when(userRepository.existsByEmail("new@gmail.com")).thenReturn(false);
when(userRepository.save(any(User.class))).thenReturn(user);
User result=userService.updateUser(userPatchDTO, 1L);
assertEquals("new@gmail.com",result.getEmail());

 verify(userRepository).findById(1L);
 verify(userRepository).existsByEmail("new@gmail.com");
verify(userRepository).save(any(User.class));

}
@Test
void updateUser_Name_Only(){
userPatchDTO.setName("Aru");
when(userRepository.findById(1L)).thenReturn(Optional.of(user));
 
when(userRepository.save(any(User.class))).thenReturn(user);
User result=userService.updateUser(userPatchDTO, 1L);
assertEquals("Aru",result.getName());
 verify(userRepository).findById(1L);

verify(userRepository).save(any(User.class));

}


}
