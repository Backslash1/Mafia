package Assignment3;

import java.util.ArrayList;

public class Detective extends Player implements Strategy{
	
	private static ArrayList<Detective> DetectiveList = new ArrayList<Detective>();
	private static int totalDetective;
	
	public Detective() { 
		super();
		updateTotalDetective();
		setType("Detective");
		setHP(800);	
		DetectiveList.add(this);
	}
	public boolean equals(Object p) {
		//Returns if same object
		if(p!=null && getClass() == p.getClass()) {
			Detective detective = (Detective)p ;
			return detective.getPlayerId() == this.getPlayerId();
		}
		else {
			return false;
		}
	}
	public static boolean equalClass(Object p) {
		String pclass = (String)p.getClass().getCanonicalName();
		String name = Thread.currentThread().getStackTrace()[1].getClassName();
		
		if(p!=null && name.equals(pclass)) {
			//System.out.println("In here");
			return true;
		}
		else {
			return false;
		}
	}
	public static void playStrategy() {
		//Implement here
		DetectPlayerRandomly();
		
	}
	private static void DetectPlayerRandomly() {
		//Detect player except of his own kin
		ArrayList<Player> playerList = Player.getPlayerList();
		int random ;
		Player p;
		while(true) {
			random = Random.getRandom(1, playerList.size());
			p = playerList.get(random-1);
			//System.out.println("Random player : "+p.toString());
			if(!p.isVotedOut() && !equalClass(p)) {
				//System.out.println("To be Detect is : ");
				//System.out.println(p.toString());
				
				CheckIfMafiaCaught(p);
				break;
			}
			//Otherwise keep selecting  randomly until a valid player that is not Mafia is selected
			continue;
		}
		//We now have Player p, which is to be tested
		
		
	}
	public static void DetectPlayerStrategically(int i) {
		Player p = PlayerList.get(i);
		CheckIfMafiaCaught(p);
	}
	private static void CheckIfMafiaCaught(Player p) {
		if(p.getType().equals("Mafia")) {
			setMafiaCaughtNo(p.getPlayerId());
			setMafiaCaught(true);
		}
		//Do nothing
	}
	public boolean checkValidity(int i ,int flag) {
		//System.out.println("In player sub check Validity----------------------");
		Player p = Player.PlayerList.get(i);
		if(p.isVotedOut()) {
			if(flag == 1) {
				System.out.println("Player "+(i+1)+" is already voted out");
			}
			return false;
		}
		if(this.getClass() == p.getClass()) {
			if(flag == 1) {
				System.out.println("You can not test a detective. ");
			}
			return false;
		}
		return true;
	}
	public void OtherPlayers() {
		System.out.print("Other Detectives are : ");
		boolean Empty = true;
		for(int i = 0 ; i < DetectiveList.size();i++) {
			Player p = DetectiveList.get(i);
			
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
	public static int getTotalDetective() {
		int totalDetective = 0;
		
		for(int i = 0 ; i<DetectiveList.size() ; i++) {
			Player p = DetectiveList.get(i);
			if(!p.isVotedOut()) {
				totalDetective++;
			}
		}
		return totalDetective;
	
	}
	private static void updateTotalDetective() {
		Detective.totalDetective ++;
	}
	public String toString() {
		return "pid : "+getPlayerId()+" type : Detective "+" hp : "+getHP()+" votedOut : "+isVotedOut();
	}
	
	public static void printDetails() {
		System.out.println("Detective list and Size : "+ DetectiveList.size());
		for (int i = 0; i < DetectiveList.size(); i++) {
			System.out.println(DetectiveList.get(i).toString());
		}
	}
	public static void printEnd() {
		for(int i = 0;i<DetectiveList.size();i++) {
			Player p = DetectiveList.get(i);
//			if(i == DetectiveList.size()-1) {
//				System.out.print("Player "+p.getPlayerId());
//				continue;
//			}
			System.out.print("Player "+p.getPlayerId()+",");
			
		}
		System.out.println("were Detectives.");
	}
	public static int getListSize() {
		return DetectiveList.size();
	}
	public static Detective getPlayer(int i) {
		return DetectiveList.get(i);
	}
}
