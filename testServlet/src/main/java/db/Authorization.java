package db;

public enum Authorization {
    ADMIN, STAFF, USER;

    @Override
    public String toString() {
        return this.name().toLowerCase();
    }
}


