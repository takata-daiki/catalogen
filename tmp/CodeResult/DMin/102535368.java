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
// Java client for standalone or Gameserver blackjack

import java.applet.*;
import java.awt.*;
import java.net.*;
//import java.lang.*;
import java.util.*;
import GraphicButton;

// CCT/Client Side Import

import CBlackJack;


// VV Import
/*
import MachineLogic;
*/

///////////////////////////////////////////////////////////////////////////////////
//                      class VVBjInt                                            //
//					BlackJack Interface Applet									 //
///////////////////////////////////////////////////////////////////////////////////

public class VVBjInt extends Applet implements Runnable {

//@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
//@@			Global Variables							@@
//@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@


	Image imageBk;													//Background image

	double chipValue[] = {5.0,25.0,100.0};			//Denominations of chips 

	MediaTracker tracker = new MediaTracker(this);					//Media Tracker for images

	final int UPDATERATE = 100;										//redraw rate for loading images

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


	private	String	sPlayerName = "",
					sPlayerEMail ="" ;

	private	final	static	String	sEMailHost = "virtualvegas.com",
									sEMailBase = "virtualvegas.com",
									sEMailName1 = "prizes1",
									sEMailName2 = "prizes2",
									sEMailName3 = "prizes3",
									sEMailName4 = "prizes4" ;

	// player details message box
	private	Label	label1,
					label2,
					label3,
					label4,
					label5,
					label6,
					label7,
					label8,
					label9,
					label10,
					label11,
					label12 ;

    private	TextField	editEMail,
						editName ;

    private	Button	buttonPlayAgain,
					buttonSend,
					buttonNoThanks ;


/*

	static final int IN_PLAY = 100, 
					WIN = 101, 
					LOSE = 102, 
					BUST = 103, 
					PUSH = 104, 
					INSURANCE = 105, 
					SPLIT = 106,
					FINISH_HAND = 107, 
					FINISH_ALL = 108, 
					BLACKJACK_WIN = 109, 
					BLACKJACK_PUSH = 110,
					GAME_ERROR = 111,
					replyNone				= 0,
                    replyLoginOK            = 10,
					replyLoginFailed		= 11;

*/
	static final int		GS_CAN_BET = 1,
							GS_CASHED_OUT = 2,
							GS_CAN_HIT = 4,
							GS_CAN_STAND = 8,
							GS_CAN_SPLIT = 16,
							GS_CAN_DOUBLE = 32,
							GS_CAN_INSURANCE = 64,
							GS_CAN_DEAL = 128;

	boolean doneOne = false;

	Image		m_pCardDib1; 
	Image		m_pCardDib2;
	Image		imageCardParts,
				imageCardTopLarge,
				imageCardTopLargeShad,
				imageCardTopSmall,
				imageCardSideLarge,
				imageCardSideSmall,
				imageCardBottomSmall,
				imageCardPipsLarge[],
				imageCardPipsSmall[],
				imageCardSuitsLarge[],
				imageCardSuitsSmall[],
				imageDlrCardBack,
				imageButtonParts,
				imageHitHL,
				imageHitS,
				imageHit,
				imageClearHL,
				imageClearS,
				imageClear,
				imageBetButtonParts; 

	boolean		paintClear = false,
				paintCardLarge = false,
				paintCardSmall = false, 
				paintInsurance = false, 
				paintDeal = false, 
				paintHit = false, 
				paintStand = false, 
				paintSplit = false, 
				paintDouble = false,
				paintDealerValue = false, 
				paintPlayerValue = false,
				paintDoubleBetInPlay = false, 
				paintResult = false,
				paintFlashResult = false, 
				paintHandsResult = false, 
				paintBK = true,
				paintCashOut = false,
				bCashedOut = false,

				bFinished = false,
				bEMailError = false,
				bSendingData = false,

				paintBet = false,
				paintBetInPlay = false,
				m_bReady = false,
				paintError = false,
				paintBalance = false,
				paintMinMax = false,
				paintDlrCardBack = false,
				paintQueryCashOut = false,
				bFirstCard = false,
				paintSplitBet = false,
				bInitialized = false,
				bPageview = false;

	boolean m_canDeal, 
			m_canHit,
			m_canStand, 
			m_canSplit, 
			m_canDouble, 
			m_canInsurance,
			m_cashedOut,
			m_canBet;

	Image butts[] = new Image[6];									//Depressed button images
	double dMIN;													//Minimum bet value
	double dMAX;													//Maximum bet value
	Rectangle	dealRect = new Rectangle(161,170,104,30),			//Deal button
				clearRect = new Rectangle(169,139,88,15),			//Clear button
				hitRect = new Rectangle(169,139,88,15),				//hit button
				standRect = new Rectangle(161,170,104,30),			//stand button
				splitRect = new Rectangle(213,154,52,15),			//split button
				doubleRect = new Rectangle(161,154,52,15),			//double button
				resultRect = new Rectangle(252,190,133,93),		//split result
				dlrTableRect = new Rectangle(169,89,258,67),		//Dealer's cards
				insuranceRect = new Rectangle(190,185,20,13),		//Where to click for insurance
				noInsuranceRect = new Rectangle(217,185,20,13),		//Where to click for no insurance
				plrCardRect = new Rectangle(0,200,427,67),		//player's cards
//				cashOutRect = new Rectangle(48,296,44,23),			//Cash Out button
				cashOutRect = new Rectangle(339,160,48,15),
				cashOutAreaRect = new Rectangle(300,136,118,60),
				buttonPanelRect = new Rectangle(123,156,180,44),
				playAreaRect = new Rectangle(0,0,427,267),			//The play area gets cleared every deal		
				balanceRect = new Rectangle(17,173,93,15),
				minMaxRect = new Rectangle(17,210,93,37),
				betRect = new Rectangle(17,187,93,15);

	int			chipMediaOriginX[] = {0,35,68};

	Rectangle	chipAddRects[] = {							// Click on to add chips
							new Rectangle(161,154,35,15),
							new Rectangle(196,154,33,15),
							new Rectangle(229,154,36,15)};								

//NOTE:   Text appears with the base line at the y coordinate for drawString, so coordinates for text
//		are in the lower left corner
	
	Image bk2;
	Graphics	offscrg;									//Off Screen Graphics for double buffering
	Image		offscrim;									//Double buffering image
	Point		mincoords = new Point(17,223),				//Display the minimum bet
				maxcoords = new Point(17,237),				//Display the maximum bet 
				balcoords = new Point(17,187),				//Dislpay the player's balance
				betcoords = new Point(17,200),				//Display the player's bet
				dlrcardcoords = new Point(169,89),			//Display the dealer's cards

				plrcardcoords = new Point(135,201),			//Display the player's cards
				plrvalcoords = new Point(140,213),			//Dislpay the player's hand count
				dlrvalcoords = new Point(189,98),			//Display the dealer's hand count
				cardPoint1,									//Point for displaying the first card. paintCard() does 2 at a time
				cardPoint2,									//Point for diaplaying the second card
				resultcoords = new Point(193,253),			//Display single hand result			
				insurancecoords = new Point(250,247),		//Display do you want insurance
				noinsurancecoords = new Point(277,247),		//Display do you want no insurance
				insurancetextcoords = new Point(190,153),	//Display "Do you want Insurance"
				betinplaycoords = new Point(187,230);		//Display the amount of bet over the cards

	String		cPlayerValueText = null;					//Text for the player's  count
	String		cDealerValueText = null;					//Text for the dealer's count
	String		cResultText	= null;							//Outcome of one hand

	int			m_OutCome;
	int			chipStackSpacing = 4;						//Space between chips
	String		card1string = null;							//String for card1 - to be replaced with an image
	String		card2string = null;							//"       "  card2 - "                         "
	String		cHandsResult[] = new String[17];			//Final result for a split
	int			numcardspaint;								//number of cards to paint
	int currentChips[] = new int[6];						//Current size of the stacks

	Thread		thisThread = null;							//This thread
 
	static final int			iScreenWidth = 427,			//Dimensions of the game client
								iScreenHeight = 267;

	GraphicButton	gbChip[] = new GraphicButton[6];
	GraphicButton	gbDeal,
					gbHit,
					gbStand,
					gbDouble,
					gbSplit,
					gbInsurance,
					gbNoInsurance,
					gbClear,
					gbCashOutYes,
					gbCashOutNo;

	static final int	plrCardSpacingX = 28,
						dlrCardSpacingX = 15,
						dlrFirstCardSpacingX = 17,
						plrCardSpacingY = 0,				// How a player's card is layed relative to the last
						plrCardWidth = 68,
						plrCardHeight = 66,
						dlrCardWidth = 40,
						dlrCardHeight = 58,
						splitSpacing = 100;					// Space between Split hands

	int					currentSplitSpacing = 0;			// What hand were on

	Vector vectorGB = new Vector();

	Image cardImage;

	double			m_bet = 0,
					m_balance = 0;

	int			byteStatePacket[];

	int m_dealer_hand[] = new int[20];		// dealer hand cards
	int m_player_upcard;					// last card dealt to player
	int m_player_downcard;					// 2nd to last card dealt to player - doesn't track previous cards
	int m_dealer_ncards;                     // how many cards does dealer have
	int m_player_ncards;                     // how many cards does player have
	int m_nhands;             				// how many hands
	int m_hands;							// which hand are you playing in a split
	int m_dealer_value;						// dealer count
	int m_player_value[] = new int [17];           		// most for 16 split pairs from 1 to 16
	int m_hands_result[] = new int[17];
	int	m_split_spacing[] = new int[17];
	int m_split_card[] = new int[17];

	AudioClip				auDeal,	
							auHit,
							auStand,
							auSplit,
							auDouble,
							auChip,
							auStart,
							auCashout,
							auWin,
							auBust,
							auLose,
							auPush,
							auBj,
							auClear;

	static final int		VERSION_1 = 1,
							STANDALONE_CLIENT = 0,
							GAMESERVER_CLIENT = 1,
							AD_FREQUENCY = 3;

	int						iAdCycle = 0;

	
// Our game defs for VV Game server
/*
	static final int		VERSION_MAJOR = VERSION_1,
							VERSION_MINOR = GAMESERVER_CLIENT;

	int						iTableNo = 0,
							iGameNo = 0;

	MachineLogic			pBlackJack;
*/
// End VV Defs

// CCT Game Defs

	static final int		VERSION_MAJOR = VERSION_1,
							VERSION_MINOR = STANDALONE_CLIENT;

	int						iTableNo = 0,
							iGameNo = 0;

	CBlackJack				pBlackJack;


// End CCT Defs

	Font					fontStd;		// Standard font
							
	long					lRepaintTime = 0;

	int						iCardNo = 0;

	int						m_status = 0;

	int						iImageProgress = 0;

	String cashoutString;
	String cashoutString2 = "Click Shift-Reload to play again";

	Dialog dialogCashOut;	
//@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
//@@			Applet Methods								@@
//@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@

/**************************************************************************
				method  void run() 
 **************************************************************************/

    public void run() {
		Thread.currentThread().setPriority(Thread.MIN_PRIORITY);
		if(!bInitialized) {
			while(!tracker.checkID(0)) {
				try {
					Thread.sleep(100);
					}
				catch(Exception e) {}				
				}
			if(tracker.isErrorAny()) {
				System.out.println("Image Error");
//				paintError = true;
				}

			paintBK = true;
			repaint();
			while(!tracker.checkAll(true)) {
				try {
					Thread.sleep(100);
					}
				catch(Exception e) {}				
				}
			MakeCardParts();
			MakeButtons();
			ShowBetButtons();
			repaint();
			auStart.play();
			paintBalance = true;
			prepaint(balanceRect);
			paintMinMax = true;
			prepaint(minMaxRect);
		
			repaint();
			bInitialized = true;
			}

	 }

/**************************************************************************
				method  void init()
**************************************************************************/

   public void init() {


		GetSoundMedia();
//		MakeBackground();
		getImages();
		getParams();		
		Dimension d = size();
		offscrim = createImage(d.width, d.height);
		setLayout(null);
		String gno = getParameter("GAMENO");
		if(gno != null) {
			iGameNo = Integer.parseInt(gno,10);
			}

// CCT Initialization


		try {
			pBlackJack = new CBlackJack();
//			pBlackJack.Login(null, getParameter("CASINO"));
			pBlackJack.Login(null,null);
			pBlackJack.ResetTable(iTableNo, VERSION_MAJOR, VERSION_MINOR);
			pBlackJack.PlayGame(iGameNo);
			GetStatePacket();
			}
		
		catch(Exception e) 
			{
			System.out.println("Error starting game");
			ErrorOut();
			return;
			}


// VV Init
/*
		try {
			pBlackJack = new MachineLogic(this);
			pBlackJack.Login(getParameter("USERNAME"), getParameter("PASSWORD"));
			pBlackJack.ResetTable(iTableNo, VERSION_MAJOR, VERSION_MINOR);
			pBlackJack.PlayGame(iGameNo);
			GetStatePacket();
			}
		
		catch(Exception e) 
			{
			ErrorOut();
			return;
			}
*/


		int dmin = 0,
			cmin = 0,
			dmax = 0,
			cmax = 0;

		int x, x0, x1;
		for(x0=0; x0<byteStatePacket[9]; x0++) 
			{
			if(byteStatePacket[10 + x0] == iGameNo) break;
			}

		x1 = x0 * 16 + 10 + byteStatePacket[9];

		for(x=3; x>=0; x--) {
			dmin <<= 8;
			cmin <<= 8;
			dmax <<= 8;
			cmax <<= 8;
			dmin += byteStatePacket[x1 + x];
			cmin += byteStatePacket[x1 + x + 4];
			dmax += byteStatePacket[x1 + x + 8];
			cmax += byteStatePacket[x1 + x + 12];
			}
		dMIN = (double)dmin + (double)cmin / 100;
		dMAX = (double)dmax + (double)cmax / 100;
		m_bReady = true;
		fontStd = new Font("Arial", Font.BOLD, 12);
		setLayout(null);
	}

/**************************************************************************
		method  void destroy() 
**************************************************************************/

