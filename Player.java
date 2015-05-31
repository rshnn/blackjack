// This class represents one blackjack player (or the dealer)
public class Player
{
	// define fields.
	
	private String name;
	private boolean isDealer;
	private Hand h;
	private double bank;
	private double bet;
	private int h_winround; // win = 1; lose = 0; push = 2
	private int s_winround; // win = 1; lose = 0; push = 2
	private boolean canSplit; 
	private Hand split;
	private double split_bet;
	private boolean splitcomplete;
	
	
	// This constructor creates a player.
	// If isDealer is true, this Player object represents the dealer.
	public Player(String playerName, boolean isDealer, double bank)
	{
		this.name = playerName;
		this.isDealer = isDealer;
		this.h = new Hand();
		this.bank = bank;
		this.bet = 0;
		this.h_winround = 3;
		this.s_winround = 3;
		this.canSplit = false;
		this.split = new Hand();
		this.split_bet = 0;
		this.splitcomplete = false;
	}

	
	// This method retrieves the player's name.
	public String getName()
	{
		return this.name; 
	}

	
	//This method returns if the player is the dealer.
	public boolean isDealer(){
		return this.isDealer;
	}
	
	public void setDealer(boolean b){
		this.isDealer = b;
		
	}
	
	// This method retrieves the player's hand of cards.
	public Hand getHand()
	{
		return this.h; 
	}
	
	public double getBank(){
		return this.bank;
	}
	
	public int getWinH(){
		return this.h_winround;
	}
	
	public int getWinS(){
		return this.s_winround;
	}
	
	public double getBetH(){
		return this.bet;
	}
	
	
	public double getBetS(){
		return this.split_bet;
	}
	
	
	// This method deals two cards to the player (one face down if this is the dealer).
	// The window input should be used to redraw the window whenever a card is dealt.
	public void startRound(Deck deck)    // , BlackjackWindow window
	{
		if(this.isDealer == true){
			Card c1 = deck.drawCard();
			c1.turnFaceUp();
			Card c2 = deck.drawCard();
			c2.turnFaceDown();
			h.addCard(c1); // window.redraw();
			h.addCard(c2); // window.redraw();
			
			System.out.println("Dealer: " + this.name);
			System.out.println( h.toStringDealer());
			System.out.println();
			
		}
		
		else if(this.isDealer == false){
			System.out.println("Player: " + this.name);
			System.out.println("Place your bet:");
			this.bet = IO.readInt(); 
			//make bets
			
			Card c1 = deck.drawCard();
			Card c2 = deck.drawCard();
			c1.turnFaceUp();
			c2.turnFaceUp();
			h.addCard(c1); // window.redraw();
			h.addCard(c2); // window.redraw();
			if(h.getCard(0).getValue() == h.getCard(1).getValue()){
				this.canSplit = true;
			}
			
			System.out.println(h.toStringPlayer());
			System.out.println();
			
		}
	
	}

