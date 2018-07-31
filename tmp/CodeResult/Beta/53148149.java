/*
 * Copyright (c) 2007, Manuel D. Rossetti (rossetti@uark.edu)
 *
 * Contact:
 *	Manuel D. Rossetti, Ph.D., P.E.
 *	Department of Industrial Engineering
 *	University of Arkansas
 *	4207 Bell Engineering Center
 *	Fayetteville, AR 72701
 *	Phone: (479) 575-6756
 *	Email: rossetti@uark.edu
 *	Web: www.uark.edu/~rossetti
 *
 * This file is part of the JSL (a Java Simulation Library). The JSL is a framework
 * of Java classes that permit the easy development and execution of discrete event
 * simulation programs.
 *
 * The JSL is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * The JSL is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with the JSL (see file COPYING in the distribution);
 * if not, write to the Free Software Foundation, Inc., 51 Franklin St, Fifth Floor,
 * Boston, MA  02110-1301  USA, or see www.fsf.org
 *
 */
package jsl.utilities.random.distributions;

import jsl.modeling.JSLTooManyIterationsException;
import jsl.utilities.math.JSLMath;
import jsl.utilities.math.FunctionIfc;
import jsl.utilities.random.rng.RNStreamFactory;
import jsl.utilities.rootfinding.BisectionRootFinder;
import jsl.utilities.rootfinding.Interval;
import jsl.utilities.rootfinding.RootFinder;
import jsl.utilities.random.rng.RngIfc;

/**
 *
 */
public class Beta extends Distribution implements ContinuousDistributionIfc {

    private static IncompleteBetaFunctionFraction myContinuedFraction = new IncompleteBetaFunctionFraction();

    private static Interval myInterval = new Interval(0.0, 1.0);

    private static RootFinder myRootFinder = new BisectionRootFinder();

    private static double delta = 0.01;

    private double myAlpha1;

    private double myAlpha2;

    private double mylnBetaA1A2;

    private double myBetaA1A2;

    private double myProb;

    private BetaRootFunction myBetaRootFunction = new BetaRootFunction();

    /** Creates a Beta with parameters 1.0, 1.0
     *
     */
    public Beta() {
        this(1.0, 1.0, RNStreamFactory.getDefault().getStream());
    }

    /** Creates a beta with the supplied parameters
     *
     * @param alpha1
     * @param alpha2
     */
    public Beta(double alpha1, double alpha2) {
        this(alpha1, alpha2, RNStreamFactory.getDefault().getStream());
    }

    /** Creates a beta with the supplied parameters
     *
     * @param parameters
     */
    public Beta(double[] parameters) {
        this(parameters[0], parameters[1], RNStreamFactory.getDefault().getStream());
    }

    /** Creates a beta with the supplied parameters
     *
     * @param parameters
     * @param rng
     */
    public Beta(double[] parameters, RngIfc rng) {
        this(parameters[0], parameters[1], rng);
    }

    /** Creates a beta with the supplied parameters
     *
     * @param alpha1
     * @param alpha2
     * @param rng
     */
    public Beta(double alpha1, double alpha2, RngIfc rng) {
        super(rng);
        setParameters(alpha1, alpha2);
        myRootFinder.setMaximumIterations(200);
    }

    /** Returns a new instance of the random source with the same parameters
     *  but an independent generator
     *
     * @return
     */
    public final Beta newInstance() {
        return (new Beta(getParameters()));
    }

    /** Returns a new instance of the random source with the same parameters
     *  with the supplied RngIfc
     * @param rng
     * @return
     */
    public final Beta newInstance(RngIfc rng) {
        return (new Beta(getParameters(), rng));
    }

    /** Returns a new instance that will supply values based
     *  on antithetic U(0,1) when compared to this distribution
     *
     * @return
     */
    public final Beta newAntitheticInstance() {
        RngIfc a = myRNG.newAntitheticInstance();
        return newInstance(a);
    }

    /** Changes the parameters to the supplied values
     *
     * @param alpha1
     * @param alpha2
     */
    public final void setParameters(double alpha1, double alpha2) {
        if (alpha1 <= 0) {
            throw new IllegalArgumentException("The 1st shape parameter must be > 0");
        }
        myAlpha1 = alpha1;

        if (alpha2 <= 0) {
            throw new IllegalArgumentException("The 2nd shape parameter must be > 0");
        }
        myAlpha2 = alpha2;

        myBetaA1A2 = betaFunction(myAlpha1, myAlpha2);
//		System.out.println("Beta("+myAlpha1+","+myAlpha2+")=" + myBetaA1A2);

        mylnBetaA1A2 = logBetaFunction(myAlpha1, myAlpha2);
//		System.out.println("lnBeta("+myAlpha1+","+myAlpha2+")=" + mylnBetaA1A2);

    }

