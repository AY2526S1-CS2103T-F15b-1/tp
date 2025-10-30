package insurabook.model.claims;

import static insurabook.commons.util.AppUtil.checkArgument;
import static java.util.Objects.requireNonNull;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;

/**
 * Represents a Claim Date in the insurance management system.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class InsuraDate extends Date {
    public static final String MESSAGE_CONSTRAINTS = "Date should be in the format YYYY-MM-DD";
    public static final String VALIDATION_REGEX = "\\d{4}-\\d{2}-\\d{2}";
    private final String date;

    /**
     * Constructs a {@code InsuraDate}.
     *
     * @param date A valid date.
     */
    public InsuraDate(String date) {
        requireNonNull(date);
        checkArgument(isValidInsuraDate(date), MESSAGE_CONSTRAINTS);
        this.date = date;
    }

    /**
     * Returns true if a given string is a valid date.
     */
    public static boolean isValidInsuraDate(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    /**
     * Returns true if the date is today in Singapore timezone.
     */
    public boolean isToday() {
        ZoneId sgZone = ZoneId.of("Asia/Singapore");
        LocalDate now = ZonedDateTime.now(sgZone).toLocalDate();
        String[] dateParts = this.date.split("-");
        int month = Integer.parseInt(dateParts[1]);
        int day = Integer.parseInt(dateParts[2]);
        return (now.getMonthValue() == month) && (now.getDayOfMonth() == day);
    }

    /**
     * Returns true if the date is after today
     * @return boolean true/false
     */
    public boolean isAfterToday() {
        LocalDate today = LocalDate.now();
        LocalDate givenDate = LocalDate.parse(this.date);
        return givenDate.isAfter(today);
    }

    /**
     * Returns true if the date is in three days' time in Singapore timezone.
     */
    public boolean isExpiringInThreeDays() {
        ZoneId sgZone = ZoneId.of("Asia/Singapore");
        LocalDate today = ZonedDateTime.now(sgZone).toLocalDate();
        LocalDate expiryDate = LocalDate.parse(this.date);
        LocalDate threeDaysLater = today.plusDays(3);
        return (!expiryDate.isBefore(today)) && (expiryDate.isBefore(threeDaysLater));
    }

    /**
     * Returns the date string.
     */
    @Override
    public String toString() {
        return date;
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
}
