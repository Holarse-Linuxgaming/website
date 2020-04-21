package de.holarse.services;

import java.time.Duration;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Locale;

public class DateUtils {

    private DateUtils(){};

    public static LocalDate parseIsoDate(final String date) {
        return LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }

    public static String format(final OffsetDateTime datetime) {
        if (datetime == null) return "";
        
        return datetime.format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.FULL, FormatStyle.MEDIUM).withLocale(Locale.GERMAN));
    }

    public static String formatISO(final OffsetDateTime datetime) {
        if (datetime == null) return "";
        
        return datetime.format(DateTimeFormatter.ISO_DATE_TIME);
    }

    /**
     * Formatiert das Datum als YYYY-MM-DD
     * @param datetime
     * @return Das Datum als YYYY-MM-DD
     */
    public static String formatDate(final OffsetDateTime datetime) {
        if (datetime == null) return "";
        
        return datetime.format(DateTimeFormatter.ISO_LOCAL_DATE);        
    }

    /**
     * Formatiert das Datum als YYYY-MM-DD
     * @param datetime
     * @return Das Datum als YYYY-MM-DD
     */
    public static String formatDate(final LocalDate datetime) {
        if (datetime == null) return "";
        
        return datetime.format(DateTimeFormatter.ISO_LOCAL_DATE);        
    }    

    /**
     * Formatiert das Datum als dd.MM.YYYY HH::mm::ss
     * @param datetime
     * @return
     */
    public static String formatShort(final OffsetDateTime datetime) {
        if (datetime == null) return "";
        
        return datetime.format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss"));
    }        

    public static String formatAgo(final OffsetDateTime datetime) {
        final Duration duration = Duration.between(OffsetDateTime.now(), datetime);
        final long minutes = Math.abs(duration.toMinutes());

        System.out.println("minutes: " + minutes);

        if (minutes <= 1) {
            return "Gerade eben";
        }

        if (minutes < 60) {
            return String.format("Vor %d Minuten", minutes);
        }

        if (minutes < 60 * 24) {
            final long hours = Math.abs(duration.toHours());
            if (hours > 1) {
                return String.format("Vor %d Stunden", hours);
            } else {
                return "Vor einer Stunde";
            }
        }

        if (minutes < 60 * 24 * 30) {
            final long days = Math.abs(duration.toDays());
            if (days > 1) {
                return String.format("Vor %d Tagen", days);
            } else {
                return "Gestern";
            }
        }

        if (minutes < 60 * 24 * 30 * 12) {
            final long months = Math.abs(duration.toDays() / 30);
            if (months > 1) {
                return String.format("Vor %d Monaten", months);
            } else {
                return "Vor einem Monat";
            }
        }

        long years = Math.abs(duration.toDays() / 365);
        if (years > 1) {
            return String.format("Vor %d Jahren", years);
        }

        return "Vor einem Jahr";
    }

}