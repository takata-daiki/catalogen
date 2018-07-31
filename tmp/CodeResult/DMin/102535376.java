/*
 *                    DO WHATEVER PUBLIC LICENSE
 *   TERMS AND CONDITIONS FOR COPYING, DISTRIBUTION AND MODIFICATION
 *
 * 0. You can do whatever you want to with the work.
 * 1. You cannot stop anybody from doing whatever they want to with the work.
 * 2. You cannot revoke anybody elses DO WHATEVER PUBLIC LICENSE in the work.
 *
 * This program is free software. It comes without any warranty, to
 * the extent permitted by applicable law. You can redistribute it
 * and/or modify it under the terms of the DO WHATEVER PUBLIC LICENSE
 * 
 * Software originally created by Justin Lloyd @ http://otakunozoku.com/
 *
 */
//import encode;
//import decode;

// class CBlackJack - the blackjack game

class CBlackJack {

	// State "macros"
// machine logic commands
	public	static	final	int	mlccBase							= 20000,
								COM_DEAL							= mlccBase + 0,
								COM_STAND							= mlccBase + 1,
								COM_HIT								= mlccBase + 2,
								COM_DOUBLE							= mlccBase + 3,
								COM_SPLIT							= mlccBase + 4,
								COM_INSURANCE						= mlccBase + 5,
								COM_NOINSURANCE						= mlccBase + 6,
								COM_RESET_TABLE						= mlccBase + 7,
								COM_PLAYGAME						= mlccBase + 8;

// machine logic reply codes
	public	static	final	int	mlsreplyBase						= 22000,
								mlsreplyNone						= mlsreplyBase + 0,
								mlsreplyOK							= mlsreplyBase + 1,
								mlsreplyError						= mlsreplyBase + 2,
								mlsreplyFailed						= mlsreplyBase + 3,
								IN_PLAY								= mlsreplyBase + 4,
								WIN									= mlsreplyBase + 5,
								LOSE								= mlsreplyBase + 6,
								BUST								= mlsreplyBase + 7,
								PUSH								= mlsreplyBase + 8,
								INSURANCE							= mlsreplyBase + 9,
								SPLIT								= mlsreplyBase + 10,
								FINISH_HAND							= mlsreplyBase + 11,
								FINISH_ALL							= mlsreplyBase + 12,
								BLACKJACK_WIN						= mlsreplyBase + 13,
								BLACKJACK_PUSH						= mlsreplyBase + 14;

// machine logic error codes
	public	static	final	int	mlserrorBase						= 24000,
								mlserrorNone						= mlserrorBase + 0,
								mlserrorTooManyParameters			= mlsreplyBase + 1,
								mlserrorUnknownGameCommand			= mlsreplyBase + 2 ;


// Server To Client reply messages
	public	static	final	int	gssreplyBase				= 2000,
								gssreplyServerConnect		= gssreplyBase + 0, // "Slot Machine Game Server v0.1 (beta)"
								gssreplyServerFull			= gssreplyBase + 1,
								gssreplyOK					= gssreplyBase + 2,
								gssreplyByeBye				= gssreplyBase + 3,
								gssreplyLoginOK				= gssreplyBase + 4,
								gssreplyLoginFailed			= gssreplyBase + 5 ;

	static final int		MAX_HANDS = 4;  // Maximum splits

	// data members
	int m_theDeck[] = new int[208];			// deck of cards
	int curcard = 0;						// top card of the deck
	private double m_balance;						// player balance
	private double m_bet;								// player bet
	private double dCasinoID;
	private double dSessionID;
	int m_dealer_hand[] = new int[20];		// dealer hand cards
	int m_player_upcard;					// last card dealt to player
	int m_player_downcard;					// 2nd to last card dealt to player - doesn't track previous cards
	int m_dealer_ncards;                     // how many cards does dealer have
	int m_player_splitcard[] = new int[MAX_HANDS + 1];  // The card that the player split with
	int m_player_ncards;                     // how many cards does player have
	int m_nhands;             				// how many hands
	double m_splitResult;             		// how much do you get back from split hands
	double m_splitBet;							// total bet of split	
	int m_hands;							// which hand are you playing in a split
	int m_dealer_value;						// dealer count
	int m_player_value[] = new int [MAX_HANDS + 1];           		// most for 16 split pairs from 1 to 16
	boolean m_blackjack_state[] = new boolean [MAX_HANDS + 1];		// blackjack in split hands
	boolean m_double_state[] = new boolean [MAX_HANDS + 1];		// double in split hands
	boolean m_dealer_soft;								// dealer has an ace counting as 11
    boolean m_player_soft;								// player "						  "
	int m_hands_result[] = new int[MAX_HANDS + 1];
	int byteStatePacket[] = new int[256];
	int	iStatePacketLength = 0;

// Actions allowed to the player
	boolean m_canDeal, 
			m_canHit,
			m_canStand, 
			m_canSplit, 
			m_canDouble, 
			m_canInsurance,
			m_cashedOut;

