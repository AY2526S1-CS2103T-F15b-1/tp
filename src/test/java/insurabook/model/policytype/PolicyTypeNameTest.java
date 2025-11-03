package insurabook.model.policytype;

import static insurabook.model.policytype.PolicyTypeName.isValidPtName;
import static insurabook.testutil.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class PolicyTypeNameTest {

    @Test
    public void constructor_null_throwsNullPointer() {
        assertThrows(NullPointerException.class, () -> new PolicyTypeName(null));
    }

    @Test
    public void constructor_invalidName_throwsIllegalArgument() {
        String invalidName = "";
        assertThrows(IllegalArgumentException.class, () -> new PolicyTypeName(invalidName));
    }

    @Test
    public void validPtName_valid_true() {
        assertTrue(isValidPtName("Valid Name123!"));
    }

    @Test
    public void validPtName_blank_false() {
        assertFalse(isValidPtName(""));
        assertFalse(isValidPtName("   "));
    }

    @Test
    public void validPtName_nonAscii_false() {
        assertFalse(isValidPtName("¥"));
    }

    @Test
    public void validPtName_startWithDash_false() {
        assertFalse(isValidPtName("-Name"));
    }

    @Test
    public void equals() {
        String validName = "Valid Name";
        PolicyTypeName name = new PolicyTypeName(validName);

        // same object -> true
        assertTrue(name.equals(name));

        // null -> false
        assertFalse(name.equals(null));

        // same name string -> true
        assertTrue(name.equals(new PolicyTypeName(validName)));

        // different name string -> false
        assertFalse(name.equals(new PolicyTypeName("Different Valid Name")));
    }
}
