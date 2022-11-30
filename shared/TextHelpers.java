package shared;

public class TextHelpers {
  public static TextSegment text(Object text) { return new TextSegment(text); }
  public static TextSegment text(String format, Object... args) {
    return new TextSegment(format, args);
  }
  public static int printWidth(Object obj) { return Util.printWidth(obj); }

  public static void println(TextSegment... segments) { TextBuilder.println(segments); }
  public static void println(String str) { System.out.println(str); }
  public static void println() { System.out.println(); }
  public static void print(TextSegment... segments) { TextBuilder.print(segments); }
  public static void print(String str) { System.out.print(str); }
}
