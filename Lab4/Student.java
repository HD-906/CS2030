class Student extends KeyableMap<Course> {

    public Student(String name) {
        super(name);
    }

    private Student(Student student, Course course) {
        super(student, course);
    }

    @Override
    public Student put(Course course) {
        return new Student(this, course);
    }
}
