package shared;

import java.util.ArrayList;

public class TextBuilder extends TextSegment {
  public static void print(TextSegment... segments) {
    for (TextSegment segment : segments) {
      System.out.print(segment);
    }
  }

  public static void println(TextSegment... segments) {
    print(segments);
    System.out.println();
  }

  public ArrayList<TextSegment> segments = new ArrayList<>();
  public TextBuilder() {}
  public TextBuilder(TextSegment... segments) {
    for (TextSegment segment : segments) {
      this.segments.add(segment);
    }
  }

  public TextBuilder add(TextSegment segment) {
    segments.add(segment);
    return this;
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    for (TextSegment segment : segments) {
      builder.append(format);
      builder.append(segment);
    }
    return builder.toString() + RESET;
  }

  public void print() { System.out.print(toString()); }

  public void println() {
    print();
    System.out.println();
  }
}
