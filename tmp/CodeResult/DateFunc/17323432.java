/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.vjet.eclipse.core.test.contentassist;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.dltk.mod.compiler.env.ISourceModule;
import org.eclipse.dltk.mod.core.CompletionProposal;
import org.eclipse.dltk.mod.core.ModelException;
import org.eclipse.dltk.mod.internal.core.VjoSourceModule;
import org.eclipse.dltk.mod.internal.ui.editor.ScriptEditor;
import org.eclipse.dltk.mod.ui.text.completion.AbstractScriptCompletionProposal;
import org.eclipse.dltk.mod.ui.text.completion.CompletionProposalLabelProvider;
import org.eclipse.dltk.mod.ui.text.completion.ScriptCompletionProposal;
import org.eclipse.dltk.mod.ui.text.completion.ScriptContentAssistInvocationContext;
import org.eclipse.jface.text.source.ISourceViewer;

import org.ebayopensource.dsf.jsnative.HtmlDocument;
import org.ebayopensource.dsf.jsnative.anno.Constructor;
import org.ebayopensource.dsf.jsnative.anno.Function;
import org.ebayopensource.dsf.jsnative.anno.Property;
import org.ebayopensource.dsf.jsnative.anno.Static;
import org.ebayopensource.dsf.jst.IJstNode;
import org.ebayopensource.dsf.jst.IJstParseController;
import org.ebayopensource.dsf.jst.IJstType;
import org.ebayopensource.dsf.jst.ts.JstTypeSpaceMgr;
import org.ebayopensource.vjo.lib.ResourceHelper;
import org.ebayopensource.dsf.jstojava.controller.JstParseController;
import org.ebayopensource.dsf.jstojava.loader.DefaultJstTypeLoader;
import org.ebayopensource.dsf.jstojava.parser.VjoParser;
import org.ebayopensource.dsf.jstojava.translator.robust.completion.JstCompletion;
import org.ebayopensource.dsf.ts.event.EventListenerStatus;
import org.ebayopensource.dsf.ts.event.ISourceEventCallback;
import org.ebayopensource.dsf.ts.event.group.AddGroupEvent;
import org.ebayopensource.dsf.ts.type.TypeName;
import com.ebay.tools.testframework.fixture.FixtureManager;
import org.ebayopensource.vjet.eclipse.codeassist.VjoCompletionEngine;
import org.ebayopensource.vjet.eclipse.core.IJSSourceModule;
import org.ebayopensource.vjet.eclipse.core.VjoNature;
import org.ebayopensource.vjet.eclipse.core.parser.VjoParserToJstAndIType;
import org.ebayopensource.vjet.eclipse.core.search.matching.ICategoryRequestor;
import org.ebayopensource.vjet.eclipse.core.test.FixtureUtils;
import org.ebayopensource.vjet.eclipse.core.test.parser.AbstractVjoModelTests;
import org.ebayopensource.vjet.eclipse.internal.ui.text.completion.VjoTypeCompletionProposalComputer;
import org.ebayopensource.vjo.tool.typespace.TypeSpaceMgr;

public class PartialCodeCompletionTests extends AbstractVjoModelTests {
	public Boolean fullyLoaded = false;
	
	public void setUp() throws Exception {
		super.setUp();
//		TypeSpaceMgr mgr = TypeSpaceMgr.getInstance();
//		Collection<IJstType> types = mgr.getAllTypes();
//		for (IJstType type : types) {
//			if (type.getName() == null)
//				continue;
//			RemoveTypeEvent removeEvent = new RemoveTypeEvent(new TypeName(
//					getProjectName(), type.getName()));
//			mgr.processEvent(removeEvent);
//		}
	}

	protected int lastPositionInFile(String string, String moduleName)
			throws ModelException {
		String content = ((IJSSourceModule) getSourceModule(
				TestConstants.PROJECT_NAME_VJETPROJECT, "src", new Path(
						moduleName))).getSource();
		if (string == null)
			return content.length();
		int position = content.lastIndexOf(string);
		if (position >= 0) {
			return position + string.length();
		}
		return -1;
	}

	protected void basicTest(String mname, int position, String[] compNames,
			String category) throws ModelException {
		assertNotSame("Invalid file content, cant find position", -1, position);

		LinkedList<CompletionProposal> results = new LinkedList<CompletionProposal>();

		IJSSourceModule module = (IJSSourceModule) getSourceModule(
				TestConstants.PROJECT_NAME_VJETPROJECT, "src", new Path(mname));
		VjoCompletionEngine c = createEngine(results, category, module);
		c.complete((ISourceModule) module, position, 0);
		compareCompletions(results, compNames, false);
	}

	protected void basicNameTest(IJSSourceModule module, int position,
			String[] compNames, String category) throws ModelException {
		assertNotSame("Invalid file content, cant find position", -1, position);

		LinkedList<CompletionProposal> results = new LinkedList<CompletionProposal>();

		VjoCompletionEngine c = createEngine(results, category, module);
		c.complete((ISourceModule) module, position, 0);
		compareCompletions(results, compNames, true);
	}

	public void testVjoDot() throws ModelException {
		String js = "partials/Partials1.js";
		FixtureManager m_fixtureManager = FixtureUtils.setUpFixture(this, js);
		try {
			String[] names = new String[] { "ctype('partials.Partials1')",
					"etype('partials.Partials1')",
					"itype('partials.Partials1')",
					"mtype('partials.Partials1')", "needs('')",
					"otype('partials.Partials1')" };
			IJSSourceModule module = (IJSSourceModule) getSourceModule(
					TestConstants.PROJECT_NAME_VJETPROJECT, "src", new Path(js));
			int position = lastPositionInFile("vjo.", module);
			containsNames(module, position, names,
					ICategoryRequestor.TYPE_CATEGORY);
		} finally {
			FixtureUtils.tearDownFixture(m_fixtureManager);
		}
	}

	public void testVjoNeedsSemiColomnBug() throws ModelException {
		String js = "partials/Partials4.js";
		FixtureManager m_fixtureManager = FixtureUtils.setUpFixture(this, js);
		try {
			String names = "needs('')";
			IJSSourceModule module = (IJSSourceModule) getSourceModule(
					TestConstants.PROJECT_NAME_VJETPROJECT, "src", new Path(js));
			int position = lastPositionInFile(".", module);
			String doc = doTypeCodeCompleate(module, position, names);
			assertTrue(doc.trim().contains(names));
			assertFalse(doc.trim().contains(";"));
		} finally {
			FixtureUtils.tearDownFixture(m_fixtureManager);
		}
	}

	public void testVjoNeedsDot() throws ModelException {
		String js = "partials/Partials2.js";
		FixtureManager m_fixtureManager = FixtureUtils.setUpFixture(this, js);
		try {
			String[] names = new String[] { "vjo" };
			IJSSourceModule module = (IJSSourceModule) getSourceModule(
					TestConstants.PROJECT_NAME_VJETPROJECT, "src", new Path(js));
			int position = lastPositionInFile("vjo.needs(' ') ", module);
			containsNames(module, position, names,
					ICategoryRequestor.TYPE_CATEGORY);
		} finally {
			FixtureUtils.tearDownFixture(m_fixtureManager);
		}
	}

	public void testVjoNeedsDotVjo() throws ModelException {
		String js = "partials/Partials3.js";
		FixtureManager m_fixtureManager = FixtureUtils.setUpFixture(this, js);
		try {
			String[] names = new String[] { "ctype('partials.Partials3')",
					"etype('partials.Partials3')",
					"itype('partials.Partials3')",
					"mtype('partials.Partials3')", "needs('')",
					"otype('partials.Partials3')" };
			IJSSourceModule module = (IJSSourceModule) getSourceModule(
					TestConstants.PROJECT_NAME_VJETPROJECT, "src", new Path(js));
			int position = lastPositionInFile("vjo.", module);
			containsNames(module, position, names,
					ICategoryRequestor.TYPE_CATEGORY);
		} finally {
			FixtureUtils.tearDownFixture(m_fixtureManager);
		}
	}

	public void testVjoCtypeDot() throws ModelException {
		String js = "partials/Partials4.js";
		FixtureManager m_fixtureManager = FixtureUtils.setUpFixture(this, js);
		try {
			String[] names = new String[] { "inherits('')", "needs('')",
					"satisfies('')", "mixin('')", "mixinProps('')",
					"inits(function(){})", "protos({})", "props({})" };
			IJSSourceModule module = (IJSSourceModule) getSourceModule(
					TestConstants.PROJECT_NAME_VJETPROJECT, "src", new Path(js));
			int position = lastPositionInFile(".", module);
			containsNames(module, position, names,
					ICategoryRequestor.TYPE_CATEGORY);
		} finally {
			FixtureUtils.tearDownFixture(m_fixtureManager);
		}
	}

