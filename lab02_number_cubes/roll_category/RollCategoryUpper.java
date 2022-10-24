package lab02_number_cubes.roll_category;

import shared.TextSegment;

import lab02_number_cubes.NumberCube;

public class RollCategoryUpper extends RollCategory {
  public final int targetValue;

  public RollCategoryUpper(String name, int targetValue) {
    super(name, String.format("sum of %s", name.toLowerCase()));
    this.targetValue = targetValue;
  }

  public String getName(Integer points) {
    if (points == null) {
      return getName();
    } else if (points < 3 * targetValue) {
      // below bonus threshold, highlight in red
      return new TextSegment(getName()).red().toString();
    } else {
      // above bonus threshold, highlight in green
      return new TextSegment(getName()).green().toString();
    }
  }

  public boolean isMatch(NumberCube[] dice) { return true; }

  public int getScore(NumberCube[] dice) {
    int score = 0;
    for (NumberCube cube : dice) {
      if (cube.value == targetValue) {
        score += cube.value;
      }
    }
    return score;
  }
}
