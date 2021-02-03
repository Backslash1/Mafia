package Assignment3;

import java.util.ArrayList;

public class Commoner extends Player implements Strategy{
	
	private static ArrayList<Commoner> CommonerList = new ArrayList<Commoner>();
	private static int totalCommoner;
	public Commoner() {
		super();
		updateTotalCommoner();
		setType("Commoner");
		setHP(1000);	
		CommonerList.add(this);
	}
	public boolean equals(Object p) {
		//Returns if same object
		if(p!=null && getClass() == p.getClass()) {
			Commoner commoner = (Commoner)p ;
			return commoner.getPlayerId() == this.getPlayerId();
		}
		else {
			return false;
		}
	}
	public static void playStrategy() {
		//Implement here
		votePlayerRandomly2();
	}
	private static void votePlayerRandomly() {
		ArrayList<Player> playerList = Player.getPlayerList();
		int random ;
		while(true) {
			random = Random.getRandom(1, playerList.size());
			Player p = playerList.get(random-1);
			System.out.println("Random player : "+p.toString());
			if(!p.isVotedOut() ) {
				//Kill player p
				System.out.println("To be healed is : ");
				System.out.println(p.toString());
				
				//healHP(p);
				break;
			}
			//Otherwise keep selecting  randomly until a valid player that is not Mafia is selected
			continue;
		}
	}
	private static void votePlayerRandomly2() {
		ArrayList<Player> playerList = Player.getPlayerList();
		int random ;
		Player pcaster ;
		for(int i = 0 ; i < playerList.size() ; i ++) {
			pcaster = playerList.get(i);
			if(pcaster.isVotedOut()) {
				//Player p can not vote now
				continue;
			}
			//Now let the player p cast his/her vote randomly
			while(true) {
				random = Random.getRandom(1, playerList.size());
				Player pVoted = playerList.get(random-1);
				//System.out.println("Random player voted : "+pVoted.toString());
				if(pcaster.equals(pVoted)) {
					//voted to himself
					//System.out.print("Don't vote for yourself. ");
					continue;
				}
				if(!pVoted.isVotedOut()) {
					//System.out.println("To be voted is : "+pVoted.toString());
					//
					Vote.giveVote(random - 1);
					break;
				}
				//if still not chosen
				continue;
			}
		}
	}

	public boolean checkValidity(int i , int flag) {
		//System.out.println("In player sub check Validity----------------------");
		Player p = Player.PlayerList.get(i);
		if(p.isVotedOut() && equals(p)) {
			if(flag == 1) {
				System.out.print("Player "+ (i+1)+" is already voted out. ");
			}
			if(equals(p) && flag == 1) {
				System.out.print("Don't vote for yourself. ");
			}
			return false;
		}
		
		return true;
	}
	public void OtherPlayers() {
		//Commoner doesn't know about anyone
		System.out.println();
	}
	public static int getTotalCommoner() {
		int totalCommoner = 0;

		for(int i = 0 ; i< CommonerList.size() ; i++) {
			Player p = CommonerList.get(i);
			if(!p.isVotedOut()) {
				totalCommoner++;
			}
		}
		return totalCommoner;
	}

	public static void updateTotalCommoner() {
		Commoner.totalCommoner ++;
	}
	public static void printEnd() {
		for(int i = 0;i<CommonerList.size();i++) {
			Player p = CommonerList.get(i);
//			if(i == CommonerList.size()-1) {
//				System.out.print("Player "+p.getPlayerId());
//				continue;
//			}
			System.out.print("Player "+p.getPlayerId()+",");
			
		}
		System.out.println("were Commoners.");
	}
	public String toString() {
		return "pid : "+getPlayerId()+" type : Commoner "+" hp : "+getHP()+" votedOut : "+isVotedOut();
	}
	public static void printDetails() {
		System.out.println("Commoner list and size : " + CommonerList.size());
		for (int i = 0; i < CommonerList.size(); i++) {
			System.out.println(CommonerList.get(i).toString());
		}
	}
	public static int getListSize() {
		return CommonerList.size();
	}
	public static Commoner getPlayer(int i) {
		return CommonerList.get(i);
	}
}