	static final int		GS_CAN_DEAL = 1,
							GS_CASHED_OUT = 2,
							GS_CAN_HIT = 4,
							GS_CAN_STAND = 8,
							GS_CAN_SPLIT = 16,
							GS_CAN_DOUBLE = 32,
							GS_CAN_INSURANCE = 64;

	static final double		dTableMins[] = {5.0, 25.0,150.0},
							dTableMaxs[] = {100.0, 500.0,1000.0},
							dStartBalance[] = {500.0, 1000.0, 2000.0};
	
	private double			dMIN,
							dMAX;

	static final int		VERSION_1 = 1,
							STANDALONE_CLIENT = 0,
							GAMESERVER_CLIENT = 1,
							MAX_GAME_NO = 2,
							MAX_TABLE_NO = 1;

/************************************************************************************
			CBlackJack() constructor
************************************************************************************/
	public CBlackJack()
	{
		m_balance = 0;
		InitData();
		shuffle_deck();
	}
/************************************************************************************
			void InitData()                initializes for each hand
************************************************************************************/
	void InitData()
	{
		m_bet = 0.0;
		m_dealer_ncards = 0;                     		// how many cards dealer has
		m_player_ncards = 0;                     		// how many cards player has
		m_nhands = 1;             						// how many hands
		m_splitResult = 0.0;
		m_splitBet = 0;
		m_hands = 1;
		m_dealer_value = 0;		

		for(int i = 1; i < MAX_HANDS + 1; i++)
		{
			m_blackjack_state[i] = false;
			m_double_state[i] = false;
			m_player_value[i] = 0;           			// most for 16 hands , from 1 to 16
		}
		m_dealer_soft = false;
		m_player_soft = false;
		m_canDeal = true;
		m_canHit = false;
		m_canStand = false;
		m_canSplit = false;
	    m_canDouble = false;
	    m_canInsurance = false;
		if(curcard < 30)                // shuffle point
			shuffle_deck();
	}

/************************************************************************************
			void shuffle_deck() 
************************************************************************************/

	private void shuffle_deck()
	{
		int m, i, j, k;

		curcard = 4 * 52;         // how many cards in this deck
		for(m = 0; m < curcard; m++)
		{
			m_theDeck[m] = m % 52;     // 0,1..51,0,1,...51,0,1...
		}	

		for (m = 0; m < (10 * curcard); m++)
		{
		/* swap two cards at random */
		i = (int) (Math.random() * curcard);
		j = (int) (Math.random() * curcard);
		k = m_theDeck[i];
		m_theDeck[i] = m_theDeck[j];
		m_theDeck[j] = k;
		} 
		curcard = curcard - 1;            // top card
	}

/************************************************************************************
			int deal_card()  returns the top card 
************************************************************************************/

	public int deal_card()
	{
		while(m_theDeck[curcard] < 0 || m_theDeck[curcard] > 51)
		{
//			TRACE("Deal a wrong card!");
			curcard--;
		}
		return m_theDeck[curcard--];
	}

/************************************************************************************
			int DealNextHand()  next hand for split
								returns state of game
************************************************************************************/

