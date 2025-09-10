package mango.task;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

public class TaskListTest {

    @Test
    void markAndUnmark_flipStatusButKeepObject() {
        TaskList list = new TaskList();
        Task t = list.add(new Todo("buy milk"));

        assertTrue(t.toString().contains("[ ][ ]") || t.toString().contains("[T][ ]"));

        list.mark(List.of(0));
        assertTrue(list.get(0).toString().contains("[T][X]"));

        list.unmark(List.of(0));
        assertTrue(list.get(0).toString().contains("[T][ ]"));
    }
}
