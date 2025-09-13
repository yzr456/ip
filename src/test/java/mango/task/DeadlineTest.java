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

        assertTrue(result.contains("return book"));
        assertTrue(result.contains("(by:"));

        var expected = java.time.LocalDateTime.parse(
                "2019-12-02 1800", java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm"))
                .format(java.time.format.DateTimeFormatter.ofPattern(
                        "MMM dd yyyy, h:mma", java.util.Locale.getDefault()));
        assertTrue(
                result.contains(expected), ()
                        -> "Date should match 'MMM dd yyyy, h:mma' in locale " + java.util.Locale.getDefault());
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
