package recursion;

/** 
 *	La classe IntList implementa "in stile C"
 *  la struttura ricorsiva "Lista concatenata (di interi)".
 *  I metodi sono tutti statici.
 *
 @author Sara Sanna
 */

public class IntList {
	private int element;
  	private IntList next;


  /**
  	* Costruttore IntList con due parametri
  	*
  	@param el primo elemento della lista
  	*
  	@param lis il next e' una lista a sua volta
  	*/
  	public IntList(int el, IntList lis) {
    	element = el;
    	next = lis;
  	}


  /**
  	* Costruttore IntList con un solo parametro
 	*
  	@param el primo elemento della lista
  	*/
  	public IntList(int el) {
    	this(el, null);
  	}

  
  /**
  	* Restituisce il valore del first della lista
  	*
  	@param lis lista di cui restituire il first
  	*
  	@return rende il valore del first
  	*/
  	public static int first(IntList lis) {
    	if(lis == null) throw new IllegalArgumentException();
    	return lis.element;
  	}


  /**
  	* Restituisce il valore del next data la lista
  	*
  	@param lis lista di cui restituire il next
  	*
  	@return rende il valore del next
  	*/
  	public static IntList next(IntList lis) {
    	if(lis == null) throw new IllegalArgumentException();
    	return lis.next;
  	}


  /** 
 	* Restituisce la lunghezza della lista lis 
 	*
 	@param lis lista della quale viene calcolata la lunghezza
 	*
 	@return restituisce la lunghezza della lista passata come parametro del metodo
 	*/
  	public static int length(IntList lis) {
    	return (lis==null) ? 0 : (1 + length(lis.next));
  	}


  /** 
 	* Restituisce la somma degli elementi della lista lis
 	*
 	@param lis lista della quale verranno sommati gli elementi
 	*
 	@return restituisce la somma degli elementi della lista
 	*/
  	public static int sum(IntList lis) {
    	return (lis==null) ? 0 : (lis.element + sum(lis.next));
  	}


  /** 
  	* Restituisce true se le liste lis1 e lis2 sono uguali,
  	* false altrimenti.
  	@param lis1 prima lista input per il confronto
  	*
  	@param lis2 lista da confrontare con la prima
  	*
  	@return rende true se le liste sono uguali altrimenti rende false
  	*/
  	public static boolean areEqual(IntList lis1, IntList lis2) {
		return (lis1==null && lis2==null) || (lis1!=null && lis2!=null && lis1.element==lis2.element && areEqual(lis1.next, lis2.next));
  	}
  	

  	/**
  	 * Stampa la lista
  	 *
  	 @param lis lista della quale si vogliono stampare i valori
  	 */
  	public static void stampaLista(IntList lis) {
  		if(lis != null) {
  			System.out.print(lis.element + " -> ");
  			stampaLista(lis.next);
  		}
  	}
}                                                                                                                                                           