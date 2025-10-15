package insurabook.logic.parser;

import static java.util.Objects.requireNonNull;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import insurabook.commons.core.index.Index;
import insurabook.commons.util.StringUtil;
import insurabook.logic.parser.exceptions.ParseException;
import insurabook.model.claims.ClaimAmount;
import insurabook.model.claims.ClaimId;
import insurabook.model.claims.ClaimMessage;
import insurabook.model.claims.InsuraDate;
import insurabook.model.client.Address;
import insurabook.model.client.ClientId;
import insurabook.model.client.Email;
import insurabook.model.client.Name;
import insurabook.model.client.Phone;
import insurabook.model.policies.PolicyId;
import insurabook.model.policytype.PolicyTypeDescription;
import insurabook.model.policytype.PolicyTypeId;
import insurabook.model.policytype.PolicyTypeName;
import insurabook.model.policytype.PolicyTypePremium;
import insurabook.model.tag.Tag;

/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 */
public class ParserUtil {

    public static final String MESSAGE_INVALID_INDEX = "Index is not a non-zero unsigned integer.";

    /**
     * Parses {@code oneBasedIndex} into an {@code Index} and returns it. Leading and trailing whitespaces will be
     * trimmed.
     * @throws ParseException if the specified index is invalid (not non-zero unsigned integer).
     */
    public static Index parseIndex(String oneBasedIndex) throws ParseException {
        String trimmedIndex = oneBasedIndex.trim();
        if (!StringUtil.isNonZeroUnsignedInteger(trimmedIndex)) {
            throw new ParseException(MESSAGE_INVALID_INDEX);
        }
        return Index.fromOneBased(Integer.parseInt(trimmedIndex));
    }

    /**
     * Parses a {@code String name} into a {@code Name}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code name} is invalid.
     */
    public static Name parseName(String name) throws ParseException {
        requireNonNull(name);
        String trimmedName = name.trim();
        if (!Name.isValidName(trimmedName)) {
            throw new ParseException(Name.MESSAGE_CONSTRAINTS);
        }
        return new Name(trimmedName);
    }

    /**
     * Parses a {@code String phone} into a {@code Phone}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code phone} is invalid.
     */
    public static Phone parsePhone(String phone) throws ParseException {
        requireNonNull(phone);
        String trimmedPhone = phone.trim();
        if (!Phone.isValidPhone(trimmedPhone)) {
            throw new ParseException(Phone.MESSAGE_CONSTRAINTS);
        }
        return new Phone(trimmedPhone);
    }

    /**
     * Parses a {@code String address} into an {@code Address}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code address} is invalid.
     */
    public static Address parseAddress(String address) throws ParseException {
        requireNonNull(address);
        String trimmedAddress = address.trim();
        if (!Address.isValidAddress(trimmedAddress)) {
            throw new ParseException(Address.MESSAGE_CONSTRAINTS);
        }
        return new Address(trimmedAddress);
    }

    /**
     * Parses a {@code String email} into an {@code Email}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code email} is invalid.
     */
    public static Email parseEmail(String email) throws ParseException {
        requireNonNull(email);
        String trimmedEmail = email.trim();
        if (!Email.isValidEmail(trimmedEmail)) {
            throw new ParseException(Email.MESSAGE_CONSTRAINTS);
        }
        return new Email(trimmedEmail);
    }

    /**
     * Parses a {@code String tag} into a {@code Tag}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code tag} is invalid.
     */
    public static Tag parseTag(String tag) throws ParseException {
        requireNonNull(tag);
        String trimmedTag = tag.trim();
        if (!Tag.isValidTagName(trimmedTag)) {
            throw new ParseException(Tag.MESSAGE_CONSTRAINTS);
        }
        return new Tag(trimmedTag);
    }

