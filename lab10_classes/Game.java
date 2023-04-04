
package lab10_classes;

public class Game {
    public Partner partner;
    public Board board;

    public Game() {
        partner = new Partner();
        board = new Board();
    }
}