	public void testVjoItypeDot() throws ModelException {
		String js = "partials/ITypeDotTest.js";
		FixtureManager m_fixtureManager = FixtureUtils.setUpFixture(this, js);
		try {
			String[] names = new String[] { "inherits('')", "needs('')",
					"mixin('')", "inits(function(){})", "protos({})",
					"props({})", "endType()" };
			IJSSourceModule module = (IJSSourceModule) getSourceModule(
					TestConstants.PROJECT_NAME_VJETPROJECT, "src", new Path(js));
			int position = firstPositionInFile("').", module);
			containsNames(module, position, names,
					ICategoryRequestor.TYPE_CATEGORY);
		} finally {
			FixtureUtils.tearDownFixture(m_fixtureManager);
		}
	}

	public void testVjoEtypeDot() throws ModelException {
		String js = "partials/ETypeDotTest.js";
		FixtureManager m_fixtureManager = FixtureUtils.setUpFixture(this, js);
		try {
			String[] names = new String[] { "values({})", "satisfies('')",
					"needs('')", "mixin('')", "protos({})", "props({})",
					"endType()" };
			IJSSourceModule module = (IJSSourceModule) getSourceModule(
					TestConstants.PROJECT_NAME_VJETPROJECT, "src", new Path(js));
			int position = firstPositionInFile("').", module);
			containsNames(module, position, names,
					ICategoryRequestor.TYPE_CATEGORY);
		} finally {
			FixtureUtils.tearDownFixture(m_fixtureManager);
		}
	}

	public void testVjoEtypeValuesDot() throws ModelException {
		String js = "partials/ETypeValuesDot.js";
		FixtureManager m_fixtureManager = FixtureUtils.setUpFixture(this, js);
		try {
			String[] names = new String[] { "endType()" };
			IJSSourceModule module = (IJSSourceModule) getSourceModule(
					TestConstants.PROJECT_NAME_VJETPROJECT, "src", new Path(js));
			int position = lastPositionInFile(".", module);
			containsNames(module, position, names,
					ICategoryRequestor.TYPE_CATEGORY);
		} finally {
			FixtureUtils.tearDownFixture(m_fixtureManager);
		}
	}

	public void testVjoEtypeNeeds() throws ModelException {
		String js = "partials/ETypeReference.js";
		FixtureManager m_fixtureManager = FixtureUtils.setUpFixture(this, js);
		try {
			String[] names = new String[] { "ETypeValuesDot" };
			IJSSourceModule module = (IJSSourceModule) getSourceModule(
					TestConstants.PROJECT_NAME_VJETPROJECT, "src", new Path(js));
			int position = lastPositionInFile("partials.", module);
			containsNames(module, position, names,
					ICategoryRequestor.TYPE_CATEGORY);
		} finally {
			FixtureUtils.tearDownFixture(m_fixtureManager);
		}
	}

	public void testVjoEtypeReference() throws ModelException {
		String js = "partials/ETypeReference.js";
		FixtureManager m_fixtureManager = FixtureUtils.setUpFixture(this, js);
		try {
			String[] names = new String[] { "S1", "S2", "S3", "S4" };
			IJSSourceModule module = (IJSSourceModule) getSourceModule(
					TestConstants.PROJECT_NAME_VJETPROJECT, "src", new Path(js));
			int position = lastPositionInFile("ETypeDotTest.", module);
			containsNames(module, position, names,
					ICategoryRequestor.TYPE_CATEGORY);
		} finally {
			FixtureUtils.tearDownFixture(m_fixtureManager);
		}
	}

	public void testVjoNeedsTypeDot() throws ModelException {
		String js = "partials/Partials5.js";
		FixtureManager m_fixtureManager = FixtureUtils.setUpFixture(this, js);
		try {
			String[] names = new String[] { "inherits('')", "needs('')",
					"satisfies('')", "mixin('')", "mixinProps('')",
					"protos({})", "props({})" };
			IJSSourceModule module = (IJSSourceModule) getSourceModule(
					TestConstants.PROJECT_NAME_VJETPROJECT, "src", new Path(js));
			int position = lastPositionInFile(".", module);
			containsNames(module, position, names,
					ICategoryRequestor.TYPE_CATEGORY);
		} finally {
			FixtureUtils.tearDownFixture(m_fixtureManager);
		}
	}

	public void testVjoNeedsTypeCompletion() throws ModelException {
		String js = "partials/Partials5.js";
		FixtureManager m_fixtureManager = FixtureUtils.setUpFixture(this, js);
		try {
			IJSSourceModule module = (IJSSourceModule) getSourceModule(
					TestConstants.PROJECT_NAME_VJETPROJECT, "src", new Path(js));
			File[] jsFiles = module.getResource().getLocation().toFile()
					.getParentFile().listFiles();
			List<String> list = new ArrayList<String>();
			for (File f : jsFiles) {
				if (f.getName().endsWith(".js"))
					list.add(f.getName().substring(0,
							f.getName().lastIndexOf(".js")));
			}
			int position = lastPositionInFile("partials.", module);
			containsNames(module, position, list
					.toArray(new String[list.size()]),
					ICategoryRequestor.TYPE_CATEGORY);
		} finally {
			FixtureUtils.tearDownFixture(m_fixtureManager);
		}
	}

	public void testVjoNeedsTypeCompletionMore() throws ModelException {
		String js = "partials/NeedTypeCompletion.js";
		FixtureManager m_fixtureManager = FixtureUtils.setUpFixture(this, js);
		try {
			IJSSourceModule module = (IJSSourceModule) getSourceModule(
					TestConstants.PROJECT_NAME_VJETPROJECT, "src", new Path(js));
			File[] jsFiles = module.getResource().getLocation().toFile()
					.getParentFile().listFiles();
			List<String> list = new ArrayList<String>();
			for (File f : jsFiles) {
				if (f.getName().endsWith(".js"))
					list.add(f.getName().substring(0,
							f.getName().lastIndexOf(".js")));
			}
			int position = lastPositionInFile("partials.", module);
			containsNames(module, position, list
					.toArray(new String[list.size()]),
					ICategoryRequestor.TYPE_CATEGORY);
		} finally {
			FixtureUtils.tearDownFixture(m_fixtureManager);
		}
	}

	public void testPropsProperties() throws ModelException {
		String js = "partials/Partials6.js";
		FixtureManager m_fixtureManager = FixtureUtils.setUpFixture(this, js);
		try {
			String[] names = new String[] { "sprop1", "sprop2", "sfunc1" };
			IJSSourceModule module = (IJSSourceModule) getSourceModule(
					TestConstants.PROJECT_NAME_VJETPROJECT, "src", new Path(js));
			int position = lastPositionInFile("sfunc1 : function() {", module);
			containsNames(module, position, names,
					ICategoryRequestor.TYPE_CATEGORY);
		} finally {
			FixtureUtils.tearDownFixture(m_fixtureManager);
		}
	}

	public void testPropsPropertiesInFunction() throws ModelException {
		String js = "partials/Partials6.js";
		FixtureManager m_fixtureManager = FixtureUtils.setUpFixture(this, js);
		try {
			String[] names = new String[] { "sprop1", "sprop2", "sfunc1" };
			IJSSourceModule module = (IJSSourceModule) getSourceModule(
					TestConstants.PROJECT_NAME_VJETPROJECT, "src", new Path(js));
			int position = lastPositionInFile("s", module);
			containsNames(module, position, names,
					ICategoryRequestor.TYPE_CATEGORY);
		} finally {
			FixtureUtils.tearDownFixture(m_fixtureManager);
		}
	}

	public void testFunction() throws ModelException {
		String js = "partials/Partials7.js";
		FixtureManager m_fixtureManager = FixtureUtils.setUpFixture(this, js);
		try {
			String[] names = new String[] { "sprop1", "sprop2", "sfunc1",
					"func2" };
			IJSSourceModule module = (IJSSourceModule) getSourceModule(
					TestConstants.PROJECT_NAME_VJETPROJECT, "src", new Path(js));
			int position = lastPositionInFile("func2 : function() {", module);
			containsNames(module, position, names,
					ICategoryRequestor.TYPE_CATEGORY);
		} finally {
			FixtureUtils.tearDownFixture(m_fixtureManager);
		}
	}

	public void testThisRefInFunction() throws ModelException {
		String js = "partials/Partials7.js";
		FixtureManager m_fixtureManager = FixtureUtils.setUpFixture(this, js);
		try {
			String[] names = new String[] { "sprop1", "sprop2", "sfunc1",
					"func2" };
			IJSSourceModule module = (IJSSourceModule) getSourceModule(
					TestConstants.PROJECT_NAME_VJETPROJECT, "src", new Path(js));
			int position = lastPositionInFile("this.", module);
			containsNames(module, position, names,
					ICategoryRequestor.TYPE_CATEGORY);
		} finally {
			FixtureUtils.tearDownFixture(m_fixtureManager);
		}
	}

