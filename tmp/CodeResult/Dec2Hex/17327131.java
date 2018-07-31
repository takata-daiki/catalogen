/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.javatojs.translate.custom.jdk;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.lang.reflect.Array;
import java.util.AbstractCollection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.ConcurrentModificationException;
import java.util.Date;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.PriorityQueue;
import java.util.Properties;
import java.util.Queue;
import java.util.Random;
import java.util.Set;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.Stack;
import java.util.StringTokenizer;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.Vector;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ClassInstanceCreation;

import org.ebayopensource.dsf.javatojs.translate.TranslateHelper;
import org.ebayopensource.dsf.javatojs.translate.config.MethodKey;
import org.ebayopensource.dsf.javatojs.translate.custom.CustomAttr;
import org.ebayopensource.dsf.javatojs.translate.custom.meta.BaseCustomMetaProvider;
import org.ebayopensource.dsf.javatojs.translate.custom.meta.CustomField;
import org.ebayopensource.dsf.javatojs.translate.custom.meta.CustomMethod;
import org.ebayopensource.dsf.javatojs.translate.custom.meta.CustomType;
import org.ebayopensource.dsf.javatojs.translate.custom.meta.ICustomMetaProvider;
import org.ebayopensource.dsf.javatojs.translate.custom.meta.PrivilegedProcessorAdapter;
import org.ebayopensource.dsf.jst.BaseJstNode;
import org.ebayopensource.dsf.jst.IJstType;
import org.ebayopensource.dsf.jst.declaration.JstArray;
import org.ebayopensource.dsf.jst.expr.InfixExpr;
import org.ebayopensource.dsf.jst.expr.MtdInvocationExpr;
import org.ebayopensource.dsf.jst.expr.ObjCreationExpr;
import org.ebayopensource.dsf.jst.expr.TextExpr;
import org.ebayopensource.dsf.jst.term.JstIdentifier;
import org.ebayopensource.dsf.jst.token.IExpr;
import org.ebayopensource.vjo.meta.VjoConvention;

public class JavaLangMeta extends BaseCustomMetaProvider implements ICustomMetaProvider {
	// 
	// Singleton
	//
	private static JavaLangMeta s_instance = new JavaLangMeta();

	private static boolean m_initialized = false;	
	
	private static final String VJOX = VjoConvention.getVjoExtScope();
	
	public static final String VJO_OBJECT = "vjo.Object";
	public static final String VJO_CLASS = "vjo.Class";
	public static final String VJO_ENUM = "vjo.Enum";
	private static String NUMBER_PKG = VJOX + ".java.lang.Number";
	private static String INTEGER_PKG = VJOX + ".java.lang.Integer";
	//private static String BOOLEAN_PKG = VJOX + ".java.lang.Boolean";
	private static String SHORT_PKG = VJOX + ".java.lang.Short";
	private static String LONG_PKG = VJOX + ".java.lang.Long";
	private static String BYTE_PKG = VJOX + ".java.lang.Byte";
	private static String FLOAT_PKG = VJOX + ".java.lang.Float";
	private static String DOUBLE_PKG = VJOX + ".java.lang.Double";
	private static String CHARACTER_PKG = VJOX + ".java.lang.Character";
	private static String COMPARABLE_PKG = VJOX + ".java.lang.Comparable";
	
	private static final String VJO_JAVA_LANG_MATH = VJOX + ".java.lang.Math";
	private static final String ARRAYLIST = VJOX + ".java.util.ArrayList";
	private static String VJO_PKG = VJOX + ".";
	
	private static String COLLECTIONS_TYPE_NAME =  VJOX + ".java.util.Collections";
	private static String STRING_UTIL_TYPE_NAME = VJOX + ".java.lang.StringUtil";
	private static String OBJECT_UTIL_TYPE_NAME = VJOX + ".java.lang.ObjectUtil";
	private static String CLASS_UTIL_TYPE_NAME = VJOX + ".java.lang.ClassUtil";
	private static String NUMBER_UTIL_TYPE_NAME = VJOX + ".java.lang.NumberUtil";
	private static String DATE_UTIL_TYPE_NAME = VJOX + ".java.lang.DateUtil";
	private static String BOOLEAN_UTIL_TYPE_NAME = VJOX + ".java.lang.BooleanUtil";

	private JavaLangMeta() {
	}

	public static JavaLangMeta getInstance() {
		if (!m_initialized) {
			synchronized (s_instance) {
				if (!m_initialized) {
					s_instance.init();
					m_initialized = true;
				}
			}
		}
		return s_instance;
	}
	
	//
	// Private
	//
	private void init() {
		loadStringType();
		loadBooleanType();
		loadNumberType();
		loadIntegerType();
		loadFloatType();
		loadDoubleType();
		loadLongType();
		loadShortType();
		loadByteType();
		loadCharacterType();
		loadCharSequenceType();
		loadClassType();
		loadMathType();
		loadObjectType();
		loadEnumType();
		loadEnumSet();
		loadEnumMap();
		loadStringBufferType();
		loadStringBuilderType();
		loadSystem();
		loadComparableType();

		//Exceptions
		loadNullPointerException();
		loadIllegalArgumentException();
		loadThrowableType();
		loadExceptionType();
		loadRuntimeExceptionType();
		loadCloneNotSupportedExceptionType();
		loadArrayType();
		loadError();
		loadIllegalAccessException();
		loadNoSuchFieldException();
		loadOutOfMemoryError();
		loadUnsupportedOperationException();
		loadNumberFormatException();
		loadArithmeticException();
		loadArrayIndexOutOfBoundsException();
		loadIndexOutOfBoundsException();
		loadStringIndexOutOfBoundsException();
		loadInstantiationException();
		loadConcurrentModificationException();
		loadIllegalStateException();
		loadNoSuchElementException();
		loadArrayStoreException();
		loadClassCastException();
		
			
		//util
		loadList();
		loadVector();
		loadArrayList();
		loadLinkedList();
		loadSet();
		loadHashSet();
		loadLinkedHashSet();
		loadCollection();
		loadEnumeration();
		loadIterator();
		loadListIterator();
		loadCollections();
		loadArrays();
		loadStack();
		loadStringTokenizer();
		loadAbstractCollection();
		loadMapEntry();
		loadTreeSet();
		loadTreeMap();
		loadSortedSet();
		loadSortedMap();
		loadComparator();
		loadRandom();
		loadDate();
		loadMap();
		loadProperties();
		loadHashMap();
		loadHashtable();
		loadLinkedHashMap();
		loadIdentityHashMap();
		loadQueue();
		loadPriorityQueue();
		
		
		
				
		//io
		loadPrintStream();
		loadPrintWriter();
		loadInputStream();
		loadFileInputStream();
		loadOutputStream();
		loadFileOutputStream();
		
	}


	private void loadStringType() {
		Class type = String.class;
		CustomType cType = new CustomType(type)
				.setAttr(CustomAttr.MAPPED_TO_JS)
				// use native
				.addCustomMethod("charAt", "charAt")
				.addCustomMethod("codePointAt", "charCodeAt")
				.addCustomMethod("concat", "concat")
				.addCustomMethod("indexOf", "indexOf")
				.addCustomMethod("lastIndexOf", "lastIndexOf")
				.addCustomMethod("length", "length", true)
				.addCustomMethod("replaceFirst", "replace")
				.addCustomMethod("split", "split")
				.addCustomMethod("substring", "substring")

				.addCustomMethod("String","build",VJOX + ".java.lang.StringFactory")

				// use StringUtil
				.addCustomMethod("compareTo", "compareTo", STRING_UTIL_TYPE_NAME)
				.addCustomMethod("compareToIgnoreCase", "compareToIgnoreCase", STRING_UTIL_TYPE_NAME)
				.addCustomMethod("endsWith", "endsWith",STRING_UTIL_TYPE_NAME)
//				.addCustomMethod("equals", "equals", STRING_UTIL_TYPE_NAME)
				.addCustomMethod("equalsIgnoreCase", "equalsIgnoreCase", STRING_UTIL_TYPE_NAME)
				.addCustomMethod("getBytes","getBytes", STRING_UTIL_TYPE_NAME)
				.addCustomMethod("getChars", "getChars", STRING_UTIL_TYPE_NAME)
				.addCustomMethod("intern","intern", STRING_UTIL_TYPE_NAME)
				.addCustomMethod("regionMatches", "regionMatches", STRING_UTIL_TYPE_NAME)
				.addCustomMethod("startsWith", "startsWith", STRING_UTIL_TYPE_NAME)
				.addCustomMethod("toCharArray", "toCharArray", STRING_UTIL_TYPE_NAME)
				.addCustomMethod("toLowerCase", "toLowerCase", STRING_UTIL_TYPE_NAME)
				.addCustomMethod("toUpperCase", "toUpperCase", STRING_UTIL_TYPE_NAME)
				.addCustomMethod("trim", "trim", STRING_UTIL_TYPE_NAME)
				.addCustomMethod("contentEquals", "contentEquals", STRING_UTIL_TYPE_NAME)
				.addCustomMethod("matches", "matches", STRING_UTIL_TYPE_NAME)
				.addCustomMethod("replace", "replace", STRING_UTIL_TYPE_NAME)
				.addCustomMethod("replaceAll", "replaceAll",STRING_UTIL_TYPE_NAME)
				.addCustomMethod("subSequence", "subSequence", STRING_UTIL_TYPE_NAME)
				.addCustomMethod("codePointBefore", "codePointBefore", STRING_UTIL_TYPE_NAME)
				.addCustomMethod("codePointCount", "codePointCount", STRING_UTIL_TYPE_NAME)
				.addCustomMethod("offsetByCodePoints", "offsetByCodePoints", STRING_UTIL_TYPE_NAME)
				.addCustomMethod("contains", "contains",STRING_UTIL_TYPE_NAME)

				//String static metthods
				//				.addCustomMethod("copyValueOf", "copyValueOf",false,false,STRING_UTIL_TYPE_NAME)
				.addCustomMethod("valueOf", "_valueOf", STRING_UTIL_TYPE_NAME)
				//				.addCustomMethod("format", "format", false,false,STRING_UTIL_TYPE_NAME)

				// isJavaOnly
				.addCustomMethod(new CustomMethod("toString").setAttr(CustomAttr.JAVA_ONLY))

				// not supported
				.addCustomMethod(new CustomMethod("getBytes").setAttr(CustomAttr.EXCLUDED))
				//.addCustomMethod(new CustomMethod("hashCode").setAttr(CustomAttr.EXCLUDED))
				;

		addCustomType(type.getSimpleName(), cType); // TODO remove after fix JstType for String
		addCustomType(type.getName(), cType);

		addObjectUtilMethods(type.getName());
		addObjectUtilMethods(type.getSimpleName()); // TODO remove after fix JstType for String
	}

