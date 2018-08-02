/*-
 * #%L
 * uniprint port for java environment
 * %%
 * Copyright (C) 2012 - 2017 COMSOFT, JSC
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */
package org.comsoft.juniprint.userfunction;

import org.apache.poi.hssf.record.formula.eval.ErrorEval;
import org.apache.poi.hssf.record.formula.eval.EvaluationException;
import org.apache.poi.hssf.record.formula.eval.OperandResolver;
import org.apache.poi.hssf.record.formula.eval.StringEval;
import org.apache.poi.hssf.record.formula.eval.ValueEval;
import org.apache.poi.hssf.record.formula.functions.FreeRefFunction;
import org.apache.poi.ss.formula.OperationEvaluationContext;
import org.comsoft.juniprint.utils.WritableSummRuRUB;

public class Sum_Prop implements FreeRefFunction {
	public static final FreeRefFunction instance = new Sum_Prop();
	
	public ValueEval evaluate(ValueEval[] args, OperationEvaluationContext ec) {
		// TODO Auto-generated method stub
		int srcCellRow = ec.getRowIndex();
		int srcCellCol = ec.getColumnIndex();
		try {
			ValueEval res = null;
			switch(args.length) {
				case 2:
					Double param1 = evaluateDoubleArg(args[0], srcCellRow, srcCellCol);
//					String param2 = evaluateStringArg(args[1], srcCellRow, srcCellCol);
					res = new StringEval(WritableSummRuRUB._instance.numberToString(param1));
					break;
				default:
					res = ErrorEval.VALUE_INVALID;
			}
			return res;
		} catch (EvaluationException e) {
			return e.getErrorEval();
		}
	}
	private static double evaluateDoubleArg(ValueEval arg, int srcCellRow, int srcCellCol) throws EvaluationException {
		ValueEval ve = OperandResolver.getSingleValue(arg, srcCellRow, (short) srcCellCol);
		return OperandResolver.coerceValueToDouble(ve);
	}
	
	private static String evaluateStringArg(ValueEval arg, int srcCellRow, int srcCellCol) throws EvaluationException {
		ValueEval ve = OperandResolver.getSingleValue(arg, srcCellRow, (short) srcCellCol);
		return OperandResolver.coerceValueToString(ve);
	}
	

}