	public void destroy() {
		super.destroy();
		}

/**************************************************************************
			method   void start()
**************************************************************************/

    public void start() {
		if(thisThread == null) {
			thisThread = new Thread(this);
			thisThread.start();
			} 
	}

 /**************************************************************************
			method  void stop()
 **************************************************************************/

    public void stop() {
		if(thisThread != null) {
			thisThread.stop();
			thisThread = null;
			}
	}

 /**************************************************************************
			method  void paint(Graphics g)
 **************************************************************************/

	public void paint(Graphics g) {
		repaint();
		}

  /**************************************************************************
			method  void update(Graphics g)
 **************************************************************************/

    public void update(Graphics g) {

		StringBuffer ptext;
		boolean allWIN = true;
		boolean allLOSE = true;

		if(paintError) {
			g.setColor(Color.black);
			g.fillRect(0,0,size().width, size().height);
			g.setColor(Color.white);
			try {
				if(tracker.isErrorAny()) {
					g.setFont(new Font("Arial", Font.BOLD, 12));
					g.drawString("Media Loading Error", 10,40);
					g.drawString("Please hold the Shift key and click on Reload.",10,60);
					g.drawString("If this fails, close and restart your browser",10,80);
					return;
					}
				}
			catch(Exception e) {}

			g.setFont(new Font("Arial", Font.BOLD, 14));
			g.drawString("Fatal Error", 10,40);
			g.drawString("Contact System Administrator",10,70);
			return;
			}
		if(!tracker.checkID(0)) {
			g.setColor(Color.black);
			g.fillRect(0,0,size().width, size().height);
			g.setColor(Color.white);
			g.setFont(new Font("Arial", Font.BOLD, 14));
//			String st = new String("Please Wait - Loading Background " + iImageProgress + "%");
			String st = new String("Please Wait - Loading Background");
			g.drawString(st,110,70);
			return;
//			g.drawImage(imageBk,0,0,null);
//			return;
			}

		if (paintClear)
			{
//			System.out.println("Here " + paintBK + " " + size().width + " " + size().height) ;
			g.setColor(Color.white) ;
			g.fillRect(0, 0, size().width, size().height);
			}

		if(paintBK) {
			paintBK = false;
			g.drawImage(imageBk,0,0,null);
			}		

		if(bFinished) 
			{
			g.setColor(Color.black);
			g.fillRect(plrCardRect.x, plrCardRect.y, plrCardRect.width, plrCardRect.height);

			g.setColor(Color.white);
			g.drawString(cashoutString,plrcardcoords.x, plrcardcoords.y + 20);
			g.drawString(cashoutString2,plrcardcoords.x, plrcardcoords.y + 40);
//			return;
			}

		if(bEMailError) 
			{
			g.setColor(Color.black);
			g.fillRect(plrCardRect.x, plrCardRect.y, plrCardRect.width, plrCardRect.height);

			g.setColor(Color.white);
			g.drawString("Unable to send your details to the server",plrcardcoords.x, plrcardcoords.y + 20);
			g.drawString("You will not be able to enter the prize drawing this time" ,plrcardcoords.x, plrcardcoords.y + 40);
//			return;
			}

		if (bSendingData)
			{
			g.setColor(Color.black);
			g.fillRect(0, 0, size().width, size().height);

			g.setColor(Color.white);
			g.drawString("Please wait...",plrcardcoords.x, plrcardcoords.y + 20);
			g.drawString("Communicating with server",plrcardcoords.x, plrcardcoords.y + 40);
//			return;
			}

/*
		if(!tracker.checkAll(true)) {
			g.setColor(Color.white);
			g.drawString("Loading graphics", insurancetextcoords.x, insurancetextcoords.y);
			return;
			}
*/
			
		if(paintMinMax) 
			{
			paintMinMax = false;
			g.setColor(Color.yellow);
			g.drawString("Min:", mincoords.x, mincoords.y);
			g.drawString("Max:", maxcoords.x, maxcoords.y);
			g.setColor(Color.white);
			g.drawString(doubleToDollar(dMIN), mincoords.x + 35, mincoords.y);
			g.drawString(doubleToDollar(dMAX), maxcoords.x + 35, maxcoords.y);
			}

		if(paintBalance) 
			{
			paintBalance = false;
			g.setColor(Color.yellow);
			g.drawString("Cash:", balcoords.x, balcoords.y);
			g.setColor(Color.white);
			g.drawString(doubleToDollar(m_balance), balcoords.x + 35, balcoords.y);
			}

		if(paintInsurance)
		{
			paintInsurance = false;
			g.setColor(Color.blue);
			g.drawString("Insurance?", insurancetextcoords.x, insurancetextcoords.y + 20);
			g.setColor(Color.white);
		}

		if(paintResult)
		{
			paintResult = false;
			PaintResultText(g, false);
		}
		
		if(paintFlashResult) 
		{
			paintFlashResult = false;
			PaintResultText(g, true);
		}


		if(paintCardSmall)
		{
			paintCardSmall = false;
			g.setColor(Color.white);
			g.fillRect(cardPoint1.x + 2, cardPoint1.y + 5, 37, 44);
			g.drawImage(imageCardTopSmall, cardPoint1.x + 2, cardPoint1.y,null);
			g.drawImage(imageCardSideSmall, cardPoint1.x, cardPoint1.y,null);
			g.drawImage(imageCardBottomSmall, cardPoint1.x + 2, cardPoint1.y + 49,null);
			int suit = iCardNo / 13;
			if(suit == 1 || suit == 3) 
				{
				g.drawImage(imageCardPipsSmall[iCardNo % 13], cardPoint1.x + 3, 
						cardPoint1.y + 5, null);
				}
			else 
				{
				g.drawImage(imageCardPipsSmall[(iCardNo % 13) + 13], cardPoint1.x + 3, 
						cardPoint1.y + 5, null);
				}

			g.drawImage(imageCardSuitsSmall[3 - suit], cardPoint1.x + 3,
					cardPoint1.y + 27, null);

		}

		if(paintCardLarge)
		{
			paintCardLarge = false;
			g.setColor(Color.white);
			g.fillRect(cardPoint1.x + 4, cardPoint1.y + 5, 64, 61);

			if(bFirstCard) 
				{
				g.drawImage(imageCardTopLarge, cardPoint1.x + 4, cardPoint1.y,null);
				}
			else 
				{
				g.drawImage(imageCardTopLargeShad, cardPoint1.x, cardPoint1.y,null);
				g.drawImage(imageCardSideLarge, cardPoint1.x, cardPoint1.y + 5,null);
				}
			bFirstCard = false;
			int suit = iCardNo / 13;
			if(suit == 1 || suit == 3) 
				{
				g.drawImage(imageCardPipsLarge[iCardNo % 13], cardPoint1.x + 5, 
					cardPoint1.y + 5, null);
				}
			else 
				{
				g.drawImage(imageCardPipsLarge[(iCardNo % 13) + 13], cardPoint1.x + 5, 
					cardPoint1.y + 5, null);

				}
			g.drawImage(imageCardSuitsLarge[3 - suit], cardPoint1.x + 5,
					cardPoint1.y + 42, null);

			if(paintPlayerValue) {
				paintPlayerValue = false;
				PaintPlayerScore(g, cardPoint1.x + 32, cardPoint1.y + 10);
				}

			if(paintSplitBet) {
				paintSplitBet = false;
				if(paintDoubleBetInPlay) {
					paintDoubleBetInPlay = false;
					PaintPlayerBetDouble(g, cardPoint1.x + 25, cardPoint1.y + 30);
					}
				else PaintPlayerBet(g, cardPoint1.x + 32, cardPoint1.y + 30);

				}

//			if(paintBetInPlay) {
//				paintBetInPlay = false;
//				PaintPlayerBet(g, cardPoint1.x + 32, cardPoint1.y + 40);
//				}

//			if(paintDoubleBetInPlay) {
//				paintDoubleBetInPlay = false;
//				PaintPlayerBetDouble(g, cardPoint1.x + 32, cardPoint1.y + 40);
//				}
				
		}

		if(paintDlrCardBack)
		{
			paintDlrCardBack = false;
			g.drawImage(imageDlrCardBack, cardPoint1.x, cardPoint1.y,null);
		}

 		if(paintBet) {
			paintBet = false;
			g.setColor(Color.yellow);
			g.drawString("Bet:", betcoords.x, betcoords.y);
			g.setColor(Color.white);
			g.drawString(doubleToDollar(m_bet), betcoords.x + 35, betcoords.y);
			}

		if(paintDealerValue)
		{
			paintDealerValue = false;
			PaintDealerScore(g);
		}

		if(paintPlayerValue)
		{
			paintPlayerValue = false;
			PaintPlayerScore(g);
		}

		if(paintBetInPlay) 
		{
			paintBetInPlay = false;
			PaintPlayerBet(g);
		}

		if(paintDoubleBetInPlay) 
		{
			paintDoubleBetInPlay = false;
			PaintPlayerBetDouble(g);
		}

		if(paintDeal)
		{
			paintDeal = false;
			g.drawImage(butts[0], dealRect.x, dealRect.y, null); /* deal */
		}	

		if(paintHit)
		{
			paintHit = false;
			g.drawImage(butts[1], hitRect.x, hitRect.y, null); /* hit */
		}	

		if(paintStand)
		{
			paintStand = false;
			g.drawImage(butts[2], standRect.x, standRect.y, null); /* stand */
		}

		if(paintSplit)
		{
			paintSplit = false;
			g.drawImage(butts[3], splitRect.x, splitRect.y, null); /* split */
		}		

		if(paintDouble)
		{
			paintDouble = false;
			g.drawImage(butts[4], doubleRect.x, doubleRect.y, null); /* double */
		}

		if(paintQueryCashOut) {
			g.setColor(new Color(196,196,196));
			g.fill3DRect(cashOutAreaRect.x, cashOutAreaRect.y, cashOutAreaRect.width, cashOutAreaRect.height,true);
			g.setColor(Color.black);
			g.drawString("Cash Out.", cashOutAreaRect.x + 30, cashOutAreaRect.y + 14);
			g.drawString("Are you sure?", cashOutAreaRect.x + 20, cashOutAreaRect.y + 28);
			paintQueryCashOut = false;
			}		
		if(paintCashOut) 
		{
			paintCashOut = false;
			g.drawImage(butts[5], cashOutRect.x, cashOutRect.y,null); 
		}

		paintGB(g);				  
	}

/*************************************************************
			public boolean mouseDown(Event evt, int x, int y)
		method
*************************************************************/

	public boolean mouseDown(Event evt, int x, int y) {
		int x0;
		GraphicButton gb;
		for(x0=0; x0<vectorGB.size(); x0++) {
			gb = (GraphicButton) vectorGB.elementAt(x0);
			if(gb.inside(x,y)) {
				gb.mouseDown(evt, x,y);
				return true;
				}
			}
		if(cashOutRect.inside(x,y) && !bCashedOut) {
			paintQueryCashOut = true;
			prepaint(cashOutAreaRect);
			ShowCashOutButtons();
			}
		return true;
		}

/*************************************************************
			public boolean mouseUp(Event evt, int x, int y)
		method
*************************************************************/

	public boolean mouseUp(Event evt, int x, int y) {
		int x0;
		GraphicButton gb;
		for(x0=0; x0<vectorGB.size(); x0++) {
			gb = (GraphicButton) vectorGB.elementAt(x0);
			if(gb.bState) {
				gb.mouseUp(evt,x,y);
				evt.target = gb;
				action(evt,gb);
				return true;
				}
			}
		return true;
		}

/*************************************************************
			public boolean mouseMove(Event evt, int x, int y)
		method
*************************************************************/

	public boolean mouseMove(Event evt, int x, int y) {
		GraphicButton gb;

		for(int z = 0; z < vectorGB.size(); z++) {
			gb = (GraphicButton)vectorGB.elementAt(z);
			if(gb.bLit && !gb.inside(x,y)) {
				gb.unlight();
				gb.paint();
				repaint();
				}

			if(!gb.bLit && gb.inside(x,y) && gb.bShowing && (gb.m_status_mask & m_status) != 0) {
				gb.light();
				gb.paint();
				repaint();
				}
			}
		return true;
		}

/**************************************************************************
			method imageUpdate

**************************************************************************/
 
