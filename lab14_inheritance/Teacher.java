package lab14_inheritance;


public class Teacher extends Staff {
    private final String subject;
    private final int room;

    public Teacher(String name, int age, String school, double pay, String subject, int room) {
        super(name, age, school, pay);
        this.subject = subject;
        this.room = room;
    }

    public String getSubject() {
        return subject;
    }

    public int getRoom() {
        return room;
    }

    @Override
    public String format() {
        return String.format("%s, %s, %d", super.format(), subject, room);
    }
}

