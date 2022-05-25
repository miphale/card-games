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
	private static boolean opponentsExist = false;
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
		if (in.equals("7")) {
			System.out.println("Thank you for playing. Goodbye!");
			System.exit(0);
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
			gameOver = false;
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
			opponentsExist = false;
			gameOver = false;
			mainMenu();
		} else {
			System.out.println("Invalid option!");
		}
	}
	
	private static void setupOpponents(int numOfOpponemts) {
		opponentsExist = true;
		
		opponents = new ArrayList<Player>();
		
		for (int i = 0; i < numOfOpponemts; i++) {
			Player player = new Player(randomName());
			opponents.add(player);
		}
	}
	
	static String randomName() {
		String[] names = {"Thabo", "Frank", "Seroke", "Erik", "Leto", "Stilgar", "Mangole", "Norah", "Oratile", "Omphile", "Gaaratwe", "Thebeetsile", "Midah", "Hildah"};
		return names[(int) (Math.random() * names.length)];
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
		deck.dealPlayerIn(dealer, true);
		deck.dealPlayerIn(player, false);
		if(opponentsExist) {
			dealOpponentsIn(opponents, false);
		}
		deck.dealPlayerIn(dealer, false);
		deck.dealPlayerIn(player, false);
		if(opponentsExist) {
			dealOpponentsIn(opponents, false);
		}
		
		revealAllPlayerHands();
	}
	
	private static void dealOpponentsIn(List<Player> opponents, boolean show) {
		for(Player opponent: opponents) {
			deck.dealPlayerIn(opponent, show);
		}
	}
	
	private static void revealAllPlayerHands() {
		System.out.println();
		System.out.println("Round " + roundCount);
		System.out.println(dealer.displayHand(false));
		System.out.println();
		System.out.println(player.displayHand(false));
		evaluatePlayers();
	}
	
	private static void evaluatePlayers() {
		if(dealer.handValue > 21) {
			System.out.println();
			System.out.println("Dealer's hand is over 21. Dealer loses.");
			playersWinOrDoThey();
		} else {
			evaluatelayerAgainstDealer(player);
			
			if(opponentsExist) {
				for(Player opponent: opponents) {
					System.out.println(opponent.displayHand(false));
					evaluatelayerAgainstDealer(opponent);
				}
			}
		}
	}
	
	private static void evaluatelayerAgainstDealer(Player playerToEvaluate) {
		if(beatsDealer(playerToEvaluate)) {
			System.out.println("Beats Dealer.");
		} else {
			System.out.println("Loses.");
		}
		System.out.println();
	}
	
	private static boolean beatsDealer(Player playerToEvaluate) {
		if (playerToEvaluate.getHand().size() == 5 && playerToEvaluate.handValue < 21) {
			return true;
		}
		
		if (playerToEvaluate.handValue >= dealer.handValue && playerToEvaluate.handValue < 21) {
			return true;
		}
		
		return false;
	}
	
	private static void oneMoreRound() {
		System.out.println();
		System.out.println("Do you want to play another round? (Y/N)");
		
		String decision = scanner.nextLine();
		
		if (decision.toUpperCase().equals("Y")) {
			roundCount+=1;

			deck.dealPlayerIn(player, false);
			if(opponentsExist) {
				dealOpponentsIn(opponents, false);
			}
			
			revealAllPlayerHands();
		} else if (decision.toUpperCase().equals("N")) {
			while(!gameOver) {
				dealerDraws();
			}
		} else {
			System.out.println("Select either Y or N.");
		}
	}
	
	private static void dealerDraws() {
		if(dealer.handValue > 17) {
			System.out.println("Dealer's hand is over 17. Dealer can't draw anymore.");
			playersWinOrDoThey();
		} else {
			deck.dealPlayerIn(dealer, true);			
			revealAllPlayerHands();
		}
		
	}
	
	private static void playersWinOrDoThey() {
		if(player.handValue < 21) {
			System.out.println(player.getName() + " wins.");
		} else {
			System.out.println(player.getName() + " loses.");
		}
		
		if(opponentsExist) {
			for(Player opponent: opponents) {
				if(opponent.handValue < 21) {
					System.out.println(opponent.getName() + " wins.");
				} else {
					System.out.println(opponent.getName() + " loses.");
				}
			}
		}
		gameOver = true;
	}
}