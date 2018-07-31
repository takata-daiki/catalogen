package org.broadway.exceptions;

import org.apache.commons.logging.Log;

/**
 * This runtime exception is thrown when an fault occurs
 * during the evaluation of scanned resources.
 * @author vmatters
 *
 */
@SuppressWarnings("serial")
public class EvaluationException extends ABroadException{
	/**
	 * Returns a standard exception
	 * @param msg
	 */
	public EvaluationException(String msg) {
		super(msg);
	}
	
	/**
	 * Returns a logged exception
	 * @param msg
	 * @param log
	 */
	public EvaluationException(String msg, Log log) {
		super(msg, log);
	}
	
	/**
	 * Returns a standard exception
	 * @param ex
	 */
	public EvaluationException(Throwable ex) {
		super(ex);
	}
	
	/**
	 * Returns a logged exception
	 * @param ex
	 * @param log
	 */
	public EvaluationException(Throwable ex, Log log) {
		super(ex, log);
	}
	
	/**
	 * Returns a standard exception
	 * @param msg
	 * @param ex
	 */
	public EvaluationException(String msg, Throwable ex) {
		super(msg, ex);
	}
	
	/**
	 * Returns a logged exception
	 * @param msg
	 * @param ex
	 * @param log
	 */
	public EvaluationException(String msg, Throwable ex, Log log) {
		super(msg,ex,log);
	}

}
