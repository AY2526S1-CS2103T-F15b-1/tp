package insurabook.model.policytype;

import static insurabook.testutil.TypicalPolicyTypes.FWD_ONE;
import static insurabook.testutil.TypicalPolicyTypes.FWD_TWO;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import insurabook.model.policytype.enums.PolicyTypeEquality;
import insurabook.testutil.PolicyTypeBuilder;

public class PolicyTypeTest {

    @Test
    public void equals() {
        // same object -> true
        assertTrue(FWD_ONE.equals(FWD_ONE));

        // null -> false
        assertFalse(FWD_ONE.equals(null));

        // same name and id, rest different -> true
        PolicyType fwdOneEdited = new PolicyTypeBuilder(FWD_ONE)
                .withDescription("Different description.")
                .withoutPremium()
                .build();
        assertTrue(FWD_ONE.equals(fwdOneEdited));

        // different name, rest same -> true
        PolicyType fwdOneDiffName = new PolicyTypeBuilder(FWD_ONE)
                .withName("FWDDiff")
                .build();
        assertTrue(FWD_ONE.equals(fwdOneDiffName));

        // different id, rest same -> true
        PolicyType fwdOneDiffId = new PolicyTypeBuilder(FWD_ONE)
                .withId("FWD000")
                .build();
        assertTrue(FWD_ONE.equals(fwdOneDiffId));

        // both id and name different, rest same -> false
        PolicyType fwdOneDiffBoth = new PolicyTypeBuilder(FWD_ONE)
                .withName("FWDDiff")
                .withId("FWD000")
                .build();
        assertFalse(FWD_ONE.equals(fwdOneDiffBoth));
    }

    @Test
    public void policyTypeEqualsTest() {
        // both fields same -> BOTH_EQUAL
        assertEquals(PolicyTypeEquality.BOTH_EQUAL,
                FWD_ONE.policyTypeEquals(FWD_ONE.getPtName(), FWD_ONE.getPtId()));

        // only name equal -> NAME_EQUAL
        assertEquals(PolicyTypeEquality.NAME_EQUAL,
                FWD_ONE.policyTypeEquals(FWD_ONE.getPtName(), FWD_TWO.getPtId()));

        // only id equal -> ID_EQUAL
        assertEquals(PolicyTypeEquality.ID_EQUAL,
                FWD_ONE.policyTypeEquals(FWD_TWO.getPtName(), FWD_ONE.getPtId()));

        // neither equal -> NEITHER_EQUAL
        assertEquals(PolicyTypeEquality.NEITHER_EQUAL,
                FWD_ONE.policyTypeEquals(FWD_TWO.getPtName(), FWD_TWO.getPtId()));
    }

}