    /** Sets the parameters
     *  parameter[0] is alpha 1
     *  parameter[1] is alhpa 2
     *
     * @param parameters an array holding the parameters
     */
    public final void setParameters(double[] parameters) {
        setParameters(parameters[0], parameters[1]);
    }

    /* (non-Javadoc)
     * @see jsl.utilities.random.DistributionIfc#getMean()
     */
    public final double getMean() {
        return (myAlpha1 / (myAlpha1 + myAlpha2));
    }

    /* (non-Javadoc)
     * @see jsl.utilities.random.DistributionIfc#getParameters()
     */
    public final double[] getParameters() {
        double[] param = new double[2];
        param[0] = myAlpha1;
        param[1] = myAlpha2;
        return (param);
    }

    /* (non-Javadoc)
     * @see jsl.utilities.random.DistributionIfc#getVariance()
     */
    public final double getVariance() {
        double n = myAlpha1 * myAlpha2;
        double d = (myAlpha1 + myAlpha2) * (myAlpha1 + myAlpha2) * (myAlpha1 + myAlpha2 + 1.0);
        return n / d;
    }

    /** Computes the CDF, has accuracy to about 10e-9
     *  @param x the x value to be evaluated
     */
    public double cdf(double x) {
        if (x <= 0.0) {
            return (0.0);
        }

        if (x >= 1.0) {
            return (1.0);
        }

        return regularizedIncompleteBetaFunction(x, myAlpha1, myAlpha2, mylnBetaA1A2);
    }

    /** Inverts the CDF, has accuracy to about 10e-7
     *  @param p the probability to invert to, must be in [0,1]
     */
    public double invCDF(double p) {
        if ((p < 0.0) || (p > 1.0)) {
            throw new IllegalArgumentException("Probability must be [0,1]");
        }

        return (inverseBetaCDF(p));
    }

    /* (non-Javadoc)
     * @see jsl.utilities.random.DistributionIfc#pdf(double)
     */
    public final double pdf(double x) {
        if ((0 < x) && (x < 1)) {
            double f1 = Math.pow(x, myAlpha1 - 1.0);
            double f2 = Math.pow(1.0 - x, myAlpha2 - 1.0);
            return ((f1 * f2 / myBetaA1A2));
        } else {
            return 0.0;
        }
    }

    /** Computes Beta(z1,z2)
     *
     * @param z1
     * @param z2
     * @return
     */
    public final static double betaFunction(double z1, double z2) {
        if (z1 <= 0) {
            throw new IllegalArgumentException("The 1st parameter must be > 0");
        }

        if (z2 <= 0) {
            throw new IllegalArgumentException("The 2nd parameter must be > 0");
        }

        double n1 = Gamma.gammaFunction(z1);
        double n2 = Gamma.gammaFunction(z2);
        double d = Gamma.gammaFunction(z1 + z2);
        return ((n1 * n2) / d);
    }

    /** The natural logarithm of Beta(z1,z2)
     *
     * @param z1
     * @param z2
     * @return
     */
    public final static double logBetaFunction(double z1, double z2) {
        if (z1 <= 0) {
            throw new IllegalArgumentException("The 1st parameter must be > 0");
        }

        if (z2 <= 0) {
            throw new IllegalArgumentException("The 2nd parameter must be > 0");
        }

        double n1 = Gamma.logGammaFunction(z1);
        double n2 = Gamma.logGammaFunction(z2);
        double d = Gamma.logGammaFunction(z1 + z2);
        return (n1 + n2 - d);
    }

    /** Computes the regularized beta function at the supplied x
     *  Beta(x, a, b)/Beta(a, b)
     *
     * @param x the point to be evaluated
     * @param a alpha 1
     * @param b alpha 2
     * @return
     */
    public final static double incompleteBetaFunction(double x, double a, double b) {
        double beta = Beta.betaFunction(a, b);
        double rBeta = Beta.regularizedIncompleteBetaFunction(x, a, b);
        return (rBeta * beta);
    }

