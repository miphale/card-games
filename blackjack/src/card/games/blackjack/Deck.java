package card.games.blackjack;

import java.io.Serializable;
import java.util.List;

public class Deck implements Serializable {
	private static final long serialVersionUID = 1L;
	private List<Card> cards;
	
	public Deck() {
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
}