package graphEditor.factorgraph;

import graphEditor.Factornode;
import graphEditor.Graph;
import graphEditor.Message;
import graphEditor.Node;
import graphEditor.Variablenode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

import org.apache.commons.collections.MapUtils;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.util.ECollections;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.transaction.RollbackException;
import org.eclipse.emf.transaction.RunnableWithResult;
import org.eclipse.emf.transaction.TransactionalCommandStack;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;

import de.lmu.genzentrum.tresch.FactorNode;
import de.lmu.genzentrum.tresch.MaxProduct;
import de.lmu.genzentrum.tresch.NoValueException;
import de.lmu.genzentrum.tresch.SumProduct;
import de.lmu.genzentrum.tresch.VariableNode;

public class DoSumAction implements IObjectActionDelegate {

	/**
	 * @generated
	 */
	private IWorkbenchPart targetPart;
	private static graphEditor.diagram.edit.parts.GraphEditPart mySelectedElement;

	public DoSumAction() {
	}

	@Override
	public void setActivePart(IAction action, IWorkbenchPart targetPart) {
		this.targetPart = targetPart;
	}

	@Override
	public void selectionChanged(IAction action, ISelection selection) {
		mySelectedElement = null;
		if (selection instanceof IStructuredSelection) {
			IStructuredSelection structuredSelection = (IStructuredSelection) selection;
			if (structuredSelection.size() == 1
					&& structuredSelection.getFirstElement() instanceof graphEditor.diagram.edit.parts.GraphEditPart) {
				mySelectedElement = (graphEditor.diagram.edit.parts.GraphEditPart) structuredSelection
						.getFirstElement();
			}
		}
		action.setEnabled(isEnabled());
	}

	private boolean isEnabled() {
		return mySelectedElement != null;
	}

