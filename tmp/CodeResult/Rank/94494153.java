package de.vermeulen.backgroundactualization.app.Model;

/**
 * Created by Fokke Vermeulen on 16.06.14.
 */
public class Rank {

	private String name;
	private int rank;
	private int wins;
	private int points;

	public Rank(String name, int rank, int wins, int points) {
		this.name = name;
		this.rank = rank;
		this.wins = wins;
		this.points = points;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getRank() {
		return rank;
	}

	public void setRank(int rank) {
		this.rank = rank;
	}

	public int getWins() {
		return wins;
	}

	public void setWins(int wins) {
		this.wins = wins;
	}

	public int getPoints() {
		return points;
	}

	public void setPoints(int points) {
		this.points = points;
	}

	@Override
	public String toString() {
		return "Rank{" +
			   "name='" + name + '\'' +
			   ", rank=" + rank +
			   ", wins=" + wins +
			   ", points=" + points +
			   '}';
	}
}
