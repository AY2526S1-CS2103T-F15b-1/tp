package insuraBook.model;

import java.util.ArrayList;
import java.util.List;

import insuraBook.exceptions.ptDuplicateError;
import insuraBook.exceptions.ptMissingError;

public class PolicyType {

    private final String pt_name;
    private final int pt_id;
    private String pt_description = "";
    private int pt_premium = 0;

    private static List<PolicyType> recorded_policyTypes = new ArrayList<>();

    public PolicyType(String pt_name, int pt_id) throws ptDuplicateError {
        checkIfDuplicate(pt_name, pt_id);
        this.pt_name = pt_name;
        this.pt_id = pt_id;
        recorded_policyTypes.add(this);
    }

    public PolicyType(String pt_name, int pt_id, String pt_description, int pt_premium) throws ptDuplicateError {
        checkIfDuplicate(pt_name, pt_id);
        this.pt_name = pt_name;
        this.pt_id = pt_id;
        this.pt_description = pt_description;
        this.pt_premium = pt_premium;
        recorded_policyTypes.add(this);
    }

    public String getPt_name() {
        return this.pt_name;
    }

    public int getPt_id() {
        return this.pt_id;
    }

    public String getPt_description() {
        return this.pt_description;
    }

    public int getPt_premium() {
        return this.pt_premium;
    }

    private static void checkIfDuplicate(String pt_name, int pt_id) throws ptDuplicateError {
        for (PolicyType pt2 : recorded_policyTypes) {
            if (isEqual(pt_name, pt_id, pt2)) {
                throw new ptDuplicateError(pt2);
            }
        }
    }

    public static void deletePolicyType(String pt_name, int pt_id) throws ptMissingError {
        boolean found = false;
        for (PolicyType pt2 : recorded_policyTypes) {
            if (isEqual(pt_name, pt_id, pt2)) {
                found = true;
                recorded_policyTypes.remove(pt2);
                break;
            }
        }
        if (!found) {
            throw new ptMissingError(pt_name, pt_id);
        }
    }

    private static boolean isEqual(String pt1_name, int pt1_id, PolicyType policyType2) {
        return pt1_name.equals(policyType2.getPt_name()) && (pt1_id == policyType2.getPt_id());
    }

}
