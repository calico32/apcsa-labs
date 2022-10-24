package shared;

public class TextHelpers {
  protected static TextSegment text(Object text) { return new TextSegment(text); }
  protected static TextSegment text(String format, Object... args) {
    return new TextSegment(format, args);
  }
  protected static int printWidth(Object obj) { return Util.printWidth(obj); }
}