	public void testTryExclusionOutsideFunction() throws ModelException {
		String js = "partials/Partials8.js";
		FixtureManager m_fixtureManager = FixtureUtils.setUpFixture(this, js);
		try {
			String[] names = new String[] { "try" };
			IJSSourceModule module = (IJSSourceModule) getSourceModule(
					TestConstants.PROJECT_NAME_VJETPROJECT, "src", new Path(js));
			int position = lastPositionInFile("try", module);
			excludesNames(module, position, names,
					ICategoryRequestor.TYPE_CATEGORY);
		} finally {
			FixtureUtils.tearDownFixture(m_fixtureManager);
		}
	}

	public void testIfExclusionOutsideFunction() throws ModelException {
		String js = "partials/Partials9.js";
		FixtureManager m_fixtureManager = FixtureUtils.setUpFixture(this, js);
		try {
			String[] names = new String[] { "if" };
			IJSSourceModule module = (IJSSourceModule) getSourceModule(
					TestConstants.PROJECT_NAME_VJETPROJECT, "src", new Path(js));
			int position = lastPositionInFile("i", module);
			excludesNames(module, position, names,
					ICategoryRequestor.TYPE_CATEGORY);
		} finally {
			FixtureUtils.tearDownFixture(m_fixtureManager);
		}
	}

	public void testInstanceOfProposal() throws ModelException {
		String js = "partials/InstanceOfTest.js";
		FixtureManager m_fixtureManager = FixtureUtils.setUpFixture(this, js);
		try {
			String[] names = new String[] { "instanceof" };
			IJSSourceModule module = (IJSSourceModule) getSourceModule(
					TestConstants.PROJECT_NAME_VJETPROJECT, "src", new Path(js));
			int position = lastPositionInFile("inst", module);
			containsNames(module, position, names,
					ICategoryRequestor.TYPE_CATEGORY);
		} finally {
			FixtureUtils.tearDownFixture(m_fixtureManager);
		}
	}

	public void testNaNProposal() throws ModelException {
		String js = "partials/NaNTest.js";
		FixtureManager m_fixtureManager = FixtureUtils.setUpFixture(this, js);
		try {
			String[] names = new String[] { "NaN" };
			IJSSourceModule module = (IJSSourceModule) getSourceModule(
					TestConstants.PROJECT_NAME_VJETPROJECT, "src", new Path(js));
			int position = lastPositionInFile("N", module);
			containsNames(module, position, names,
					ICategoryRequestor.TYPE_CATEGORY);
		} finally {
			FixtureUtils.tearDownFixture(m_fixtureManager);
		}
	}

	public void testEvalProposal() throws ModelException {
		String js = "partials/EvalFunc.js";
		FixtureManager m_fixtureManager = FixtureUtils.setUpFixture(this, js);
		try {
			String[] names = new String[] { "eval" };
			IJSSourceModule module = (IJSSourceModule) getSourceModule(
					TestConstants.PROJECT_NAME_VJETPROJECT, "src", new Path(js));
			int position = lastPositionInFile(" e", module);
			containsNames(module, position, names,
					ICategoryRequestor.TYPE_CATEGORY);
		} finally {
			FixtureUtils.tearDownFixture(m_fixtureManager);
		}
	}

	public void testRepeatEndTypesProposal() throws ModelException {
		String js = "partials/RepeatEndTypes.js";
		FixtureManager m_fixtureManager = FixtureUtils.setUpFixture(this, js);
		try {
			String[] names = new String[] { "endType()" };
			IJSSourceModule module = (IJSSourceModule) getSourceModule(
					TestConstants.PROJECT_NAME_VJETPROJECT, "src", new Path(js));
			int position = lastPositionInFile(").", module);
			excludesNames(module, position, names,
					ICategoryRequestor.TYPE_CATEGORY);
		} finally {
			FixtureUtils.tearDownFixture(m_fixtureManager);
		}
	}

	public void testDoubleUnderscoreProposal() throws ModelException {
		String js = "partials/DoubleUnderscoreTest.js";
		FixtureManager m_fixtureManager = FixtureUtils.setUpFixture(this, js);
		try {
			String[] names = new String[] { "__noSuchMethod__", "__parent__",
					"__proto__", "__defineGetter__", "__defineSetter__",
					"__lookupGetter__", "__lookupSetter__" };
			IJSSourceModule module = (IJSSourceModule) getSourceModule(
					TestConstants.PROJECT_NAME_VJETPROJECT, "src", new Path(js));
			int position = lastPositionInFile("arguments)", module) + 1;
			excludesNames(module, position, names,
					ICategoryRequestor.TYPE_CATEGORY);
		} finally {
			FixtureUtils.tearDownFixture(m_fixtureManager);
		}
	}

	public void testPropsReferenceProposal() throws ModelException {
		String js = "partials/PropsReference.js";
		FixtureManager m_fixtureManager = FixtureUtils.setUpFixture(this, js);
		try {
			String[] names = new String[] { "this.vj$.PropsReference.s_init",
					"this.vj$.PropsReference.helloWorld" };
			IJSSourceModule module = (IJSSourceModule) getSourceModule(
					TestConstants.PROJECT_NAME_VJETPROJECT, "src", new Path(js));
			int position = lastPositionInFile("name; ", module);
			doTypeCodeCompleate(module, position, names[0]);
		} finally {
			FixtureUtils.tearDownFixture(m_fixtureManager);
		}
	}

	public void testCompletionAfterNeedsInherits() throws ModelException {
		String js = "partials/CompletionAfterNeedsInherits.js";
		FixtureManager m_fixtureManager = FixtureUtils.setUpFixture(this, js);
		try {
			String[] names = new String[] { "needs('')", "satisfies('')",
					"mixin('')", "inits(function(){})", "protos({})",
					"endType()" };
			IJSSourceModule module = (IJSSourceModule) getSourceModule(
					TestConstants.PROJECT_NAME_VJETPROJECT, "src", new Path(js));
			int position = lastPositionInFile(
					"inherits('partials.RunTimeExceptionTest').", module);
			containsNames(module, position, names,
					ICategoryRequestor.TYPE_CATEGORY);
		} finally {
			FixtureUtils.tearDownFixture(m_fixtureManager);
		}
	}

	public void testThisReferenceProposal() throws ModelException {
		String js = "partials/ThisReference.js";
		FixtureManager m_fixtureManager = FixtureUtils.setUpFixture(this, js);
		try {
			String[] containNames = new String[] { "s_init", "helloWorld",
					"helloWorld1" };
			String[] excludeNames = new String[] { "setName", "helloWorld2",
					"helloWorld3" };
			IJSSourceModule module = (IJSSourceModule) getSourceModule(
					TestConstants.PROJECT_NAME_VJETPROJECT, "src", new Path(js));
			int position = lastPositionInFile("this.vj$.ThisReference.", module);
			excludesNames(module, position, excludeNames,
					ICategoryRequestor.TYPE_CATEGORY);
			containsNames(module, position, containNames,
					ICategoryRequestor.TYPE_CATEGORY);
		} finally {
			FixtureUtils.tearDownFixture(m_fixtureManager);
		}
	}

	public void testThisReferencePropsProposal() throws ModelException {
		String js = "partials/ThisReferenceProps.js";
		FixtureManager m_fixtureManager = FixtureUtils.setUpFixture(this, js);
		try {
			String[] containNames = new String[] { "s_init", "helloWorld",
					"helloWorld1", "this.vj$" };
			String[] excludeNames = new String[] { "setName", "helloWorld2",
					"helloWorld3" };
			IJSSourceModule module = (IJSSourceModule) getSourceModule(
					TestConstants.PROJECT_NAME_VJETPROJECT, "src", new Path(js));
			int position = lastPositionInFile("this.", module);
			excludesNames(module, position, excludeNames,
					ICategoryRequestor.TYPE_CATEGORY);
			containsNames(module, position, containNames,
					ICategoryRequestor.TYPE_CATEGORY);
		} finally {
			FixtureUtils.tearDownFixture(m_fixtureManager);
		}
	}

	public void testInstanceProposalTest() throws ModelException {
		String js = "partials/InstanceProposalTest.js";
		FixtureManager m_fixtureManager = FixtureUtils.setUpFixture(this, js);
		try {
			String[] containNames = new String[] { "s_prop", "s1", "s2", "s3",
					"e1", "testMe" };
			IJSSourceModule module = (IJSSourceModule) getSourceModule(
					TestConstants.PROJECT_NAME_VJETPROJECT, "src", new Path(js));
			int position = lastPositionInFile("instanceObj.", module);
			containsNames(module, position, containNames,
					ICategoryRequestor.TYPE_CATEGORY);
		} finally {
			FixtureUtils.tearDownFixture(m_fixtureManager);
		}
	}

