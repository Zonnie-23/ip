package nova.parser;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import nova.exception.NovaException;

public class Parser {
    /**
     * Parses a string into a LocalDateTime object. The input can be date and time or just a date (in which case the
     * time defaults to 23:59).
     *
     * @param dateTime String of date and/or time to parse.
     * @return the corresponding time in a LocalDateTime object.
     * @throws DateTimeException if the input format is invalid.
     */
    public static LocalDateTime parseDateTime(String dateTime) throws NovaException {
        DateTimeFormatter dateTimeFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        String[] components = dateTime.split("\\s+");
        try {
            if (components.length == 2) {
                return LocalDateTime.parse(dateTime, dateTimeFormat);
            } else if (components.length == 1) {
                LocalDate date = LocalDate.parse(dateTime, dateFormat);
                LocalTime time = LocalTime.of(23, 59);
                return LocalDateTime.of(date, time);
            } else {
                throw new DateTimeException("Invalid date format: Expected \"yyyy-MM-dd HH:mm\" or \"yyyy-MM-dd\"");
            }
        } catch (DateTimeException e) {
            throw new NovaException("Invalid date format: Expected \"yyyy-MM-dd HH:mm\" or \"yyyy-MM-dd\"");
        }
    }

    public static LocalDate parseDate(String date) throws NovaException {
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        String[] components = date.split("\\s+");
        try {
            if (components.length == 1) {
                return LocalDate.parse(date, dateFormat);
            } else {
                throw new DateTimeException("Invalid date format");
            }
        } catch (DateTimeException e) {
            throw new NovaException("Invalid date format: Expected \"yyyy-MM-dd\"");
        }
    }

    /**
     * Formats specific time into a human-readable string.
     *
     * @param localDateTime the LocalDateTime object to format
     * @return a formatted string representing the date and time.
     */
    public static String outputDateTime(LocalDateTime localDateTime) {
        DateTimeFormatter outputDateTimeFormat = DateTimeFormatter.ofPattern("MMM dd yyyy HH:mm");
        return localDateTime.format(outputDateTimeFormat);
    }

    /**
     * Formats date into a human-readable string.
     *
     * @param localDate the LocalDate object to format
     * @return a formatted string representing the date.
     */
    public static String outputDate(LocalDate localDate) {
        if (localDate.isEqual(LocalDate.now())) {
            return "today";
        }
        DateTimeFormatter outputDateTimeFormat = DateTimeFormatter.ofPattern("MMM dd yyyy");
        return localDate.format(outputDateTimeFormat);
    }
}
