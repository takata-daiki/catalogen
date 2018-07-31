/*
 * Copyright (c) 2007 BUSINESS OBJECTS SOFTWARE LIMITED
 * All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * 
 *     * Redistributions of source code must retain the above copyright notice,
 *       this list of conditions and the following disclaimer.
 *  
 *     * Redistributions in binary form must reproduce the above copyright
 *       notice, this list of conditions and the following disclaimer in the
 *       documentation and/or other materials provided with the distribution.
 *  
 *     * Neither the name of Business Objects nor the names of its contributors
 *       may be used to endorse or promote products derived from this software
 *       without specific prior written permission.
 *  
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */


/*
 * CALDocChecker.java
 * Creation date: Aug 17, 2005.
 * By: Joseph Wong
 */
package org.openquark.cal.compiler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import org.openquark.cal.module.Cal.Core.CAL_Prelude;
import org.openquark.util.Pair;


/**
 * Checks the CALDoc comments in CAL source code. The process of checking the
 * comments produce as an end result instances of CALDocComment representing
 * these comments. These CALDocComment objects are then associated with the
 * appropriate entities stored in the ModuleTypeInfo, making them available
 * to clients for later retrieval.
 *
 * @author Joseph Wong
 */
class CALDocChecker {

    /** The CALCompiler associated with this checker. */
    private final CALCompiler compiler;

    /** The module type info of the current module. */
    private final ModuleTypeInfo currentModuleTypeInfo;
    
    /** The FreeVariableChecker associated with this checker. */
    private final FreeVariableFinder finder;
    
    /** The CALTypeChecker associated with this checker. */
    private final CALTypeChecker typeChecker;
    
    /**
     * An internal exception class used to signal that the processing of a CALDoc comment should
     * end after having encountered an error in the comment.
     *
     * @author Joseph Wong
     */
    private static final class InvalidCALDocException extends Exception {
       
        private static final long serialVersionUID = -5609261520985652737L;

        /** Private constructor for this internal exception class. */
        private InvalidCALDocException() {}
    }
    
    /**
     * A type-safe enumeration for constants representing the different 'categories' into which
     * a CALDoc cross-reference appearing without 'context' keywords may be resolved.
     * 
     * Such a reference may appear in a CALDoc comment, as in:
     * {at-link Prelude at-} - a module reference without context
     * {at-link Nothing at-} - a data constructor reference without context
     * at-see Prelude.Int - a type constructor reference without context
     * at-see Eq - a type class reference without context
     *
     * @author Joseph Wong
     */
    static final class CategoryForCALDocConsNameWithoutContextCrossReference {
        /**
         * Represents the fact that a CALDoc cross-reference appearing without a 'context' keyword unambiguously resolves to a module name.
         */
        static final CategoryForCALDocConsNameWithoutContextCrossReference MODULE_NAME = new CategoryForCALDocConsNameWithoutContextCrossReference("module name");
        /**
         * Represents the fact that a CALDoc cross-reference appearing without a 'context' keyword unambiguously resolves to a type constructor name.
         */
        static final CategoryForCALDocConsNameWithoutContextCrossReference TYPE_CONS_NAME = new CategoryForCALDocConsNameWithoutContextCrossReference("type constructor name");
        /**
         * Represents the fact that a CALDoc cross-reference appearing without a 'context' keyword unambiguously resolves to a data constructor name.
         */
        static final CategoryForCALDocConsNameWithoutContextCrossReference DATA_CONS_NAME = new CategoryForCALDocConsNameWithoutContextCrossReference("data constructor name");
        /**
         * Represents the fact that a CALDoc cross-reference appearing without a 'context' keyword unambiguously resolves to a type class name.
         */
        static final CategoryForCALDocConsNameWithoutContextCrossReference TYPE_CLASS_NAME = new CategoryForCALDocConsNameWithoutContextCrossReference("type class name");
        
        /** The display name for the enumeration constant. */
        private final String displayName;
        
        /** Private constructor for this type-safe enumeration. */
        private CategoryForCALDocConsNameWithoutContextCrossReference(String displayName) {
            this.displayName = displayName;
        }
        
        /** @return the display name for the enumeration constant. */
        @Override
        public String toString() {
            return displayName;
        }
    }
    
    /**
     * Constructs a CALDocChecker.
     * 
     * @param compiler
     * @param currentModuleTypeInfo
     * @param finder
     * @param typeChecker
     */
    CALDocChecker(
        CALCompiler compiler,
        ModuleTypeInfo currentModuleTypeInfo,
        FreeVariableFinder finder,
        CALTypeChecker typeChecker) {
        
        this.compiler = compiler;
        this.currentModuleTypeInfo = currentModuleTypeInfo;
        this.finder = finder;
        this.typeChecker = typeChecker;
    }

    /**
     * Checks the CALDoc comments within the specified module definition. The checks
     * include verifying that identifiers contained within the comment are indeed
     * resolvable to entities within the module and/or other imported modules.
     * 
     * @param moduleDefnNode the module definition node whose subtree is to be checked.
     */
    void checkCALDocCommentsInModuleDefn(ParseTreeNode moduleDefnNode) {
        
        moduleDefnNode.verifyType(CALTreeParserTokenTypes.MODULE_DEFN);
        
        // check the module's CALDoc comment.
        ParseTreeNode optionalCALDocNode = moduleDefnNode.firstChild();
        CALDocComment caldoc = checkAndBuildCALDocComment(optionalCALDocNode, null, null, false, false, false);
        
        // associate the CALDoc with the entity
        currentModuleTypeInfo.setCALDocComment(caldoc);
        
        // check the children.
        ParseTreeNode moduleNameNode = optionalCALDocNode.nextSibling();
        moduleNameNode.verifyType(CALTreeParserTokenTypes.HIERARCHICAL_MODULE_NAME, CALTreeParserTokenTypes.HIERARCHICAL_MODULE_NAME_EMPTY_QUALIFIER);
              
        ParseTreeNode importDeclarationListNode = moduleNameNode.nextSibling();
        importDeclarationListNode.verifyType(CALTreeParserTokenTypes.IMPORT_DECLARATION_LIST);
        
        ParseTreeNode friendDeclarationListNode = importDeclarationListNode.nextSibling();
        friendDeclarationListNode.verifyType(CALTreeParserTokenTypes.FRIEND_DECLARATION_LIST);
        
        ParseTreeNode outerDefnListNode = friendDeclarationListNode.nextSibling();
        outerDefnListNode.verifyType(CALTreeParserTokenTypes.OUTER_DEFN_LIST);
        checkCALDocCommentsInOuterDefnList(outerDefnListNode);
    }
    