	// public void testNeedsAliasProposal() throws ModelException {
	// String js = ""partials/NeedsAliasTest.js"";
	// FixtureManager m_fixtureManager = FixtureUtils.setUpFixture(this, js);
	// try {
	// String[] containNames = new String[] { "staticDo" };
	// IJSSourceModule module = (IJSSourceModule) getSourceModule(
	// TestConstants.PROJECT_NAME_VJETPROJECT, "src", new Path(js"));
	// int position = lastPositionInFile("this.vj$.MyAlias.", module);
	// containsNames(module, position, containNames,
	// ICategoryRequestor.TYPE_CATEGORY);
	// } finally {
	// FixtureUtils.tearDownFixture(m_fixtureManager);
	// }
	// }

	public void testNeedsAliasExtnProposal() throws ModelException {
		String js = "partials/NeedsAliasTestExtn.js";
		FixtureManager m_fixtureManager = FixtureUtils.setUpFixture(this, js);
		try {
			String[] containNames = new String[] { "staticDo", "doIt" };
			IJSSourceModule module = (IJSSourceModule) getSourceModule(
					TestConstants.PROJECT_NAME_VJETPROJECT, "src", new Path(js));
			int position = lastPositionInFile("x.", module);
			containsNames(module, position, containNames,
					ICategoryRequestor.TYPE_CATEGORY);
		} finally {
			FixtureUtils.tearDownFixture(m_fixtureManager);
		}
	}

	public void testPropertyType() throws ModelException {
		String js = "partials/PropertyType.js";
		FixtureManager m_fixtureManager = FixtureUtils.setUpFixture(this, js);
		try {
			String[] names = new String[] { "sprop1", "sprop2", "sprop3",
					"sprop4", "sprop5", "sprop6", "sfunc1", "func2" };
			String[] types = new String[] { "int - PropertyType",
					"String - PropertyType", "Object - PropertyType",
					"Class - PropertyType", "Integer - PropertyType",
					"partials.PropertyType - PropertyType",
					"void - PropertyType", "void - PropertyType" };
			IJSSourceModule module = (IJSSourceModule) getSourceModule(
					TestConstants.PROJECT_NAME_VJETPROJECT, "src", new Path(js));
			int position = lastPositionInFile("this.", module);
			assertNotSame("Invalid file content, cant find position", -1,
					position);

			LinkedList<CompletionProposal> results = new LinkedList<CompletionProposal>();
			VjoCompletionEngine c = createEngine(results,
					ICategoryRequestor.TYPE_CATEGORY, module);
			c.complete((ISourceModule) module, position, 0);

			Map<String, String> resultNames = new HashMap<String, String>();
			for (CompletionProposal proposal : results) {
				Object obj = proposal.extraInfo;
				if (obj != null) {
					resultNames.put(String.valueOf(proposal.getCompletion())
							.trim(), obj.toString().trim());
				}
			}
			for (int i = 0; i < names.length; i++) {
				assertTrue("Results not contains " + names[i]
						+ " proposal in map " + resultNames, resultNames
						.containsKey(names[i]));
				assertTrue("Results not contains type info for " + names[i]
						+ " in map " + resultNames, resultNames.get(names[i])
						.equals(types[i]));
			}
		} finally {
			FixtureUtils.tearDownFixture(m_fixtureManager);
		}
	}

	public void testArgumentsProposal() throws ModelException {
		String js = "partials/ArgumentsObj.js";
		FixtureManager m_fixtureManager = FixtureUtils.setUpFixture(this, js);
		try {
			String[] names = new String[] { "Arguments" };
			IJSSourceModule module = (IJSSourceModule) getSourceModule(
					TestConstants.PROJECT_NAME_VJETPROJECT, "src", new Path(js));
			int position = lastPositionInFile("A", module);
			containsNames(module, position, names,
					ICategoryRequestor.TYPE_CATEGORY);
		} finally {
			FixtureUtils.tearDownFixture(m_fixtureManager);
		}
	}

	public void testArgumentsObjectProposal() throws ModelException {
		String js = "partials/ArgumentsFunc.js";
		FixtureManager m_fixtureManager = FixtureUtils.setUpFixture(this, js);
		try {
			String[] names = getSuggestions(org.ebayopensource.dsf.jsnative.global.Arguments.class);
			IJSSourceModule module = (IJSSourceModule) getSourceModule(
					TestConstants.PROJECT_NAME_VJETPROJECT, "src", new Path(js));
			int position = lastPositionInFile("a.", module);
			containsNames(module, position, names,
					ICategoryRequestor.TYPE_CATEGORY);
		} finally {
			FixtureUtils.tearDownFixture(m_fixtureManager);
		}
	}

	public void testArrayProposal() throws ModelException {
		String js = "partials/ArrayObj.js";
		FixtureManager m_fixtureManager = FixtureUtils.setUpFixture(this, js);
		try {
			String[] names = new String[] { "Array" };
			IJSSourceModule module = (IJSSourceModule) getSourceModule(
					TestConstants.PROJECT_NAME_VJETPROJECT, "src", new Path(js));
			int position = lastPositionInFile("A", module);
			containsNames(module, position, names,
					ICategoryRequestor.TYPE_CATEGORY);
		} finally {
			FixtureUtils.tearDownFixture(m_fixtureManager);
		}
	}

	public void testArrayObjectProposal() throws ModelException {
		String js = "partials/ArrayFunc.js";
		FixtureManager m_fixtureManager = FixtureUtils.setUpFixture(this, js);
		try {
			String[] names = getSuggestions(org.ebayopensource.dsf.jsnative.global.Array.class);
			IJSSourceModule module = (IJSSourceModule) getSourceModule(
					TestConstants.PROJECT_NAME_VJETPROJECT, "src", new Path(js));
			int position = lastPositionInFile("a.", module);
			containsNames(module, position, names,
					ICategoryRequestor.TYPE_CATEGORY);
		} finally {
			FixtureUtils.tearDownFixture(m_fixtureManager);
		}
	}

	public void testArrayPartial() throws ModelException {
		String js = "partials/ArrayPartial.js";
		FixtureManager m_fixtureManager = FixtureUtils.setUpFixture(this, js);
		try {
			String[] containNames = getSuggestions(org.ebayopensource.dsf.jsnative.global.Array.class);
			IJSSourceModule module = (IJSSourceModule) getSourceModule(
					TestConstants.PROJECT_NAME_VJETPROJECT, "src", new Path(js));
			int position = lastPositionInFile("    arr.", module);
			containsNames(module, position, containNames,
					ICategoryRequestor.TYPE_CATEGORY);
		} finally {
			FixtureUtils.tearDownFixture(m_fixtureManager);
		}
	}

	public void testBooleanProposal() throws ModelException {
		String js = "partials/BooleanObj.js";
		FixtureManager m_fixtureManager = FixtureUtils.setUpFixture(this, js);
		try {
			String[] names = new String[] { "Boolean" };
			IJSSourceModule module = (IJSSourceModule) getSourceModule(
					TestConstants.PROJECT_NAME_VJETPROJECT, "src", new Path(js));
			int position = lastPositionInFile("B", module);
			containsNames(module, position, names,
					ICategoryRequestor.TYPE_CATEGORY);
		} finally {
			FixtureUtils.tearDownFixture(m_fixtureManager);
		}
	}

	public void testBooleanObjectProposal() throws ModelException {
		String js = "partials/BooleanFunc.js";
		FixtureManager m_fixtureManager = FixtureUtils.setUpFixture(this, js);
		try {
			String[] names = getSuggestions(org.ebayopensource.dsf.jsnative.global.Boolean.class);
			IJSSourceModule module = (IJSSourceModule) getSourceModule(
					TestConstants.PROJECT_NAME_VJETPROJECT, "src", new Path(js));
			int position = lastPositionInFile("b.", module);
			containsNames(module, position, names,
					ICategoryRequestor.TYPE_CATEGORY);
		} finally {
			FixtureUtils.tearDownFixture(m_fixtureManager);
		}
	}

	public void testDateProposal() throws ModelException {
		String js = "partials/DateObj.js";
		FixtureManager m_fixtureManager = FixtureUtils.setUpFixture(this, js);
		try {
			String[] names = new String[] { "Date" };
			IJSSourceModule module = (IJSSourceModule) getSourceModule(
					TestConstants.PROJECT_NAME_VJETPROJECT, "src", new Path(js));
			int position = lastPositionInFile("D", module);
			containsNames(module, position, names,
					ICategoryRequestor.TYPE_CATEGORY);
		} finally {
			FixtureUtils.tearDownFixture(m_fixtureManager);
		}
	}

