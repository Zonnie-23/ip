import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import nova.command.DeadlineCommand;
import nova.exception.NovaException;
import nova.tasklist.TaskList;
import nova.ui.Ui;

public class DeadlineCommandTest {
    @Test
    public void testValidDeadlineFormat() throws NovaException {
        TaskList taskList = new TaskList(new ArrayList<>());
        Ui ui = new Ui();
        String instruction = "deadline finish assignment /by 2025-02-05 23:59";

        DeadlineCommand cmd = new DeadlineCommand(taskList, ui, instruction);
        boolean isSuccessful = cmd.execute();
        assertTrue(isSuccessful);
        assertEquals(1, taskList.size());
    }

    @Test
    public void testValidDeadlineFormat2() throws NovaException {
        TaskList taskList = new TaskList(new ArrayList<>());
        Ui ui = new Ui();
        String instruction = "deadline complete notetaking for CS2103T /by 2025-02-05";

        DeadlineCommand cmd = new DeadlineCommand(taskList, ui, instruction);
        boolean isSuccessful = cmd.execute();
        assertTrue(isSuccessful);
        assertEquals(1, taskList.size());
    }

    @Test
    public void testInvalidMissingBackslash() throws NovaException {
        TaskList taskList = new TaskList(new ArrayList<>());
        Ui ui = new Ui();
        String instruction = "deadline finsh assignment by 2025-02-05 23:59";
        boolean isSuccessful = false;

        try {
            DeadlineCommand cmd = new DeadlineCommand(taskList, ui, instruction);
        } catch (NovaException e) {
            isSuccessful = true;
        }
        assertTrue(isSuccessful);
        assertEquals(0, taskList.size());
    }

    @Test
    public void testInvalidMissingDescriptor() throws NovaException {
        TaskList taskList = new TaskList(new ArrayList<>());
        Ui ui = new Ui();
        String instruction = "deadline /by 2025-02-05 23:59";
        boolean isSuccessful = false;

        try {
            DeadlineCommand cmd = new DeadlineCommand(taskList, ui, instruction);
            isSuccessful = !cmd.execute();
        } catch (NovaException e) {
            isSuccessful = true;
        }
        assertTrue(isSuccessful);
        assertEquals(0, taskList.size());
    }

    @Test
    public void testInvalidDateFormat() throws NovaException {
        TaskList taskList = new TaskList(new ArrayList<>());
        Ui ui = new Ui();
        String instruction = "deadline /by 2025--:05 23:";
        boolean isSuccessful = false;

        try {
            DeadlineCommand cmd = new DeadlineCommand(taskList, ui, instruction);
        } catch (NovaException e) {
            isSuccessful = true;
        }
        assertTrue(isSuccessful);
        assertEquals(0, taskList.size());
    }
}