    /**
     * Checks the CALDoc comments within the specified outer definition list. The checks
     * include verifying that identifiers contained within the comment are indeed
     * resolvable to entities within the module and/or other imported modules.
     * 
     * @param outerDefnListNode the outer definition list node whose subtree is to be checked.
     */
    void checkCALDocCommentsInOuterDefnList(ParseTreeNode outerDefnListNode) {
        
        outerDefnListNode.verifyType(CALTreeParserTokenTypes.OUTER_DEFN_LIST);
        
        for (final ParseTreeNode outerDefnNode : outerDefnListNode) {
            
            switch (outerDefnNode.getType()) {
              
                case CALTreeParserTokenTypes.TOP_LEVEL_TYPE_DECLARATION:
                {
                    checkCALDocCommentsInFunctionTypeDeclaration(outerDefnNode);
                    break;
                }
    
                case CALTreeParserTokenTypes.TOP_LEVEL_FUNCTION_DEFN:
                {
                    checkCALDocCommentsInAlgebraicFunctionDefn(outerDefnNode);
                    break;
                }
    
                case CALTreeParserTokenTypes.FOREIGN_FUNCTION_DECLARATION:
                {
                    checkCALDocCommentsInForeignFunctionDefn(outerDefnNode);
                    break;
                }
                
                case CALTreeParserTokenTypes.PRIMITIVE_FUNCTION_DECLARATION:
                {
                    checkCALDocCommentsInPrimitiveFunctionDefn(outerDefnNode);
                    break;
                }
    
                case CALTreeParserTokenTypes.DATA_DECLARATION:
                {                   
                    checkCALDocCommentsInAlgebraicTypeDefn(outerDefnNode);
                    break;
                }
    
                case CALTreeParserTokenTypes.FOREIGN_DATA_DECLARATION:
                {                     
                    checkCALDocCommentsInForeignTypeDefn(outerDefnNode);
                    break;
                }
    
                case CALTreeParserTokenTypes.TYPE_CLASS_DEFN:
                {                    
                    checkCALDocCommentsInTypeClassDefn(outerDefnNode);
                    break;
                }
    
                case CALTreeParserTokenTypes.INSTANCE_DEFN:
                {                   
                    checkCALDocCommentsInInstanceDefn(outerDefnNode);
                    break;
                }
    
                default:
                {
                    outerDefnNode.unexpectedParseTreeNode();
                    break;
                }
            }
        }
    }

    /**
     * Checks the CALDoc comments within the specified function type declaration. The checks
     * include verifying that identifiers contained within the comment are indeed
     * resolvable to entities within the module and/or other imported modules.
     * 
     * @param topLevelTypeDeclarationNode the function type declaration node whose subtree is to be checked.
     */
    private void checkCALDocCommentsInFunctionTypeDeclaration(ParseTreeNode topLevelTypeDeclarationNode) {
        
        topLevelTypeDeclarationNode.verifyType(CALTreeParserTokenTypes.TOP_LEVEL_TYPE_DECLARATION);
        
        ParseTreeNode optionalCALDocNode = topLevelTypeDeclarationNode.firstChild();
        optionalCALDocNode.verifyType(CALTreeParserTokenTypes.OPTIONAL_CALDOC_COMMENT);
        
        ParseTreeNode typeDeclarationNode = optionalCALDocNode.nextSibling();
        typeDeclarationNode.verifyType(CALTreeParserTokenTypes.TYPE_DECLARATION);
        
        ParseTreeNode functionNameNode = typeDeclarationNode.firstChild();
        functionNameNode.verifyType(CALTreeParserTokenTypes.VAR_ID);
        String functionName = functionNameNode.getText(); 
        
        ParseTreeNode topLevelFunctionDefnNode = typeChecker.getFunctionDefinitionNode(functionName);
        
        if (topLevelFunctionDefnNode == null) {
            // the function is declared but not defined. This is illegal in CAL.
            compiler.logMessage(new CompilerMessage(functionNameNode, new MessageKind.Error.DefinitionMissing(functionName)));
        } else {
            // fetch the parameter list from the function definition.
            topLevelFunctionDefnNode.verifyType(CALTreeParserTokenTypes.TOP_LEVEL_FUNCTION_DEFN);                
    
            ParseTreeNode paramListNode = topLevelFunctionDefnNode.getChild(3);
            paramListNode.verifyType(CALTreeParserTokenTypes.FUNCTION_PARAM_LIST); 
                
            // fetch the entity and then the type expression from the entity associated with the function.
            Function entity = currentModuleTypeInfo.getFunction(functionName);
            if (entity == null) {
                String displayName = getQualifiedNameDisplayString(functionNameNode);
                // TypeChecker: unknown function or variable {displayName}.
                compiler.logMessage(new CompilerMessage(functionNameNode, new MessageKind.Error.UnknownFunctionOrVariable(displayName)));
                
            } else {
                TypeExpr typeExpr = entity.getTypeExpr();
                
                // check the function's CALDoc with the function's type expression and the parameter list from the function definition.
                CALDocComment caldoc = checkAndBuildCALDocComment(optionalCALDocNode, paramListNode, typeExpr, true, true, false);
                
                // associate the CALDoc with the entity
                entity.setCALDocComment(caldoc);
            }
        }
    }

    /**
     * Checks the CALDoc comments within the specified algebraic function definition. The checks
     * include verifying that identifiers contained within the comment are indeed
     * resolvable to entities within the module and/or other imported modules.
     * 
     * @param topLevelFunctionDefnNode the algebraic function definition node whose subtree is to be checked.
     */
    private void checkCALDocCommentsInAlgebraicFunctionDefn(ParseTreeNode topLevelFunctionDefnNode) {
        
        topLevelFunctionDefnNode.verifyType(CALTreeParserTokenTypes.TOP_LEVEL_FUNCTION_DEFN);                
    
        ParseTreeNode optionalCALDocNode = topLevelFunctionDefnNode.firstChild();
        optionalCALDocNode.verifyType(CALTreeParserTokenTypes.OPTIONAL_CALDOC_COMMENT);
        
        ParseTreeNode accessModifierNode = optionalCALDocNode.nextSibling();
        accessModifierNode.verifyType(CALTreeParserTokenTypes.ACCESS_MODIFIER);                 
    
        ParseTreeNode functionNameNode = accessModifierNode.nextSibling();
        functionNameNode.verifyType(CALTreeParserTokenTypes.VAR_ID);
        String functionName = functionNameNode.getText(); 
        
        ParseTreeNode paramListNode = functionNameNode.nextSibling();
        paramListNode.verifyType(CALTreeParserTokenTypes.FUNCTION_PARAM_LIST); 
        
        ParseTreeNode definingExprNode = paramListNode.nextSibling();
        checkCALDocCommentsInExpr(definingExprNode);
    
        // if the function has an associated type declaration, then the CALDoc comment must appear
        // immediately before the type declaration, and not immediately before the function definition.
        if (typeChecker.hasFunctionTypeDeclaration(functionName)) {
            
            if (optionalCALDocNode.firstChild() != null) {
                compiler.logMessage(
                    new CompilerMessage(
                        optionalCALDocNode.firstChild(),
                        new MessageKind.Error.CALDocCommentForAlgebraicFunctionMustAppearBeforeTypeDeclaration(functionName)));
            }
            
        } else {
            // There is no associated type declaration, so it is okay to have a CALDoc comment before this function definition.
            
            // fetch the entity and then the type expression from the entity associated with the function.
            FunctionalAgent entity = currentModuleTypeInfo.getFunction(functionName);
            if (entity == null) {
                String displayName = getQualifiedNameDisplayString(functionNameNode);
                // TypeChecker: unknown function or variable {displayName}.
                compiler.logMessage(new CompilerMessage(functionNameNode, new MessageKind.Error.UnknownFunctionOrVariable(displayName)));
                
            } else {
                TypeExpr typeExpr = entity.getTypeExpr();
                
                // check the function's CALDoc with the function's type expression.
                CALDocComment caldoc = checkAndBuildCALDocComment(optionalCALDocNode, paramListNode, typeExpr, true, true, false);
                
                // associate the CALDoc with the entity
                entity.setCALDocComment(caldoc);
            }
        }
    }
    
