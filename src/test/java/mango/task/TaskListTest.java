package mango.task;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TaskListTest {

    @Test
        void markAndUnmark_flipStatusButKeepObject() {
        TaskList list = new TaskList();
        Task t = list.add(new Todo("buy milk"));

        assertTrue(t.toString().contains("[ ][ ]") || t.toString().contains("[T][ ]"));

        list.mark(0);
        assertTrue(list.get(0).toString().contains("[T][X]"));

        list.unmark(0);
        assertTrue(list.get(0).toString().contains("[T][ ]"));
    }
}
