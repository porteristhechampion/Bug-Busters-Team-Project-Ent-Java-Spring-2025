package com.bugbusters.webservice.persistence;

import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.query.criteria.HibernateCriteriaBuilder;

import java.util.List;

public class GenericDAO {

    private static final Logger logger = LogManager.getLogger(GenericDAO.class);

    private Session getSession() {return SessionFactoryProvider.getSessionFactory().openSession();}

    public List<Object> getAll() {

        Session session = getSession();

        HibernateCriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Object> criteria = builder.createQuery(Object.class);
        List<Object> list = session.createQuery(criteria).getResultList();

        logger.info(list.toString());
        session.close();
        return list;
    }

}