    /**
     * Checks the CALDoc comments within the specified expression. The checks
     * include verifying that identifiers contained within the comment are indeed
     * resolvable to entities within the module and/or other imported modules.
     * 
     * @param parseTree the expression node whose subtree is to be checked.
     */
    void checkCALDocCommentsInExpr(ParseTreeNode parseTree) {
        int nodeType = parseTree.getType();

        switch (nodeType) {
            case CALTreeParserTokenTypes.VIRTUAL_LET_NONREC:
            case CALTreeParserTokenTypes.VIRTUAL_LET_REC:
            {
                checkCALDocCommentsInLet(parseTree);
                break;
            }

            case CALTreeParserTokenTypes.LAMBDA_DEFN:
            {
                ParseTreeNode paramListNode = parseTree.firstChild();
                paramListNode.verifyType(CALTreeParserTokenTypes.FUNCTION_PARAM_LIST);
                
                ParseTreeNode definingExprNode = paramListNode.nextSibling();
                checkCALDocCommentsInExpr(definingExprNode);
                break;
            }

            case CALTreeParserTokenTypes.LITERAL_if:
            {
                ParseTreeNode conditionNode = parseTree.firstChild();
                checkCALDocCommentsInExpr(conditionNode);

                ParseTreeNode ifTrueNode = conditionNode.nextSibling();
                checkCALDocCommentsInExpr(ifTrueNode);

                ParseTreeNode ifFalseNode = ifTrueNode.nextSibling();
                checkCALDocCommentsInExpr(ifFalseNode);
                break;
            }

            case CALTreeParserTokenTypes.VIRTUAL_DATA_CONSTRUCTOR_CASE:
            {
                checkCALDocCommentsInDataConstructorCase(parseTree);
                break;
            }
            
            case CALTreeParserTokenTypes.VIRTUAL_TUPLE_CASE: 
            {
                checkCALDocCommentsInTupleCase(parseTree);
                break;
            }
            
            case CALTreeParserTokenTypes.VIRTUAL_RECORD_CASE:
            {                
                checkCALDocCommentsInRecordCase(parseTree);
                break;
            }

            case CALTreeParserTokenTypes.BARBAR:              
            case CALTreeParserTokenTypes.AMPERSANDAMPERSAND: 
            case CALTreeParserTokenTypes.PLUSPLUS:               
            case CALTreeParserTokenTypes.EQUALSEQUALS:                              
            case CALTreeParserTokenTypes.NOT_EQUALS:              
            case CALTreeParserTokenTypes.GREATER_THAN_OR_EQUALS:             
            case CALTreeParserTokenTypes.GREATER_THAN:               
            case CALTreeParserTokenTypes.LESS_THAN:                
            case CALTreeParserTokenTypes.LESS_THAN_OR_EQUALS:              
            case CALTreeParserTokenTypes.PLUS:               
            case CALTreeParserTokenTypes.MINUS:              
            case CALTreeParserTokenTypes.ASTERISK:              
            case CALTreeParserTokenTypes.SOLIDUS: 
            case CALTreeParserTokenTypes.PERCENT:
            case CALTreeParserTokenTypes.COLON:
            case CALTreeParserTokenTypes.POUND:
            {
                ParseTreeNode arg1Node = parseTree.firstChild();
                checkCALDocCommentsInExpr(arg1Node);
                
                ParseTreeNode arg2Node = arg1Node.nextSibling();        
                checkCALDocCommentsInExpr(arg2Node);
                break;
            }
                
            case CALTreeParserTokenTypes.UNARY_MINUS:
            {
                ParseTreeNode arg1Node = parseTree.firstChild();
                checkCALDocCommentsInExpr(arg1Node);
                break;
            }
            
            case CALTreeParserTokenTypes.DOLLAR:
            {
                // Treat the node representing the $ operator as an application node 
                checkCALDocCommentsInApplication(parseTree);
                break;
            }

            case CALTreeParserTokenTypes.BACKQUOTE:
            {
                // Skip the backquoted node 
                checkCALDocCommentsInApplication(parseTree.firstChild());
                break;
            }
            
            case CALTreeParserTokenTypes.APPLICATION:
            {
                checkCALDocCommentsInApplication(parseTree);
                break;
            }

            //variables and functions
            case CALTreeParserTokenTypes.QUALIFIED_VAR:
            //data constructors 
            case CALTreeParserTokenTypes.QUALIFIED_CONS:
            //literals
            case CALTreeParserTokenTypes.CHAR_LITERAL :
            case CALTreeParserTokenTypes.INTEGER_LITERAL :
            case CALTreeParserTokenTypes.FLOAT_LITERAL :
            case CALTreeParserTokenTypes.STRING_LITERAL : 
            {
                // nothing to do
                break;
            }

            case CALTreeParserTokenTypes.LIST_CONSTRUCTOR :
            {
                for (final ParseTreeNode elementNode : parseTree) {

                    checkCALDocCommentsInExpr(elementNode);
                }
                break;
            }
            
            case CALTreeParserTokenTypes.TUPLE_CONSTRUCTOR :
            {
                for (final ParseTreeNode componentNode : parseTree) {
                                                     
                    checkCALDocCommentsInExpr(componentNode);
                }
                break;
            }
           
            case CALTreeParserTokenTypes.RECORD_CONSTRUCTOR :
            {
                ParseTreeNode baseRecordNode = parseTree.firstChild();
                baseRecordNode.verifyType(CALTreeParserTokenTypes.BASE_RECORD);           
                
                ParseTreeNode baseRecordExprNode = baseRecordNode.firstChild();
                if (baseRecordExprNode != null) {
                    checkCALDocCommentsInExpr(baseRecordExprNode);
                }
                
                ParseTreeNode fieldModificationListNode = baseRecordNode.nextSibling();
                fieldModificationListNode.verifyType(CALTreeParserTokenTypes.FIELD_MODIFICATION_LIST);
                
                for (final ParseTreeNode fieldModificationNode : fieldModificationListNode) {
                    
                    fieldModificationNode.verifyType(CALTreeParserTokenTypes.FIELD_EXTENSION,
                        CALTreeParserTokenTypes.FIELD_VALUE_UPDATE);
                    
                    ParseTreeNode fieldNameNode = fieldModificationNode.firstChild();
                    
                    ParseTreeNode valueExprNode = fieldNameNode.nextSibling();
                    
                    checkCALDocCommentsInExpr(valueExprNode);
                }
                break;
            }
            
            case CALTreeParserTokenTypes.SELECT_DATA_CONSTRUCTOR_FIELD :
            {
                checkCALDocCommentsInDataConstructorFieldSelection(parseTree);
                break;
            }
                
            case CALTreeParserTokenTypes.SELECT_RECORD_FIELD:
            {
                ParseTreeNode exprNode = parseTree.firstChild();
                checkCALDocCommentsInExpr(exprNode);                
                break;
            }
            
            case CALTreeParserTokenTypes.EXPRESSION_TYPE_SIGNATURE:
            {
                ParseTreeNode exprNode = parseTree.firstChild();
                checkCALDocCommentsInExpr(exprNode);
                break;
            }

            default :
            {            
                parseTree.unexpectedParseTreeNode();               
                break;
            }
        }
    }
    
    /**
     * Checks the CALDoc comments within the specified application expression. The checks
     * include verifying that identifiers contained within the comment are indeed
     * resolvable to entities within the module and/or other imported modules.
     * 
     * @param parseTree the application expression node whose subtree is to be checked.
     */
    private void checkCALDocCommentsInApplication(ParseTreeNode parseTree) {
               
        for (final ParseTreeNode exprNode : parseTree) {
            checkCALDocCommentsInExpr(exprNode);
        }
    }
    