    /** Computes the regularized incomplete beta function at the supplied x
     *
     * @param x the point to be evaluated
     * @param a alpha 1
     * @param b alpha 2
     * @return
     */
    public final static double regularizedIncompleteBetaFunction(double x, double a, double b) {
        double lnbeta = logBetaFunction(a, b);
        return (regularizedIncompleteBetaFunction(x, a, b, lnbeta));
    }

    /** Computes the regularized incomplete beta function at the supplied x
     *
     * @param x the point to be evaluated
     * @param a alpha 1
     * @param b alpha 2
     * @param lnbeta the natural log of Beta(alpha1,alpha2)
     * @return
     */
    protected final static double regularizedIncompleteBetaFunction(double x, double a, double b, double lnbeta) {

        if ((x < 0.0) || (x > 1.0)) {
            throw new IllegalArgumentException("Argument x, must be in [0,1]");
        }

        if (a <= 0) {
            throw new IllegalArgumentException("The 1st shape parameter must be > 0");
        }

        if (b <= 0) {
            throw new IllegalArgumentException("The 2nd shape parameter must be > 0");
        }

        if (x == 0.0) {
            return 0.0;
        }

        if (x == 1.0) {
            return 1.0;
        }

        double bt = Math.exp(-lnbeta + a * Math.log(x) + b * Math.log(1.0 - x));

        /* use the continued fraction object instead
        if ( x < (a + 1.0)/(a+b+2.0))
        return (bt*betaContinuedFraction(x, a, b)/a);
        else
        return (1.0 - bt*betaContinuedFraction(1.0 - x, b, a)/b);
         */

        if (x < (a + 1.0) / (a + b + 2.0)) {
            return (bt / (myContinuedFraction.evaluateFraction(x, a, b) * a));
        } else {
            return (1.0 - bt / (myContinuedFraction.evaluateFraction(1.0 - x, b, a) * b));
        }

    }

    /** Computes the continued fraction for the incomplete beta function.
     *
     * @param x
     * @param a
     * @param b
     * @return
     */
    protected final static double betaContinuedFraction(double x, double a, double b) {
        double em;
        double tem;
        double d;
        double bm = 1.0;
        double bp;
        double bpp;
        double az = 1.0;
        double am = 1.0;
        double ap;
        double app;
        double aold;

        double qab = a + b;
        double qap = a + 1.0;
        double qam = a - 1.0;
        double bz = 1.0 - qab * x / qap;
        int maxi = JSLMath.getMaxNumIterations();
        double eps = JSLMath.getDefaultNumericalPrecision();

        for (int i = 1; i <= maxi; i++) {
            em = (double) i;
            tem = em + em;
            d = em * (b - em) * x / ((qam + tem) * (a + tem));
            ap = az + d * am;
            bp = bz + d * bm;
            d = -(a + em) * (qab + em) * x / ((qap + tem) * (a + tem));
            app = ap + d * az;
            bpp = bp + d * bz;
            aold = az;
            am = ap / bpp;
            bm = bp / bpp;
            az = app / bpp;
            bz = 1.0;
            if (Math.abs(az - aold) < (eps * Math.abs(az))) {
                return az;
            }
        }
        throw new JSLTooManyIterationsException("Too many iterations in computing betaContinuedFraction, increase max iterations via setMaxNumIterations()");

    }

    /** Computes the inverse of the beta CDF at the supplied probability point
     *  using an initial approximation and a root finding technique
     *
     * @param p
     * @return
     */
    protected final double inverseBetaCDF(double p) {
        if (JSLMath.equal(p, 1.0)) {
            return (1.0);
        }

        if (JSLMath.equal(p, 0.0)) {
            return (0.0);
        }

        myProb = p;

        // calculate initial approximation
        double xbeta = approximateCDF(myAlpha1, myAlpha2, p, mylnBetaA1A2);
//		System.out.println("initial approximation = " + xbeta);

        // setup the search for the root
        double xL = Math.max(0.0, xbeta - delta);
        double xU = Math.min(1.0, xbeta + delta);
        myInterval.setInterval(xL, xU);
//		System.out.println("Interval before RootFinder.findInterval: " + myInterval);
        boolean found = RootFinder.findInterval(myBetaRootFunction, myInterval);
//		System.out.println("Interval after RootFinder.findInterval: " + myInterval);
        if (found == false) {
//			System.out.println("RootFinder did not find an interval");
            myInterval.setInterval(0.0, 1.0);
        } else {
            xL = Math.max(0.0, myInterval.getLowerLimit());
            xU = Math.min(1.0, myInterval.getUpperLimit());
            myInterval.setInterval(xL, xU);
        }
//		System.out.println("Searching in " + myInterval);

        myRootFinder.setInterval(myBetaRootFunction, myInterval);
        myRootFinder.evaluate();

        if (!myRootFinder.hasConverged()) {
            throw new JSLTooManyIterationsException("Unable to invert CDF for Beta: Beta(x," + myAlpha1 + "," + myAlpha2 + ")=" + p);
        }

        xbeta = myRootFinder.getResult();

        return (xbeta);
    }

