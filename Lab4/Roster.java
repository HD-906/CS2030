class Roster extends KeyableMap<Student> {
    private static final String NO_RECORD_FORMAT = "No such record: %s %s %s";

    public Roster(String name) {
        super(name);
    }

    private Roster(Roster roster, Student student) {
        super(roster, student);
    }

    public String getGrade(String student, String course, String assessment) {
        return this.get(student)
        .flatMap(x -> x.get(course))
        .flatMap(x -> x.get(assessment))
        .map(x -> x.getGrade())
        .orElse(String.format(NO_RECORD_FORMAT, student, course, assessment));
    }

    public Roster add(String nStudent, String nCourse, String nAssessment, String grade) {
        Student student = super.get(nStudent).orElse(new Student(nStudent));
        Course course = student.get(nCourse).orElse(new Course(nCourse));
        return this.put(student.put(course.put(new Assessment(nAssessment, grade))));
    }

    @Override
    public Roster put(Student student) {
        return new Roster(this, student);
    }
}
