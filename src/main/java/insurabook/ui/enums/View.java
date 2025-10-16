package insurabook.ui.enums;

/**
 * This enum represents the different Views for MainWindow (e.g. Client, Policy).
 */
public enum View {
    CLIENT_VIEW,
    POLICY_VIEW;

    @Override
    public String toString() {
        return super.name();
    }
}
