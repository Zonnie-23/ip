import Command.DeadlineCommand;
import Exception.NovaException;
import TaskList.TaskList;
import Ui.Ui;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class DeadlineCommandTest {
    @Test
    public void testValidDeadlineFormat() throws NovaException {
        TaskList taskList = new TaskList(new ArrayList<>());
        Ui ui = new Ui();
        String instruction = "deadline finish assignment /by 2025-02-05 23:59";

        DeadlineCommand cmd = new DeadlineCommand(taskList, ui, instruction);
        boolean result = cmd.execute();
        assertTrue(result);
        assertEquals(1, taskList.size());
    }

    @Test
    public void testInvalidDeadlineFormat() throws NovaException {
        TaskList taskList = new TaskList(new ArrayList<>());
        Ui ui = new Ui();
        String instruction = "deadline finsh assignment by 2025-02-05 23:59";
        boolean result = true;

        try {
            DeadlineCommand cmd = new DeadlineCommand(taskList, ui, instruction);
        } catch (NovaException e) {
            result = false;
        }
        assertFalse(result);
        assertEquals(0, taskList.size());
    }
}
