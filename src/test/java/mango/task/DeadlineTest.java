package mango.task;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import mango.exception.MangoException;

public class DeadlineTest {

    @Test
    void toFileString_formatIsConsistent() throws MangoException {
        Deadline d = new Deadline("return book", "2019-12-02 1800");
        String file = d.toFileString();

        assertTrue(file.startsWith("D | 0 | return book | 2019-12-02 1800"));
    }

    @Test
    void toString_formatIsConsistent() throws MangoException {
        Deadline d = new Deadline("return book", "2019-12-02 1800");
        String shown = d.toString();

        assertTrue(shown.contains("return book"));
        assertTrue(shown.contains("Dec") || shown.contains("Dec ") || shown.contains("Dec 02")); // month name
    }
}
