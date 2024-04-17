package com.server.newhopeserver;

import java.util.Arrays;

public class Polynomial {
    private final long[] coef;
    private final int grado;

    public Polynomial() {
        this.coef = new long[1];
        this.coef[0] = 0;
        this.grado = -1;
    }

    public Polynomial(long[] coef) {
        int i = coef.length;
        while (i > 1 && coef[i - 1] == 0) {
            i--;
        }
        this.coef = coef;
        this.grado = i - 1;
    }

    public long[] GetCoef() {
        return this.coef;
    }

    public int GetGrado() {
        return this.grado;
    }

    @Override
    public String toString() {
        String p = new String();
        for (int i = 0; i <= this.grado; i++) {
            long temp = this.coef[i];
            if (temp == 0)
                continue;
            if (i == 0) {
                p = p + String.format("%d ", temp);
            } else {
                if (temp == 1) {
                    if (p.equals("")) {
                        p = p + String.format("x^%d ", i);
                    } else {
                        p = p + String.format("+x^%d ", i);
                    }
                } else if (temp == -1) {
                    p = p + String.format("-x^%d ", i);
                } else {
                    if (p.equals("")) {
                        p = p + String.format("%d*x^%d ", temp, i);
                    } else {
                        p = p + String.format("%+d*x^%d ", temp, i);
                    }
                }
            }

        }
        if (p.equals(""))
            p = "0";
        return p;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof Polynomial)) {
            return false;
        }
        Polynomial c = (Polynomial) o;
        return Arrays.equals(c.GetCoef(), this.coef);
    }
}