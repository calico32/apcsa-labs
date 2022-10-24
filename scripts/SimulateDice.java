package scripts;
import java.util.HashMap;

class SimulateDice {
  final static int totalRounds   = 100_000;
  final static int rollsPerRound = 3;
  final static int handSize      = 5;
  static int threeOfAKinds       = 0;
  static int fourOfAKinds        = 0;
  static int fiveOfAKinds        = 0;

  static final HashMap<Integer, Integer> counts = new HashMap<>();
  static int leftToRoll                         = handSize;

  public static void main(String[] args) {
    for (int round = 0; round < totalRounds; round++) {
      counts.clear();

      leftToRoll = handSize;

      for (int roll = 0; roll < rollsPerRound; roll++) {
        rollHand();
        findBestChoice();

        // logCounts();
        // System.out.println();

        counts.clear();
        counts.put(best, bestCount);

        leftToRoll = handSize - bestCount;

        if (leftToRoll == 0)
          break;
      }

      if (bestCount >= 3) {
        threeOfAKinds++;
        if (bestCount >= 4) {
          fourOfAKinds++;
          if (bestCount >= 5) {
            fiveOfAKinds++;
          }
        }
      }

      // break;
    }

    System.out.println(String.format("Simulated %d rounds", totalRounds));
    System.out.println(String.format(
      "Three of a kinds: %d (%f%%)",
      threeOfAKinds,
      (double)threeOfAKinds / totalRounds * 100
    ));
    System.out.println(String.format(
      "Four of a kinds: %d (%f%%)", fourOfAKinds, (double)fourOfAKinds / totalRounds * 100
    ));
    System.out.println(String.format(
      "Five of a kinds: %d (%f%%)", fiveOfAKinds, (double)fiveOfAKinds / totalRounds * 100
    ));
  }

  static int best;
  static int bestCount = -1;
  static int findBestChoice() {
    best      = 0;
    bestCount = -1;

    counts.forEach((k, v) -> {
      if (v > bestCount) {
        best      = k;
        bestCount = v;
      }
    });

    return best;
  }

  static void rollHand() {
    for (int i = 0; i < leftToRoll; i++) {
      int x = roll();

      Integer count = counts.get(x);
      if (count == null) {
        counts.put(x, 1);
      } else {
        counts.put(x, count + 1);
      }
    }
  }

  static int roll() { return (int)(Math.random() * 6) + 1; }

  static void logCounts() {
    counts.forEach((k, v) -> {
      System.out.println(String.format("%d rolled %d times", k, v));
    });
  }
}
