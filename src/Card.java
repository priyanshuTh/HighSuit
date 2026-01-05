import java.util.Random;

public class Card implements Comparable<Card> {
    private int rank;
    private int suit;

    private static final String[] ranks = {"2", "3", "4", "5", "6", "7", "8", "9", "10",
        "Jack", "Queen", "King", "Ace"};
    private static final String[] suits = {"Clubs", "Diamonds", "Hearts", "Spades"};
    
    // Card values for scoring
    private static final int[] values = {2, 3, 4, 5, 6, 7, 8, 9, 10, 10, 10, 10, 11};

    public Card() {
        Random random = new Random();
        this.rank = random.nextInt(ranks.length);
        this.suit = random.nextInt(suits.length);
    }

    public Card(int rank, int suit) {
        this.rank = rank;
        this.suit = suit;
    }

    public String getRank() {
        return ranks[this.rank];
    }

    public String getSuit() {
        return suits[this.suit];
    }
    
    public int getSuitValue() {
        return this.suit;
    }

    public boolean isBiggerThan(Card anotherCard) {
        return this.rank > anotherCard.rank;
    }

    public String toString() {
        return getRank() + " of " + getSuit();
    }
    
    // Get symbolic representation of card
    public String toSymbol() {
        String[] suitSymbols = {"\u2663", "\u2666", "\u2665", "\u2660"};
        String rankStr;
        
        if (rank < 8) {
            rankStr = ranks[rank];
        } else if (rank == 8) {
            rankStr = "10";
        } else if (rank == 9) {
            rankStr = "J";
        } else if (rank == 10) {
            rankStr = "Q";
        } else if (rank == 11) {
            rankStr = "K";
        } else {
            rankStr = "A";
        }
        
        return rankStr + suitSymbols[suit];
    }
    
    public int getRankValue() {
        return this.rank;
    }
    
    // Get scoring value for HighSuit game
    public int getScore() {
        return values[this.rank];
    }
    
    public int compareTo(Card otherCard) {
        if (this.getRankValue() > otherCard.getRankValue()) {
            return 1;
        } else if (this.getRankValue() < otherCard.getRankValue()) {
            return -1;
        } else {
            return 0;
        }
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Card card = (Card) obj;
        return rank == card.rank && suit == card.suit;
    }
    
    @Override
    public int hashCode() {
        return 31 * rank + suit;
    }
}