	//issue getClass() and TYPE
	private void loadBooleanType() {

		Class type = Boolean.class;
		//Class type = org.ebayopensource.dsf.jsnative.global.Boolean.class;
		CustomType cType = new CustomType(type)
				.setAttr(CustomAttr.MAPPED_TO_JS)
				
				// use BooleanUtil
				.addCustomMethod("booleanValue", "booleanValue", BOOLEAN_UTIL_TYPE_NAME)
				.addCustomMethod("getBoolean", "getBoolean", BOOLEAN_UTIL_TYPE_NAME)
				.addCustomMethod("parseBoolean", "parseBoolean", BOOLEAN_UTIL_TYPE_NAME)
				.addCustomMethod("toBoolean", "toBoolean", BOOLEAN_UTIL_TYPE_NAME)
				.addCustomMethod("toString", "toString", BOOLEAN_UTIL_TYPE_NAME)
				.addCustomMethod("valueOf", "valueOf_", BOOLEAN_UTIL_TYPE_NAME)
				.addCustomMethod("compareTo", "compareTo", OBJECT_UTIL_TYPE_NAME)
				.addCustomMethod("equals", "equals", OBJECT_UTIL_TYPE_NAME)
				.addCustomMethod("hashCode", "hashCode", OBJECT_UTIL_TYPE_NAME)

				//use BooleanUtil for constructors
				.addCustomMethod("Boolean", "valueOf_", BOOLEAN_UTIL_TYPE_NAME)

				//excluded method
				.addCustomMethod(new CustomMethod("getClass").setAttr(CustomAttr.EXCLUDED))
				
				.addCustomField(
						new CustomField("TRUE")
							.setAttr(CustomAttr.MAPPED_TO_VJO)
							.setJstName("TRUE")
							.setJstTypeName(type.getName())
							.setJstOwnerTypeName(BOOLEAN_UTIL_TYPE_NAME)
						)
				.addCustomField(
						new CustomField("FALSE")
							.setAttr(CustomAttr.MAPPED_TO_VJO)
							.setJstName("FALSE")
							.setJstTypeName(type.getName())
							.setJstOwnerTypeName(BOOLEAN_UTIL_TYPE_NAME)
						)
				.addCustomField(
						new CustomField("TYPE")
							.setAttr(CustomAttr.MAPPED_TO_VJO)
							.setJstName("TRUE")
							.setJstTypeName(type.getName())
							.setJstOwnerTypeName(BOOLEAN_UTIL_TYPE_NAME)
						);
		
		addCustomType(type.getSimpleName(), cType);
		addCustomType(type.getName(), cType);
		addCustomType(VJO_PKG+type.getName(), cType);

//		CustomType cType = new CustomType(type, BOOLEAN_PKG)
//			.lookupMtdBySigniture(true)
//			// fields
//			.addCustomField(new CustomField(type, "TYPE").setAttr(CustomAttr.EXCLUDED))
//			// not supported
//		
//		addCustomType(type.getName(), cType);
//		addCustomType(VJO_PKG+type.getName(), cType);
		addObjectUtilMethods(type.getName());
	}
	
	private void loadNumberType(){
		Class type = Number.class;
		CustomType cType = new CustomType(type, NUMBER_PKG)
			// not supported
			.addCustomMethod(new CustomMethod("getInteger").setAttr(CustomAttr.EXCLUDED))
			;

		addCustomType(type.getName(), cType);
		addCustomType(NUMBER_PKG, cType);
		addObjectUtilMethods(type.getName());
	}
	
	//	issue getClass() and TYPE
	private void loadIntegerType(){
		Class type = Integer.class;
		
		String[] params1 = {"String nm"};
		MethodKey key1 = new MethodKey("getInteger", true, params1);

		String[] params2 = {"String nm", "int val"};
		MethodKey key2 = new MethodKey("getInteger", true, params2);
		
		String[] params3 = {"String nm", "Integer val"};
		MethodKey key3 = new MethodKey("getInteger", true, params3);
		
		CustomType cType = new CustomType(type, INTEGER_PKG)
			/*
			 * Unsupported Fields
			 */
			.addCustomField(new CustomField("TYPE")
				.setAttr(CustomAttr.EXCLUDED))
			
			/*
			 * Supported Mapped Methods
			 */
		    .addCustomMethod("myParseInt", "parseInteger", NUMBER_UTIL_TYPE_NAME)

			/*
			 * Unsupported Methods
			 */
			.addCustomMethod(new CustomMethod(key1).setAttr(CustomAttr.EXCLUDED))
			.addCustomMethod(new CustomMethod(key2).setAttr(CustomAttr.EXCLUDED))
			.addCustomMethod(new CustomMethod(key3).setAttr(CustomAttr.EXCLUDED))
			;

		addCustomType(type.getName(), cType);
		addCustomType(INTEGER_PKG, cType);
		addObjectUtilMethods(type.getName());
	}
	private void loadDoubleType(){
		Class type = Double.class;

		CustomType cType = new CustomType(type,DOUBLE_PKG)
		
		/*
		 * Unsupported Fields
		 */
		.addCustomField(new CustomField("TYPE")
			.setAttr(CustomAttr.EXCLUDED))

		/*
		 * Supported Mapped Methods
		 */
		.addCustomMethod(new CustomMethod("myParseDouble", "Number")
			.setJstReturnTypeName("double")
			.setAttr(CustomAttr.MAPPED_TO_JS))

		.addCustomMethod(new CustomMethod("parseFD", "Number")
			.setJstReturnTypeName("double")
			.setAttr(CustomAttr.MAPPED_TO_JS))
			
		.addCustomMethod(new CustomMethod("myIsFinite", "isFinite")
			.setJstReturnTypeName("boolean")
			.setAttr(CustomAttr.MAPPED_TO_JS))

		.addCustomMethod(new CustomMethod("myIsNaN", "isNaN")
			.setJstReturnTypeName("boolean")
			.setAttr(CustomAttr.MAPPED_TO_JS))

		.addCustomMethod(new CustomMethod("myToHexString", "vjo.java.lang.MathUtil.dec2Hex")
			.setJstReturnTypeName("String")
			.setAttr(CustomAttr.MAPPED_TO_VJO))	
			
		/*
		 * Unsupported Methods
		 */
		.addCustomMethod(new CustomMethod("doubleToLongBits").setAttr(CustomAttr.EXCLUDED))
		.addCustomMethod(new CustomMethod("doubleToRawLongBits").setAttr(CustomAttr.EXCLUDED))
		.addCustomMethod(new CustomMethod("longBitsToDouble").setAttr(CustomAttr.EXCLUDED))
		.addCustomMethod(new CustomMethod("rawCopySign").setAttr(CustomAttr.EXCLUDED))
		.addCustomMethod(new CustomMethod("getExponent").setAttr(CustomAttr.EXCLUDED))
		;

		/*
		 * Add types
		 */
		addCustomType(type.getName(), cType);
		addCustomType(VJO_PKG+type.getName(), cType);
		addObjectUtilMethods(type.getName());
		
	}
	//getClass() getType() issue
	private void loadLongType(){
		Class type = Long.class;
		CustomType cType = new CustomType(type,LONG_PKG)
		
		/*
		 * Unsupported Fields
		 */
		.addCustomField(new CustomField("TYPE")
			.setAttr(CustomAttr.EXCLUDED))

		/*
		 * Supported Mapped Methods
		 */
		.addCustomMethod("myParseLong", "parseLong", NUMBER_UTIL_TYPE_NAME)

//		.addCustomMethod(new CustomMethod("myToHexString", "vjo.java.lang.LongUtil.toHexString", false)
//			.setJstReturnType("String")
//			.setAttr(CustomAttr.MAPPED_TO_VJO))
//			
//		.addCustomMethod(new CustomMethod("myToOctalString", "vjo.java.lang.LongUtil.toOctalString", false)
//			.setJstReturnType("String")
//			.setAttr(CustomAttr.MAPPED_TO_VJO))
//			
//		.addCustomMethod(new CustomMethod("myToBinaryString", "vjo.java.lang.LongUtil.toBinaryString", false)
//			.setJstReturnType("String")
//			.setAttr(CustomAttr.MAPPED_TO_VJO))
			
		/*
		 * Unsupported Methods
		 */

		;
		
		/*
		 * Add types
		 */
		addCustomType(type.getName(), cType);
		addCustomType(VJO_PKG+type.getName(), cType);
		addObjectUtilMethods(type.getName());
	}
	
	//getClass and getType issue
	private void loadShortType(){
		Class type = Short.class;
		CustomType cType = new CustomType(type,SHORT_PKG)
        
		/*
		 * Unsupported Fields
		 */
		.addCustomField(new CustomField("TYPE")
			.setAttr(CustomAttr.EXCLUDED))
			
			
		/*
		 * Supported Mapped Methods
		 */
		 .addCustomMethod("myParseShort", "parseShort", NUMBER_UTIL_TYPE_NAME)

		/*
		 * Unsupported Methods
		 */
		;
		
		/*
		 * Add types
		 */
		addCustomType(type.getName(), cType);
		addCustomType(VJO_PKG+type.getName(), cType);
		addObjectUtilMethods(type.getName());
	}	
	
