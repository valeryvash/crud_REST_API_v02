package org.valeryvash.repository.impl;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.valeryvash.model.Event;
import org.valeryvash.repository.EventRepository;

import java.util.ArrayList;
import java.util.List;

import static org.valeryvash.util.HibernateSessionProvider.provideSession;

public class HibernateEventRepositoryImpl implements EventRepository {
    @Override
    public Event add(Event entity) {
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
    public Event get(Long entityId) {
        Event event = null;

        try (Session session = provideSession()) {

            event = session
                    .createQuery("""
                                    FROM Event event
                                    LEFT JOIN FETCH event.file
                                    LEFT JOIN FETCH event.user
                                    WHERE event.id = :entityId 
                                    """,
                            Event.class)
                    .setParameter("entityId", entityId)
                    .getSingleResult();

        } catch (HibernateException e) {
            e.printStackTrace();
            throw e;
        }
        return event;
    }

    @Override
    public Event update(Event entity) {
        Event event = null;
        Transaction transaction = null;

        try (Session session = provideSession()) {
            transaction = session.beginTransaction();

            event = session.merge(entity);

            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
            throw e;
        }
        return event;
    }

    @Override
    public Event remove(Long entityId) {
        Event event = null;
        Transaction transaction = null;

        try (Session session = provideSession()) {
            transaction = session.beginTransaction();

            event = session.get(Event.class, entityId);

            session.remove(event);

            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
            throw e;
        }
        return event;
    }

    @Override
    public List<Event> getAll() {
        List<Event> eventsToBeReturned = null;

        try (Session session = provideSession()) {
            eventsToBeReturned = session
                    .createQuery("""
                                    FROM Event event
                                    LEFT JOIN FETCH event.file
                                    LEFT JOIN FETCH event.user
                                    """,
                            Event.class)
                    .getResultList();
        } catch (HibernateException e) {
            e.printStackTrace();
            throw e;
        }

        if (eventsToBeReturned == null) {
            eventsToBeReturned = new ArrayList<>();
        }

        return eventsToBeReturned;
    }
}
