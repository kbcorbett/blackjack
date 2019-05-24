/* Authored by Kristoffer corbett 5/16/2019
 * deck object creates 52 card objects, 1 for each card, then shuffles the order.
 * drawing a card pulls from the "top" of the card array and advances "current card" which acts as a pointer to the deck
*/

import java.util.Random;

public class deck 
{
	card[] currentDeck = new card[52]; 
	int currentCard = 0; //points to the next array value to be drawn as a card
	
	//default constructor
	deck()
	{
		//initialize variable for card constructor loop
		int suit = 1;
		int face = 2;
		
		for (int i=0 ; i < 52 ; i++) //create 52 card objects
		{
			currentDeck[i] = new card(suit,face); //construct with current suit and face			
			face++; //next face
			if(face >= 15) //if face is higher then 14(ace value) then suit changes and face resets to 2
			{
				face = 2;
				suit++;
			}
		}
		for (int i = 0 ; i < 7 ; i++)  //run shuffle method on the new deck 7 times
			shuffle();	
	}

	//method randomizes position of each card in deck 
	private card[] shuffle()
	{
		Random rnd = new Random();
		
		for (int i=0; i < 52; i++) //iterate through all card objects
		{
			//swap position of current card object with a random other card
		    int randomPosition = rnd.nextInt(52); 
		    card temp = this.currentDeck[i];
		    this.currentDeck[i] = this.currentDeck[randomPosition];
		    this.currentDeck[randomPosition] = temp;
		}
		
		
		return this.currentDeck;
	}

	//default getter
	public card[] getCurrentDeck() 
	{
		return currentDeck;
	}

	//default setter
	public void setCurrentDeck(card[] currentDeck) 
	{
		this.currentDeck = currentDeck;
	}
	
	//return current "top" card and advance var: currentCard to the next card in the deck
	public card draw()
	{
		card temp = this.currentDeck[this.currentCard];
		this.currentCard++;
		return temp;
	}
	
	
	
	

}
