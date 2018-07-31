/*******************************************************************************
 * Copyright (c) 2012 Danilo Pianini.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.html
 ******************************************************************************/

package alice.alchemist.model.implementations.reactions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import alice.alchemist.exceptions.UncomparableDistancesException;
import alice.alchemist.model.implementations.conditions.IntMoleculePresent;
import alice.alchemist.model.interfaces.IAction;
import alice.alchemist.model.interfaces.ICondition;
import alice.alchemist.model.interfaces.IEnvironment;
import alice.alchemist.model.interfaces.IMolecule;
import alice.alchemist.model.interfaces.INode;
import alice.alchemist.model.interfaces.IPosition;
import alice.alchemist.model.interfaces.ITime;
import alice.alchemist.utils.MathUtils;
import cern.jet.random.engine.RandomEngine;

/**
 * @author Danilo Pianini
 * @version 20110825
 * 
 */
public class Diffusion<N extends Number, D extends Number> extends
		ExpTimeReactionBindsNeighbors<N, D, Integer> {

	private static final long serialVersionUID = 7363967315457771232L;
	private final HashMap<INode<Integer>, D> distances = new HashMap<INode<Integer>, D>();
	private final HashMap<INode<Integer>, Double> propensities = new HashMap<INode<Integer>, Double>();
	private final IMolecule mol;
	private final double r;
	private final double ni;
	private final double p;
	private final double t;
	private final int i = 0;

	/**
	 * @param mol
	 *            the molecule which diffuses
	 * @param n
	 *            the node
	 * @param environment
	 *            the current environment
	 * @param rate
	 *            speed of the reaction
	 * @param random
	 *            the random engine for this simulation
	 * @param r
	 *            the ratio between an empirical constant k_f and the intrinsic
	 *            viscosity ni. According to Harding and Johnson
	 *            "The concentration dependence of macromolecular parameters",
	 *            this value is in a range of 1.4-1.7, and the more asymmetric a
	 *            particle is, the lower.
	 * @param ni
	 *            intrinsic viscosity coefficient. A rigid sphere has a ni = 2.5
	 * @param p
	 *            mass of a molecule
	 * @param l
	 *            dimension of the mesh (distance between nodes)
	 * @param t
	 *            temperature
	 * @param i
	 *            number of iterations for second virial coefficient
	 *            computation. Values higher than 4 may compromise performances.
	 *            Values higher than 170 may (will) result in wrong results.
	 */
	public Diffusion(IMolecule mol, INode<Integer> n,
			IEnvironment<N, D, Integer> environment, double rate,
			RandomEngine random, double r, double ni, double p, double t) {
		super(environment, n, rate, random);
		this.mol = mol;
		this.r = r;
		this.ni = ni;
		this.p = p;
		this.t = t;
	}

	public Diffusion(IMolecule mol, INode<Integer> n,
			IEnvironment<N, D, Integer> environment, double rate,
			RandomEngine random, double p) {
		this(mol, n, environment, rate, random, 1.6, 2.5, p, 298.15);
	}

	/**
	 * @param c
	 *            this parameter is ignored.
	 */
	@Override
	public void setConditions(List<? extends ICondition<Integer>> c) {
		ArrayList<ICondition<Integer>> cond = new ArrayList<ICondition<Integer>>(
				1);
		cond.add(buildPresentCondition(mol, getNode()));
		super.setConditions(cond);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * alice.alchemist.model.implementations.reactions.ExpTimeReaction#setActions
	 * (java.util.List)
	 */
	@Override
	public void setActions(List<? extends IAction<Integer>> c) {
		ArrayList<IAction<Integer>> cond = new ArrayList<IAction<Integer>>(1);
		cond.add(buildMoveAction(mol, getNode()));
		super.setActions(cond);
	}

	protected final static IAction<Integer> buildMoveAction(IMolecule mol,
			INode<Integer> node) {
		// TODO: create action
		return null;
	}

	protected final static IntMoleculePresent buildPresentCondition(
			IMolecule mol, INode<Integer> n) {
		return new IntMoleculePresent(mol, n, 1);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see alice.alchemist.model.interfaces.IReaction#getPropensity()
	 */
	@Override
	public double getPropensity() {
		initPossibleNodes();
		IPosition<N, D> mypos = getEnvironment().getPosition(getNode());
		double result = 0;
		for (INode<Integer> n : getPossibleNodes()) {
			try {
				D distance = mypos.getDistanceTo(getEnvironment()
						.getPosition(n));
				D olddistance = distances.get(n);
				/*
				 * Note: cache is never freed.
				 */
				if (!distance.equals(olddistance)) {
					distances.put(n, distance);
					double p = computePropensity(distance);
					result += p;
					propensities.put(n, p);
				} else {
					result += propensities.get(distance);
				}
			} catch (UncomparableDistancesException e1) {
				e1.printStackTrace();
			}
		}
		return getRate() * result;
	}

	/**
	 * @param distance
	 * @return
	 */
	private double computePropensity(D distance) {
		double l = distance.doubleValue();
		return diffusion_coefficient(getNode().getConcentration(mol), r, ni, p,
				l, t, i) / (l * l);
	}

	private final static double BC = -Math.PI * MathUtils.AVOGADRO / 6;

	/**
	 * This method computes the second virial coefficient as in Equation 27 of
	 * Lecca et al.
	 * "Stochastic simulation of the spatio-temporal dynamics of reaction-diffusion systems: the case for the bicoid gradient"
	 * 
	 * @param t
	 *            temperature number of iterations for approximation. According
	 *            to Lecca's article, values higher than 4 do not influence the
	 *            simulation results.
	 * @return
	 */
	public static double second_virial_coefficient(final double t, final int i,
			final boolean useLanczos) {
		double sum = 0;
		for (int j = 0; j < i; j++) {
			final double v = ((double) j) / 2 - 0.25;
			System.out.println(v);
			final double gamma = useLanczos ? MathUtils.lanczosGamma(v)
					: MathUtils.stirlingGamma(v);
			sum += Math.pow(4, j) * Math.pow(MathUtils.K_BOLTZMANN * t, v)
					* gamma / MathUtils.factDouble(j);
		}
		return BC * sum;
	}

	/**
	 * This function evaluates the value of the frictional coefficient as in
	 * Equation 24 of Lecca et al.
	 * "Stochastic simulation of the spatio-temporal dynamics of reaction-diffusion systems: the case for the bicoid gradient"
	 * 
	 * @param c
	 *            concentration of the molecule in the node (mesh or
	 *            compartment)
	 * @param r
	 *            the ratio between an empirical constant k_f and the intrinsic
	 *            viscosity ni. According to Harding and Johnson
	 *            "The concentration dependence of macromolecular parameters",
	 *            this value is in a range of 1.4-1.7, and the more asymmetric a
	 *            particle is, the lower.
	 * @param ni
	 *            intrinsic viscosity coefficient. A rigid sphere has a ni = 2.5
	 * @return the frictional coefficient f_(i,k)
	 */
	public static double frictional_coefficient(double c, double r, double ni) {
		return c * r * ni;
	}

	/**
	 * This function evaluates the value of the activity coefficient of the
	 * solute as in Equation 23 of Lecca et al.
	 * "Stochastic simulation of the spatio-temporal dynamics of reaction-diffusion systems: the case for the bicoid gradient"
	 * 
	 * @param c
	 *            concentration of the molecule in the node (mesh or
	 *            compartment)
	 * @param l
	 *            dimension of the mesh (distance between nodes)
	 * @param c
	 *            concentration
	 * @param t
	 *            temperature
	 * @param i
	 *            number of iterations for second virial coefficient
	 *            computation. Values higher than 4 may compromise performances.
	 *            Values higher than 170 may (will) result in wrong results.
	 * @return the activity coefficient y
	 */
	public static double activity_coefficient(double p, double l, double c,
			double t, int i) {
		return Math.exp(k(p, l, t, i) * c * c);
	}

	/**
	 * Computes the value of the derivative of the activity coefficient on the
	 * concentration. The analytic expression for it was calculated manually by
	 * deriving the equation 23 of Lecca et al.
	 * "Stochastic simulation of the spatio-temporal dynamics of reaction-diffusion systems: the case for the bicoid gradient"
	 * 
	 * @param p
	 *            mass of a molecule
	 * @param l
	 *            dimension of the mesh (distance between nodes)
	 * @param c
	 *            concentration
	 * @param t
	 *            temperature
	 * @param i
	 *            number of iterations for second virial coefficient
	 *            computation. Values higher than 4 may compromise performances.
	 *            Values higher than 170 may (will) result in wrong results.
	 * @return the derivative of the activity coefficient on the concentration
	 */
	public static double dy_dc(double p, double l, double c, double t, int i) {
		final double k = k(p, c, t, i);
		return 2 * k * c * Math.exp(k * c * c);
	}

	/**
	 * This function calculates a part of the eq. 23 of Lecca et al.
	 * "Stochastic simulation of the spatio-temporal dynamics of reaction-diffusion systems: the case for the bicoid gradient"
	 * 
	 * @param p
	 *            mass of a molecule
	 * @param l
	 *            dimension of the mesh (distance between nodes)
	 * @param t
	 *            temperature
	 * @param i
	 *            number of iterations for second virial coefficient
	 *            computation. Values higher than 4 may compromise performances.
	 *            Values higher than 170 may (will) result in wrong results.
	 */
	private static double k(double p, double l, double t, int i) {
		return 2 * second_virial_coefficient(t, i, true) * p * l
				/ MathUtils.AVOGADRO;
	}

	/**
	 * This function calculates eq. 12 of Lecca et al.
	 * "Stochastic simulation of the spatio-temporal dynamics of reaction-diffusion systems: the case for the bicoid gradient"
	 * 
	 * @param c
	 *            concentration of the molecule in the node (mesh or
	 *            compartment)
	 * @param r
	 *            the ratio between an empirical constant k_f and the intrinsic
	 *            viscosity ni. According to Harding and Johnson
	 *            "The concentration dependence of macromolecular parameters",
	 *            this value is in a range of 1.4-1.7, and the more asymmetric a
	 *            particle is, the lower.
	 * @param ni
	 *            intrinsic viscosity coefficient. A rigid sphere has a ni = 2.5
	 * @param p
	 *            mass of a molecule
	 * @param l
	 *            dimension of the mesh (distance between nodes)
	 * @param t
	 *            temperature
	 * @param i
	 *            number of iterations for second virial coefficient
	 *            computation. Values higher than 4 may compromise performances.
	 *            Values higher than 170 may (will) result in wrong results.
	 * @return the diffusion coefficient
	 */
	public static double diffusion_coefficient(double c, double r, double ni,
			double p, double l, double t, int i) {
		return MathUtils.K_BOLTZMANN
				* t
				/ frictional_coefficient(c, r, ni)
				* (1 + c * dy_dc(p, l, c, t, i)
						/ activity_coefficient(p, l, c, t, i));
	}
}
