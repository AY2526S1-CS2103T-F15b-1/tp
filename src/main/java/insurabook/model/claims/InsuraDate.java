package insurabook.model.claims;

import static insurabook.commons.util.AppUtil.checkArgument;
import static java.util.Objects.requireNonNull;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.Date;

/**
 * Represents a Claim Date in the insurance management system.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class InsuraDate {
    public static final String MESSAGE_CONSTRAINTS = "Date should be in the format YYYY-MM-DD";
    public static final String VALIDATION_PATTERN = "uuuu-MM-dd";
    public static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private final LocalDate date;

    /**
     * Constructs a {@code InsuraDate}.
     *
     * @param date A valid date.
     */
    public InsuraDate(String date) {
        requireNonNull(date);
        checkArgument(isValidInsuraDate(date), MESSAGE_CONSTRAINTS);
        this.date = LocalDate.parse(date);
    }

    /**
     * Returns true if a given string is a valid date.
     */
    public static boolean isValidInsuraDate(String test) {
        DateTimeFormatter formatter = DateTimeFormatter
                .ofPattern(VALIDATION_PATTERN)
                .withResolverStyle(ResolverStyle.STRICT);

        try {
            LocalDate.parse(test, formatter);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    /**
     * Returns true if the date is today in Singapore timezone.
     */
    public boolean isToday() {
        ZoneId sgZone = ZoneId.of("Asia/Singapore");
        LocalDate now = ZonedDateTime.now(sgZone).toLocalDate();
        int month = this.date.getMonthValue();
        int day = this.date.getDayOfMonth();
        return (now.getMonthValue() == month) && (now.getDayOfMonth() == day);
    }

    /**
     * Returns true if the date is after today
     * @return boolean true/false
     */
    public boolean isAfterToday() {
        LocalDate today = LocalDate.now();
        return this.date.isAfter(today);
    }

    /**
     * Returns true if the date is in three days' time in Singapore timezone.
     */
    public boolean isExpiringInThreeDays() {
        ZoneId sgZone = ZoneId.of("Asia/Singapore");
        LocalDate today = ZonedDateTime.now(sgZone).toLocalDate();
        LocalDate expiryDate = this.date;
        LocalDate threeDaysLater = today.plusDays(3);
        return (!expiryDate.isBefore(today)) && (expiryDate.isBefore(threeDaysLater));
    }

    /**
     * Returns a formatted date string for UI display.
     */
    public String toUiString() {
        try {
            LocalDate localDate = this.date;
            Date dateObj = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
            SimpleDateFormat formatter = new SimpleDateFormat("MMM dd, yyyy");
            return formatter.format(dateObj);
        } catch (DateTimeParseException e) {
            return this.toString();
        }
    }

    /**
     * Returns the date string.
     */
    @Override
    public String toString() {
        return date.format(FORMATTER);
    }

    /**
     * Returns true if both InsuraDate have the same date.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof InsuraDate)) {
            return false;
        }

        InsuraDate otherDate = (InsuraDate) other;
        return otherDate.date.equals(this.date);
    }

    @Override
    public int hashCode() {
        return date.hashCode();
    }

    /**
     * Compares this InsuraDate with another InsuraDate.
     *
     * @param claimDate The other InsuraDate to compare to.
     * @return A negative integer, zero, or a positive integer as this date
     *         is before, equal to, or after the specified date.
     */
    public int compareTo(InsuraDate claimDate) {
        requireNonNull(claimDate);
        Date thisDate = Date.from(this.date.atStartOfDay(ZoneId.systemDefault()).toInstant());
        Date otherDate = Date.from(claimDate.date.atStartOfDay(ZoneId.systemDefault()).toInstant());
        return thisDate.compareTo(otherDate);
    }
}
