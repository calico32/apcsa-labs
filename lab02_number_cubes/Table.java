package lab02_number_cubes;

import shared.TextHelpers;

public class Table extends TextHelpers {
  public final int leftWidth;
  public final int rightWidth;
  public final int width;

  static final int initialCapacity = 1300;

  public StringBuilder sb = new StringBuilder(initialCapacity);

  public Table(int leftWidth, int rightWidth) {
    this.leftWidth  = leftWidth;
    this.rightWidth = rightWidth;

    width = 2 + leftWidth + 3 + rightWidth + 2;
  }

  public Table clear() {
    sb = new StringBuilder(initialCapacity);
    return this;
  }

  public String toString() { return sb.toString(); }

  public boolean selected = false;

  public Table select() {
    selected = true;
    return this;
  }

  class TableLine {
    StringBuilder line = new StringBuilder();

    public TableLine() {
      if (selected) {
        line.append(text("> ").blue());
      } else {
        line.append("  ");
      }
    }

    public TableLine add(Object obj) {
      line.append(obj);
      return this;
    }

    public void end() {
      if (selected) {
        line.append(text(" <").blue());
        selected = false;
      }
      line.append("  \n");
      sb.append(line);
    }
  }

  TableLine line() { return new TableLine(); }

  public Table divider() {
    line()
      .add("├─")
      .add("─".repeat(leftWidth))
      .add("─┼─")
      .add("─".repeat(rightWidth))
      .add("─┤")
      .end();

    return this;
  }

  public Table divider(Object left, Object middle, Object right) {
    line()
      .add(left)
      .add("─".repeat(leftWidth + 2))
      .add(middle)
      .add("─".repeat(rightWidth + 2))
      .add(right)
      .end();

    return this;
  }

  public Table row(Object content) {
    line()
      .add("│ ")
      .add(content)
      .add(" ".repeat(width - printWidth(content) - 3))
      .add("│")
      .end();

    return this;
  }

  public Table scoreRow(Object left, int right) {
    if (right < 0) {
      row(left, "-");
    } else {
      row(left, (Object)right);
    }
    return this;
  }

  public Table scoreRow(Object left, Object middle, int right) {
    if (right < 0) {
      row(left, middle, "-");
    } else {
      row(left, middle, (Object)right);
    }
    return this;
  }

  public Table row(Object left, Object right) {
    line()
      .add("│ ")
      .add(left)
      .add(" ".repeat(leftWidth - printWidth(left)))
      .add(" │ ")
      .add(" ".repeat(rightWidth - printWidth(right)))
      .add(right)
      .add(" │")
      .end();

    return this;
  }

  public Table row(Object left, Object middle, Object right) {
    if (printWidth(left) + printWidth(middle) > leftWidth) {
      row(text("ERROR: content too long"), right);
      return this;
    }

    line()
      .add("│ ")
      .add(left)
      .add(" ".repeat(leftWidth - printWidth(left) - printWidth(middle)))
      .add(middle)
      .add(" │ ")
      .add(" ".repeat(rightWidth - printWidth(right)))
      .add(right)
      .add(" │")
      .end();

    return this;
  }
}
