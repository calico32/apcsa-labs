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

  final String verticalBars   = "▁▂▃▄▅▆▇█";
  final String horizontalBars = "▏▎▍▌▋▊▉█";

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

  public void print() {
    if (categories.size() == 0) {
      System.out.println("No categories to print");
      return;
    }

    DecimalFormat df = new DecimalFormat("#.##");
    df.setRoundingMode(RoundingMode.DOWN);
    df.setGroupingUsed(true);

    Console.Size consoleSize = Console.getSize();

    int maxNameWidth = Math.min(
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
          values[categories.indexOf(category)] =
            df.format(category.value) + " (" +
            df.format(category.value / maxValue * 100) + "%)";
          break;
      }
    }

    int maxValueWidth = valuePlacement != ValuePlacement.NONE
                          ? categories.stream()
                              .mapToInt(c -> values[categories.indexOf(c)].length())
                              .max()
                              .getAsInt()
                          : 0;

    int maxBarWidth = orientation == Orientation.HORIZONTAL
                        ? consoleSize.width - maxNameWidth - maxValueWidth - 1
                        : consoleSize.height - categories.size() - 1;

    int resolution = maxBarWidth * 8;

    println(text(title).blue().bold());

    if (orientation == Orientation.HORIZONTAL) {
      for (int i = 0; i < categories.size(); i++) {
        Category category = categories.get(i);
        int eighths       = (int)Math.round(category.value / maxValue * resolution);
        int fullBars      = eighths / 8;
        int lastBar       = eighths % 8;
        if (lastBar == 0) {
          fullBars--;
          lastBar = 7;
        }

        switch (valuePlacement) {
          case INSIDE:
            if (fullBars < maxValueWidth + 1) {
              // not enough space for value inside bar
              // don't break, fall through to OUTSIDE
            } else {
              System.out.printf(
                "%-" + maxNameWidth + "s %s%s%s\n",
                Util.truncate(category.name, maxNameWidth),
                text(horizontalBars.substring(7).repeat(fullBars - maxValueWidth))
                  .blackBright(),
                text(values[i]).white().bgBlackBright(),
                text(horizontalBars.charAt(lastBar)).blackBright()
              );
              break;
            }
          case OUTSIDE:
            System.out.printf(
              "%-" + maxNameWidth + "s %s %s\n",
              Util.truncate(category.name, maxNameWidth),
              text(
                horizontalBars.substring(7).repeat(fullBars) +
                horizontalBars.charAt(lastBar)
              )
                .blackBright(),
              text(values[i]).white()
            );
            break;
          case NONE:
            System.out.printf(
              "%-" + maxNameWidth + "s %s\n",
              Util.truncate(category.name, maxNameWidth),
              text(
                horizontalBars.substring(7).repeat(fullBars) +
                horizontalBars.charAt(lastBar)
              )
                .blackBright()
            );
        }
      }
    } else {
      // TODO vertical
      println("todo lmao");
    }
  }
}