   public boolean imageUpdate(Image img, int infoflags,
			       int x, int y, int width, int height) 
		{
		if(!tracker.checkID(0)) 
			{
//			if(System.currentTimeMillis() < lRepaintTime) 
//				{
//				return true;
//				}
//			lRepaintTime = System.currentTimeMillis() + 100;
			iImageProgress = (int) ((float) (y + height) * 100.0 / iScreenHeight);
//			repaint();
			}

		return true;
		}			

/**************************************************************************
			method repaint()

		used to instigate double buffering
**************************************************************************/

	public void repaint() {
		offscrg = offscrim.getGraphics(); 
		offscrg.setFont(fontStd);
		offscrg.setColor(Color.white);
		offscrg.clipRect(0,0,size().width, size().height);
		update(offscrg);
		Graphics g = getGraphics();
		g.drawImage(offscrim,0,0,null);
		g.dispose();
		offscrg.dispose();
		}

/**************************************************************************
			method repaint(Rectangle c)

		used to clip rectange c
**************************************************************************/

	public void repaint(Rectangle c)
		{
		offscrg = offscrim.getGraphics(); 
		offscrg.setFont(fontStd);
		offscrg.setColor(Color.white);
		offscrg.clipRect(c.x, c.y, c.width, c.height);
		update(offscrg);
		Graphics g = getGraphics();
		g.clipRect(c.x, c.y, c.width, c.height);
		g.drawImage(offscrim,0,0,null);
		g.dispose();
		offscrg.dispose();
		}

/**************************************************************************
			prepaint(Rectangle c)

		used to clip rectange c , prepare buffer w/o drawing it
**************************************************************************/

	public void prepaint(Rectangle c)
		{
		offscrg = offscrim.getGraphics(); 
		offscrg.setFont(fontStd);
		offscrg.setColor(Color.white);
		offscrg.clipRect(c.x, c.y, c.width, c.height);
		update(offscrg);
		offscrg.dispose();
		}

/***********************************************************************
					public boolean action(Event evt, Object obj)

							method
***********************************************************************/

	public boolean action(Event evt, Object obj) {
		int x;
		for(x=0; x<3; x++) {
			if(evt.target == gbChip[x]) {
				AddBet(x);
				return true;
				}
			}
		if(evt.target == gbDeal) {
			Deal();
			return true;
			}

		if(evt.target == gbClear) {
			ClearBet();
			return true;
			}

		if(evt.target == gbHit) {
			Hit();
			return true;
			}

		if(evt.target == gbStand) {
			Stand();
			return true;
			}
		if(evt.target == gbDouble) {
			Double();
			return true;
			}
		if(evt.target == gbSplit) {
			Split();
			return true;
			}
		if(evt.target == gbInsurance) {
			Insurance();
			return true;
			}
		if(evt.target == gbNoInsurance) {
			NoInsurance();
			return true;
			}
		
		if(evt.target == gbCashOutYes) {
			gbCashOutYes.hide();
			gbCashOutNo.hide();
			paintBK = true;
			prepaint(cashOutAreaRect);
			CashOut();
			return true;
			}

		if(evt.target == gbCashOutNo) {
			gbCashOutYes.hide();
			gbCashOutNo.hide();
			paintBK = true;

			prepaint(cashOutAreaRect);
			if(m_canBet) {
				ShowBetButtons();
				return true;
				}
			else if(m_canInsurance) {
				ShowInsuranceButtons();
				return true;
				}
			else {
				ShowPlayButtons();
				return true;
				}

			}

		return true;
		}

/************************************************************************
			Method String getAppletInfo()
************************************************************************/

	public String getAppletInfo() {
		return "Blackjack game Copyright (c) 1996 Justin Lloyd.\n";
		}

/************************************************************************
			Method boolean mouseEnter(Event evt, int x, int y) 
************************************************************************/

	public boolean mouseEnter(Event evt, int x, int y) 
		{
		showStatus("Virtual Vegas Online Blackjack 1.0");
		return true;
		}

//@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
//@@			Midgame subroutines							@@
//@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@

/**************************************************************************
					void GetHandsResult()  

				Gets the result string for a Split hand
				and stores the result text in cResultText 	
**************************************************************************/

	void GetHandsResult()
	{
		PaintDealer();
		int j;
		int mss = currentSplitSpacing;
		for(int i = 1; i <= m_nhands; i++)
			{
			j = m_hands_result[i];
			if(j==WIN) 
				{
					cResultText = "WIN";
					auWin.play();
				}					
			else if(j==LOSE)
				{
					cResultText = "LOSE";
					auLose.play();
				}
			else if(j==BLACKJACK_WIN)
				{
					cResultText = "WIN";
					auWin.play();
				}
			else if(j==BLACKJACK_PUSH)
				{
					cResultText = "PUSH";
					auPush.play();
				}
			else if(j==BUST)
				{
					cResultText = "LOSE";
					auLose.play();
				}
			else if(j==PUSH)
				{
					cResultText = "PUSH";
					auPush.play();
				}
			if(i == m_nhands) currentSplitSpacing = mss;
			else currentSplitSpacing = m_split_spacing[i] - 20;

			FlashResult();
			}				
	}

/**************************************************************************
					void FlashResult()  

			Flashes the result
**************************************************************************/

	void FlashResult() 
	{
		for(int n=0; n<2; n++) 
			{
			paintFlashResult = true;
			repaint(plrCardRect);
			try 
				{
				Thread.sleep(200);
				}

			catch(Exception e) {}

			paintResult = true;
			repaint(plrCardRect);
			try 
				{
				Thread.sleep(200);
				}

			catch(Exception e) {}
			}
	}

/**************************************************************************
				void AddBet(int i)

			Click on chip # i
**************************************************************************/

	void AddBet(int i)
	{
							
		if(m_bet + chipValue[i] > dMAX || m_balance < chipValue[i])
			return;
		auChip.play();
		m_bet += chipValue[i];
		m_balance -= chipValue[i];
		if(m_bet >= dMIN)
			{
			m_canDeal = true;
			m_status += GS_CAN_DEAL;
			}

		paintBK = true;
		paintBet = true;
		prepaint(betRect);

		paintBK = true;
		paintBalance = true;
		prepaint(balanceRect);
		repaint();
	}

/**************************************************************************
				void MinusBet(int i)

			Click on chip stack #i to reduce the stack
**************************************************************************/

	public void MinusBet(int i)
	{
/*
//		if(canResponse || playingMovie)
//			return;	

		if((m_bet - chipValue[i]) < 0) return;
		if(currentChips[i] == 0)
			return;
		
		m_bet -= chipValue[i];
		m_balance += chipValue[i];
		if(m_bet < dMIN) pBlackJack.m_canDeal = false;
//		sndPlaySound("wavs\\chips_dn.wav", SND_ASYNC);
		paintBK = true;
		repaint(tableRect);
*/
	}

/**************************************************************************
				void ClearBet()

			Sets the bet to zero
**************************************************************************/
	
	public void ClearBet() {
		auClear.play();
		m_balance += m_bet;
		m_bet = 0;
		paintBK = true;
		paintBet = true;
		prepaint(betRect);
		paintBK = true;
		paintBalance = true;
		prepaint(balanceRect);
		repaint();
		
		if(m_canDeal) {
			m_canDeal = false;
			m_status -= GS_CAN_DEAL;
			}
		}
		
/*************************************************************
			void GetStatePacket()
		
*************************************************************/

	void GetStatePacket() {
		try 
			{
			byteStatePacket = pBlackJack.GetStatePacket();
			}
		catch(Exception e) 
			{
			ErrorOut();
			return;
			}
		int state = byteStatePacket[0];
		m_status = state;
		int dollars = 0, cents = 0, x;

		m_canDeal = m_canHit = m_canStand = m_canDouble = m_canSplit = m_canInsurance = m_cashedOut = m_canBet = false;

		if((state & GS_CAN_BET) != 0) m_canBet = true;
		if((state & GS_CAN_HIT) != 0) m_canHit = true;
		if((state & GS_CAN_STAND) != 0) m_canStand = true;
		if((state & GS_CAN_DOUBLE) != 0) m_canDouble = true;
		if((state & GS_CAN_SPLIT) != 0) m_canSplit = true;
		if((state & GS_CAN_INSURANCE) != 0) m_canInsurance = true;
		if((state & GS_CASHED_OUT) != 0) m_cashedOut = true;
		
		for(x=3; x>=0; x--) {
			dollars <<= 8;
			dollars += (byteStatePacket[x + 1] < 0)? byteStatePacket[x + 1] + 256 : byteStatePacket[x + 1];
			}

		for(x=3; x>=0; x--) {
			cents <<= 8;
			cents += byteStatePacket[x + 5];
			}


		m_balance = (double) dollars + (double) cents / 100;
	
		return;
		}
/*************************************************************
			void StandStatePacket()
		Gets the state packet for Stand() responses
*************************************************************/

	void StandStatePacket() {
		m_player_upcard = byteStatePacket[9];
		m_player_value[m_hands] = byteStatePacket[10];
		m_dealer_value = byteStatePacket[11];
		m_dealer_ncards = byteStatePacket[12];

		for(int x=0; x<m_dealer_ncards; x++) {
			m_dealer_hand[x] = byteStatePacket[x + 13];
			}
		}

/**************************************************************************
			String ctos(int card)

		card to string for testing 
		to be replaced with a card image
**************************************************************************/

	public String ctos(int card) {
		String st1, st2;
		int mod, suit;
		st1 = null;
		st2 = null;
		suit = card / 13;
		mod = card % 13;
		switch(mod) {
			case 0: 
				st1 = "A";
				break;
			case 10: 
				st1 = "J";
				break;
			case 11: 
				st1 = "Q";
				break;
			case 12:
				st1 = "K";
				break;
			default:
				mod += 1;
				st1 = String.valueOf(mod);
			}
		switch(suit) {
			case 0:
				st2 = "D";
				break;
			case 1:
				st2 = "C";
				break;
			case 2:
				st2 = "H";
				break;
			case 3:
				st2 = "S";
				break;
			}
		return (new StringBuffer(st1 + st2)).toString();
		}

/************************************************************************
			String doubleToDollar(double d)		- Convert a double to a 
											a string that looks like a 
											dollar amount.
************************************************************************/

	String doubleToDollar(double d) {
		int iv = (int) d;
		double r = d - iv;
		r *= 100;
		int tenths = (int) r / 10;
		r -= tenths * 10;
		int hundredths = (int) r;
		return new String("$" + iv + "." + tenths + hundredths);
		}

/*************************************************************
			void FinishHand()
		Gets the state information for finishing a split hand
*************************************************************/

	void FinishHand() {
		m_player_upcard = byteStatePacket[9];
		m_player_ncards = byteStatePacket[10];
		m_player_value[m_hands] = byteStatePacket[11];
		
		if(m_player_value[m_hands] > 21) { // Bust
			PaintPlayerHit();
			auBust.play();
			try {
				Thread.sleep(1000);
				}
			catch(Exception e) {}
			}

		boolean pd = paintDoubleBetInPlay;
		paintDoubleBetInPlay = false;
				
		paintBK = true;

		if(m_hands == 1) {
			currentSplitSpacing -= 130;
			}

		else {
			 currentSplitSpacing = m_split_spacing[m_hands];
		 	 }

		Rectangle rc = new Rectangle(plrcardcoords.x + currentSplitSpacing, plrcardcoords.y, 
				playAreaRect.width - (plrcardcoords.x + currentSplitSpacing), plrCardHeight);
		
		prepaint(rc);
		paintDoubleBetInPlay = pd;
		currentSplitSpacing = StackCards();
		m_split_spacing[m_hands] = currentSplitSpacing; 
		currentSplitSpacing += plrCardWidth + 3;

//		PaintPlayerHit();
//		System.out.println("Player Value: "+m_player_value[m_hands] + " Hand #" +m_hands);

		m_hands = byteStatePacket[12];
		m_split_spacing[m_hands] = currentSplitSpacing;
		if(currentSplitSpacing < 0) currentSplitSpacing = 0;
		 
		m_player_downcard = byteStatePacket[13];
		m_player_upcard = byteStatePacket[14];
		m_player_value[m_hands] = byteStatePacket[15];
//		System.out.println("Player Value: "+m_player_value[m_hands] + " Hand #" +m_hands);
		m_player_ncards = 2;
//		currentSplitSpacing = (m_hands - 1) * splitSpacing;
		cardPoint1 = new Point(plrcardcoords.x + currentSplitSpacing, plrcardcoords.y);
		PrePaintCardLarge(m_player_downcard, true);
		if(m_nhands > m_hands) {
			for(int x = m_hands + 1; x <= m_nhands; x++) {
				cardPoint1 = new Point(plrcardcoords.x + currentSplitSpacing + 
							(x - m_hands) * splitSpacing, plrcardcoords.y);
				paintSplitBet = true;
				PrePaintCardLarge(m_split_card[x], true);
				}
			}

		PaintPlayerHit();
		}
			
/*************************************************************
			void FinishAll()
		Gets the state information for finishing a split game
*************************************************************/

