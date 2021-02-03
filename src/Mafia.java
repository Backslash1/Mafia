package Assignment3;

import java.util.ArrayList;

public class Mafia extends Player implements Strategy{
	
	private static ArrayList<Mafia> MafiaList = new ArrayList<Mafia>();
	private static int totalMafia = 0;
	
	public Mafia() {
		super();
		updateTotalMafia();
		setType("Mafia");
		setHP(2500);
		MafiaList.add(this);
	}
	public boolean equals(Object p) {
		//Returns if same object
		if(p!=null && getClass() == p.getClass()) {
			Mafia mafia = (Mafia)p ;
			return mafia.getPlayerId() == this.getPlayerId();
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
//		MafiaList.get(0).setHP(700);
//		MafiaList.get(1).setHP(300);
		killPlayerRandomly();
		//Choose a player randomly
	}
	private static int killPlayerRandomly() {
		//Shouldn't be a Mafia
		ArrayList<Player> playerList = Player.getPlayerList();
		int random ;
		while(true) {
			random = Random.getRandom(1, playerList.size());
			Player p = playerList.get(random-1);
			//System.out.println("Random player : "+p.toString());
			if(!p.isVotedOut() && !equalClass(p)) {
				//Kill player p
				//System.out.println("To be killed is : ");
				//System.out.println(p.toString());
				//Update the HP of Player killed and Both the Mafia's who chose to kill him
				updateHp(p);
				break;
			}
			//Otherwise keep selecting  randomly until a valid player that is not Mafia is selected
			continue;
		}
		return 0;
		
	}
	public static void killPLayerStrategically(int i) {
		Player p = PlayerList.get(i);
		updateHp(p);
	}
	private static void updateHp(Player p) {
		//Set the HP of player p to 0 if Mafias HP >= Player p's HP
		//Set the HP of player p to p_HP = p_HP - MafiasHp if Player p's HP > Mafias HP
		int playerHp = p.getHP(); //Player Hp is X
		
		ArrayList<Mafia> mafiaList = Mafia.MafiaList;
		
		int mafiaHp = 0;
		int totalMafia = Mafia.getTotalMafia();
		
		for(int i = 0 ; i < mafiaList.size() ; i++) {
			mafiaHp += mafiaList.get(i).getHP();
		}
		int reduceBy = playerHp/totalMafia;
		
		if(mafiaHp > playerHp) {
			p.setHP(0);
		}
		else {
			p.setHP(p.getHP() - mafiaHp);
		}
		int totalHPtoreduce = totalMafia*reduceBy;

		int leftover = 0;
		while(true) {
			int i;
			leftover = 0;
			boolean AllConsumed = false;
			for( i = 0 ; i < mafiaList.size() ; i++) {
				Mafia mp =  mafiaList.get(i);
				if(mp.getHP()>=reduceBy && mp.getHP()>0) {
					if(mp.getHP() == reduceBy) {
						totalMafia --;
					}
					mp.setHP(mp.getHP()-reduceBy);
					AllConsumed = true;
					
					continue;
					
				}
				else if(mp.getHP() < reduceBy && mp.getHP()>0){
					
					leftover+=reduceBy - mp.getHP();
					mp.setHP(0);
					totalMafia--;
					AllConsumed = true;
					continue;
				}
			}
			if(totalMafia == 0) {
				break;
			}
			if(!AllConsumed) {
				//Can't consume anymore
				break;
			}
			if(leftover == 0) {
				break;
			}
			i=0;
			reduceBy = leftover/totalMafia;
			
		}
		//printDetails();
		//System.out.println();
		//System.out.println(p.toString());
		//System.out.println("After");
		//p.printDetails(); //this is printing the super class list not of sub class p
		
	}
	public boolean checkValidity(int i , int flag) {
		//System.out.println("In player sub check Validity----------------------");
		Player p = Player.PlayerList.get(i);
		if(p.isVotedOut()) {
			if(flag == 1) {
				System.out.println("Player "+(i+1)+" is already voted out. ");
			}
			return false;
		}
		if(this.getClass() == p.getClass()) {
			if(flag == 1) {
				System.out.println("You can not target a Mafia. ");
			}
			return false;
		}
		return true;
	}
	public void OtherPlayers() {
		System.out.print("Other Mafias are : ");
		boolean Empty = true;
		for(int i = 0 ; i < MafiaList.size();i++) {
			Player p = MafiaList.get(i);
			
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
	public static int getTotalMafia() {
		int totalMafia = 0;
		for(int i = 0 ; i<MafiaList.size() ; i++) {
			Player p = MafiaList.get(i);
			if(!p.isVotedOut()) {
				totalMafia++;
			}
		}
		return totalMafia;
	}

	public static void updateTotalMafia() {
		Mafia.totalMafia ++;
	}
	
	public static void printEnd() {
		for(int i = 0;i<MafiaList.size();i++) {
			Player p = MafiaList.get(i);
//			if(i == MafiaList.size()-1) {
//				System.out.print("Player "+p.getPlayerId());
//				continue;
//			}
			System.out.print("Player "+p.getPlayerId()+",");
			
		}
		System.out.println("were Mafias.");
	}
	
	public String toString() {
		return "pid : "+getPlayerId()+" type : Mafia "+" hp : "+getHP()+" votedOut : "+isVotedOut();
	}
	public static void printDetails() {
		System.out.println("Mafia list and size : "+MafiaList.size());
		for (int i = 0; i < MafiaList.size(); i++) {
			System.out.println(MafiaList.get(i).toString());
		}
	}
	public static int getListSize() {
		return MafiaList.size();
	}
	public static Mafia getPlayer(int i) {
		return MafiaList.get(i);
	}

}
