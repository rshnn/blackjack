// This is the main program for the blackjack game.
public class Blackjack
{
	// This method should:
	//	- Ask the user how many people want to play (up to 3, not including the dealer).
	//	- Create an array of players.
	//	- Create a Blackjack window.
	// 	- Play rounds until the players want to quit the game.
	//	- Close the window.
	public static void main(String[] args)
	{
		
		System.out.println("================================================");
		System.out.println("===================BLACKJACK====================");
		System.out.println("================================================");
		System.out.println("");
		System.out.println("                           .------.                                             ");
		System.out.println("        .------.           |A .   |    ");
		System.out.println("        |A_  _ |    .------; / \\  |     ");
		System.out.println("        |( \\/ )|-----. _   |(_,_) |   ");
		System.out.println("        | \\  / | /\\  |( )  |  I  A|                                             ");
		System.out.println("        |  \\/ A|/  \\ |_x_) |------'                                             ");
		System.out.println("        `-----+'\\  / | Y  A|                               ");
		System.out.println("              |  \\/ A|-----'                                                    ");
		System.out.println("              `------'                                                          ");
		System.out.println("");
		System.out.println("================================================");
		System.out.println("================================================");
		

		
		System.out.println("");
		System.out.println("How many players? (Not including dealer)  Enter number:");
		int num_players = IO.readInt(); num_players = num_players + 1;
		boolean dealerpresent = false;
		
		Player[] players = new Player [num_players];
		
		for(int i=1; i<num_players+1;i++){
			
			System.out.println("Name of player "+ i +" :" );
			String name = IO.readString();
			System.out.println("Is player " + i + " the dealer?    Y/N");
			boolean dealer = IO.readBoolean();
			System.out.println("What is " + i + " 's starting bank value?  Double in dollars:");
			double bank = IO.readDouble();
			// Ask user for player name and if he/she is dealer
			
			
			if(dealer == true && dealerpresent == true){
				System.out.println("Sorry, only one dealer allowed!  Make " + name + " dealer instead?");
				boolean x = IO.readBoolean();
				
				if( x == true){
					for(int j=0; j<players.length;j++){
						if(players[j] != null){
							if(players[j].isDealer() == true){
								players[j].setDealer(false);
							}
						}
					}	
				}
				if( x == false){
					dealer = false;
				}	
			} //request dealer if already dealer present.  Ask user for input.
			
			if(dealer == true && dealerpresent == false){
				dealerpresent = true;
			} //declares that a dealer exists so far.
			
			System.out.println("");
			
			players[i-1] = new Player(name, dealer, bank);
		}		
		//create array of Player object called players.
		//players.length is the number of the players (user input)
		
		
		
		System.out.println("====================================================");

		
		//BlackjackWindow window = new BlackjackWindow (players);
			//create blackjack window, window.
		
		System.out.println("How many decks would you like to play with?");
		int num_decks = IO.readInt();
		
		Deck d = new Deck(num_decks);
		d.shuffle();
		//create deck and shuffle.
		
		boolean dealerlose = false;
		boolean quit = false;
		while(quit == false){	
			playRound(players, d);
			
			for(int i=0; i<players.length;i++){
				if(players[i].isDealer()==true){
					if(players[i].getBank() <= 0){
						System.out.println("");
						System.out.println("");
						System.out.println("GAME OVER!  Dealer's bank has run out!  Congrats players!");
						dealerlose = true;
					}
				}
			}
			
			if(dealerlose != true){
				
				System.out.println("====================================================");
				System.out.println("Do you want to quit?   Y/N"); quit = IO.readBoolean();
				//play round, ask to quit after round is over.
				
				
				boolean changeDealer = false;
				String newdealer = "";
				if( quit == false){
					System.out.println("Change dealer?");
					changeDealer = IO.readBoolean();
					if(changeDealer == true){
						System.out.println("Who is the new dealer?");
						newdealer = IO.readString();
						
						for(int i=0; i<players.length;i++){
							players[i].setDealer(false);
						}
						
						for(int i=0; i<players.length;i++){
							if(players[i].getName().equals(newdealer)){
								players[i].setDealer(true);
							}	
						}
					}	
				}
			}
			//if not quitting, change dealer?
			//ask who new dealer is.  set new dealer.
			else if(dealerlose == true){
				break;
			}
			
			
		}
		//play rounds until user decides to quit.
		
		
		System.out.println("");
		System.out.println("====================================================");
		System.out.println("====================================================");

		System.out.println("              __                                               ");
		System.out.println("        _..-''--'----_.                                        ");
		System.out.println("      ,''.-''| .---/ _`-._                                     ");
		System.out.println("    ,' \\ \\  ;| | ,/ / `-._`-.                                  ");
		System.out.println("  ,' ,',\\ \\( | |// /,-._  / /                                  ");
		System.out.println("  ;.`. `,\\ \\`| |/ / |   )/ /                                   ");
		System.out.println(" / /`_`.\\_\\ \\| /_.-.'-''/ /                                    ");
		System.out.println("/ /_|_:.`.\\ |;'`..')  / /         THANKS FOR                  ");
		System.out.println("`-._`-._`.`.;`.\\  ,'  / /           PLAYING!                   ");
		System.out.println("    `-._`.`/    ,'-._/ /                                       ");
		System.out.println("      : `-/     \\`-.._/                                        ");
		System.out.println("      |  :      ;._ (                                          ");
		System.out.println("      :  |      \\  ` \\                                         ");
		System.out.println("       \\         \\   |                                         ");
		System.out.println("        :        :   ;                                         ");
		System.out.println("        |           /                                          ");
		System.out.println("        ;         ,'                                           ");
		System.out.println("       /         /                                             ");
		System.out.println("      /         /                                              ");
		System.out.println("               /                                               ");
		System.out.println("====================================================");
		System.out.println("====================================================");



		
		
	}

