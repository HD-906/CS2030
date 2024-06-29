class Course extends KeyableMap<Assessment> {

    public Course(String name) {
        super(name);
    }

    private Course(Course course, Assessment assessment) {
        super(course, assessment);
    }

    @Override
    public Course put(Assessment assessment) {
        return new Course(this, assessment);
    }
}
