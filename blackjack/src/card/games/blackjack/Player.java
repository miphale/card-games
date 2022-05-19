package card.games.blackjack;

import java.io.Serializable;
import java.util.List;

public class Player implements Serializable {
	private static final long serialVersionUID = 1L;
	private String name;
	private List<Card> hand;
	private int handValue;
}