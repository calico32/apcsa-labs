package lab15_recursion;

import shared.Input;

import static shared.TextHelpers.println;
import static shared.TextHelpers.text;

public class Exponentiator {
    public static double pow(double base, int exponent) {
        if (exponent == 0) {
            return 1;
        }

        return base * pow(base, exponent - 1);
    }

    public static void main(String[] args) {
        println(text("Exponentiator").bold().randomRainbow());

        double start = Input.readDouble("Enter a base");
        int exponent = Input.readInt(1, 2048, text("Enter an exponent (1-2048)"));

        double result = pow(start, exponent);

        println(text("The result is %f", result));
    }
}
