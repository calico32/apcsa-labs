package lab02_number_cubes.roll_category;

import lab02_number_cubes.Game;
import lab02_number_cubes.NumberCube;

public abstract class RollCategory {
  public static final RollCategoryUpper ONES   = new RollCategoryUpper("Ones", 1);
  public static final RollCategoryUpper TWOS   = new RollCategoryUpper("Twos", 2);
  public static final RollCategoryUpper THREES = new RollCategoryUpper("Threes", 3);
  public static final RollCategoryUpper FOURS  = new RollCategoryUpper("Fours", 4);
  public static final RollCategoryUpper FIVES  = new RollCategoryUpper("Fives", 5);
  public static final RollCategoryUpper SIXES  = new RollCategoryUpper("Sixes", 6);

  public static final RollCategoryOfAKind THREE_OF_A_KIND =
    new RollCategoryOfAKind("Three of a Kind", "sum", 3);
  public static final RollCategoryOfAKind FOUR_OF_A_KIND =
    new RollCategoryOfAKind("Four of a Kind", "sum", 4);

  public static final RollCategoryOfAKind YACHT =
    new RollCategoryOfAKind("Yacht", "50 pts", 5) {
      public int getScore(NumberCube[] dice) { return 50; }
    };

  public static final RollCategoryStraight SMALL_STRAIGHT =
    new RollCategoryStraight("Small Straight", 4, 30);
  public static final RollCategoryStraight LARGE_STRAIGHT =
    new RollCategoryStraight("Large Straight", 5, 40);
  /**/
  public static final RollCategory CHANCE = new RollCategory("Chance", "sum") {
    public boolean isMatch(NumberCube[] dice) { return true; }

    public int getScore(NumberCube[] dice) {
      int score = 0;
      for (int i = 0; i < dice.length; i++) {
        score += dice[i].value;
      }
      return score;
    }
  };

  public static final RollCategory FULL_HOUSE = new RollCategory("Full House", "25 pts") {
    public boolean isMatch(NumberCube[] dice) {
      int[] counts = new int[Game.DICE_SIDES];
      for (int i = 0; i < dice.length; i++) {
        counts[dice[i].value - 1]++;
      }
      boolean hasThree = false;
      boolean hasTwo   = false;
      for (int i = 0; i < counts.length; i++) {
        if (counts[i] == 3) {
          hasThree = true;
        } else if (counts[i] == 2) {
          hasTwo = true;
        }
      }
      return hasThree && hasTwo;
    }

    public int getScore(NumberCube[] dice) { return 25; }
  };

  public final String name;
  public final String scoring;

  public RollCategory(String name, String scoring) {
    this.name    = name;
    this.scoring = scoring;
  }

  public String getName() { return name; }
  public String getName(Integer points) { return getName(); }
  public String getScoring() { return scoring; }

  public abstract boolean isMatch(NumberCube[] dice);

  public abstract int getScore(NumberCube[] dice);
}
