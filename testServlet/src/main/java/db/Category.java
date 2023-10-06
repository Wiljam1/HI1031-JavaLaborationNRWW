package db;

public enum Category {
    SODA, CHIPS;

    @Override
    public String toString() {
        return this.name().toLowerCase();
    }
}
