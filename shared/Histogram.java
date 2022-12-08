package shared;

import static shared.TextHelpers.*;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class Histogram {
  public static enum Orientation { HORIZONTAL, VERTICAL }
  public static enum ValueMode { ABSOLUTE, PERCENTAGE, BOTH }
  public static enum ValuePlacement { INSIDE, OUTSIDE, NONE }
  public class Category {
    public final String name;
    public final double value;

    public Category(String name, Number value) {
      this.name  = name;
      this.value = value.doubleValue();
    }
  }

  final char fullBlock        = '█';
  final String verticalBars   = " ▁▂▃▄▅▆▇";
  final String horizontalBars = " ▏▎▍▌▋▊▉";

  public final ArrayList<Category> categories;
  public String title;
  public Orientation orientation       = Orientation.HORIZONTAL;
  public ValueMode valueMode           = ValueMode.ABSOLUTE;
  public ValuePlacement valuePlacement = ValuePlacement.OUTSIDE;

  public Histogram(String title) {
    this.title      = title;
    this.categories = new ArrayList<>();
  }

  public Histogram setTitle(String title) {
    this.title = title;
    return this;
  }
  public Histogram setOrientation(Orientation orientation) {
    this.orientation = orientation;
    return this;
  }
  public Histogram setValueMode(ValueMode valueMode) {
    this.valueMode = valueMode;
    return this;
  }
  public Histogram setValuePlacement(ValuePlacement valuePlacement) {
    this.valuePlacement = valuePlacement;
    return this;
  }
  public Histogram addCategory(String name, Number value) {
    categories.add(new Category(name, value));
    return this;
  }
  public Histogram clear() {
    categories.clear();
    return this;
  }

  public void display() {
    println(text(title).blue().bold());

    if (categories.size() == 0) {
      System.out.println("No entries exist.");
      return;
    }

    DecimalFormat df = new DecimalFormat("###,###.##");
    df.setRoundingMode(RoundingMode.DOWN);
    df.setGroupingUsed(true);

    Console.Size consoleSize = Console.getSize();

    int nameWidth = Math.min(
      categories.stream().mapToInt(c -> c.name.length()).max().getAsInt(),
      consoleSize.width / 2
    );

    Double maxValue = categories.stream().mapToDouble(c -> c.value).max().getAsDouble();

    String[] values = new String[categories.size()];
    for (Category category : categories) {
      switch (valueMode) {
        case ABSOLUTE:
          values[categories.indexOf(category)] = df.format(category.value);
          break;
        case PERCENTAGE:
          values[categories.indexOf(category)] =
            df.format(category.value / maxValue * 100) + "%";
          break;
        case BOTH:
          values[categories.indexOf(category)] = df.format(category.value) + " (" +
            df.format(category.value / maxValue * 100) + "%)";
          break;
      }
    }

    int valueWidth = valuePlacement != ValuePlacement.NONE
      ? categories.stream()
          .mapToInt(c -> values[categories.indexOf(c)].length())
          .max()
          .getAsInt()
      : 0;

    int barWidth = orientation == Orientation.HORIZONTAL
      ? consoleSize.width - valueWidth - 2 -
        (valuePlacement == ValuePlacement.OUTSIDE ? valueWidth : 0)
      : consoleSize.height - categories.size() - 1;

    int resolution = barWidth * 8;

    if (orientation == Orientation.HORIZONTAL) {
      for (int i = 0; i < categories.size(); i++) {
        Category category = categories.get(i);
        int eighths       = (int)Math.round(category.value / maxValue * resolution);
        int fullBars      = eighths / 8;
        int lastBarIndex  = eighths % 8;
        char lastBar;
        if (lastBarIndex == 0) {
          fullBars--;
          lastBar = fullBlock;
        } else {
          lastBar = horizontalBars.charAt(lastBarIndex);
        }

        print(text("%-" + nameWidth + "s ", Util.truncate(category.name, nameWidth)));

        if (valuePlacement != ValuePlacement.INSIDE || fullBars < valueWidth + 1) {
          print(
            text(Character.toString(fullBlock).repeat(fullBars) + lastBar).blackBright()
          );
          if (valuePlacement != ValuePlacement.NONE) {
            print(text(" " + values[i]).white());
          }
          println();
        } else {
          String thisValue = values[i];
          println(
            text(Character.toString(fullBlock).repeat(fullBars - thisValue.length()))
              .blackBright(),
            text(thisValue).white().bgBlackBright(),
            text(lastBar).blackBright()
          );
        }
      }
    } else {
      // TODO vertical
      println("todo lmao");
    }
  }
}
