package lab14_inheritance;


public class Staff extends Person {
    private final String school;
    private final double pay;

    public Staff(String name, int age, String school, double pay) {
        super(name, age);
        this.school = school;
        this.pay = pay;
    }

    public String getSchool() {
        return school;
    }

    public double getPay() {
        return pay;
    }

    @Override
    public String format() {
        return String.format("%s, %s, $%.2f", super.format(), school, pay);
    }
}

