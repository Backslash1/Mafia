package Assignment3;

public class Random {
	public static int getRandom(int min , int max) {
		
		int randomNumber = (int)(Math.random()*(max-min + 1)) + min;
		return randomNumber;
		
	}
}