	private void loadByteType(){
		Class type = Byte.class;
		CustomType cType = new CustomType(type,BYTE_PKG)

		/*
		 * Unsupported Fields
		 */
		.addCustomField(new CustomField("TYPE")
			.setAttr(CustomAttr.EXCLUDED))
			
		/*
		 * Supported Mapped Methods
		 */
		 .addCustomMethod("myParseByte", "parseByte", NUMBER_UTIL_TYPE_NAME)

		/*
		 * Unsupported Methods
		 */
		
		;
				
		/*
		 * Add types
		 */
		addCustomType(type.getName(), cType);
		addCustomType(VJO_PKG+type.getName(), cType);
		addObjectUtilMethods(type.getName());
	}

	private void loadCharacterType(){
		Class type = Character.class;
		CustomType cType = new CustomType(type,CHARACTER_PKG)

		/*
		 * Unsupported Fields
		 */
		.addCustomField(new CustomField("TYPE")
			.setAttr(CustomAttr.EXCLUDED))
		
		/*
		 * Unsupported Methods
		 */
		
		;
		
		/*
		 * Add types
		 */
		addCustomType(type.getName(), cType);
		addCustomType(VJO_PKG+type.getName(), cType);
		
		
//		.addCustomMethod("charValue", "charValue", STRING_UTIL_TYPE_NAME)
//		.addCustomMethod("equals", "equals", STRING_UTIL_TYPE_NAME)
		//static method
//		.addCustomMethod("valueOf", "valueOf", STRING_UTIL_TYPE_NAME)
//		.addCustomMethod("isValidCodePoint", "isValidCodePoint",STRING_UTIL_TYPE_NAME)
//		.addCustomMethod("isSupplementaryCodePoint", "isSupplementaryCodePoint", STRING_UTIL_TYPE_NAME)
//		.addCustomMethod("isHighSurrogate", "isHighSurrogate", STRING_UTIL_TYPE_NAME)
//		.addCustomMethod("isLowSurrogate", "isLowSurrogate",STRING_UTIL_TYPE_NAME)
//		.addCustomMethod("isSurrogatePair", "isSurrogatePair", STRING_UTIL_TYPE_NAME)
//		.addCustomMethod("charCount", "charCount", STRING_UTIL_TYPE_NAME)
//		.addCustomMethod("toCodePoint", "toCodePoint", STRING_UTIL_TYPE_NAME)
//		.addCustomMethod("codePointAt", "codePointAt", STRING_UTIL_TYPE_NAME)
//		.addCustomMethod("codePointAt", "codePointAt", STRING_UTIL_TYPE_NAME)
//		.addCustomMethod("codePointBefore", "codePointBefore", STRING_UTIL_TYPE_NAME)
//		.addCustomMethod("toChars", "toChars",STRING_UTIL_TYPE_NAME)
//		.addCustomMethod("codePointCount", "codePointCount", STRING_UTIL_TYPE_NAME)
//		.addCustomMethod("offsetByCodePoints", "offsetByCodePoints", STRING_UTIL_TYPE_NAME)
//		.addCustomMethod("isLowerCase", "isLowerCase", STRING_UTIL_TYPE_NAME)
//		.addCustomMethod("isUpperCase", "isUpperCase",STRING_UTIL_TYPE_NAME)
//		.addCustomMethod("isTitleCase", "isTitleCase", STRING_UTIL_TYPE_NAME)
//		.addCustomMethod("isDigit", "isDigit", STRING_UTIL_TYPE_NAME)
//		.addCustomMethod("isDefined", "isDefined", STRING_UTIL_TYPE_NAME)
//		.addCustomMethod("isLetter", "isLetter", STRING_UTIL_TYPE_NAME)
//		.addCustomMethod("isLetterOrDigit", "isLetterOrDigit", STRING_UTIL_TYPE_NAME)
//		.addCustomMethod("isJavaIdentifierStart", "isJavaIdentifierStart", STRING_UTIL_TYPE_NAME)		
//		.addCustomMethod("isJavaIdentifierPart", "isJavaIdentifierPart",STRING_UTIL_TYPE_NAME)
//		.addCustomMethod("isUnicodeIdentifierStart", "isUnicodeIdentifierStart", STRING_UTIL_TYPE_NAME)
//		.addCustomMethod("isUnicodeIdentifierPart", "isUnicodeIdentifierPart", STRING_UTIL_TYPE_NAME)
//		.addCustomMethod("isHighSurrogate", "isHighSurrogate", STRING_UTIL_TYPE_NAME)
//		.addCustomMethod("isLowSurrogate", "isLowSurrogate",STRING_UTIL_TYPE_NAME)
//		.addCustomMethod("isSurrogatePair", "isSurrogatePair", STRING_UTIL_TYPE_NAME)
//		.addCustomMethod("charCount", "charCount", STRING_UTIL_TYPE_NAME)
//		.addCustomMethod("toCodePoint", "toCodePoint", STRING_UTIL_TYPE_NAME)
//		.addCustomMethod("codePointAt", "codePointAt", STRING_UTIL_TYPE_NAME)
//		.addCustomMethod("codePointAt", "codePointAt", STRING_UTIL_TYPE_NAME)
//		.addCustomMethod("codePointBefore", "codePointBefore", STRING_UTIL_TYPE_NAME)
//		.addCustomMethod("toChars", "toChars",STRING_UTIL_TYPE_NAME)
//		.addCustomMethod("codePointCount", "codePointCount", STRING_UTIL_TYPE_NAME)
//		.addCustomMethod("offsetByCodePoints", "offsetByCodePoints", STRING_UTIL_TYPE_NAME)
//		.addCustomMethod("isLowerCase", "isLowerCase", STRING_UTIL_TYPE_NAME)
//		.addCustomMethod("isUpperCase", "isUpperCase",STRING_UTIL_TYPE_NAME)
//		.addCustomMethod("isTitleCase", "isTitleCase", STRING_UTIL_TYPE_NAME)
//		.addCustomMethod("isDigit", "isDigit", STRING_UTIL_TYPE_NAME)
//		.addCustomMethod("isDefined", "isDefined", STRING_UTIL_TYPE_NAME)
//		.addCustomMethod("isLetter", "isLetter", STRING_UTIL_TYPE_NAME)
//		.addCustomMethod("isLetterOrDigit", "isLetterOrDigit", STRING_UTIL_TYPE_NAME)
//		.addCustomMethod("isJavaIdentifierStart", "isJavaIdentifierStart", STRING_UTIL_TYPE_NAME)
//      .addCustomMethod(new CustomMethod("hashCode").setIsSupported(false));	
		addObjectUtilMethods(type.getName());
	}	
	
	private void loadFloatType(){
		Class<?> type = Float.class;
		
		String[] params1 = {"double value"};
		MethodKey key1 = new MethodKey("Float", false, params1);
		
		CustomType cType = new CustomType(type,FLOAT_PKG)
		
		/*
		 * Unsupported Fields
		 */
		.addCustomField(new CustomField("TYPE")
			.setAttr(CustomAttr.EXCLUDED))

		/*
		 * Supported Mapped Methods
		 */
		.addCustomMethod(new CustomMethod("myParseFloat", "Number")
			.setJstReturnTypeName("float")
			.setAttr(CustomAttr.MAPPED_TO_JS))
			
		.addCustomMethod(new CustomMethod("myToHexString", "vjo.java.lang.MathUtil.dec2Hex")
			.setJstReturnTypeName("String")
			.setAttr(CustomAttr.MAPPED_TO_VJO))	

		.addCustomMethod(new CustomMethod("myIsNaN", "isNaN")
			.setJstReturnTypeName("boolean")
			.setAttr(CustomAttr.MAPPED_TO_JS))	
		
		/*
		 * Unsupported Methods
		 */
		// Unsupported ambiguous constructor in JavaScript. Using float instead of double
		.addCustomMethod(new CustomMethod(key1).setAttr(CustomAttr.EXCLUDED))
		.addCustomMethod(new CustomMethod("floatToIntBits").setAttr(CustomAttr.EXCLUDED))
		.addCustomMethod(new CustomMethod("floatToRawIntBits").setAttr(CustomAttr.EXCLUDED))
		.addCustomMethod(new CustomMethod("intBitsToFloat").setAttr(CustomAttr.EXCLUDED))
		;
			
		/*
		 * Add types
		 */
		addCustomType(type.getName(), cType);
		addCustomType(VJO_PKG+type.getName(), cType);
		addObjectUtilMethods(type.getName());
	}
	
	private void loadCharSequenceType() {
		Class type = CharSequence.class;
		CustomType cType = new CustomType(type, VJOX + ".java.lang.CharSequence")
			.setAttr(CustomAttr.MAPPED_TO_VJO);

		/*
		 * Add types
		 */
		addCustomType(type.getName(), cType);
		addCustomType(VJO_PKG+type.getName(), cType);
		addObjectUtilMethods(type.getName());
	}
	
	private void loadArrayType() {
		Class type = Array.class;
		CustomType cType = new CustomType(type, VJOX + ".java.lang.reflect.Array");

		/*
		 * Add types
		 */
		addCustomType(type.getName(), cType);
		addCustomType(VJO_PKG+type.getName(), cType);
	}
	
