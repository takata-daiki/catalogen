/*
 * This file is part of FFractal, created by Guilhelm Savin and modified
 * by Bilyan Borisov.
 * 
 * FFractal is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * FFractal is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with FFractal.  If not, see <http://www.gnu.org/licenses/>.
 * 
 * Copyright 2010
 * 	Guilhelm Savin
 * 
 * Copyright 2012
 *  Bilyan Borisov
 */
package org.ri2c.flame.gui;

import org.ri2c.flame.FFunction;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.JSpinner;
import javax.swing.JDialog;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JSlider;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.event.ActionEvent;
import java.util.LinkedList;
import java.util.Random;

public class FFWizardArray extends JDialog implements ActionListener, ChangeListener {

	
	private static final long serialVersionUID = 5743383624470930441L;

	public static interface FFWizardArrayListener {
		void ffWizardArrayFinished(FFWizardArray ffwa) throws InstantiationException, IllegalAccessException;
	}

	String[] ff_names;
	double[] ffs_colors;
	double ff_w;
	double[][] ff_data;

	JPanel buttons;
	JPanel content;
	JSpinner num;
	JSlider color;
	JButton finish;
	JSpinner weight;
	VariationsComboBox vlist;
	LinkedList<FFWizardArrayListener> listeners;
	Random random = new Random();
	
	public String[] getFFNames()
	{
		return ff_names;
	}
	public FFWizardArray() {
		this(null);
	}

	public FFWizardArray(Frame f) {
		super(f, f != null);
		setTitle("New Flame Function Array");
		setResizable(false);

		GridBagLayout bag = new GridBagLayout();
		GridBagConstraints c = new GridBagConstraints();

		setLayout(bag);
		Insets insets = new Insets(10, 20, 10, 20);

		c.insets = insets;
		c.gridwidth = GridBagConstraints.REMAINDER;
		c.gridheight = 3;
		c.fill = GridBagConstraints.HORIZONTAL;

		content = new JPanel();
		content.setPreferredSize(new Dimension(400, 400)); 
															
		c.weighty = 1;
		c.fill = GridBagConstraints.BOTH;
		bag.setConstraints(content, c);
		add(content);

		c.gridwidth = GridBagConstraints.REMAINDER;
		c.fill = GridBagConstraints.HORIZONTAL;
		JLabel msg = new JLabel("This is the array function creator\n\n\n\n");
		bag.setConstraints(msg, c);
		content.add(msg);

		FFWizardArray.this.content.setLayout(bag);

		num = new JSpinner(new SpinnerNumberModel(0, 0, 200, 1));
		num.setBorder(BorderFactory.createTitledBorder("number of functions"));
		num.addChangeListener(this);
		color = new JSlider(0, 360, random.nextInt(360));
		color.setBorder(BorderFactory
				.createTitledBorder("color of all functions"));
		color.setBackground(Color.getHSBColor(color.getValue() / 360.0f, 1, 1));
		color.addChangeListener(this);
		c.gridwidth = GridBagConstraints.REMAINDER;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weighty = 1;
		bag.setConstraints(num, c);
		bag.setConstraints(color, c);
		content.add(num);
		content.add(color);

		vlist = new VariationsComboBox();
		weight = new JSpinner(new SpinnerNumberModel(1, 0, 1, 0.001));
		weight.setBorder(BorderFactory.createTitledBorder("weight"));
		c.gridwidth = GridBagConstraints.REMAINDER;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weighty = 1;
		bag.setConstraints(weight, c);
		content.add(weight);

		bag.setConstraints(vlist, c);

		c.insets = new Insets(5, 0, 0, 5);
		c.fill = GridBagConstraints.NONE;
		c.weightx = 1;
		c.gridwidth = 1;
		// c.gridy = 1;
		c.fill = GridBagConstraints.HORIZONTAL;

		content.add(vlist);

		buttons = new JPanel();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weighty = 0;
		bag.setConstraints(buttons, c);
		add(buttons);

		finish = new JButton("finish");
		finish.addActionListener(this);
		finish.setActionCommand("finish");
		buttons.add(finish);

		pack();
	
		listeners = new LinkedList<FFWizardArrayListener>();

	}

	public FFunction[] getFFunctions() throws InstantiationException,
			IllegalAccessException {
		int n = (Integer) num.getValue();
		FFunction[] ffs = new FFunction[n];
		for (int i = 0; i < n; i++) {
			ffs[i] = new FFunction(ffs_colors[i], ff_data[i]);
			ffs[i].setName(ff_names[i]);
			ffs[i].setWeight(ff_w);
			ffs[i].addVariation((Double) weight.getValue(), vlist
					.getSelectedVariation().newInstance());
		}

		return ffs;
	}

	public static FFunction[] createNewFlameFunctions() {
		FFWizardArray ffwa = new FFWizardArray();
		ffwa.setVisible(true);

		return null;
	}

	public void setNames(int n) {

		ff_names = new String[n];
		for (int i = 0; i < n; i++) {
			ff_names[i] = new String("ArrayFunction#" + (FFWizard.count));
			FFWizard.incCount();
		}
	}

	public void setColors(int n) {
		ffs_colors = new double[n];
		double inc = 0.0;
		for (int i = 0; i < n; i++) {
			ffs_colors[i] = color.getValue() / 360.0 + inc;
			inc += 0.3 / n;
		}
	}

	public void setFFdata(int n) {
		ff_w = 1.0;
		ff_data = new double[n][6];
		for (int i = 0; i < n; i++)
			for (int j = 0; j < 6; j++)
				if (Math.random() < 0.5)
					ff_data[i][j] = random.nextDouble();
				else
					ff_data[i][j] = -random.nextDouble();

	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getActionCommand().equals("finish")) {
			int n = (Integer) num.getValue();
			setNames(n);
			setColors(n);
			setFFdata(n);
		}

		setVisible(false);
		for (FFWizardArrayListener l : listeners)
			try {
				l.ffWizardArrayFinished(this);
			} catch (InstantiationException e1) {

				e1.printStackTrace();
			} catch (IllegalAccessException e1) {

				e1.printStackTrace();
			}
	}

	@Override
	public void stateChanged(ChangeEvent e) {
		color.setBackground(Color.getHSBColor(color.getValue() / 360.0f, 1, 1));
	}

	public void addWizardArrayListener(FFWizardArrayListener listener) {
		listeners.add(listener);
	}

	public void removeWizardArrayListener(FFWizardArrayListener listener) {
		listeners.remove(listener);
	}
}
