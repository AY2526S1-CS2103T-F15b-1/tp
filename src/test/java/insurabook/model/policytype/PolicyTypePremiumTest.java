package insurabook.model.policytype;

import static insurabook.model.policytype.PolicyTypePremium.isValidPtPremium;
import static insurabook.testutil.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class PolicyTypePremiumTest {

    @Test
    public void constructor_null_throwsNullPointer() {
        assertThrows(NullPointerException.class, () -> new PolicyTypePremium(null));
    }

    @Test
    public void constructor_invalid_throwsIllegalArgument() {
        String invalidPremium = "";
        assertThrows(IllegalArgumentException.class, () -> new PolicyTypePremium(invalidPremium));
    }

    @Test
    public void isValidPtPremium_valid_true() {
        assertTrue(isValidPtPremium("100.0"));
    }

    @Test
    public void isValidPtPremium_empty_false() {
        assertFalse(isValidPtPremium(""));
        assertFalse(isValidPtPremium("   "));
    }

    @Test
    public void isValidPtPremium_nonNumber_false() {
        assertFalse(isValidPtPremium("a"));
    }

    @Test
    public void equals() {
        String validPremium = "100";
        PolicyTypePremium premium = new PolicyTypePremium(validPremium);

        // same object -> true
        assertTrue(premium.equals(premium));

        // null -> false
        assertFalse(premium.equals(null));

        // same value -> true
        assertTrue(premium.equals(new PolicyTypePremium(validPremium)));

        // different value -> false
        assertFalse(premium.equals(new PolicyTypePremium("200")));
    }

}