    /**
     * Checks the CALDoc comments within the specified record case. The checks
     * include verifying that identifiers contained within the comment are indeed
     * resolvable to entities within the module and/or other imported modules.
     * 
     * @param recordCaseNode the record case node whose subtree is to be checked.
     */
    private void checkCALDocCommentsInRecordCase(ParseTreeNode recordCaseNode) {
        
        recordCaseNode.verifyType(CALTreeParserTokenTypes.VIRTUAL_RECORD_CASE);

        ParseTreeNode conditionNode = recordCaseNode.firstChild();
        checkCALDocCommentsInExpr(conditionNode);

        ParseTreeNode altListNode = conditionNode.nextSibling();
        altListNode.verifyType(CALTreeParserTokenTypes.ALT_LIST);
              
        if (!altListNode.hasExactlyOneChild()) {
            //record-case patterns have only 1 alternative. This should be caught earlier in static analysis.
            throw new IllegalArgumentException();
        }
        
        ParseTreeNode altNode = altListNode.firstChild();       
        altNode.verifyType(CALTreeParserTokenTypes.ALT);

        ParseTreeNode patternNode = altNode.firstChild();
        patternNode.verifyType(CALTreeParserTokenTypes.RECORD_PATTERN);

        ParseTreeNode exprNode = patternNode.nextSibling();
        checkCALDocCommentsInExpr(exprNode);
    }
    
    /**
     * Checks the CALDoc comments within the specified data constructor case. The checks
     * include verifying that identifiers contained within the comment are indeed
     * resolvable to entities within the module and/or other imported modules.
     * 
     * @param caseNode the data constructor case node whose subtree is to be checked.
     */
    private void checkCALDocCommentsInDataConstructorCase(ParseTreeNode caseNode) {
        
        caseNode.verifyType(CALTreeParserTokenTypes.VIRTUAL_DATA_CONSTRUCTOR_CASE);

        ParseTreeNode conditionNode = caseNode.firstChild();
        checkCALDocCommentsInExpr(conditionNode);

        ParseTreeNode altListNode = conditionNode.nextSibling();
        altListNode.verifyType(CALTreeParserTokenTypes.ALT_LIST);

        for (final ParseTreeNode altNode : altListNode) {

            altNode.verifyType(CALTreeParserTokenTypes.ALT);

            ParseTreeNode patternNode = altNode.firstChild();
            ParseTreeNode exprNode = patternNode.nextSibling();
            checkCALDocCommentsInExpr(exprNode);
        }
    }
    
    /**
     * Checks the CALDoc comments within the specified tupe case. The checks
     * include verifying that identifiers contained within the comment are indeed
     * resolvable to entities within the module and/or other imported modules.
     * 
     * @param caseNode the tuple case node whose subtree is to be checked.
     */
    private void checkCALDocCommentsInTupleCase(ParseTreeNode caseNode) {
        
        caseNode.verifyType(CALTreeParserTokenTypes.VIRTUAL_TUPLE_CASE);

        ParseTreeNode conditionNode = caseNode.firstChild();
        checkCALDocCommentsInExpr(conditionNode);

        ParseTreeNode altListNode = conditionNode.nextSibling();
        altListNode.verifyType(CALTreeParserTokenTypes.ALT_LIST);
        
        if (!altListNode.hasExactlyOneChild()) {
            //tuple-case patterns have only 1 alternative. This should be caught earlier in static analysis.
            throw new IllegalArgumentException();
        }      

        ParseTreeNode altNode = altListNode.firstChild();            
        altNode.verifyType(CALTreeParserTokenTypes.ALT);

        ParseTreeNode patternNode = altNode.firstChild();
        patternNode.verifyType(CALTreeParserTokenTypes.TUPLE_CONSTRUCTOR);
        
        ParseTreeNode exprNode = patternNode.nextSibling();
        checkCALDocCommentsInExpr(exprNode);
    }
    
    /**
     * Checks the CALDoc comments within the specified data constructor field selection. The checks
     * include verifying that identifiers contained within the comment are indeed
     * resolvable to entities within the module and/or other imported modules.
     * 
     * @param selectNode the field selection node whose subtree is to be checked.
     */
    private void checkCALDocCommentsInDataConstructorFieldSelection(ParseTreeNode selectNode) {
        
        selectNode.verifyType(CALTreeParserTokenTypes.SELECT_DATA_CONSTRUCTOR_FIELD);
        
        ParseTreeNode conditionNode = selectNode.firstChild();
        checkCALDocCommentsInExpr(conditionNode);
        
        ParseTreeNode dcNameNode = conditionNode.nextSibling();
        dcNameNode.verifyType(CALTreeParserTokenTypes.QUALIFIED_CONS);
        
        ParseTreeNode fieldNameNode = dcNameNode.nextSibling();
        fieldNameNode.verifyType(CALTreeParserTokenTypes.VAR_ID, CALTreeParserTokenTypes.ORDINAL_FIELD_NAME);
        
        ParseTreeNode qualifiedVarNode = fieldNameNode.nextSibling();
        qualifiedVarNode.verifyType(CALTreeParserTokenTypes.QUALIFIED_VAR);

        checkCALDocCommentsInExpr(qualifiedVarNode);
    }
    
