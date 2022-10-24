package lab02_number_cubes;

import java.util.HashMap;
import java.util.Map.Entry;

import shared.TextHelpers;

import lab02_number_cubes.roll_category.RollCategory;

public class Scoresheet extends TextHelpers {
  static final RollCategory[] UPPER_SECTION = {
    RollCategory.ONES,
    RollCategory.TWOS,
    RollCategory.THREES,
    RollCategory.FOURS,
    RollCategory.FIVES,
    RollCategory.SIXES,
  };

  static final RollCategory[] LOWER_SECTION = {
    RollCategory.THREE_OF_A_KIND,
    RollCategory.FOUR_OF_A_KIND,
    RollCategory.FULL_HOUSE,
    RollCategory.SMALL_STRAIGHT,
    RollCategory.LARGE_STRAIGHT,
    RollCategory.YACHT,
    RollCategory.CHANCE,
  };

  public final HashMap<RollCategory, Integer> upperSection = new HashMap<>();
  public final HashMap<RollCategory, Integer> lowerSection = new HashMap<>();

  static final int scoreWidth = 5;
  final Table t;

  public RollCategory selectedCategory = null;

  public Scoresheet() {
    int nameWidth = 0;

    for (RollCategory category : UPPER_SECTION) {
      nameWidth = Math.max(nameWidth, category.name.length());
    }
    for (RollCategory category : LOWER_SECTION) {
      nameWidth = Math.max(nameWidth, category.name.length());
    }

    nameWidth += 12;

    t = new Table(nameWidth, scoreWidth);
  }

  public void clear() {
    upperSection.clear();
    lowerSection.clear();
  }

  public void select(RollCategory category) { selectedCategory = category; }

  public String draw() {
    t.clear();

    t.divider("┌", "─", "┐");
    t.row(text("Upper Section").bold());
    t.divider("├", "┬", "┤");

    for (RollCategory category : UPPER_SECTION) {
      if (selectedCategory == category) {
        t.select().scoreRow(
          text(category.name).blue().bold(),
          text(category.scoring).dim(),
          upperSection.getOrDefault(category, -1)
        );
      } else {
        t.scoreRow(
          text(category.name),
          text(category.scoring).dim(),
          upperSection.getOrDefault(category, -1)
        );
      }
    }

    t.divider("├", "┼", "┤");
    int upperTotal = 0;
    for (Entry<RollCategory, Integer> entry : upperSection.entrySet()) {
      upperTotal += entry.getValue();
    }

    t.row(text("Upper Total").bold(), text(upperTotal).bold());
    t.row(
      text("Bonus").bold(),
      text("total ≥ 63 → 35 pts").dim(),
      text(upperTotal >= 63 ? 35 : 0).bold()
    );
    t.divider("├", "┴", "┤");

    t.row(text("Lower Section").bold());
    t.divider("├", "┬", "┤");
    for (RollCategory category : LOWER_SECTION) {
      if (selectedCategory == category) {
        t.select().scoreRow(
          text(category.name).blue().bold(),
          text(category.scoring).dim(),
          lowerSection.getOrDefault(category, -1)
        );
      } else {
        t.scoreRow(
          text(category.name),
          text(category.scoring).dim(),
          lowerSection.getOrDefault(category, -1)
        );
      }
    }
    t.divider("├", "┼", "┤");

    int lowerTotal = 0;
    for (Entry<RollCategory, Integer> entry : lowerSection.entrySet()) {
      lowerTotal += entry.getValue();
    }

    t.row(text("Lower Total").bold(), text(lowerTotal).bold());
    t.divider("├", "┼", "┤");
    t.row(text("Grand Total").bold(), text(upperTotal + lowerTotal).bold());
    t.divider("└", "┴", "┘");

    return t.toString();
  }
}