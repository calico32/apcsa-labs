package lab02_number_cubes;

public class GameUI {
  Game game;
  PlayState state;

  public GameUI() { state = PlayState.TITLE; }

  public void startGame() {
    state = PlayState.PLAYING;
    game  = new Game();
  }

  public void endGame() { state = PlayState.GAME_OVER; }

  public void reset() {
    state = PlayState.TITLE;
    game  = null;
  }
}