    /**
     * Checks the CALDoc comments within the specified let expression. The checks
     * include verifying that identifiers contained within the comment are indeed
     * resolvable to entities within the module and/or other imported modules.
     * 
     * @param letNode the let expression node whose subtree is to be checked.
     */
    private void checkCALDocCommentsInLet(ParseTreeNode letNode) {
        ParseTreeNode defnListNode = letNode.firstChild();
        defnListNode.verifyType(CALTreeParserTokenTypes.LET_DEFN_LIST);
        
        Map<String, ParseTreeNode> funcNamesToDefnNodes = new HashMap<String, ParseTreeNode>();
        Set<String> functionsWithTypeDecls = new HashSet<String>();

        // First pass: gather the function definition nodes into a map,
        // and the names of the functions with type declarations into a set.
        for (final ParseTreeNode defnNode : defnListNode) {

            defnNode.verifyType(CALTreeParserTokenTypes.LET_DEFN, CALTreeParserTokenTypes.LET_DEFN_TYPE_DECLARATION);
            
            if (defnNode.getType() == CALTreeParserTokenTypes.LET_DEFN) {
                ParseTreeNode optionalCALDocNode = defnNode.firstChild();
                optionalCALDocNode.verifyType(CALTreeParserTokenTypes.OPTIONAL_CALDOC_COMMENT);
                
                ParseTreeNode localFunctionNameNode = optionalCALDocNode.nextSibling();
                String functionName = localFunctionNameNode.getText();
    
                ParseTreeNode paramListNode = localFunctionNameNode.nextSibling();
                paramListNode.verifyType(CALTreeParserTokenTypes.FUNCTION_PARAM_LIST);
                
                ParseTreeNode definingExprNode = paramListNode.nextSibling();
                checkCALDocCommentsInExpr(definingExprNode);

                funcNamesToDefnNodes.put(functionName, defnNode);
                
            } else if (defnNode.getType() == CALTreeParserTokenTypes.LET_DEFN_TYPE_DECLARATION) {
                
                ParseTreeNode optionalCALDocNode = defnNode.firstChild();
                optionalCALDocNode.verifyType(CALTreeParserTokenTypes.OPTIONAL_CALDOC_COMMENT);
                
                ParseTreeNode typeDeclNode = optionalCALDocNode.nextSibling();
                typeDeclNode.verifyType(CALTreeParserTokenTypes.TYPE_DECLARATION);
                
                String functionName = typeDeclNode.firstChild().getText();
                
                functionsWithTypeDecls.add(functionName);
            }
        }

        // Second pass: perform the actual checking of the CALDoc comments
        for (final ParseTreeNode defnNode : defnListNode) {

            if (defnNode.getType() == CALTreeParserTokenTypes.LET_DEFN) {
                ParseTreeNode optionalCALDocNode = defnNode.firstChild();
                optionalCALDocNode.verifyType(CALTreeParserTokenTypes.OPTIONAL_CALDOC_COMMENT);
                
                ParseTreeNode localFunctionNameNode = optionalCALDocNode.nextSibling();
                String functionName = localFunctionNameNode.getText();
    
                ParseTreeNode paramListNode = localFunctionNameNode.nextSibling();
                paramListNode.verifyType(CALTreeParserTokenTypes.FUNCTION_PARAM_LIST);

                // if we have previously encountered a type declaration for this function
                // in the first pass, then we require that the CALDoc comment be associated
                // with the type declaration and not with the function definition.
                if (functionsWithTypeDecls.contains(functionName)) {
                    if (optionalCALDocNode.firstChild() != null) {
                        compiler.logMessage(
                            new CompilerMessage(
                                optionalCALDocNode.firstChild(),
                                new MessageKind.Error.CALDocCommentForAlgebraicFunctionMustAppearBeforeTypeDeclaration(functionName)));
                    }
                } else {
                    // the information on the function's type is stored as
                    // data on the optionalCALDocNode associated with the function definition.
                    
                    // we use the information in the type to determine how many @arg tags
                    // is allowed to be in the CALDoc comment.
                    int nTopLevelArrowsInType = optionalCALDocNode.getFunctionTypeForLocalFunctionCALDocComment().getArity();
                    CALDocComment calDocComment = checkAndBuildCALDocComment(optionalCALDocNode, paramListNode, nTopLevelArrowsInType, true, true, false);
                    
                    LocalFunctionIdentifier identifier = typeChecker.getLocalFunctionIdentifier(functionName);
                    if(identifier != null) {
                        Function toplevelFunction = currentModuleTypeInfo.getFunction(identifier.getToplevelFunctionName().getUnqualifiedName());
                        Function localFunction = toplevelFunction.getLocalFunction(identifier);
                        if(localFunction != null) {
                            localFunction.setCALDocComment(calDocComment);
                        }
                    }
                }
                
            } else if (defnNode.getType() == CALTreeParserTokenTypes.LET_DEFN_TYPE_DECLARATION) {
                
                ParseTreeNode optionalCALDocNode = defnNode.firstChild();
                optionalCALDocNode.verifyType(CALTreeParserTokenTypes.OPTIONAL_CALDOC_COMMENT);
                
                ParseTreeNode typeDeclNode = optionalCALDocNode.nextSibling();
                typeDeclNode.verifyType(CALTreeParserTokenTypes.TYPE_DECLARATION);
                
                String functionName = typeDeclNode.firstChild().getText();
                
                // we need to fetch the function definition node corresponding to this
                // type declaration so that we can check the @arg tags against
                // the declared parameters of the function.
                ParseTreeNode funcDefnNode = funcNamesToDefnNodes.get(functionName);
                
                if (funcDefnNode == null) {
                    compiler.logMessage(new CompilerMessage(defnNode, new MessageKind.Error.DefinitionMissing(functionName)));
                    
                } else {
                    ParseTreeNode funcDefnOptionalCALDocNode = funcDefnNode.firstChild();
                    funcDefnOptionalCALDocNode.verifyType(CALTreeParserTokenTypes.OPTIONAL_CALDOC_COMMENT);
                    
                    ParseTreeNode localFunctionNameNode = funcDefnOptionalCALDocNode.nextSibling();
                    
                    // get the node containing the parameters of the function (for checking against the @arg tags).
                    ParseTreeNode paramListNode = localFunctionNameNode.nextSibling();
                    paramListNode.verifyType(CALTreeParserTokenTypes.FUNCTION_PARAM_LIST);                        
                    
                    // the information on the function's type is stored as
                    // data on the optionalCALDocNode associated with the function definition.
                    
                    // we use the information in the type to determine how many @arg tags
                    // is allowed to be in the CALDoc comment.
                    int nTopLevelArrowsInType = funcDefnOptionalCALDocNode.getFunctionTypeForLocalFunctionCALDocComment().getArity();
                    CALDocComment calDocComment = checkAndBuildCALDocComment(optionalCALDocNode, paramListNode, nTopLevelArrowsInType, true, true, false);

                    LocalFunctionIdentifier identifier = typeChecker.getLocalFunctionIdentifier(functionName);
                    if(identifier != null) {
                        Function toplevelFunction = currentModuleTypeInfo.getFunction(identifier.getToplevelFunctionName().getUnqualifiedName());
                        Function localFunction = toplevelFunction.getLocalFunction(identifier);
                        if(localFunction != null) {
                            localFunction.setCALDocComment(calDocComment);
                        }
                    }
                }
            }
        }   
        
        ParseTreeNode inExprNode = defnListNode.nextSibling();
        if (inExprNode != null) {
            checkCALDocCommentsInExpr(inExprNode);
        }
    }

    /**
     * Checks the CALDoc comments within the specified foreign function definition. The checks
     * include verifying that identifiers contained within the comment are indeed
     * resolvable to entities within the module and/or other imported modules.
     * 
     * @param foreignFunctionDeclarationNode the foreign function definition node whose subtree is to be checked.
     */
    private void checkCALDocCommentsInForeignFunctionDefn(ParseTreeNode foreignFunctionDeclarationNode) {
        
        foreignFunctionDeclarationNode.verifyType(CALTreeParserTokenTypes.FOREIGN_FUNCTION_DECLARATION);
                               
        ParseTreeNode optionalCALDocNode = foreignFunctionDeclarationNode.firstChild();
        optionalCALDocNode.verifyType(CALTreeParserTokenTypes.OPTIONAL_CALDOC_COMMENT);
    
        ParseTreeNode externalNameNode = optionalCALDocNode.nextSibling();
        externalNameNode.verifyType(CALTreeParserTokenTypes.STRING_LITERAL);
                                                    
        ParseTreeNode accessModifierNode = externalNameNode.nextSibling();
        accessModifierNode.verifyType(CALTreeParserTokenTypes.ACCESS_MODIFIER);                 
        
        ParseTreeNode typeDeclarationNode = accessModifierNode.nextSibling(); 
        typeDeclarationNode.verifyType(CALTreeParserTokenTypes.TYPE_DECLARATION);
                                                                                                                       
        ParseTreeNode functionNameNode = typeDeclarationNode.firstChild();
        functionNameNode.verifyType(CALTreeParserTokenTypes.VAR_ID);             
        String functionName = functionNameNode.getText();
        
        // fetch the entity and then the type expression from the entity associated with the foreign function.
        FunctionalAgent entity = currentModuleTypeInfo.getFunction(functionName);
        if (entity == null) {
            String displayName = getQualifiedNameDisplayString(functionNameNode);
            // TypeChecker: unknown function or variable {displayName}.
            compiler.logMessage(new CompilerMessage(functionNameNode, new MessageKind.Error.UnknownFunctionOrVariable(displayName)));
            
        } else {
            TypeExpr typeExpr = entity.getTypeExpr();
    
            // check the foreign function's CALDoc with the function's type expression.
            CALDocComment caldoc = checkAndBuildCALDocComment(optionalCALDocNode, null, typeExpr, true, true, false);
            
            // associate the CALDoc with the entity
            entity.setCALDocComment(caldoc);
        }
    }

