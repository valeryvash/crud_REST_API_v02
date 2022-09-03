package org.valeryvash.repository;

import org.valeryvash.model.File;

import java.util.List;

public interface FileRepository extends GenericRepository<Long, File> {
    @Override
    File add(File entity);

    @Override
    File get(Long entityId);

    @Override
    File update(File entity);

    @Override
    File remove(Long entityId);

    @Override
    List<File> getAll();
}
