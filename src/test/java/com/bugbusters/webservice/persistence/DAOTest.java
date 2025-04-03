package com.bugbusters.webservice.persistence;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DAOTest {

    GenericDAO dao;

    @BeforeEach
    void setUp() {
        dao = new GenericDAO();
    }

}