    /**
     * Checks the CALDoc comments within the specified primitive function definition. The checks
     * include verifying that identifiers contained within the comment are indeed
     * resolvable to entities within the module and/or other imported modules.
     * 
     * @param primitiveFunctionNode the primitive function definition node whose subtree is to be checked.
     */
    private void checkCALDocCommentsInPrimitiveFunctionDefn(ParseTreeNode primitiveFunctionNode) {
        
        primitiveFunctionNode.verifyType(CALTreeParserTokenTypes.PRIMITIVE_FUNCTION_DECLARATION);
        
        ParseTreeNode optionalCALDocNode = primitiveFunctionNode.firstChild();
        optionalCALDocNode.verifyType(CALTreeParserTokenTypes.OPTIONAL_CALDOC_COMMENT);
    
        ParseTreeNode accessModifierNode = optionalCALDocNode.nextSibling();
        accessModifierNode.verifyType(CALTreeParserTokenTypes.ACCESS_MODIFIER);                 
        
        ParseTreeNode typeDeclarationNode = accessModifierNode.nextSibling();  
        typeDeclarationNode.verifyType(CALTreeParserTokenTypes.TYPE_DECLARATION);                            
                                                                                                         
        ParseTreeNode functionNameNode = typeDeclarationNode.firstChild();
        functionNameNode.verifyType(CALTreeParserTokenTypes.VAR_ID);             
        String functionName = functionNameNode.getText();
        
        // fetch the entity and then the type expression from the entity associated with the primitive function.
        FunctionalAgent entity = currentModuleTypeInfo.getFunction(functionName);
        if (entity == null) {
            String displayName = getQualifiedNameDisplayString(functionNameNode);
            // TypeChecker: unknown function or variable {displayName}.
            compiler.logMessage(new CompilerMessage(functionNameNode, new MessageKind.Error.UnknownFunctionOrVariable(displayName)));
            
        } else {
            TypeExpr typeExpr = entity.getTypeExpr();
            
            // check the primitive function's CALDoc with the function's type expression.
            CALDocComment caldoc = checkAndBuildCALDocComment(optionalCALDocNode, null, typeExpr, true, true, false);
            
            // associate the CALDoc with the entity
            entity.setCALDocComment(caldoc);
        }
    }

    /**
     * Checks the CALDoc comments within the specified algebraic type definition. The checks
     * include verifying that identifiers contained within the comment are indeed
     * resolvable to entities within the module and/or other imported modules.
     * 
     * @param algebraicTypeDefnNode the algebraic type definition node whose subtree is to be checked.
     */
    private void checkCALDocCommentsInAlgebraicTypeDefn(ParseTreeNode algebraicTypeDefnNode) {
        
        algebraicTypeDefnNode.verifyType(CALTreeParserTokenTypes.DATA_DECLARATION);
        
        ParseTreeNode optionalCALDocNode = algebraicTypeDefnNode.firstChild();
        // check the class's CALDoc comment.
        CALDocComment caldoc = checkAndBuildCALDocComment(optionalCALDocNode, null, null, false, false, false);
    
        // check the children.
        ParseTreeNode accessModifierNode = optionalCALDocNode.nextSibling();
        accessModifierNode.verifyType(CALTreeParserTokenTypes.ACCESS_MODIFIER);
        
        ParseTreeNode typeNameNode = accessModifierNode.nextSibling();
        typeNameNode.verifyType(CALTreeParserTokenTypes.CONS_ID);
    
        ParseTreeNode typeParamListNode = typeNameNode.nextSibling();
        typeParamListNode.verifyType(CALTreeParserTokenTypes.TYPE_CONS_PARAM_LIST);
        
        ParseTreeNode dataConsDefnListNode = typeParamListNode.nextSibling();
        dataConsDefnListNode.verifyType(CALTreeParserTokenTypes.DATA_CONSTRUCTOR_DEFN_LIST);
        
        for (final ParseTreeNode dataConsDefnNode : dataConsDefnListNode) {
            
            checkCALDocCommentsInDataConsDefn(dataConsDefnNode);            
        }
        
        // associate the CALDoc with the entity
        currentModuleTypeInfo.getTypeConstructor(typeNameNode.getText()).setCALDocComment(caldoc);
    }

    /**
     * Checks the CALDoc comments within the specified data constructor definition. The checks
     * include verifying that identifiers contained within the comment are indeed
     * resolvable to entities within the module and/or other imported modules.
     * 
     * @param dataConsDefnNode the data constructor definition node whose subtree is to be checked.
     */
    private void checkCALDocCommentsInDataConsDefn(ParseTreeNode dataConsDefnNode) {
    
        dataConsDefnNode.verifyType(CALTreeParserTokenTypes.DATA_CONSTRUCTOR_DEFN);
    
        ParseTreeNode optionalCALDocNode = dataConsDefnNode.firstChild();
        optionalCALDocNode.verifyType(CALTreeParserTokenTypes.OPTIONAL_CALDOC_COMMENT);
    
        ParseTreeNode scopeNode = optionalCALDocNode.nextSibling();
        scopeNode.verifyType(CALTreeParserTokenTypes.ACCESS_MODIFIER);
              
        ParseTreeNode dataConsNameNode = scopeNode.nextSibling();
        dataConsNameNode.verifyType(CALTreeParserTokenTypes.CONS_ID);
    
        ParseTreeNode dataConsArgListNode = dataConsNameNode.nextSibling();
        dataConsArgListNode.verifyType(CALTreeParserTokenTypes.DATA_CONSTRUCTOR_ARG_LIST);
              
        CALDocComment caldoc = checkAndBuildCALDocComment(optionalCALDocNode, dataConsArgListNode, dataConsArgListNode.getNumberOfChildren(), true, false, true);
        
        // associate the CALDoc with the entity
        (currentModuleTypeInfo.getDataConstructor(dataConsNameNode.getText())).setCALDocComment(caldoc);
    }

    /**
     * Checks the CALDoc comments within the specified foreign type definition. The checks
     * include verifying that identifiers contained within the comment are indeed
     * resolvable to entities within the module and/or other imported modules.
     * 
     * @param foreignTypeDefnNode the foreign type definition node whose subtree is to be checked.
     */
    private void checkCALDocCommentsInForeignTypeDefn(ParseTreeNode foreignTypeDefnNode) {
        
        foreignTypeDefnNode.verifyType(CALTreeParserTokenTypes.FOREIGN_DATA_DECLARATION);
        
        ParseTreeNode optionalCALDocNode = foreignTypeDefnNode.firstChild();
        CALDocComment caldoc = checkAndBuildCALDocComment(optionalCALDocNode, null, null, false, false, false);
        
        ParseTreeNode implementationScopeNode = optionalCALDocNode.nextSibling();
        implementationScopeNode.verifyType(CALTreeParserTokenTypes.ACCESS_MODIFIER);  
    
        ParseTreeNode externalNameNode = implementationScopeNode.nextSibling();
        externalNameNode.verifyType(CALTreeParserTokenTypes.STRING_LITERAL);                                           
                      
        ParseTreeNode accessModifierNode = externalNameNode.nextSibling();
        accessModifierNode.verifyType(CALTreeParserTokenTypes.ACCESS_MODIFIER);                 
        
        ParseTreeNode typeNameNode = accessModifierNode.nextSibling();
        typeNameNode.verifyType(CALTreeParserTokenTypes.CONS_ID);                                   
        String typeName = typeNameNode.getText();
        
        // associate the CALDoc with the entity
        currentModuleTypeInfo.getTypeConstructor(typeName).setCALDocComment(caldoc);
    }