	//TODO complete class type
	private void loadClassType() {
		Class type = Class.class;
		CustomType cType = new CustomType(type, VJO_CLASS)
		.setAttr(CustomAttr.MAPPED_TO_VJO)
	
		/*
		 * Supported Mapped Methods
		 */
		.addCustomMethod("getEnumConstants", "getEnumConstants", CLASS_UTIL_TYPE_NAME)
		.addCustomMethod("getSuperclass", "getSuperclass", CLASS_UTIL_TYPE_NAME)

		/*
		 * Unsupported Methods
		 */
		.addCustomMethod(new CustomMethod("asSubclass").setAttr(CustomAttr.EXCLUDED))
		.addCustomMethod(new CustomMethod("cast").setAttr(CustomAttr.EXCLUDED))
		.addCustomMethod(new CustomMethod("desiredAssertionStatus").setAttr(CustomAttr.EXCLUDED))
		.addCustomMethod(new CustomMethod("forName").setAttr(CustomAttr.EXCLUDED))
		.addCustomMethod(new CustomMethod("getAnnotation").setAttr(CustomAttr.EXCLUDED))
		.addCustomMethod(new CustomMethod("getAnnotations").setAttr(CustomAttr.EXCLUDED))
		.addCustomMethod(new CustomMethod("getCanonicalName").setAttr(CustomAttr.EXCLUDED))
		.addCustomMethod(new CustomMethod("getClasses").setAttr(CustomAttr.EXCLUDED))
		.addCustomMethod(new CustomMethod("getClassLoader").setAttr(CustomAttr.EXCLUDED))
		.addCustomMethod(new CustomMethod("getComponentType").setAttr(CustomAttr.EXCLUDED))
		.addCustomMethod(new CustomMethod("getConstructor").setAttr(CustomAttr.EXCLUDED))
		.addCustomMethod(new CustomMethod("getConstructors").setAttr(CustomAttr.EXCLUDED))
		.addCustomMethod(new CustomMethod("getDeclaredAnnotations").setAttr(CustomAttr.EXCLUDED))
		.addCustomMethod(new CustomMethod("getDeclaredClasses").setAttr(CustomAttr.EXCLUDED))
		.addCustomMethod(new CustomMethod("getDeclaredConstructor").setAttr(CustomAttr.EXCLUDED))
		.addCustomMethod(new CustomMethod("getDeclaredConstructors").setAttr(CustomAttr.EXCLUDED))
		.addCustomMethod(new CustomMethod("getDeclaredField").setAttr(CustomAttr.EXCLUDED))
		.addCustomMethod(new CustomMethod("getDeclaredFields").setAttr(CustomAttr.EXCLUDED))
		.addCustomMethod(new CustomMethod("getDeclaredMethod").setAttr(CustomAttr.EXCLUDED))
		.addCustomMethod(new CustomMethod("getDeclaredMethods").setAttr(CustomAttr.EXCLUDED))
		.addCustomMethod(new CustomMethod("getDeclaringClass").setAttr(CustomAttr.EXCLUDED))
		.addCustomMethod(new CustomMethod("getEnclosingClass").setAttr(CustomAttr.EXCLUDED))
		.addCustomMethod(new CustomMethod("getEnclosingConstructor").setAttr(CustomAttr.EXCLUDED))
		.addCustomMethod(new CustomMethod("getEnclosingMethod").setAttr(CustomAttr.EXCLUDED))
		.addCustomMethod(new CustomMethod("getField").setAttr(CustomAttr.EXCLUDED))
		.addCustomMethod(new CustomMethod("getFields").setAttr(CustomAttr.EXCLUDED))
		.addCustomMethod(new CustomMethod("getGenericInterfaces").setAttr(CustomAttr.EXCLUDED))
		.addCustomMethod(new CustomMethod("getGenericSuperclass").setAttr(CustomAttr.EXCLUDED))
		.addCustomMethod(new CustomMethod("getInterfaces").setAttr(CustomAttr.EXCLUDED))
		.addCustomMethod(new CustomMethod("getMethod").setAttr(CustomAttr.EXCLUDED))
		.addCustomMethod(new CustomMethod("getMethods").setAttr(CustomAttr.EXCLUDED))
		.addCustomMethod(new CustomMethod("getModifiers").setAttr(CustomAttr.EXCLUDED))
		.addCustomMethod(new CustomMethod("getPackage").setAttr(CustomAttr.EXCLUDED))
		.addCustomMethod(new CustomMethod("getProtectionDomain").setAttr(CustomAttr.EXCLUDED))
		.addCustomMethod(new CustomMethod("getResource").setAttr(CustomAttr.EXCLUDED))
		.addCustomMethod(new CustomMethod("getResourceAsStream").setAttr(CustomAttr.EXCLUDED))
		.addCustomMethod(new CustomMethod("getSigners").setAttr(CustomAttr.EXCLUDED))
		.addCustomMethod(new CustomMethod("getTypeParameters").setAttr(CustomAttr.EXCLUDED))
		.addCustomMethod(new CustomMethod("isAnnotation").setAttr(CustomAttr.EXCLUDED))
		.addCustomMethod(new CustomMethod("isAnnotationPresent").setAttr(CustomAttr.EXCLUDED))
		.addCustomMethod(new CustomMethod("isAnonymousClass").setAttr(CustomAttr.EXCLUDED))
		.addCustomMethod(new CustomMethod("isArray").setAttr(CustomAttr.EXCLUDED))
		.addCustomMethod(new CustomMethod("isAssignableFrom").setAttr(CustomAttr.EXCLUDED))
		.addCustomMethod(new CustomMethod("isEnum").setAttr(CustomAttr.EXCLUDED))
		.addCustomMethod(new CustomMethod("isLocalClass").setAttr(CustomAttr.EXCLUDED))
		.addCustomMethod(new CustomMethod("isMemberClass").setAttr(CustomAttr.EXCLUDED))
		.addCustomMethod(new CustomMethod("isPrimitive").setAttr(CustomAttr.EXCLUDED))
		.addCustomMethod(new CustomMethod("isSynthetic").setAttr(CustomAttr.EXCLUDED))
		.addCustomMethod(new CustomMethod("newInstance").setAttr(CustomAttr.EXCLUDED))
		;
		
		/*
		 * Add types
		 */
		addCustomType(VJO_CLASS, cType);
		addCustomType(type.getName(), cType);
	}
	private void loadThrowableType(){
		Class type = Throwable.class;
		CustomType cType = new CustomType(type, VJOX + ".java.lang.Throwable");
		
		/*
		 * Add types
		 */
		addCustomType(type.getName(), cType);
		addCustomType(VJO_PKG+type.getName(), cType);
		addObjectUtilMethods(type.getName());
	}	
	private void loadExceptionType(){
		Class type = Exception.class;
		CustomType cType = new CustomType(type, VJOX + ".java.lang.Exception");
		
		/*
		 * Add types
		 */
		addCustomType(type.getName(), cType);
		addCustomType(VJO_PKG+type.getName(), cType);
		addObjectUtilMethods(type.getName());
	}
	private void loadRuntimeExceptionType(){
		Class type = RuntimeException.class;
		CustomType cType = new CustomType(type, VJOX + ".java.lang.RuntimeException");
		
		/*
		 * Add types
		 */
		addCustomType(type.getName(), cType);
		addCustomType(VJO_PKG+type.getName(), cType);
		addObjectUtilMethods(type.getName());
	}
	private void loadCloneNotSupportedExceptionType(){
		Class type = CloneNotSupportedException.class;
		CustomType cType = new CustomType(type, VJOX + ".java.lang.CloneNotSupportedException");
		
		/*
		 * Add types
		 */
		addCustomType(type.getName(), cType);
		addCustomType(VJO_PKG+type.getName(), cType);
		addObjectUtilMethods(type.getName());
	}
	private void loadMathType(){	
		Class type = Math.class;
		
		// abs keys
//		String[] params1 = {"double d"};
//		MethodKey key1 = new MethodKey("abs", true, params1);
		String[] params2 = {"float f"};
		MethodKey key2 = new MethodKey("abs", true, params2);
		String[] params3 = {"int i"};
		MethodKey key3 = new MethodKey("abs", true, params3);
		String[] params4 = {"long l"};
		MethodKey key4 = new MethodKey("abs", true, params4);
		
		// max keys
//		String[] params5 = {"double d1", "double d2"};
//		MethodKey key5 = new MethodKey("max", true, params5);
		String[] params6 = {"float f1", "float f2"};
		MethodKey key6 = new MethodKey("max", true, params6);
		String[] params7 = {"int i1", "int i2"};
		MethodKey key7 = new MethodKey("max", true, params7);
		String[] params8 = {"long l1", "long l2"};
		MethodKey key8 = new MethodKey("max", true, params8);

		// min keys
//		String[] params9 = {"double d1", "double d2"};
//		MethodKey key9 = new MethodKey("min", true, params9);
		String[] params10 = {"float f1", "float f2"};
		MethodKey key10 = new MethodKey("min", true, params10);
		String[] params11 = {"int i1", "int i2"};
		MethodKey key11 = new MethodKey("min", true, params11);
		String[] params12 = {"long l1", "long l2"};
		MethodKey key12 = new MethodKey("min", true, params12);

		// round keys
//		String[] params13 = {"double f"};
//		MethodKey key13 = new MethodKey("round", true, params13);
		String[] params14 = {"float f"};
		MethodKey key14 = new MethodKey("round", true, params14);

		// signum keys
//		String[] params15 = {"double d"};
//		MethodKey key15 = new MethodKey("signum", true, params15);
		String[] params16 = {"float f"};
		MethodKey key16 = new MethodKey("signum", true, params16);

		CustomType cType = new CustomType(type, VJO_JAVA_LANG_MATH)
		
		/*
		 * Supported Mapped Fields
		 */
		.addCustomField(new CustomField("E_")
			.setAttr(CustomAttr.MAPPED_TO_JS)
			.setJstOwnerTypeName("Math")
			.setJstName("E")
			.setJstTypeName(type.getName()))
		.addCustomField(new CustomField("PI_")
			.setAttr(CustomAttr.MAPPED_TO_JS)
			.setJstOwnerTypeName("Math")
			.setJstName("PI")
			.setJstTypeName(type.getName()))
		
		
		/*
		 * Supported Mapped JS Methods
		 */
		.addCustomMethod(new CustomMethod("myAbs","Math.abs").setAttr(CustomAttr.MAPPED_TO_JS))
		.addCustomMethod(new CustomMethod("myMax","Math.max").setAttr(CustomAttr.MAPPED_TO_JS))
		.addCustomMethod(new CustomMethod("myMin","Math.min").setAttr(CustomAttr.MAPPED_TO_JS))
		.addCustomMethod(new CustomMethod("myRandom","Math.random").setAttr(CustomAttr.MAPPED_TO_JS))
		.addCustomMethod(new CustomMethod("myRound","Math.round").setAttr(CustomAttr.MAPPED_TO_JS))
		.addCustomMethod(new CustomMethod("myPow","Math.pow").setAttr(CustomAttr.MAPPED_TO_JS))
		.addCustomMethod(new CustomMethod("mySqrt","Math.sqrt").setAttr(CustomAttr.MAPPED_TO_JS))
		.addCustomMethod(new CustomMethod("mySin","Math.sin").setAttr(CustomAttr.MAPPED_TO_JS))
		.addCustomMethod(new CustomMethod("myCos","Math.cos").setAttr(CustomAttr.MAPPED_TO_JS))
		.addCustomMethod(new CustomMethod("myTan","Math.tan").setAttr(CustomAttr.MAPPED_TO_JS))
		.addCustomMethod(new CustomMethod("myFloor","Math.floor").setAttr(CustomAttr.MAPPED_TO_JS))
		.addCustomMethod(new CustomMethod("myCeil","Math.ceil").setAttr(CustomAttr.MAPPED_TO_JS))
		.addCustomMethod(new CustomMethod("myExp","Math.exp").setAttr(CustomAttr.MAPPED_TO_JS))
		.addCustomMethod(new CustomMethod("myLog","Math.log").setAttr(CustomAttr.MAPPED_TO_JS))
		.addCustomMethod(new CustomMethod("myAtan","Math.atan").setAttr(CustomAttr.MAPPED_TO_JS))
		.addCustomMethod(new CustomMethod("myAsin","Math.asin").setAttr(CustomAttr.MAPPED_TO_JS))
		.addCustomMethod(new CustomMethod("myAcos","Math.acos").setAttr(CustomAttr.MAPPED_TO_JS))
		.addCustomMethod(new CustomMethod("myAtan2","Math.atan2").setAttr(CustomAttr.MAPPED_TO_JS))

		/*
		 * Supported Mapped VJO Methods
		 */
		.addCustomMethod(new CustomMethod("mySignum","vjo.java.lang.MathUtil.signum").setAttr(CustomAttr.MAPPED_TO_VJO))
		.addCustomMethod(new CustomMethod("myLog10","vjo.java.lang.MathUtil.log10").setAttr(CustomAttr.MAPPED_TO_VJO))
		.addCustomMethod(new CustomMethod("myCbrt","vjo.java.lang.MathUtil.cbrt").setAttr(CustomAttr.MAPPED_TO_VJO))
		.addCustomMethod(new CustomMethod("mySinh","vjo.java.lang.MathUtil.sinh").setAttr(CustomAttr.MAPPED_TO_VJO))
		.addCustomMethod(new CustomMethod("myCosh","vjo.java.lang.MathUtil.cosh").setAttr(CustomAttr.MAPPED_TO_VJO))
		.addCustomMethod(new CustomMethod("myTanh","vjo.java.lang.MathUtil.tanh").setAttr(CustomAttr.MAPPED_TO_VJO))
		.addCustomMethod(new CustomMethod("myHypot","vjo.java.lang.MathUtil.hypot").setAttr(CustomAttr.MAPPED_TO_VJO))
		.addCustomMethod(new CustomMethod("myExpm1","vjo.java.lang.MathUtil.expm1").setAttr(CustomAttr.MAPPED_TO_VJO))
		.addCustomMethod(new CustomMethod("myLog1p","vjo.java.lang.MathUtil.log1p").setAttr(CustomAttr.MAPPED_TO_VJO))
		.addCustomMethod(new CustomMethod("myRint","vjo.java.lang.MathUtil.rint").setAttr(CustomAttr.MAPPED_TO_VJO))

		/*
		 * Unsupported Methods
		 */
		.addCustomMethod(new CustomMethod(key2).setAttr(CustomAttr.EXCLUDED))
		.addCustomMethod(new CustomMethod(key3).setAttr(CustomAttr.EXCLUDED))
		.addCustomMethod(new CustomMethod(key4).setAttr(CustomAttr.EXCLUDED))
	
		.addCustomMethod(new CustomMethod(key6).setAttr(CustomAttr.EXCLUDED))
		.addCustomMethod(new CustomMethod(key7).setAttr(CustomAttr.EXCLUDED))
		.addCustomMethod(new CustomMethod(key8).setAttr(CustomAttr.EXCLUDED))
	
		.addCustomMethod(new CustomMethod(key10).setAttr(CustomAttr.EXCLUDED))
		.addCustomMethod(new CustomMethod(key11).setAttr(CustomAttr.EXCLUDED))
		.addCustomMethod(new CustomMethod(key12).setAttr(CustomAttr.EXCLUDED))

		.addCustomMethod(new CustomMethod(key14).setAttr(CustomAttr.EXCLUDED))
		.addCustomMethod(new CustomMethod(key16).setAttr(CustomAttr.EXCLUDED))
		.addCustomMethod(new CustomMethod("ulp").setAttr(CustomAttr.EXCLUDED))

		;
		
//		.addCustomMethod("cbrt", "cbrt",MATH_UTIL)
//		.addCustomMethod("signum", "signum",MATH_UTIL)
//		.addCustomMethod("cbrt", "cbrt",MATH_UTIL)
//		.addCustomMethod("toRadians", "toRadians",MATH_UTIL)		
//		.addCustomMethod("toDegrees","toDegrees",MATH_UTIL);
		//cType
	//	.addCustomMethod(new CustomMethod("ulp")).setIsSupported(false)
		//.addCustomMethod(new CustomMethod("rint")).setIsSupported(false)
		//.addCustomMethod(new CustomMethod("IEEEremainder")).setIsSupported(false);

		/*
		 * Add types
		 */
		addCustomType(type.getName(), cType);
		addCustomType(VJO_JAVA_LANG_MATH, cType);
	}	
	
