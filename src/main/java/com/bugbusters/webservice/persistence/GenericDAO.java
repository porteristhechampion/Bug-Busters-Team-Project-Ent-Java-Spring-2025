package com.bugbusters.webservice.persistence;

import com.bugbusters.webservice.entity.Meme;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.query.criteria.HibernateCriteriaBuilder;

import java.util.List;

/**
 * The type Generic dao.
 */
public class GenericDAO {

    private static final Logger logger = LogManager.getLogger(GenericDAO.class);

    private Session getSession() {return SessionFactoryProvider.getSessionFactory().openSession();}

    /**
     * Gets all memes.
     *
     * @return all memes
     */
    public List<Meme> getAll() {

        Session session = getSession();

        HibernateCriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Meme> criteria = builder.createQuery(Meme.class);
        Root<Meme> root = criteria.from(Meme.class);
        List<Meme> list = session.createQuery(criteria).getResultList();

        logger.info(list.toString());
        session.close();
        return list;
    }
}