	public int DealNextHand()
	{
		m_player_ncards = 0;                     		// how many cards player has
		m_player_soft = false;
		m_canDeal = false;
		m_canHit = m_canStand = true;
		m_canSplit = false;
		m_canInsurance = false;

		m_hands++;
		
		m_player_downcard = m_player_splitcard[m_hands];
		add_player_card(m_player_downcard);	

		m_player_upcard = deal_card();
		add_player_card(m_player_upcard);

//		if(m_player_value[m_hands] == 21)
//		{
//			m_blackjack_state[m_hands] = true;
//			if(m_hands < m_nhands)
//				return FINISH_HAND;
//			return FINISH_ALL;
//		}

		if(m_player_upcard % 13 == m_player_downcard % 13 && m_nhands < MAX_HANDS)
		{
			m_canSplit = true;
		}

		if((m_player_value[m_hands] >= 9) && (m_player_value[m_hands] <= 11)) {
			m_canDouble = true;
			}

		if(m_player_soft) {
			int value2 = m_player_value[m_hands] - 10;
			if((value2 >= 9) && (value2 <= 11)) {
				m_canDouble = true;
				}
			}		
		m_canHit = m_canStand = true;
		return IN_PLAY;							// in play
	}

/************************************************************************************
			int Deal(double bet)  start another round
						returns the state of the game 
************************************************************************************/
	public int Deal(double bet)
	{
		if((bet < dMIN) || (bet > dMAX)) {
			return mlsreplyError;
			}
		if(bet > m_balance) {
			return mlsreplyError;
			}
		if(!m_canDeal) {
			return mlsreplyError;
			} 

		m_canHit = m_canStand = true;

		m_bet = bet;
		m_balance -= m_bet;

		m_canDeal = false;
		int result;

		m_player_downcard = deal_card();
		add_player_card(m_player_downcard);
		add_dealer_card(deal_card());

		m_player_upcard = deal_card();
		add_player_card(m_player_upcard);

		add_dealer_card(deal_card());
	
		if(m_player_value[m_hands] == 21)
		{
			m_blackjack_state[m_hands] = true;
			if(m_dealer_value != 21)
			{
				m_balance += m_bet * 2.5;
				result = WIN;                 	// BlackJack WIN
				byteStatePacket[9] = (byte) m_player_downcard;
				byteStatePacket[10] = (byte) m_player_upcard;
				byteStatePacket[11] = (byte) m_dealer_hand[0];
				byteStatePacket[12] = (byte) m_dealer_hand[1];
				byteStatePacket[13] = (byte) m_dealer_value;
				makeStatePacket(14);
			}
			else
			{
				m_balance += m_bet;
				result = PUSH;                 	// BlackJack Push
				byteStatePacket[9] = (byte) m_player_downcard;
				byteStatePacket[10] = (byte) m_player_upcard;
				byteStatePacket[11] = (byte) m_dealer_hand[0];
				byteStatePacket[12] = (byte) m_dealer_hand[1];
				makeStatePacket(13);
			}

			InitData();
			return result;
		}

		if((m_dealer_hand[1] % 13 == 0) && (m_balance >= m_bet / 2))            // dealer upcard is a ACE , enable insurance
		{
			m_canInsurance = true;
			m_canHit = m_canStand = false;
			byteStatePacket[9] = (byte) m_player_downcard;
			byteStatePacket[10] = (byte) m_player_upcard;
			byteStatePacket[11] = (byte) m_player_value[m_hands];
			byteStatePacket[12] = (byte) m_dealer_hand[1];
			makeStatePacket(13);
			return INSURANCE;
		}
		
		if(m_dealer_value == 21) { // Dealer Blackjack
				byteStatePacket[9] = (byte) m_player_downcard;
				byteStatePacket[10] = (byte) m_player_upcard;
				byteStatePacket[11] = (byte) m_dealer_hand[0];
				byteStatePacket[12] = (byte) m_dealer_hand[1];
				byteStatePacket[13] = (byte) m_player_value[1];
			InitData();
			makeStatePacket(14);
			return LOSE;			
			}
			
		if(m_player_upcard % 13 == m_player_downcard % 13)
		{
			m_canSplit = true;
		}

		if((m_player_value[m_hands] >= 9) && (m_player_value[m_hands] <= 11)) {
			m_canDouble = true;
			}

		if(m_player_soft) {
			int value2 = m_player_value[m_hands] - 10;
			if((value2 >= 9) && (value2 <= 11)) {
				m_canDouble = true;
				}
			}		

		byteStatePacket[9] = (byte) m_player_downcard;
		byteStatePacket[10] = (byte) m_player_upcard;
		byteStatePacket[11] = (byte) m_player_value[m_hands];
		byteStatePacket[12] = (byte) m_dealer_hand[1];
		makeStatePacket(13);

		return IN_PLAY;							// in play
	}
    
/************************************************************************************
			int Hit()   Player Hit
						returns the state of the game 
************************************************************************************/