	public void testDateObjectProposal() throws ModelException {
		String js = "partials/DateFunc.js";
		FixtureManager m_fixtureManager = FixtureUtils.setUpFixture(this, js);
		try {
			String[] names = getSuggestions(org.ebayopensource.dsf.jsnative.global.Date.class);
			IJSSourceModule module = (IJSSourceModule) getSourceModule(
					TestConstants.PROJECT_NAME_VJETPROJECT, "src", new Path(js));
			int position = lastPositionInFile("d.", module);
			containsNames(module, position, names,
					ICategoryRequestor.TYPE_CATEGORY);
		} finally {
			FixtureUtils.tearDownFixture(m_fixtureManager);
		}
	}

	public void testDocumentProposal() throws ModelException {
		String js = "partials/DocumentObj.js";
		FixtureManager m_fixtureManager = FixtureUtils.setUpFixture(this, js);
		try {
			String[] names = new String[] { "document" };
			IJSSourceModule module = (IJSSourceModule) getSourceModule(
					TestConstants.PROJECT_NAME_VJETPROJECT, "src", new Path(js));
			int position = lastPositionInFile(" d", module);
			containsNames(module, position, names,
					ICategoryRequestor.TYPE_CATEGORY);
		} finally {
			FixtureUtils.tearDownFixture(m_fixtureManager);
		}
	}

	public void testDocumentObjectProposal() throws ModelException {
		String js = "partials/DocumentFunc.js";
		FixtureManager m_fixtureManager = FixtureUtils.setUpFixture(this, js);
		try {
			String[] names = getSuggestions(HtmlDocument.class);
			IJSSourceModule module = (IJSSourceModule) getSourceModule(
					TestConstants.PROJECT_NAME_VJETPROJECT, "src", new Path(js));
			int position = lastPositionInFile("document.", module);
			containsNames(module, position, names,
					ICategoryRequestor.TYPE_CATEGORY);
		} finally {
			FixtureUtils.tearDownFixture(m_fixtureManager);
		}
	}

	public void testDocumentDotWriteProposal() throws ModelException {
		String js = "partials/DocumentFunc.js";
		FixtureManager m_fixtureManager = FixtureUtils.setUpFixture(this, js);
		try {
			String name = "write(text)";
			IJSSourceModule module = (IJSSourceModule) getSourceModule(
					TestConstants.PROJECT_NAME_VJETPROJECT, "src", new Path(js));
			int position = lastPositionInFile("document.", module);
			doTypeCodeCompleate(module, position, name);
		} finally {
			FixtureUtils.tearDownFixture(m_fixtureManager);
		}
	}

	public void testElementListProposal() throws ModelException {
		String js = "partials/ElementObj.js";
		FixtureManager m_fixtureManager = FixtureUtils.setUpFixture(this, js);
		try {
			String[] names = new String[] { "Element" };
			IJSSourceModule module = (IJSSourceModule) getSourceModule(
					TestConstants.PROJECT_NAME_VJETPROJECT, "src", new Path(js));
			int position = lastPositionInFile("El", module);
			containsNames(module, position, names,
					ICategoryRequestor.TYPE_CATEGORY);
		} finally {
			FixtureUtils.tearDownFixture(m_fixtureManager);
		}
	}

	public void testElementObjectProposal() throws ModelException {
		String js = "partials/ElementFunc.js";
		FixtureManager m_fixtureManager = FixtureUtils.setUpFixture(this, js);
		try {
			String[] names = getSuggestions(org.ebayopensource.dsf.jsnative.Element.class);
			IJSSourceModule module = (IJSSourceModule) getSourceModule(
					TestConstants.PROJECT_NAME_VJETPROJECT, "src", new Path(js));
			int position = lastPositionInFile("el.", module);
			containsNames(module, position, names,
					ICategoryRequestor.TYPE_CATEGORY);
		} finally {
			FixtureUtils.tearDownFixture(m_fixtureManager);
		}
	}

	public void testEnumeratorProposal() throws ModelException {
		String js = "partials/EnumeratorObj.js";
		FixtureManager m_fixtureManager = FixtureUtils.setUpFixture(this, js);
		try {
			String[] names = new String[] { "Enumerator" };
			IJSSourceModule module = (IJSSourceModule) getSourceModule(
					TestConstants.PROJECT_NAME_VJETPROJECT, "src", new Path(js));
			int position = lastPositionInFile("Enu", module);
			containsNames(module, position, names,
					ICategoryRequestor.TYPE_CATEGORY);
		} finally {
			FixtureUtils.tearDownFixture(m_fixtureManager);
		}
	}

	public void testEnumeratorObjectProposal() throws ModelException {
		String js = "partials/EnumeratorFunc.js";
		FixtureManager m_fixtureManager = FixtureUtils.setUpFixture(this, js);
		try {
			String[] names = getSuggestions(org.ebayopensource.dsf.jsnative.global.Enumerator.class);
			IJSSourceModule module = (IJSSourceModule) getSourceModule(
					TestConstants.PROJECT_NAME_VJETPROJECT, "src", new Path(js));
			int position = lastPositionInFile("enu.", module);
			containsNames(module, position, names,
					ICategoryRequestor.TYPE_CATEGORY);
		} finally {
			FixtureUtils.tearDownFixture(m_fixtureManager);
		}
	}

	public void testErrorProposal() throws ModelException {
		String js = "partials/ErrorObj.js";
		FixtureManager m_fixtureManager = FixtureUtils.setUpFixture(this, js);
		try {
			String[] names = new String[] { "Error", "EvalError" };
			IJSSourceModule module = (IJSSourceModule) getSourceModule(
					TestConstants.PROJECT_NAME_VJETPROJECT, "src", new Path(js));
			int position = lastPositionInFile("E", module);
			containsNames(module, position, names,
					ICategoryRequestor.TYPE_CATEGORY);
		} finally {
			FixtureUtils.tearDownFixture(m_fixtureManager);
		}
	}

	public void testErrorObjectProposal() throws ModelException {
		String js = "partials/ErrorFunc.js";
		FixtureManager m_fixtureManager = FixtureUtils.setUpFixture(this, js);
		try {
			String[] names = getSuggestions(org.ebayopensource.dsf.jsnative.global.Error.class);
			IJSSourceModule module = (IJSSourceModule) getSourceModule(
					TestConstants.PROJECT_NAME_VJETPROJECT, "src", new Path(js));
			int position = lastPositionInFile("err.", module);
			containsNames(module, position, names,
					ICategoryRequestor.TYPE_CATEGORY);
		} finally {
			FixtureUtils.tearDownFixture(m_fixtureManager);
		}
	}

	public void testEvalErrorObjectProposal() throws ModelException {
		String js = "partials/EvalErrorFunc.js";
		FixtureManager m_fixtureManager = FixtureUtils.setUpFixture(this, js);
		try {
			String[] names = getSuggestions(org.ebayopensource.dsf.jsnative.global.EvalError.class);
			IJSSourceModule module = (IJSSourceModule) getSourceModule(
					TestConstants.PROJECT_NAME_VJETPROJECT, "src", new Path(js));
			int position = lastPositionInFile("err.", module);
			containsNames(module, position, names,
					ICategoryRequestor.TYPE_CATEGORY);
		} finally {
			FixtureUtils.tearDownFixture(m_fixtureManager);
		}
	}

	public void testFunctionProposal() throws ModelException {
		String js = "partials/FunctionObj.js";
		FixtureManager m_fixtureManager = FixtureUtils.setUpFixture(this, js);
		try {
			String[] names = new String[] { "Function" };
			IJSSourceModule module = (IJSSourceModule) getSourceModule(
					TestConstants.PROJECT_NAME_VJETPROJECT, "src", new Path(js));
			int position = lastPositionInFile("F", module);
			containsNames(module, position, names,
					ICategoryRequestor.TYPE_CATEGORY);
		} finally {
			FixtureUtils.tearDownFixture(m_fixtureManager);
		}
	}

	public void testFunctionObjectProposal() throws ModelException {
		String js = "partials/FunctionFunc.js";
		FixtureManager m_fixtureManager = FixtureUtils.setUpFixture(this, js);
		try {
			String[] names = getSuggestions(org.ebayopensource.dsf.jsnative.global.Function.class);
			IJSSourceModule module = (IJSSourceModule) getSourceModule(
					TestConstants.PROJECT_NAME_VJETPROJECT, "src", new Path(js));
			int position = lastPositionInFile("f.", module);
			containsNames(module, position, names,
					ICategoryRequestor.TYPE_CATEGORY);
		} finally {
			FixtureUtils.tearDownFixture(m_fixtureManager);
		}
	}

	public void testGlobalProposal() throws ModelException {
		String js = "partials/GlobalObj.js";
		FixtureManager m_fixtureManager = FixtureUtils.setUpFixture(this, js);
		try {
			String[] names = new String[] { "Global" };
			IJSSourceModule module = (IJSSourceModule) getSourceModule(
					TestConstants.PROJECT_NAME_VJETPROJECT, "src", new Path(js));
			int position = lastPositionInFile("G", module);
			excludesNames(module, position, names,
					ICategoryRequestor.TYPE_CATEGORY);
		} finally {
			FixtureUtils.tearDownFixture(m_fixtureManager);
		}
	}

