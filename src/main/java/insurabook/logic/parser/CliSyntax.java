package insurabook.logic.parser;

/**
 * Contains Command Line Interface (CLI) syntax definitions common to multiple commands
 */
public class CliSyntax {

    /* Prefix definitions */
    public static final Prefix PREFIX_PHONE = new Prefix("-phone");
    public static final Prefix PREFIX_EMAIL = new Prefix("-email");
    public static final Prefix PREFIX_ADDRESS = new Prefix("-address");
    public static final Prefix PREFIX_TAG = new Prefix("-tag");

    /* InsuraBook specific prefixes */
    public static final Prefix PREFIX_POLICY_ID = new Prefix("-p_id");
    public static final Prefix PREFIX_CLIENT_ID = new Prefix("-c_id");
    public static final Prefix PREFIX_CLIENT_NAME = new Prefix("-n");
    public static final Prefix PREFIX_DESCRIPTION = new Prefix("-d");
    public static final Prefix PREFIX_POLICY_TYPE = new Prefix("-pt");
    public static final Prefix PREFIX_POLICY_TYPE_ID = new Prefix("-pt_id");
    public static final Prefix PREFIX_PREMIUM = new Prefix("-pr");
    public static final Prefix PREFIX_EXPIRY_DATE = new Prefix("-exp");
    public static final Prefix PREFIX_CLAIM_ID = new Prefix("-cl_id");
    public static final Prefix PREFIX_CLAIM_AMOUNT = new Prefix("-amt");
    public static final Prefix PREFIX_CLAIM_DATE = new Prefix("-date");


}
