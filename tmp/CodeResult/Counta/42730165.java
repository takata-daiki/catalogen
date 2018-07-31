package a7.s100502016;

import java.lang.Math;

public class User {

	private int[] cardInHand = new int[5];

	private String cardType = new String("");
	private int cardTypeLevel;

	private int countJoker;
	private int countA;
	private int count2;
	private int countJ;
	private int countQ;
	private int countK;

	public void setCardInHand() {
		for (int i = 0; i < 5; i++) {
			int temp = (int) (Math.random() * 20);
			for (int j = 0; j <= i; j++) {
				if (temp == cardInHand[j])
					continue;
				else
					cardInHand[i] = temp;
			}
		}
	}

	public int getCardInHand(int i) {
		return cardInHand[i];
	}

	public void setCardType() {
		countJoker = countA = count2 = countJ = countQ = countK = 0;
		for (int i = 0; i < 5; i++) {
			if (getCardInHand(i) == 20) {
				countJoker++;
				break;
			} else if (getCardInHand(i) >= 0 && getCardInHand(i) <= 3)
				countA++;
			else if (getCardInHand(i) >= 4 && getCardInHand(i) <= 7)
				count2++;
			else if (getCardInHand(i) >= 8 && getCardInHand(i) <= 11)
				countJ++;
			else if (getCardInHand(i) >= 12 && getCardInHand(i) <= 15)
				countQ++;
			else if (getCardInHand(i) >= 16 && getCardInHand(i) <= 19)
				countK++;
		}

		if (countJoker >= 1) {
			cardType = "Joker";
			cardTypeLevel = 5;
		} else if (countA == 4 || count2 == 4 || countJ == 4 || countQ == 4
				|| countK == 4) {
			cardType = "Four_of_a_kind";
			cardTypeLevel = 4;
		} else if ((countA == 3 || count2 == 3 || countJ == 3 || countQ == 3 || countK == 3)
				&& (countA == 2 || count2 == 2 || countJ == 2 || countQ == 2 || countK == 2)) {
			cardType = "Full_House";
			cardTypeLevel = 3;
		} else if (countA == 3 || count2 == 3 || countJ == 3 || countQ == 3
				|| countK == 3) {
			cardType = "Three_of_kind";
			cardTypeLevel = 2;
		} else if (countA == 2 || count2 == 2 || countJ == 2 || countQ == 2
				|| countK == 2) {
			cardType = "Two_pairs";
			cardTypeLevel = 1;
		} else {
			cardType = "Nothing";
			cardTypeLevel = 0;
		}
	}

	public String getCardType() {
		return cardType;
	}
	public int getCardTypeLevel() {
		return cardTypeLevel;
	}

}