	void FinishAll() {
		m_player_upcard = byteStatePacket[9];
		m_player_ncards = byteStatePacket[10];
		m_player_value[m_hands] = byteStatePacket[11];
		PaintPlayerHit();
		int x;
		for(x=0; x<m_nhands; x++) {
			m_hands_result[x + 1] = byteStatePacket[12 + x] + mlsreplyBase;
			}
		int curpos = 12 + m_nhands;
		m_dealer_value = byteStatePacket[curpos];
		m_dealer_ncards = byteStatePacket[curpos+1];
		curpos += 2;

		for(x=0; x<m_dealer_ncards; x++) {
			m_dealer_hand[x] = byteStatePacket[curpos + x];
			}
		PaintResult();
		}

/*************************************************************
			void ErrorOut()
		quits the game, delivers the error message
*************************************************************/

	void ErrorOut()
	{
		m_cashedOut = true;
		m_canHit = m_canStand = m_canDeal = m_canDouble = m_canSplit = m_canInsurance = false;
		paintError = true;
		repaint();
		
	}


//@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
//@@			Painting subroutines						@@
//@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@

/**************************************************************************
					void PaintResult()

				paint the result after hand(s) finished
**************************************************************************/

	void PaintResult()
	{
		if(m_nhands == 1)        // one hand only
		{
			PaintDealer();
			PaintPlayerHit();
	
			if(m_OutCome == 0) { // win
				auWin.play();
				}
			if(m_OutCome == 1) { 
				if(m_player_value[1] > 21) auBust.play(); // bust
				else auLose.play();	// lose
				}

			if(m_OutCome == 2) {  //blackjack
				auBj.play();
				}
			if(m_OutCome == 3) {  //push
				auPush.play();
				}
			paintResult = true;
			repaint(plrCardRect);
		}
		else    				//  multi hands and finished
		{
			GetHandsResult();
		}

		if(m_balance < dMIN)
		{
			try {
				Thread.sleep(1500);
				}
			catch(Exception e) {}

			CashOut();
			return;
		}

//		try {
//			Thread.sleep(1000);
//			}
//		catch(Exception e) {}

		ShowBetButtons();

// reset bet, balance, and chips	
		paintBK = true;
		paintBalance = true;
		prepaint(balanceRect);
		m_bet = 0;
		paintBet = true;
		paintBK = true;
		prepaint(betRect);
		repaint();

		if(bPageview) {
			iAdCycle++;
			if(iAdCycle == AD_FREQUENCY) {
				try {
					URL url = new URL(getDocumentBase(), "pageview.html");
					getAppletContext().showDocument(url, "ad");
					}
				catch(Exception e) {}
				iAdCycle = 0;
				}
			}
		System.gc();
	}

/**************************************************************************
						void PaintDealer()

					Paint the Dealer's cards
**************************************************************************/

	void PaintDealer()
	{
		if(m_dealer_value > 21)
			cDealerValueText = "Bust";
		if(m_dealer_value == 21 && m_dealer_ncards == 2)
			cDealerValueText = "Blackjack";
		if(m_dealer_value < 21 ||
			m_dealer_value == 21 && m_dealer_ncards > 2)
			cDealerValueText = String.valueOf(m_dealer_value);

		gbHit.hide();
		gbStand.hide();
		gbDouble.hide();
		gbSplit.hide();

		paintBK = true;
		prepaint(buttonPanelRect);
		paintBK = true;
		paintDlrCardBack = true;
		cardPoint1 = new Point(dlrcardcoords.x, dlrcardcoords.y);
		prepaint(dlrTableRect);
		cardPoint1 = new Point(dlrcardcoords.x + dlrFirstCardSpacingX, dlrcardcoords.y);
		PrePaintCardSmall(m_dealer_hand[0]);
		cardPoint1.x += dlrCardSpacingX;
		PrePaintCardSmall(m_dealer_hand[1]);
		auDeal.play();
		repaint();

		try {
			Thread.sleep(200);
			}
		catch(Exception e) {}
		
		int i;
		for(i = 2; i < (m_dealer_ncards - 1); i++)
		{

			cardPoint1 = new Point(dlrcardcoords.x + dlrFirstCardSpacingX + 
					i * dlrCardSpacingX,dlrcardcoords.y);
			PaintCardSmall(m_dealer_hand[i]);
			auDeal.play();
			try {
				Thread.sleep(200);
				}
			catch(Exception e) {}
		}
		
		if(i == m_dealer_ncards - 1) {
			cardPoint1 = new Point(dlrcardcoords.x + dlrFirstCardSpacingX + 
					i * dlrCardSpacingX,dlrcardcoords.y);
			paintDealerValue = true;
			PaintCardSmall(m_dealer_hand[i]);
			}
		else 
			{
			paintDealerValue = true;
			repaint();
			}
	}


/**************************************************************************
				void PaintCardSmall(int n)    Paint card # n (0 - 51)
**************************************************************************/

	void PaintCardSmall(int n)
	{

		paintCardSmall = true;
		iCardNo = n;
		repaint();
	}

/**************************************************************************
				void PaintCardLarge(int n, boolean first)    Paint card # n (0 - 51)
**************************************************************************/

	void PaintCardLarge(int n, boolean first)
	{
		paintCardLarge = true;
		iCardNo = n;
		bFirstCard = first;
		repaint();
	}

/**************************************************************************
				void PrePaintCardLarge(int n, boolean first) Paint card # n (0 - 51) 
						on backscreen
**************************************************************************/

	void PrePaintCardLarge(int n, boolean first)
	{
		Rectangle rc = new Rectangle(cardPoint1.x, cardPoint1.y, plrCardWidth, plrCardHeight);
		paintCardLarge = true;
		bFirstCard = first;
		iCardNo = n;
		prepaint(rc);
	}

/**************************************************************************
				void PrePaintCardSmall(int n, boolean first)    Paint card # n (0 - 51) 
						on backscreen
**************************************************************************/

	void PrePaintCardSmall(int n)
	{
		Rectangle rc = new Rectangle(cardPoint1.x, cardPoint1.y, dlrCardWidth, dlrCardHeight);
		paintCardSmall = true;
		iCardNo = n;
		prepaint(rc);
	}


/**************************************************************************
				void PaintPlayerDeal()   Paints the player's first 2 cards
**************************************************************************/

/*
	void PaintPlayerDeal()
	{

		if(m_player_value[m_hands] == 21) {
			if(m_nhands == 1) cPlayerValueText = "BlackJack";
			else cPlayerValueText = "21";
			}
		if(m_player_value[m_hands] < 21) {
			cPlayerValueText = String.valueOf(m_player_value[m_hands]);
			}
		currentSplitSpacing = (m_hands - 1) * splitSpacing;

		cardPoint1 = new Point(plrcardcoords.x + (m_player_ncards - 2) * plrCardSpacingX + currentSplitSpacing, 
									plrcardcoords.y + (m_player_ncards - 2) * plrCardSpacingY);
		paintPlayerValue = true;
		paintBetInPlay = true;
		PaintCardLarge(m_player_downcard, true);

		cardPoint1.x += plrCardSpacingX;
		cardPoint1.y += plrCardSpacingY;

		paintPlayerValue = true;
		paintBetInPlay = true;
		PaintCardLarge(m_player_upcard, false);
	}
*/

/*************************************************************
			void PaintPlayerScore(Graphics g)
				paints the player's score on top of the cards
*************************************************************/

	void PaintPlayerScore(Graphics g) {
		FontMetrics fm = g.getFontMetrics();
		int strWidth = fm.stringWidth(cPlayerValueText);
		int x = plrvalcoords.x + currentSplitSpacing + m_player_ncards * plrCardSpacingX;
		int y = plrvalcoords.y;

		g.setColor(Color.black);
		g.fillRect(x,y,strWidth,fm.getHeight());
		g.setColor(Color.white);
		g.drawString(cPlayerValueText,x,y + fm.getMaxAscent());
		}
	
/*************************************************************
			void PaintPlayerScore(Graphics g, int x, int y)
				paints the player's score at specified locus
*************************************************************/

	void PaintPlayerScore(Graphics g, int x, int y) {
		FontMetrics fm = g.getFontMetrics();
		int strWidth = fm.stringWidth(cPlayerValueText);
		g.setColor(Color.black);
		g.fillRect(x,y,strWidth,fm.getHeight());
		g.setColor(Color.white);
		g.drawString(cPlayerValueText,x,y + fm.getMaxAscent());
		}
	
/*************************************************************
			void PaintDealerScore(Graphics g)
				paints the dealer's score on top of the cards
*************************************************************/

	void PaintDealerScore(Graphics g) {
		FontMetrics fm = g.getFontMetrics();
		int strWidth = fm.stringWidth(cDealerValueText);
		g.setColor(Color.black);
		g.fillRect(dlrvalcoords.x + m_dealer_ncards * dlrCardSpacingX,dlrvalcoords.y,strWidth,fm.getHeight());
		g.setColor(Color.white);
		g.drawString(cDealerValueText,dlrvalcoords.x + m_dealer_ncards * dlrCardSpacingX,dlrvalcoords.y + fm.getMaxAscent());
		}

/*************************************************************
			void PaintResultText(Graphics g, boolean flash)
				paints the game outcome on top of the cards
*************************************************************/

	void PaintResultText(Graphics g, boolean flash) {
		FontMetrics fm = g.getFontMetrics();
		int strWidth = fm.stringWidth(cResultText);
		g.setColor(Color.black);
		g.fillRect(resultcoords.x + currentSplitSpacing,resultcoords.y,strWidth,fm.getHeight());

		if(!flash) g.setColor(Color.white);
		else g.setColor(Color.red);

		g.drawString(cResultText,resultcoords.x + currentSplitSpacing,resultcoords.y + fm.getMaxAscent());
		}
				
/*************************************************************
			void PaintPlayerBet(Graphics g)
				paints the player's bet on top of the cards
*************************************************************/

	void PaintPlayerBet(Graphics g) {
		g.setFont(new Font("Arial", 0,12));
		FontMetrics fm = g.getFontMetrics();
		String str = doubleToDollar(m_bet);
		int strWidth = fm.stringWidth(str);
		g.setColor(Color.yellow);
		g.fillOval(betinplaycoords.x + currentSplitSpacing,betinplaycoords.y,strWidth,strWidth);
		g.setColor(Color.blue);
		g.drawString(str,betinplaycoords.x + currentSplitSpacing,betinplaycoords.y + fm.getMaxAscent() / 2 + strWidth / 2);
		}

/*************************************************************
			void PaintPlayerBet(Graphics g, int x, int y)
				paints the player's bet on top of the cards
*************************************************************/

	void PaintPlayerBet(Graphics g, int x, int y) {
		g.setFont(new Font("Arial", 0,12));
		FontMetrics fm = g.getFontMetrics();
		String str = doubleToDollar(m_bet);
		int strWidth = fm.stringWidth(str);
		g.setColor(Color.yellow);
		g.fillOval(x,y,strWidth,strWidth);
		g.setColor(Color.blue);
		g.drawString(str, x, y + fm.getMaxAscent() / 2 + strWidth / 2);
		}

/*************************************************************
			void PaintPlayerBetDouble(Graphics g)
				paints the player's bet 2x on top of the cards
*************************************************************/

	void PaintPlayerBetDouble(Graphics g) {
		g.setFont(new Font("Arial", 0,12));
		FontMetrics fm = g.getFontMetrics();
		String str = doubleToDollar(m_bet);
		int strWidth = fm.stringWidth(str);
		g.setColor(Color.yellow);
		g.fillOval(betinplaycoords.x + currentSplitSpacing,betinplaycoords.y,strWidth,strWidth);
		g.fillOval(betinplaycoords.x - (strWidth + 5) + currentSplitSpacing,betinplaycoords.y,strWidth,strWidth);
		g.setColor(Color.blue);
		g.drawString(str,betinplaycoords.x + currentSplitSpacing,betinplaycoords.y + fm.getMaxAscent() / 2 + strWidth / 2);
		g.drawString(str,betinplaycoords.x - (strWidth + 5) + currentSplitSpacing,betinplaycoords.y + fm.getMaxAscent() / 2 + strWidth / 2);

		}

/*************************************************************
			void PaintPlayerBetDouble(Graphics g, int x, int y)
				paints the player's bet 2x on top of the cards
*************************************************************/

	void PaintPlayerBetDouble(Graphics g, int x, int y) {
		g.setFont(new Font("Arial", 0,12));
		FontMetrics fm = g.getFontMetrics();
		String str = doubleToDollar(m_bet * 2);
		int strWidth = fm.stringWidth(str);
		g.setColor(Color.yellow);
		g.fillOval(x,y,strWidth,strWidth);
		g.setColor(Color.blue);
		g.drawString(str, x, y + fm.getMaxAscent() / 2 + strWidth / 2);
		}

/**************************************************************************
				void PaintPlayerHit()   Paints the player's next card
**************************************************************************/

