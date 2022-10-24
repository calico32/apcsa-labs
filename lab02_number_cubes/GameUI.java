package lab02_number_cubes;

import java.io.IOException;

import shared.Console;
import shared.TextBuilder;
import shared.TextHelpers;
import shared.TextSegment;
import shared.Util;

public class GameUI extends TextHelpers {
  void println(String text) { System.out.println(text); }
  void println() { System.out.println(); }

  enum UIState { TITLE, PLAYING, GAME_OVER }
  enum GameState { START, ROLLING, ROLL_MENU, SELECTING_DICE, SELECTING_CATEGORY }

  public static final String logo =
    "│.│_ .__    _. _│__│_ _\n│││_)|(/_\\/(_│(_| ||__>\n         /";

  Hand hand;
  Scoresheet scoresheet;
  int selected = 0;

  UIState uiState     = UIState.TITLE;
  GameState gameState = GameState.START;

  void changeState(UIState uiState) {
    Console.clear();
    this.uiState = uiState;
  }

  void changeState(GameState gameState) {
    Console.clear();
    this.gameState = gameState;
  }

  public GameUI() {}

  public void startGame() {
    Console.clear();
    uiState   = UIState.PLAYING;
    gameState = GameState.START;

    hand       = new Hand(Game.DICE_COUNT);
    scoresheet = new Scoresheet();
  }

  public void endGame() { changeState(UIState.GAME_OVER); }

  public void reset() {
    changeState(UIState.TITLE);
    scoresheet = null;
  }

  enum TitleMenuOptions {
    START_GAME("New Game"),
    QUIT("Quit");

    public final String text;
    TitleMenuOptions(String text) { this.text = text; }
  }

  enum RollMenuOptions {
    ROLL("Select and roll again"),
    SCORE("Score roll");

    public final String text;
    RollMenuOptions(String text) { this.text = text; }
  }

  public void main() throws IOException, InterruptedException {
    Console.init();
    Console.hideCursor();

    changeState(UIState.TITLE);

    try {
      draw();
      while (true) {
        int[] input = Console.next();

        boolean shouldExit = handleInput(input);
        if (shouldExit) {
          break;
        }
        Console.moveCursor(0, 0);
        draw();
      }

    } catch (Exception e) {
      e.printStackTrace();
    }

    Console.showCursor();
    Console.reset();
  }

  public void draw() {
    switch (uiState) {
      case TITLE: drawTitle(); break;
      case PLAYING: drawPlaying(); break;
      case GAME_OVER: drawGameOver(); break;
    }
  }

  void drawTitle() {
    println();
    println(Util.indent(GameUI.logo, 10));
    println();
    println("Welcome to LibreYachts!");
    println();
    for (TitleMenuOptions option : TitleMenuOptions.values()) {
      TextSegment t = text(option.text);
      if (selected == option.ordinal()) {
        TextBuilder.println(text("> ").blue(), t.blue());
      } else {
        TextBuilder.println(text("  "), t);
      }
    }
  }

  void drawPlaying() {
    println(scoresheet.draw());
    switch (gameState) {
      case START: println("Press any key to roll the dice."); break;
      case ROLLING: drawRolling(); break;
      case ROLL_MENU: drawRollMenu(); break;
      case SELECTING_DICE:
        println("Select dice to keep or press any key to roll again.");
        break;
      case SELECTING_CATEGORY:
        println("Select a category to score or press any key to roll again.");
        break;
    }
  }

  void drawRolling() {
    println("Rolling...");
    println();
    println(hand.draw());
  }

  void drawRollMenu() {
    println(hand.draw());
    println();
    println("Select an option:");
    for (RollMenuOptions option : RollMenuOptions.values()) {
      TextSegment t = text(option.text);
      if (selected == option.ordinal()) {
        TextBuilder.println(text("> ").blue(), t.blue());
      } else {
        TextBuilder.println(text("  "), t);
      }
    }
  }

  void drawGameOver() {
    println();
    println(Util.indent(GameUI.logo, 10));
    println();
    println("Game Over!");
    println();
    println("Press any key to continue...");
  }

  boolean handleInput(int[] input) {
    switch (uiState) {
      case TITLE: return handleTitleInput(input);
      case PLAYING:
        switch (gameState) {
          case START: return handleStartInput(input);
          case ROLLING: return false; // ignore input
          case ROLL_MENU: return handleRollMenuInput(input);
          case SELECTING_DICE:
            return handleSelectingDiceInput(input);
            // case SELECTING_CATEGORY: return handleSelectingCategoryInput(input);
        }
    }

    return false;
  }

  boolean handleTitleInput(int[] input) {
    Console.KeyCode key = Console.KeyCode.fromBytes(input);

    if (key != null) {
      switch (key) {
        case ARROW_UP: selected = Math.max(selected - 1, 0); break;
        case ARROW_DOWN:
          selected = Math.min(selected + 1, TitleMenuOptions.values().length - 1);
          break;
        case ENTER:
          switch (TitleMenuOptions.values()[selected]) {
            case START_GAME: startGame(); break;
            case QUIT: return true;
          }
          break;
        default: break;
      }
    } else {
      switch (input[0]) {
        case 'q': return true;
        default: break;
      }
    }

    return false;
  }

  boolean handleStartInput(int[] input) {
    Console.KeyCode key = Console.KeyCode.fromBytes(input);
    if (key != null) {
      switch (key) {
        case ENTER:
          changeState(GameState.ROLLING);
          draw();
          hand.roll((cube, index) -> {
            Console.moveCursor(0, 0);
            draw();
          }, 750);
          changeState(GameState.ROLL_MENU);
          break;
        default: break;
      }
    }

    return false;
  }

  boolean handleRollMenuInput(int[] input) {
    Console.KeyCode key = Console.KeyCode.fromBytes(input);
    if (key != null) {
      switch (key) {
        case ARROW_UP: selected = Math.max(selected - 1, 0); break;
        case ARROW_DOWN:
          selected = Math.min(selected + 1, RollMenuOptions.values().length - 1);
          break;
        case ENTER:
          switch (RollMenuOptions.values()[selected]) {
            case ROLL: changeState(GameState.SELECTING_DICE); break;
            case SCORE: changeState(GameState.SELECTING_CATEGORY); break;
          }
          break;
        default: break;
      }
    }

    return false;
  }

  boolean handleSelectingDiceInput(int[] input) {
    Console.KeyCode key = Console.KeyCode.fromBytes(input);
    if (key != null) {
      switch (key) {
        case ARROW_LEFT: selected = Math.max(selected - 1, 0); break;
        case ARROW_RIGHT: selected = Math.min(selected + 1, Game.DICE_COUNT - 1); break;
        case SPACE: hand.toggle(selected); break;
        case ENTER: changeState(GameState.ROLLING); break;
        default: break;
      }
    }

    return false;
  }

  public static void main(String[] args) throws IOException, InterruptedException {
    GameUI ui = new GameUI();
    ui.main();
  }
}
