import java.util.ArrayList;
import java.util.Collections;

public class Deck {
    private ArrayList<Card> cards;
    
    public Deck() {
        cards = new ArrayList<>();
        // Create a full deck of 52 cards
        for (int suit = 0; suit < 4; suit++) {
            for (int rank = 0; rank < 13; rank++) {
                cards.add(new Card(rank, suit));
            }
        }
    }
    
    public void shuffle() {
        Collections.shuffle(cards);
    }
    
    public Card dealCard() {
        if (cards.isEmpty()) {
            return null;
        }
        return cards.remove(0);
    }
    
    public int cardsRemaining() {
        return cards.size();
    }
    
    public boolean isEmpty() {
        return cards.isEmpty();
    }
}