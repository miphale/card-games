package card.games.blackjack;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import card.games.blackjack.utils.Face;

public class Player implements Serializable {
	private static final long serialVersionUID = 1L;
	private String name;
	private List<Card> hand;
	public int handValue;
	
	public Player(String name) {
		this.name = name;
		this.hand = new ArrayList<Card>();
		this.handValue = 0;
	}
	
	public void addCard(Card card, boolean show) {
		hand.add(card);
		getHandValue(card);
		if (show) {
			System.out.println(name + " has added card: " + card.display());			
		}
	}

	public String getName() {
		return name;
	}

	public List<Card> getHand() {
		return hand;
	}

	public int getHandValue(Card card) {
		//An Ace can be equal to 1 or 11 based on the other cards in hand
		if(card.getFace().equals(Face.Ace) && handValue <= 10) {
			handValue += 11;
		} else {
			handValue += card.getFace().getValue();
		}
		return handValue;
	}
	
	public String displayHand(boolean hideCard) {
        StringBuilder sb = new StringBuilder();
        sb.append(name + "\'s current hand:");
        sb.append('\n');
        for (int i = 0; i < hand.size(); i++) {
            if (i == 0 && hideCard) {
                sb.append("[Hidden card]");
                sb.append('\n');
            } else {
                sb.append(hand.get(i).display());
                sb.append('\n');
            }
        }
        sb.append("Hand value: " + handValue);
        return sb.toString();
    }
}