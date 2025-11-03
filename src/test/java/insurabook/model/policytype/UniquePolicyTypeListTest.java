package insurabook.model.policytype;

import static insurabook.testutil.Assert.assertThrows;
import static insurabook.testutil.TypicalPolicyTypes.AIA_ONE;
import static insurabook.testutil.TypicalPolicyTypes.FWD_ONE;
import static insurabook.testutil.TypicalPolicyTypes.PRU_ONE;
import static insurabook.testutil.TypicalPolicyTypes.SING_ONE;
import static insurabook.testutil.TypicalPolicyTypes.SING_TWO;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import insurabook.commons.exceptions.IllegalValueException;
import insurabook.model.policytype.exceptions.PolicyTypeDuplicateException;
import insurabook.model.policytype.exceptions.PolicyTypeMissingException;
import insurabook.testutil.PolicyTypeBuilder;

public class UniquePolicyTypeListTest {

    private final UniquePolicyTypeList policyTypes = new UniquePolicyTypeList();

    @Test
    public void add_null_throwsNullPointer() {
        assertThrows(NullPointerException.class, () -> policyTypes.add(null));
    }

    @Test
    public void add_duplicate_throwsPolicyTypeDuplicate() {
        policyTypes.setPolicyTypes(List.of(SING_ONE, SING_TWO));

        assertThrows(PolicyTypeDuplicateException.class, () -> policyTypes.add(SING_ONE));
    }

    @Test
    public void checkDuplicate_duplicate_throwsPolicyTypeDuplicate() {
        policyTypes.setPolicyTypes(List.of(SING_ONE, SING_TWO, AIA_ONE));

        assertThrows(PolicyTypeDuplicateException.class, () -> policyTypes.checkDuplicate(SING_TWO));
    }

    @Test
    public void removeIndex_invalidIndex_throwsIllegalValue() {
        policyTypes.setPolicyTypes(List.of(SING_ONE, SING_TWO, AIA_ONE));

        // negative index
        assertThrows(IllegalValueException.class, () -> policyTypes.remove(-1));

        // above list length index
        assertThrows(IllegalValueException.class, () -> policyTypes.remove(3));
    }

    @Test
    public void removeNameId_bothMatch_successfulRemove() {
        policyTypes.setPolicyTypes(List.of(SING_ONE, SING_TWO, AIA_ONE, FWD_ONE));

        policyTypes.remove(SING_TWO.getPtName(), SING_TWO.getPtId());

        assertFalse(policyTypes.containsName(SING_TWO.getPtName()));
        assertFalse(policyTypes.containsId(SING_TWO.getPtId()));
    }

    @Test
    public void removeNameId_oneMatch_returnIndexList() {
        policyTypes.setPolicyTypes(List.of(SING_ONE, SING_TWO, AIA_ONE, FWD_ONE));

        assertEquals(List.of(1, 2), policyTypes.remove(SING_TWO.getPtName(), AIA_ONE.getPtId()));
    }

    @Test
    public void removeNameId_noneMatch_throwsPolicyTypeMissing() {
        policyTypes.setPolicyTypes(List.of(SING_ONE, SING_TWO, AIA_ONE, FWD_ONE));

        assertThrows(PolicyTypeMissingException.class, () ->
                policyTypes.remove(PRU_ONE.getPtName(), PRU_ONE.getPtId()));
    }

    @Test
    public void setPolicyTypes_null_throwsNullPointer() {
        UniquePolicyTypeList nullPtList = null;
        List<PolicyType> nullList = null;

        assertThrows(NullPointerException.class, () -> policyTypes.setPolicyTypes(nullPtList));
        assertThrows(NullPointerException.class, () -> policyTypes.setPolicyTypes(nullList));
    }

    @Test
    public void setPolicyType_null_throwsNullPointer() {
        assertThrows(NullPointerException.class, () -> policyTypes.setPolicyType(null, SING_TWO));
        assertThrows(NullPointerException.class, () -> policyTypes.setPolicyType(SING_ONE, null));
    }

    @Test
    public void setPolicyType_targetNotFound_throwsPolicyTypeMissing() {
        policyTypes.setPolicyTypes(List.of(SING_ONE, SING_TWO));

        assertThrows(PolicyTypeMissingException.class, () -> policyTypes.setPolicyType(PRU_ONE, PRU_ONE));
    }

    @Test
    public void setPolicyType_changeToDuplicate_throwsPolicyTypeDuplicate() {
        policyTypes.setPolicyTypes(List.of(SING_ONE, SING_TWO, AIA_ONE));

        PolicyType sameIdSingTwo = new PolicyTypeBuilder(SING_TWO)
                .withName("Modified Name")
                .build();
        PolicyType sameNameSingTwo = new PolicyTypeBuilder(SING_TWO)
                .withId("Modified-Id")
                .build();

        assertThrows(PolicyTypeDuplicateException.class, () -> policyTypes.setPolicyType(SING_ONE, sameIdSingTwo));
        assertThrows(PolicyTypeDuplicateException.class, () -> policyTypes.setPolicyType(SING_ONE, sameNameSingTwo));
    }

