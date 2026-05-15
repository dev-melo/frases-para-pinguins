package com.trugdz.frase_de_pinguim.service;

import com.trugdz.frase_de_pinguim.dto.UserResponseDTO;
import com.trugdz.frase_de_pinguim.model.User;
import com.trugdz.frase_de_pinguim.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
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
    }
    @Test
    void getByIdNoData() {
    }

    @Test
    void create() {
    }

    @Test
    void delete() {
    }

    @Test
    void activeUser() {
    }

    @Test
    void disableUser() {
    }
}