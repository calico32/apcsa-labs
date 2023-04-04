package lab02_number_cubes;

public class LibreYachts {
    public static void main(String[] args) {
        Game game = new Game();
        try {
            game.run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