    @Test
    public void setPolicyType_valid_successfulChange() {
        policyTypes.setPolicyTypes(List.of(SING_ONE, SING_TWO, AIA_ONE));

        PolicyType sameIdSingTwo = new PolicyTypeBuilder(SING_TWO)
                .withName("Modified Name")
                .build();
        PolicyType sameNameSingTwo = new PolicyTypeBuilder(SING_TWO)
                .withId("Modified-Id")
                .build();

        policyTypes.setPolicyType(SING_TWO, sameIdSingTwo);
        assertTrue(policyTypes.containsName(sameIdSingTwo.getPtName()));

        policyTypes.setPolicyType(sameIdSingTwo, sameNameSingTwo);
        assertTrue(policyTypes.containsId(sameNameSingTwo.getPtId()));
    }

    @Test
    public void get_invalidIndex_throwsIllegalValue() {
        policyTypes.setPolicyTypes(List.of(SING_ONE, SING_TWO, AIA_ONE));

        // negative index
        assertThrows(IllegalValueException.class, () -> policyTypes.get(-1));

        // above list length index
        assertThrows(IllegalValueException.class, () -> policyTypes.get(3));
    }

    @Test
    public void findMatching_null_throwsNullPointer() {
        policyTypes.setPolicyTypes(List.of(SING_ONE));

        assertThrows(NullPointerException.class, () -> policyTypes.findMatching(null, SING_ONE.getPtId()));
        assertThrows(NullPointerException.class, () -> policyTypes.findMatching(SING_ONE.getPtName(), null));
    }

    @Test
    public void findMatching_valid_returnsCorrectIndices() {
        policyTypes.setPolicyTypes(List.of(SING_ONE, SING_TWO, AIA_ONE));

        // none matching
        assertEquals(List.of(), policyTypes.findMatching(PRU_ONE.getPtName(), PRU_ONE.getPtId()));

        // some matching
        assertEquals(List.of(0), policyTypes.findMatching(PRU_ONE.getPtName(), SING_ONE.getPtId()));
        assertEquals(List.of(1), policyTypes.findMatching(SING_TWO.getPtName(), PRU_ONE.getPtId()));
        assertEquals(List.of(0, 1), policyTypes.findMatching(SING_TWO.getPtName(), SING_ONE.getPtId()));
    }

    @Test
    public void containsName_null_throwsNullPointer() {
        assertThrows(NullPointerException.class, () -> policyTypes.containsName(null));
    }

    @Test
    public void containsName() {
        policyTypes.setPolicyTypes(List.of(SING_ONE, SING_TWO, AIA_ONE));

        assertFalse(policyTypes.containsName(PRU_ONE.getPtName()));
        assertTrue(policyTypes.containsName(SING_ONE.getPtName()));
    }

    @Test
    public void containsId_null_throwsNullPointer() {
        assertThrows(NullPointerException.class, () -> policyTypes.containsId(null));
    }

    @Test
    public void containsId() {
        policyTypes.setPolicyTypes(List.of(SING_ONE, SING_TWO, AIA_ONE));

        assertFalse(policyTypes.containsId(PRU_ONE.getPtId()));
        assertTrue(policyTypes.containsId(SING_ONE.getPtId()));
    }

    @Test
    public void equals() {
        policyTypes.setPolicyTypes(List.of(SING_ONE, SING_TWO, AIA_ONE));

        // null -> false
        assertFalse(policyTypes.equals(null));

        // same object -> true
        assertTrue(policyTypes.equals(policyTypes));

        // different values -> false
        UniquePolicyTypeList diffValues = new UniquePolicyTypeList();
        diffValues.setPolicyTypes(List.of(SING_ONE, SING_TWO, PRU_ONE));
        assertFalse(policyTypes.equals(diffValues));

        // same values -> also false (not same internalList)
        UniquePolicyTypeList sameValues = new UniquePolicyTypeList();
        sameValues.setPolicyTypes(List.of(SING_ONE, SING_TWO, AIA_ONE));
        assertFalse(policyTypes.equals(sameValues));
    }

    @Test
    public void getPolicyType_null_throwsNullPointer() {
        PolicyTypeName nullName = null;
        PolicyTypeId nullId = null;

        assertThrows(NullPointerException.class, () -> policyTypes.getPolicyType(nullName));
        assertThrows(NullPointerException.class, () -> policyTypes.getPolicyType(nullId));
    }

    @Test
    public void getPolicyType() {
        policyTypes.setPolicyTypes(List.of(SING_ONE, SING_TWO, AIA_ONE));

        // by name
        assertEquals(SING_TWO, policyTypes.getPolicyType(SING_TWO.getPtName()));
        assertThrows(PolicyTypeMissingException.class, () -> policyTypes.getPolicyType(PRU_ONE.getPtName()));

        // by id
        assertEquals(SING_TWO, policyTypes.getPolicyType(SING_TWO.getPtId()));
        assertThrows(PolicyTypeMissingException.class, () -> policyTypes.getPolicyType(PRU_ONE.getPtId()));
    }

}
