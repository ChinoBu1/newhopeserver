package com.server.newhopeserver;

public final class polyUtils {

    private polyUtils() {
    }

    public static Polynomial PolyEscalar(long a, Polynomial b) {
        long[] resultcoef = new long[b.GetGrado() + 1];
        for (int i = 0; i <= b.GetGrado(); i++) {
            resultcoef[i] = b.GetCoef()[i] * a;
        }
        Polynomial result = new Polynomial(resultcoef);
        return result;
    }

    public static Polynomial PolySum(Polynomial a, Polynomial b) {
        long[] resultcoef = new long[Math.max(a.GetGrado(), b.GetGrado()) + 1];
        if (a.GetGrado() < b.GetGrado()) {
            Polynomial temp = a;
            a = b;
            b = temp;
        }
        int i;
        for (i = 0; i <= b.GetGrado(); i++) {
            resultcoef[i] = a.GetCoef()[i] + b.GetCoef()[i];
        }
        for (int j = i; j <= a.GetGrado(); j++) {
            resultcoef[j] = a.GetCoef()[j];
        }
        Polynomial result = new Polynomial(resultcoef);
        return result;
    }

    public static Polynomial PolyMult(Polynomial a, Polynomial b) {
        long[] resultcoef = new long[a.GetGrado() + b.GetGrado() + 1];
        int i = 0;
        while (i <= a.GetGrado()) {
            long tempa = a.GetCoef()[i];
            int j = 0;
            while (j <= b.GetGrado()) {
                long tempb = b.GetCoef()[j];
                if (i == 0) {
                    resultcoef[j] = tempa * tempb;
                } else {
                    long valor = resultcoef[j + i] + tempa * tempb;
                    resultcoef[j + i] = valor;
                }
                j++;
            }
            i++;
        }
        Polynomial c = new Polynomial(resultcoef);
        return c;
    }

    public static Polynomial PolyModInt(Polynomial a, long q) {
        long[] resultcoef = new long[a.GetGrado() + 1];
        for (int i = 0; i <= a.GetGrado(); i++) {
            if (a.GetCoef()[i] % q < 0) {
                resultcoef[i] = a.GetCoef()[i] % q + q;
            } else {
                resultcoef[i] = a.GetCoef()[i] % q;
            }
        }
        Polynomial b = new Polynomial(resultcoef);
        return b;
    }

    public static Polynomial[] PolyDiv(Polynomial a, Polynomial F) {
        Polynomial q = new Polynomial();
        Polynomial r = a;
        if (a.GetGrado() < F.GetGrado()) {
            return new Polynomial[] { new Polynomial(), r };
        }
        while (r.GetGrado() >= F.GetGrado()) {
            int temp1 = r.GetGrado() - F.GetGrado();
            long[] coef_t = new long[temp1 + 1];
            long temp2 = r.GetCoef()[r.GetGrado()] / F.GetCoef()[F.GetGrado()];
            coef_t[temp1] = temp2;
            Polynomial t = new Polynomial(coef_t);
            q = PolySum(q, t);
            r = PolySum(r, PolyEscalar(-1, PolyMult(F, t)));
        }
        return new Polynomial[] { q, r };
    }

    public static Polynomial[] PolyDiv(Polynomial a, Polynomial F, long P) {
        Polynomial q = new Polynomial();
        Polynomial r = a;
        if (a.GetGrado() < F.GetGrado()) {
            return new Polynomial[] { new Polynomial(), r };
        }
        while (r.GetGrado() >= F.GetGrado() && r.GetGrado() > 0) {
            int temp1 = r.GetGrado() - F.GetGrado();
            long[] coef_t = new long[temp1 + 1];
            if (r.GetCoef()[r.GetGrado()] < F.GetCoef()[F.GetGrado()])
                r.GetCoef()[r.GetGrado()] += P;
            long temp2 = r.GetCoef()[r.GetGrado()] / F.GetCoef()[F.GetGrado()];
            coef_t[temp1] = temp2;
            Polynomial t = new Polynomial(coef_t);
            q = PolySum(q, t);
            r = PolySum(r, PolyEscalar(-1, PolyMult(F, t)));
        }
        return new Polynomial[] { q, r };
    }

    public static Polynomial PolyModF(Polynomial a, Polynomial F) {
        return PolyDiv(a, F)[1];
    }

    public static Polynomial PolyInvModF(Polynomial a, Polynomial F, long p) {
        Polynomial t = new Polynomial();
        Polynomial newt = new Polynomial(new long[] { 1 });
        Polynomial r = F;
        Polynomial newr = a;
        while (r.GetGrado() != 0) {
            Polynomial q = PolyModInt(PolyDiv(r, newr, p)[0], p);
            Polynomial temp = r;
            r = newr;
            newr = PolyModInt(PolySum(temp, PolyEscalar(-1, PolyMult(q, newr))), p);

            temp = t;
            t = newt;
            newt = PolyModInt(PolySum(temp, PolyEscalar(-1, PolyMult(q, newt))), p);
        }
        if (newr.GetGrado() > 0)
            return a;
        return newt;
    }

}
