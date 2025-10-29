package insurabook.logic;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import insurabook.logic.parser.Prefix;
import insurabook.model.claims.Claim;
import insurabook.model.client.Client;
import insurabook.model.policies.Policy;
import insurabook.model.policytype.PolicyType;

/**
 * Container for user visible messages.
 */
public class Messages {

    public static final String MESSAGE_UNKNOWN_COMMAND = "Unknown command";
    public static final String MESSAGE_INVALID_COMMAND_FORMAT = "Invalid command format! \n%1$s";
    public static final String MESSAGE_INVALID_PERSON_DISPLAYED_INDEX = "This client does not exist.";
    public static final String MESSAGE_PERSONS_LISTED_OVERVIEW = "%1$d persons listed!";
    public static final String MESSAGE_DUPLICATE_FIELDS =
                "Multiple values specified for the following single-valued field(s): ";
    public static final String MESSAGE_INVALID_CLAIM = "This claim does not exist.";

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
                .append("; ClientId: ")
                .append(client.getClientId());
        return builder.toString();
    }

    /**
     * Formats the {@code claim} for display to the user.
     * If op is 0, format for AddClaimCommand or EditClaimCommand success message.
     * If op is 1, format for DeleteClaimCommand display message.
     */
    public static String format(Claim claim, int op) {
        final StringBuilder builder = new StringBuilder();
        if (op == 0) {
            builder.append(claim.getClaimId())
                    .append(" for client ")
                    .append(claim.getClient().getClientId())
                    .append(", policy ")
                    .append(claim.getPolicy().getPolicyId())
                    .append(" with an amount of $")
                    .append(claim.getAmount())
                    .append(" on date ")
                    .append(claim.getDate())
                    .append("; Description: ")
                    .append(claim.getDescription());
        } else {
            builder.append(claim.getClaimId())
                    .append(" on policy ")
                    .append(claim.getPolicy().getPolicyId())
                    .append(" for client ")
                    .append(claim.getClient().getClientId())
                    .append("; Description: ")
                    .append(claim.getDescription());
        }
        return builder.toString();
    }

    /**
     * Formats the {@code policy} for display to the user.
     */
    public static String format(Policy policy, int op) {
        final StringBuilder builder = new StringBuilder();
        if (op == 0) {
            builder.append("Policy Id: ")
                    .append(policy.getPolicyId())
                    .append("; Client Id: ")
                    .append(policy.getClient().getClientId())
                    .append("; PolicyTypeId: ")
                    .append(policy.getPolicyType().getPtId())
                    .append("; Expiry Date: ")
                    .append(policy.getExpiryDate());
        } else {
            builder.append(policy.getPolicyId())
                    .append(" for client ")
                    .append(policy.getClient().getClientId());
        }
        return builder.toString();
    }

    /**
     * Formats the {@code policyType} for display to the user.
     * If op is 0, format for AddPolicyTypeCommand (name, ID)
     * If op is 1, format for EditPolicyTypeCommand (all fields)
     */
    public static String format(PolicyType policyType, int op) {
        final StringBuilder builder = new StringBuilder();
        if (op == 0) {
            builder.append("Policy Type ")
                    .append(policyType.getPtName())
                    .append(" with ID ")
                    .append(policyType.getPtId());
        } else if (op == 1) {
            builder.append("Name: ")
                    .append(policyType.getPtName())
                    .append(", ID: ")
                    .append(policyType.getPtId());
            if (!policyType.getPtDescription().isEmpty) {
                builder.append(", Desc: ")
                        .append(policyType.getPtDescription());
            }
            if (!policyType.getPtPremium().isEmpty) {
                builder.append(", Premium: ")
                        .append(policyType.getPtPremium());
            }
        }
        return builder.toString();
    }

    /**
     * Formats the {@code client} for display to the user for birthday reminders.
     */
    public static String formatBirthdayClients(Client client) {
        final StringBuilder builder = new StringBuilder();
        builder.append("Client " + client.getName())
                .append(" (ClientId: ")
                .append(client.getClientId())
                .append(")");
        return builder.toString();
    }

    /**
     * Formats the {@code policy} for display to the user for expiring policy reminders.
     */
    public static String formatExpiringPolicies(Policy policy) {
        final StringBuilder builder = new StringBuilder();
        builder.append("Client ID: ")
                .append(policy.getClient().getClientId())
                .append(", Policy ID: ")
                .append(policy.getPolicyId())
                .append(", Expiry Date: ")
                .append(policy.getExpiryDate());
        return builder.toString();
    }
}
