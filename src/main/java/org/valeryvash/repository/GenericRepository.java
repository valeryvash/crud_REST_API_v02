package org.valeryvash.repository;

import java.util.List;

public interface GenericRepository<ID, T> {

    T add(T entity);

    T get(ID entityId);

    T update(T entity);

    T remove(ID entityId);

    List<T> getAll();
}