	// This method executes gameplay for one player.
	// If this player is the dealer:
	//	- hits until score is at least 17
	// If this is an ordinary player:
	//	- repeatedly asks the user if they want to hit (draw another card)
	//	  until either the player wants to stand (not take any more cards) or
	//	  his/her score exceeds 21 (busts).
	// The window input should be used to redraw the window whenever a card is dealt or turned over.
	public void playRound(Deck deck) // , BlackjackWindow window
	{
		int totalscore = h.getScore();
		System.out.println(this.name + " play round:");
		System.out.println(h.toStringPlayer());
		System.out.println("");
		System.out.println("\t" + this.name + "'s current score: " + h.getScore());
		System.out.println("");
		

		
		if(this.isDealer == true){
			System.out.println("DEALER TURN");
			while(totalscore < 17){
				Card c = deck.drawCard();
				c.turnFaceUp();
				h.addCard(c);
				System.out.println("\t" + "Drew a " + c.toString());
				System.out.println("\t" + this.name + "'s current score: " + h.getScore());
				
				if(h.getScore() > 21)
					System.out.println("Dealer busts!");
				if(h.getScore() == 21)
					System.out.println("Dealer has blackjack!");
				if(h.getScore() >= 17)
					return;
				
			}
			
		}
		
		
		if(this.isDealer == false){
			
			if(canSplit == false){
				
				boolean doubledown = false;
				System.out.println("Would you like to double down?  Your current bet is:" +this.bet+"     Y/N");
				doubledown = IO.readBoolean(); 
				
				if(doubledown == false){
					System.out.println("Hit?  Y/N"); 
					boolean hit = IO.readBoolean();
					
					while(hit == true){
						Card c = deck.drawCard();
						c.turnFaceUp();
						h.addCard(c);
						System.out.println("\t" + "Drew a " + c.toString());
						System.out.println("\t" + this.name + "'s current score: " + h.getScore());
						
						if(h.getScore() == 21){
							System.out.println( this.name + " has blackjack!");
							return;
						}
						
						if(h.getScore() > 21){
							System.out.println("You busted.");
							return;
						}
				
					System.out.println("Hit?  Y/N"); hit = IO.readBoolean(); 
					}
				}// regular hit/stand.  not double down.
				
				if(doubledown == true){
					this.bet = this.bet+this.bet;
					Card c = deck.drawCard();
					c.turnFaceUp();
					h.addCard(c);
					System.out.println("\t" + "Drew a " + c.toString());
					System.out.println("\t" + this.name + "'s current score: " + h.getScore());
					
					if(h.getScore() == 21){
						System.out.println( this.name + " has blackjack!");
						return;
					}
					
					if(h.getScore() > 21){
						System.out.println("You busted.");
						return;
					}
				} // double down process
			} ////can not split
			
			splitcomplete = false;
			boolean doSplit = false;
			if(canSplit == true){
				System.out.println("Do you want to split?");
				doSplit = IO.readBoolean();
				
				if(doSplit == true){
					this.bet = this.split_bet;
					
					this.split.addCard(h.getCard(1));
					Card temp = h.getCard(0);
					h.discardAll();
					h.addCard(temp);
					split.addCard(deck.drawCard());
					h.addCard(deck.drawCard());
					System.out.println(h.toStringPlayer());
					System.out.println("\t" +"This hand's current score: " + h.getScore());
					System.out.println("");
					System.out.println(split.toStringPlayer());
					System.out.println("\t" +"This hand's current score: " + split.getScore());
					////splits hand into two.  h and split.
					
					
					boolean doubledown = false;
					System.out.println("Would you like to double down the first hand?  Your current bet is:" +this.bet+"     Y/N");
					doubledown = IO.readBoolean(); 
					
					if(doubledown == false){
						System.out.println("Hit?  Y/N"); 
						boolean hit = IO.readBoolean();
						
						while(hit == true){
							Card c = deck.drawCard();
							c.turnFaceUp();
							h.addCard(c);
							System.out.println("\t" + "Drew a " + c.toString());
							System.out.println("\t" + this.name + "'s current score: " + h.getScore());
							
							if(h.getScore() == 21){
								System.out.println( this.name + " has blackjack!");
								return;
							}
							
							if(h.getScore() > 21){
								System.out.println("You busted.");
								return;
							}
					
						System.out.println("Hit?  Y/N"); hit = IO.readBoolean(); 
						}
					}// regular hit/stand.  not double down.
					
					if(doubledown == true){
						this.bet = this.bet+this.bet;
						Card c = deck.drawCard();
						c.turnFaceUp();
						h.addCard(c);
						System.out.println("\t" + "Drew a " + c.toString());
						System.out.println("\t" + this.name + "'s current score: " + h.getScore());
						
						if(h.getScore() == 21){
							System.out.println( this.name + " has blackjack!");
							return;
						}
						
						if(h.getScore() > 21){
							System.out.println("You busted.");
							return;
						}
					} // double down process
					////for hand 'h'
					
					doubledown = false;
					System.out.println("Would you like to double down the second hand?  Your current bet is:" + this.split_bet+"     Y/N");
					doubledown = IO.readBoolean(); 
					
					if(doubledown == false){
						System.out.println("Hit?  Y/N"); 
						boolean hit = IO.readBoolean();
						
						while(hit == true){
							Card c = deck.drawCard();
							c.turnFaceUp();
							split.addCard(c);
							System.out.println("\t" + "Drew a " + c.toString());
							System.out.println("\t" + this.name + "'s current score: " + split.getScore());
							
							if(split.getScore() == 21){
								System.out.println( this.name + " has blackjack!");
								return;
							}
							
							if(split.getScore() > 21){
								System.out.println("You busted.");
								return;
							}
					
						System.out.println("Hit?  Y/N"); hit = IO.readBoolean(); 
						}
					}// regular hit/stand.  not double down.
					
					if(doubledown == true){
						this.split_bet = this.split_bet+this.split_bet;
						Card c = deck.drawCard();
						c.turnFaceUp();
						split.addCard(c);
						System.out.println("\t" + "Drew a " + c.toString());
						System.out.println("\t" + this.name + "'s current score: " + split.getScore());
						
						if(split.getScore() == 21){
							System.out.println( this.name + " has blackjack!");
							return;
						}
						
						if(split.getScore() > 21){
							System.out.println("You busted.");
							return;
						}
						
						splitcomplete = true;
					} // double down process
					////for hand 'split'
					
					
					
					
				}//end of split, do split.
				
				if(doSplit == false){
					
					boolean doubledown = false;
					System.out.println("Would you like to double down the first hand?  Your current bet is:" +this.bet+"     Y/N");
					doubledown = IO.readBoolean(); 
					
					if(doubledown == false){
						System.out.println("Hit?  Y/N"); 
						boolean hit = IO.readBoolean();
						
						while(hit == true){
							Card c = deck.drawCard();
							c.turnFaceUp();
							h.addCard(c);
							System.out.println("\t" + "Drew a " + c.toString());
							System.out.println("\t" + this.name + "'s current score: " + h.getScore());
							
							if(h.getScore() == 21){
								System.out.println( this.name + " has blackjack!");
								return;
							}
							
							if(h.getScore() > 21){
								System.out.println("You busted.");
								return;
							}
					
						System.out.println("Hit?  Y/N"); hit = IO.readBoolean(); 
						}
					}// regular hit/stand.  not double down.
					
					if(doubledown == true){
						this.bet = this.bet+this.bet;
						Card c = deck.drawCard();
						c.turnFaceUp();
						h.addCard(c);
						System.out.println("\t" + "Drew a " + c.toString());
						System.out.println("\t" + this.name + "'s current score: " + h.getScore());
						
						if(h.getScore() == 21){
							System.out.println( this.name + " has blackjack!");
							return;
						}
						
						if(h.getScore() > 21){
							System.out.println("You busted.");
							return;
						}
					} // double down process
						
				}//end of don't split
				
				
				
			}//end of canSplit
			
			
		}//end of Player round
		
			
		return;	
		
	}

	
	
	
	// This method informs the player about whether they won, lost, or pushed.
	// It also discards the player's cards to prepare for the next round.
	// The window input should be used to redraw the window after cards are discarded.
	public void finishRound(int dealerScore) // , BlackjackWindow window)
	{
		
		if(this.isDealer == false){
			
			System.out.print(this.name + ":  ");
			
			if(splitcomplete == false){
				if(h.getScore() > 21){
					System.out.println("You lose because you busted!");
					this.bank = this.bank - this.bet;
					System.out.println("\t" + this.name + "'s Current bank value: " + this.bank);
					this.h_winround = 0;
				}
				
				else if(dealerScore > 21){
					System.out.println("You win!  The Dealer busted!");
					this.bank = this.bank + this.bet;
					System.out.println("\t" +this.name + "'s Current bank value: " + this.bank);
					this.h_winround = 1;
				}
				
				else if( h.getScore() < dealerScore){
					System.out.println("You lose because the Dealer beat you!");
					this.bank = this.bank - this.bet;
					System.out.println("\t" +this.name + "'s Current bank value: " + this.bank);
					this.h_winround = 0;
	
				}
				else if( h.getScore() > dealerScore){
					System.out.println("You win!  You beat the Dealer!");
					this.bank = this.bank + this.bet;
					System.out.println("\t" +this.name + "'s Current bank value: " + this.bank);
					this.h_winround = 1;
				}
				
				else if( h.getScore() == dealerScore){
					System.out.println("You pushed because you tied the Dealer.");
					System.out.println("\t" +this.name + "'s Current bank value: " + this.bank);
					this.h_winround = 2;
				}	
			}//no split
			
			if(splitcomplete==true){
				System.out.println("\t" +"First hand: ");
				if(h.getScore() > 21){
					System.out.println("You lose because you busted!");
					this.bank = this.bank - this.bet;
					System.out.println("\t" + this.name + "'s Current bank value: " + this.bank);
					this.h_winround = 0;
				}
				
				else if(dealerScore > 21){
					System.out.println("You win!  The Dealer busted!");
					this.bank = this.bank + this.bet;
					System.out.println("\t" +this.name + "'s Current bank value: " + this.bank);
					this.h_winround = 1;
				}
				
				else if( h.getScore() < dealerScore){
					System.out.println("You lose because the Dealer beat you!");
					this.bank = this.bank - this.bet;
					System.out.println("\t" +this.name + "'s Current bank value: " + this.bank);
					this.h_winround = 0;
	
				}
				else if( h.getScore() > dealerScore){
					System.out.println("You win!  You beat the Dealer!");
					this.bank = this.bank + this.bet;
					System.out.println("\t" +this.name + "'s Current bank value: " + this.bank);
					this.h_winround = 1;
				}
				
				else if( h.getScore() == dealerScore){
					System.out.println("You pushed because you tied the Dealer.");
					System.out.println("\t" +this.name + "'s Current bank value: " + this.bank);
					this.h_winround = 2;
				}	
				////results for first hand 'h'
				
				System.out.println("\t" +"Second hand: ");
				if(split.getScore() > 21){
					System.out.println("You lose because you busted!");
					this.bank = this.bank - this.split_bet;
					System.out.println("\t" + this.name + "'s Current bank value: " + this.bank);
					this.s_winround = 0;
				}
				
				else if(dealerScore > 21){
					System.out.println("You win!  The Dealer busted!");
					this.bank = this.bank + this.split_bet;
					System.out.println("\t" +this.name + "'s Current bank value: " + this.bank);
					this.s_winround = 1;
				}
				
				else if( split.getScore() < dealerScore){
					System.out.println("You lose because the Dealer beat you!");
					this.bank = this.bank - this.split_bet;
					System.out.println("\t" +this.name + "'s Current bank value: " + this.bank);
					this.s_winround = 0;
	
				}
				else if( split.getScore() > dealerScore){
					System.out.println("You win!  You beat the Dealer!");
					this.bank = this.bank + this.split_bet;
					System.out.println("\t" +this.name + "'s Current bank value: " + this.bank);
					this.s_winround = 1;
				}
				
				else if( split.getScore() == dealerScore){
					System.out.println("You pushed because you tied the Dealer.");
					System.out.println("\t" +this.name + "'s Current bank value: " + this.bank);
					this.s_winround = 2;
				}	
				////results for second hand 'split'
				
				
				
			}//did split
			
			
			
			
			
		}//isdealer false
		
		
		h.discardAll();
		split.discardAll();
		
		return; 
		
	}
	
	
	
	
	
	public void finishRoundDealer(double totalbets){
		
		this.bank = this.bank + totalbets;
		System.out.println("\t" + "Dealer: " + this.name + "'s Current bank value: " + this.bank);
		
	}
	
	
	
	
	
}
