package com.bugbusters.webservice.persistence;

import com.bugbusters.util.Database;
import com.bugbusters.webservice.entity.Meme;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * The type Dao test.
 */
public class DAOTest {

    /**
     * The Dao.
     */
    GenericDAO dao;

    /**
     * Sets up.
     */
    @BeforeEach
    void setUp() {
        dao = new GenericDAO();
        Database db = new Database();
        db.runSQL("cleanDB.sql");
    }

    /**
     * Gets all.
     */
    @Test
    void getAll() {
        List<Meme> memes = dao.getAll();
        assertEquals(5, memes.size());
    }
}
