package Assignment3;

import java.util.InputMismatchException;
import java.util.Scanner;

public class MafiaMain {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//Here goes the game
		
		Scanner in = new Scanner(System.in);
		System.out.println("WELCOME TO MAFIA");
		
		int N;
		while(true) {
			System.out.print("Enter number of players : ");
			try {
				N = in.nextInt();//total players in game
				if(N<6) {
					System.out.println("Enter a value of N > 6... ");
					continue;
				}
				break;
			}
			catch(InputMismatchException e) {
				System.out.println("Enter a valid number!");
				in.nextLine();
				//continue;
			}
		}
		
		int totalMafias = N/5;
		int totalDetectives = N/5;
		int totalHealers = Math.max(1, N/10);
		int totalCommoners = N - totalDetectives - totalHealers - totalMafias;
		int j = 10;
		
		int tm =N/5 , td = N/5 , th = Math.max(1, N/10) , tc = N - td-tm-th;
		
		//Assign player number randomly to each type
		while(true) {
			int r = Random.getRandom(1, 4);
			if(r == 1 && tm >0) {
				//assign mafia
				new Mafia();
				tm--;
			}
			else if(r == 2 && td >0) {
				//assign detective
				new Detective();
				td--;
			}
			else if(r == 3 && th > 0) {
				//assign healers
				new Healer();
				th--;
			}
			else if(r == 4 && tc > 0) {
				//assign commoners
				new Commoner();
				tc--;
			}
			if(th == 0 && tm ==0 && td == 0 && tc ==0) {
				break;
			}
		}
		//Player.printPlayerList();
		Game newGame = new Game();
		newGame.GameStart();
		in.close();
	}

}
