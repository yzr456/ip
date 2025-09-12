package mango.task;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import mango.exception.MangoException;

public class TaskListTest {

    @Test
    void add_duplicateTask_throwsMangoExceptionWithDuplicateMessage() throws MangoException {
        TaskList list = new TaskList();
        Task t = new Todo("duplicate");
        list.add(t);
        MangoException ex = assertThrows(MangoException.class, () -> list.add(t));
        assertEquals(MangoException.ERR_DUPLICATE_TASK, ex.getMessage());
    }

    @Test
    void mark_validSingleIndex_setsTaskDoneAndKeepsIdentity() throws MangoException {
        TaskList list = new TaskList();
        list.add(new Todo("buy milk"));
        Task before = list.get(0);
        List<Task> updated = list.mark(List.of(0));
        assertEquals(1, updated.size());
        assertSame(before, list.get(0), "Object identity should be preserved");
        assertTrue(list.get(0).toString().contains("[X]"), "Marked task should show as done");
    }

    @Test
    void unmark_validSingleIndex_setsTaskNotDoneAndKeepsIdentity() throws MangoException {
        TaskList list = new TaskList();
        list.add(new Todo("buy milk"));
        list.mark(List.of(0));
        Task before = list.get(0);
        List<Task> updated = list.unmark(List.of(0));
        assertEquals(1, updated.size());
        assertSame(before, list.get(0), "Object identity should be preserved");
        assertTrue(list.get(0).toString().contains("[ ]"), "Unmarked task should show as not done");
    }

    @Test
    void mark_multipleIndices_setsAllDone() throws MangoException {
        TaskList list = new TaskList();
        list.add(new Todo("T0"));
        list.add(new Todo("T1"));
        list.add(new Todo("T2"));
        list.mark(List.of(0, 2));
        assertTrue(list.get(0).toString().contains("[X]"));
        assertTrue(list.get(2).toString().contains("[X]"));
        assertTrue(list.get(1).toString().contains("[ ]"), "Untouched task remains not done");
    }

    @Test
    void find_keywordProvided_returnsOnlyMatchingTasks() throws MangoException {
        TaskList list = new TaskList();
        list.add(new Todo("buy milk"));
        list.add(new Todo("call mom"));
        list.add(new Todo("milk powder"));
        List<Task> results = list.find("milk");
        assertEquals(2, results.size());
        assertTrue(results.stream().allMatch(t -> t.description.contains("milk")));
    }
}
