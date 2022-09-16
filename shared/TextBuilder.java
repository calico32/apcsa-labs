package shared;

import java.util.ArrayList;

public class TextBuilder {
  public static final String RESET = "\033[0m";

  public ArrayList<String> segments;
  public TextBuilder() { segments = new ArrayList<String>(); }
  public TextBuilder(String initial) {
    segments = new ArrayList<String>();
    segments.add(initial);
  }

  public TextBuilder text(String text) {
    segments.add(text);
    return this;
  }

  @Override
  public String toString() {
    String result = "";
    for (String segment : this.segments) {
      result += segment + RESET;
    }
    return result;
  }

  public String string() { return toString(); }

  public void print() { System.out.println(toString()); }

  TextBuilder style(String color) {
    int index = this.segments.size() - 1;
    String segment = this.segments.get(index);
    this.segments.set(index, color + segment);
    return this;
  }

  // Regular Colors
  public static final String BLACK = "\033[0;30m";  // BLACK
  public static final String RED = "\033[0;31m";    // RED
  public static final String GREEN = "\033[0;32m";  // GREEN
  public static final String YELLOW = "\033[0;33m"; // YELLOW
  public static final String BLUE = "\033[0;34m";   // BLUE
  public static final String PURPLE = "\033[0;35m"; // PURPLE
  public static final String CYAN = "\033[0;36m";   // CYAN
  public static final String WHITE = "\033[0;37m";  // WHITE

  public TextBuilder black() { return style(BLACK); }
  public TextBuilder red() { return style(RED); }
  public TextBuilder green() { return style(GREEN); }
  public TextBuilder yellow() { return style(YELLOW); }
  public TextBuilder blue() { return style(BLUE); }
  public TextBuilder purple() { return style(PURPLE); }
  public TextBuilder cyan() { return style(CYAN); }
  public TextBuilder white() { return style(WHITE); }

  // Bold
  public static final String BLACK_BOLD = "\033[1;30m";  // BLACK
  public static final String RED_BOLD = "\033[1;31m";    // RED
  public static final String GREEN_BOLD = "\033[1;32m";  // GREEN
  public static final String YELLOW_BOLD = "\033[1;33m"; // YELLOW
  public static final String BLUE_BOLD = "\033[1;34m";   // BLUE
  public static final String PURPLE_BOLD = "\033[1;35m"; // PURPLE
  public static final String CYAN_BOLD = "\033[1;36m";   // CYAN
  public static final String WHITE_BOLD = "\033[1;37m";  // WHITE

  public TextBuilder blackBold() { return style(BLACK_BOLD); }
  public TextBuilder redBold() { return style(RED_BOLD); }
  public TextBuilder greenBold() { return style(GREEN_BOLD); }
  public TextBuilder yellowBold() { return style(YELLOW_BOLD); }
  public TextBuilder blueBold() { return style(BLUE_BOLD); }
  public TextBuilder purpleBold() { return style(PURPLE_BOLD); }
  public TextBuilder cyanBold() { return style(CYAN_BOLD); }
  public TextBuilder whiteBold() { return style(WHITE_BOLD); }

  // Underline
  public static final String BLACK_UNDERLINED = "\033[4;30m";  // BLACK
  public static final String RED_UNDERLINED = "\033[4;31m";    // RED
  public static final String GREEN_UNDERLINED = "\033[4;32m";  // GREEN
  public static final String YELLOW_UNDERLINED = "\033[4;33m"; // YELLOW
  public static final String BLUE_UNDERLINED = "\033[4;34m";   // BLUE
  public static final String PURPLE_UNDERLINED = "\033[4;35m"; // PURPLE
  public static final String CYAN_UNDERLINED = "\033[4;36m";   // CYAN
  public static final String WHITE_UNDERLINED = "\033[4;37m";  // WHITE

  public TextBuilder blackUnderlined() { return style(BLACK_UNDERLINED); }
  public TextBuilder redUnderlined() { return style(RED_UNDERLINED); }
  public TextBuilder greenUnderlined() { return style(GREEN_UNDERLINED); }
  public TextBuilder yellowUnderlined() { return style(YELLOW_UNDERLINED); }
  public TextBuilder blueUnderlined() { return style(BLUE_UNDERLINED); }
  public TextBuilder purpleUnderlined() { return style(PURPLE_UNDERLINED); }
  public TextBuilder cyanUnderlined() { return style(CYAN_UNDERLINED); }
  public TextBuilder whiteUnderlined() { return style(WHITE_UNDERLINED); }

  // Background
  public static final String BLACK_BACKGROUND = "\033[40m";  // BLACK
  public static final String RED_BACKGROUND = "\033[41m";    // RED
  public static final String GREEN_BACKGROUND = "\033[42m";  // GREEN
  public static final String YELLOW_BACKGROUND = "\033[43m"; // YELLOW
  public static final String BLUE_BACKGROUND = "\033[44m";   // BLUE
  public static final String PURPLE_BACKGROUND = "\033[45m"; // PURPLE
  public static final String CYAN_BACKGROUND = "\033[46m";   // CYAN
  public static final String WHITE_BACKGROUND = "\033[47m";  // WHITE

