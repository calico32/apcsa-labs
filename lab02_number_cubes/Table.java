package lab02_number_cubes;

import shared.TextSegment;

public class Table {
  public final int leftWidth;
  public final int rightWidth;
  public final int width;
  public StringBuilder sb = new StringBuilder();

  public Table(int leftWidth, int rightWidth) {
    this.leftWidth  = leftWidth;
    this.rightWidth = rightWidth;

    width = 2 + leftWidth + 3 + rightWidth + 2;
  }

  public Table clear() {
    sb = new StringBuilder();
    return this;
  }

  public Table divider() {
    sb.append("├─");
    sb.append("─".repeat(leftWidth));
    sb.append("─┼─");
    sb.append("─".repeat(rightWidth));
    sb.append("─┤\n");
    return this;
  }

  public Table divider(String left, String middle, String right) {
    sb.append(left);
    sb.append("─".repeat(leftWidth + 2));
    sb.append(middle);
    sb.append("─".repeat(rightWidth + 2));
    sb.append(right);
    sb.append("\n");
    return this;
  }

  public Table row(String left, String right) {
    sb.append("│ ");
    sb.append(left);
    sb.append(" ".repeat(leftWidth - left.length()));
    sb.append(" │ ");
    sb.append(right);
    sb.append(" ".repeat(rightWidth - right.length()));
    sb.append(" │\n");
    return this;
  }

  public Table row(String left, String middle, String right) {
    if (left.length() + middle.length() > leftWidth) {
      row("ERROR: content too long", right);
      return this;
    }

    sb.append("│ ");
    sb.append(left);
    sb.append(" ".repeat(leftWidth - left.length() - middle.length()));
    sb.append(middle);
    sb.append(" │ ");
    sb.append(" ".repeat(rightWidth - right.length()));
    sb.append(right);
    sb.append(" │\n");
    return this;
  }

  public Table row(String left, int right) {
    if (right < 0) {
      row(left, " ".repeat(rightWidth - 1) + "-");
    } else {
      row(left, String.format("%" + Integer.toString(rightWidth) + "d", right));
    }
    return this;
  }

  public Table row(String text) {
    sb.append("│ ");
    sb.append(text);
    sb.append(" ".repeat(width - text.length() - 3));
    sb.append("│\n");
    return this;
  }

  public Table row(TextSegment tb) {
    sb.append("│ ");
    sb.append(tb);
    sb.append(" ".repeat(width - tb.text.length() - 3));
    sb.append("│\n");
    return this;
  }

  public String toString() { return sb.toString(); }
}
