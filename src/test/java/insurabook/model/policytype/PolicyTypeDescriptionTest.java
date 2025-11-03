package insurabook.model.policytype;

import static insurabook.model.policytype.PolicyTypeDescription.isValidPtDescription;
import static insurabook.testutil.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class PolicyTypeDescriptionTest {

    @Test
    public void constructor_null_throwsNullPointer() {
        assertThrows(NullPointerException.class, () -> new PolicyTypeDescription(null));
    }

    @Test
    public void constructor_invalidDesc_throwsIllegalArgument() {
        String invalidDesc = "";
        assertThrows(IllegalArgumentException.class, () -> new PolicyTypeDescription(invalidDesc));
    }

    @Test
    public void isValidPtDesc_valid_true() {
        assertTrue(isValidPtDescription("Valid description!"));
    }

    @Test
    public void isValidPtDesc_empty_false() {
        assertFalse(isValidPtDescription(""));
        assertFalse(isValidPtDescription("    "));
    }

    @Test
    public void isValidPtDesc_nonAscii_false() {
        assertFalse(isValidPtDescription("¥"));
    }

    @Test
    public void isValidPtDesc_startWithDash_false() {
        assertFalse(isValidPtDescription("-desc"));
    }

    @Test
    public void equals() {
        String validDesc = "Description";
        PolicyTypeDescription desc = new PolicyTypeDescription(validDesc);

        // same object -> true
        assertTrue(desc.equals(desc));

        // null -> false
        assertFalse(desc.equals(null));

        // same desc string -> true
        assertTrue(desc.equals(new PolicyTypeDescription(validDesc)));

        // different desc string -> false
        assertFalse(desc.equals(new PolicyTypeDescription("Other Description")));
    }
}
