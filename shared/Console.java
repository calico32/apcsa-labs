package shared;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class Console {
  public enum KeyCode {
    ARROW_UP(0x1b5b41),
    ARROW_DOWN(0x1b5b42),
    ARROW_RIGHT(0x1b5b43),
    ARROW_LEFT(0x1b5b44),
    ENTER(0xa),
    BACKSPACE(0x7f),
    ESCAPE(0x1b),
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

  public static void init() throws IOException, InterruptedException {
    setTerminalToCBreak();
  }

  public static void reset() throws IOException, InterruptedException { stty(ttyConfig); }

  private static String ttyConfig;

  private static void setTerminalToCBreak() throws IOException, InterruptedException {
    ttyConfig = stty("-g");

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

  public static int[] next() throws IOException {
    List<Integer> bytes = new ArrayList<>();
    // read at least 1 byte from stdin, and more if available (for special keys that are
    // more than 1 byte)
    // blocks until the next key is pressed
    do {
      bytes.add(System.in.read());
    } while (System.in.available() > 0);

    int[] result = new int[bytes.size()];
    for (int i = 0; i < bytes.size(); i++) {
      result[i] = bytes.get(i);
    }
    return result;
  }

  /**
   *  Execute the stty command with the specified arguments
   *  against the current active terminal.
   */
  private static String stty(final String args) throws IOException, InterruptedException {
    String cmd = "stty " + args + " < /dev/tty";

    return exec(new String[] {"sh", "-c", cmd});
  }

  /**
   *  Execute the specified command and return the output
   *  (both stdout and stderr).
   */
  private static String exec(final String[] cmd)
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
