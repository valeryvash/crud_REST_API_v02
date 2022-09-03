package org.valeryvash.repository.impl;

import jakarta.persistence.NoResultException;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.valeryvash.model.File;
import org.valeryvash.model.User;
import org.valeryvash.repository.UserRepository;

import java.util.List;

import static org.valeryvash.util.HibernateSessionProvider.provideSession;

public class HibernateUserRepositoryImpl implements UserRepository {
    @Override
    public User add(User entity) {
        Transaction transaction = null;

        try (Session session = provideSession()) {
            transaction = session.beginTransaction();

            session.persist(entity);

            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw e;
        }
        return entity;
    }

    @Override
    public User get(Long entityId) {
        User user = null;

        try (Session session = provideSession()) {

            user = session
                    .createQuery("""
                                    FROM User user
                                    LEFT JOIN FETCH user.events
                                    WHERE user.id = :entityId 
                                    """,
                            User.class)
                    .setParameter("entityId", entityId)
                    .getSingleResult();

            List<File> files = session
                    .createQuery("""
                                    FROM File file
                                    LEFT JOIN FETCH file.event events
                                    WHERE events.user = :user                          
                                    """,
                            File.class)
                    .setParameter("user",user)
                    .getResultList();

        } catch (HibernateException e) {
            throw e;
        }

        if (user == null) {
            user = new User();
        }

        return user;
    }

    @Override
    public User update(User entity) {
        User user = null;
        Transaction transaction = null;

        try (Session session = provideSession()) {
            transaction = session.beginTransaction();

            user = session.merge(entity);

            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw e;
        }
        return user;
    }

    @Override
    public User remove(Long entityId) {
        User user = null;
        Transaction transaction = null;

        try (Session session = provideSession()) {
            transaction = session.beginTransaction();

            user = session.get(User.class, entityId);

            if (user == null) {
                throw new NoResultException("Null entity not presented in persisted context");
            }
            session.remove(user);

            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw e;
        }
        return user;
    }

    @Override
    public List<User> getAll() {
        List<User> list = null;
        Transaction transaction = null;

        try (Session session = provideSession()) {
            transaction = session.beginTransaction();
            list = session
                    .createQuery("""
                                    FROM User user
                                    LEFT JOIN FETCH user.events
                                    """,
                            User.class)
                    .getResultList();

            List<File> files = session
                    .createQuery("""
                                    FROM File file
                                    LEFT JOIN FETCH file.event events
                                    WHERE events.user in (:list)                          
                                    """,
                            File.class)
                    .setParameter("list",list)
                    .getResultList();

            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw e;
        }
        return list;
    }

}
