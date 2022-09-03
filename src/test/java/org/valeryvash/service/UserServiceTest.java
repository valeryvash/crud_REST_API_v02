package org.valeryvash.service;

import org.junit.jupiter.api.Test;
import org.valeryvash.dto.UserPostResponseDto;
import org.valeryvash.repository.UserRepository;

import java.util.stream.LongStream;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;

class UserServiceTest {

    private UserService userService = null;

    public UserServiceTest() {
        this.userService = new UserService(mock(UserRepository.class));
    }

    @Test
    void passNullToMethods() {
        assertThrows(
                IllegalArgumentException.class,
                () -> userService.add(null)
        );

        assertThrows(
                IllegalArgumentException.class,
                () -> userService.get(null)
        );

        assertThrows(
                IllegalArgumentException.class,
                () -> userService.update(null)
        );

        assertThrows(
                IllegalArgumentException.class,
                () -> userService.remove(null)
        );
    }

    @Test
    void passNotPositiveId() {
        UserPostResponseDto responseDto = new UserPostResponseDto();

        LongStream.range(-10000L,1).forEach(
                id -> {
                    assertThrows(
                            IllegalArgumentException.class,
                            () -> userService.get(id)
                    );

                    assertThrows(
                            IllegalArgumentException.class,
                            () -> userService.remove(id)
                    );

                    responseDto.setId(id);
                    assertThrows(
                            IllegalArgumentException.class,
                            () -> userService.update(responseDto)
                    );
                }
        );
    }

}