    /** Computes an approximation of the CDF for the Beta distribution
     *  Uses part of algorithm AS109, Applied Statistics, vol 26, no 1, 1977, pp 111-114
     *
     * @param pp  Alpha 1 parameter
     * @param qq  Alpha 2 parameter
     * @param a   The point to be evaluated
     * @param lnbeta The log of Beta(alpha1,alpha2)
     * @return
     */
    public final static double approximateCDF(double pp, double qq, double a, double lnbeta) {
        double r, y, t, s, h, w, x;

        r = Math.sqrt(-Math.log(a * a));
        y = r - (2.30753 + 0.27061 * r) / (1.0 + (0.99229 + 0.04481 * r) * r);

        if ((pp > 1.0) && (qq > 1.0)) {
            r = (y * y - 3.0) / 6.0;
            s = 1.0 / (pp + pp - 1.0);
            t = 1.0 / (qq + qq - 1.0);
            h = 2.0 / (s + t);
            w = y * Math.sqrt(h + r) / h - (t - s) * (r + 5.0 / 6.0 - 2.0 / (3.0 * h));
            x = pp / (pp + qq * Math.exp(w + w));
        } else {
            r = qq + qq;
            t = 1.0 / (9.0 * qq);
            t = r * Math.pow(1.0 - t + y * Math.sqrt(t), 3);
            if (t <= 0.0) {
                x = 1.0 - Math.exp(Math.log((1.0 - a) * qq) + lnbeta) / qq;
            } else {
                t = (4.0 * pp + r - 2.0) / t;
                if (t <= 1.0) {
                    x = Math.exp((Math.log(a * pp) + lnbeta) / pp);
                } else {
                    x = 1.0 - 2.0 / (t + 1.0);
                }
            }
        }

        return (x);
    }

    /** Sets the desired precision in the continued fraction expansion for
     *  the computation of the incompleteBetaFunction
     *
     * @param prec
     */
    public final static void setContinuedFractionDesiredPrecision(double prec) {
        myContinuedFraction.setDesiredPrecision(prec);
    }

    /** Sets the maximum number of iterations in the continued fraction expansion for
     *  the computation of the incompleteBetaFunction
     *
     * @param maxIter
     */
    public final static void setContinuedFractionMaximumIterations(int maxIter) {
        myContinuedFraction.setMaximumIterations(maxIter);
    }

    /** Sets the desired precision of the root finding algorithm in the CDF
     *  inversion computation
     *
     * @param prec
     */
    public final static void setRootFindingDesiredPrecision(double prec) {
        myRootFinder.setDesiredPrecision(prec);
    }

    /** Sets the maximum number of iterations of the root finding algorithm in the CDF
     *  inversion computation
     *
     * @param maxIter
     */
    public final static void setRootFindingMaximumIterations(int maxIter) {
        myRootFinder.setMaximumIterations(maxIter);
    }

    private class BetaRootFunction implements FunctionIfc {

        public double fx(double x) {
            return cdf(x) - myProb;
        }
    }

    public static void main(String args[]) {
        double a1 = .6;
        double a2 = 3.3;

        Beta n2 = new Beta(a1, a2);

        System.out.println("mean = " + n2.getMean());
        System.out.println("var = " + n2.getVariance());
        double x = 0.5;
        System.out.println("pdf at " + x + " = " + n2.pdf(x));
        System.out.println("cdf at " + x + " = " + n2.cdf(x));
        System.out.println("------------");
        for (int i = 0; i <= 10; i++) {
            x = 0.1 * i;
            System.out.println("cdf at " + x + " = " + n2.cdf(x));
        }
        System.out.println("------------");
        for (int i = 0; i <= 10; i++) {
            double p = 0.1 * i;
            System.out.println("invcdf at " + p + " = " + n2.invCDF(p));
        }

        System.out.println("------------");
        System.out.println("------------");

        for (int i = 1; i <= 10; i++) {
            System.out.println("nextRandom(" + i + ")= " + n2.getValue());
        }
        System.out.println("------------");

        System.out.println("done");
    }
}
