package com.company;

import java.util.Arrays;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;

public class Main {
    public static void main(String[] args) {
//        linear();
        notLinear();
    }

    private static void notLinear() {
        double[] x = {-2.75, -2, -1, 0.5, 1};
        double[] y = {-0.2, -1.1, -2.3, 0.5, 1};

//        double[] x = {1, 2, 3, 4};
//        double[] y = {6, 5, 7, 10};

//        double[] x = {-1, 0, 1};
//        double[] y = {-1, 0, 1};

        double[] a;
        double vidhilenia;
        double prevVidhilenia = Double.MAX_VALUE;
        for (int i = 0; true; i++) {
            a = mnk(x, y, x.length, i);
            vidhilenia = vidhilenia(a, x, y);
            if (prevVidhilenia - vidhilenia <=0) {
                break;
            }
            prevVidhilenia = vidhilenia;
        }
        System.out.println(Arrays.toString(a));
        System.out.println(vidhilenia);
    }

    private static void linear() {
        double[] x = {-2.75, -2, -1, 0.5, 1};
        double[] y = {-0.2, -1.1, -2.3, 0.5, 1};
//
//        double[] x = {1, 2, 3, 4};
//        double[] y = {6, 5, 7, 10};

        double[] polinom = liniarKvadratMethod(x, y);
        double sigma = vidhilenia(polinom, x, y);
        System.out.println(Arrays.toString(polinom));
        System.out.println(sigma);
    }


    private static double[] mnk(double[] x, double[] y, int n, int m) {
        double[][] A = new double[m][m];
        double[] b = new double[m];
        for (int i = 0; i < m; i++) {
            b[i] = IntStream.range(0, x.length).mapToDouble(k -> y[k] * Math.pow(x[k], k)).sum();
            for (int j = 0; j < m; j++) {
                int finalI = i;
                int finalJ = j;
                A[i][j] = Arrays.stream(x).map(v -> Math.pow(v, (double) finalI + finalJ)).sum();
            }
        }
        return Algorithm.solve(A, b);
    }

    private static double vidhilenia(double[] polinom, double[] x, double[] y) {
        double sum = IntStream.range(0, x.length)
                .mapToDouble(i -> Math.pow(func(polinom, x[i]) - y[i], 2)).sum();
        return Math.sqrt(sum / (x.length - polinom.length + 1));
    }

    private static double func(double[] polinom, double x) {
        return IntStream.range(0, polinom.length).mapToDouble(i -> polinom[i] * Math.pow(x, i)).sum();
    }

    private static double[] liniarKvadratMethod(double[] x, double[] y) {
        int m = x.length;
        double b1 = ((m * suma(x, y)) - (suma(x) * suma(y)))
                / ((m * suma(x, 2)) - (Math.pow(suma(x), 2)));
        double b0 = (suma(y) / m) - (suma(x) * b1 / m);
        return new double[]{b0, b1};
    }


    private static double suma(double[] x, int pow) {
        return DoubleStream.of(x).map(el -> Math.pow(el, pow)).sum();
    }

    private static double suma(double[] x, double[] y) {
        return IntStream.range(0, x.length).mapToDouble(i -> x[i] * y[i]).sum();
    }

    private static double suma(double[] x) {
        return DoubleStream.of(x).sum();
    }

}