	// This method executes an single round of play (for all players).
	//	- Create and shuffle a deck of cards.
	//	- Start the round (deal cards) for each player, then the dealer.
	//	- Allow each player to play, then the dealer.
	//	- Finish the round (announce results) for each player.
	public static void playRound(Player[] players,Deck d) //, BlackjackWindow window
	{
		
		for(int i=0; i<players.length;i++){
			if(players[i].isDealer() == true){
				Player temp = players[i];
				players[i] = players[players.length-1];
				players[players.length-1] = temp;
				break;
			}		
		} // puts dealer as last player. search and replace.
		
		
		for(int i=0; i<players.length;i++){
			players[i].startRound(d);
			System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++");
			System.out.println("");
		}
		//players + dealer start round. Card dealt.
		
		
		
		for(int i=0; i<players.length;i++){
			if(players[i].isDealer() == false)
				Hint(players[i]);
			players[i].playRound(d);
			System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++");
			System.out.println("");
		}
		//players + dealer play round. Hit or stand.
		
		
		
		int dealerhand = 0;
		for(int i=0; i<players.length;i++){
			if(players[i].isDealer() == true){
				dealerhand = players[i].getHand().getScore();
			}	
		}
		//get dealer's hand value.
		
		
		double totalbets = 0;
		
		for(int i=0; i<players.length;i++){
			players[i].finishRound(dealerhand);
			if(players[i].getWinH() == 1)
				totalbets = totalbets - players[i].getBetH();
			if(players[i].getWinH() == 0)
				totalbets = totalbets + players[i].getBetH();
			
			if(players[i].getWinS() == 1)
				totalbets = totalbets - players[i].getBetS();
			if(players[i].getWinS() == 0)
				totalbets = totalbets + players[i].getBetS();
			
			if(players[i].isDealer() == true){
				players[i].finishRoundDealer(totalbets);
			}
			
		}
		
		
		//players finish round.  Declare victories and losses based on dealer's score.
		//dealer finishes round.  add/subtract from bank as necessary.
		
		
		
		
		
		
	
	
	}
	
	


	public static void Hint(Player p){
		
		System.out.println("Would you like a hint?");
		boolean takehint = IO.readBoolean();
		
		if(takehint == true){
			int score = p.getHand().getScore();
					
			if(score == 21)
				System.out.println("You have blackjack!");
			if(score == 20) 
				System.out.println("There is about a 92 % chance that you will bust if you hit.");
			if(score == 19)
				System.out.println("There is about a 85 % chance that you will bust if you hit.");
			if(score == 18)
				System.out.println("There is about a 77 % chance that you will bust if you hit.");
			if(score == 17)
				System.out.println("There is about a 69 % chance that you will bust if you hit.");
			if(score == 16)
				System.out.println("There is about a 62 % chance that you will bust if you hit.");
			if(score == 15)
				System.out.println("There is about a 54 % chance that you will bust if you hit.");
			if(score == 14)
				System.out.println("There is about a 46 % chance that you will bust if you hit.");
			if(score == 13)
				System.out.println("There is about a 38 % chance that you will bust if you hit.");
			if(score == 12)
				System.out.println("There is about a 31 % chance that you will bust if you hit.");
			if(score <= 11)
				System.out.println("There is about a 0 % chance that you will bust if you hit.");
			
		}
		
	}

}