    /**
     * Parses {@code Collection<String> tags} into a {@code Set<Tag>}.
     */
    public static Set<Tag> parseTags(Collection<String> tags) throws ParseException {
        requireNonNull(tags);
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(parseTag(tagName));
        }
        return tagSet;
    }

    /**
     * Parses a {@code String clientId} into a {@code ClientId}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code clientId} is invalid
     */
    public static ClientId parseClientId(String clientId) throws ParseException {
        requireNonNull(clientId);
        String trimmedClientId = clientId.trim();
        if (!ClientId.isValidClientId(trimmedClientId)) {
            throw new ParseException(ClientId.MESSAGE_CONSTRAINTS);
        }
        return new ClientId(trimmedClientId);
    }

    /**
     * Parses a {@code String policyId} into a {@code PolicyId}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code policyId} is invalid
     */
    public static PolicyId parsePolicyId(String policyId) throws ParseException {
        requireNonNull(policyId);
        String trimmedPolicyId = policyId.trim();
        if (!PolicyId.isValidPolicyId(trimmedPolicyId)) {
            throw new ParseException(PolicyId.MESSAGE_CONSTRAINTS);
        }
        return new PolicyId(trimmedPolicyId);
    }

    /**
     * Parses a {@code String amount} into a {@code ClaimAmount}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code amount} is invalid.
     */
    public static ClaimAmount parseClaimAmount(String amount) throws ParseException {
        requireNonNull(amount);
        String trimmedAmount = amount.trim();
        if (!ClaimAmount.isValidClaimAmount(trimmedAmount)) {
            throw new ParseException(ClaimAmount.MESSAGE_CONSTRAINTS);
        }
        return new ClaimAmount(trimmedAmount);
    }

    /**
     * Parses a {@code String date} into a {@code InsuraDate}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code date} is invalid.
     */
    public static InsuraDate parseInsuraDate(String date) throws ParseException {
        requireNonNull(date);
        String trimmedDate = date.trim();
        if (!InsuraDate.isValidInsuraDate(trimmedDate)) {
            throw new ParseException(InsuraDate.MESSAGE_CONSTRAINTS);
        }
        return new InsuraDate(trimmedDate);
    }

    /**
     * Parses a {@code String message} into a {@code ClaimMessage}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code message} is invalid.
     */
    public static ClaimMessage parseClaimMessage(String message) throws ParseException {
        requireNonNull(message);
        String trimmedMessage = message.trim();
        if (!ClaimMessage.isValidClaimMessage(trimmedMessage)) {
            throw new ParseException(ClaimMessage.MESSAGE_CONSTRAINTS);
        }
        return new ClaimMessage(trimmedMessage);
    }

    /**
     * Parses a {@code String claimId} into a {@code ClaimId}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code claimId} is invalid.
     */
    public static ClaimId parseClaimId(String claimId) throws ParseException {
        requireNonNull(claimId);
        String trimmedClaimId = claimId.trim();
        if (!ClaimId.isValidClientId(trimmedClaimId)) {
            throw new ParseException(ClaimId.MESSAGE_CONSTRAINTS);
        }
        return new ClaimId(trimmedClaimId);
    }

    /**
     * Parses a {@code String ptName} into a {@code PolicyTypeName}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code ptName} is invalid.
     */
    public static PolicyTypeName parsePtName(String ptName) throws ParseException {
        requireNonNull(ptName);
        String trimmedPtName = ptName.trim();
        if (!PolicyTypeName.isValidPtName(trimmedPtName)) {
            throw new ParseException(PolicyTypeName.MESSAGE_CONSTRAINTS);
        }
        return new PolicyTypeName(trimmedPtName);
    }

    /**
     * Parses a {@code String ptId} into a {@code PolicyTypeId}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code ptId} is invalid.
     */
    public static PolicyTypeId parsePtId(String ptId) throws ParseException {
        requireNonNull(ptId);
        String trimmedPtId = ptId.trim();
        if (!PolicyTypeId.isValidPtId(trimmedPtId)) {
            throw new ParseException(PolicyTypeId.MESSAGE_CONSTRAINTS);
        }
        return new PolicyTypeId(trimmedPtId);
    }

    /**
     * Parses a {@code String ptDesc} into a {@code PolicyTypeId}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code ptDesc} is invalid.
     */
    public static PolicyTypeDescription parsePtDescription(String ptDesc) throws ParseException {
        if (!ptDesc.isBlank()) {
            String trimmedPtDesc = ptDesc.trim();
            if (!PolicyTypeDescription.isValidPtDescription(trimmedPtDesc)) {
                throw new ParseException(PolicyTypeDescription.MESSAGE_CONSTRAINTS);
            }
            return new PolicyTypeDescription(trimmedPtDesc);
        } else {
            return new PolicyTypeDescription();
        }
    }

    /**
     * Parses a {@code String ptDesc} into a {@code PolicyTypeId}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code ptDesc} is invalid.
     */
    public static PolicyTypePremium parsePtPremium(String ptPremium) throws ParseException {
        if (!ptPremium.isBlank()) {
            String trimmedPtPremium = ptPremium.trim();
            if (!PolicyTypePremium.isValidPtPremium(trimmedPtPremium)) {
                throw new ParseException(PolicyTypePremium.MESSAGE_CONSTRAINTS);
            }
            return new PolicyTypePremium(trimmedPtPremium);
        } else {
            return new PolicyTypePremium();
        }
    }

}