	void PaintPlayerHit()
	{

		if(m_player_value[m_hands] > 21)
			cPlayerValueText =  "Bust";
		if(m_player_value[m_hands] == 21 && m_player_ncards == 2 && m_nhands == 1)
			cPlayerValueText = "BlackJack";
		if(m_player_value[m_hands] < 21 || m_player_value[m_hands] == 21 && 
			(m_player_ncards > 2 || m_nhands > 1))
			cPlayerValueText = String.valueOf(m_player_value[m_hands]);

//		System.out.println("Player Value "+ m_player_value[m_hands] + " Hand: " + m_hands);

		cardPoint1 = new Point(plrcardcoords.x + (m_player_ncards - 1) * plrCardSpacingX + currentSplitSpacing, 
									plrcardcoords.y + (m_player_ncards - 1) * plrCardSpacingY);
		paintPlayerValue = true;
		paintBetInPlay = true;
		PaintCardLarge(m_player_upcard, false);

	}

/*************************************************************
			void PaintDeal()
		Paints the initial dealing of the hand
*************************************************************/

	void PaintDeal() {
		currentSplitSpacing = (m_hands - 1) * splitSpacing;

		cardPoint1 = new Point(plrcardcoords.x + (m_player_ncards - 2) * plrCardSpacingX + currentSplitSpacing, 
									plrcardcoords.y + (m_player_ncards - 2) * plrCardSpacingY);
		auDeal.play();
		PaintCardLarge(m_player_downcard, true);
		try {
			Thread.sleep(200);
			}
		catch(Exception e) {}

		cardPoint1 = new Point(dlrcardcoords.x, dlrcardcoords.y);
		paintDlrCardBack = true;
		auDeal.play();
		repaint();
		try {
			Thread.sleep(200);
			}
		catch(Exception e) {}

		auDeal.play();
		PaintPlayerHit();
		try {
			Thread.sleep(200);
			}
		catch(Exception e) {}

		cardPoint1 = new Point(dlrcardcoords.x + dlrFirstCardSpacingX + dlrCardSpacingX, dlrcardcoords.y);
		auDeal.play();
		PaintCardSmall(m_dealer_hand[1]);
		}

/*************************************************************
			int StackCards()
		Stacks the Cards for a split to make more room,
		returns the x pixel value for the next card to sit
*************************************************************/

	int StackCards() {
		int x;
		boolean pdbip = paintDoubleBetInPlay;
		paintDoubleBetInPlay = false;

		cardPoint1 = new Point(plrcardcoords.x + currentSplitSpacing, plrcardcoords.y);
		if(m_hands == 1) {
			PrePaintCardLarge(m_player_upcard,true);
			}
		else {
			PrePaintCardLarge(m_player_upcard,false);
			}

		cardPoint1.x += 4;
		for(x=1; x<m_player_ncards - 1; x++) {
			PrePaintCardLarge(m_player_upcard,false);
			cardPoint1.x += 4;
			}
		
		int temp_pncards = m_player_ncards;
		m_player_ncards = 1;

		if(m_player_value[m_hands] > 21)
			cPlayerValueText =  "Bust";
		if(m_player_value[m_hands] == 21 && m_player_ncards == 2 && m_nhands == 1)
			cPlayerValueText = "BlackJack";
		if(m_player_value[m_hands] < 21 || m_player_value[m_hands] == 21 && m_player_ncards > 2)
			cPlayerValueText = String.valueOf(m_player_value[m_hands]);

		paintPlayerValue = true;

		paintSplitBet = true;
		paintDoubleBetInPlay = pdbip;

		PrePaintCardLarge(m_player_upcard, false);
		
		m_player_ncards = temp_pncards;

		return cardPoint1.x - plrcardcoords.x;
		}

//@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
//@@			Graphic button subroutines					@@
//@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
/*************************************************************
			void HideAllButtons();
				
			hides all the buttons
*************************************************************/

	void HideAllButtons() {
		int x;
		for(x=0; x<3; x++) {
			gbChip[x].hide();
			}
		gbDeal.hide();
		gbClear.hide();
		gbInsurance.hide();
		gbNoInsurance.hide();
		gbHit.hide();
		gbStand.hide();
		gbSplit.hide();
		gbDouble.hide();
		gbInsurance.hide();
		gbNoInsurance.hide();
		}

/*************************************************************
			void ShowPlayButtons();
				
				hides the betting and Insurance stuff,
				shows the play buttons
*************************************************************/

	void ShowPlayButtons() {
//		int x;
//		for(x=0; x<6; x++) {
//			gbChip[x].hide();
//			}
//		gbDeal.hide();
//		gbClear.hide();
//		gbInsurance.hide();
//		gbNoInsurance.hide();

		gbHit.gTransSwap.drawImage(offscrim,-gbHit.m_x, -gbHit.m_y, null); 
		gbHit.show();
		gbStand.show();
		gbDouble.show();
		gbSplit.show();
		paintBK = true;
		prepaint(betRect);
		paintBK = true;
		prepaint(buttonPanelRect);
		repaint();
		}

/*************************************************************
			void ShowBetButtons();
				
				shows the betting stuff,
				hides the play and Insurance buttons
*************************************************************/
	
	void ShowBetButtons() {
		int x;
		for(x=0; x<3; x++) {
			gbChip[x].show();
			}
		gbDeal.show();
		gbClear.gTransSwap.drawImage(offscrim,-gbClear.m_x, -gbClear.m_y, null); 
		gbClear.show();
		gbInsurance.hide();
		gbNoInsurance.hide();
		gbHit.hide();
		gbStand.hide();
		gbDouble.hide();
		gbSplit.hide();
		prepaint(clearRect);
		paintBK = true;
		prepaint(buttonPanelRect);
		repaint();
		}

/*************************************************************
			void ShowInsuranceButtons();
				
				shows the betting stuff,
				hides the play and Insurance buttons
*************************************************************/

	void ShowInsuranceButtons() {
		int x;
		for(x=0; x<3; x++) {
			gbChip[x].hide();
			}
		gbDeal.hide();
		gbClear.hide();
		paintBK = true;
		prepaint(betRect);
		paintBK = true;
		paintInsurance = true;
		prepaint(buttonPanelRect);
		gbInsurance.show();
		gbNoInsurance.show();
		prepaint(insuranceRect);
		prepaint(noInsuranceRect);
		repaint();
		}

/*************************************************************
			void ShowCashOutButtons();
				
				shows the betting stuff,
				hides the play and Insurance buttons
*************************************************************/

	void ShowCashOutButtons() {
		int x;
		for(x=0; x<3; x++) {
			gbChip[x].hide();
			}
		gbDeal.hide();
		gbClear.hide();
		paintBK = true;
		prepaint(betRect);
		gbInsurance.hide();
		gbNoInsurance.hide();
		prepaint(insuranceRect);
		prepaint(noInsuranceRect);
		gbCashOutYes.show();
		gbCashOutNo.show();
		prepaint(cashOutAreaRect);
		repaint();
		}

/*************************************************************
			void addGB(GraphicButton gb);
				
		Add graphic buttons to the vector
		use in place of add
*************************************************************/

	void addGB(GraphicButton gb) {
		vectorGB.addElement((Object)gb);
		}	
		
/*************************************************************
			void paintGB(Graphics g);

		paints the graphic buttons
*************************************************************/

	void paintGB(Graphics g){
		
		int x;
		GraphicButton gb;
		Image im;

		for(x=0; x<vectorGB.size(); x++) {
			gb = (GraphicButton) vectorGB.elementAt(x);
			im = gb.getImage();
			if(im != null) {
				if(gb.bTransparent) {
					g.drawImage(gb.imageTransSwap,gb.m_x, gb.m_y, null);
					}
				g.drawImage(im, gb.m_x, gb.m_y,null);
				}
			}
		}

//@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
//@@			Interface command routines					@@
//@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@

/**************************************************************************
				void Deal()  press the Deal button
**************************************************************************/

	void Deal()
	{
		if(!m_canDeal)
			return;	

		HideAllButtons();
		paintBK = true;
		prepaint(buttonPanelRect);
	
		m_nhands = 1;
		m_hands = 1;
		currentSplitSpacing = 0;
		m_player_ncards = 2;
		m_dealer_ncards = 2;
		paintBK = true;
		paintMinMax = true;
		prepaint(plrCardRect);
		paintBK = true;
		prepaint(dlrTableRect);
		repaint();

		int result = mlsreplyError;
		try {
			result = pBlackJack.Deal(m_bet);
			}
		catch(Exception e) 
			{
			ErrorOut();
			return;
			}
		GetStatePacket();

		HideAllButtons();
		
		if(result == IN_PLAY) {			    		
				//paint dealer first 2 cards
	
				m_player_downcard = (int) byteStatePacket[9];
				m_player_upcard = (int) byteStatePacket[10];
				m_player_value[1] = (int) byteStatePacket[11];
				m_dealer_hand[1] = (int) byteStatePacket[12];

				PaintDeal();
				ShowPlayButtons();
				}
		else if(result == WIN) {
				m_player_downcard = (int) byteStatePacket[9];
				m_player_upcard = (int) byteStatePacket[10];
				m_dealer_hand[0] = (int) byteStatePacket[11];
				m_dealer_hand[1] = (int) byteStatePacket[12];
				m_dealer_value = (int) byteStatePacket[13];
				m_player_value[1] = 21;

				PaintDeal();
				cResultText = "WIN";
				m_OutCome = 2;
				PaintResult();
				}
		else if(result == PUSH) {			     
				m_player_downcard = (int) byteStatePacket[9];
				m_player_upcard = (int) byteStatePacket[10];
				m_dealer_hand[0] = (int) byteStatePacket[11];
				m_dealer_hand[1] = (int) byteStatePacket[12];
				m_dealer_value = 21;
				m_player_value[1] = 21;

				PaintDeal();
				cResultText = "PUSH";
				m_OutCome = 3;
				PaintResult();
				}
		else if(result == INSURANCE) {
				m_player_downcard = (int) byteStatePacket[9];
				m_player_upcard = (int) byteStatePacket[10];
				m_player_value[1] = (int) byteStatePacket[11];
				m_dealer_hand[1] = (int) byteStatePacket[12];
							
				PaintDeal();

				// ask for insurance

				ShowInsuranceButtons();
				}
		else if(result == LOSE) { // Dealer blackjack			
				m_player_downcard = (int) byteStatePacket[9];
				m_player_upcard = (int) byteStatePacket[10];
				m_dealer_hand[0] = (int) byteStatePacket[11];
				m_dealer_hand[1] = (int) byteStatePacket[12];
				m_player_value[1] = (int) byteStatePacket[13];
				m_dealer_value = 21;

				PaintDeal();
				cResultText = "LOSE";
				m_OutCome = 1;
				PaintResult();
				
		}

	}

/**************************************************************************
				void Hit()

			press the Hit button
**************************************************************************/

	void Hit()
	{
		if(!m_canHit)
			return;

		if(m_player_value[m_hands] == 21)
			return;
		
		auHit.play();

		int result = mlsreplyError;
		try {
			result = pBlackJack.Hit();
			}
		catch(Exception e) 
			{
			ErrorOut();
			return;
			}
		GetStatePacket();
			
		if(result == BUST) {
				m_player_upcard = (int) byteStatePacket[9];
				m_player_ncards = (int) byteStatePacket[10];
				m_dealer_hand[0] = (int) byteStatePacket[11];
				m_dealer_hand[1] = (int) byteStatePacket[12];
				m_dealer_value = (int) byteStatePacket[13];
				m_player_value[1] = 22;
				m_dealer_ncards = 2;
				PaintPlayerHit();
				cResultText = "LOSE";
				m_OutCome = 1;
				PaintResult();
				}

		if(result == FINISH_ALL) { // last hand bust
				FinishAll();
			}
		if(result == FINISH_HAND) { 
				FinishHand();
				}

		if(result == IN_PLAY) {
				m_player_upcard = (int) byteStatePacket[9];
				m_player_ncards = (int) byteStatePacket[10];
				m_player_value[m_hands] = (int) byteStatePacket[11];
				PaintPlayerHit();
				}
	
    
	}

/**************************************************************************
					void Stand()

				press the Stand button
**************************************************************************/

	void Stand()
	{
		if(!m_canStand)
			return;

		auStand.play();

		int result = mlsreplyError;
		try {
			result = pBlackJack.Stand();
			}
		catch(Exception e) 
			{
			ErrorOut();
			return;
			}
		GetStatePacket();
			
		if(result == WIN) 
			{
				StandStatePacket();
				cResultText = "WIN";
				m_OutCome = 0;
			    PaintResult();
			}
		else if(result == PUSH) 
			{
				StandStatePacket();
				cResultText = "PUSH";
				m_OutCome = 3;
			    PaintResult();
			}
						
		else if(result == LOSE) 
			{
				StandStatePacket();
				cResultText = "LOSE";
				m_OutCome = 1;
			    PaintResult();
			}
		else if(result == FINISH_HAND) 
			{
				FinishHand();
			}
			
		else if(result == FINISH_ALL) 
			{
				FinishAll();
			}

	}

/**************************************************************************
					void Split()

				press the Split button
**************************************************************************/

