package org.valeryvash.service;

import org.valeryvash.dto.UserPostRequestDto;
import org.valeryvash.dto.UserPostResponseDto;
import org.valeryvash.model.Event;
import org.valeryvash.model.User;
import org.valeryvash.repository.UserRepository;
import org.valeryvash.repository.impl.HibernateUserRepositoryImpl;

import java.util.ArrayList;
import java.util.List;

import static org.valeryvash.util.ServiceChecker.throwIfNotPositive;
import static org.valeryvash.util.ServiceChecker.throwIfNull;

public class UserService {
    private final UserRepository userRepository;

    public UserService() {
        userRepository = new HibernateUserRepositoryImpl();
    }

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserPostResponseDto add(UserPostRequestDto userRequestDto) {
        throwIfNull(userRequestDto);

        User user = userRequestDto.getUser();

        user = userRepository.add(user);

        return new UserPostResponseDto(user);
    }

    public UserPostResponseDto get(Long id) {
        throwIfNull(id);
        throwIfNotPositive(id);

        User user = userRepository.get(id);

        return new UserPostResponseDto(user);
    }


    public List<UserPostResponseDto> getAll() {
        List<UserPostResponseDto> userPostResponseDtoList = new ArrayList<>();

        List<User> userList = userRepository.getAll();

        for (User user : userList) {
            userPostResponseDtoList.add(new UserPostResponseDto(user));
        }

        return userPostResponseDtoList;
    }


    public UserPostResponseDto remove(Long id) {
        throwIfNull(id);
        throwIfNotPositive(id);

        User user = userRepository.remove(id);

        return new UserPostResponseDto(user);
    }

    /**
     * Каждый пользователь (из контекстов сохранения и обновления) имеет по одной коллекции эвентов
     * Мы имеем следующие варианты событий:
     *  - обе пусты - ничего не меняем,
     *  - сохранения пуста, обновления имеет элементы. Добавляем элементы из 2й в 1ю.
     *  - обновления пуста, сохранения имеет элементы. Удаляем элементы из 2й.
     *  - обе имею элементы. Описание ниже
     *    Сохранения может иметь индексы только больше нуля, поскольку все элементы уже в контексте сохраняемости.
     *    Обновления может иметь индексы как больше нуля так и нулевые. Некоторые объекты могут быть новыми для контекста сохраняемости.
     *    Но не может быть ситуации когда индекс события и файла различны по состоянию т.е. файл в контексте сохраняемости а событие нет и наоборот
     *    Если индекс файла нулевой, то и индекс события будет нулевой. Так же верно обратное.
     *    Из выше описанного получается следующий сценарий:
     *     - коллекция сохранения фильтруется по индексам коллекции обновления
     *     - в коллекцию сохранения добавляются элементы с нулевыми индексами.
     *
     */
    public UserPostResponseDto update(UserPostResponseDto requestDto) {
        throwIfNull(requestDto);
        throwIfNotPositive(requestDto.getId());

        User updatedUserData = requestDto.getUser();

        User persistedUserData = userRepository.get(requestDto.getId());

        persistedUserData.setName(requestDto.getName());

        List<Event> persistedEvents = persistedUserData.getEvents();
        List<Event> updatedEvents = updatedUserData.getEvents();

        if (persistedEvents.isEmpty() & !updatedEvents.isEmpty()) {
            persistedEvents.addAll(updatedEvents);
        } else if (!persistedEvents.isEmpty() & updatedEvents.isEmpty()) {
            persistedEvents.clear();
        } else if (!persistedEvents.isEmpty() & !updatedEvents.isEmpty()) {
            persistedEvents.retainAll(updatedEvents);
            updatedEvents.forEach( event -> {
                if (event.getId() == 0L)
                    persistedEvents.add(event);
            });
        }

        persistedUserData = userRepository.update(persistedUserData);

        return new UserPostResponseDto(persistedUserData);
    }
}