	private void loadSystem(){	
		Class type = System.class;
		CustomType cType = new CustomType(type,VJOX + ".java.lang.System")

		/*
		 * Unsupported Fields
		 */
		.addCustomField(new CustomField("TYPE").setAttr(CustomAttr.EXCLUDED))
		;
		
		/*
		 * Add types
		 */
		addCustomType(type.getName(), cType);
		addCustomType(VJO_PKG+type.getName(), cType);
	}	

	private void loadObjectType() {
		Class type = Object.class;
		CustomType cType = new CustomType(type, VJO_OBJECT)
		.setAttr(CustomAttr.MAPPED_TO_VJO)
		
		/*
		 * Supported Mapped Methods
		 */
		.addCustomMethod("equals", "equals", OBJECT_UTIL_TYPE_NAME)
		.addCustomMethod("hashCode", "hashCode", OBJECT_UTIL_TYPE_NAME)

		/*
		 * Unsupported Methods
		 */
		.addCustomMethod(new CustomMethod("wait").setAttr(CustomAttr.EXCLUDED))
		.addCustomMethod(new CustomMethod("notify").setAttr(CustomAttr.EXCLUDED))
		.addCustomMethod(new CustomMethod("notifyAll").setAttr(CustomAttr.EXCLUDED))
		.addCustomMethod(new CustomMethod("finalize").setAttr(CustomAttr.EXCLUDED))
		.addCustomMethod(new CustomMethod("clone").setAttr(CustomAttr.EXCLUDED))
		.addCustomMethod(new CustomMethod("getClass").setAttr(CustomAttr.EXCLUDED))
		;
		
		/*
		 * Add types
		 */
		addCustomType(VJO_OBJECT, cType);
		addCustomType(type.getName(), cType);
		/*
		addPrivilegedMethodProcessor(type.getName(), "equals", new PrivilegedProcessorAdapter(){
			public IExpr processMtdInvocation(
					final ASTNode astNode,
					final JstIdentifier identifier, 
					final IExpr optionalExpr, 
					final List<IExpr> args, 
					final boolean isSuper,
					final BaseJstNode jstNode,
					final CustomType cType,
					final CustomMethod cMtd){
				
				if (optionalExpr == null){
					return null;
				}
				
				IJstType qualifierType = optionalExpr.getResultType();
				if (qualifierType == null){
					return null;
				}
				
				if (qualifierType instanceof JstArray){
					return null;
				}
				else {
					MtdInvocationExpr mtdCall = new MtdInvocationExpr(new JstIdentifier(cMtd.getJavaMethodName()));
					mtdCall.setQualifyExpr(optionalExpr);
					mtdCall.setArgs(args);
					return mtdCall;
				}
			}
		});
		*/
	}
	

	private void loadComparableType() {
		Class type = Comparable.class;
		CustomType cType = new CustomType(type, COMPARABLE_PKG)
		.setAttr(CustomAttr.MAPPED_TO_VJO)
		
		/*
		 * Supported Mapped Methods
		 */
		.addCustomMethod("compareTo", "compareTo", OBJECT_UTIL_TYPE_NAME);

		/*
		 * Add types
		 */
		addCustomType(COMPARABLE_PKG, cType);
		addCustomType(type.getName(), cType);
	}

