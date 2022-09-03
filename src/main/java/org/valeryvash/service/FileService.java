package org.valeryvash.service;

import org.valeryvash.dto.FilePostRequestDto;
import org.valeryvash.dto.FilePostResponseDto;
import org.valeryvash.model.EventType;
import org.valeryvash.model.File;
import org.valeryvash.model.User;
import org.valeryvash.repository.EventRepository;
import org.valeryvash.repository.FileRepository;
import org.valeryvash.repository.UserRepository;
import org.valeryvash.repository.impl.HibernateEventRepositoryImpl;
import org.valeryvash.repository.impl.HibernateFileRepositoryImpl;
import org.valeryvash.repository.impl.HibernateUserRepositoryImpl;

import java.util.ArrayList;
import java.util.List;

import static org.valeryvash.util.ServiceChecker.throwIfNotPositive;
import static org.valeryvash.util.ServiceChecker.throwIfNull;

public class FileService {

    private final FileRepository fileRepository;
    private final UserRepository userRepository;

    private final EventRepository eventRepository;

    public FileService() {
        this.fileRepository = new HibernateFileRepositoryImpl();
        this.userRepository = new HibernateUserRepositoryImpl();
        this.eventRepository = new HibernateEventRepositoryImpl();
    }

    public FileService(FileRepository fileRepository, UserRepository userRepository, EventRepository eventRepository) {
        this.fileRepository = fileRepository;
        this.userRepository = userRepository;
        this.eventRepository = eventRepository;
    }

    public FilePostResponseDto add(FilePostRequestDto filePostRequestDto) {
        throwIfNull(filePostRequestDto);

        FilePostResponseDto responseDto = null;

        File file = filePostRequestDto.getFile();
        file.addEvent();

        User user = userRepository.get(filePostRequestDto.getUserId());
        user.addFile(file);

        file = fileRepository.add(file);

        responseDto = new FilePostResponseDto(file);
        return responseDto;
    }

    public FilePostResponseDto get(Long id) {
        throwIfNull(id);
        throwIfNotPositive(id);

        File file = fileRepository.get(id);

        return new FilePostResponseDto(file);
    }

    public List<FilePostResponseDto> getAll() {
        List<FilePostResponseDto> responseDtos = new ArrayList<>();

        List<File> files = fileRepository.getAll();

        for (File file : files) {
            responseDtos.add(new FilePostResponseDto(file));
        }

        return responseDtos;
    }

    public FilePostResponseDto update(FilePostResponseDto responseDto) {
        throwIfNull(responseDto);
        throwIfNotPositive(responseDto.getId());

        File persistedFile = fileRepository.get(responseDto.getId());

        persistedFile.setFileName(responseDto.getName());
        persistedFile.setFilePath(responseDto.getFilePath());

        persistedFile = fileRepository.update(persistedFile);

        return new FilePostResponseDto(persistedFile);
    }

    public FilePostResponseDto remove(Long id) {
        throwIfNull(id);
        throwIfNotPositive(id);

        File file = fileRepository.get(id);
        file.getEvent().setEventType(EventType.DELETED);
        file = fileRepository.update(file);

        return new FilePostResponseDto(file);
    }
}
