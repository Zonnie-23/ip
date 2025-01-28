import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import java.util.ArrayList;
import java.util.List;

public class Storage {
    private final File dataFile;

    public Storage(String filePath) {
        this.dataFile = new File(filePath.replace("/", File.separator));
    }

    // Load tasks from CSV file
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
                if (task != null) {
                    tasks.add(task);
                }
            }
            return tasks;
            // If parseTask returns null, don't add to tasks
        } catch (IOException e) {
            System.out.println("Error reading data file: " + e.getMessage());
            return null;
        }
    }

    /*
     * Convert csv to corresponding Task object for a given line
     * Since it is a private method, it is assumed that the programmer knows that only
     * one line of input is allowed
     */
    private Task parseTask(String line) {
        String[] parts = line.split(",", 4);
        try {
            String type = parts[0];
            boolean isDone = parts[1].equals("1");
            String description = parts[2];

            switch (type) {
            case "T":
                return new Todo(description, isDone);
            case "D":
                String time = parts[3];
                return new Deadline(description, isDone, time);
            case "E":
                String startTime = parts[3];
                String endTime = parts[4];
                return new Event(description, isDone, startTime, endTime);
            default:
                System.out.println("Unknown task type: " + type);
                return null;
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Error parsing data file: " + e.getMessage());
            return null;
        }
    }

    public boolean saveTask(List<Task> tasks) {
        try {
            if (!dataFile.getParentFile().exists()) {
                dataFile.getParentFile().mkdirs();
            }

            if (!dataFile.exists()) {
                dataFile.createNewFile();
            }

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(dataFile))) {
                for (Task task : tasks) {
                    writer.write(task.toCSV());
                    writer.newLine();
                }
                return true;
            }
        } catch (IOException e) {
            System.out.println("Error saving tasks: " + e.getMessage());
            return false;
        }
    }
}
