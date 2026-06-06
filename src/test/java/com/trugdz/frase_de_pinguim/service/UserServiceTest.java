package com.trugdz.frase_de_pinguim.service;

import com.trugdz.frase_de_pinguim.dto.CreateUserDTO;
import com.trugdz.frase_de_pinguim.dto.UserDetailsResponseDTO;
import com.trugdz.frase_de_pinguim.dto.UserResponseDTO;
import com.trugdz.frase_de_pinguim.dto.UserStatusDTO;
import com.trugdz.frase_de_pinguim.model.User;
import com.trugdz.frase_de_pinguim.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
class UserServiceTest {

    @Mock
    UserRepository userRepository;
    @InjectMocks
    UserService userService;



    @Test
    void getAll_whenDataExists() {
        // Arrange: simula uma lista com dois usuários
        User user1 = new User();
        user1.setId(1L);
        user1.setAtivo(true);
        user1.setNickname("Ana");

        User user2 = new User();
        user2.setId(2L);
        user2.setAtivo(true);
        user2.setNickname("Paula");

        List<User> lista = List.of(user1, user2);

        when(this.userRepository.findAll()).thenReturn(lista);

        // Act: chama o serviço
        List<UserResponseDTO> response = userService.getAll();

        // Assert: verifica se a lista tem dois elementos e que os dados batem
        assertNotNull(response);
        assertEquals(2, response.size());
        assertEquals("Ana", response.get(0).nickname());
        assertEquals("Paula", response.get(1).nickname());

        verify(userRepository).findAll();
    }

    @Test
    void getAll_whenNoData() {
        List<User> lista = List.of();
        when(this.userRepository.findAll()).thenReturn(lista);

        List<UserResponseDTO> response = userService.getAll();
        assertTrue(response.isEmpty());
        verify(userRepository).findAll();
    }

    @Test
    void getByIdDataExists() {
        User user1 = new User();
        user1.setId(1L);
        user1.setAtivo(true);
        user1.setNickname("Ana");
        user1.setFrases(List.of());

        when(this.userRepository.findById(1L)).thenReturn(Optional.of(user1));

        UserDetailsResponseDTO response = userService.getById(1L);

        assertNotNull(response);
        assertEquals("Ana", response.nickname());

        verify(userRepository).findById(1L);
    }
    @Test
    void getByIdNoData() {
        when(this.userRepository.findById(1L)).thenReturn(Optional.empty());
        RuntimeException thrown = Assertions.assertThrows(RuntimeException.class,
                () -> userService.getById(1L)
        );
        Assertions.assertEquals("User not found", thrown.getMessage());
        verify(userRepository).findById(1L);
    }

    @Test
    void createUser() {
        CreateUserDTO request = new CreateUserDTO("felipe");
        User savedUser = new User();
        savedUser.setId(1L);
        savedUser.setNickname("felipe");

        when(userRepository.save(any(User.class))).thenReturn(savedUser);

        UserResponseDTO response = userService.create(request);

        assertEquals(1L, response.id());
        assertEquals("felipe", response.nickname());

        verify(userRepository).save(any(User.class));
    }



    @Test
    void deleteUserExists() {
        when(userRepository.existsById(1L)).thenReturn(true);
        userService.delete(1L);
        verify(userRepository).deleteById(1L);

    }
    @Test
    void deleteUserNotFount(){
        when(userRepository.existsById(1L)).thenReturn(false);
        RuntimeException thrown = Assertions.assertThrows(RuntimeException.class, () ->
                userService.delete(1L));
        Assertions.assertEquals("User not found", thrown.getMessage());
        //verify(userRepository).deleteById(1L);
    }

    @Test
    void activeUserNotFound() {
        deleteUserNotFount();
    }
    @Test
    void activeUser() {
        when(userRepository.existsById(1L)).thenReturn(true);
        User user = new User();
        user.setId(1L);
        user.setNickname("felipe");
        user.setAtivo(false);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        UserStatusDTO response = userService.activeUser(1L);

        assertEquals(1L, response.id());
        assertEquals("felipe", response.nickname());
        assertTrue(response.ativo());

        verify(userRepository).save(user);

    }

    @Test
    void disableUser() {
        when(userRepository.existsById(1L)).thenReturn(true);
        User user = new User();
        user.setId(1L);
        user.setAtivo(true);
        user.setNickname("felipe");

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        UserStatusDTO res = userService.disableUser(1L);

        assertEquals("felipe", res.nickname());

        assertEquals(1L, res.id());
        assertFalse(res.ativo());

        verify(userRepository).save(user);


    }
}