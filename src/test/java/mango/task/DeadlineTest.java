package mango.task;

import mango.exception.MangoException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DeadlineTest {

    @Test
    void toFileString_and_toString_formatsAreConsistent() throws MangoException {
        Deadline d = new Deadline("return book", "2019-12-02 1800");
        String file = d.toFileString();
        String shown = d.toString();

        assertTrue(file.startsWith("D | 0 | return book | 2019-12-02 1800"));
        assertTrue(shown.contains("return book"));
        assertTrue(shown.contains("Dec") || shown.contains("Dec ") || shown.contains("Dec 02")); // month name
    }
}
