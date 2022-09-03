package org.valeryvash.repository;

import org.valeryvash.model.User;

import java.util.List;

public interface UserRepository extends GenericRepository<Long, User> {
    @Override
    User add(User entity);

    @Override
    User get(Long entityId);

    @Override
    User update(User entity);

    @Override
    User remove(Long entityId);

    @Override
    List<User> getAll();
}
