package lab02_number_cubes.roll_category;

import lab02_number_cubes.NumberCube;

public class RollCategoryUpper extends RollCategory {
  public final int targetValue;

  public RollCategoryUpper(String name, int targetValue) {
    super(name);
    this.targetValue = targetValue;
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
