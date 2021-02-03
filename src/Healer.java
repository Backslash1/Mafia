package Assignment3;

import java.util.ArrayList;

public class Healer extends Player implements Strategy{
	
	private static ArrayList<Healer> HealerList = new ArrayList<Healer>();
	private static int totalHealer;
	
	public Healer() {
		super();
		updateTotalHealer();
		setType("Healer");
		setHP(800);	
		HealerList.add(this);
	}
	public boolean equals(Object p) {
		//Returns if same object
		if(p!=null && getClass() == p.getClass()) {
			Healer healer = (Healer)p ;
			return healer.getPlayerId() == this.getPlayerId();
		}
		else {
			return false;
		}
	}
	public static void playStrategy() {
		//Implement here
		healPlayerRandomly();
		
	}
	private static void healPlayerRandomly() {
		//Can be himself as well
		ArrayList<Player> playerList = Player.getPlayerList();
		int random ;
		while(true) {
			random = Random.getRandom(1, playerList.size());
			Player p = playerList.get(random-1);
			//System.out.println("Random player : "+p.toString());
			if(!p.isVotedOut()) {
				//Kill player p
				//System.out.println("To be healed is : ");
				//System.out.println(p.toString());
				//Update the HP of Player killed and Both the Mafia's who chose to kill him
				healHP(p);
				break;
			}
			//Otherwise keep selecting  randomly until a valid player that is not Mafia is selected
			continue;
		}
	}
	public static void healPlayerStrategically(int i) {
		Player p = PlayerList.get(i);
		healHP(p);
	}
	private static void healHP(Player p) {
		//heal that player
		//System.out.println("updating.......");
		p.setHP(p.getHP()+500);
		
	}
	public boolean checkValidity(int i , int flag) {
		//System.out.println("In player sub check Validity----------------------");
		Player p = Player.PlayerList.get(i);
		if(p.isVotedOut()) {
			if(flag == 1) {
				System.out.print("Player "+(i+1)+" is already voted out. ");
			}
			return false;
		}
		
		return true;
	}
	public void OtherPlayers() {
		System.out.print("Other Healers are : ");
		boolean Empty = true;
		
		for( int i = 0 ; i < HealerList.size();i++) {
			Player p = HealerList.get(i);
			
			if(!this.equals(p)) {
				System.out.print("[Player "+p.getPlayerId()+"] ");
				Empty = false;
			}
		}
		if(Empty) {
			System.out.print("[None]");
		}
		System.out.println();
	}
	public static int getTotalHealer() {
		int totalHealer = 0;

		for(int i = 0 ; i< HealerList.size() ; i++) {
			Player p = HealerList.get(i);
			if(!p.isVotedOut()) {
				totalHealer++;
			}
		}

		return totalHealer;
	}
	public static void printEnd() {
		for(int i = 0;i<HealerList.size();i++) {
			Player p = HealerList.get(i);
//			if(i == HealerList.size()-1) {
//				System.out.print("Player "+p.getPlayerId());
//				continue;
//			}
			System.out.print("Player "+p.getPlayerId()+",");
			
		}
		System.out.println("were Healers.");
	}
	public static void updateTotalHealer() {
		Healer.totalHealer ++;
	}

	public String toString() {
		return "pid : "+getPlayerId()+" type : Healer "+" hp : "+getHP()+" votedOut : "+isVotedOut();
	}
	public static void printDetails() {
		System.out.println("Healer list and Size : "+ HealerList.size());
		for (int i = 0; i < HealerList.size(); i++) {
			System.out.println(HealerList.get(i).toString());
		}
	}
	public static int getListSize() {
		return HealerList.size();
	}
	public static Healer getPlayer(int i) {
		return HealerList.get(i);
	}
}
