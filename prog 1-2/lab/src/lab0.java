import java.util.Arrays;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class lab0 {
    public static void main(String[] args) {
        System.out.println(IntStream.range(3,16).boxed().collect(Collectors.toList()).stream().filter(number -> number % 2 != 0).collect(Collectors.toList()));
        final long[] w = f1();
        final double[] x = f2();
        printArray(f3(x, w));
    }

    static void printArray(double[][] w1) {
        System.out.println(
                Arrays.stream(w1).map(arr -> Arrays.stream(arr).mapToObj(value -> String.format("%.2f", value)).collect(Collectors.joining(" ", "[", "]"))).collect(Collectors.joining("\n"))
        );
    }

    static long[] f1() {
        return IntStream.range(3,16).filter(number -> number % 2 != 0).mapToLong(i -> i).toArray();
    }

    static double[] f2() {
        Random rnd = new Random();
        return IntStream.range(0, 16).mapToDouble(i -> -6.0 + rnd.nextDouble() * 15.0).toArray();
    }

    static double[][] f3(double[] x, long[] w) {
        final double[][] w1 = new double[7][16];
        for (int i = 0; i < w1.length; i++) {
            for (int j = 0; j < w1[i].length; j++) {
                double curEl;
                curEl = switch ((int) w[i]) {
                    case 13 -> Math.pow(2 * (Math.pow((3 / (2 + Math.pow(1.0 / 3 * x[j], x[j]))), (Math.pow((x[j] - 1) / x[j], 2)))), 2);
                    case 3, 5, 11 -> Math.pow(Math.asin(Math.sin(x[j])), 1.0 / 3);
                    default -> Math.tan(Math.log(Math.pow(Math.sin(Math.pow(Math.cos(x[j]), Math.sin(x[j]))), 2)));
                };
                w1[i][j] = curEl;
            }
        }
        return w1;
    }
}