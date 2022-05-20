package card.games.blackjack;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Blackjack implements Serializable {
	private static final long serialVersionUID = 1L;
	private static Player player;
	private static Player dealer;
	private static List<Player> opponents;
	private static Deck deck = new Deck();
	private static int roundCount;
	private static boolean gameOver = false;
	private static Scanner scanner = new Scanner(System.in);
	
	public static void main(String[] args) {
		System.out.println("Welcome to Blackjack");
		System.out.println("Input your name: ");
		String playerName = scanner.nextLine();
		
		while (true) {
			player = new Player(playerName);
			
			mainMenu();
		}
	}
	
	private static void mainMenu() {
		System.out.println();
		System.out.println(player.getName()+ ", what would you like to do?");
		System.out.println("1: Number of opponents");
		System.out.println("2: Play Blackjack");
		System.out.println("3: New Deck");
		System.out.println("4: Shuffle Cards");
		System.out.println("5: Display Card in Deck");
		System.out.println("6: Reset Game");
		System.out.println("7: Exit");
		String in = scanner.nextLine();
		if (in.equals("7")){
			return;
		} else {
			try {
				menu(in);
			} catch (IndexOutOfBoundsException e) {
				System.out.println("Exception thrown  :" + e);
				System.out.println("No more cards! Go back to menu for new deck.");
			}
		}
	}
	
	private static void menu(String in) {
		if (in.equals("1")) {
			System.out.println("Number of opponents:");
			int numOfOpponents = Integer.valueOf(scanner.nextLine());
			setupOpponents(numOfOpponents);
			System.out.println("Opponents have been setup.");
		} else if (in.equals("2")) {
			playBlackjack();
		} else if (in.equals("3")) {
			deck = new Deck();
			System.out.println("New deck made.");
		} else if (in.equals("4")) {
			deck.shuffle();
			System.out.println("Deck has been shuffled.");
		} else if (in.equals("5")) {
			System.out.println("Deck cards");
			deck.displayDeck();
		} else if (in.equals("6")) {
			deck = new Deck();
			deck.shuffle();
			opponents = null;
			mainMenu();
		} else {
			System.out.println("Invalid option!");
		}
	}
	
	private static void playBlackjack() {
		dealer = new Player("Dealer");
		
		roundCount = 1;
		
		dealAllIn();
		
		while(!gameOver) {
			oneMoreRound();
		}
	}
	
	private static void dealAllIn() {
		deck.dealPlayerIn(dealer, false);
		deck.dealPlayerIn(player, false);
		dealOpponentsIn(opponents, false);
		deck.dealPlayerIn(dealer, false);
		deck.dealPlayerIn(player, false);
		dealOpponentsIn(opponents, false);
		
		revealAllPlayerHands();
	}
	
	private static void revealAllPlayerHands() {
		System.out.println();
		System.out.println("Round " + roundCount);
		System.out.println(dealer.displayHand(false));
		System.out.println();
		System.out.println(player.displayHand(false));
		evaluateHandValueAgainstDealer(player.getHandValue());
		for(Player opponent: opponents) {
			System.out.println(opponent.displayHand(false));
			evaluateHandValueAgainstDealer(opponent.getHandValue());
		}
	}
	
	private static void evaluateHandValueAgainstDealer(int handValue) {
		if (handValue >= dealer.getHandValue()) {
			if(handValue > 21) {
				System.out.println("Lose.");
			} else {
				System.out.println("Beats dealer.");
			}
		} else {
			System.out.println("Lose.");
		}
		System.out.println();		
	}
	
	private static void oneMoreRound() {
		System.out.println();
		System.out.println("Do you want to play another round? (Y/N)");
		
		String decision = scanner.nextLine();
		
		if (decision.toUpperCase().equals("Y")) {
			roundCount+=1;
			System.out.println("incomplete");
			//TODO
			gameOver = true;
		}
	}
	
	private static void setupOpponents(int numOfOpponemts) {
		opponents = new ArrayList<Player>();
		
		for (int i = 0; i < numOfOpponemts; i++) {
			Player player = new Player(randomName());
			opponents.add(player);
		}
	}
	
	private static void dealOpponentsIn(List<Player> opponents, boolean show) {
		for(Player opponent: opponents) {
			deck.dealPlayerIn(opponent, show);
		}
	}
	
	static String randomName() {
		String[] names = {"Thabo", "Frank", "Seroke", "Erik", "Leto", "Stilgar", "Mangole", "Norah", "Oratile", "Omphile", "Gaaratwe", "Thebeetsile", "Midah", "Hildah"};
		return names[(int) (Math.random() * names.length)];
	}
}