	private static de.lmu.genzentrum.tresch.Node convertNode(final Node ab) {
		TransactionalEditingDomain domain = null;
		if (mySelectedElement != null)
			domain = mySelectedElement.getEditingDomain();

		de.lmu.genzentrum.tresch.Node tresch = null;
		if (ab instanceof Variablenode) {

			List values = ((Variablenode) ab).getValues();
			Double[] da = (Double[]) values.toArray();
			double[] arrayvalues = new double[da.length];
			for (int i = 0; i < da.length; i++) {
				arrayvalues[i] = da[i].doubleValue();
			}
			Object[] oa = new Object[da.length];
			for (int i = 0; i < da.length; i++) {
				oa[i] = da[i].doubleValue();
			}
			tresch = new de.lmu.genzentrum.tresch.VariableNode(ab.getName(),
					(int) ab.getId(), arrayvalues, ((Variablenode) ab)
							.isIsKnown());
		}
		if (ab instanceof Factornode) {
			double[][] values = ((Factornode) ab).getValues();
			int xmax = values.length;
			int ymax = values[0].length;

			double[][] result = new double[ymax][xmax];
			HashMap<String, Double> map = new HashMap<String, Double>();
			for (int x = 0; x < xmax; x++)
				for (int y = 0; y < ymax; y++)
					map.put(new String(x + "-" + y), values[x][y]);
			for (int x = 0; x < xmax; x++)
				for (int y = 0; y < ymax; y++)
					result[y][x] = map.get(new String(x + "-" + y));

			Object[][] oa = new Object[values.length][values[0].length];
			for (int i = 0; i < values.length; i++)
				for (int j = 0; j < values[0].length; j++)
					oa[i][j] = values[i][j];

			List<Variablenode> connections = null;
			try {
				final Resource r = domain.getResourceSet().getResources()
						.get(0);
				connections = (List<Variablenode>) domain
						.runExclusive(new RunnableWithResult.Impl() {
							public void run() {
								setResult(((graphEditor.Graph) r.getContents()
										.get(0))
										.getConnectingVariablenodes((Factornode) ab));
							}
						});
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			if (connections == null) {
				tresch = new de.lmu.genzentrum.tresch.FactorNode(ab.getName(),
						(int) ab.getId(), result);
			} else {

				if (connections.size() == 1) {
					tresch = new de.lmu.genzentrum.tresch.FactorNode(ab
							.getName(), (int) ab.getId(), result, null);
				} else {
					int[] cons = new int[connections.size()];
					for (int i = 0; i < connections.size(); i++)
						cons[i] = (int) connections.get(i).getId();
					tresch = new de.lmu.genzentrum.tresch.FactorNode(ab
							.getName(), (int) ab.getId(), result, cons);
				}
			}

		}

		return tresch;

	}

	private static synchronized de.lmu.genzentrum.tresch.Graph convertEditDomainToFactorgraph() {
		TransactionalEditingDomain domain = null;
		if (mySelectedElement != null)
			domain = mySelectedElement.getEditingDomain();

		graphEditor.Graph graph = null;

		try {
			final Resource r = domain.getResourceSet().getResources().get(0);
			graph = (graphEditor.Graph) domain
					.runExclusive(new RunnableWithResult.Impl() {
						public void run() {
							setResult(((graphEditor.Graph) r.getContents().get(
									0)));
						}
					});
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		LinkedList<de.lmu.genzentrum.tresch.Message> messages = new LinkedList<de.lmu.genzentrum.tresch.Message>();

		EList<Message> mesg = graph.getMessages();
		ECollections.sort(mesg);

		for (Message m : mesg) {

			final Node nodeFrom = m.getFrom();
			de.lmu.genzentrum.tresch.Node from = convertNode(nodeFrom);
			final Node nodeTo = m.getTo();
			de.lmu.genzentrum.tresch.Node to = convertNode(nodeTo);
			de.lmu.genzentrum.tresch.Message message = new de.lmu.genzentrum.tresch.Message(
					from, to);
			messages.add(message);
		}
		de.lmu.genzentrum.tresch.Graph factorgraph = new de.lmu.genzentrum.tresch.Graph(
				messages);

		return factorgraph;
	}

	public static de.lmu.genzentrum.tresch.Graph testGraph() {
		double[] defWerte = new double[2];
		defWerte[0] = 0;
		defWerte[1] = 1;

		double[][] funcFA = new double[2][2];
		funcFA[0][0] = 0;
		funcFA[1][0] = 0.5;
		funcFA[0][1] = 1;
		funcFA[1][1] = 0.5;
		
		double[][] funcFC = new double[2][2];
		funcFC[0][0] = 0;
		funcFC[1][0] = 0.5;
		funcFC[0][1] = 1;
		funcFC[1][1] = 0.5;
		
		int[] nodeArrayFB=new int[2];
		nodeArrayFB[0]=2;
		nodeArrayFB[1]=3;
		double[][] funcFB=new double[3][4];
		funcFB[0][0]=0;
		funcFB[1][0]=0;
		funcFB[2][0]=0.25;
		
		funcFB[0][1]=0;
		funcFB[1][1]=1;
		funcFB[2][1]=0.25;
		
		funcFB[0][2]=1;
		funcFB[1][2]=0;
		funcFB[2][2]=0.25;
		
		funcFB[0][3]=1;
		funcFB[1][3]=1;
		funcFB[2][3]=0.25;
		
		
		//FactorNode f1 = new FactorNode("fA", 1, funcFA);
	//	FactorNode f1 = new FactorNode("fA", 1, funcFB,nodeArrayFB);
		FactorNode f1 = new FactorNode("fA", 1, funcFA);
		FactorNode f2 = new FactorNode("fB", 1, funcFC);
		VariableNode v1 = new VariableNode("x1", 2, defWerte, true);
		//VariableNode v2 = new VariableNode("x2", 3, defWerte, true);

		LinkedList<de.lmu.genzentrum.tresch.Message> mSeq = new LinkedList<de.lmu.genzentrum.tresch.Message>();

		de.lmu.genzentrum.tresch.Message m1 = new de.lmu.genzentrum.tresch.Message(
				f1, v1);
		mSeq.add(m1);
		de.lmu.genzentrum.tresch.Message m2 = new de.lmu.genzentrum.tresch.Message(
				v1, f2);
		mSeq.add(m2);
		de.lmu.genzentrum.tresch.Message m3 = new de.lmu.genzentrum.tresch.Message(
				f2, v1);
		mSeq.add(m3);
		de.lmu.genzentrum.tresch.Message m4 = new de.lmu.genzentrum.tresch.Message(
				v1, f2);
		mSeq.add(m4);

		de.lmu.genzentrum.tresch.Graph test = new de.lmu.genzentrum.tresch.Graph(
				mSeq);

		return test;
	}

	@Override
	public void run(IAction action) {
		TransactionalEditingDomain domain = null;
		if (mySelectedElement != null)
			domain = mySelectedElement.getEditingDomain();

		graphEditor.Graph graph = null;

		try {
			final Resource r = domain.getResourceSet().getResources().get(0);
			graph = (graphEditor.Graph) domain
					.runExclusive(new RunnableWithResult.Impl() {
						public void run() {
							setResult(((graphEditor.Graph) r.getContents().get(
									0)));
						}
					});
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		de.lmu.genzentrum.tresch.Graph factorgraph = convertEditDomainToFactorgraph();

		double[][] result = null;
		try {

			 de.lmu.genzentrum.tresch.SumProduct sum = new de.lmu.genzentrum.tresch.SumProduct(factorgraph);
			 Hashtable<de.lmu.genzentrum.tresch.Node, Hashtable<Double,Double>> treschResult = sum.doSum();
			 
			 

		} catch (NoValueException e) {
			e.printStackTrace();
		}

//		Command cmd = null;
//		cmd = SetCommand.create(domain, graph,
//				graphEditor.GraphEditorPackage.eINSTANCE
//						.getVariablenode_Values(), result);
//		try {
//			((TransactionalCommandStack) domain.getCommandStack()).execute(cmd,
//					null);
//		} catch (RollbackException rbe) {
//		} catch (InterruptedException e) {
//		}

	}

}