    /**
     * Checks the CALDoc comments within the specified type class definition. The checks
     * include verifying that identifiers contained within the comment are indeed
     * resolvable to entities within the module and/or other imported modules.
     * 
     * @param typeClassDefnNode the type class definition node whose subtree is to be checked.
     */
    private void checkCALDocCommentsInTypeClassDefn(ParseTreeNode typeClassDefnNode) {
        
        typeClassDefnNode.verifyType(CALTreeParserTokenTypes.TYPE_CLASS_DEFN);      
        
        ParseTreeNode optionalCALDocNode = typeClassDefnNode.firstChild();
        // check the instance's CALDoc comment.
        CALDocComment caldoc = checkAndBuildCALDocComment(optionalCALDocNode, null, null, false, false, false);
    
        // check the children.
        ParseTreeNode scopeNode = optionalCALDocNode.nextSibling();
        scopeNode.verifyType(CALTreeParserTokenTypes.ACCESS_MODIFIER);
        
        ParseTreeNode contextListNode = scopeNode.nextSibling();
        contextListNode.verifyType(CALTreeParserTokenTypes.CLASS_CONTEXT_LIST, CALTreeParserTokenTypes.CLASS_CONTEXT_SINGLETON, CALTreeParserTokenTypes.CLASS_CONTEXT_NOTHING);
        
        ParseTreeNode typeClassNameNode = contextListNode.nextSibling();
        typeClassNameNode.verifyType(CALTreeParserTokenTypes.CONS_ID);              
        
        ParseTreeNode typeVarNode = typeClassNameNode.nextSibling();
        typeVarNode.verifyType(CALTreeParserTokenTypes.VAR_ID);
        
        ParseTreeNode classMethodListNode = typeVarNode.nextSibling();
        classMethodListNode.verifyType(CALTreeParserTokenTypes.CLASS_METHOD_LIST);
        
        String className = typeClassNameNode.getText();                       
        TypeClass typeClass = currentModuleTypeInfo.getTypeClass(className);
        
        for (final ParseTreeNode classMethodNode : classMethodListNode) {
            
            checkCALDocCommentsInClassMethodDefn(classMethodNode, typeClass);
        }
        
        // associate the CALDoc with the entity
        typeClass.setCALDocComment(caldoc);
    }

    /**
     * Checks the CALDoc comments within the specified class method definition. The checks
     * include verifying that identifiers contained within the comment are indeed
     * resolvable to entities within the module and/or other imported modules.
     * 
     * @param classMethodNode the class method definition node whose subtree is to be checked.
     * @param typeClass the type class entity.
     */
    private void checkCALDocCommentsInClassMethodDefn(ParseTreeNode classMethodNode, TypeClass typeClass) {
    
        classMethodNode.verifyType(CALTreeParserTokenTypes.CLASS_METHOD);  
        
        ParseTreeNode optionalCALDocNode = classMethodNode.firstChild();
        
        ParseTreeNode scopeNode = optionalCALDocNode.nextSibling();
        scopeNode.verifyType(CALTreeParserTokenTypes.ACCESS_MODIFIER);
        
        ParseTreeNode classMethodNameNode = scopeNode.nextSibling();                     
        classMethodNameNode.verifyType(CALTreeParserTokenTypes.VAR_ID);
        String methodName = classMethodNameNode.getText();
        
        TypeExpr typeExpr = currentModuleTypeInfo.getClassMethod(methodName).getTypeExpr();
        
        // check the class method CALDoc with the type expression calculated from the declared type signature.
        CALDocComment caldoc = checkAndBuildCALDocComment(optionalCALDocNode, null, typeExpr, true, true, false);
        
        // associate the CALDoc with the entity
        typeClass.getClassMethod(methodName).setCALDocComment(caldoc);
    }

    /**
     * Constructs a ClassInstanceIdentifier from an instance type cons node.
     * 
     * @param instanceTypeConsNode the instance type cons node.
     * @param typeClassName the name of the type class of the instance being declared.
     * @return a new ClassInstanceIdentifier representing the name of the instance.
     */
    private ClassInstanceIdentifier buildClassInstanceIdentifier(ParseTreeNode instanceTypeConsNode, QualifiedName typeClassName) {
        
        switch(instanceTypeConsNode.getType()) {
            case CALTreeParserTokenTypes.GENERAL_TYPE_CONSTRUCTOR:
            case CALTreeParserTokenTypes.UNPARENTHESIZED_TYPE_CONSTRUCTOR:
            {
                ParseTreeNode typeConsNameNode = instanceTypeConsNode.firstChild();                
                return new ClassInstanceIdentifier.TypeConstructorInstance(typeClassName, typeConsNameNode.toQualifiedName());
            }
                
            case CALTreeParserTokenTypes.FUNCTION_TYPE_CONSTRUCTOR:            
            {
                return new ClassInstanceIdentifier.TypeConstructorInstance(typeClassName, CAL_Prelude.TypeConstructors.Function);
            }
            
            case CALTreeParserTokenTypes.UNIT_TYPE_CONSTRUCTOR:
            {               
                return new ClassInstanceIdentifier.TypeConstructorInstance(typeClassName, CAL_Prelude.TypeConstructors.Unit);                          
            }
            
            case CALTreeParserTokenTypes.LIST_TYPE_CONSTRUCTOR:           
            {
                return new ClassInstanceIdentifier.TypeConstructorInstance(typeClassName, CAL_Prelude.TypeConstructors.List);
            } 
            
            case CALTreeParserTokenTypes.RECORD_TYPE_CONSTRUCTOR:
            {
                return new ClassInstanceIdentifier.UniversalRecordInstance(typeClassName);
            }
            
            default: {
                instanceTypeConsNode.unexpectedParseTreeNode();
                return null;
            }
        }
    }