	public int Hit()
	{
		if(!m_canHit) return mlsreplyError;

		m_player_downcard = m_player_upcard;
		m_player_upcard = deal_card();
		add_player_card(m_player_upcard);	

		m_canSplit = false;
		m_canDouble = false;

		if(m_player_value[m_hands] > 21)
		{
			if(m_nhands == 1)
			{
				m_canDeal = true;
				m_canHit = m_canStand = false;
				byteStatePacket[9] = (byte) m_player_upcard;
				byteStatePacket[10] = (byte) m_player_ncards;
				byteStatePacket[11] = (byte) m_dealer_hand[0];
				byteStatePacket[12] = (byte) m_dealer_hand[1];
				byteStatePacket[13] = (byte) m_dealer_value;
				makeStatePacket(14);
				InitData();	
				return BUST;
			}
			if(m_hands < m_nhands ) {
				FinishHand();
				return FINISH_HAND;
				}
			FinishAll();
			return FINISH_ALL;
		}
		byteStatePacket[9] = (byte) m_player_upcard;
		byteStatePacket[10] = (byte) m_player_ncards;
		byteStatePacket[11] = (byte) m_player_value[m_hands];
		makeStatePacket(12);
		return IN_PLAY;
	}

/************************************************************************************
			int Stand()  Player Stand
						returns the state of the game 
************************************************************************************/
    
	public int Stand()
	{
		if(!m_canStand) return mlsreplyError;

		m_canStand = false;
		m_canSplit = m_canDouble = false;

		if(m_hands < m_nhands)  {      // finish a hand
			FinishHand();
			return FINISH_HAND;
			}
		if(m_hands == m_nhands && m_nhands > 1) {       // finish all hands
			FinishAll();
			return FINISH_ALL;
			}

		deal_dealer_cards();
		byteStatePacket[9] = (byte) m_player_upcard;
		byteStatePacket[10] = (byte) m_player_value[m_hands];
		byteStatePacket[11] = (byte) m_dealer_value;
		byteStatePacket[12] = (byte) m_dealer_ncards;
		
 
		int psize = 13 + m_dealer_ncards;
		
		for(int x=0; x<m_dealer_ncards; x++) {
			byteStatePacket[x + 13] = (byte) m_dealer_hand[x];
			}

		if((m_player_value[m_hands] > m_dealer_value) || (m_dealer_value > 21))
		{
			if(m_double_state[m_hands])
				m_balance += m_bet * 4;
			else
				m_balance += m_bet * 2;
			InitData();
			makeStatePacket(psize);
			return WIN;
		}

		if(m_player_value[m_hands] < m_dealer_value)
		{
			InitData();
			makeStatePacket(psize);
			return LOSE;
		}

		if(m_double_state[m_hands]) 
			m_balance += m_bet * 2;
		else m_balance += m_bet;

		InitData();
		makeStatePacket(psize);
		return PUSH;
	}	

/************************************************************************************
			int Insurance()  player wants insurance 
						returns the state of the game 
************************************************************************************/
    
	public int Insurance()
	{
		if(!m_canInsurance) return mlsreplyError;

		m_canInsurance = false;

		m_balance -= m_bet / 2;
		if(m_dealer_value == 21)         // BlackJack
		{
			m_balance += m_bet;   // dealer pay you 2 to 1 for your insurance
			byteStatePacket[9] = (byte) m_dealer_hand[0];
			InitData();
			makeStatePacket(10);
			return LOSE;
		}	

		if(m_player_upcard % 13 == m_player_downcard % 13)
		{
			m_canSplit = true;
		}	

		if((m_player_value[m_hands] >= 9) && (m_player_value[m_hands] <= 11)) {
			m_canDouble = true;
			}

		if(m_player_soft) {
			int value2 = m_player_value[m_hands] - 10;
			if((value2 >= 9) && (value2 <= 11)) {
				m_canDouble = true;
				}
			}		

		m_canHit = m_canStand = true;
		
		makeStatePacket(9);

		return IN_PLAY;	
	}

/************************************************************************************
			int NoInsurance()  player wants no insurance 
						returns the state of the game 
************************************************************************************/

