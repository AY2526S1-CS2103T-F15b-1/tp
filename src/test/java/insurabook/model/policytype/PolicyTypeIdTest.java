package insurabook.model.policytype;

import static insurabook.model.policytype.PolicyTypeId.isValidPtId;
import static insurabook.testutil.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class PolicyTypeIdTest {

    @Test
    public void constructor_invalidId_throwsIllegalArgument() {
        String invalidId = "";
        assertThrows(IllegalArgumentException.class, () -> new PolicyTypeId(invalidId));
    }

    @Test
    public void isValidPtId_valid_true() {
        assertTrue(isValidPtId("valid1"));
    }

    @Test
    public void isValidPtId_blank_false() {
        assertFalse(isValidPtId(""));
        assertFalse(isValidPtId("   "));
    }

    @Test
    public void isValidPtId_nonAlphaNumeric_false() {
        assertFalse(isValidPtId("!"));
    }

    @Test
    public void isValidPtId_startWithDash_false() {
        assertFalse(isValidPtId("-id"));
    }

    @Test
    public void equals() {
        String validId = "ValidId";
        PolicyTypeId id = new PolicyTypeId(validId);

        // same object -> true
        assertTrue(id.equals(id));

        // null -> false
        assertFalse(id.equals(null));

        // same id string -> true
        assertTrue(id.equals(new PolicyTypeId(validId)));

        // different Id string -> false
        assertFalse(id.equals(new PolicyTypeId("DifferentValidId")));
    }

}