	private void loadEnumType() {
		Class<?> type = Enum.class;
		CustomType cType = new CustomType(type, VJO_ENUM)
			.setAttr(CustomAttr.MAPPED_TO_VJO)
			
		/*
		 * Supported Mapped Methods
		 */
		//.addCustomMethod("valueOf", "from");
		
		/*
		 * Custom processor
		 */
		/*
		addPrivilegedMethodProcessor(type.getName(), "valueOf", new PrivilegedProcessorAdapter(){
			public IExpr processMtdInvocation(
					final ASTNode astNode,
					final JstIdentifier identifier, 
					final IExpr optionalExpr, 
					final List<IExpr> jstArgs, 
					final boolean isSuper,
					final BaseJstNode jstNode,
					final CustomType cType,
					final CustomMethod cMtd){
				
				IJstType varType = null;

				if (optionalExpr == null) {
					return null;
				} 

				IExpr qualifierExpr = optionalExpr;
				if (qualifierExpr == null){
					qualifierExpr = identifier.getQualifier();
				}
				if (qualifierExpr == null || identifier == null){
					return null;
				} else {
					varType = qualifierExpr.getResultType();
					String name = identifier.getName();
					
					if (varType == null){
							return null;
					}
					if (!varType.isEnum() || !"valueOf".equals(name)) {
						return null;
					}
				}
				
				identifier.setName("from"); //VJO replacement for valueOf is 'from'
				MtdInvocationExpr mtdCall = new MtdInvocationExpr(identifier);
				mtdCall.setQualifyExpr(qualifierExpr);
				if (jstArgs != null){
					for (IExpr e: jstArgs){
						mtdCall.addArg(e);
					}
				}
				return mtdCall;
			}
		})*/;
		
		/*
		 * Add types
		 */
		addCustomType(VJO_ENUM, cType);
		addCustomType(type.getName(), cType);
	}
	
	private void loadNullPointerException() {
		Class type = NullPointerException.class;
		CustomType cType = new CustomType(type,VJOX + ".java.lang.NullPointerException");

		addCustomType(type.getName(), cType);
		addCustomType(VJO_PKG+type.getName(), cType);
		addObjectUtilMethods(type.getName());
	}
	private void loadIllegalArgumentException() {
		Class type = IllegalArgumentException.class;
		CustomType cType = new CustomType(type,VJOX + ".java.lang.IllegalArgumentException");

		addCustomType(type.getName(), cType);
		addCustomType(VJO_PKG+type.getName(), cType);
		addObjectUtilMethods(type.getName());
	}
	
	private void loadError() {
		Class type = Error.class;
		CustomType cType = new CustomType(type,VJOX + ".java.lang.Error");

		addCustomType(type.getName(), cType);
		addCustomType(VJO_PKG+type.getName(), cType);
		addObjectUtilMethods(type.getName());
	}
	
	private void loadIllegalAccessException() {
		Class type = IllegalAccessException.class;
		CustomType cType = new CustomType(type,VJOX + ".java.lang.IllegalAccessException");

		addCustomType(type.getName(), cType);
		addCustomType(VJO_PKG+type.getName(), cType);
		addObjectUtilMethods(type.getName());
	}
	private void loadNoSuchFieldException() {
		Class type = NoSuchFieldException.class;
		CustomType cType = new CustomType(type,VJOX + ".java.lang.NoSuchFieldException");

		addCustomType(type.getName(), cType);
		addCustomType(VJO_PKG+type.getName(), cType);
		addObjectUtilMethods(type.getName());
	}
	private void loadOutOfMemoryError() {
		Class type = OutOfMemoryError.class;
		CustomType cType = new CustomType(type,VJOX + ".java.lang.OutOfMemoryError");

		addCustomType(type.getName(), cType);
		addCustomType(VJO_PKG+type.getName(), cType);
		addObjectUtilMethods(type.getName());
	}
	private void loadNumberFormatException() {
		Class type = NumberFormatException.class;
		CustomType cType = new CustomType(type,VJOX + ".java.lang.NumberFormatException");

		addCustomType(type.getName(), cType);
		addCustomType(VJO_PKG+type.getName(), cType);
		addObjectUtilMethods(type.getName());
	}	
	
	private void loadUnsupportedOperationException() {
		Class type = UnsupportedOperationException.class;
		CustomType cType = new CustomType(type,VJOX + ".java.lang.UnsupportedOperationException");

		addCustomType(type.getName(), cType);
		addCustomType(VJO_PKG+type.getName(), cType);
		addObjectUtilMethods(type.getName());
	}
	private void loadArithmeticException() {
		Class type = ArithmeticException.class;
		CustomType cType = new CustomType(type,VJOX + ".java.lang.ArithmeticException");

		addCustomType(type.getName(), cType);
		addCustomType(VJO_PKG+type.getName(), cType);
		addObjectUtilMethods(type.getName());
	}	
	
	private void loadArrayIndexOutOfBoundsException() {
		Class type = ArrayIndexOutOfBoundsException.class;
		CustomType cType = new CustomType(type,VJOX + ".java.lang.ArrayIndexOutOfBoundsException");

		addCustomType(type.getName(), cType);
		addCustomType(VJO_PKG+type.getName(), cType);
		addObjectUtilMethods(type.getName());
	}
	
	private void loadIndexOutOfBoundsException() {
		Class type = IndexOutOfBoundsException.class;
		CustomType cType = new CustomType(type,VJOX + ".java.lang.IndexOutOfBoundsException");

		addCustomType(type.getName(), cType);
		addCustomType(VJO_PKG+type.getName(), cType);
		addObjectUtilMethods(type.getName());
	}
	
	private void loadStringIndexOutOfBoundsException() {
		Class type = StringIndexOutOfBoundsException.class;
		CustomType cType = new CustomType(type,VJOX + ".java.lang.StringIndexOutOfBoundsException");

		addCustomType(type.getName(), cType);
		addCustomType(VJO_PKG+type.getName(), cType);
		addObjectUtilMethods(type.getName());
	}
	
	private void loadInstantiationException() {
		Class type = InstantiationException.class;
		CustomType cType = new CustomType(type,VJOX + ".java.lang.InstantiationException");

		addCustomType(type.getName(), cType);
		addCustomType(VJO_PKG+type.getName(), cType);
		addObjectUtilMethods(type.getName());
	}
	
	private void loadConcurrentModificationException() {
		Class type = ConcurrentModificationException.class;
		CustomType cType = new CustomType(type,VJOX + ".java.util.ConcurrentModificationException");

		addCustomType(type.getName(), cType);
		addCustomType(VJO_PKG+type.getName(), cType);
		addObjectUtilMethods(type.getName());
	}
	
	private void loadIllegalStateException() {
		Class type = IllegalStateException.class;
		CustomType cType = new CustomType(type,VJOX + ".java.lang.IllegalStateException");

		addCustomType(type.getName(), cType);
		addCustomType(VJO_PKG+type.getName(), cType);
		addObjectUtilMethods(type.getName());
	}
	private void loadNoSuchElementException() {
		Class type = NoSuchElementException.class;
		CustomType cType = new CustomType(type,VJOX + ".java.util.NoSuchElementException");

		addCustomType(type.getName(), cType);
		addCustomType(VJO_PKG+type.getName(), cType);
		addObjectUtilMethods(type.getName());
	}
	
	private void loadArrayStoreException() {
		Class type = ArrayStoreException.class;
		CustomType cType = new CustomType(type,VJOX + ".java.lang.ArrayStoreException");

		addCustomType(type.getName(), cType);
		addCustomType(VJO_PKG+type.getName(), cType);
		addObjectUtilMethods(type.getName());
	}
	
	private void loadClassCastException() {
		Class type = ClassCastException.class;
		CustomType cType = new CustomType(type,VJOX + ".java.lang.ClassCastException");

		addCustomType(type.getName(), cType);
		addCustomType(VJO_PKG+type.getName(), cType);
		addObjectUtilMethods(type.getName());
	}
	
	private void loadStringBufferType() {
		Class type = StringBuffer.class;

//		String[] params1 = {"char ch"};
//		MethodKey mtdKey1 = new MethodKey("append",false,"StringBuffer",params1);
//		
//		String[] params2 = {"char chars[]"};
//		MethodKey mtdKey2 = new MethodKey("append",false,"StringBuffer",params2);
//		
//		CustomMethod customMtd = new CustomMethod("append","append",false);
//		customMtd.setAttr(CustomAttr.EXCLUDED);
//		
//		String[] params3 = {"int index", "char[] chars"};
//		MethodKey mtdKey3 = new MethodKey("insert",false,"StringBuffer",params3);
//		
//		String[] params4 = {"int index", "char ch"};
//		MethodKey mtdKey4 = new MethodKey("insert",false,"StringBuffer",params4);
//		
//		CustomMethod customMtd1 = new CustomMethod("insert","insert",false);
//		customMtd1.setAttr(CustomAttr.EXCLUDED);
		
		CustomType cType = new CustomType(type, VJOX + "." + type.getName());
//	    	.lookupMtdBySigniture(true)
//		    .addCustomMethod(mtdKey1,customMtd)
//		    .addCustomMethod(mtdKey2,customMtd)
//		    .addCustomMethod(mtdKey3,customMtd1)
//		    .addCustomMethod(mtdKey4, customMtd1);

		addCustomType(type.getName(), cType);
		addCustomType(VJO_PKG + type.getName(), cType);
		addObjectUtilMethods(type.getName());
	}
	
