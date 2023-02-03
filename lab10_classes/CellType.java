package lab10_classes;

import java.util.function.Consumer;

public enum CellType {
  NONE("🌲", p -> {}),
  GAS_STATION("⛽", p -> p.addGas(100)),

  TACOS("🌮", p -> p.addFood(60)),
  ITALIAN_RESTAURANT("🍝", p -> p.addFood(60)),
  SANDWICH_SHOP("🥪", p -> p.addFood(40).addDrink(40)),

  JUICE_BAR("🥤", p -> p.addDrink(60)),
  COFFEE_HOUSE("☕", p -> p.addDrink(60)),

  THEATRE("🎭", p -> p.addEnjoyment(60)),
  GARDEN("🌻", p -> p.addEnjoyment(60)),
  NIGHTCLUB("🍸", p -> p.addEnjoyment(40).addDrink(40)),
  FAIR("🎡", p -> p.addEnjoyment(40).addFood(20).addDrink(20)),
  BALLROOM("💃", p -> p.addEnjoyment(100).addFood(-10).addDrink(-15)),

  HOME("🏠", p -> p.setState(Partner.State.WIN_EARLY));

  public final String symbol;
  public Consumer<Partner> effect;

  private CellType(String symbol, Consumer<Partner> effect) {
    this.symbol = symbol;
    this.effect = effect;
  }
}
