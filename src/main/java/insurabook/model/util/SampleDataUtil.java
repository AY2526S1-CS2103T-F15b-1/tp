package insurabook.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import insurabook.model.InsuraBook;
import insurabook.model.ReadOnlyInsuraBook;
import insurabook.model.client.Address;
import insurabook.model.client.Client;
import insurabook.model.client.ClientId;
import insurabook.model.client.Email;
import insurabook.model.client.Name;
import insurabook.model.client.Phone;
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
            new Client(new Name("Alex Yeoh"), new Phone("87438807"), new Email("alexyeoh@example.com"),
                new Address("Blk 30 Geylang Street 29, #06-40"),
                getTagSet("friends"), new ClientId("A1")),
            new Client(new Name("Bernice Yu"), new Phone("99272758"), new Email("berniceyu@example.com"),
                new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18"),
                getTagSet("colleagues", "friends"), new ClientId("B2")),
            new Client(new Name("Charlotte Oliveiro"), new Phone("93210283"), new Email("charlotte@example.com"),
                new Address("Blk 11 Ang Mo Kio Street 74, #11-04"),
                getTagSet("neighbours"), new ClientId("C3")),
            new Client(new Name("David Li"), new Phone("91031282"), new Email("lidavid@example.com"),
                new Address("Blk 436 Serangoon Gardens Street 26, #16-43"),
                getTagSet("family"), new ClientId("D4")),
            new Client(new Name("Irfan Ibrahim"), new Phone("92492021"), new Email("irfan@example.com"),
                new Address("Blk 47 Tampines Street 20, #17-35"),
                getTagSet("classmates"), new ClientId("E5")),
            new Client(new Name("Roy Balakrishnan"), new Phone("92624417"), new Email("royb@example.com"),
                new Address("Blk 45 Aljunied Street 85, #11-31"),
                getTagSet("colleagues"), new ClientId("F6"))
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
