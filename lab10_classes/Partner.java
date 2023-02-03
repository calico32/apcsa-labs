package lab10_classes;

public class Partner {
  public enum State {
    PLAYING,
    WIN,
    WIN_EARLY,
    LOSE_GAS,
    LOSE_FOOD,
    LOSE_DRINK,
    LOSE_ENJOYMENT,
  }

  public final int HUNGRY  = 1 << 0;
  public final int THIRSTY = 1 << 1;
  public final int BORED   = 1 << 2;

  private State state = State.PLAYING;
  private int flags   = 0;

  public State getState() { return state; }
  public boolean is(int flags) { return (this.flags & flags) == flags; }

  public void setState(State state) { this.state = state; }
  public void setFlag(int flag) { flags |= flag; }
  public void clearFlag(int flag) { flags &= ~flag; }

  private int gas       = 100;
  private int food      = 50;
  private int drink     = 50;
  private int enjoyment = 75;
  private int time      = 100;

  public int getGas() { return gas; }
  public int getFood() { return food; }
  public int getDrink() { return drink; }
  public int getEnjoyment() { return enjoyment; }
  public int getTime() { return time; }

  public Partner addGas(int amount) {
    gas += amount;
    return this;
  }
  public Partner addFood(int amount) {
    food += amount;
    return this;
  }
  public Partner addDrink(int amount) {
    drink += amount;
    return this;
  }
  public Partner addEnjoyment(int amount) {
    enjoyment += amount;
    return this;
  }

  public void step() {
    if (gas <= 0) {
      state = State.LOSE_GAS;
    } else if (food <= 0) {
      state = State.LOSE_FOOD;
    } else if (drink <= 0) {
      state = State.LOSE_DRINK;
    } else if (enjoyment <= 0) {
      state = State.LOSE_ENJOYMENT;
    } else if (time <= 0) {
      state = State.WIN;
    } else {
      time -= 4;
    }
  }

  public void move() { gas -= random(5, 8); }

  private int random(int min, int max) {
    return (int)(Math.random() * (max - min + 1)) + min;
  }
}