	private void loadStringBuilderType() {
		Class type = StringBuilder.class;
		String[] params1 = {"char ch"};
		MethodKey mtdKey1 = new MethodKey("append",false,params1);
		String[] params2 = {"char chars[]"};
		MethodKey mtdKey2 = new MethodKey("append",false,params2);
		String[] params3 = {"int index", "char[] chars"};
		MethodKey mtdKey3 = new MethodKey("insert",false,params3);
		String[] params4 = {"int index", "char ch"};
		MethodKey mtdKey4 = new MethodKey("insert",false,params4);		

		CustomType cType = new CustomType(type, VJOX + "." + type.getName())
		    .addCustomMethod(new CustomMethod(mtdKey1,"append").setAttr(CustomAttr.EXCLUDED))
		    .addCustomMethod(new CustomMethod(mtdKey2,"append").setAttr(CustomAttr.EXCLUDED))
		    .addCustomMethod(new CustomMethod(mtdKey3,"insert").setAttr(CustomAttr.EXCLUDED))
		    .addCustomMethod(new CustomMethod(mtdKey4,"insert").setAttr(CustomAttr.EXCLUDED));

		addCustomType(type.getName(), cType);
		addCustomType(VJO_PKG+type.getName(), cType);	
		addObjectUtilMethods(type.getName());
	}

	
	// ************ util *******************//
	private void loadHashMap() {
		Class type = HashMap.class;
		CustomType cType = new CustomType(type, VJOX + ".java.util.HashMap")

		// not supported
		.addCustomMethod(new CustomMethod("clone").setAttr(CustomAttr.EXCLUDED))
		.addCustomMethod(new CustomMethod("wait").setAttr(CustomAttr.EXCLUDED))
	    .addCustomMethod(new CustomMethod("notify").setAttr(CustomAttr.EXCLUDED))
	    .addCustomMethod(new CustomMethod("notifyAll").setAttr(CustomAttr.EXCLUDED));

		addCustomType(type.getName(), cType);
		addCustomType(VJO_PKG+type.getName(), cType);
		addObjectUtilMethods(type.getName());
	}
	private void loadVector() {
		Class type = Vector.class;
		CustomType cType = new CustomType(type, VJOX + ".java.util.Vector")
//		 not supported
		.addCustomMethod(new CustomMethod("getClass").setAttr(CustomAttr.EXCLUDED))
	    .addCustomMethod(new CustomMethod("wait").setAttr(CustomAttr.EXCLUDED))
	    .addCustomMethod(new CustomMethod("notify").setAttr(CustomAttr.EXCLUDED))
	    .addCustomMethod(new CustomMethod("notifyAll").setAttr(CustomAttr.EXCLUDED));

		addCustomType(type.getName(), cType);
		addCustomType(VJO_PKG+type.getName(), cType);
		addObjectUtilMethods(type.getName());
	}
	private void loadArrayList() {
		Class type = ArrayList.class;
		CustomType cType = new CustomType(type, ARRAYLIST)
//		 not supported
		.addCustomMethod(new CustomMethod("clone").setAttr(CustomAttr.EXCLUDED))
	    .addCustomMethod(new CustomMethod("wait").setAttr(CustomAttr.EXCLUDED))
	    .addCustomMethod(new CustomMethod("notify").setAttr(CustomAttr.EXCLUDED))
	    .addCustomMethod(new CustomMethod("notifyAll").setAttr(CustomAttr.EXCLUDED));

		addCustomType(type.getName(), cType);
		addCustomType(ARRAYLIST,cType);
		
		addObjectUtilMethods(type.getName());
	}

	private void addObjectUtilMethods(String typeName) {
		ObjectPrivilegedProcessorAdapter objectPrivilegedProcessorAdapter = new ObjectPrivilegedProcessorAdapter();
		addPrivilegedMethodProcessor(typeName, "equals", objectPrivilegedProcessorAdapter);
		addPrivilegedMethodProcessor(typeName, "hashCode", objectPrivilegedProcessorAdapter);
	}
	
	private void loadHashtable() {
		Class type = Hashtable.class;
		CustomType cType = new CustomType(type, VJOX + ".java.util.Hashtable")
//		 not supported
		.addCustomMethod(new CustomMethod("getClass").setAttr(CustomAttr.EXCLUDED))
	    .addCustomMethod(new CustomMethod("wait").setAttr(CustomAttr.EXCLUDED))
	    .addCustomMethod(new CustomMethod("notify").setAttr(CustomAttr.EXCLUDED))
	    .addCustomMethod(new CustomMethod("notifyAll").setAttr(CustomAttr.EXCLUDED));

		addCustomType(type.getName(), cType);
		addCustomType(VJO_PKG+type.getName(), cType);
		addObjectUtilMethods(type.getName());
	}
	private void loadHashSet() {
		Class type = HashSet.class;
		CustomType cType = new CustomType(type, VJOX + ".java.util.HashSet")
//		 not supported
		.addCustomMethod(new CustomMethod("getClass").setAttr(CustomAttr.EXCLUDED))
	    .addCustomMethod(new CustomMethod("wait").setAttr(CustomAttr.EXCLUDED))
	    .addCustomMethod(new CustomMethod("notify").setAttr(CustomAttr.EXCLUDED))
	    .addCustomMethod(new CustomMethod("notifyAll").setAttr(CustomAttr.EXCLUDED));

		addCustomType(type.getName(), cType);
		addCustomType(VJO_PKG+type.getName(), cType);
		addObjectUtilMethods(type.getName());
	}
	private void loadSet() {
		Class type = Set.class;
		CustomType cType = new CustomType(type, VJOX + ".java.util.Set")
//		 not supported
		.addCustomMethod(new CustomMethod("getClass").setAttr(CustomAttr.EXCLUDED))
	    .addCustomMethod(new CustomMethod("wait").setAttr(CustomAttr.EXCLUDED))
	    .addCustomMethod(new CustomMethod("notify").setAttr(CustomAttr.EXCLUDED))
	    .addCustomMethod(new CustomMethod("notifyAll").setAttr(CustomAttr.EXCLUDED));

		addCustomType(type.getName(), cType);
		addCustomType(VJO_PKG+type.getName(), cType);
		addObjectUtilMethods(type.getName());
	}
	private void loadMap() {
		Class type = Map.class;
		CustomType cType = new CustomType(type, VJOX + ".java.util.Map")
//		 not supported
		.addCustomMethod(new CustomMethod("getClass").setAttr(CustomAttr.EXCLUDED))
	    .addCustomMethod(new CustomMethod("wait").setAttr(CustomAttr.EXCLUDED))
	    .addCustomMethod(new CustomMethod("notify").setAttr(CustomAttr.EXCLUDED))
	    .addCustomMethod(new CustomMethod("notifyAll").setAttr(CustomAttr.EXCLUDED));

		addCustomType(type.getName(), cType);
		addCustomType(VJO_PKG+type.getName(), cType);
		addObjectUtilMethods(type.getName());
	}
	private void loadList() {
		Class type = List.class;
		CustomType cType = new CustomType(type, VJOX + ".java.util.List")
//		 not supported
		.addCustomMethod(new CustomMethod("getClass").setAttr(CustomAttr.EXCLUDED))
	    .addCustomMethod(new CustomMethod("wait").setAttr(CustomAttr.EXCLUDED))
	    .addCustomMethod(new CustomMethod("notify").setAttr(CustomAttr.EXCLUDED))
	    .addCustomMethod(new CustomMethod("notifyAll").setAttr(CustomAttr.EXCLUDED));

		addCustomType(type.getName(), cType);
		addCustomType(VJO_PKG+type.getName(), cType);
		addObjectUtilMethods(type.getName());
	}
	private void loadCollection() {
		Class type = Collection.class;
		CustomType cType = new CustomType(type, VJOX + ".java.util.Collection")
//		 not supported
		.addCustomMethod(new CustomMethod("getClass").setAttr(CustomAttr.EXCLUDED))
	    .addCustomMethod(new CustomMethod("wait").setAttr(CustomAttr.EXCLUDED))
	    .addCustomMethod(new CustomMethod("notify").setAttr(CustomAttr.EXCLUDED))
	    .addCustomMethod(new CustomMethod("notifyAll").setAttr(CustomAttr.EXCLUDED));

		addCustomType(type.getName(), cType);
		addCustomType(VJO_PKG+type.getName(), cType);
		addObjectUtilMethods(type.getName());
	}
	private void loadEnumeration() {
		Class type = Enumeration.class;
		CustomType cType = new CustomType(type, VJOX + ".java.util.Enumeration")
//		 not supported
		.addCustomMethod(new CustomMethod("getClass").setAttr(CustomAttr.EXCLUDED))
	    .addCustomMethod(new CustomMethod("wait").setAttr(CustomAttr.EXCLUDED))
	    .addCustomMethod(new CustomMethod("notify").setAttr(CustomAttr.EXCLUDED))
	    .addCustomMethod(new CustomMethod("notifyAll").setAttr(CustomAttr.EXCLUDED));

		addCustomType(type.getName(), cType);
		addCustomType(VJO_PKG+type.getName(), cType);
		addObjectUtilMethods(type.getName());
	}
	private void loadIterator() {
		Class type = Iterator.class;
		CustomType cType = new CustomType(type, VJOX + ".java.util.Iterator")

		// not supported
		.addCustomMethod(new CustomMethod("getClass").setAttr(CustomAttr.EXCLUDED))
	    .addCustomMethod(new CustomMethod("wait").setAttr(CustomAttr.EXCLUDED))
	    .addCustomMethod(new CustomMethod("notify").setAttr(CustomAttr.EXCLUDED))
	    .addCustomMethod(new CustomMethod("notifyAll").setAttr(CustomAttr.EXCLUDED));

		addCustomType(type.getName(), cType);
		addCustomType(VJO_PKG+type.getName(), cType);
		addObjectUtilMethods(type.getName());
	}		
	private void loadListIterator() {
		Class type = ListIterator.class;
		CustomType cType = new CustomType(type, VJOX + ".java.util.ListIterator")

		// not supported
		.addCustomMethod(new CustomMethod("getClass").setAttr(CustomAttr.EXCLUDED))
	    .addCustomMethod(new CustomMethod("wait").setAttr(CustomAttr.EXCLUDED))
	    .addCustomMethod(new CustomMethod("notify").setAttr(CustomAttr.EXCLUDED))
	    .addCustomMethod(new CustomMethod("notifyAll").setAttr(CustomAttr.EXCLUDED));

		addCustomType(type.getName(), cType);
		addCustomType(VJO_PKG+type.getName(), cType);
		addObjectUtilMethods(type.getName());
	}	
	private void loadCollections() {
		Class type = Collections.class;
		CustomType cType = new CustomType(type, VJOX + ".java.util.Collections")

//       supported mapped methods
		.addCustomMethod("singleton", "singletonSet", COLLECTIONS_TYPE_NAME)

//		 not supported
		.addCustomMethod(new CustomMethod("getClass").setAttr(CustomAttr.EXCLUDED))
	    .addCustomMethod(new CustomMethod("wait").setAttr(CustomAttr.EXCLUDED))
	    .addCustomMethod(new CustomMethod("notify").setAttr(CustomAttr.EXCLUDED))
	    .addCustomMethod(new CustomMethod("notifyAll").setAttr(CustomAttr.EXCLUDED));

		addCustomType(type.getName(), cType);
		addCustomType(VJO_PKG+type.getName(), cType);
		addObjectUtilMethods(type.getName());
	}
	private void loadArrays() {
		Class type = Arrays.class;
		CustomType cType = new CustomType(type, VJOX + ".java.util.Arrays")
//		 not supported
		.addCustomMethod(new CustomMethod("getClass").setAttr(CustomAttr.EXCLUDED))
	    .addCustomMethod(new CustomMethod("wait").setAttr(CustomAttr.EXCLUDED))
	    .addCustomMethod(new CustomMethod("notify").setAttr(CustomAttr.EXCLUDED))
	    .addCustomMethod(new CustomMethod("notifyAll").setAttr(CustomAttr.EXCLUDED));

		addCustomType(type.getName(), cType);
		addCustomType(VJO_PKG+type.getName(), cType);
	}
	private void loadStack() {
		Class type = Stack.class;
		CustomType cType = new CustomType(type, VJOX + ".java.util.Stack")
//		 not supported
		.addCustomMethod(new CustomMethod("getClass").setAttr(CustomAttr.EXCLUDED))
	    .addCustomMethod(new CustomMethod("wait").setAttr(CustomAttr.EXCLUDED))
	    .addCustomMethod(new CustomMethod("notify").setAttr(CustomAttr.EXCLUDED))
	    .addCustomMethod(new CustomMethod("notifyAll").setAttr(CustomAttr.EXCLUDED));

		addCustomType(type.getName(), cType);
		addCustomType(VJO_PKG+type.getName(), cType);
	}
	private void loadProperties() {
		Class type = Properties.class;
		CustomType cType = new CustomType(type, VJOX + ".java.util.Properties")
//		 not supported
		.addCustomMethod(new CustomMethod("getClass").setAttr(CustomAttr.EXCLUDED))
	    .addCustomMethod(new CustomMethod("wait").setAttr(CustomAttr.EXCLUDED))
	    .addCustomMethod(new CustomMethod("notify").setAttr(CustomAttr.EXCLUDED))
	    .addCustomMethod(new CustomMethod("notifyAll").setAttr(CustomAttr.EXCLUDED));

		addCustomType(type.getName(), cType);
		addCustomType(VJO_PKG+type.getName(), cType);
	}
	private void loadStringTokenizer() {
		Class type = StringTokenizer.class;
		CustomType cType = new CustomType(type, VJOX + ".java.util.StringTokenizer")
//		 not supported
		.addCustomMethod(new CustomMethod("getClass").setAttr(CustomAttr.EXCLUDED))
	    .addCustomMethod(new CustomMethod("wait").setAttr(CustomAttr.EXCLUDED))
	    .addCustomMethod(new CustomMethod("notify").setAttr(CustomAttr.EXCLUDED))
	    .addCustomMethod(new CustomMethod("notifyAll").setAttr(CustomAttr.EXCLUDED));

		addCustomType(type.getName(), cType);
		addCustomType(VJO_PKG+type.getName(), cType);
		addObjectUtilMethods(type.getName());
	}
	private void loadAbstractCollection() {
		Class type = AbstractCollection.class;
		CustomType cType = new CustomType(type, VJOX + ".java.util.AbstractCollection")

		.addCustomMethod(new CustomMethod("getClass").setAttr(CustomAttr.EXCLUDED))
	    .addCustomMethod(new CustomMethod("wait").setAttr(CustomAttr.EXCLUDED))
	    .addCustomMethod(new CustomMethod("notify").setAttr(CustomAttr.EXCLUDED))
	    .addCustomMethod(new CustomMethod("notifyAll").setAttr(CustomAttr.EXCLUDED));

		addCustomType(type.getName(), cType);
		addCustomType(VJO_PKG+type.getName(), cType);
		addObjectUtilMethods(type.getName());
	}
	private void loadMapEntry() {
		Class type = Map.Entry.class;
		CustomType cType = new CustomType(type, VJOX + ".java.util.Map.Entry")

		.addCustomMethod(new CustomMethod("getClass").setAttr(CustomAttr.EXCLUDED))
	    .addCustomMethod(new CustomMethod("wait").setAttr(CustomAttr.EXCLUDED))
	    .addCustomMethod(new CustomMethod("notify").setAttr(CustomAttr.EXCLUDED))
	    .addCustomMethod(new CustomMethod("notifyAll").setAttr(CustomAttr.EXCLUDED));
		addCustomType("java.util.Map.Entry", cType);
		addCustomType("Map.Entry", cType);
		addCustomType(VJO_PKG+"java.util.Map.Entry", cType);
		addObjectUtilMethods(type.getName());
	}	
	
