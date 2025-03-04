package ru.job4j.cinema.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import ru.job4j.cinema.dto.FileDto;
import ru.job4j.cinema.service.file.FileService;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class FileControllerTest {
    private FileController fileController;
    private FileService fileService;

    @BeforeEach
    public void initServices() {
        fileService = mock(FileService.class);
        fileController = new FileController(fileService);
    }

    @Test
    public void whenGetByIdThenStatus200() {
        Optional<FileDto> file = Optional.of(new FileDto("name", new byte[]{1, 1, 1}));
        when(fileService.findById(anyInt())).thenReturn(file);
        ResponseEntity<?> response = fileController.getById(1);
        assertThat(response.getStatusCode().value()).isEqualTo(200);
    }

    @Test
    public void whenFileExistThenStatus404() {
        when(fileService.findById(anyInt())).thenReturn(Optional.empty());
        ResponseEntity<?> response = fileController.getById(1);
        assertThat(response.getStatusCode().value()).isEqualTo(404);
    }
}