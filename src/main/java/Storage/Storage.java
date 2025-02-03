package Storage;

import Task.Task;
import Task.Deadline;
import Task.Todo;
import Task.Event;
import TaskList.TaskList;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Handles file-based storage operations for tasks.
 */
public class Storage {
    private final File dataFile;

    public Storage(String filePath) {
        this.dataFile = new File(filePath.replace("/", File.separator));
    }

    /**
     * Loads tasks from CSV data file.
     *
      * @return a list of tasks loaded from the file; returns empty list if file not found.
     */
    public List<Task> loadTask() {
        List<Task> tasks = new ArrayList<Task>();

        // If the file doesn't exist; then assume to start with empty list
        if (!dataFile.getParentFile().exists() || !dataFile.exists()) {
            return tasks;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(dataFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Task task = parseTask(line);
                // If parseTask returns null, don't add to list
                if (task != null) {
                    tasks.add(task);
                }
            }
            return tasks;
        } catch (IOException e) {
            System.out.println("    Error reading data file: " + e.getMessage());
            return null;
        }
    }

    /**
     * Parses a line from the data file into a Task object.
     *
     * @param line a CSV-formatted string representing a task.
     * @return the Task object, or null if parsing fails.
     */
    private Task parseTask(String line) {
        String[] parts = line.split(",", 5);
        try {
            String type = parts[0];
            boolean isDone = parts[1].equals("1");
            String description = parts[2];

            switch (type) {
            case "T":
                return new Todo(description, isDone);
            case "D":
                LocalDateTime time = LocalDateTime.parse(parts[3]);
                return new Deadline(description, time, isDone);
            case "E":
                LocalDateTime startTime = LocalDateTime.parse(parts[3]);
                LocalDateTime endTime = LocalDateTime.parse(parts[4]);
                return new Event(description, startTime, endTime, isDone);
            default:
                System.out.println("    Unknown task type: " + type);
                return null;
            }
        } catch (ArrayIndexOutOfBoundsException | DateTimeException e) {
            System.out.println("    Error parsing data file: " + e.getMessage());
            return null;
        }
    }

    /**
     * Saves the tasks from the given task list into the data file.
     *
     * @param tasks the TaskList containing tasks to be saved.
     * @return true if saving was successful, false otherwise.
     */
    public boolean saveTask(TaskList tasks) {
        try {
            List<Task> taskList = tasks.getTasks();
            if (!dataFile.getParentFile().exists()) {
                dataFile.getParentFile().mkdirs();
            }

            if (!dataFile.exists()) {
                dataFile.createNewFile();
            }

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(dataFile))) {
                for (Task task : taskList) {
                    writer.write(task.toCsv());
                    writer.newLine();
                }
                return true;
            }
        } catch (IOException e) {
            System.out.println("    Error saving tasks: " + e.getMessage());
            return false;
        }
    }
}
