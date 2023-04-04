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

        return switch (this) {
            case F -> switch (to) {
                case C -> f_c(value);
                case K -> f_k(value);
                default -> throw new IllegalStateException("Unexpected value: " + to);
            };
            case C -> switch (to) {
                case F -> c_f(value);
                case K -> c_k(value);
                default -> throw new IllegalStateException("Unexpected value: " + to);
            };
            case K -> switch (to) {
                case F -> k_f(value);
                case C -> k_c(value);
                default -> throw new IllegalStateException("Unexpected value: " + to);
            };
        };
    }

    static TempUnit fromString(String s) {
        for (var unit : TempUnit.values()) {
            if (unit.name.equalsIgnoreCase(s) || unit.symbol.equalsIgnoreCase(s)) {
                return unit;
            }
            for (var alias : unit.aliases) {
                if (alias.equalsIgnoreCase(s)) {
                    return unit;
                }
            }
        }

        throw new IllegalArgumentException("Invalid unit: " + s);
    }

    static double f_c(double f) {
        return (f - 32) * 5 / 9;
    }

    static double c_f(double c) {
        return c * 9 / 5 + 32;
    }

    static double k_c(double k) {
        return k - 273.15;
    }

    static double c_k(double c) {
        return c + 273.15;
    }

    static double f_k(double f) {
        return c_k(f_c(f));
    }

    static double k_f(double k) {
        return c_f(k_c(k));
    }
}
