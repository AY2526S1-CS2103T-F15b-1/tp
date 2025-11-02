package insurabook.testutil;

import java.util.List;

import insurabook.model.policytype.PolicyType;

/**
 * A class containing PolicyTypes to be used in testing.
 */
public class TypicalPolicyTypes {

    // Policy types without description or premium.
    public static final PolicyType PRU_ONE = new PolicyTypeBuilder()
            .withName("PRUOne")
            .withId("PRU001")
            .withoutDescription()
            .withoutPremium()
            .build();
    public static final PolicyType PRU_TWO = new PolicyTypeBuilder()
            .withName("PRUTwo")
            .withId("PRU002")
            .withoutDescription()
            .withoutPremium()
            .build();
    public static final PolicyType PRU_THREE = new PolicyTypeBuilder()
            .withName("PRUThree")
            .withId("PRU003")
            .withoutDescription()
            .withoutPremium()
            .build();

    // Policy types with only description and no premium.
    public static final PolicyType SING_ONE = new PolicyTypeBuilder()
            .withName("SINGOne")
            .withId("SNG001")
            .withDescription("Description of SingOne.")
            .withoutPremium()
            .build();
    public static final PolicyType SING_TWO = new PolicyTypeBuilder()
            .withName("SINGTwo")
            .withId("SNG002")
            .withDescription("Description of SingTwo.")
            .withoutPremium()
            .build();

    // Policy types with only premium and no description.
    public static final PolicyType AIA_ONE = new PolicyTypeBuilder()
            .withName("AIAOne")
            .withId("AIA001")
            .withoutDescription()
            .withPremium("100")
            .build();
    public static final PolicyType AIA_TWO = new PolicyTypeBuilder()
            .withName("AIATwo")
            .withId("AIA002")
            .withoutDescription()
            .withPremium("200")
            .build();

    // Policy types with both premium and description.
    public static final PolicyType FWD_ONE = new PolicyTypeBuilder()
            .withName("FWDOne")
            .withId("FWD001")
            .withDescription("Description of FWDOne.")
            .withPremium("1000")
            .build();
    public static final PolicyType FWD_TWO = new PolicyTypeBuilder()
            .withName("FWDTwo")
            .withId("FWD002")
            .withDescription("Description of FWDTwo.")
            .withPremium("2000")
            .build();

    /**
     * Returns a list of typical PolicyTypes.
     */
    public static List<PolicyType> getTypicalPolicyTypes() {
        return List.of(PRU_ONE, PRU_TWO, SING_ONE, AIA_ONE, FWD_ONE, FWD_TWO);
    }
}
