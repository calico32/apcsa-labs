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

  HashMap<RollCategory, Integer> upperSection = new HashMap<>();
  HashMap<RollCategory, Integer> lowerSection = new HashMap<>();

  int nameWidth;
  int width;

  static final int scoreWidth = 5;

  final Table t;

  public Scoresheet() {
    for (RollCategory category : UPPER_SECTION) {
      nameWidth = Math.max(nameWidth, category.name.length());
    }
    for (RollCategory category : LOWER_SECTION) {
      nameWidth = Math.max(nameWidth, category.name.length());
    }

    nameWidth += 12;

    width = 2            // "| "
            + nameWidth  // names
            + 3          // " | "
            + scoreWidth // scores
            + 2;         // " |"

    t = new Table(nameWidth, scoreWidth);
  }

  public String draw() {
    t.clear();

    t.divider("┌", "─", "┐");
    t.row(text("Upper Section").bold());
    t.divider("├", "┬", "┤");

    for (RollCategory category : UPPER_SECTION) {
      t.row(category.name, upperSection.getOrDefault(category, -1));
    }

    t.divider("├", "┼", "┤");
    int upperTotal = 0;
    for (Entry<RollCategory, Integer> entry : upperSection.entrySet()) {
      upperTotal += entry.getValue();
    }

    t.row("Upper Total", upperTotal);
    t.row("Bonus", "total ≥ 63", Integer.toString(upperTotal >= 63 ? 35 : 0));
    t.divider("├", "┴", "┤");

    t.row("Lower Section");
    t.divider("├", "┬", "┤");
    for (RollCategory category : LOWER_SECTION) {
      t.row(category.name, lowerSection.getOrDefault(category, -1));
    }
    t.divider("├", "┼", "┤");

    int lowerTotal = 0;
    for (Entry<RollCategory, Integer> entry : lowerSection.entrySet()) {
      lowerTotal += entry.getValue();
    }

    t.row("Lower Total", lowerTotal);
    t.divider("├", "┼", "┤");
    t.row("Grand Total", upperTotal + lowerTotal);
    t.divider("└", "┴", "┘");

    return t.toString();
  }
}
