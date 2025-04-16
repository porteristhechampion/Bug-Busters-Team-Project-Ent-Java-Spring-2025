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
public class MemeDAOTest {

    /**
     * The Dao.
     */
    GenericDAO<Meme> memeDAO;

    /**
     * Sets up.
     */
    @BeforeEach
    void setUp() {
        memeDAO = new GenericDAO<>(Meme.class);
        Database db = new Database();
        db.runSQL("cleanDB.sql");
    }

    @Test
    void getAll() {
        memeDAO.getAll();
        List<Meme> memes = memeDAO.getAll();
        assertEquals(5, memes.size());
    }

    @Test
    void getById() {
        memeDAO.getById(1);
        Meme meme = memeDAO.getById(1);
        assertEquals("When the build works", meme.getTextTop());
    }

    @Test
    void insert() {
        Meme memeToBeInserted = new Meme("https://bug-busters-cat-meme.s3.us-east-2.amazonaws.com/memes/Buster-1.jpg", "NOBODY CAN TELL", "I'M BARELY HOLDING IT TOGETHER");

        Meme insertedMeme = memeDAO.insert(memeToBeInserted);
        assertNotNull(insertedMeme);
        assertEquals("NOBODY CAN TELL", insertedMeme.getTextTop());
    }
}
