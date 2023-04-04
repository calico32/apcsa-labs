package lab14_inheritance;


public class Student extends Person {
    private final int grade;
    private final String major;

    public Student(String name, int age, int grade, String major) {
        super(name, age);
        this.grade = grade;
        this.major = major;
    }

    public int getGrade() {
        return grade;
    }

    public String getMajor() {
        return major;
    }

    @Override
    public String format() {
        return String.format("%s, Grade %d, %s", super.format(), grade, major);
    }
}
