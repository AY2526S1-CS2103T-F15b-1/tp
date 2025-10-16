package insurabook.model.client;

import java.util.List;
import java.util.function.Predicate;

import insurabook.commons.util.StringUtil;
import insurabook.commons.util.ToStringBuilder;

/**
 * Tests that a {@code Client}'s {@code ClientId} matches any of the keywords given.
 */
public class IdContainsKeywordsPredicate implements Predicate<Client> {
    private final List<String> keywords;

    public IdContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Client client) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(client.getClientId().toString(), keyword));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof IdContainsKeywordsPredicate)) {
            return false;
        }

        IdContainsKeywordsPredicate otherIdContainsKeywordsPredicate = (IdContainsKeywordsPredicate) other;
        return keywords.equals(otherIdContainsKeywordsPredicate.keywords);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("keywords", keywords).toString();
    }
}
