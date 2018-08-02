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
import org.comsoft.juniprint.utils.WritableIntNumberPropRu;

public class FML implements FreeRefFunction {
	
	public static final FreeRefFunction instance = new FML();

	public ValueEval evaluate(ValueEval[] args, OperationEvaluationContext arg1) {
		int srcCellRow = arg1.getRowIndex();
		int srcCellCol = arg1.getColumnIndex();
		try {
			String paramValue = ""; // default
			ValueEval res = null;
			switch(args.length) {
				case 1:
					paramValue = evaluateStringArg(args[0], srcCellRow, srcCellCol);
					res = new StringEval(paramValue);
					break;
				default:
					res = ErrorEval.VALUE_INVALID;
			}
			return res;
		} catch (EvaluationException e) {
			return e.getErrorEval();
		}

	}
	
	protected String convert(String arg){
		if (arg != null){
			String s1 = arg = arg.trim();
			String[] s = s1.split(" +");
			if (s.length == 3 && s[0].length() > 0 && s[1].length() > 0 && s[2].length() > 0){
				String res = s[0] +" "+s[1].charAt(0) +"."+ s[2].charAt(0)+".";
				arg = res;
			}
		}
		return arg;
	}
	
	private static String evaluateStringArg(ValueEval arg, int srcCellRow, int srcCellCol) throws EvaluationException {
		ValueEval ve = OperandResolver.getSingleValue(arg, srcCellRow, (short) srcCellCol);
		return OperandResolver.coerceValueToString(ve);
	}
	
	public static void main(String[] arg){
		FML fml = new FML();
		System.out.println(fml.convert("Матвеев Алексей  Николаевич"));
	}

}
