package insurabook.logic;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import insurabook.logic.parser.Prefix;
import insurabook.model.claims.Claim;
import insurabook.model.client.Client;
import insurabook.model.policies.Policy;

/**
 * Container for user visible messages.
 */
public class Messages {

    public static final String MESSAGE_UNKNOWN_COMMAND = "Unknown command";
    public static final String MESSAGE_INVALID_COMMAND_FORMAT = "Invalid command format! \n%1$s";
    public static final String MESSAGE_INVALID_PERSON_DISPLAYED_INDEX = "The person index provided is invalid";
    public static final String MESSAGE_PERSONS_LISTED_OVERVIEW = "%1$d persons listed!";
    public static final String MESSAGE_DUPLICATE_FIELDS =
                "Multiple values specified for the following single-valued field(s): ";

    /**
     * Returns an error message indicating the duplicate prefixes.
     */
    public static String getErrorMessageForDuplicatePrefixes(Prefix... duplicatePrefixes) {
        assert duplicatePrefixes.length > 0;

        Set<String> duplicateFields =
                Stream.of(duplicatePrefixes).map(Prefix::toString).collect(Collectors.toSet());

        return MESSAGE_DUPLICATE_FIELDS + String.join(" ", duplicateFields);
    }

    /**
     * Formats the {@code person} for display to the user.
     */
    public static String format(Client client) {
        final StringBuilder builder = new StringBuilder();
        builder.append(client.getName())
                .append("; Phone: ")
                .append(client.getPhone())
                .append("; Email: ")
                .append(client.getEmail())
                .append("; Address: ")
                .append(client.getAddress())
                .append("; Tags: ");
        client.getTags().forEach(builder::append);
        return builder.toString();
    }

    /**
     * Formats the {@code claim} for display to the user.
     */
    public static String format(Claim claim) {
        final StringBuilder builder = new StringBuilder();
        builder.append(claim.getClaimId())
                .append("; Client Id: ")
                .append(claim.getClientId())
                .append("; Policy Id: ")
                .append(claim.getPolicyId())
                .append("; Claim amount: ")
                .append(claim.getAmount())
                .append("; Claim date: ")
                .append(claim.getDate())
                .append("; Description: ")
                .append(claim.getDescription());
        return builder.toString();
    }

    /**
     * Formats the {@code policy} for display to the user.
     */
    public static String format(Policy policy) {
        final StringBuilder builder = new StringBuilder();
        builder.append(policy.getPolicyId())
                .append("; Policy Id: ")
                .append(policy.getPolicyId())
                .append("; Client Id: ")
                .append(policy.getClient().getClientId())
                .append("; PolicyTypeId: ")
                .append(policy.getPolicyType().getPtId())
                .append("; Expiry Date: ")
                .append(policy.getExpiryDate());
        return builder.toString();
    }

}