	public void testGlobalObjectProposal() throws ModelException {
		String js = "partials/GlobalFunc.js";
		FixtureManager m_fixtureManager = FixtureUtils.setUpFixture(this, js);
		try {
			String[] names = getSuggestions(org.ebayopensource.dsf.jsnative.global.Global.class);
			IJSSourceModule module = (IJSSourceModule) getSourceModule(
					TestConstants.PROJECT_NAME_VJETPROJECT, "src", new Path(js));
			int position = lastPositionInFile("g.", module);
			containsNames(module, position, names,
					ICategoryRequestor.TYPE_CATEGORY);
		} finally {
			FixtureUtils.tearDownFixture(m_fixtureManager);
		}
	}

	public void testMathProposal() throws ModelException {
		String js = "partials/MathObj.js";
		FixtureManager m_fixtureManager = FixtureUtils.setUpFixture(this, js);
		try {
			String[] names = new String[] { "Math" };
			IJSSourceModule module = (IJSSourceModule) getSourceModule(
					TestConstants.PROJECT_NAME_VJETPROJECT, "src", new Path(js));
			int position = lastPositionInFile("M", module);
			containsNames(module, position, names,
					ICategoryRequestor.TYPE_CATEGORY);
		} finally {
			FixtureUtils.tearDownFixture(m_fixtureManager);
		}
	}

	public void testMathObjectProposal() throws ModelException {
		String js = "partials/MathFunc.js";
		FixtureManager m_fixtureManager = FixtureUtils.setUpFixture(this, js);
		try {
			String[] names = getSuggestions(org.ebayopensource.dsf.jsnative.global.Math.class);
			IJSSourceModule module = (IJSSourceModule) getSourceModule(
					TestConstants.PROJECT_NAME_VJETPROJECT, "src", new Path(js));
			int position = lastPositionInFile("m.", module);
			containsNames(module, position, names,
					ICategoryRequestor.TYPE_CATEGORY);
		} finally {
			FixtureUtils.tearDownFixture(m_fixtureManager);
		}
	}

	public void testMathObjectProposalMore() throws ModelException {
		String js = "partials/MathFuncMore.js";
		FixtureManager m_fixtureManager = FixtureUtils.setUpFixture(this, js);
		try {
			String[] names = getSuggestions(org.ebayopensource.dsf.jsnative.global.Math.class);
			IJSSourceModule module = (IJSSourceModule) getSourceModule(
					TestConstants.PROJECT_NAME_VJETPROJECT, "src", new Path(js));
			int position = lastPositionInFile("Math.", module);
			containsNames(module, position, names,
					ICategoryRequestor.TYPE_CATEGORY);
		} finally {
			FixtureUtils.tearDownFixture(m_fixtureManager);
		}
	}

	public void testNodeObjectProposal() throws ModelException {
		String js = "partials/NodeFunc.js";
		FixtureManager m_fixtureManager = FixtureUtils.setUpFixture(this, js);
		try {
			String[] names = getSuggestions(org.ebayopensource.dsf.jsnative.Node.class);
			IJSSourceModule module = (IJSSourceModule) getSourceModule(
					TestConstants.PROJECT_NAME_VJETPROJECT, "src", new Path(js));
			int position = lastPositionInFile("n.", module);
			containsNames(module, position, names,
					ICategoryRequestor.TYPE_CATEGORY);
		} finally {
			FixtureUtils.tearDownFixture(m_fixtureManager);
		}
	}

	public void testNodeListObjectProposal() throws ModelException {
		String js = "partials/NodeListFunc.js";
		FixtureManager m_fixtureManager = FixtureUtils.setUpFixture(this, js);
		try {
			String[] names = getSuggestions(org.ebayopensource.dsf.jsnative.NodeList.class);
			IJSSourceModule module = (IJSSourceModule) getSourceModule(
					TestConstants.PROJECT_NAME_VJETPROJECT, "src", new Path(js));
			int position = lastPositionInFile("n.", module);
			containsNames(module, position, names,
					ICategoryRequestor.TYPE_CATEGORY);
		} finally {
			FixtureUtils.tearDownFixture(m_fixtureManager);
		}
	}

	public void testNumberProposal() throws ModelException {
		String js = "partials/NumberObj.js";
		FixtureManager m_fixtureManager = FixtureUtils.setUpFixture(this, js);
		try {
			String[] names = new String[] { "Number" };
			IJSSourceModule module = (IJSSourceModule) getSourceModule(
					TestConstants.PROJECT_NAME_VJETPROJECT, "src", new Path(js));
			int position = lastPositionInFile("N", module);
			containsNames(module, position, names,
					ICategoryRequestor.TYPE_CATEGORY);
		} finally {
			FixtureUtils.tearDownFixture(m_fixtureManager);
		}
	}

	public void testNumberObjectProposal() throws ModelException {
		String js = "partials/NumberFunc.js";
		FixtureManager m_fixtureManager = FixtureUtils.setUpFixture(this, js);
		try {
			String[] names = getSuggestions(org.ebayopensource.dsf.jsnative.global.Number.class);
			IJSSourceModule module = (IJSSourceModule) getSourceModule(
					TestConstants.PROJECT_NAME_VJETPROJECT, "src", new Path(js));
			int position = lastPositionInFile("n.", module);
			containsNames(module, position, names,
					ICategoryRequestor.TYPE_CATEGORY);
		} finally {
			FixtureUtils.tearDownFixture(m_fixtureManager);
		}
	}

	public void testNumberObjectProposalMore() throws ModelException {
		String js = "partials/NumberFuncMore.js";
		FixtureManager m_fixtureManager = FixtureUtils.setUpFixture(this, js);
		try {
			String[] names = new String[] { "Number" };
			IJSSourceModule module = (IJSSourceModule) getSourceModule(
					TestConstants.PROJECT_NAME_VJETPROJECT, "src", new Path(js));
			int position = lastPositionInFile("Numb", module);
			containsNames(module, position, names,
					ICategoryRequestor.TYPE_CATEGORY);
		} finally {
			FixtureUtils.tearDownFixture(m_fixtureManager);
		}
	}

	public void testObjectProposal() throws ModelException {
		String js = "partials/ObjectObj.js";
		FixtureManager m_fixtureManager = FixtureUtils.setUpFixture(this, js);
		try {
			String[] names = new String[] { "Object" };
			IJSSourceModule module = (IJSSourceModule) getSourceModule(
					TestConstants.PROJECT_NAME_VJETPROJECT, "src", new Path(js));
			int position = lastPositionInFile("O", module);
			containsNames(module, position, names,
					ICategoryRequestor.TYPE_CATEGORY);
		} finally {
			FixtureUtils.tearDownFixture(m_fixtureManager);
		}
	}

	public void testObjectObjProposal() throws ModelException {
		String js = "partials/ObjectFunc.js";
		FixtureManager m_fixtureManager = FixtureUtils.setUpFixture(this, js);
		try {
			String[] names = getSuggestions(org.ebayopensource.dsf.jsnative.global.Object.class);
			IJSSourceModule module = (IJSSourceModule) getSourceModule(
					TestConstants.PROJECT_NAME_VJETPROJECT, "src", new Path(js));
			int position = lastPositionInFile("o.", module);
			containsNames(module, position, names,
					ICategoryRequestor.TYPE_CATEGORY);
		} finally {
			FixtureUtils.tearDownFixture(m_fixtureManager);
		}
	}

	public void testRangeErrorProposal() throws ModelException {
		String js = "partials/RangeErrorObj.js";
		FixtureManager m_fixtureManager = FixtureUtils.setUpFixture(this, js);
		try {
			String[] names = new String[] { "RangeError", "ReferenceError" };
			IJSSourceModule module = (IJSSourceModule) getSourceModule(
					TestConstants.PROJECT_NAME_VJETPROJECT, "src", new Path(js));
			int position = lastPositionInFile("R", module);
			containsNames(module, position, names,
					ICategoryRequestor.TYPE_CATEGORY);
		} finally {
			FixtureUtils.tearDownFixture(m_fixtureManager);
		}
	}

	public void testRangeErrorObjectProposal() throws ModelException {
		String js = "partials/RangeErrorFunc.js";
		FixtureManager m_fixtureManager = FixtureUtils.setUpFixture(this, js);
		try {
			String[] names = getSuggestions(org.ebayopensource.dsf.jsnative.global.RangeError.class);
			IJSSourceModule module = (IJSSourceModule) getSourceModule(
					TestConstants.PROJECT_NAME_VJETPROJECT, "src", new Path(js));
			int position = lastPositionInFile("err.", module);
			containsNames(module, position, names,
					ICategoryRequestor.TYPE_CATEGORY);
		} finally {
			FixtureUtils.tearDownFixture(m_fixtureManager);
		}
	}

