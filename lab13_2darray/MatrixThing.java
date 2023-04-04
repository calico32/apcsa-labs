package lab13_2darray;

import shared.Input;

import java.util.ArrayList;

import static shared.TextHelpers.println;
import static shared.TextHelpers.text;

public class MatrixThing {
    public static void main(String[] args) {
        println(text("MatrixThing").bold().randomRainbow());

        var rows = Input.readInt("# of rows");
        var columns = Input.readInt("# of columns");

        var array = new int[rows][columns];

        var input = new ArrayList<Integer>();

        while (input.size() < rows * columns) {
            var needed = rows * columns - input.size();
            String in = Input.readString(text("Enter numbers to insert (%d more)", needed));
            var split = in.split("\\s+");
            for (var s : split) {
                try {
                    input.add(Integer.parseInt(s));
                } catch (NumberFormatException ignored) {
                    println(text("skipped '%s': not a number", s).red());
                }
            }
        }

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                array[i][j] = input.get(i * columns + j);
            }
        }

        println(text("matrix:"));
        displayArray(array);
        println();
        println(text("sums:"));
        displaySumRowColumn(array);
        println();
        println(text("transposed:"));
        displayArray(transpose(array));
        println();
        println(text("largest: %d", findLargest(array)));
    }

    public static void displayArray(int[][] array) {
        for (int[] row : array) {
            for (int cell : row) {
                System.out.printf("%4d", cell);
            }
            System.out.println();
        }
    }

    public static void displaySumRowColumn(int[][] array) {
        int sumRow = 0;
        int sumColumn = 0;
        for (int ri = 0; ri < array.length; ri++) {
            var row = array[ri];
            for (int cell : row) {
                sumRow += cell;
            }
            System.out.printf("row %2d  %4d\n", ri, sumRow);
            sumRow = 0;
        }
        System.out.println();
        for (int ci = 0; ci < array[0].length; ci++) {
            System.out.printf("col %2d\t", ci);
        }
        System.out.println();
        for (int ci = 0; ci < array[0].length; ci++) {
            for (int[] row : array) {
                sumColumn += row[ci];
            }
            System.out.printf("%6d\t", sumColumn);
            sumColumn = 0;
        }
        System.out.println();
    }

    public static int[][] transpose(int[][] matrix) {
        int[][] transposed = new int[matrix[0].length][matrix.length];

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                transposed[j][i] = matrix[i][j];
            }
        }

        return transposed;
    }

    public static int findLargest(int[][] array) {
        int largest = array[0][0];
        for (int[] row : array) {
            for (int cell : row) {
                if (cell > largest) {
                    largest = cell;
                }
            }
        }
        return largest;
    }
}
