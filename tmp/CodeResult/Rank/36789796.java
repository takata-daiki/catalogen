package com.clinkworks.carddeck.datatypes;

public enum Rank {
	Ace,
	Two,
	Three,
	Four,
	Five,
	Six,
	Seven,
	Eight,
	Nine,
	Ten,
	Jack,
	Queen,
	King,
	Joker;
	
	public static Rank[] getRanksWithoutJokers(){
		Rank[] ranks = Rank.values();
		Rank[] ranksWithoutJokers = new Rank[Rank.values().length - 1];
		for(int i = 0; i < ranksWithoutJokers.length; i++){
			ranksWithoutJokers[i] = ranks[i];
		}
		return ranksWithoutJokers;
	}
}
