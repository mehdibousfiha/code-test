package se.kry.codetest.model;


public enum Status {
    OK("OK"), KO("KO"), NOD("NOD");
    private final String value;

    Status(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
