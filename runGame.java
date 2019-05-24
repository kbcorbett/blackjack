/* Authored by Kristoffer corbett 5/16/2019
 * card object holds data for card face, suit, and eventually location of graphic file
 * Modified 5/24/2019
*/


import java.util.Scanner;

public class runGame {
	
	//create deck object and "draw" the first card
	static deck gameDeck = new deck();
	static card currentCard = gameDeck.draw();
	
	/* initialize player and dealer
	 * score starts at 0 and max hand size is 5
	 */
	static int playerScore = 0;
	static card[] playerHand = new card[5];
	static int dealerScore =0;
	static card[] dealerHand = new card[5];
	
	/* these variable keep track of how many cards are currently held by each player
	 * initialized to 2 for initial cards dealt
	 */
	
	static int playerCards = 2;
	static int dealerCards = 2;
	
	public enum gameState 
	{ 
		PLAYING, BJPLAYER, BJDEALER, BUSTDEALER, BUSTPLAYER, CHPLAYER, CHDEALER, HELD 
	}
	
	
	static gameState state = gameState.PLAYING;

	public static void main(String[] args) 
	{
		boolean playAgain = true;
		Scanner input = new Scanner(System.in);
		deal(); //deal method method deals initial cards and checks for an instant blackjack
		int menu;
		
		
		while(playAgain) //loops to play again after each complete hand
		{
		while(state == gameState.PLAYING) //loops until a win/lose state is set
		{
			menu = 2; //initialized to anything except 1
			
			System.out.println("1 to hit, any other key to stay");
			
			try
			{
				menu = Integer.parseInt(input.nextLine());
			}
			catch(Exception e) //catch is irrelevant, don't need to know why the input couldn't be parsed since program only cares about "1" and "other"
			{
				
			}
			
			if (menu == 1)
				hit();
			else
				stay();
		}
	
		switch(state) //checks the win/lose state and displays result
		{
		case BJDEALER:
			System.out.println("Dealer has 21! You lose!");
			break;
		case BJPLAYER:
			System.out.println("You win!");
			break;
		case CHDEALER:
			System.out.println("Dealer has five card charlie. You lose!");
			break;
		case CHPLAYER:
			System.out.println("Five card Charlie. You win!");
			break;
		case BUSTPLAYER:
			System.out.println("Busted. You lose!");
			break;
		case BUSTDEALER:
			System.out.println("Dealer busted. You win!");
			break;
		default:
			System.out.println("Dealer has " + dealerScore + " and you have " + playerScore + ".");
			if(dealerScore >= playerScore)
				System.out.println("You lose!");
			else
				System.out.println("You Win!");
			break;
		}
		
		System.out.println("Would you like to play again? 1 to play, any other key to quit.");
		menu = 2; //initialized to anything except 1
		try
		{
			menu = Integer.parseInt(input.nextLine());
		}
		catch(Exception e) //catch is irrelevant, don't need to know why the input couldn't be parsed since program only cares about "1" and "other"
		{
			
		}
		if (menu != 1)
			playAgain = false;
		
		//if user selected to play again reinitialize game with a new deck and reset hands and scores
		else 
		{
			gameDeck = new deck();
			currentCard = gameDeck.draw();
			playerScore = 0;
			playerHand = new card[5];
			dealerScore = 0;
			dealerHand = new card[5];
			playerCards = 2;
			dealerCards = 2;
			deal();
			state = gameState.PLAYING;
		}
		}
		input.close();
	}
	
