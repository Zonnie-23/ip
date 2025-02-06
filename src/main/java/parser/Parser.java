package parser;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Parser {
    /**
     * Parses a string into a LocalDateTime object. The input can be date and time or just a date (in which case the
     * time defaults to 23:59).
     *
     * @param dateTime String of date and/or time to parse.
     * @return the corresponding time in a LocalDateTime object.
     * @throws DateTimeException if the input format is invalid.
     */
    public static LocalDateTime parseDateTime(String dateTime) throws DateTimeException {
        DateTimeFormatter dateTimeFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        String[] components = dateTime.split("\\s+");
        if (components.length == 2) {
            return LocalDateTime.parse(dateTime, dateTimeFormat);
        } else if (components.length == 1) {
            LocalDate date = LocalDate.parse(dateTime, dateFormat);
            LocalTime time = LocalTime.of(23, 59);
            return LocalDateTime.of(date, time);
        } else {
            throw new DateTimeException("Invalid date format: Expected \"yyyy-MM-dd HH:mm\" or \"yyyy-MM-dd\"");
        }
    }

    /**
     * Formats into a human-readable string.
     *
     * @param localDateTime the LocalDateTime object to format
     * @return a formatted string representing the date and time.
     */
    public static String outputDateTime(LocalDateTime localDateTime) {
        DateTimeFormatter outputDateTimeFormat = DateTimeFormatter.ofPattern("MMM dd yyyy HH:mm");
        return localDateTime.format(outputDateTimeFormat);
    }
}
