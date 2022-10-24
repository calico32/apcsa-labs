package lab02_number_cubes;

public class NumberCube {
  public int value;

  public NumberCube() { this.value = this.roll(); }

  public int roll() {
    this.value = (int)(Math.random() * 6) + 1;
    return this.value;
  }

  public static final String[] faces = {
    "╭───────╮\n│       │\n│   ●   │\n│       │\n╰───────╯",
    "╭───────╮\n│ ●     │\n│       │\n│     ● │\n╰───────╯",
    "╭───────╮\n│ ●     │\n│   ●   │\n│     ● │\n╰───────╯",
    "╭───────╮\n│ ●   ● │\n│       │\n│ ●   ● │\n╰───────╯",
    "╭───────╮\n│ ●   ● │\n│   ●   │\n│ ●   ● │\n╰───────╯",
    "╭───────╮\n│ ●   ● │\n│ ●   ● │\n│ ●   ● │\n╰───────╯",
  };

  public String draw() { return faces[this.value - 1]; }
}
