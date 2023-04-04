package lab14_inheritance;

public class Administrator extends Staff {
    private final String title;

    public Administrator(String name, int age, String school, double pay, String title) {
        super(name, age, school, pay);
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public String format() {
        return String.format("%s, %s", super.format(), title);
    }
}
