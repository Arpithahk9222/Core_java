package com.hm.UserMicroservice.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.springframework.http.MediaType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hm.UserMicroservice.Controllers.UserController;
import com.hm.UserMicroservice.DTO.UserDTO;
import com.hm.UserMicroservice.entity.User;
import com.hm.UserMicroservice.service.UserService;
 
import static org.mockito.ArgumentMatchers.eq;
 

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.util.List;

 
 

 
 
import com.hm.UserMicroservice.DTO.UserPatchDTO;
 


@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    private User user;

    private UserDTO userDTO;
private List<User> users;
    @BeforeEach
    void setup(){
        user=new User(1L, "Arpitha", "arpitha@gmail.com", 24);
        userDTO=new UserDTO("Arpitha", "arpitha@gmail.com", 24);
 users = List.of(
            new User(1L, "Arpitha", "arpitha@gmail.com", 24),
            new User(2L, "John", "john@gmail.com", 30)
    );
    }
    /**
     * 
     * Test Case 1 — SUCCESS (Happy Path)

✔ Valid JSON
✔ Service returns User
✔ Expect HTTP 201
✔ Verify response body
     */
    @Test
    void createUser_ShouldReturnCreated()throws Exception{
        when(userService.createUserFromDTO(any(UserDTO.class))).thenReturn(user);
         mockMvc.perform(post("/api/v1/users")//Simulates HTTP POST requestEquivalent to:POST http://localhost/api/v1/users
        .contentType( MediaType.APPLICATION_JSON)//Request body is JSON
        .content(objectMapper.writeValueAsString(userDTO)))//Convert DTO → JSON string.
        .andExpect(status().isCreated())//Verify HTTP status = 201
        .andExpect(jsonPath("$.name").value("Arpitha"))
        .andExpect(jsonPath("$.email").value("arpitha@gmail.com"))
        .andExpect(jsonPath("$.age").value(24));//"From JSON response, read the field name and verify it equals Arpitha"
    }
/*Test Case 2 — VALIDATION FAILURE (Very Important)

Because your method uses:

@Valid @RequestBody UserDTO


Spring automatically rejects bad input.

✔ Invalid DTO
✔ Service NOT called
✔ Expect HTTP 400 */
    @Test
    void createUser_ShouldReturnCreated_WhenvalidInput() throws Exception{
         //Invalid Values
         userDTO =new UserDTO("Arpitha", "arpitha@gmail.com", 24, "arp@123");

         mockMvc.perform(post("/api/v1/users")
        .contentType(MediaType.APPLICATION_JSON)
    .content(objectMapper.writeValueAsString(userDTO)))
    .andExpect(status().isBadRequest());
    }

    @Test
    void getAllUser_Shouldreturn_ListOfUsers()throws Exception{
        when(userService.getAllUsers()).thenReturn(users);

        mockMvc.perform(get("/api/v1/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(jsonPath("$[0].name").value("Arpitha"))
                .andExpect(jsonPath("$[1].name").value("John"));

    }

    @Test
    void getUserById_ShouldReturn() throws Exception{
        when(userService.getUserById(1L)).thenReturn(user);
        mockMvc.perform(get("/api/v1/users/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Arpitha"))
                .andExpect(jsonPath("$.email").value("arpitha@gmail.com"));
    }
    
}
