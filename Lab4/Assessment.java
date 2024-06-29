class Assessment implements Keyable {
    private static final String FORMAT = "{%s: %s}";
    private final String name;
    private final String grade;

    Assessment(String name, String grade) {
        this.name = name;
        this.grade = grade;
    }

    public String getGrade() {
        return this.grade;
    }

    public String getKey() {
        return this.name;
    }

    @Override
    public String toString() {
        return String.format(FORMAT, this.name, this.grade);
    }
}
