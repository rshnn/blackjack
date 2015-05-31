import java.util.Random;

// This class represents the deck of cards from which cards are dealt to players.
public class Deck
{
	// define fields.
	private Card[] d;

	
	
	// This constructor builds a deck of 52 cards.
	public Deck(int num_decks)
	{
		
		this.d = new Card [52*num_decks]; 
		int pos=0;
		
		for(int k=0; k<num_decks;k++){ //for each deck
			
			for(int i=0; i<4;i++){ // for each suit	
				for(int j=1; j<14; j++){ //for each face
					Card temp = new Card(i,j);
					this.d[pos] = temp;
					pos++;
				}
			}
			
		}
	}

	// This method shuffles the deck (randomizes the array of cards).
	// Hint: loop over the cards and swap each one with another card in a random position.
	public void shuffle()
	{
		Card temp = null;
		int randomindex=0;
		
		for(int i=0; i<d.length;i++){
			randomindex = (int) ( Math.random()*51 );
			temp = d[randomindex];
			d[randomindex] = d[i];
			d[i] = temp;
		
		}
		
		
		
		// complete this method
	}
	
	// This method takes the top card off the deck and returns it.
	public Card drawCard()
	{
		Card draw = null;
		
		for(int i=0; i<this.d.length;i++){
			if(this.d[i] != null){
				draw = this.d[i];
				this.d[i] = null;
				break;
			}
		}
		
		return draw; 
	}
	
	// This method returns the number of cards left in the deck.
	public int getSize()
	{
		int notempty = 0;
		
		for(int i=0; i<this.d.length;i++){
			if(this.d[i] != null){
				notempty++;
			}
		}
		
		return notempty;
	}



}

