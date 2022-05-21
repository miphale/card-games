package card.games.blackjack;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import card.games.blackjack.utils.Face;
import card.games.blackjack.utils.Suit;

public class Deck implements Serializable {
	private static final long serialVersionUID = 1L;
	private List<Card> cards;
	
	public Deck() {
		cards = new ArrayList<Card>();
		for(Suit suit: Suit.values()) {
			for (Face face: Face.values()) {
				cards.add(new Card(face, suit));
			}
		}
	}
	
	public Card draw() {
		return cards.remove(0);
	}
	
	public List<Card> getCards() {
		return cards;
	}
	
	public void setCards(List<Card> cards) {
		this.cards = cards;
	}
	
	public void shuffle() {
		List<Card> tempCards = new ArrayList<>();
		Random r = new Random();
		int count = cards.size();
		int track = 0;
		for (int i = 0; i < count; i++) {
			track = count - i;
			int idx = r.nextInt(track);
			Card card = cards.get(idx);
			cards.remove(idx);
			tempCards.add(i, card);
		}
		cards = tempCards;
	}
	
	public void dealPlayerIn(Player player, boolean show) {
		Card dealtCard = cards.get(0);
		draw();
		player.addCard(dealtCard, show);
	}
	
	public void displayDeck() {
		for (Card card: cards) {
			System.out.println(card.display());
		}
	}
}