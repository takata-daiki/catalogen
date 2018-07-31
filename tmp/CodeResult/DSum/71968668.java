import java.util.*;



public class BlackJack
{
	ArrayList<Card> dealer = new ArrayList<Card>();
	ArrayList<Card> user = new ArrayList<Card>();
	private boolean gameOver, userWins;
	
	public BlackJack()
	{
		
		gameOver = false;
		userWins = false;
	}
	
	public Card dealCard()
	{
		Card card1 = new Card();
		int random = 1+(int)(Math.random()*13);
		card1.setValue(random);
		return (card1);
	}
	
	public void initGame()
	{
		dealer.clear();
		user.clear();
		dealer.add(this.dealCard());
		dealer.add(this.dealCard());
		user.add(this.dealCard());
		user.add(this.dealCard());
		System.out.println("Game begins!");
	}
	
	
	public int getSum (ArrayList<Card> cards)
	{
		int total = 0;
		for (int i=0; i<cards.size();i++ )
			
			total=cards.get(i).getValue()+total;
		
		return (total);
	}
	

	
	public boolean checkGameOver()
	{
		int usum = this.getSum(user);
		int dsum = this.getSum(dealer);
		int ucount = user.size();
		int dcount = dealer.size();
			
		if (usum>21)
		{
			this.gameOver = true;
			this.userWins = false;
			System.out.println("User loses");
		}
		else if (dsum >21)
		{
			this.gameOver = true;
			this.userWins = true;
			System.out.println("User wins");
		}
		else if (usum==21 && dsum!=21)
		{
			this.gameOver=false;
		}
		else
		{
			this.gameOver=false;
			
		}
		System.out.println(user);
		System.out.println(dealer);
		return this.gameOver;
	}
	
	public void showResults() {
		System.out.println ("Game over=" + this.gameOver + " User Wins=" +this.userWins);
		System.out.println (user) ;
		System.out.println (dealer);
	}
	
	public void userHits()
	{
		Scanner scan = new Scanner(System.in);
		while (true)
		{
			System.out.println("Hit once more (y/n)?");
			String input = scan.nextLine();
			System.out.println(input);
			if (input.startsWith("y") )
			{
				user.add(dealCard());
				if (this.checkGameOver())
					break;
			}
			else
				break;
		}
	}
	
	public void dealerHits()
	{
		
		int dsum = getSum(dealer);
		while(dsum<=16)
		{
			dealer.add(dealCard());
			dsum = getSum(dealer);
		}
		int dcount = dealer.size();
		int usum = getSum(user);
		int ucount = user.size();
		if (dsum==21)
		{
			this.gameOver=true;
			this.userWins=false;
		}
		else if(dsum>21)
		{
			this.gameOver=true;
			this.userWins=true;
		}
		else
			if (usum >dsum && ucount>=dcount)
				{
				this.gameOver=true;
				this.userWins=true;
				}
			else if (usum > dsum && dcount > ucount )
			{
				this.gameOver=true;
				this.userWins=false;
			}
			else if (dsum > usum)
			{
				this.gameOver = true;
				this.userWins = false;
			}
			else
				System.out.println("not sure");
	}
	
	

	public static void main(String[] args)
	{
		BlackJack bj = new BlackJack();
		bj.initGame();
		if (!bj.checkGameOver())
				{
				bj.userHits();
				bj.dealerHits();
				}
		bj.showResults() ;
		
	}
}