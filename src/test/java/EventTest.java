import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import nova.task.Event;

public class EventTest {
    @Test
    public void testToString() {
        LocalDateTime startTime = LocalDateTime.of(2025, 05, 25, 10, 0);
        LocalDateTime endTime = LocalDateTime.of(2025, 05, 25, 11, 0);;
        Event event = new Event("Meeting", startTime, endTime);
        String output = event.toString();
        assertEquals("[E][ ] Meeting (from: May 25 2025 10:00 to: May 25 2025 11:00)", output);
    }

    @Test
    public void testSetStatus() {

        LocalDateTime startTime = LocalDateTime.of(2025, 05, 25, 10, 0);
        LocalDateTime endTime = LocalDateTime.of(2025, 05, 25, 11, 0);;
        Event event = new Event("Meeting", startTime, endTime);
        event.setStatus(true);
        String output = event.toString();
        assertEquals("[E][X] Meeting (from: May 25 2025 10:00 to: May 25 2025 11:00)", output);
    }
}