	public int NoInsurance()
	{
		if(!m_canInsurance) return mlsreplyError;

		m_canInsurance = false;

		if(m_dealer_value == 21) {        // BlackJack
			byteStatePacket[9] =(byte)  m_dealer_hand[0];
			InitData();
			makeStatePacket(10);
			return LOSE;	
			}

		if(m_player_upcard % 13 == m_player_downcard % 13)
		{
			m_canSplit = true;
		}	

		if((m_player_value[m_hands] >= 9) && (m_player_value[m_hands] <= 11)) {
			m_canDouble = true;
			}

		if(m_player_soft) {
			int value2 = m_player_value[m_hands] - 10;
			if((value2 >= 9) && (value2 <= 11)) {
				m_canDouble = true;
				}
			}		

		m_canHit = m_canStand = true;
		makeStatePacket(9);
		return IN_PLAY;	
	}

/************************************************************************************
			int Split()  Player Split 
						returns the state of the game 
************************************************************************************/

	public int Split()
	{
		if(!m_canSplit) return mlsreplyError;

		m_canSplit = m_canDouble = false;
		if((m_bet > m_balance) || (m_nhands == MAX_HANDS)) {
			makeStatePacket(9);
			return IN_PLAY;
			}

		m_balance -= m_bet;
		m_nhands++;
		m_player_splitcard[m_nhands] = m_player_upcard;
		int value = m_player_downcard % 13 + 1;
		switch(value)
		{
			case 11:
			case 12:
			case 13:
				m_player_value[m_hands] -= 10;
				break;
				
			default:
				m_player_value[m_hands] -= value;
				break;
		}
		m_player_ncards--;
		m_player_upcard = deal_card();
		add_player_card(m_player_upcard);
		if(m_player_downcard % 13 == m_player_upcard % 13)
			m_canSplit = true;

//		if(m_player_value[m_hands] == 21)  // No blackjack on split
//		{
//			m_blackjack_state[m_hands] = true;
//			return WIN;
//		}

		if((m_player_value[m_hands] >= 9) && (m_player_value[m_hands] <= 11)) {
			m_canDouble = true;
			}

		if(m_player_soft) {
			int value2 = m_player_value[m_hands] - 10;
			if((value2 >= 9) && (value2 <= 11)) {
				m_canDouble = true;
				}
			}		
		byteStatePacket[9] = (byte) m_nhands;
		byteStatePacket[10] = (byte) m_player_splitcard[m_nhands];
		byteStatePacket[11] = (byte) m_player_upcard;
		byteStatePacket[12] = (byte) m_player_value[m_hands];
		makeStatePacket(13);
		return SPLIT;            // split
	}
	 
/************************************************************************************
			int Double()  Player Double
						returns the state of the game 
************************************************************************************/

	public int Double()
	{
		int hitret;

		if(!m_canDouble) return mlsreplyError;

		if(m_bet > m_balance) {
			makeStatePacket(12);
			return IN_PLAY;
			}
		m_balance -= m_bet;
		m_double_state[m_hands] = true;
		hitret = Hit();
		if((hitret == BUST) || (hitret == FINISH_HAND) || (hitret == FINISH_ALL)) {
			return hitret;
			}
		else          // 0
			return Stand();
	}
	 
/************************************************************************************
		int deal_dealer_cards       dealer continuously hits until 17 or higher
									returns the dealers resulting count
************************************************************************************/
  
	private int deal_dealer_cards()
	{
		while(true)
		{
			if(m_dealer_value >= 17)
				return m_dealer_value;
			add_dealer_card(deal_card());
		}
	}	

/************************************************************************************
		int add_dealer_card(int card)    adds "card" to dealer's hand and returns the
										card's value
************************************************************************************/
	private int add_dealer_card(int card)
	{
		int value = (card % 13) + 1;
		switch(value)
		{
			case 1:
				{
					if(m_dealer_value <= 10)
				    {
				    	m_dealer_soft = true;
				    	value = 11;
				    }
				    else
				    {
				    	value = 1;
				    }
				}
				break;
				
			case 11:
			case 12:
			case 13:
				{
					value = 10;
				}
				break;
			
			default:
				break;		
		}
		m_dealer_value += value;
		if(m_dealer_value > 21 && m_dealer_soft)
		{
			m_dealer_value -= 10;
			m_dealer_soft = false;
		}

		m_dealer_hand[m_dealer_ncards++] = card;
		return value;
	}

/************************************************************************************
		int add_player_card			Adds a card to the player's hand
									returns the card's value
************************************************************************************/
	private int add_player_card(int card)
	{
		int value = (card % 13) + 1;
		switch(value)
		{
			case 1:
				{
					if(m_player_value[m_hands] <= 10)
				    {
				    	m_player_soft = true;
				    	value = 11;
				    }
				    else
				    {
				    	value = 1;	
				    }
				}
				break;
				
			case 11:
			case 12:
			case 13:
				{
					value = 10;
				}
				break;
			
			default:
				break;		
		}
		m_player_value[m_hands] += value;
		if(m_player_value[m_hands] > 21 && m_player_soft)
		{
			m_player_value[m_hands] -= 10;
			m_player_soft = false;
		}
		m_player_ncards++;
		return value;
	}	

/************************************************************************************
			int GetHandsResultA(int n)		Gets the result of Split hand n
											returns it
************************************************************************************/

