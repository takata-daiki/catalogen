package balmysundaycandy.morelowlevel.sample.services;

import org.jquantlib.Settings;
import org.jquantlib.daycounters.Actual365Fixed;
import org.jquantlib.daycounters.DayCounter;
import org.jquantlib.exercise.EuropeanExercise;
import org.jquantlib.exercise.Exercise;
import org.jquantlib.instruments.EuropeanOption;
import org.jquantlib.instruments.Option;
import org.jquantlib.instruments.Payoff;
import org.jquantlib.instruments.PlainVanillaPayoff;
import org.jquantlib.instruments.VanillaOption;
import org.jquantlib.pricingengines.AnalyticEuropeanEngine;
import org.jquantlib.processes.BlackScholesMertonProcess;
import org.jquantlib.quotes.Handle;
import org.jquantlib.quotes.Quote;
import org.jquantlib.quotes.SimpleQuote;
import org.jquantlib.termstructures.BlackVolTermStructure;
import org.jquantlib.termstructures.YieldTermStructure;
import org.jquantlib.termstructures.volatilities.BlackConstantVol;
import org.jquantlib.termstructures.yieldcurves.FlatForward;
import org.jquantlib.time.Calendar;
import org.jquantlib.time.Date;
import org.jquantlib.time.Month;
import org.jquantlib.time.calendars.Target;

import balmysundaycandy.morelowlevel.sample.model.BlackScholesArgument;

public class BlackScholesCalcurationService {
	
	public double npv(BlackScholesArgument blackScholesArgument){
        // set up dates
        final Calendar calendar = new Target();
        final Date todaysDate = new Date(15, Month.May, 1998);
        final Date settlementDate = new Date(17, Month.May, 1998);
        new Settings().setEvaluationDate(todaysDate);

        // our options
        final Option.Type type = Option.Type.Put;
        final double strike = 40.0;
        final double underlying = 36.0;
        /*@Rate*/final double riskFreeRate = 0.06;
        final double volatility = 0.2;
        final double dividendYield = 0.00;


        final Date maturity = new Date(17, Month.May, 1999);
        final DayCounter dayCounter = new Actual365Fixed();

        // Define exercise for European Options
        final Exercise europeanExercise = new EuropeanExercise(maturity);

        // bootstrap the yield/dividend/volatility curves
        final Handle<Quote> underlyingH = new Handle<Quote>(new SimpleQuote(underlying));
        final Handle<YieldTermStructure> flatDividendTS = new Handle<YieldTermStructure>(new FlatForward(settlementDate, dividendYield, dayCounter));
        final Handle<YieldTermStructure> flatTermStructure = new Handle<YieldTermStructure>(new FlatForward(settlementDate, riskFreeRate, dayCounter));
        final Handle<BlackVolTermStructure> flatVolTS = new Handle<BlackVolTermStructure>(new BlackConstantVol(settlementDate, calendar, volatility, dayCounter));
        final Payoff payoff = new PlainVanillaPayoff(type, strike);

        final BlackScholesMertonProcess bsmProcess = new BlackScholesMertonProcess(underlyingH, flatDividendTS, flatTermStructure, flatVolTS);

        // European Options
        final VanillaOption europeanOption = new EuropeanOption(payoff, europeanExercise);

        // Black-Scholes for European
        europeanOption.setPricingEngine(new AnalyticEuropeanEngine(bsmProcess));
        
        return europeanOption.NPV();
	}

}