	void Split()
	{
	    if(!m_canSplit)
		 	return;
    
		int downcard;

		auSplit.play();

		int result = mlsreplyError;
		try {
			result = pBlackJack.Split();
			}
		catch(Exception e) 
			{
			System.out.println("Error - " + e.toString());

			ErrorOut();
			return;
			}
		GetStatePacket();
			
		if(result == SPLIT)
		{
				paintBK = true;
				paintBalance = true;
				prepaint(balanceRect);
				
				Rectangle cr = new Rectangle(plrcardcoords.x + currentSplitSpacing + 
						plrCardSpacingX, plrcardcoords.y + plrCardSpacingY,
						plrCardWidth, plrCardHeight);
				paintBK = true;
				prepaint(cr);
				
				cardPoint1 = new Point(plrcardcoords.x + currentSplitSpacing, 
									plrcardcoords.y);

				PrePaintCardLarge(m_player_downcard, true);

				m_nhands = byteStatePacket[9];
				downcard = byteStatePacket[10];
				m_player_upcard = byteStatePacket[11];
				m_player_value[m_hands] = byteStatePacket[12];

				currentSplitSpacing += (m_nhands - m_hands) * splitSpacing;
				cardPoint1 = new Point(plrcardcoords.x + currentSplitSpacing,
						plrcardcoords.y);
				paintSplitBet = true;
				PrePaintCardLarge(downcard, true);
				m_split_card[m_nhands] = downcard;
				m_split_spacing[m_nhands] = currentSplitSpacing;

				repaint();

				currentSplitSpacing -= (m_nhands - m_hands) * splitSpacing;
				PaintPlayerHit();
		}
		// result == IN_PLAY means there's no money to split.
	}

/**************************************************************************
					void Double()

			press the Double button
**************************************************************************/

	void Double()
	{
		double mbal, mbal2;

		if(!m_canDouble)
	    	return;
		
		if(m_balance < m_bet) return;

		auDouble.play();
		
		int result = mlsreplyError;
		try {
			result = pBlackJack.Double();
			}
		catch(Exception e) 
			{
			ErrorOut();
			return;
			}

		mbal = m_balance;

		GetStatePacket();

		mbal2 = m_balance;
		
		m_balance = mbal;

		m_balance -= m_bet;
			
		paintBK = true;
		paintBalance = true;

		prepaint(balanceRect);

		if(result == WIN) {
				StandStatePacket();
				m_player_ncards = 3;
				paintDoubleBetInPlay = true;
				PaintPlayerHit();
				cResultText = "WIN";
				m_OutCome = 0;
				m_balance = mbal2;
    			PaintResult();
			}
		else if(result == PUSH) {
				StandStatePacket();
				m_player_ncards = 3;
				paintDoubleBetInPlay = true;
				PaintPlayerHit();
				cResultText = "PUSH";
				m_OutCome = 3;
				m_balance = mbal2;
    			PaintResult();
			}
		else if(result == BUST) {
				m_player_upcard = (int) byteStatePacket[9];
				m_player_ncards = (int) byteStatePacket[10];
				m_dealer_hand[0] = (int) byteStatePacket[11];
				m_dealer_hand[1] = (int) byteStatePacket[12];
				m_dealer_value = (int) byteStatePacket[13];
				m_player_value[1] = 22;
				m_dealer_ncards = 2;
				paintDoubleBetInPlay = true;
				PaintPlayerHit();
				cResultText = "LOSE";
				m_OutCome = 1;
				m_balance = mbal2;
    			PaintResult();
			}
		else if(result == LOSE) {				
				StandStatePacket();
				m_player_ncards = 3;
				paintDoubleBetInPlay = true;
				PaintPlayerHit();
				cResultText = "LOSE";
				m_OutCome = 1;
				m_balance = mbal2;
    			PaintResult();
			}
		else if(result == IN_PLAY) {
				m_balance = mbal2;
				return; 
			}
		
		else if(result == FINISH_HAND) {
				paintDoubleBetInPlay = true;
				m_player_upcard = byteStatePacket[9];
				m_player_ncards = byteStatePacket[10];
				m_player_value[m_hands] = byteStatePacket[11];
				PaintPlayerHit();
				try {
					Thread.sleep(1000);
					}
				catch(Exception e) {}
				paintDoubleBetInPlay = true;
				m_balance = mbal2;
				FinishHand();
				return; 
			}			                           
		else if(result == FINISH_ALL) {
				paintDoubleBetInPlay = true;
				m_balance = mbal2;
				FinishAll();
			}
    
	}

/**************************************************************************
					void Insurance()

				choose Insurance
**************************************************************************/

	void Insurance()
	{
		if(!m_canInsurance || (m_balance < m_bet))
			return;
				
		int result = mlsreplyError;
		try {
			result = pBlackJack.Insurance();
			}
		catch(Exception e)
			{
			ErrorOut();
			return;
			}

		GetStatePacket();
	
		HideAllButtons();
				
		if(result == LOSE) 
		{
			m_dealer_hand[0] = byteStatePacket[9];
			m_dealer_value = 21;
			cResultText = "LOSE";
			m_OutCome = 1;
			PaintResult();
//			sndPlaySound("wavs\\lose1.wav", SND_SYNC);
		}
		else        // dealer is not BlackJack, continue
		{
			paintBK = true;
			ShowPlayButtons();
		}
	}

/**************************************************************************
					void NoInsurance()

				choose not to have insurance
**************************************************************************/

	void NoInsurance()
	{
		if(!m_canInsurance)
			return;	

		int result = mlsreplyError;
		try {
			result = pBlackJack.NoInsurance();
			}
		catch(Exception e)
			{
			ErrorOut();
			return;
			}

		GetStatePacket();
		
		HideAllButtons();
			
		if(result == LOSE) 
		{
			m_dealer_hand[0] = byteStatePacket[9];
			m_dealer_value = 21;
			cResultText = "LOSE";
			m_OutCome = 1;
			PaintResult();
		}
		else ShowPlayButtons();
	}

	/****************************************************************
    *                                                               *
    * NAME:	Details (method)										*
    *                                                               *
    ****************************************************************/

	private void Details()
		{
		hide() ;
		removeAll() ;
		paintClear = true ;
//		repaint() ;
		setLayout(null) ;
/*
		if (SlotMachine.dCashPaid >= MAXIMUM_PAYOUT)
			{
			label2 = new Label("Sorry, this slot machine has expired, but...") ;
			add(label2) ;
			label2.reshape(0, 23, 350, 23) ;
			}
*/
		label1 = new Label("Congratulations!") ;
		add(label1) ;
		label1.reshape(0, 6, 500, 23) ;
		label3 = new Label("You're eligible to enter the free prize drawing.") ;
		add(label3) ;
		label3.reshape(0, 29, 350, 23) ;
		label4 = new Label("Please enter some simple details so we know who you are") ;
		add(label4) ;
		label4.reshape(0, 52, 350, 23) ;
		label5 = new Label("and how we can contact you, should you win.") ;
		add(label5) ;
		label5.reshape(0, 75, 350, 23) ;

		label8=new Label("You're cash-out balance for this game is " + doubleToDollar(m_balance) + " credits.") ;
		add(label8) ;
		label8.reshape(0, 98, 400, 23) ;

        label6=new Label("Player Name");
        add(label6);
        label6.reshape(0,126,110,23);

        editName=new TextField(64) ;
        add(editName) ;
		editName.setText(sPlayerName) ;
        editName.reshape(112,126,300,22) ;

        label7=new Label("e-mail Address");
        add(label7);
        label7.reshape(0,149,110,22);

        editEMail=new TextField(64) ;
        add(editEMail) ;
		editEMail.setText(sPlayerEMail) ;
        editEMail.reshape(112,149,300,22) ;

        buttonSend=new Button("I wanna win!") ;
        add(buttonSend);
        buttonSend.reshape(50,185,350,40) ;

		buttonNoThanks=new Button("No Thanks! I've got more toys than I know what to do with") ;
		add(buttonNoThanks) ;
		buttonNoThanks.reshape(50, 235, 350, 26) ;
		show() ;
		}


	/****************************************************************
    *                                                               *
    * NAME:	clickedbuttonSend (method)								*
    *                                                               *
    ****************************************************************/

	private void clickedbuttonSend()
		{
		String	sMsg ;

		long	lHours,
				lMinutes,
				lSeconds ;

//		lHours = lGameTotalTime / 3600 ;
//		lMinutes = lGameTotalTime / 60 % 60 ;
//		lSeconds = lGameTotalTime % 60 ;

		paintClear = false ;
		sPlayerName = editName.getText() ;
		sPlayerEMail = editEMail.getText() ;
		setForeground(Color.white) ;
		setBackground(Color.black) ;
		removeAll() ;
//		ClearScreen() ;
//		nGameState = GS_NETWORK ;
		bSendingData = true ;
//		Message(MSG_WAIT, true) ;
		SendMail smPost = new SendMail() ;
		smPost.SetSenderHost(sEMailBase) ;
		smPost.SetMailHost(sEMailHost) ;
		smPost.SetMessage("Player " + sPlayerName + " (" + sPlayerEMail +") won $" + doubleToDollar(m_balance)) ;
//		smPost.AddMessageLine("Graphic download time was " + (int)(lDownloadTotalTime / 1000) + " seconds") ;
//		smPost.AddMessageLine("Game time " + lHours / 10 + lHours % 10 + ":" + lMinutes / 10 + lMinutes % 10 +":" + lSeconds / 10 + lSeconds % 10) ;
		smPost.AddMessageLine("Local time is " + new Date()) ;
//		smPost.AddMessageLine("IP: " + InetAddress.getLocalHost().toString()) ;
		smPost.AddMessageLine("Version: " + VERSION_MAJOR + "." + VERSION_MINOR) ;
		try
			{
			smPost.AddMessageLine("Vendor: " + System.getProperty("java.version") + " " + System.getProperty("java.vendor") + " " + System.getProperty("java.vendor.url")) ;
			}

		catch (Exception e)
			{
			}

		try
			{
			smPost.AddMessageLine("OS: " + System.getProperty("os.name") + " " + System.getProperty("os.arch") + " " + System.getProperty("os.version")) ;
			}

		catch (Exception e)
			{
			}

		if (m_balance >= 1000)
			{
			smPost.SetRecipient(sEMailName4 + "@" + sEMailBase) ;
			smPost.SetSenderName(sEMailName4) ;
			}
		else if (m_balance >= 750)
			{
			smPost.SetRecipient(sEMailName3 + "@" + sEMailBase) ;
			smPost.SetSenderName(sEMailName3) ;
			}
		else if (m_balance >= 500)
			{
			smPost.SetRecipient(sEMailName2 + "@" + sEMailBase) ;
			smPost.SetSenderName(sEMailName2) ;
			}
		else if (m_balance >= 250)
			{
			smPost.SetRecipient(sEMailName1 + "@" + sEMailBase) ;
			smPost.SetSenderName(sEMailName1) ;
			}

		if (!smPost.Send())
			{
//			Message(MSG_EMAIL_ERROR, true) ;
			bEMailError = true ;
//			nGameState = GS_ERROR ;
			PlayAgainOption() ;
			}
		else
			bFinished = true ;
//			GameOver() ;

		smPost = null ;
//		SlotMachine.SetCash(0.0) ;
		}


	/****************************************************************
    *                                                               *
    * NAME:	clickedbuttonNoThanks (method)							*
    *                                                               *
    ****************************************************************/

	private void clickedbuttonNoThanks()
		{
		paintClear = false ;
		bFinished = true ;
//		GameOver() ;
		}


	/****************************************************************
    *                                                               *
    * NAME:	clickedbuttonPlayAgain (method)							*
    *                                                               *
    ****************************************************************/

	private void clickedbuttonPlayAgain()
		{
		paintClear = false ;
		hide() ;
		removeAll() ;
//		ClearScreen() ;
//		CreateMachine() ;
//		nGameState = GS_WAITING ;
//		DisplayInterface() ;
//		EnableButtons() ;
//		show() ;
//		lGraphicBits = GB_ALLBITS ;
		}


	/****************************************************************
    *                                                               *
    * NAME:	handleEvent (event)										*
    *                                                               *
    ****************************************************************/

    public boolean handleEvent(Event event)
    	{
        if (event.id == Event.ACTION_EVENT)
			{
			if (event.target == buttonSend)
				{
				clickedbuttonSend() ;

	            return (true) ;
				}
			else if (event.target == buttonNoThanks)
				{
				clickedbuttonNoThanks() ;

				return (true) ;
				}
			else if (event.target == buttonPlayAgain)
				{
				clickedbuttonPlayAgain() ;

				return (true) ;
				}

			}


        return (super.handleEvent(event)) ;
	    }


	/****************************************************************
    *                                                               *
    * NAME:	PlayAgainOption (method)								*
    *                                                               *
    ****************************************************************/

	private void PlayAgainOption()
		{
		hide() ;
		setLayout(null) ;

		buttonPlayAgain = new Button("Play It Again!") ;
		add(buttonPlayAgain) ;
		buttonPlayAgain.reshape(50, 350, 340, 50) ;

		show() ;
		}


/************************************************************************
			void CashOut()		- Cash out the user
************************************************************************/

