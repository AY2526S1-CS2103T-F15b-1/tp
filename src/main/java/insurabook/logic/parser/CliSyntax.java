package insurabook.logic.parser;

/**
 * Contains Command Line Interface (CLI) syntax definitions common to multiple commands
 */
public class CliSyntax {

    /* Prefix definitions */
    public static final Prefix PREFIX_NAME = new Prefix("n/");
    public static final Prefix PREFIX_PHONE = new Prefix("p/");
    public static final Prefix PREFIX_EMAIL = new Prefix("e/");
    public static final Prefix PREFIX_ADDRESS = new Prefix("a/");
    public static final Prefix PREFIX_TAG = new Prefix("t/");

    public static final Prefix PREFIX_CLIENT = new Prefix("-c ");
    public static final Prefix PREFIX_POLICY = new Prefix("-p ");
    public static final Prefix PREFIX_POLICYTYPE = new Prefix("-pt ");
    // public static final Prefix PREFIX_CLAIM = new Prefix("-cl ");
    public static final Prefix PREFIX_DATE = new Prefix("-d ");
    public static final Prefix PREFIX_AMOUNT = new Prefix("-amt ");
    public static final Prefix PREFIX_DESCRIPTION = new Prefix("-m ");
}
