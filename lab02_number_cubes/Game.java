package lab02_number_cubes;

import java.io.IOException;
import java.util.function.Consumer;

import shared.Console;
import shared.TextBuilder;
import shared.TextHelpers;
import shared.TextSegment;
import shared.Util;

import lab02_number_cubes.roll_category.RollCategory;

public class Game extends TextHelpers {
  public static final int MAX_ROLLS  = 3;
  public static final int DICE_COUNT = 5;
  public static final int DICE_SIDES = 6;
  public static final int ROLL_DELAY = 400;

  static void println(String text) { System.out.println(text); }
  static void println(TextSegment... text) { TextBuilder.println(text); }
  static void println() { System.out.println(); }

  enum UIState { TITLE, PLAYING, GAME_OVER }
  enum GameState { START, ROLLING, ROLL_MENU, SELECTING_DICE, SELECTING_CATEGORY }

  public static final String logo =
    "│.│_ .__    _. _│__│_ _\n│││_)|(/_\\/(_│(_| ||__>\n         /";

  Hand hand;
  Scoresheet scoresheet;

  int selected  = 0;
  int rollsLeft = Game.MAX_ROLLS;

  boolean shouldExit = false;

  UIState uiState     = UIState.TITLE;
  GameState gameState = GameState.START;

  public Game() {}

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

  void changeState(UIState uiState) {
    Console.clear();
    selected     = 0;
    this.uiState = uiState;
  }

  void changeState(GameState gameState) {
    Console.clear();
    selected       = 0;
    this.gameState = gameState;
    switch (gameState) {
      case ROLL_MENU: scoresheet.select(null); break;
      case SELECTING_CATEGORY:
        scoresheet.select(scoresheet.getAvailableCategories()[0]);
        break;
      case START:
        if (scoresheet.getAvailableCategories().length == 0) {
          changeState(UIState.GAME_OVER);
          endGame();
        }
        break;
      default: break;
    }
  }

  enum TitleMenuOptions {
    START_GAME("New Game", (game) -> game.startGame()),
    QUIT("Quit", (game) -> { game.shouldExit = true; });

    public final String text;
    public final Consumer<Game> action;
    TitleMenuOptions(String text, Consumer<Game> action) {
      this.text   = text;
      this.action = action;
    }
  }

  enum RollMenuOptions {
    ROLL("Select and reroll", (game) -> game.changeState(GameState.SELECTING_DICE)),
    SCORE("Score roll", (game) -> game.changeState(GameState.SELECTING_CATEGORY));

    public final String text;
    public final Consumer<Game> action;
    RollMenuOptions(String text, Consumer<Game> action) {
      this.text   = text;
      this.action = action;
    }
  }

