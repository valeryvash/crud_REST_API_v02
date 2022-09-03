package org.valeryvash.repository.impl;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.valeryvash.model.File;
import org.valeryvash.repository.FileRepository;

import java.util.ArrayList;
import java.util.List;

import static org.valeryvash.util.HibernateSessionProvider.provideSession;

public class HibernateFileRepositoryImpl implements FileRepository {
    @Override
    public File add(File entity) {
        Transaction transaction = null;

        try (Session session = provideSession()) {
            transaction = session.beginTransaction();

            session.persist(entity);

            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
            throw e;
        }
        return entity;
    }

    @Override
    public File get(Long entityId) {
        File file = null;

        try (Session session = provideSession()) {
            file = session.createQuery("""
                            FROM File file 
                            WHERE file.id = :id
                            """, File.class)
                    .setParameter("id", entityId)
                    .getSingleResult();

        } catch (HibernateException e) {
            e.printStackTrace();
            throw e;
        }

        return file;
    }

    @Override
    public File update(File entity) {
        File file = null;
        Transaction transaction = null;

        try (Session session = provideSession()) {
            transaction = session.beginTransaction();

            file = session.merge(entity);

            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
            throw e;
        }
        return file;
    }

    @Override
    public File remove(Long entityId) {
        File file = null;
        Transaction transaction = null;

        try (Session session = provideSession()) {
            transaction = session.beginTransaction();

            file = session.get(File.class, entityId);

            if (file == null) {
                throw new RuntimeException("Entity not found");
            }

            session.remove(file);

            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
            throw e;
        }
        return file;
    }

    @Override
    public List<File> getAll() {
        List<File> filesToBeReturned = null;

        try (Session session = provideSession()) {
            filesToBeReturned = session
                    .createQuery("""
                            FROM File file
                                    """,
                            File.class)
                    .getResultList();
        } catch (RuntimeException e) {
            e.printStackTrace();
            throw e;
        }

        if (filesToBeReturned == null) {
            filesToBeReturned = new ArrayList<>();
        }

        return filesToBeReturned;
    }
}
