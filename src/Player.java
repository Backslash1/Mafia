package Assignment3;

import java.util.ArrayList;

public class Player implements Strategy{
	
	private int HP;
	private String Type;
	private static int totalPlayer;
	private int PlayerId;
	private static int PlayerIdCounter = 0;
	private boolean votedOut ;
	private static boolean MafiaCaught = false;
	private static int MafiaCaughtNo ;
	private static int playerRemaining = 0;
	
	protected static ArrayList<Player> PlayerList = new ArrayList<Player>();
	
	public Player() {
		PlayerId = ++PlayerIdCounter;
		PlayerList.add(this);
		updateTotalPlayer();
		updatePlayerRemaining(1);
	}
	public static void printDetails() {
		System.out.println("Player list and size : "+PlayerList.size());
		for (int i = 0; i < PlayerList.size(); i++) {
			System.out.println(PlayerList.get(i).toString());
		}
	}
	public static void printRemainingPlayers(){
		for(int i = 0 ; i < PlayerList.size() ; i++) {
			Player p = PlayerList.get(i);
			if(! p.isVotedOut()) {
				System.out.print("Player"+p.getPlayerId()+", ");
			}
		}
		System.out.println();
	}
	public static Player getZeroHP() {
		Player p ;
		for(int i = 0 ; i < PlayerList.size(); i++) {
			p = PlayerList.get(i);
			if(p.getHP() <= 0 && !p.isVotedOut() && !p.getType().equals("Mafia")) {
				//If mafia then we don't delete it
				//And player should be in game
				//Mafia can be voted out so HP doesn't make any sense for it
				return p;
			}
		}
		return null;
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
	
	public static void votePlayerStrategically(int i , Player user) {
		// i --->playerId-1;
		
		boolean valid = user.checkValidity2(i, 1);
		if(valid) {
			Vote.giveVote(i);//due to implementation in Vote class
		}
	}
	public boolean checkValidity2(int i , int flag) {
		//System.out.println("In player check Validity----------------------");
		Player p = Player.PlayerList.get(i);
		if(p.isVotedOut() || equals(p)) {
			if(p.isVotedOut() && flag == 1) {
				System.out.print("Player "+ (i+1)+" is already voted out. ");
			}
			if(equals(p) && flag == 1) {
				System.out.print("Don't vote for yourself. ");
			}
			return false;
		}
		
		return true;
	}
	public boolean checkValidity(int i , int flag) {
		//System.out.println("In player check Validity----------------------");
		Player p = Player.PlayerList.get(i);
		if(p.isVotedOut() || equals(p)) {
			if(p.isVotedOut() && flag == 1) {
				System.out.print("Player "+ (i+1)+" is already voted out. ");
			}
			if(equals(p) && flag == 1) {
				System.out.print("Don't vote for yourself. ");
			}
			return false;
		}
		
		return true;
	}

	public boolean isVotedOut() {
		return votedOut;
	}

	public void setVotedOut(boolean votedOut) {
		updatePlayerRemaining(-1);
		this.votedOut = votedOut;
	}

	public int getHP() {
		return HP;
	}

	protected void setHP(int hP) {
		HP = hP;
	}
	
	public static boolean isMafiaCaught() {
		return MafiaCaught;
	}
	protected static void setMafiaCaught(boolean mafiaCaught) {
		MafiaCaught = mafiaCaught;
	}
	
	public static int getMafiaCaughtNo() {
		return MafiaCaughtNo;
	}
	protected static void setMafiaCaughtNo(int mafiaCaughtNo) {
		MafiaCaughtNo = mafiaCaughtNo;
	}
	public static ArrayList<Player> getPlayerList() {
		return PlayerList;
	}

	private static void setPlayerList(ArrayList<Player> playerList) {
		PlayerList = playerList;
	}

	public static void printPlayerList() {
		for (int i = 0; i < PlayerList.size(); i++) {
			System.out.println(PlayerList.get(i).toString());
		}
	}
	public void OtherPlayers() {
		
	}

	public static int getPlayerRemaining() {
		return playerRemaining;
	}
	protected static void updatePlayerRemaining(int playerRemaining) {
		Player.playerRemaining += playerRemaining;
	}
	public static int getTotalPlayer() {
	int totalPlayer = 0;
		
		for(int i = 0 ; i<PlayerList.size() ; i++) {
			Player p = PlayerList.get(i);
			if(!p.isVotedOut()) {
				totalPlayer++;
			}
		}
		return totalPlayer;
	
		
	}

	public static void updateTotalPlayer() {
		Player.totalPlayer ++;
	}

	public String toString() {
		return "Pid : " + Integer.toString(PlayerId)+" hp : " + Integer.toString(getHP()) +" type : "+ getType();
	}
	
	public String getType() {
		return Type;
	}

	public void setType(String type) {
		Type = type;
	}

	public int getPlayerId() {
		return PlayerId;
	}

	private void setPlayerId(int playerId) {
		PlayerId = playerId;
	}
	public static Player getPlayer(int i) {
		return PlayerList.get(i);
	}

	
	public static void playStrategy() {
		//Don't implement here
		//No need...as subclasses are already implementing it
		votePlayerRandomly2();
	}
	
}
