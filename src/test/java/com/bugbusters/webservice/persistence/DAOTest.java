package com.bugbusters.webservice.persistence;

import com.bugbusters.util.Database;
import com.bugbusters.webservice.entity.Meme;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class DAOTest {

    GenericDAO dao;

    @BeforeEach
    void setUp() {
        dao = new GenericDAO();
        Database db = new Database();
        db.runSQL("cleanDB.sql");
    }

    @Test
    void getAll() {
        List<Meme> memes = dao.getAll();
        assertEquals(5, memes.size());
    }

}
