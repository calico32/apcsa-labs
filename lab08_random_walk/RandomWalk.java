package lab08_random_walk;

import static shared.TextHelpers.*;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.function.BiConsumer;
import java.util.function.Function;

import shared.Console;
import shared.Console.KeyCode;
import shared.TextSegment;

import lab08_random_walk.Path.Direction;

public class RandomWalk {
  public static void main(String[] args) throws IOException, InterruptedException {
    var instance        = new RandomWalk();
    instance.cameraMode = CameraMode.FOLLOWING;
    instance.run();
  }

  public RandomWalk() {
    path     = new Path();
    position = Point.ZERO;
  }

  public final Path path;
  public Point position;
  public int width;
  public int height;
  public Direction lastDirection;

  public CameraMode cameraMode     = CameraMode.CENTERED;
  public DisplayMode displayMode   = DisplayMode.UNICODE;
  public SimulateMode simulateMode = SimulateMode.INFINITE;
  public UIState uiState           = UIState.MENU;
  public final String title =
    text("Random walk simulator").bold().randomRainbow().string();

  public boolean shouldExit   = false;
  public DisplayThread thread = null;

  public int moveCount      = 0;
  public int targetMoves    = 100;
  public int targetDistance = 20;

  public void setSize(int width, int height) {
    this.width  = width;
    this.height = height - 4;
    Console.clear();
  }

  public void setState(UIState state) {
    Console.clear();
    uiState = state;
    if (state == UIState.RUNNING) {
      thread = new DisplayThread();
      thread.start();
    } else if (thread != null) {
      thread.interrupt();
    }
  }

  private void reset() {
    path.reset();
    position      = Point.ZERO;
    lastDirection = null;
    moveCount     = 0;
  }

  public double distance() {
    return Math.sqrt(Math.pow(position.x, 2) + Math.pow(position.y, 2));
  }

  public void move() {
    var values = lastDirection != null ? lastDirection.choices() : Direction.values();
    // var values    = Direction.values();
    var direction = values[(int)(Math.random() * values.length)];
    path.move(direction);

    lastDirection = direction;
    position      = position.add(direction.x, direction.y);
    moveCount++;
  }

