package lab02_number_cubes;

public class LibreYachts {
  public static void main(String[] args) {
    GameUI ui = new GameUI();
    try {
      ui.main();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
