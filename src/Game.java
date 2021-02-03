package Assignment3;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Game {
	Scanner input = new Scanner(System.in);
	
	private static boolean getWinningCriteriaForMafia() {

		return Player.getTotalPlayer()-Mafia.getTotalMafia() <= Mafia.getTotalMafia();
	}
	
	public void GameStart() {
		int roundNumber = 1;
		Scanner input = new Scanner(System.in);
		System.out.println("Choose a Character");
		System.out.println("1) Mafia");
		System.out.println("2) Detective");
		System.out.println("3) Healer");
		System.out.println("4) Commoner");
		System.out.println("5) Assign Randomly");
		int characterNo ;
		while(true) {
			try {
				characterNo = input.nextInt();
				if(characterNo<1 || characterNo >5) {
					System.out.print("Enter value between [1,5]...");
					continue;
				}
			}
			catch(InputMismatchException e) {
				System.out.print("Enter a valid input...");
				input.nextLine();
				continue;
			}
			break;
		}
		
		String Character = AssignCharacter(characterNo); //Character name
		Player  p = AssignPlayer(Character); //Assign player
		System.out.println("You are player : " + p.getPlayerId());
		System.out.print("You are a " + p.getType()+". ");
		p.OtherPlayers();
		//p.playStrategy();
		//System.out.println("You are player");
		//System.out.println(p.equals(p));
		while(true) {
			
			if(getWinningCriteriaForMafia()) {
				//Game ends Mafia wins
				//exit from the loop
				System.out.println();
				System.out.println("GAME OVER!!!");
				System.out.println("The Mafias have won.");
				break;
			}
			if(Mafia.getTotalMafia() == 0) {
				//Lineage of Mafia's end here...
				//Mafia's lost the battle
				System.out.println();
				System.out.println("GAME OVER!!!");
				System.out.println("The Mafias have lost.");
				break;
			}
			System.out.println("----------Round "+roundNumber+" -------------- ");
			System.out.print(Player.getPlayerRemaining()+" players are remaining : ");
			Player.printRemainingPlayers();
			/* Code for game play begins from here */
			
			/* 
			 	We would be playing with player p and all the other players are simulated by our program.
			 	In case the user or player p dies then the user controls would be transferred to our program, in short we would be
			 	simulated as well.
				
				1- Mafia chooses to kill someone.
				2- Detective suspects a player to be Mafia
				3- Healer heals a player
				4- Then the user player p votes for someone to eliminate him/her
				5- Then commoners vote for a player.
				
			*/
			
			/*
				Mafia should have a function to choose a player to kill.
				If user is Mafia then we should skip the function and choose to kill him/her ourselves
				Try to minimise if else statements by using the advantage of polymorphism
				Similarly do this for every type of player.
			 
			 */
			if(Mafia.getTotalMafia() > 0) {
				//Mafia not voted out
				//Mafia still can be at HP = 0
				MafiaTurn(p);
			}
			else {
				//All the Mafia's are voted out already so the game is over.
				System.out.println("Mafia's has chosen a target.");
				//break;
			}
			if(Detective.getTotalDetective() > 0) {
				//Turn for detective
				DetectiveTurn(p);
//				if(Player.isMafiaCaught()) {
//					//Mafia caught
//					System.out.println("Player "+ Player.getMafiaCaughtNo()+" is a Mafia");
//				}
//				else {
//					System.out.println("Player");
//				}
				//System.out.println("Is mafia caught : " + Player.isMafiaCaught());
				//System.out.println("Mafia number : "+ Player.getMafiaCaughtNo());
//				if(Player.getMafiaCaughtNo()>0) {
//					
//					Player.getPlayer(Player.getMafiaCaughtNo()-1).setVotedOut(true);
//					System.out.println();
//				}
				
			}
			else {
				System.out.println("Detectives have tested someone. ");
			}
			if(Healer.getTotalHealer() > 0) {
				
				HealerTurn(p);
				
			}
			else {
				System.out.println("Healer has chosen someone to heal. ");
			}
			
			System.out.println("--End of actions--");
			
			//Now check that even after healing is there someone with HP of 0
			//If found than vote out that player
			Player removeP = Player.getZeroHP();
			if(removeP != null) {
				removeP.setVotedOut(true);
				System.out.println("Player "+ removeP.getPlayerId()+" has died.");
			}
			else {
				System.out.println("No one died.");
			}
			
			
			
			/*
			 	  If detective found the Mafia
			 	  Don't do the voting just simply remove the Mafia or Vote him out
			 	  
			 */
			if(Player.isMafiaCaught()) {
				//Skip the voting process and don't let the Commoner vote
				//If the user is commoner then tell him to please bear with us
				Player.getPlayer(Player.getMafiaCaughtNo()-1).setVotedOut(true);
				//Removed the player Mafia
				Player.setMafiaCaught(false);
				Player.setMafiaCaughtNo(0);
				System.out.println("---------End of round " + roundNumber+"----------"+'\n');
				roundNumber++;
				continue;
			}
			/*
			 	Initialise vote object as it is that time of the year where we cast out votes.
			 	
			*/
			new Vote();
			
			//Voting process for non commoner
//			VoteNotCommoner(p);
			System.out.println("-------------voting start----------------");
			VotingTurn(p); //Everyone will vote
			
//			if(Commoner.getTotalCommoner() > 0) {
//				//Turn for a commoner
//				VotingTurn(p);
//			}
			while(true) {
				if(Vote.hasVoteClashing()) {
					System.out.println("Voting clashed. New voting process begins...");
					//This means that there are 2 or more  players having the same votes
					//We need to call again the automated voting mechanism
					//Only commoners will take part in it
					Vote.clearVotes(); // firstly remove the previous votes
					//if(p.getType().equals("Commoner") && !p.isVotedOut()) {
					VotingTurn(p);
					
					//VoteNotCommoner(p);
					
				}
				else {
					//No vote clashing now
					break;
				}
			}
			//Now remove the highest voted person from the game
			Player votedMost = Vote.getMaximumVoted();
			votedMost.setVotedOut(true);
			System.out.println("Player "+votedMost.getPlayerId()+" has been voted out.");
			//Reset the variables that are static
			
			Player.setMafiaCaught(false);
			Player.setMafiaCaughtNo(0);
			Vote.clearVotes();
			System.out.println("----------End of round " + roundNumber+"----------"+'\n');
			roundNumber++;
			//break;
		}
		System.out.println();
		
		Mafia.printEnd();
		Detective.printEnd();
		Healer.printEnd();
		Commoner.printEnd();
		
		//Player.printDetails();
		input.close();
	}
	

	private void MafiaTurn(Player p) {
		//Scanner input = new Scanner(System.in);
		int target;
		if(p.getType().equals("Mafia") && !p.isVotedOut()) {
			//User is Mafia
			
			while(true) {
				System.out.print("Choose a target : ");
				target = input.nextInt();
				//If target is mafia or he has chosen himself then prompt user to enter the input again
				boolean noMoreInput = p.checkValidity(target-1 , 1);
				if(noMoreInput) {
					break;
				}
				else {
					//System.out.print("You can't target a Mafia. ");
					continue;
				}	
			}	
			
			//Now we must kill Player PLayerList[target-1]
			//Implement here...
			Mafia.killPLayerStrategically(target-1);
		}
		else{
			//Automate the play for Mafia
			System.out.println("Mafia's has chosen a target.");
			Mafia.playStrategy();
		}
		
		
	}
	private void HealerTurn(Player p) {
		//Scanner input2 = new Scanner(System.in);
		if(p.getType().equals("Healer") && !p.isVotedOut()) {
			//User is Healer
			//He can choose to heal himselve as well
			int heal;
			while(true) {
				System.out.print("Choose to heal someone : ");
				//input.nextLine();
				heal = input.nextInt();
				//int heal = 1;
				boolean noMoreInput = p.checkValidity(heal-1 , 1);
				if(noMoreInput) {
					break;
				}
				else {
					
					continue;
				}
			}
			//Now heal player PlayerList[heal-1]
			//Implement here
			Healer.healPlayerStrategically(heal-1);
		}
		else {
			//Automate the play for Healer
			System.out.println("Healers have chosen someone to heal.");
			Healer.playStrategy();
		}
	}
	private void DetectiveTurn(Player p) {
		
		if(p.getType().equals("Detective") && !p.isVotedOut()) {
			//User is Mafia
			int target;
			while(true) {
				System.out.print("Choose a player to test : ");
				target = input.nextInt();
				//If target is mafia or he has chosen himself then prompt user to enter the input again
				boolean noMoreInput = p.checkValidity(target-1 , 1);
				if(noMoreInput) {
					break;
				}
				else {
					//System.out.print("You can't target a Mafia. ");
					continue;
				}	
			}
			//Now check if you chose Mafia or not
			//If mafia chosen then Mafia gets voted out
			//Implement here
			Detective.DetectPlayerStrategically(target-1);
			if(Player.isMafiaCaught()) {
				//Mafia caught
				System.out.println("Player "+ Player.getMafiaCaughtNo()+" is a Mafia.");
			}
			else {
				//You fool of a took
				System.out.println("Player "+ target + " is not a Mafia.");
			}
			
		}
		else{
			//Automate the play for detective
			System.out.println("Detectives have chosen a player to test.");
			Detective.playStrategy();
		}
		
	}
	private void VotingTurn(Player p) {
		//Commoner will cast vote only
		if(!p.isVotedOut()) {
			//User is Mafia
			int vote;
			while(true) {
				System.out.print("Choose a player to vote : ");
				vote = input.nextInt();
				//If target is mafia or he has chosen himself then prompt user to enter the input again
				boolean noMoreInput = p.checkValidity2(vote-1 , 1);
				if(noMoreInput) {
					break;
				}
				else {
					//System.out.print("You can't target a Mafia. ");
					continue;
				}	
			}
			//Now vote commoner
			//You don't vote for yourself
			// Vote strategically
			Player.votePlayerStrategically(vote-1, p);
		}
//		else{
//			//Automate the play for detective
//			Commoner.playStrategy();
//		}
		Player.playStrategy();
	}
	private Player AssignPlayer(String c) {
		int rand;
		Player player; // Polymorphism 
		//As Player player = PlayerType object
		
		if(c.equals("Detective")) {
			//Assign player from DetectiveList
			rand = Random.getRandom(1, Detective.getListSize());
			player = Detective.getPlayer(rand-1);
			return Player.getPlayer(player.getPlayerId()-1);
		}
		else if(c.equals("Mafia")) {
			rand = Random.getRandom(1, Mafia.getListSize());
			player = Mafia.getPlayer(rand-1);
			return Player.getPlayer(player.getPlayerId()-1);
		}
		else if(c.equals("Healer")) {
			rand = Random.getRandom(1, Healer.getListSize());
			player = Healer.getPlayer(rand-1);
			return Player.getPlayer(player.getPlayerId()-1);
		}
		else if(c.equals("Commoner")) {
			rand = Random.getRandom(1, Commoner.getListSize());
			player = Commoner.getPlayer(rand-1);
			return Player.getPlayer(player.getPlayerId()-1);
		}
		return null;
		
	}
	private String AssignCharacter(int c) {
		
		if(c == 5) {
			//Assigns a random characterNo
			c = Random.getRandom(1, 4);
		}
		
		if(c == 1) {
			return "Mafia"; 
		}
		else if(c == 2) {
			return "Detective";
		}
		else if(c == 3) {
			return "Healer";
		}
		else if(c == 4) {
			return "Commoner";
		}
		
		return "";
	}
}
