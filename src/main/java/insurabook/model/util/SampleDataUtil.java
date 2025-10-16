package insurabook.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import insurabook.model.InsuraBook;
import insurabook.model.ReadOnlyInsuraBook;
import insurabook.model.client.Client;
import insurabook.model.client.ClientId;
import insurabook.model.client.Name;
import insurabook.model.policytype.PolicyType;
import insurabook.model.policytype.PolicyTypeDescription;
import insurabook.model.policytype.PolicyTypeId;
import insurabook.model.policytype.PolicyTypeName;
import insurabook.model.policytype.PolicyTypePremium;
import insurabook.model.tag.Tag;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {
    public static Client[] getSamplePersons() {
        return new Client[] {
            new Client(new Name("Alex Yeoh"), new ClientId("1")),
            new Client(new Name("Bernice Yu"), new ClientId("2")),
            new Client(new Name("Charlotte Oliveiro"), new ClientId("3")),
            new Client(new Name("David Li"), new ClientId("4")),
            new Client(new Name("Irfan Ibrahim"), new ClientId("5")),
            new Client(new Name("Roy Balakrishnan"), new ClientId("6"))
        };
    }

    public static PolicyType[] getSamplePolicyTypes() {
        return new PolicyType[] {
            new PolicyType(new PolicyTypeName("Policy A"), new PolicyTypeId("P01"),
                    new PolicyTypeDescription("Description for Policy A"),
                    new PolicyTypePremium("100")),
            new PolicyType(new PolicyTypeName("Policy B"), new PolicyTypeId("P02"),
                    new PolicyTypeDescription("Description for Policy B"),
                    new PolicyTypePremium("200")),
            new PolicyType(new PolicyTypeName("Policy C"), new PolicyTypeId("P03"),
                    new PolicyTypeDescription("Description for Policy C"),
                    new PolicyTypePremium("300")),
            new PolicyType(new PolicyTypeName("Policy D"), new PolicyTypeId("P04"),
                    new PolicyTypeDescription("Description for Policy D"),
                    new PolicyTypePremium("400")),
            new PolicyType(new PolicyTypeName("Policy E"), new PolicyTypeId("P05"),
                    new PolicyTypeDescription("Description for Policy E"),
                    new PolicyTypePremium("500"))
        };
    }

    public static ReadOnlyInsuraBook getSamepleInsuraBook() {
        InsuraBook sampleAb = new InsuraBook();
        for (Client sampleClient : getSamplePersons()) {
            sampleAb.addClient(sampleClient);
        }
        for (PolicyType samplePolicyType : getSamplePolicyTypes()) {
            sampleAb.addPolicyType(samplePolicyType);
        }
        return sampleAb;
    }

    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Tag> getTagSet(String... strings) {
        return Arrays.stream(strings)
                .map(Tag::new)
                .collect(Collectors.toSet());
    }

}