  public void run() throws IOException, InterruptedException {
    Console.init();
    Console.hideCursor();

    changeState(UIState.TITLE);

    try {
      draw();
      while (true) {
        int[] input = Console.next();

        handleInput(input);
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
    println(Util.indent(Game.logo, 10));
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
    switch (gameState) {
      case START:
        println(scoresheet.draw());
        println(text("Press "), text("ENTER").bold(), text(" to roll."));
        println(text("Rolls left: "), text(rollsLeft).bold().blue());
        break;
      case ROLLING: drawRolling(); break;
      case ROLL_MENU: drawRollMenu(); break;
      case SELECTING_DICE: drawSelectingDice(); break;
      case SELECTING_CATEGORY: drawSelectingCategory(); break;
    }
  }

  void drawRolling() {
    println(scoresheet.draw());
    println(hand.draw());
    println();
    println("Rolling...");
  }

  void drawRollMenu() {
    println(scoresheet.draw());
    println(hand.draw());
    println();
    println("Select an option:");
    for (RollMenuOptions option : RollMenuOptions.values()) {
      TextSegment t = text(option.text);
      if (option == RollMenuOptions.ROLL) {
        t.text += " (rolls left: " + rollsLeft + ")";
        if (rollsLeft == 0) {
          t.dim();
          if (selected == option.ordinal()) {
            TextBuilder.println(text("> ").dim(), t.dim());
            continue;
          }
        }
      }

      if (selected == option.ordinal()) {
        TextBuilder.println(text("> ").blue(), t.blue());
      } else {
        TextBuilder.println(text("  "), t);
      }
    }
  }

  void drawSelectingDice() {
    println(scoresheet.draw());
    println(hand.draw(TextSegment.DIM, selected));
    println();
    println(
      text("Select dice to keep with "),
      text("←/→").whiteBright(),
      text(" and "),
      text("SPACE").bold(),
      text(", and press "),
      text("ENTER").bold(),
      text(" to roll.")
    );
    println(text("Press "), text("ESC").bold(), text(" to cancel."));
  }

  void drawSelectingCategory() {
    println(scoresheet.draw(TextSegment.DIM));
    println(hand.draw(TextSegment.WHITE_BRIGHT, null));
    println();
    println(
      text("Select a category to score with "),
      text("↑").whiteBright(),
      text(" and "),
      text("↓").whiteBright(),
      text(" and press "),
      text("ENTER").bold(),
      text(" to score.")
    );
    println(text("Press "), text("ESC").bold(), text(" to cancel."));

    println();

    RollCategory cat = scoresheet.selectedCategory;

    if (cat != null) {
      println(text("Selected: ").bold(), text(cat.name).padRight(30).bold().blue());
      int points = cat.isMatch(hand.dice) ? cat.getScore(hand.dice) : 0;
      println(
        text("Scoring here would give you ").bold(),
        text(points).bold().blue(),
        points == 1 ? text(" point.").padRight(10).bold()
                    : text(" points.").padRight(10).bold()
      );
    }
  }

  void drawGameOver() {
    println(Util.indent(Game.logo, 10));
    println();
    println(scoresheet.draw());
    println();
    println("Game over!");
    println();
    println(text("Press "), text("ENTER").bold(), text(" to play again."));
  }

  void handleInput(int[] input) {
    switch (uiState) {
      case TITLE: handleTitleInput(input); break;
      case PLAYING:
        switch (gameState) {
          case START: handleStartInput(input); break;
          case ROLLING: break; // ignore input
          case ROLL_MENU: handleRollMenuInput(input); break;
          case SELECTING_DICE: handleSelectingDiceInput(input); break;
          case SELECTING_CATEGORY: handleSelectingCategoryInput(input); break;
        }
        break;
      case GAME_OVER: handleGameOverInput(input); break;
    }
  }

  void handleTitleInput(int[] input) {
    Console.KeyCode key = Console.KeyCode.fromBytes(input);

    if (handleMenuNavigation(key, TitleMenuOptions.values()))
      return;

    if (key != null) {

    } else {
      switch (input[0]) {
        case 'q': break;
        default: break;
      }
    }
  }

  boolean handleStartInput(int[] input) {
    Console.KeyCode key = Console.KeyCode.fromBytes(input);
    if (key != null) {
      switch (key) {
        case ENTER:
          rollsLeft--;
          changeState(GameState.ROLLING);
          draw();
          hand.roll((cube, index) -> {
            Console.moveCursor(0, 0);
            draw();
          }, ROLL_DELAY);
          changeState(GameState.ROLL_MENU);
          break;
        default: break;
      }
    }

    return false;
  }

  void handleRollMenuInput(int[] input) {
    Console.KeyCode key = Console.KeyCode.fromBytes(input);

    if (key != null && key == Console.KeyCode.ENTER && selected == RollMenuOptions.ROLL.ordinal() && rollsLeft == 0) {
      return;
    }

    handleMenuNavigation(key, RollMenuOptions.values());
  }

  void handleSelectingDiceInput(int[] input) {
    Console.KeyCode key = Console.KeyCode.fromBytes(input);
    if (key != null) {
      switch (key) {
        case ARROW_LEFT: selected = Math.max(selected - 1, 0); break;
        case ARROW_RIGHT: selected = Math.min(selected + 1, Game.DICE_COUNT - 1); break;
        case SPACE: hand.toggle(selected); break;
        case ENTER:
          if (hand.allKept()) {
            changeState(GameState.ROLL_MENU);
            break;
          }
          rollsLeft--;
          changeState(GameState.ROLLING);
          draw();
          hand.rollUnkept((cube, index) -> {
            Console.moveCursor(0, 0);
            draw();
          }, ROLL_DELAY);
          changeState(GameState.ROLL_MENU);
          break;
        case ESCAPE: changeState(GameState.ROLL_MENU); break;
        default: break;
      }
    }
  }

  void handleSelectingCategoryInput(int[] input) {
    Console.KeyCode key = Console.KeyCode.fromBytes(input);

    RollCategory[] categories = scoresheet.getAvailableCategories();

    if (key != null) {
      switch (key) {
        case ARROW_UP:
          selected = Math.max(selected - 1, 0);
          scoresheet.select(categories[selected]);
          break;
        case ARROW_DOWN:
          selected = Math.min(selected + 1, categories.length - 1);
          scoresheet.select(categories[selected]);
          break;
        case ENTER:
          RollCategory category = categories[selected];
          if (category.isMatch(hand.dice)) {
            scoresheet.setScore(category, category.getScore(hand.dice));
          } else {
            scoresheet.setScore(category, 0);
          }
          changeState(GameState.START);
          rollsLeft = 3;
          hand.clear();
          scoresheet.select(null);
          changeState(GameState.START);
          break;
        case ESCAPE: changeState(GameState.ROLL_MENU); break;
        default: break;
      }
    }
  }

  void handleGameOverInput(int[] input) {
    Console.KeyCode key = Console.KeyCode.fromBytes(input);

    if (key != null) {
      switch (key) {
        case ENTER:
          scoresheet.reset();
          changeState(UIState.TITLE);
          rollsLeft = 3;
          hand.clear();
          changeState(GameState.START);
          break;
        default: break;
      }
    }
  }

  public static void main(String[] args) throws IOException, InterruptedException {
    Game game = new Game();
    game.run();
  }

  @SuppressWarnings("unchecked")
  boolean handleMenuNavigation(Console.KeyCode key, Enum<?>[] options) {
    if (key == null)
      return false;

    switch (key) {
      case ARROW_UP: selected = Math.max(selected - 1, 0); return true;
      case ARROW_DOWN: selected = Math.min(selected + 1, options.length - 1); return true;
      case ENTER:
        try {
          final Consumer<Game> action =
            (Consumer<Game>)options[selected].getClass().getField("action").get(
              options[selected]
            );
          action.accept(this);
        } catch (Exception e) {
        }
        return true;
      default: return false;
    }
  }
}