  public TextBuilder bgBlack() { return style(BLACK_BACKGROUND); }
  public TextBuilder bgRed() { return style(RED_BACKGROUND); }
  public TextBuilder bgGreen() { return style(GREEN_BACKGROUND); }
  public TextBuilder bgYellow() { return style(YELLOW_BACKGROUND); }
  public TextBuilder bgBlue() { return style(BLUE_BACKGROUND); }
  public TextBuilder bgPurple() { return style(PURPLE_BACKGROUND); }
  public TextBuilder bgCyan() { return style(CYAN_BACKGROUND); }
  public TextBuilder bgWhite() { return style(WHITE_BACKGROUND); }

  // High Intensity
  public static final String BLACK_BRIGHT = "\033[0;90m";  // BLACK
  public static final String RED_BRIGHT = "\033[0;91m";    // RED
  public static final String GREEN_BRIGHT = "\033[0;92m";  // GREEN
  public static final String YELLOW_BRIGHT = "\033[0;93m"; // YELLOW
  public static final String BLUE_BRIGHT = "\033[0;94m";   // BLUE
  public static final String PURPLE_BRIGHT = "\033[0;95m"; // PURPLE
  public static final String CYAN_BRIGHT = "\033[0;96m";   // CYAN
  public static final String WHITE_BRIGHT = "\033[0;97m";  // WHITE

  public TextBuilder blackBright() { return style(BLACK_BRIGHT); }
  public TextBuilder redBright() { return style(RED_BRIGHT); }
  public TextBuilder greenBright() { return style(GREEN_BRIGHT); }
  public TextBuilder yellowBright() { return style(YELLOW_BRIGHT); }
  public TextBuilder blueBright() { return style(BLUE_BRIGHT); }
  public TextBuilder purpleBright() { return style(PURPLE_BRIGHT); }
  public TextBuilder cyanBright() { return style(CYAN_BRIGHT); }
  public TextBuilder whiteBright() { return style(WHITE_BRIGHT); }

  // Bold High Intensity
  public static final String BLACK_BOLD_BRIGHT = "\033[1;90m";  // BLACK
  public static final String RED_BOLD_BRIGHT = "\033[1;91m";    // RED
  public static final String GREEN_BOLD_BRIGHT = "\033[1;92m";  // GREEN
  public static final String YELLOW_BOLD_BRIGHT = "\033[1;93m"; // YELLOW
  public static final String BLUE_BOLD_BRIGHT = "\033[1;94m";   // BLUE
  public static final String PURPLE_BOLD_BRIGHT = "\033[1;95m"; // PURPLE
  public static final String CYAN_BOLD_BRIGHT = "\033[1;96m";   // CYAN
  public static final String WHITE_BOLD_BRIGHT = "\033[1;97m";  // WHITE

  public TextBuilder blackBoldBright() { return style(BLACK_BOLD_BRIGHT); }
  public TextBuilder redBoldBright() { return style(RED_BOLD_BRIGHT); }
  public TextBuilder greenBoldBright() { return style(GREEN_BOLD_BRIGHT); }
  public TextBuilder yellowBoldBright() { return style(YELLOW_BOLD_BRIGHT); }
  public TextBuilder blueBoldBright() { return style(BLUE_BOLD_BRIGHT); }
  public TextBuilder purpleBoldBright() { return style(PURPLE_BOLD_BRIGHT); }
  public TextBuilder cyanBoldBright() { return style(CYAN_BOLD_BRIGHT); }
  public TextBuilder whiteBoldBright() { return style(WHITE_BOLD_BRIGHT); }

  // High Intensity backgrounds
  public static final String BLACK_BACKGROUND_BRIGHT = "\033[0;100m";  // BLACK
  public static final String RED_BACKGROUND_BRIGHT = "\033[0;101m";    // RED
  public static final String GREEN_BACKGROUND_BRIGHT = "\033[0;102m";  // GREEN
  public static final String YELLOW_BACKGROUND_BRIGHT = "\033[0;103m"; // YELLOW
  public static final String BLUE_BACKGROUND_BRIGHT = "\033[0;104m";   // BLUE
  public static final String PURPLE_BACKGROUND_BRIGHT = "\033[0;105m"; // PURPLE
  public static final String CYAN_BACKGROUND_BRIGHT = "\033[0;106m";   // CYAN
  public static final String WHITE_BACKGROUND_BRIGHT = "\033[0;107m";  // WHITE

  public TextBuilder bgBlackBright() { return style(BLACK_BACKGROUND_BRIGHT); }
  public TextBuilder bgRedBright() { return style(RED_BACKGROUND_BRIGHT); }
  public TextBuilder bgGreenBright() { return style(GREEN_BACKGROUND_BRIGHT); }
  public TextBuilder bgYellowBright() {
    return style(YELLOW_BACKGROUND_BRIGHT);
  }
  public TextBuilder bgBlueBright() { return style(BLUE_BACKGROUND_BRIGHT); }
  public TextBuilder bgPurpleBright() {
    return style(PURPLE_BACKGROUND_BRIGHT);
  }
  public TextBuilder bgCyanBright() { return style(CYAN_BACKGROUND_BRIGHT); }
  public TextBuilder bgWhiteBright() { return style(WHITE_BACKGROUND_BRIGHT); }
}
