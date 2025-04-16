package com.bugbusters.webservice.persistence;

import com.bugbusters.webservice.entity.Meme;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.criteria.HibernateCriteriaBuilder;

import java.util.List;

/**
 * The type Generic dao.
 */
public class GenericDAO<T> {
    private final Class<T> type;
    private final Logger logger = LogManager.getLogger(this.getClass());

    /**
     * Constructs a new GenericDAO for the given entity class.
     *
     * @param type The class type of the entity.
     */
    public GenericDAO(Class<T> type) {
        this.type = type;
    }

    private Session getSession() {
        return SessionFactoryProvider.getSessionFactory().openSession();
    }

    /**
     * Retrieves all entities of the given type from the database.
     *
     * @return A list of all entities.
     */
    public List<T> getAll() {
        try (Session session = SessionFactoryProvider.getSessionFactory().openSession()) {
            HibernateCriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<T> query = builder.createQuery(type);
            query.from(type);
            List<T> entities = session.createSelectionQuery(query).getResultList();
            logger.debug("The list of entities: " + entities);
            return entities;
        }
    }

    /**
     * Retrieves an entity by its primary key.
     *
     * @param id   The primary key value.
     * @param <ID> The type of the primary key.
     * @return The entity with the given ID, or null if not found.
     */
    public <ID> T getById(ID id) {
        try (Session session = SessionFactoryProvider.getSessionFactory().openSession()) {
            return session.get(type, id);
        }
    }


    /**
     * Inserts a new entity into the database and refreshes it to retrieve its generated ID.
     *
     * @param entity The entity to insert.
     * @return The inserted and refreshed entity.
     */
    public T insert(T entity) {
        try (Session session = SessionFactoryProvider.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.persist(entity);
            transaction.commit();
            session.refresh(entity);
            return entity;
        }
    }


}