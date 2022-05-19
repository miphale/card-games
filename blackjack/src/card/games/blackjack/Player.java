package card.games.blackjack;

import java.io.Serializable;
import java.util.List;

public class Player implements Serializable {
	private static final long serialVersionUID = 1L;
	private String name;
	private List<Card> hand;
	private int handValue;
	
	public Player() {		
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Card> getHand() {
		return hand;
	}

	public void setHand(List<Card> hand) {
		this.hand = hand;
	}

	public int getHandValue() {
		return handValue;
	}

	public void setHandValue(int handValue) {
		this.handValue = handValue;
	}
}