/**
 * ITEC802 Assignment 1 blackjack
 * Department of Computing, Macquarie University
 * @author Pongsak Suvanpong pongsak.suvanpong@students.mq.edu.au
 * Copyright(c) 2012 by Pongsak Suvanpong
 * redistribute of this file must retain this block of this comment.
 * BSD license
 */

package psksvp.CardGameEngine;

import java.util.ArrayList;



/**
 * This enum contains type of Rank for standard 52 cards per deck describe in 
 * <a href="http://en.wikipedia.org/wiki/Standard_52-card_deck">http://en.wikipedia.org/wiki/Standard_52-card_deck</a> 
 * @author psksvp
 * @author Pongsak Suvanpong <psksvp@gmail.com>
 * @version 0.1
 * @since August 22, 2012
 * @see Card
 */
public enum Rank 
{
	//Rank and its default numeric values
    Ace (1),
    Two (2),
    Three (3),
    Four (4),
    Five (5),
    Six (6),
    Seven (7),
    Eight (8),
    Nine (9),
    Ten (10),
    Jack (10),
    Queen (10),
    King (10);
    
    
    private int numericValue;
    
    Rank(int n)
    {
      this.setNumericValue(n);
    }
    
    public int numbericValue()
    {
    	return this.numericValue;
    }
    
    public void setNumericValue(int n)
    {
    	this.numericValue = n;	
    }
    
	public static ArrayList<Rank> allRanks()
	{
		ArrayList<Rank> ranks = new ArrayList<Rank>();
		ranks.add(Rank.Ace);
		ranks.add(Rank.Two);
		ranks.add(Rank.Three);
		ranks.add(Rank.Four);
		ranks.add(Rank.Five);
		ranks.add(Rank.Six);
		ranks.add(Rank.Seven);
		ranks.add(Rank.Eight);
		ranks.add(Rank.Nine);
		ranks.add(Rank.Ten);
		ranks.add(Rank.Jack);
		ranks.add(Rank.Queen);
		ranks.add(Rank.King);
		return ranks;
	} 
}
