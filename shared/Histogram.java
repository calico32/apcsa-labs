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
      this.name  = name.replaceAll("\\n", "\\n");
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
  private Console.Size consoleSize;

  public Histogram(String title) {
    this.title      = title;
    this.categories = new ArrayList<>();
  }

  public Histogram setSize(Console.Size consoleSize) {
    this.consoleSize = consoleSize;
    return this;
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

  public String draw() { return draw(null); }
  public String draw(Number max) {
    var output = new StringBuilder();
    if (title != null) {
      output.append(text(title).blue().bold() + "\n");
    }

    if (categories.size() == 0) {
      output.append("No entries exist.\n");
      return output.toString();
    }

    var df = new DecimalFormat("###,###.##");
    df.setRoundingMode(RoundingMode.DOWN);
    df.setGroupingUsed(true);

    var consoleSize = this.consoleSize != null ? this.consoleSize : Console.getSize();

    int nameWidth = Math.min(
      categories.stream().mapToInt(c -> c.name.length()).max().getAsInt(),
      consoleSize.width / 2
    );

    var maxValue = max != null
      ? max.doubleValue()
      : categories.stream().mapToDouble(c -> c.value).max().getAsDouble();

    var values = new String[categories.size()];
    for (var category : categories) {
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

    var valueWidth = valuePlacement != ValuePlacement.NONE
      ? categories.stream()
          .mapToInt(c -> values[categories.indexOf(c)].length())
          .max()
          .getAsInt()
      : 0;

    var barWidth = orientation == Orientation.HORIZONTAL
      ? consoleSize.width - valueWidth - 2 -
        (valuePlacement == ValuePlacement.OUTSIDE ? valueWidth : 0)
      : consoleSize.height - categories.size() - 1;

    var resolution = barWidth * 8;

    if (orientation == Orientation.HORIZONTAL) {
      for (int i = 0; i < categories.size(); i++) {
        var category     = categories.get(i);
        var eighths      = (int)Math.round(category.value / maxValue * resolution);
        int fullBars     = eighths / 8;
        int lastBarIndex = eighths % 8;
        char lastBar;
        if (lastBarIndex == 0) {
          if (fullBars == 0) {
            lastBar = horizontalBars.charAt(1);
          } else {
            fullBars--;
            lastBar = fullBlock;
          }
        } else {
          lastBar = horizontalBars.charAt(lastBarIndex);
        }

        output.append(
          text("%-" + nameWidth + "s ", Util.truncate(category.name, nameWidth))
        );

        if (valuePlacement != ValuePlacement.INSIDE || fullBars < valueWidth + 1) {
          output.append(
            text(Character.toString(fullBlock).repeat(fullBars) + lastBar).blackBright()
          );
          if (valuePlacement != ValuePlacement.NONE) {
            output.append(text(" " + values[i]).white());
          }
          output.append("\n");
        } else {
          var thisValue = values[i];
          output.append(
            text(Character.toString(fullBlock).repeat(fullBars - thisValue.length()))
              .blackBright()
              .toString() +
            text(thisValue).white().bgBlackBright() + text(lastBar).blackBright()
          );
          output.append("\n");
        }
      }
    } else {
      // TODO vertical
      output.append("todo lmao\n");
    }

    return output.toString();
  }
}
