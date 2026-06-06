package com.trugdz.frase_de_pinguim.service;

import com.trugdz.frase_de_pinguim.dto.CreateFraseDTO;
import com.trugdz.frase_de_pinguim.dto.FraseResponseDTO;
import com.trugdz.frase_de_pinguim.model.Frase;
import com.trugdz.frase_de_pinguim.model.User;
import com.trugdz.frase_de_pinguim.repository.FraseRepository;
import com.trugdz.frase_de_pinguim.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static com.trugdz.frase_de_pinguim.service.FraseService.USUARIO_DELETADO_ID;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
class FraseServiceTest {
    @Mock
    FraseRepository fraseRepository;
    @Mock
    UserRepository userRepository;

    @InjectMocks
    FraseService fraseService;

    @Test
    void getAll_whenListExists() {
        Frase frase1 = new Frase(1L, LocalDate.now(), 0, "frase 1", new User());
        Frase frase2 = new Frase(2L, LocalDate.now(), 0, "frase 2", new User());
        List<Frase> lista = List.of(frase1, frase2);
        when(fraseRepository.findAll()).thenReturn(lista);

        List<FraseResponseDTO> res = fraseService.getAll();

        assertEquals(1L, res.get(0).id());
        assertEquals("frase 1", res.get(0).frase());

        assertEquals(2L, res.get(1).id());
        assertEquals("frase 2", res.get(1).frase());

        verify(fraseRepository).findAll();
    }

    @Test
    void getAll_whenNoDataExists() {
        List<Frase> lista = List.of();
        when(fraseRepository.findAll()).thenReturn(lista);

        List<FraseResponseDTO> res = fraseService.getAll();

        assertTrue(res.isEmpty());
        verify(fraseRepository).findAll();
    }

    @Test
    void getById() {
        Frase frase1 = new Frase(1L, LocalDate.now(), 0, "frase 1", new User());
        when(fraseRepository.findById(1L)).thenReturn(Optional.of(frase1));

        FraseResponseDTO res = fraseService.getById(1L);

        assertEquals(1L, res.id());
        assertEquals(0, res.deslike());
        assertEquals("frase 1", res.frase());
        assertEquals(LocalDate.now(), res.dataCriacao());

        verify(fraseRepository).findById(1L);
    }

    @Test
    void getByIdNoExists(){
        when(fraseRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException thrown = assertThrows(RuntimeException.class,
                () -> fraseService.getById(1L));

        assertEquals("Frase not found", thrown.getMessage());
    }

    @Test
    void getFrasesByUserNoData() {

        when(userRepository.findById(1L)).thenReturn(Optional.empty());
        RuntimeException t = assertThrows(RuntimeException.class,
                () -> fraseService.getFrasesByUser(1L));

        assertEquals("User not found", t.getMessage());
    }

    @Test
    void getFrasesByUser(){
        User user = new User(
                1L,LocalDate.now(),
                "felipe",true,List.of());
        Frase frase1 = new Frase(1L, LocalDate.now(),
                0, "frase1", user);
        Frase frase2 = new Frase(2L, LocalDate.now(),
                0, "frase2", user);
        user.setFrases(List.of(frase1, frase2));
        when(userRepository.findById(1L))
                .thenReturn(Optional.of(user));
        List<FraseResponseDTO> lista = fraseService.getFrasesByUser(1L);

        assertEquals(2, lista.size());
        assertEquals("frase1", lista.get(0).frase());
        assertEquals("frase2", lista.get(1).frase());
        assertEquals(1L, lista.get(0).id());
        assertEquals(2L, lista.get(1).id());

        assertEquals(1L, lista.get(0).userId());
        assertEquals(1L, lista.get(1).userId());

        verify(userRepository).findById(1L);

    }

    @Test
    void create() {
    }

    @Test
    void createFraseUserDisable(){
        User user = new User();
        user.setId(1L);
        user.setAtivo(false);

        CreateFraseDTO request = new CreateFraseDTO("fraseDisableUser", 1L);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        RuntimeException t = assertThrows(RuntimeException.class,
                () -> fraseService.create(request, 1L));

        assertEquals("User disable, contact admin", t.getMessage());

        verify(userRepository).findById(1L);
        verify(fraseRepository, never()).save(any());

    }

    @Test
    void createFraseWithBadWordShouldAssignPermabanUser(){
        User user = new User();
        user.setAtivo(true);
        user.setId(1L);
        user.setNickname("felipe");

        User permaBan = new User();
        permaBan.setId(13L);


        CreateFraseDTO request = new CreateFraseDTO("qwert123456", 1L);


        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userRepository.findById(13L)).thenReturn(Optional.of(permaBan));


        when(fraseRepository.save(any(Frase.class))).thenAnswer(
                invocation -> invocation.getArgument(0));

        FraseResponseDTO res = fraseService.create(request, 1L);

        ArgumentCaptor<Frase> captor = ArgumentCaptor.forClass(Frase.class);

        verify(fraseRepository).save(captor.capture());

        Frase fraseSalva = captor.getValue();

        assertEquals(13L, fraseSalva.getUser().getId());

        assertEquals("Usuario Original: felipe | Frase: qwert123456", fraseSalva.getFrase());
    }

    @Test
    void deslike() {
        Frase frase = new Frase();
        frase.setId(1L);
        frase.setDeslike(0);

        when(fraseRepository.findById(1L)).thenReturn(Optional.of(frase));

        var res = fraseService.deslike(1L);

        assertEquals(1, res.deslike());

        verify(fraseRepository).save(frase);
        verify(fraseRepository).findById(1L);

    }

    @Test
    void delete() {
        User user = new User();

        user.setAtivo(true);
        user.setId(1L);
        user.setNickname("felipe");

        User userDeletado = new User();
        userDeletado.setAtivo(true);
        userDeletado.setId(12L);
        userDeletado.setNickname("deletado");


        Frase frase = new Frase();
        frase.setId(1L);
        frase.setFrase("abc");
        frase.setUser(user);


        when(fraseRepository.findById(1L)).thenReturn(Optional.of(frase));
        when(userRepository.findById(USUARIO_DELETADO_ID)).thenReturn(Optional.of(userDeletado));

        fraseService.delete(1L);

        assertEquals(USUARIO_DELETADO_ID, frase.getUser().getId());
        verify(fraseRepository).save(frase);


    }
}