	int GetHandsResultA(int n)
	{
		double bet = m_double_state[n] ? 2 * m_bet : m_bet;
		m_splitBet += bet;
	 
		if(m_player_value[n] > 21)
		{
			return BUST;
		}			

		if(m_player_value[n] > m_dealer_value || m_dealer_value > 21)
		{
// No Blackjack from split

//			if(m_blackjack_state[n])
//			{
//				m_balance += bet * 2.5;
//				m_splitResult += bet * 2.5;
//				return BLACKJACK_WIN;
//			}
//			else
//			{
				m_balance += bet * 2.0;
				m_splitResult += bet * 2.0;
				return WIN;
//			}
		}
		
		if(m_player_value[n] < m_dealer_value)
		{
			return LOSE;
		}
		// m_player_value[n] == m_dealer_value
		m_balance += bet;
		m_splitResult += bet;
//		if(m_blackjack_state[n])
//			return BLACKJACK_PUSH;
//		else
//			return PUSH;
		return PUSH;
	}

/***********************************************************************
*			void FinishHand()										   *
*		finishes a split hand and deals the next one				   *
***********************************************************************/

	void FinishHand() {
		byteStatePacket[9] = (byte) m_player_upcard;
		byteStatePacket[10] = (byte) m_player_ncards;
		byteStatePacket[11] = (byte) m_player_value[m_hands];
		DealNextHand();
		byteStatePacket[12] = (byte) m_hands;
		byteStatePacket[13] = (byte) m_player_downcard;
		byteStatePacket[14] = (byte) m_player_upcard;
		byteStatePacket[15] = (byte) m_player_value[m_hands];
		makeStatePacket(16);
		}

/***********************************************************************
*			void FinishAll()										   *
*		end of a split game											   *
***********************************************************************/

	void FinishAll() {
		int x, curpos;
		byteStatePacket[9] = (byte) m_player_upcard;
		byteStatePacket[10] = (byte) m_player_ncards;
		byteStatePacket[11] = (byte) m_player_value[m_hands];

		deal_dealer_cards();

		for(x=0; x<m_nhands; x++) {
			byteStatePacket[12 + x] = (byte) (GetHandsResultA(x+1) - mlsreplyBase);
			}
		curpos = 12 + m_nhands;
		byteStatePacket[curpos] = (byte) m_dealer_value;
		byteStatePacket[curpos + 1] = (byte) m_dealer_ncards;
		curpos += 2;
		for(x = 0; x < m_dealer_ncards; x++) {
			byteStatePacket[curpos + x] = (byte) m_dealer_hand[x];
			}
		InitData();
		makeStatePacket(x + curpos);
		}

/***********************************************************************
*		byte[] intToBytes(int i)									   *
*	convert an int to 4 bytes										   *
*	an agrivation													   *
***********************************************************************/

	int[] intToBytes(int i) {
		int result[] = new int[4];
		result[0] = (i & 0xff);
		result[1] = ((i & 0xff00) >> 8);
		result[2] = ((i & 0xff0000) >> 16);
		result[3] = ((i & 0xff000000) >> 24);
		return result;
		}

/***********************************************************************
*		void initStatePacket()										   *
*	initializes the first 9 members of byteStatePacket				   *
***********************************************************************/			

	void initStatePacket() {
		byte state = 0;
		int cash[];
		int dollars, cents;
		if(m_canDeal) state += GS_CAN_DEAL;
		if(m_canHit) state += GS_CAN_HIT;
		if(m_canStand) state += GS_CAN_STAND;
		if(m_canDouble) state += GS_CAN_DOUBLE;
		if(m_canSplit) state += GS_CAN_SPLIT;
		if(m_canInsurance) state += GS_CAN_INSURANCE;
		if(m_cashedOut) state += GS_CASHED_OUT;
		byteStatePacket[0] = state;
		
		dollars = (int) m_balance;
		cents = (int)(m_balance * 100) - dollars * 100;
		cash = intToBytes(dollars);
		for(int x=0; x<4; x++) byteStatePacket[x+1] = cash[x];
		cash = intToBytes(cents);
		for(int x=0; x<4; x++) byteStatePacket[x+5] = cash[x];
		return;
		}

/**********************************************************************
*		void makeStatePacket(int n_members)							  *
*			prepares the state packet for length n_members			  *
**********************************************************************/

