package lab02_number_cubes.roll_category;

import lab02_number_cubes.Game;
import lab02_number_cubes.NumberCube;

public class RollCategoryOfAKind extends RollCategory {
  public final int targetCount;

  public RollCategoryOfAKind(String name, String scoring, int targetCount) {
    super(name, scoring);
    this.targetCount = targetCount;
  }

  public boolean isMatch(NumberCube[] dice) {
    int[] counts = new int[Game.DICE_SIDES];
    for (int i = 0; i < dice.length; i++) {
      counts[dice[i].value - 1]++;
    }
    for (int i = 0; i < counts.length; i++) {
      if (counts[i] >= targetCount) {
        return true;
      }
    }
    return false;
  }

  public int getScore(NumberCube[] dice) {
    int score = 0;
    for (int i = 0; i < dice.length; i++) {
      score += dice[i].value;
    }
    return score;
  }
}
