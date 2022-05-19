package card.games.blackjack;

import java.io.Serializable;

import card.games.blackjack.utils.Face;
import card.games.blackjack.utils.Suit;

public class Card implements Serializable {
	private static final long serialVersionUID = 1L;
	private final Face face;
	private final Suit suit;
	
	public Card(Face face, Suit suit) {
		this.face = face;
		this.suit = suit;
	}
}