	private void loadComparator() {
		Class type = Comparator.class;
		CustomType cType = new CustomType(type, VJOX + ".java.util.Comparator")

		.addCustomMethod(new CustomMethod("getClass").setAttr(CustomAttr.EXCLUDED))
	    .addCustomMethod(new CustomMethod("wait").setAttr(CustomAttr.EXCLUDED))
	    .addCustomMethod(new CustomMethod("notify").setAttr(CustomAttr.EXCLUDED))
	    .addCustomMethod(new CustomMethod("notifyAll").setAttr(CustomAttr.EXCLUDED));

		addCustomType(type.getName(), cType);
		addCustomType(VJO_PKG+type.getName(), cType);
	}

	private void loadRandom() {
		Class type = Random.class;
		CustomType cType = new CustomType(type, VJOX + ".java.util.Random")

		.addCustomMethod(new CustomMethod("getClass").setAttr(CustomAttr.EXCLUDED))
	    .addCustomMethod(new CustomMethod("wait").setAttr(CustomAttr.EXCLUDED))
	    .addCustomMethod(new CustomMethod("notify").setAttr(CustomAttr.EXCLUDED))
	    .addCustomMethod(new CustomMethod("notifyAll").setAttr(CustomAttr.EXCLUDED));

		addCustomType(type.getName(), cType);
		addCustomType(VJO_PKG+type.getName(), cType);
		addObjectUtilMethods(type.getName());
	}
	private void loadDate() {
		final Class type = Date.class;
		CustomType cType = new CustomType(type)
				.setAttr(CustomAttr.MAPPED_TO_JS)
				// use DateUtil
				.addCustomMethod("after", "after", DATE_UTIL_TYPE_NAME)
				.addCustomMethod("before", "before", DATE_UTIL_TYPE_NAME)
				.addCustomMethod("compareTo", "compareTo", DATE_UTIL_TYPE_NAME)
				//Use ObjectUtil for this:
				//.addCustomMethod("equals", "equals", DATE_UTIL_TYPE_NAME)
				.addCustomMethod("getMillisOf", "getMillisOf", DATE_UTIL_TYPE_NAME)
				//Use ObjectUtil for this:
				//.addCustomMethod("hashCode", "hashCode", DATE_UTIL_TYPE_NAME)
				.addCustomMethod("getTimezoneOffset", "getTimezoneOffset", DATE_UTIL_TYPE_NAME)
				
				//not supported
				.addCustomMethod(new CustomMethod("clone").setAttr(CustomAttr.EXCLUDED))
		        .addCustomMethod(new CustomMethod("wait").setAttr(CustomAttr.EXCLUDED))
     	         .addCustomMethod(new CustomMethod("notify").setAttr(CustomAttr.EXCLUDED))
		         .addCustomMethod(new CustomMethod("notifyAll").setAttr(CustomAttr.EXCLUDED))
		         .addCustomMethod(new CustomMethod("getClass").setAttr(CustomAttr.EXCLUDED)) ;
		
		addCustomType(type.getSimpleName(), cType);
		addCustomType(type.getName(), cType);
		addCustomType(VJO_PKG+type.getName(), cType);
		
		addPrivilegedConstructorProcessor(type.getName(), new PrivilegedProcessorAdapter(){
			public IExpr processInstanceCreation(
					final ClassInstanceCreation cic,
					final BaseJstNode jstNode,
					final List<IExpr> argExprList,
					final CustomType clientType) {
				int argCount = argExprList.size();
				IExpr[] newArgs =new IExpr[argCount];
				
				if (argCount == 3 || argCount == 5 || argCount == 6) {
					for (int i = 0; i < argExprList.size(); i++) {
						if (i == 0) {
							//Update year arg with +1900
							InfixExpr expr = new InfixExpr(argExprList.get(i), new TextExpr("1900"), InfixExpr.Operator.PLUS);
							newArgs[i] = expr;
						} else {
							newArgs[i] = argExprList.get(i);
						}
					}
					
					ObjCreationExpr objExpr = TranslateHelper.Expression
						.createObjCreationExpr(type.getName(), newArgs);
					return objExpr;
				}
				
				return null;
			}
		});
		addPrivilegedMethodProcessor(type.getName(), "setYear", new PrivilegedProcessorAdapter(){
			public IExpr processMtdInvocation(
					final ASTNode astNode,
					final JstIdentifier identif