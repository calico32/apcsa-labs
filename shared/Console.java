package shared;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class Console {
  public enum KeyCode {
    ESCAPE(0x1b),
    ARROW_UP(0x1b5b41),
    ARROW_DOWN(0x1b5b42),
    ARROW_RIGHT(0x1b5b43),
    ARROW_LEFT(0x1b5b44),
    ENTER(0xa),
    BACKSPACE(0x7f),
    SPACE(0x20),
    TAB(0x9),
    HOME(0x1b5b48),
    END(0x1b5b46),
    DELETE(0x1b5b337e),
    INSERT(0x1b5b327e),
    PAGE_UP(0x1b5b357e),
    PAGE_DOWN(0x1b5b367e);

    public final int code;
    KeyCode(int code) { this.code = code; }

    public static KeyCode fromBytes(int[] bytes) {
      int code = 0;
      for (int b : bytes) {
        code <<= 8;
        code |= b & 0xff;
      }
      return fromCode(code);
    }

    public static KeyCode fromCode(int code) {
      for (KeyCode k : KeyCode.values()) {
        if (k.code == code)
          return k;
      }
      return null;
    }
  }

  public static class Size {
    public final int width;
    public final int height;

    public Size(int width, int height) {
      this.width  = width;
      this.height = height;
    }
  }

  public static void init() throws IOException, InterruptedException {
    setTerminalToCBreak();
  }

  public static void reset() throws IOException, InterruptedException { stty(ttyConfig); }

  private static String ttyConfig;

  private static void setTerminalToCBreak() throws IOException, InterruptedException {
    ttyConfig = stty("-g").trim();

    // set the console to be character-buffered instead of line-buffered
    stty("-icanon");

    // disable character echoing
    stty("-echo");
  }

  public static void clear() {
    System.out.print("\033[H\033[2J");
    System.out.flush();
  }

  public static void hideCursor() {
    System.out.print("\033[?25l");
    System.out.flush();
  }

  public static void showCursor() {
    System.out.print("\033[?25h");
    System.out.flush();
  }

  public static void moveCursor(int x, int y) {
    System.out.print("\033[" + y + ";" + x + "H");
    System.out.flush();
  }

  static boolean shouldSkipNext = false;

  public static int[] next() throws IOException {
    List<Integer> bytes = new ArrayList<>();
    // wait until the next byte is available
    while (System.in.available() == 0 && !shouldSkipNext) {
      try {
        Thread.sleep(10);
      } catch (InterruptedException e) {
        // ignore
      }
    }

    if (shouldSkipNext) {
      shouldSkipNext = false;
      while (System.in.available() > 0) {
        System.in.read();
      }
      return new int[0];
    }

    // read all available bytes
    do {
      bytes.add(System.in.read());
    } while (System.in.available() > 0);

    int[] result = new int[bytes.size()];
    for (int i = 0; i < bytes.size(); i++) {
      result[i] = bytes.get(i);
    }
    return result;
  }

  public static void skipNext() { shouldSkipNext = true; }

  public static Size getSize() {
    try {
      String[] parts = stty("-a").trim().split(";");

      int rows = Integer.parseInt(parts[1].split(" ")[2]);
      int cols = Integer.parseInt(parts[2].split(" ")[2]);

      return new Size(cols, rows);
    } catch (Exception e) {
      return new Size(80, 24);
    }
  }

  public static String unquote(String str) {
    if (str.startsWith("\"") && str.endsWith("\""))
      return str.substring(1, str.length() - 1);
    return str;
  }

  /**
   *  Execute the stty command with the specified arguments
   *  against the current active terminal.
   */
  private static String stty(final String args) throws IOException, InterruptedException {
    String cmd = "stty " + args + " < /dev/tty";

    return exec("sh", "-c", cmd);
  }

  /**
   *  Execute the specified command and return the output
   *  (both stdout and stderr).
   */
  private static String exec(final String... cmd)
    throws IOException, InterruptedException {

    Process process   = Runtime.getRuntime().exec(cmd);
    InputStream input = process.getInputStream();

    ByteArrayOutputStream out = new ByteArrayOutputStream();
    int ch;

    while ((ch = input.read()) != -1) {
      out.write(ch);
    }

    input = process.getErrorStream();

    while ((ch = input.read()) != -1) {
      out.write(ch);
    }

    process.waitFor();

    return new String(out.toByteArray());
  }
}
