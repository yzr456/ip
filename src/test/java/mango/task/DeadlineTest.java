package mango.task;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.format.DateTimeParseException;

import org.junit.jupiter.api.Test;

import mango.exception.MangoException;

public class DeadlineTest {

    @Test
    void constructor_validDateString_parsesSuccessfully() {
        assertDoesNotThrow(() -> new Deadline("return book", "2019-12-02 1800"));
    }

    @Test
    void toFileString_validDeadline_preservesExactInputDateTime() {
        Deadline d = new Deadline("return book", "2019-12-02 1800");
        String file = d.toFileString();
        assertTrue(file.startsWith("D | 0 | return book | 2019-12-02 1800"));
    }

    @Test
    void toString_validDeadline_hasDescriptionBySectionAndFormattedDate() {
        Deadline d = new Deadline("return book", "2019-12-02 1800");
        String result = d.toString();
        assertTrue(result.contains("return book"), "Description should appear in toString");
        assertTrue(result.contains("(by:"), "Output should contain '(by: ... )' section");
        assertTrue(result.contains("Dec 02 2019, 6:00pm"),
                "Date should match format 'MMM dd yyyy, h:mma'");
    }

    @Test
    void constructor_invalidDateString_throwsIllegalArgumentException() {
        assertThrows(
                IllegalArgumentException.class, () -> new Deadline("bad", "2019-13-40 2500"));
    }

    @Test
    void constructor_invalidDateString_setsErrorMessageToErrBadDate() {
        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class, () -> new Deadline("bad", "2019-13-40 2500"));
        assertEquals(MangoException.ERR_BAD_DATE, ex.getMessage());
    }

    @Test
    void constructor_invalidDateString_wrapsDateTimeParseExceptionAsCause() {
        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class, () -> new Deadline("bad", "2019-13-40 2500"));
        assertInstanceOf(DateTimeParseException.class, ex.getCause());
    }
}
