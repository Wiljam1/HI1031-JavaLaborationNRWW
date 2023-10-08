package db;

public enum Authorization {
    ADMIN, STAFF, USER, UNAUTHORIZED;

    @Override
    public String toString() {
        return this.name().toLowerCase();
    }
}