	void CashOut() {
		if(m_balance < dMIN) {
			cashoutString = "Sorry, you are below the table minimum.";
			}
		else {
			cashoutString = new String("Balance: " + doubleToDollar(m_balance));
			}


		try {
			pBlackJack.Logoff();
			}
		catch(Exception e) {}
			
		m_balance = 0f;
		m_bet = 0;
		
		auCashout.play();
		bCashedOut = true;
		HideAllButtons();
		paintBK = true;
		repaint();
		Details() ;
//		Graphics g = getGraphics();
//		g.setColor(Color.white);

//		g.drawString(cashoutString,plrcardcoords.x,plrcardcoords.y);
//		g.drawString(cashoutString2,plrcardcoords.x,plrcardcoords.y + 20);

//		pBlackJack.cashOut();
		}

//@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
//@@			Initialization subroutines					@@
//@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@

/***********************************************************************
		void MakeButtons();
				Create the buttons for low bandwidth version
***********************************************************************/

void MakeButtons() {
	Image button1, button2, button3;
	Graphics g1,g2,g3;
	for(int x=0; x<3; x++) {
		button1 = createImage(chipAddRects[x].width, chipAddRects[x].height);
		button2 = createImage(chipAddRects[x].width, chipAddRects[x].height);
		button3 = createImage(chipAddRects[x].width, chipAddRects[x].height);
		g1 = button1.getGraphics();
		g2 = button2.getGraphics();
		g3 = button3.getGraphics();
	
		g1.drawImage(imageBetButtonParts,-chipMediaOriginX[x],-141,null);
		g2.drawImage(imageBetButtonParts,-chipMediaOriginX[x],-157,null);
		g3.drawImage(imageBetButtonParts,-chipMediaOriginX[x],-172,null);
		
		
		gbChip[x] = new GraphicButton(chipAddRects[x].x, chipAddRects[x].y, chipAddRects[x].width,
						chipAddRects[x].height,button1, button3, button2, this, false);

		addGB(gbChip[x]);
		gbChip[x].hide();
		gbChip[x].m_status_mask = GS_CAN_BET;
		}
	


	button1 = createImage(dealRect.width, dealRect.height);
	button2 = createImage(dealRect.width, dealRect.height);
	button3 = createImage(dealRect.width, dealRect.height);
	g1 = button1.getGraphics();
	g2 = button2.getGraphics();
	g3 = button3.getGraphics();

	g1.drawImage(imageBetButtonParts,0,0,null);
	g2.drawImage(imageBetButtonParts,0,-31,null);
	g3.drawImage(imageBetButtonParts,0,-62,null);
		
	gbDeal = new GraphicButton(dealRect.x, dealRect.y, dealRect.width, dealRect.height,
				button1,button3,button2,this, false);

	addGB(gbDeal);
	gbDeal.hide();						

	imageBetButtonParts.flush();		

//	button1 = createImage(hitRect.width, hitRect.height);
//	button2 = createImage(hitRect.width, hitRect.height);
//	button3 = createImage(hitRect.width, hitRect.height);
//	g1 = button1.getGraphics();
//	g2 = button2.getGraphics();
//	g3 = button3.getGraphics();

//	g1.drawImage(imageButtonParts,0,-92,null);
//	g2.drawImage(imageButtonParts,0,-108,null);
//	g3.drawImage(imageButtonParts,0,-124,null);
		
	gbHit = new GraphicButton(hitRect.x, hitRect.y, hitRect.width, hitRect.height,
				imageHit, imageHitS, imageHitHL,this, true);
	addGB(gbHit);
	gbHit.hide();

	button1 = createImage(standRect.width, standRect.height);
	button2 = createImage(standRect.width, standRect.height);
	button3 = createImage(standRect.width, standRect.height);
	g1 = button1.getGraphics();
	g2 = button2.getGraphics();
	g3 = button3.getGraphics();

	g1.drawImage(imageButtonParts,0,0,null);
	g2.drawImage(imageButtonParts,0,-31,null);
	g3.drawImage(imageButtonParts,0,-62,null);
		
	gbStand = new GraphicButton(standRect.x, standRect.y, standRect.width, standRect.height,
				button1,button3,button2,this, false);

	addGB(gbStand);
	gbStand.hide();

	button1 = createImage(splitRect.width, splitRect.height);
	button2 = createImage(splitRect.width, splitRect.height);
	button3 = createImage(splitRect.width, splitRect.height);
	g1 = button1.getGraphics();
	g2 = button2.getGraphics();
	g3 = button3.getGraphics();
	g1.drawImage(imageButtonParts,-52,-141,null);
	g2.drawImage(imageButtonParts,-52,-157,null);
	g3.drawImage(imageButtonParts,-52,-172,null);
		
	gbSplit = new GraphicButton(splitRect.x, splitRect.y, splitRect.width, splitRect.height,
				button1,button3,button2,this, false);
	addGB(gbSplit);
	gbSplit.hide();

	button1 = createImage(doubleRect.width, doubleRect.height);
	button2 = createImage(doubleRect.width, doubleRect.height);
	button3 = createImage(doubleRect.width, doubleRect.height);
	g1 = button1.getGraphics();
	g2 = button2.getGraphics();
	g3 = button3.getGraphics();

	g1.drawImage(imageButtonParts,0,-141,null);
	g2.drawImage(imageButtonParts,0,-157,null);
	g3.drawImage(imageButtonParts,0,-172,null);
		
	gbDouble = new GraphicButton(doubleRect.x, doubleRect.y, doubleRect.width, doubleRect.height,
				button1,button3,button2,this, false);

	addGB(gbDouble);
	gbDouble.hide();

	imageButtonParts.flush();

	gbInsurance = new GraphicButton(insuranceRect.x, insuranceRect.y, insuranceRect.width,
					insuranceRect.height, "Yes",
					Color.blue, new Color(128,128,255), Color.white, 
					new Font("Arial", Font.BOLD, 10),this);						
	addGB(gbInsurance);
	gbInsurance.hide();

	gbNoInsurance = new GraphicButton(noInsuranceRect.x, noInsuranceRect.y, noInsuranceRect.width,
					noInsuranceRect.height, "No",
					Color.blue, new Color(128,128,255), Color.white, 
					new Font("Arial", Font.BOLD, 10),this);

	addGB(gbNoInsurance);
	gbNoInsurance.hide();

	gbCashOutYes = new GraphicButton(317, 170, 30,20, "Yes",
					Color.gray, new Color(128,128,255), Color.white, 
					new Font("Arial", Font.BOLD, 10),this);

	addGB(gbCashOutYes);
	gbCashOutYes.hide();

	gbCashOutNo = new GraphicButton(370, 170, 30,20, "No",
					Color.gray, new Color(128,128,255), Color.white, 
					new Font("Arial", Font.BOLD, 10),this);

	addGB(gbCashOutNo);
	gbCashOutNo.hide();

	gbClear = new GraphicButton(clearRect.x, clearRect.y, clearRect.width, clearRect.height, 
					imageClear, imageClearS, imageClearHL,this,true);

		
	gbClear.hide();
	addGB(gbClear);

	gbDeal.m_status_mask = GS_CAN_DEAL;
	gbHit.m_status_mask = GS_CAN_HIT;
	gbStand.m_status_mask = GS_CAN_STAND;
	gbSplit.m_status_mask = GS_CAN_SPLIT;
	gbDouble.m_status_mask = GS_CAN_DOUBLE;
	gbInsurance.m_status_mask = GS_CAN_INSURANCE;
	gbNoInsurance.m_status_mask = GS_CAN_INSURANCE;
	gbClear.m_status_mask = GS_CAN_BET;
	
	}

/***********************************************************************
		void MakeBackground();
				Create the buttons for low bandwidth version
***********************************************************************/

		void MakeBackground() {
			imageBk = createImage(iScreenWidth, iScreenHeight);
			Graphics bkg = imageBk.getGraphics();
			bkg.setColor(new Color(0,74,0));
			bkg.fillRect(0,0,iScreenWidth, iScreenHeight);
			}

 /**************************************************************************
			void getImages() 
 **************************************************************************/

	public void getImages() {
		String param;

		StringTokenizer strt;
		int x;

		String impref = "graphics/";

		imageBk = getImage2("backgrnd3.gif");
		prepareImage(imageBk,this);
		tracker.addImage(imageBk,0);

		imageCardParts = getImage3(impref + "numsuit.gif");
		prepareImage(imageCardParts, null);
		tracker.addImage(imageCardParts,1);
	
		imageCardTopSmall = getImage3(impref + "scardf1.gif");
		imageCardTopLarge = getImage3(impref + "lcardt1.gif");
		imageCardTopLargeShad = getImage3(impref + "lcardt2.gif");
		imageCardSideSmall = getImage3(impref + "sshad.gif");
		imageCardSideLarge = getImage3(impref + "lshad.gif");
		imageCardBottomSmall = getImage3(impref + "scardf2.gif");
		imageDlrCardBack = getImage2("scard1.jpg");
		imageButtonParts = getImage2("hitstand.gif");
		imageHitHL = getImage3(impref + "hithl.gif");
		imageHitS = getImage3(impref + "hits.gif");
		imageHit = getImage3(impref + "hit.gif");
		imageBetButtonParts = getImage2("dealclear.gif");
		imageClearHL = getImage3(impref + "clearhl.gif");
		imageClearS = getImage3(impref + "clears.gif");
		imageClear = getImage3(impref + "clear.gif");

		prepareImage(imageCardTopSmall, null);
		prepareImage(imageCardTopLarge, null);
		prepareImage(imageCardSideSmall, null);
		prepareImage(imageCardSideLarge, null);
		prepareImage(imageCardBottomSmall, null);
		prepareImage(imageDlrCardBack,null);
		prepareImage(imageButtonParts,null);
		prepareImage(imageCardTopLargeShad,null);
		prepareImage(imageHitHL, null);
		prepareImage(imageHitS, null);
		prepareImage(imageHit, null);
		prepareImage(imageBetButtonParts, null);
		prepareImage(imageClearHL, null);
		prepareImage(imageClearS, null);
		prepareImage(imageClear, null);

		tracker.addImage(imageCardTopSmall,2);
		tracker.addImage(imageCardTopLarge,3);
		tracker.addImage(imageCardSideSmall,4);
		tracker.addImage(imageCardSideLarge,5);
		tracker.addImage(imageDlrCardBack,6);
		tracker.addImage(imageButtonParts,7);
		tracker.addImage(imageCardTopLargeShad,8);
		tracker.addImage(imageHitHL,9);
		tracker.addImage(imageHitS,10);
		tracker.addImage(imageHit,11);
		tracker.addImage(imageCardBottomSmall,12);
		tracker.addImage(imageBetButtonParts,13);
		tracker.addImage(imageClearHL,14);
		tracker.addImage(imageClearS,15);
		tracker.addImage(imageClear,16);
		tracker.checkAll(true);
		}

 /**************************************************************************
		getParams()     gets parameters from html
 **************************************************************************/