	public void testReferenceErrorObjectProposal() throws ModelException {
		String js = "partials/ReferenceErrorFunc.js";
		FixtureManager m_fixtureManager = FixtureUtils.setUpFixture(this, js);
		try {
			String[] names = getSuggestions(org.ebayopensource.dsf.jsnative.global.ReferenceError.class);
			IJSSourceModule module = (IJSSourceModule) getSourceModule(
					TestConstants.PROJECT_NAME_VJETPROJECT, "src", new Path(js));
			int position = lastPositionInFile("err.", module);
			containsNames(module, position, names,
					ICategoryRequestor.TYPE_CATEGORY);
		} finally {
			FixtureUtils.tearDownFixture(m_fixtureManager);
		}
	}

	public void testRegExpProposal() throws ModelException {
		String js = "partials/RegExpObj.js";
		FixtureManager m_fixtureManager = FixtureUtils.setUpFixture(this, js);
		try {
			String[] names = new String[] { "RegExp" };
			IJSSourceModule module = (IJSSourceModule) getSourceModule(
					TestConstants.PROJECT_NAME_VJETPROJECT, "src", new Path(js));
			int position = lastPositionInFile("R", module);
			containsNames(module, position, names,
					ICategoryRequestor.TYPE_CATEGORY);
		} finally {
			FixtureUtils.tearDownFixture(m_fixtureManager);
		}
	}

	public void testRegExpObjectProposal() throws ModelException {
		String js = "partials/RegExpFunc.js";
		FixtureManager m_fixtureManager = FixtureUtils.setUpFixture(this, js);
		try {
			String[] names = getSuggestions(org.ebayopensource.dsf.jsnative.global.RegExp.class);
			IJSSourceModule module = (IJSSourceModule) getSourceModule(
					TestConstants.PROJECT_NAME_VJETPROJECT, "src", new Path(js));
			int position = lastPositionInFile("r.", module);
			containsNames(module, position, names,
					ICategoryRequestor.TYPE_CATEGORY);
		} finally {
			FixtureUtils.tearDownFixture(m_fixtureManager);
		}
	}

	public void testStringProposal() throws ModelException {
		String js = "partials/StringObj.js";
		FixtureManager m_fixtureManager = FixtureUtils.setUpFixture(this, js);
		try {
			String[] names = new String[] { "String" };
			IJSSourceModule module = (IJSSourceModule) getSourceModule(
					TestConstants.PROJECT_NAME_VJETPROJECT, "src", new Path(js));
			int position = lastPositionInFile("S", module);
			containsNames(module, position, names,
					ICategoryRequestor.TYPE_CATEGORY);
		} finally {
			FixtureUtils.tearDownFixture(m_fixtureManager);
		}
	}

	public void testStringObjectProposal() throws ModelException {
		String js = "partials/StringFunc.js";
		FixtureManager m_fixtureManager = FixtureUtils.setUpFixture(this, js);
		try {
			String[] names = getSuggestions(org.ebayopensource.dsf.jsnative.global.String.class);
			IJSSourceModule module = (IJSSourceModule) getSourceModule(
					TestConstants.PROJECT_NAME_VJETPROJECT, "src", new Path(js));
			int position = lastPositionInFile("s.", module);
			containsNames(module, position, names,
					ICategoryRequestor.TYPE_CATEGORY);
		} finally {
			FixtureUtils.tearDownFixture(m_fixtureManager);
		}
	}

	public void testStringObjectProposalMore() throws ModelException {
		String js = "partials/StringFuncMore.js";
		FixtureManager m_fixtureManager = FixtureUtils.setUpFixture(this, js);
		try {
			String[] names = getSuggestions(org.ebayopensource.dsf.jsnative.global.String.class);
			IJSSourceModule module = (IJSSourceModule) getSourceModule(
					TestConstants.PROJECT_NAME_VJETPROJECT, "src", new Path(js));
			int position = lastPositionInFile("s.", module);
			containsNames(module, position, names,
					ICategoryRequestor.TYPE_CATEGORY);
		} finally {
			FixtureUtils.tearDownFixture(m_fixtureManager);
		}
	}

	public void testSyntaxErrorProposal() throws ModelException {
		String js = "partials/SyntaxErrorObj.js";
		FixtureManager m_fixtureManager = FixtureUtils.setUpFixture(this, js);
		try {
			String[] names = new String[] { "SyntaxError" };
			IJSSourceModule module = (IJSSourceModule) getSourceModule(
					TestConstants.PROJECT_NAME_VJETPROJECT, "src", new Path(js));
			int position = lastPositionInFile("S", module);
			containsNames(module, position, names,
					ICategoryRequestor.TYPE_CATEGORY);
		} finally {
			FixtureUtils.tearDownFixture(m_fixtureManager);
		}
	}

	public void testSyntaxErrorObjectProposal() throws ModelException {
		String js = "partials/SyntaxErrorFunc.js";
		FixtureManager m_fixtureManager = FixtureUtils.setUpFixture(this, js);
		try {
			String[] names = getSuggestions(org.ebayopensource.dsf.jsnative.global.SyntaxError.class);
			IJSSourceModule module = (IJSSourceModule) getSourceModule(
					TestConstants.PROJECT_NAME_VJETPROJECT, "src", new Path(js));
			int position = lastPositionInFile("err.", module);
			containsNames(module, position, names,
					ICategoryRequestor.TYPE_CATEGORY);
		} finally {
			FixtureUtils.tearDownFixture(m_fixtureManager);
		}
	}

	public void testTypeErrorProposal() throws ModelException {
		String js = "partials/TypeErrorObj.js";
		FixtureManager m_fixtureManager = FixtureUtils.setUpFixture(this, js);
		try {
			String[] names = new String[] { "TypeError" };
			IJSSourceModule module = (IJSSourceModule) getSourceModule(
					TestConstants.PROJECT_NAME_VJETPROJECT, "src", new Path(js));
			int position = lastPositionInFile("new T", module);
			containsNames(module, position, names,
					ICategoryRequestor.TYPE_CATEGORY);
		} finally {
			FixtureUtils.tearDownFixture(m_fixtureManager);
		}
	}

	public void testTypeErrorObjectProposal() throws ModelException {
		String js = "partials/TypeErrorFunc.js";
		FixtureManager m_fixtureManager = FixtureUtils.setUpFixture(this, js);
		try {
			String[] names = getSuggestions(org.ebayopensource.dsf.jsnative.global.TypeError.class);
			IJSSourceModule module = (IJSSourceModule) getSourceModule(
					TestConstants.PROJECT_NAME_VJETPROJECT, "src", new Path(js));
			int position = lastPositionInFile("err.", module);
			containsNames(module, position, names,
					ICategoryRequestor.TYPE_CATEGORY);
		} finally {
			FixtureUtils.tearDownFixture(m_fixtureManager);
		}
	}

	public void testURIErrorProposal() throws ModelException {
		String js = "partials/URIErrorObj.js";
		FixtureManager m_fixtureManager = FixtureUtils.setUpFixture(this, js);
		try {
			String[] names = new String[] { "URIError" };
			IJSSourceModule module = (IJSSourceModule) getSourceModule(
					TestConstants.PROJECT_NAME_VJETPROJECT, "src", new Path(js));
			int position = lastPositionInFile("U", module);
			containsNames(module, position, names,
					ICategoryRequestor.TYPE_CATEGORY);
		} finally {
			FixtureUtils.tearDownFixture(m_fixtureManager);
		}
	}

	public void testURIErrorObjectProposal() throws ModelException {
		String js = "partials/URIErrorFunc.js";
		FixtureManager m_fixtureManager = FixtureUtils.setUpFixture(this, js);
		try {
			String[] names = getSuggestions(org.ebayopensource.dsf.jsnative.global.URIError.class);
			IJSSourceModule module = (IJSSourceModule) getSourceModule(
					TestConstants.PROJECT_NAME_VJETPROJECT, "src", new Path(js));
			int position = lastPositionInFile("err.", module);
			containsNames(module, position, names,
					ICategoryRequestor.TYPE_CATEGORY);
		} finally {
			FixtureUtils.tearDownFixture(m_fixtureManager);
		}
	}

	public void testWindowProposal() throws ModelException {
		String js = "partials/WindowObj.js";
		FixtureManager m_fixtureManager = FixtureUtils.setUpFixture(this, js);
		try {
			String[] names = new String[] { "Window" };
			IJSSourceModule module = (IJSSourceModule) getSourceModule(
					TestConstants.PROJECT_NAME_VJETPROJECT, "src", new Path(js));
			int position = lastPositionInFile("W", module);
			containsNames(module, position, names,
					ICategoryRequestor.TYPE_CATEGORY);
		} finally {
			FixtureUtils.tearDownFixture(m_fixtureManager);
		}
	}

