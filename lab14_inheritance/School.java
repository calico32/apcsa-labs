package lab14_inheritance;

public class School {
    public static void main(String[] args) {
        var lines = new String[]{
            new Person("John", 20).format(),
            new Student("Jane", 21, 12, "Computer Science").format(),
            new Staff("Joe", 40, "MIT", 100000).format(),
            new Administrator("Jill", 35, "MIT", 100000, "Dean").format(),
            new Teacher("Jill", 35, "MIT", 100000, "Biology", 123).format(),
        };

        for (var line : lines) {
            System.out.println(line);
        }
    }
}
