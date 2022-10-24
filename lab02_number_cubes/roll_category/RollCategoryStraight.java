package lab02_number_cubes.roll_category;

import lab02_number_cubes.Game;
import lab02_number_cubes.NumberCube;

public class RollCategoryStraight extends RollCategory {
  public final int targetLength;
  public final int points;

  public RollCategoryStraight(String name, int targetLength, int points) {
    super(name, String.format("%d pts", points));
    this.targetLength = targetLength;
    this.points       = points;
  }

  public boolean isMatch(NumberCube[] dice) {
    int[] counts = new int[Game.DICE_SIDES];
    for (int i = 0; i < dice.length; i++) {
      counts[dice[i].value - 1]++;
    }

    int lowValue = 1;

    while (lowValue <= Game.DICE_SIDES - targetLength + 1) {
      int length = 0;
      for (int i = 0; i < targetLength; i++) {
        if (counts[lowValue + i - 1] > 0) {
          length++;
        } else {
          break;
        }
      }

      if (length == targetLength) {
        return true;
      }

      lowValue++;
    }

    return false;
  }

  public int getScore(NumberCube[] dice) { return points; }
}
