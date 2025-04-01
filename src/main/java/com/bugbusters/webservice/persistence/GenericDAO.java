package com.bugbusters.webservice.persistence;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;

public class GenericDAO {

    private static final Logger logger = LogManager.getLogger(GenericDAO.class);

    private Session getSession() {return SessionFactoryProvider.getSessionFactory().openSession();}

}