    /**
     * Checks the CALDoc comments within the specified instance definition. The checks
     * include verifying that identifiers contained within the comment are indeed
     * resolvable to entities within the module and/or other imported modules.
     * 
     * @param instanceDefnNode the instance definition node whose subtree is to be checked.
     */
    private void checkCALDocCommentsInInstanceDefn(ParseTreeNode instanceDefnNode) {
        
        instanceDefnNode.verifyType(CALTreeParserTokenTypes.INSTANCE_DEFN);
                
        ParseTreeNode optionalCALDocNode = instanceDefnNode.firstChild();
        // check the instance's CALDoc comment.
        CALDocComment caldoc = checkAndBuildCALDocComment(optionalCALDocNode, null, null, false, false, false);
    
        // check the children.
        ParseTreeNode instanceNameNode = optionalCALDocNode.nextSibling();
        instanceNameNode.verifyType(CALTreeParserTokenTypes.INSTANCE_NAME);
        
        ParseTreeNode contextListNode = instanceNameNode.firstChild();
        contextListNode.verifyType(CALTreeParserTokenTypes.CLASS_CONTEXT_LIST, CALTreeParserTokenTypes.CLASS_CONTEXT_SINGLETON, CALTreeParserTokenTypes.CLASS_CONTEXT_NOTHING);
        
        ParseTreeNode typeClassNameNode  = contextListNode.nextSibling();
        QualifiedName typeClassName = typeClassNameNode.toQualifiedName();
        
        ClassInstanceIdentifier instanceName = buildClassInstanceIdentifier(typeClassNameNode.nextSibling(), typeClassName);
        ClassInstance instance = currentModuleTypeInfo.getClassInstance(instanceName);
        
        // associate the CALDoc with the entity
        instance.setCALDocComment(caldoc);
                
        ParseTreeNode instanceMethodListNode = instanceNameNode.nextSibling();
        instanceMethodListNode.verifyType(CALTreeParserTokenTypes.INSTANCE_METHOD_LIST);
        
        for (final ParseTreeNode instanceMethodNode : instanceMethodListNode) {
            
            instanceMethodNode.verifyType(CALTreeParserTokenTypes.INSTANCE_METHOD);
            
            ParseTreeNode optionalInstanceMethodCALDocNode = instanceMethodNode.firstChild();
            optionalInstanceMethodCALDocNode.verifyType(CALTreeParserTokenTypes.OPTIONAL_CALDOC_COMMENT);
            
            ParseTreeNode instanceMethodNameNode = optionalInstanceMethodCALDocNode.nextSibling();
            instanceMethodNameNode.verifyType(CALTreeParserTokenTypes.VAR_ID);
            String instanceMethodName = instanceMethodNameNode.getText();
            
            ParseTreeNode instanceMethodDefnNode = instanceMethodNameNode.nextSibling();
            instanceMethodDefnNode.verifyType(CALTreeParserTokenTypes.QUALIFIED_VAR);
            
            // fetch the entity and then the type expression from the instance method's defining function.
            FunctionalAgent entity = retrieveQualifiedVar(instanceMethodDefnNode);
            if (entity == null) {
                String displayName = getQualifiedNameDisplayString(instanceMethodDefnNode);
                // TypeChecker: unknown function or variable {displayName}.
                compiler.logMessage(new CompilerMessage(instanceMethodDefnNode.getChild(1), new MessageKind.Error.UnknownFunctionOrVariable(displayName)));
            }
            
            TypeExpr typeExpr = entity.getTypeExpr();
    
            // check the instance method CALDoc with the defining function's type expression.
            CALDocComment methodCALDoc = checkAndBuildCALDocComment(optionalInstanceMethodCALDocNode, null, typeExpr, true, true, false);
            
            // associate the CALDoc with the entity
            instance.setMethodCALDocComment(instanceMethodName, methodCALDoc);
        }
    }

    /**
     * Constructs a CALDocComment.TextBlock from a parse tree node representing a text block
     * in a CALDoc comment.
     * 
     * @param blockNode the node representing the text block.
     * @param inPreformattedContext whether the text is in a preformatted context, and to be dealt with accordingly.
     * @param summaryCollector the SummaryCollector to use in building up the comment's summary.
     * @return a new CALDocComment.TextBlock object representing the text block.
     */
    private CALDocComment.TextBlock buildCALDocTextBlock(ParseTreeNode blockNode, boolean inPreformattedContext, CALDocComment.SummaryCollector summaryCollector) throws InvalidCALDocException {
        blockNode.verifyType(CALTreeParserTokenTypes.CALDOC_TEXT);
        
        return new CALDocComment.TextBlock(buildCALDocParagraphs(blockNode, inPreformattedContext, summaryCollector));
    }
    
    /**
     * Constructs a CALDocComment.TextBlock from a parse tree node representing a preformatted text segment in a CALDoc comment.
     * 
     * @param blockNode the ParseTreeNode representing a CALDoc preformatted text segment.
     * @param summaryCollector the SummaryCollector to use in building up the comment's summary.
     * @return a new CALDocComment.TextBlock representing the preformatted text segment.
     */
    private CALDocComment.TextBlock buildCALDocPreformattedBlock(ParseTreeNode blockNode, CALDocComment.SummaryCollector summaryCollector) throws InvalidCALDocException {
        blockNode.verifyType(CALTreeParserTokenTypes.CALDOC_TEXT_PREFORMATTED_BLOCK);
        
        return new CALDocComment.TextBlock(buildCALDocParagraphs(blockNode, true, summaryCollector));
    }
    
    /**
     * Constructs a CALDocComment.TextParagraph from a parse tree node whose children represent the text segments of the paragraph.
     * It is an error for the these segments to contain a paragraph break.
     * 
     * @param parentNodeOfSegments the ParseTreeNode whose children represent text segments in a CALDoc comment.
     * @param inPreformattedContext whether the text is in a preformatted context, and to be dealt with accordingly.
     * @param contextTagName the name of the enclosing inline tag segment, for error reporting purposes.
     * @param summaryCollector the SummaryCollector to use in building up the comment's summary.
     * @return a new CALDocComment.TextParagraph representing the single text paragraph.
     */
    private CALDocComment.TextParagraph checkAndBuildCALDocSingleTextParagraph(ParseTreeNode parentNodeOfSegments, boolean inPreformattedContext, String contextTagName, CALDocComment.SummaryCollector summaryCollector) throws InvalidCALDocException {
        List<CALDocComment.Segment> segments = new ArrayList<CALDocComment.Segment>();

        ////
        /// A plain text segment is represented as one or more of CALDOC_TEXT_LINE, CALDOC_BLANK_TEXT_LINE and
        /// CALDOC_TEXT_LINE_BREAK. We loop through the children of the supplied node aggregating contiguous blocks
        /// of these nodes into plain text segments. A CALDOC_TEXT_INLINE_BLOCK node represents an inline block and is
        /// handled as an independent segment.
        //
        
        ////
        /// In the algorithm below, we trim any excess whitespace and newlines at the start of the paragraph and
        /// at the start of each line.
        ///
        /// We accomplish that by keeping track of whether the paragraph is empty - we trim whitespace
        /// for as long as nothing has been aggregated into the paragraph.
        ///
        /// We skip this step if we're in a pre-formatted context, since whitespace is preserved in that case.
        //
        boolean startOfLine = true;
        StringBuilder plainSegmentBuffer = new StringBuilder();
        boolean paragraphIsEmpty = true;
        
        loop:
        for (final ParseTreeNode contentNode : parentNodeOfSegments) {
            
            switch (contentNode.getType()) {
            case CALTreeParserTokenTypes.CALDOC_TEXT_LINE:
            {
                String text = contentNode.getText();
                if (startOfLine && !inPreformattedContext) {
                    text = CALDocLexer.trimLeadingCALDocWhitespace(text);
                }
                
                plainSegmentBuffer.append(text);
                
                if (paragraphIsEmpty && text.length() > 0) {
                    paragraphIsEmpty = false;
                }
                
                startOfLine = false;
                break;
            }
                
            case CALTreeParserTokenTypes.CALDOC_BLANK_TEXT_LINE:
            {
                String text = contentNode.getText();
                if (!inPreformattedContext) {
                    if (startOfLine) {
                        text = "";
                    } else {
                        text = " ";
                    }
                }
                
                plainSegmentBuffer.append(text);
                
                if (paragraphIsEmpty && text.length() > 0) {
                    paragraphIsEmpty = false;
                }
                
                // we don't change start-of-line status here
                break;
            }
            
            case CALTreeParserTokenTypes.CALDOC_TEXT_LINE_BREAK:
            {
                // process newlines only if there's some text in the buffer already - we ignore initial newlines in a paragraph
       