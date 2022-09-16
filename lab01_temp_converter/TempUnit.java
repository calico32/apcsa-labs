package lab01_temp_converter;

enum TempUnit {
  F("fahrenheit", "°F", "f"),
  C("celsius", "°C", "c"),
  K("kelvin", "K", "k");

  final String name;
  final String symbol;
  final String[] aliases;

  TempUnit(String name, String symbol, String... aliases) {
    this.name = name;
    this.symbol = symbol;
    this.aliases = aliases;
  }

  @Override
  public String toString() {
    return symbol;
  }

  double convert(double value, TempUnit to) {
    if (this == to) {
      return value;
    }

    if (this == F) {
      if (to == C) {
        return f_c(value);
      } else if (to == K) {
        return f_k(value);
      }
    } else if (this == C) {
      if (to == F) {
        return c_f(value);
      } else if (to == K) {
        return c_k(value);
      }
    } else if (this == K) {
      if (to == F) {
        return k_f(value);
      } else if (to == C) {
        return k_c(value);
      }
    }

    throw new IllegalArgumentException("Cannot convert from " + this + " to " +
                                       to);
  }

  static TempUnit fromString(String s) {
    for (TempUnit unit : TempUnit.values()) {
      if (unit.name.equalsIgnoreCase(s) || unit.symbol.equalsIgnoreCase(s)) {
        return unit;
      }
      for (String alias : unit.aliases) {
        if (alias.equalsIgnoreCase(s)) {
          return unit;
        }
      }
    }

    throw new IllegalArgumentException("Invalid unit: " + s);
  }

  static double f_c(double f) { return (f - 32) * 5 / 9; }
  static double c_f(double c) { return c * 9 / 5 + 32; }

  static double k_c(double k) { return k - 273.15; }
  static double c_k(double c) { return c + 273.15; }

  static double f_k(double f) { return c_k(f_c(f)); }
  static double k_f(double k) { return c_f(k_c(k)); }
}
