package org.nhncorp.badugi;

import java.util.ArrayList;
import java.util.LinkedList;

import org.apache.poi.util.SystemOutLogger;
import org.nhncorp.badugi.betting.BettingType;


public class Game {
	
	public static final int NOT_EXISTS_EXCHANGE_CARD = -1;
	public static final int MORNING = 0;
	public static final int AFTERNOON = 1;
	public static final int EVENING = 2;
	public static final int HIDDEN = 3;
	
	private static final int MAX_USER_COUNT = 5;
	private static final int LAST_RACE_USER = 1;
	private static final int TURN_DIRECTION = 1;
	private static final int ROUND_LENGTH = 4;
	
	private ArrayList<User> userList;
	private int baseMoney;
	private Dealer dealer;
	private int bossIndex;
	private int currentTurnUserIndex;
	private int raceMoney;
	private int gameMoney;
	private int callDieCount;
	private int round;
	private int deadPersonCount;
	
	public Game(int baseMoney) {
		this.userList = new ArrayList<User>();
		this.baseMoney = baseMoney;
		this.bossIndex = currentTurnUserIndex = 0;
		this.gameMoney = 0;
		this.callDieCount = 0;
		this.raceMoney = 0;
		this.deadPersonCount = 0;
		this.round = MORNING;
	}
	
	public void start() {
		this.dealer = new Dealer(userList, bossIndex);
		this.gameMoney = takeBaseMoneyFromUsers();
	}
	
	private int takeBaseMoneyFromUsers() {
		
		int sumOfMoney = 0;
		for(User user : userList) {
			user.subtractMoney(baseMoney);
			sumOfMoney += baseMoney;
		}
		return sumOfMoney;
	}

	public boolean addUser(User user) {
		if(userList.size() < MAX_USER_COUNT 
				&& user.getMoney() >= baseMoney) {
			userList.add(user);
			return true;
		}
		return false;
	}

	public int getUserSize() {
		return userList.size();
	}

	public Dealer getDealer() {
		return dealer;
	}

	public ArrayList<User> getUserList() {
		return userList;
	}

	public int getBossIndex() {
		return bossIndex;
	}

	public void setBossIndex(int bossIndex) {
		this.bossIndex = bossIndex;
	}

	public User getCurrentTurnUser() {
		return userList.get(currentTurnUserIndex);
	}
	
	public void nextTurn() {
		int nextTurnUserIndex = (currentTurnUserIndex + TURN_DIRECTION) % getUserSize();
		User nextTurnUser = userList.get(nextTurnUserIndex);
		
		while (nextTurnUser == null || nextTurnUser.getIsDeadPlayer()) {
			nextTurnUserIndex = (currentTurnUserIndex + TURN_DIRECTION) % getUserSize();
			nextTurnUser = userList.get(nextTurnUserIndex);
		}
		
		this.currentTurnUserIndex = nextTurnUserIndex;
	}
	
	public void nextBettingTurn() {
		int callDieUserCount = ( getUserSize() - LAST_RACE_USER);
		
		if (this.callDieCount != callDieUserCount) {
			nextTurn();
		} else {
			// ????? ???? ??
		}
	}
	
	public void bet(BettingType bettingType) {
		bettingType.bet(this);
	}

	public boolean isExistsExchangeCards(ArrayList<Integer> cardIds) {
		User user = getCurrentTurnUser();
		UserDeck userDeck = user.getDeck();
		for (int i = 0; i < cardIds.size(); i++) {
			boolean isExistId = false;
			for (Card card : userDeck) {
				if (card.getId() != cardIds.get(i)) {
					isExistId = true;
				}
			}
			if (!isExistId) {
				return false;
			}
		}
		return true;
	}
	
	public boolean exchangeCards(ArrayList<Integer> cardIds) {
		User user = getCurrentTurnUser();
		UserDeck userDeck = user.getDeck();
		if (isExistsExchangeCards(cardIds)) {
			for (int i = 0; i < cardIds.size(); i++) {
				Card addCardToExchangedDeck = this.dealer.getCardById(cardIds.get(i));
				this.dealer.addExchangedDeck(addCardToExchangedDeck);

				userDeck.removeById(cardIds.get(i));
				
				Card receiveCard = this.dealer.removeDealerDeck();
				userDeck.add(receiveCard);
			}
			return true;
		}
		return false;
	}
	
	public boolean isExchangeCard(Card card, int exchangeCardNumber, int exchangeCardShape) {
		return (card.getNumber() == exchangeCardNumber && card.getShape() == exchangeCardShape);
	}
	
	public int getBaseMoney() {
		return baseMoney;
	}

	public void addGameMoney(int money) {
		gameMoney+=money;
	}
	public int getGameMoney() {
		return this.gameMoney;
	}
	public int getRaceMoney() {
		return raceMoney;
	}
	public void setRaceMoney(int money) {
		raceMoney = money;
	}
	public int getCallDieCount() {
		return this.callDieCount;
	}
	public void clearCallDieCount() {
		this.callDieCount = this.deadPersonCount;
	}
	public void encrementCallDieCount() {
		this.callDieCount++;
	}
	public void encrementDeadPersonCount() {
		this.deadPersonCount++;
	}
	public int getDeadPersonCount() {
		return this.deadPersonCount;
	}
	public int getRound() {
		return this.round;
	}
	public void setRound(int round) {
		this.round = round;
	}
	public void nextRound() {
		this.round = (this.round++) % ROUND_LENGTH;
	}
}