	//this method initializes the dealer and player hands
	public static void deal()
	{
		//deal 2 cards each to player ands dealer. advance to next card after each is given out
		playerHand[0] = currentCard;
		currentCard = gameDeck.draw();
		dealerHand[0] = currentCard;
		currentCard = gameDeck.draw();
		playerHand[1] = currentCard;
		currentCard = gameDeck.draw();
		dealerHand[1] = currentCard;
		currentCard = gameDeck.draw();
		//call score method to check current value of held cards
		score(true);
		score(false);
		
		//notify user of which cards they were dealt and thier value
		System.out.println("You are dealt the " + playerHand[0].getFaceName() + " of " + playerHand[0].getSuitName() + " and the " + playerHand[1].getFaceName() + " of " + playerHand[1].getSuitName() + ".");
		System.out.println("Your current score is: " + playerScore);
		
		//check for dealt blackjack
		if(dealerScore == 21)
			state = gameState.BJDEALER;
		else if(playerScore ==21)
			state = gameState.BJPLAYER;
	}
	
	//this method draws an additional player card
	public static void hit()
	{
		playerHand[playerCards] = currentCard;
		currentCard = gameDeck.draw();
		System.out.println("You are dealt the " + playerHand[playerCards].getFaceName() + " of " + playerHand[playerCards].getSuitName() + ".");
		playerCards++; //increase player hand size, max is 5
		score(true); //update score
		System.out.println("Your current score is: " + playerScore);
		
		//check if player is busted(greater than 21)
		if(playerScore > 21)
			state = gameState.BUSTPLAYER;
		//check if player has blackjack
		else if(playerScore == 21)
			state = gameState.BJPLAYER;
		//check if player auto wins for full hand
		else if(playerCards ==5 )
			state = gameState.CHPLAYER;

	}
	
	//if player stays this method plays out the dealers turn
	public static void stay()
	{
		//only draw and update score if dealer has less than 17 points and less than 5 cards
		while((dealerScore < 17) && (dealerCards < 5))
		{
			System.out.println("The dealer takes a card");
			dealerHand[dealerCards] = currentCard;
			currentCard = gameDeck.draw();
			dealerCards++;
			score(false);
		}
		
		//check if dealer has blackjack
		if(dealerScore == 21)
			state = gameState.BJDEALER;
		//check if dealer is buster
		else if(dealerScore > 21)
			state = gameState.BUSTDEALER;
		//check if dealer has full hand win
		else if(dealerCards ==5 )
			state = gameState.CHDEALER;
		//held state compares scores to determine winner in main method
		else
			state = gameState.HELD;
		
	}
	/* this method updates the score based on cards in hand
	 * input: true-player score, false-dealer score
	*/
	public static void score(boolean player)
	{
		if(player)
		{
			//initialize score and aces in hand to 0
			playerScore = 0;
			int ace = 0;
			
			for (int i=0 ; i < playerCards ; i++) //iterate through card in hand
			{
				if(playerHand[i].getFace() > 13) //increment aces in hand if necessary
				{
					ace++;
				}
				else if(playerHand[i].getFace() >= 10) //for non aces add card value(10 for face cards)
					playerScore = playerScore + 10;
				else
					playerScore = playerScore + playerHand[i].getFace();
			}
			
			while(ace > 0) //loop once for each ace in hand
			{
				ace--;
				//add 11 to score, reduce to 1 if adding 11 causes a bust
				playerScore = playerScore + 11;
				if(playerScore > 21)
					playerScore = playerScore - 10;
			}
		}
		
		else
		{
			//initialize score and aces in hand to 0
			dealerScore = 0;
			int ace = 0;
			
			for (int i=0 ; i < dealerCards; i++) //iterate through card in hand
			{
				if(dealerHand[i].getFace() > 13) //increment aces in hand if necessary
				{
					ace++;
				}
				else if(dealerHand[i].getFace() >= 10) //for non aces add card value(10 for face cards)
					dealerScore = dealerScore + 10;
				else
					dealerScore = dealerScore + dealerHand[i].getFace();
			}
			
			while(ace > 0) //loop once for each ace in hand
			{
				ace--;
				//add 11 to score, reduce to 1 if adding 11 causes a bust
				dealerScore = dealerScore + 11;
				if(dealerScore > 21)
					dealerScore = dealerScore - 10;
			}
		}
		
	}

}