  public void run() throws IOException, InterruptedException {
    Console.clear();
    Console.moveCursor(0, 0);
    Console.init();
    Console.hideCursor();

    var size = Console.getSize();
    setSize(size.width, size.height);

    setState(UIState.MENU);

    try {
      draw();
      while (true) {
        var input = Console.next();

        handleInput(input);
        if (shouldExit) {
          Console.reset();
          Console.showCursor();
          System.exit(0);
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
    if (uiState == UIState.MENU) {
      drawMenu();
    } else {
      drawMain();
    }
  }

  public void drawMenu() {
    println(title);
    for (var option : MenuOptions.values()) {
      var t = text(option.text.apply(this));
      if (selected == option.ordinal()) {
        println(text("> ").blue(), t.blue());
      } else {
        println(text("  "), t);
      }
    }
  }

  public void drawMain() {
    var sb      = new StringBuilder();
    int xOffset = -width / 2;
    int yOffset = -height / 2;
    if (cameraMode == CameraMode.FOLLOWING) {
      xOffset += position.x;
      yOffset += position.y;
    }
    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {
        var p = Point.xy(x + xOffset, y + yOffset);
        if (p == Point.ZERO) {
          var symbol = displayMode == DisplayMode.UNICODE ? "◆" : '@';
          sb.append(text(symbol).green().bold());
        } else if (p == position) {
          var symbol = displayMode == DisplayMode.UNICODE ? lastDirection.unicode
                                                          : lastDirection.ascii;

          sb.append(text(symbol).blue().bold());
        } else if (path.nodes.containsKey(p)) {
          var symbol =
            displayMode == DisplayMode.UNICODE ? path.nodes.get(p).symbol : '.';
          var segment = text(symbol);
          var index   = path.path.lastIndexOf(p);

          if (index >= path.path.size() - 8) {
            segment = segment.white();
          } else if (index >= path.path.size() - 16) {
            segment = segment.blackBright();
          } else {
            segment = segment.black();
          }

          sb.append(segment);
        } else
          sb.append(' ');
      }
      sb.append('\n');
    }

    Console.moveCursor(0, 0);
    println(
      uiState == UIState.RUNNING    ? text("► Running    ").green().bold()
        : uiState == UIState.PAUSED ? text("⏸ Paused     ").yellow().bold()
                                    : text("⏹ Finished   ").red().bold(),
      text("Simulation mode: ").bold(),
      text(simulateMode.toString().toLowerCase()),
      text("   Camera mode (c): ").bold(),
      text(cameraMode.toString().toLowerCase()).padRight(9),
      text("   Display mode (d): ").bold(),
      text(displayMode.toString().toLowerCase()).padRight(8)
    );
    println(
      text("Moves: ").bold(),
      text(moveCount).padRight(7),
      text("Distance: "),
      text(new DecimalFormat("#.##").format(distance())).padRight(11),
      text("Press ").dim(),
      text("space").dim().bold(),
      text(" to ").dim(),
      text(
        uiState == UIState.RUNNING    ? "pause "
          : uiState == UIState.PAUSED ? "resume"
                                      : "return to menu"
      )
        .dim(),
      text("    ").dim(),
      text("Press ").dim(),
      text("q").dim().bold(),
      text(" to quit").dim()
    );
    print(sb.toString());
  }

  public void handleInput(int[] input) {
    switch (uiState) {
      case MENU: handleMenuInput(input); break;
      case RUNNING:
      case PAUSED: handleMainInput(input); break;
      case FINISHED: handleFinishedInput(input); break;
    }
  }

  public void handleMenuInput(int[] input) {
    var key = Console.KeyCode.fromBytes(input);

    if (handleMenuNavigation(key, MenuOptions.values()))
      return;
  }

  public void handleMainInput(int[] input) {
    if (input.length == 0)
      return;

    var key = Console.KeyCode.fromBytes(input);

    if (key == null) {
      switch (input[0]) {
        case 'c': cameraMode = next(cameraMode, 2); break;
        case 'd': displayMode = next(displayMode, 2); break;
        case 'p':
          setState(uiState == UIState.RUNNING ? UIState.PAUSED : UIState.RUNNING);
          break;
        case 'q': shouldExit = true; break;
      }
      return;
    }

    switch (key) {
      case ESCAPE: setState(UIState.MENU); break;
      case SPACE:
        setState(uiState == UIState.RUNNING ? UIState.PAUSED : UIState.RUNNING);
        break;
      default: break;
    }
  }

  public void handleFinishedInput(int[] input) {
    if (input.length == 0)
      return;

    var key = Console.KeyCode.fromBytes(input);

    if (key == null) {
      switch (input[0]) {
        case 'r': reset(); break;
        case 'q': shouldExit = true; break;
      }
      return;
    }

    switch (key) {
      case ESCAPE: setState(UIState.MENU); break;
      case SPACE:
        reset();
        setState(UIState.MENU);

        break;
      default: break;
    }
  }

  class DisplayThread extends Thread {
    public void run() {
      while (true) {
        try {
          Thread.sleep(100);
          move();
          if (uiState == UIState.RUNNING) {
            if (simulateMode.shouldStop.apply(RandomWalk.this)) {
              uiState = UIState.FINISHED;
              Console.skipNext();
              return;
            }
          }
          Console.skipNext();
        } catch (InterruptedException e) {
          break;
        }
      }
    }
  }

  int selected;
  @SuppressWarnings("unchecked")
  boolean handleMenuNavigation(Console.KeyCode key, Enum<?>[] options) {
    if (key == null)
      return false;

    BiConsumer<RandomWalk, Integer> action;
    try {
      action = (BiConsumer<RandomWalk, Integer>)options[selected]
                 .getClass()
                 .getField("action")
                 .get(options[selected]);
    } catch (
      IllegalArgumentException | IllegalAccessException | NoSuchFieldException |
      SecurityException e
    ) {
      e.printStackTrace();
      return true;
    }

    if (key == KeyCode.ARROW_LEFT) {
      action.accept(this, -1);
      return true;
    } else if (key == KeyCode.ARROW_RIGHT) {
      action.accept(this, 1);
      return true;
    } else if (key == KeyCode.ENTER) {
      action.accept(this, 2);
      return true;
    }

    switch (key) {
      case ARROW_UP: selected = Math.max(selected - 1, 0); return true;
      case ARROW_DOWN: selected = Math.min(selected + 1, options.length - 1); return true;
      default: return false;
    }
  }

  private static Function<RandomWalk, String>
  menuToggle(String start, Enum<?>[] values, Function<RandomWalk, Enum<?>> current) {
    return (cls) -> {
      var line = start + TextSegment.RESET;
      for (Enum<?> mode : values) {
        var segment = text(mode.toString().toLowerCase());
        if (mode == current.apply(cls)) {
          segment = segment.blue().underline().bold();
        }
        line += segment.string() + " ";
      }
      return line;
    };
  }

  @SuppressWarnings("unchecked")
  private static <T extends Enum<?>> T next(T value, int delta) {
    T[] values = (T[])value.getClass().getEnumConstants();
    if (delta == 2) {
      return values[(value.ordinal() + 1) % values.length];
    }

    var target = Math.min(values.length - 1, Math.max(0, value.ordinal() + delta));
    return values[target];
  }

  enum MenuOptions {
    SIMULATE_MODE(
      menuToggle("Simulation mode: ", SimulateMode.values(), (cls) -> cls.simulateMode),
      (cls, delta) -> cls.simulateMode = next(cls.simulateMode, delta)
    ),
    CAMERA_MODE(
      menuToggle("Camera mode: ", CameraMode.values(), (cls) -> cls.cameraMode),
      (cls, delta) -> cls.cameraMode = next(cls.cameraMode, delta)
    ),
    DISPLAY_MODE(
      menuToggle("Display mode: ", DisplayMode.values(), (cls) -> cls.displayMode),
      (cls, delta) -> cls.displayMode = next(cls.displayMode, delta)
    ),
    START((cls) -> "Start", (cls, delta) -> {
      if (delta == 2) {
        cls.setState(UIState.RUNNING);
      }
    }),
    EXIT((cls) -> "Exit", (cls, delta) -> {
      if (delta == 2) {
        cls.shouldExit = true;
      }
    });

    public final Function<RandomWalk, String> text;
    public final BiConsumer<RandomWalk, Integer> action;
    MenuOptions(
      Function<RandomWalk, String> text, BiConsumer<RandomWalk, Integer> action
    ) {
      this.text   = text;
      this.action = action;
    }
  }
}

enum CameraMode { CENTERED, FOLLOWING }
enum DisplayMode { ASCII, UNICODE }
enum SimulateMode {
  INFINITE((cls) -> false),
  MOVE_20((cls) -> cls.moveCount >= 20),
  MOVE_100(cls -> cls.moveCount >= 100),
  MOVE_1000(cls -> cls.moveCount >= 1000),
  DISTANCE_10(cls -> cls.distance() >= 10d),
  DISTANCE_20(cls -> cls.distance() >= 20d),
  DISTANCE_50(cls -> cls.distance() >= 50d);

  public final Function<RandomWalk, Boolean> shouldStop;
  SimulateMode(Function<RandomWalk, Boolean> shouldStop) { this.shouldStop = shouldStop; }
}

enum UIState { MENU, RUNNING, PAUSED, FINISHED }
