package org.valeryvash.service;

import org.junit.jupiter.api.Test;
import org.valeryvash.dto.FilePostResponseDto;
import org.valeryvash.repository.EventRepository;
import org.valeryvash.repository.FileRepository;
import org.valeryvash.repository.UserRepository;

import java.util.stream.LongStream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FileServiceTest {

    private FileService fileService = null;

    public FileServiceTest() {
        this.fileService = new FileService(
                mock(FileRepository.class),
                mock(UserRepository.class),
                mock(EventRepository.class)
        );
    }

    @Test
    void passNullToMethods() {
        assertThrows(
                IllegalArgumentException.class,
                () -> fileService.add(null)
        );

        assertThrows(
                IllegalArgumentException.class,
                () -> fileService.get(null)
        );

        assertThrows(
                IllegalArgumentException.class,
                () -> fileService.update(null)
        );

        assertThrows(
                IllegalArgumentException.class,
                () -> fileService.remove(null)
        );
    }

    @Test
    void passNotPositiveId() {
        FilePostResponseDto responseDto = new FilePostResponseDto();

        LongStream.range(-10000L,1).forEach(
                id -> {
                    assertThrows(
                            IllegalArgumentException.class,
                            () -> fileService.get(id)
                    );

                    assertThrows(
                            IllegalArgumentException.class,
                            () -> fileService.remove(id)
                    );

                    responseDto.setId(id);
                    assertThrows(
                            IllegalArgumentException.class,
                            () -> fileService.update(responseDto)
                    );
                }
        );
    }

}