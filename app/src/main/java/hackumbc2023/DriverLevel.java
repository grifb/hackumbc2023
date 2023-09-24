package hackumbc2023;

public enum DriverLevel {
    MOTORCOACH(4),
    CHARTER(3),

    CDL(2),
    DEMANDRES(1);

    private final int levelVal;

    private DriverLevel(int levelVal) {
        this.levelVal = levelVal;
    }

    public int getValue() {
        return levelVal;
    }
}
