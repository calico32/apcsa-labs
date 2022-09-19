package shared;

public class TextSegment {
  public String text;
  public String format;

  TextSegment() { format = ""; }
  public TextSegment(Object text) {
    this();
    this.text = text.toString();
  }
  public TextSegment(String format, Object... args) {
    this();
    this.text = String.format(format, args);
  }
  public TextSegment(TextSegment... segments) {
    this();
    StringBuilder builder = new StringBuilder();
    for (TextSegment segment : segments) {
      builder.append(segment);
    }
    this.text = builder.toString();
  }

  @Override
  public String toString() {
    return format + text + RESET;
  }

  public String string() { return toString(); }

  public static final String RESET = "\033[0m";

  public void print() { System.out.print(toString()); }
  public void println() {
    print();
    System.out.println();
  }

  TextSegment style(String color) {
    format += color;
    return this;
  }

  // Styles
  public static final String BOLD          = "\033[1m";
  public static final String ITALIC        = "\033[3m";
  public static final String UNDERLINE     = "\033[4m";
  public static final String STRIKETHROUGH = "\033[9m";

  public TextSegment bold() { return style(BOLD); }
  public TextSegment italic() { return style(ITALIC); }
  public TextSegment underline() { return style(UNDERLINE); }
  public TextSegment strikethrough() { return style(STRIKETHROUGH); }

  // Regular Colors
  public static final String BLACK  = "\033[0;30m"; // BLACK
  public static final String RED    = "\033[0;31m"; // RED
  public static final String GREEN  = "\033[0;32m"; // GREEN
  public static final String YELLOW = "\033[0;33m"; // YELLOW
  public static final String BLUE   = "\033[0;34m"; // BLUE
  public static final String PURPLE = "\033[0;35m"; // PURPLE
  public static final String CYAN   = "\033[0;36m"; // CYAN
  public static final String WHITE  = "\033[0;37m"; // WHITE

  public TextSegment black() { return style(BLACK); }
  public TextSegment red() { return style(RED); }
  public TextSegment green() { return style(GREEN); }
  public TextSegment yellow() { return style(YELLOW); }
  public TextSegment blue() { return style(BLUE); }
  public TextSegment purple() { return style(PURPLE); }
  public TextSegment cyan() { return style(CYAN); }
  public TextSegment white() { return style(WHITE); }

  // Background
  public static final String BLACK_BACKGROUND  = "\033[40m"; // BLACK
  public static final String RED_BACKGROUND    = "\033[41m"; // RED
  public static final String GREEN_BACKGROUND  = "\033[42m"; // GREEN
  public static final String YELLOW_BACKGROUND = "\033[43m"; // YELLOW
  public static final String BLUE_BACKGROUND   = "\033[44m"; // BLUE
  public static final String PURPLE_BACKGROUND = "\033[45m"; // PURPLE
  public static final String CYAN_BACKGROUND   = "\033[46m"; // CYAN
  public static final String WHITE_BACKGROUND  = "\033[47m"; // WHITE

  public TextSegment bgBlack() { return style(BLACK_BACKGROUND); }
  public TextSegment bgRed() { return style(RED_BACKGROUND); }
  public TextSegment bgGreen() { return style(GREEN_BACKGROUND); }
  public TextSegment bgYellow() { return style(YELLOW_BACKGROUND); }
  public TextSegment bgBlue() { return style(BLUE_BACKGROUND); }
  public TextSegment bgPurple() { return style(PURPLE_BACKGROUND); }
  public TextSegment bgCyan() { return style(CYAN_BACKGROUND); }
  public TextSegment bgWhite() { return style(WHITE_BACKGROUND); }

  // High Intensity
  public static final String BLACK_BRIGHT  = "\033[0;90m"; // BLACK
  public static final String RED_BRIGHT    = "\033[0;91m"; // RED
  public static final String GREEN_BRIGHT  = "\033[0;92m"; // GREEN
  public static final String YELLOW_BRIGHT = "\033[0;93m"; // YELLOW
  public static final String BLUE_BRIGHT   = "\033[0;94m"; // BLUE
  public static final String PURPLE_BRIGHT = "\033[0;95m"; // PURPLE
  public static final String CYAN_BRIGHT   = "\033[0;96m"; // CYAN
  public static final String WHITE_BRIGHT  = "\033[0;97m"; // WHITE

  public TextSegment blackBright() { return style(BLACK_BRIGHT); }
  public TextSegment redBright() { return style(RED_BRIGHT); }
  public TextSegment greenBright() { return style(GREEN_BRIGHT); }
  public TextSegment yellowBright() { return style(YELLOW_BRIGHT); }
  public TextSegment blueBright() { return style(BLUE_BRIGHT); }
  public TextSegment purpleBright() { return style(PURPLE_BRIGHT); }
  public TextSegment cyanBright() { return style(CYAN_BRIGHT); }
  public TextSegment whiteBright() { return style(WHITE_BRIGHT); }

  // High Intensity backgrounds
  public static final String BLACK_BACKGROUND_BRIGHT  = "\033[0;100m"; // BLACK
  public static final String RED_BACKGROUND_BRIGHT    = "\033[0;101m"; // RED
  public static final String GREEN_BACKGROUND_BRIGHT  = "\033[0;102m"; // GREEN
  public static final String YELLOW_BACKGROUND_BRIGHT = "\033[0;103m"; // YELLOW
  public static final String BLUE_BACKGROUND_BRIGHT   = "\033[0;104m"; // BLUE
  public static final String PURPLE_BACKGROUND_BRIGHT = "\033[0;105m"; // PURPLE
  public static final String CYAN_BACKGROUND_BRIGHT   = "\033[0;106m"; // CYAN
  public static final String WHITE_BACKGROUND_BRIGHT  = "\033[0;107m"; // WHITE

  public TextSegment bgBlackBright() { return style(BLACK_BACKGROUND_BRIGHT); }
  public TextSegment bgRedBright() { return style(RED_BACKGROUND_BRIGHT); }
  public TextSegment bgGreenBright() { return style(GREEN_BACKGROUND_BRIGHT); }
  public TextSegment bgYellowBright() {
    return style(YELLOW_BACKGROUND_BRIGHT);
  }
  public TextSegment bgBlueBright() { return style(BLUE_BACKGROUND_BRIGHT); }
  public TextSegment bgPurpleBright() {
    return style(PURPLE_BACKGROUND_BRIGHT);
  }
  public TextSegment bgCyanBright() { return style(CYAN_BACKGROUND_BRIGHT); }
  public TextSegment bgWhiteBright() { return style(WHITE_BACKGROUND_BRIGHT); }

  // Custom
  // https://github.com/busyloop/lolcat/blob/f4cca5601ea57df2b5b3c98feea8ad05f4421039/lib/lolcat/lol.rb#L36
  String getRainbowColor(double freq, int i) {
    double red   = Math.sin(freq * i + 0) * 127 + 128;
    double green = Math.sin(freq * i + 2 * Math.PI / 3) * 127 + 128;
    double blue  = Math.sin(freq * i + 4 * Math.PI / 3) * 127 + 128;
    return "\033[38;2;" + (int)red + ";" + (int)green + ";" + (int)blue + "m";
  }

  public TextSegment rainbow(int start) {
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < text.length(); i++) {
      sb.append(getRainbowColor(0.1, i + start));
      sb.append(text.charAt(i));
    }
    text = sb.toString();
    return this;
  }

  public TextSegment rainbow() { return rainbow(0); }

  public TextSegment randomRainbow() {
    return rainbow((int)(Math.random() * 100));
  }
}