	void getParams() {


		String gparam;
		StringTokenizer strt;
		int x;
	
		gparam = getParameter("BETVALUES");

		if(gparam != null) {
			try {
				strt = new StringTokenizer(gparam,",");
				for(x=0; x<3; x++) {
					chipValue[x] = (Double.valueOf(strt.nextElement().toString())).doubleValue();
					}
				}
			catch(Exception e) {}
			}

		gparam = getParameter("PAGEVIEW");

		if(gparam != null) {
			if(gparam.equals("N")) bPageview = false;
			if(gparam.equals("Y")) bPageview = true;
			}

/*
		gparam = getParameter("HITRECT");
		if(gparam != null) {
			try {
				strt = new StringTokenizer(gparam,",");
				hitRect = new Rectangle(Integer.parseInt((strt.nextElement()).toString(),10),
								Integer.parseInt((strt.nextElement()).toString(),10),
								Integer.parseInt((strt.nextElement()).toString(),10),
								Integer.parseInt((strt.nextElement()).toString(),10));
				}
			catch(Exception e) {}
			}
		gparam = getParameter("SPLITRECT");
		if(gparam != null) {
			try {
				strt = new StringTokenizer(gparam,",");
				splitRect = new Rectangle(Integer.parseInt((strt.nextElement()).toString(),10),
								Integer.parseInt((strt.nextElement()).toString(),10),
								Integer.parseInt((strt.nextElement()).toString(),10),
								Integer.parseInt((strt.nextElement()).toString(),10));
				}
			catch(Exception e) {}
			}
		gparam = getParameter("DOUBLERECT");
		if(gparam != null) {
			try {
				strt = new StringTokenizer(gparam,",");
				doubleRect = new Rectangle(Integer.parseInt((strt.nextElement()).toString(),10),
								Integer.parseInt((strt.nextElement()).toString(),10),
								Integer.parseInt((strt.nextElement()).toString(),10),
								Integer.parseInt((strt.nextElement()).toString(),10));
				}
			catch(Exception e) {}
			}
		gparam = getParameter("STANDRECT");
		if(gparam != null) {
			try {
				strt = new StringTokenizer(gparam,",");
				standRect = new Rectangle(Integer.parseInt((strt.nextElement()).toString(),10),
								Integer.parseInt((strt.nextElement()).toString(),10),
								Integer.parseInt((strt.nextElement()).toString(),10),
								Integer.parseInt((strt.nextElement()).toString(),10));
				}
			catch(Exception e) {}
			}
							    
		gparam = getParameter("DEALRECT");
		if(gparam != null) {
			try {
				strt = new StringTokenizer(gparam,",");
				dealRect = new Rectangle(Integer.parseInt((strt.nextElement()).toString(),10),
								Integer.parseInt((strt.nextElement()).toString(),10),
								Integer.parseInt((strt.nextElement()).toString(),10),
								Integer.parseInt((strt.nextElement()).toString(),10));
				}
			catch(Exception e) {}
			}

		gparam = getParameter("TABLERECT");
		if(gparam != null) {
			try {
				strt = new StringTokenizer(gparam,",");
				tableRect = new Rectangle(Integer.parseInt((strt.nextElement()).toString(),10),
								Integer.parseInt((strt.nextElement()).toString(),10),
								Integer.parseInt((strt.nextElement()).toString(),10),
								Integer.parseInt((strt.nextElement()).toString(),10));
				}
			catch(Exception e) {}
			}

		gparam = getParameter("RESULTRECT");
		if(gparam != null) {
			try {
				strt = new StringTokenizer(gparam,",");
				resultRect = new Rectangle(Integer.parseInt((strt.nextElement()).toString(),10),
								Integer.parseInt((strt.nextElement()).toString(),10),
								Integer.parseInt((strt.nextElement()).toString(),10),
								Integer.parseInt((strt.nextElement()).toString(),10));
				}
			catch(Exception e) {}
			}


		gparam = getParameter("DLRTABLERECT");
		if(gparam != null) {
			try {
				strt = new StringTokenizer(gparam,",");
				dlrTableRect = new Rectangle(Integer.parseInt((strt.nextElement()).toString(),10),
								Integer.parseInt((strt.nextElement()).toString(),10),
								Integer.parseInt((strt.nextElement()).toString(),10),
								Integer.parseInt((strt.nextElement()).toString(),10));
				}
			catch(Exception e) {}
			}

		gparam = getParameter("INSURANCERECT");
		if(gparam != null) {
			try {
				strt = new StringTokenizer(gparam,",");
				insuranceRect = new Rectangle(Integer.parseInt((strt.nextElement()).toString(),10),
								Integer.parseInt((strt.nextElement()).toString(),10),
								Integer.parseInt((strt.nextElement()).toString(),10),
								Integer.parseInt((strt.nextElement()).toString(),10));
				}
			catch(Exception e) {}
			}

		gparam = getParameter("NOINSURANCERECT");
		if(gparam != null) {
			try {
				strt = new StringTokenizer(gparam,",");
				noInsuranceRect = new Rectangle(Integer.parseInt((strt.nextElement()).toString(),10),
								Integer.parseInt((strt.nextElement()).toString(),10),
								Integer.parseInt((strt.nextElement()).toString(),10),
								Integer.parseInt((strt.nextElement()).toString(),10));
				}
			catch(Exception e) {}
			}

		gparam = getParameter("DLRTABLERECT");
		if(gparam != null) {
			try {
				strt = new StringTokenizer(gparam,",");
				dlrTableRect = new Rectangle(Integer.parseInt((strt.nextElement()).toString(),10),
								Integer.parseInt((strt.nextElement()).toString(),10),
								Integer.parseInt((strt.nextElement()).toString(),10),
								Integer.parseInt((strt.nextElement()).toString(),10));
				}
			catch(Exception e) {}
			}

		gparam = getParameter("PLRCARDRECT");
		if(gparam != null) {
			try {
				strt = new StringTokenizer(gparam,",");
				plrCardRect = new Rectangle(Integer.parseInt((strt.nextElement()).toString(),10),
								Integer.parseInt((strt.nextElement()).toString(),10),
								Integer.parseInt((strt.nextElement()).toString(),10),
								Integer.parseInt((strt.nextElement()).toString(),10));
				}
			catch(Exception e) {}
			}
				
		gparam = getParameter("CHIPADDRECTS");
		if(gparam != null) {
			try {
				strt = new StringTokenizer(gparam,",");
				for(x=0; x<6; x++) {
					chipAddRects[x] = new Rectangle(Integer.parseInt((strt.nextElement()).toString(),10),
								Integer.parseInt((strt.nextElement()).toString(),10),
								Integer.parseInt((strt.nextElement()).toString(),10),
								Integer.parseInt((strt.nextElement()).toString(),10));
					}
				}
			catch(Exception e) {}
			}


		gparam = getParameter("CHIPVALUES");
		if(gparam != null) {
			try {
				strt = new StringTokenizer(gparam,",");
				for(x=0; x<6; x++) {
					chipValue[x] = Integer.parseInt((strt.nextElement()).toString(),10);
					}
				}
			catch(Exception e) {}
			}

		gparam = getParameter("MINCOORDS");
		if(gparam != null) {
			try {
				strt = new StringTokenizer(gparam,",");
				mincoords = new Point(Integer.parseInt((strt.nextElement()).toString(),10),
								Integer.parseInt((strt.nextElement()).toString(),10));
				}
			catch(Exception e) {}
			}
		gparam = getParameter("MAXCOORDS");
		if(gparam != null) {
			try {
				strt = new StringTokenizer(gparam,",");
				maxcoords = new Point(Integer.parseInt((strt.nextElement()).toString(),10),
								Integer.parseInt((strt.nextElement()).toString(),10));
				}
			catch(Exception e) {}
			}
		gparam = getParameter("BETCOORDS");
		if(gparam != null) {
			try {
				strt = new StringTokenizer(gparam,",");
				betcoords = new Point(Integer.parseInt((strt.nextElement()).toString(),10),
								Integer.parseInt((strt.nextElement()).toString(),10));
				}
			catch(Exception e) {}
			}
		gparam = getParameter("BALCOORDS");
		if(gparam != null) {
			try {
				strt = new StringTokenizer(gparam,",");
				balcoords = new Point(Integer.parseInt((strt.nextElement()).toString(),10),
								Integer.parseInt((strt.nextElement()).toString(),10));
				}
			catch(Exception e) {}
			}
		gparam = getParameter("DLRCARDCOORDS");
		if(gparam != null) {
			try {
				strt = new StringTokenizer(gparam,",");
				dlrcardcoords = new Point(Integer.parseInt((strt.nextElement()).toString(),10),
								Integer.parseInt((strt.nextElement()).toString(),10));
				}
			catch(Exception e) {}
			}

		gparam = getParameter("PLRCARDCOORDS");
		if(gparam != null) {
			try {
				strt = new StringTokenizer(gparam,",");
				plrcardcoords = new Point(Integer.parseInt((strt.nextElement()).toString(),10),
								Integer.parseInt((strt.nextElement()).toString(),10));
				}
			catch(Exception e) {}
			}

		gparam = getParameter("PLRVALCOORDS");
		if(gparam != null) {
			try {
				strt = new StringTokenizer(gparam,",");
				plrvalcoords = new Point(Integer.parseInt((strt.nextElement()).toString(),10),
								Integer.parseInt((strt.nextElement()).toString(),10));
				}
			catch(Exception e) {}
			}

		gparam = getParameter("DLRVALCOORDS");
		if(gparam != null) {
			try {
				strt = new StringTokenizer(gparam,",");
				dlrvalcoords = new Point(Integer.parseInt((strt.nextElement()).toString(),10),
								Integer.parseInt((strt.nextElement()).toString(),10));
				}
			catch(Exception e) {}
			}

		gparam = getParameter("RESULTCOORDS");
		if(gparam != null) {
			try {
				strt = new StringTokenizer(gparam,",");
				resultcoords = new Point(Integer.parseInt((strt.nextElement()).toString(),10),
								Integer.parseInt((strt.nextElement()).toString(),10));
				}
			catch(Exception e) {}
			}

		gparam = getParameter("INSURANCECOORDS");
		if(gparam != null) {
			try {
				strt = new StringTokenizer(gparam,",");
				insurancecoords = new Point(Integer.parseInt((strt.nextElement()).toString(),10),
								Integer.parseInt((strt.nextElement()).toString(),10));
				}
			catch(Exception e) {}
			}

		gparam = getParameter("NOINSURANCECOORDS");
		if(gparam != null) {
			try {
				strt = new StringTokenizer(gparam,",");
				noinsurancecoords = new Point(Integer.parseInt((strt.nextElement()).toString(),10),
								Integer.parseInt((strt.nextElement()).toString(),10));
				}
			catch(Exception e) {}
			}

		gparam = getParameter("CHIPSTACKSPACING");
		if(gparam != null) {
			try {
				strt = new StringTokenizer(gparam,",");
				chipStackSpacing = Integer.parseInt((strt.nextElement()).toString(),10);
				}
			catch(Exception e) {}
			}
 */
	}


/*************************************************************
			void GetSoundMedia()
		Load all of the soubd media
*************************************************************/
			
	void GetSoundMedia() {
		String audioDir = "sound/";
		auDeal = getAudioClip3(audioDir + "deal_hl.au");
		auHit = getAudioClip3(audioDir + "ht_sl.au"); 
		auStand = getAudioClip3(audioDir + "st_sl.au");
		auSplit = getAudioClip3(audioDir + "sp_sl.au");
		auDouble = getAudioClip3(audioDir + "dd_sl.au");
		auChip = getAudioClip3(audioDir + "chip_plus.au");
		auStart = getAudioClip3(audioDir + "player_arrive.au");
		auCashout = getAudioClip3(audioDir + "player_depart.au");
		auWin = getAudioClip3(audioDir + "win.au");
		auBust = getAudioClip3(audioDir + "bust.au");
		auLose = auBust;
		auPush = getAudioClip3(audioDir + "push.au");
		auBj = getAudioClip3(audioDir + "bj.au");
		auClear = getAudioClip3(audioDir + "clear_sl.au");
		}

/*************************************************************
			void getAudioClip2(String file)
		deals with the make URL stuff
*************************************************************/

	AudioClip getAudioClip2(String st) {
		try {
			URL url = new URL(getDocumentBase(), st);	
			return getAudioClip(url);
			}
		catch(Exception e) {
			System.out.println(st + " corrupt");
			}
		return null;
		}
				
/*************************************************************
			void getAudioClip3(String file)
		Same as audio2, but in the codebase
*************************************************************/

	AudioClip getAudioClip3(String st) {
		try {
			URL url = new URL(getCodeBase(), st);	
			return getAudioClip(url);
			}
		catch(Exception e) {
			System.out.println(st + " corrupt");
			}
		return null;
		}

/**************************************************************************
			Image getImage2()

		so you don't have to go through the rigamaroll of creating
		a url every time....
**************************************************************************/

	Image getImage2(String s) {
		URL url;
		try {
			url = new URL(getDocumentBase(),s);
			return getImage(url);
			}
		catch(Exception e) {
			System.out.println("Get image 2 error");
			}
		return null;
		}

/**************************************************************************
			Image getImage3()

		Same as getImage2 but using codebase
**************************************************************************/

	Image getImage3(String s) {
		URL url;
		try {
			url = new URL(getCodeBase(),s);
			return getImage(url);
			}
		catch(Exception e) {
			System.out.println("Get image 2 error");
			}
		return null;
		}

/**************************************************************************
	void MakeCardParts();
		makes all of the card parts
**************************************************************************/
	
	void MakeCardParts() 
		{
		imageCardPipsLarge = new Image[26];
		imageCardPipsSmall = new Image[26];
		imageCardSuitsLarge = new Image[4];
		imageCardSuitsSmall = new Image[4];

		int x;
		Graphics	tg;

		for(x=0; x<13; x++) {
			imageCardPipsLarge[x] = createImage(22,35);
			tg = imageCardPipsLarge[x].getGraphics();
			tg.drawImage(imageCardParts, -x * 44, 0, null);
			tg.dispose();
			imageCardPipsSmall[x] = createImage(12,20);
			tg = imageCardPipsSmall[x].getGraphics();
			tg.drawImage(imageCardParts, -x * 24, -36, null);
			tg.dispose();
			imageCardPipsLarge[x + 13] = createImage(22,35);
			tg = imageCardPipsLarge[x + 13].getGraphics();
			tg.drawImage(imageCardParts, -x * 44 - 22, 0, null);
			tg.dispose();
			imageCardPipsSmall[x + 13] = createImage(12,20);
			tg = imageCardPipsSmall[x + 13].getGraphics();
			tg.drawImage(imageCardParts, -x * 24 - 12, -36, null);
			tg.dispose();
			}

		for(x=0; x<4; x++) {
			imageCardSuitsLarge[x] = createImage(22,21);
			tg = imageCardSuitsLarge[x].getGraphics();
			tg.drawImage(imageCardParts, (-x * 22) - 312, -36, null);
			tg.dispose();
			imageCardSuitsSmall[x] = createImage(12,12);
			tg = imageCardSuitsSmall[x].getGraphics();
			tg.drawImage(imageCardParts, (-x * 12) - 400, -35, null);
			tg.dispose();
			}
		}
}		