	public void testWindowObjectProposal() throws ModelException {
		String js = "partials/WindowFunc.js";
		FixtureManager m_fixtureManager = FixtureUtils.setUpFixture(this, js);
		try {
			String[] names = getSuggestions(org.ebayopensource.dsf.jsnative.Window.class);
			IJSSourceModule module = (IJSSourceModule) getSourceModule(
					TestConstants.PROJECT_NAME_VJETPROJECT, "src", new Path(js));
			int position = lastPositionInFile("win.", module);
			containsNames(module, position, names,
					ICategoryRequestor.TYPE_CATEGORY);
		} finally {
			FixtureUtils.tearDownFixture(m_fixtureManager);
		}
	}

	public void testMethodStubCompletion() throws ModelException {
		String js = "partials/RunTimeExceptionTest.js";
		FixtureManager m_fixtureManager = FixtureUtils.setUpFixture(this, js);
		try {
			IJSSourceModule module = (IJSSourceModule) getSourceModule(
					TestConstants.PROJECT_NAME_VJETPROJECT, "src", new Path(js));
			int position = lastPositionInFile("getName", module);
			assertNotSame("Invalid file content, cant find position", -1,
					position);

			LinkedList<CompletionProposal> results = new LinkedList<CompletionProposal>();

			VjoCompletionEngine c = createEngine(results,
					ICategoryRequestor.TEXT_CATEGORY, module);
			c.complete((ISourceModule) module, position, 0);
			assertTrue("New method stub completion should be provided", results
					.size() > 0);
			CompletionProposal cp = results.getFirst();
			assertEquals("Invalid completion proposal kind", "getName",
					new String(cp.getName()));
			assertTrue(
					"Invalid completion proposal kind",
					cp.getKind() == CompletionProposal.POTENTIAL_METHOD_DECLARATION);
		} finally {
			FixtureUtils.tearDownFixture(m_fixtureManager);
		}
	}

	public void testAsyncLoadJs() throws Exception {
		String js = "partials/RunTimeExceptionTest.js";
		FixtureManager m_fixtureManager = FixtureUtils.setUpFixture(this, js);
		try {
			fullyLoaded = false;
			IJstParseController controller = new JstParseController(
					new VjoParser());
			JstTypeSpaceMgr ts = new JstTypeSpaceMgr(controller,
					new DefaultJstTypeLoader());
			ts.initialize();
			ts.loadLibrary(ResourceHelper.getInstance().getJsNativeSerializedStream(),
					JstTypeSpaceMgr.JS_NATIVE_GRP);
			IJSSourceModule module = (IJSSourceModule) getSourceModule(
					TestConstants.PROJECT_NAME_VJETPROJECT, "src", new Path(js));

			String path = module.getResource().getLocation().toFile()
					.getAbsolutePath();
			int end = path.indexOf("\\RunTimeExceptionTest.js");
			String groupFullPath = path.substring(0, end);
			int lastSlashIdx = groupFullPath.lastIndexOf("\\");
			String groupPath = groupFullPath.substring(0, lastSlashIdx + 1);
			String srcPath = groupFullPath.substring(lastSlashIdx + 1);
			ts.processEvent(
					new AddGroupEvent("test", groupPath, srcPath, null),
					new ISourceEventCallback<IJstType>() {
							public void onComplete(
								EventListenerStatus<IJstType> status) {
								synchronized (fullyLoaded) {
									fullyLoaded.notify();
								}
							}
							public void onProgress(float percent) {
//								System.out.println("Percentage of completion " + percent);
							}
						}
					);

			synchronized (fullyLoaded) {
				fullyLoaded.wait();
			}
			TypeName typeName = new TypeName("test",
					"partials.RunTimeExceptionTest");
			IJstType type = ts.getQueryExecutor().findType(typeName);
			assertNotNull(type);
			assertEquals("partials.RunTimeExceptionTest", type.getName());
		} finally {
			FixtureUtils.tearDownFixture(m_fixtureManager);
		}
	}

	public void testAsyncLoadJsWithCodeComplete() throws Exception {
		String js = "partials/ThisReferenceProps.js";
		FixtureManager m_fixtureManager = FixtureUtils.setUpFixture(this, js);
		try {
			String[] containNames = new String[] { "s_init", "helloWorld",
					"helloWorld1", "this.vj$" };

			fullyLoaded = false;
			IJstParseController controller = new JstParseController(
					new VjoParser());
			JstTypeSpaceMgr ts = new JstTypeSpaceMgr(controller,
					new DefaultJstTypeLoader());
			ts.initialize();
			ts.loadLibrary(ResourceHelper.getInstance().getJsNativeSerializedStream(),
					JstTypeSpaceMgr.JS_NATIVE_GRP);
			IJSSourceModule module = (IJSSourceModule) getSourceModule(
					TestConstants.PROJECT_NAME_VJETPROJECT, "src", new Path(js));
			int position = lastPositionInFile("this.", module);
			String path = module.getResource().getLocation().toFile()
					.getAbsolutePath();
			int end = path.indexOf("partials\\ThisReferenceProps.js");
			String groupFullPath = path.substring(0, end);
			int lastSlashIdx = groupFullPath.lastIndexOf("\\");
			String groupPath = groupFullPath.substring(0, lastSlashIdx + 1);
			String srcPath = groupFullPath.substring(lastSlashIdx + 1);
			ts.processEvent(
					new AddGroupEvent("test", groupPath, srcPath, null),
					new ISourceEventCallback<IJstType>() {
						public void onComplete(
								EventListenerStatus<IJstType> status) {
							fullyLoaded = true;
						}
						
						public void onProgress(float percent) {
//							System.out.println("Percentage of completion " + percent);
						}
					});
			containsNames(module, position, containNames,
					ICategoryRequestor.TYPE_CATEGORY);
		} finally {
			FixtureUtils.tearDownFixture(m_fixtureManager);
		}
	}

	private String[] getSuggestions(Class clazz) {
		Method[] methods = clazz.getDeclaredMethods();

		List<String> list = new ArrayList<String>();
		int counter = 0;
		for (Method m : methods) {
			// remove get from method name if it is property
			if (m.isAnnotationPresent(Property.class)) {
				String removeMe = "get";
				String propName = m.getName().substring(removeMe.length());
				if (!m.isAnnotationPresent(Static.class)
						&& !propName.equals("URL")
						&& !propName.equals("URLUnencoded")
						&& !propName.equals("Infinity")
						&& !propName.equals("NaN")) {
					propName = propName.substring(0, 1).toLowerCase()
							+ propName.substring(1);
				}
				if (!list.contains(propName))
					list.add(propName);
			} else if (m.isAnnotationPresent(Function.class)
					|| m.isAnnotationPresent(Constructor.class)) {
				if (m.getName().equalsIgnoreCase("_void")) {
					list.add("void");
				} else {
					list.add(m.getName());
				}
			}
			counter++;
		}
		String[] names = new String[list.size()];
		return list.toArray(names);
	}

	public String doTypeCodeCompleate(IJSSourceModule module, int position,
			String completionName) throws ModelException {
		assertNotSame("Invalid file content, cant find position", -1, position);

		VjoTypeCompletionProposalComputer computer = new VjoTypeCompletionProposalComputer();
		ScriptEditor vEditor = (ScriptEditor) getEditor(module);
		ISourceViewer viewer = vEditor.getViewer();

		ScriptContentAssistInvocationContext context = new ScriptContentAssistInvocationContext(
				viewer, position, vEditor, VjoNature.NATURE_ID) {
			protected CompletionProposalLabelProvider createLabelProvider() {
				return null;
			}
		};

		List<ScriptCompletionProposal> completions = computer
				.computeCompletionProposals(context, null);
		List<String> results = new ArrayList<String>();
		for (AbstractScriptCompletionProposal vjoCompletionProposal : completions) {
			results.add(vjoCompletionProposal.getReplacementString());
		}
		assertTrue("Results not contains " + completionName
				+ " proposal in list " + results, results
				.contains(completionName));

		AbstractScriptCompletionProposal proposal = null;
		for (AbstractScriptCompletionProposal vjoCompletionProposal : completions) {
			if (completionName.equals(vjoCompletionProposal
					.getReplacementString())) {
				proposal = vjoCompletionProposal;
				break;
			}
		}

		if (proposal != null) {
			proposal.apply(viewer.getDocument());
		}

		vEditor.doSave(null);
		return viewer.getDocument().get();
	}

	public void testErrorLogFile() throws Exception {
		String canonicalPath = Platform.getLogFileLocation().toFile()
				.getCanonicalPath();
		String VJETLogFileName = canonicalPath.substring(0, canonicalPath
				.lastIndexOf("."))
				+ "_VJETTests.log";

		File file = new File(VJETLogFileName);
		BufferedReader inputStream = new BufferedReader(new FileReader(file
				.getAbsolutePath()));
		String inLine = null;
		StringBuffer sb = new StringBuffer();
		while ((inLine