	void makeStatePacket(int n_members) {
		iStatePacketLength = n_members;
		}
	
/**********************************************************************
*		public byte[] GetStatePacket()								  *
*			returns the State Packet								  *
**********************************************************************/

	public int[] GetStatePacket() {
		initStatePacket();
		return byteStatePacket;
		}
		
 /****************************************************************
			public void ResetTable(int tableNo, int clientIDMajor
						, int clientIDMinor) throws Exception
****************************************************************/

	public void ResetTable(int tableNo, int clientIDMajor, 
				int clientIDMinor) throws Exception
		{
		if(tableNo > MAX_TABLE_NO) 
			{
			throw new Exception("Invalid Table Number");
			}
		if(dMIN > m_balance) 
			{
			throw new Exception("Insufficient Funds");
			}		
		if(clientIDMajor != VERSION_1) 
			{
			throw new Exception("Invalid Client Version");
			}

		if(clientIDMinor != STANDALONE_CLIENT) 
			{
			throw new Exception("Invalid Client Version");
			}
			

		byteStatePacket[9] = MAX_GAME_NO + 1;

		int x, y, packetsize,dmin,cmin,dmax,cmax;
		double cm,cma;
		int conv1[], conv2[], conv3[], conv4[];
			
		for(x=0; x<MAX_GAME_NO + 1; x++) {
			byteStatePacket[x+10] = x;
			}
		
		
		packetsize = x + 10;

		for(x=0; x<MAX_GAME_NO + 1; x++) {
			dmin = (int) dTableMins[x]; 
			cm = (dTableMins[x] * 100) - dmin * 100;
			cmin = (int) cm;
			dmax = (int) dTableMaxs[x];
			cma = (dTableMaxs[x] * 100) - dmax * 100;
			cmax = (int) cma;

			conv1 = intToBytes(dmin);
			conv2 = intToBytes(cmin);
			conv3 = intToBytes(dmax);
			conv4 = intToBytes(cmax);

			System.out.println("Minmax: " + dmin + " " + cmin + " " + dmax + " " + cmax);
			for(y=0; y<4; y++) {
				byteStatePacket[packetsize + y] = conv1[y];
				byteStatePacket[packetsize + y + 4] = conv2[y];
				byteStatePacket[packetsize + y + 8] = conv3[y];
				byteStatePacket[packetsize + y + 12] = conv4[y];
				}
			packetsize += 16;
			}

		m_canDeal = m_canStand = m_canHit = m_canDouble = m_canSplit = m_canInsurance = false;
		m_bet = 0.0;
//		m_balance = 0.0;

		makeStatePacket(packetsize);
		}

/****************************************************************
			public void PlayGame(int gameNo)
****************************************************************/
	
	public void PlayGame(int gameNo) throws Exception
		{
		if(gameNo > MAX_GAME_NO) {
			throw new Exception("Invalid Game");
			}

		dMIN = dTableMins[gameNo];
		dMAX = dTableMaxs[gameNo];
		m_balance = dStartBalance[gameNo];

		if(dMIN > m_balance) {
			throw new Exception("Insufficient Funds");
			}
		InitData();
		}

/****************************************************************
			public int Login(String sUserName, String sPassword)
****************************************************************/

    public int Login(String sUserName, String sPassword)
		{
/*
		decode dEncrypted=new decode(sPassword) ;

		dSessionID=dEncrypted.session_id ;
		m_balance=dEncrypted.money ;
		dCasinoID=dEncrypted.casino_id ;

		if(dSessionID != 0) return gssreplyLoginOK;
		return gssreplyLoginFailed;
*/
//		m_balance = dStartBalance[m_gameNo];
		m_bet = 0;
		return gssreplyLoginOK;
		}

/****************************************************************
			public void Logoff()
****************************************************************/

	public void Logoff()
		{
//		encode eEncrypted=new encode(dSessionID, m_balance, dCasinoID) ;	// communicate with server
//		System.out.println("Encrypted string is\""+eEncrypted+"\"") ;
		}
}

