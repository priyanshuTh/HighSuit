import java.util.Random; // Import Random Class

// Class definition: Represents a single playing card with rank and suit
public class Card implements Comparable<Card>{

    // Instance variables: store the numeric rank and suit of the card
    private int rank;
    private int suit;

    // Static arrays: map integer values to readable rank and suit names
    private static String[] ranks = {"2", "3", "4", "5", "6", "7", "8", "9", "10",
        "Jack", "Queen", "King", "Ace"};
    private static String[] suits = {
        "Clubs", "Diamonds", "Hearts", "Spades"};

    // Default constructor: creates a card with random rank and suit
    public Card() {
        Random random = new Random();
        this.rank = random.nextInt(Card.ranks.length);
        this.suit = random.nextInt(Card.suits.length);
    }

    // Overloaded constructor: creates a card with specific rank and suit
    public Card(int rank, int suit) {
        this.rank = rank;
        this.suit = suit;
    }

    // Getter method: returns the rank as a string
    public String getRank() {
        return Card.ranks[this.rank];
    }

    // Getter method: returns the suit as a string
    public String getSuit() {
        return Card.suits[this.suit];
    }

    // Normal method: compares this card's rank with another card's rank
    public boolean isBiggerThan(Card anotherCard) {
        return this.rank > anotherCard.rank;
    }

    // toString method: returns a readable string like "King of Hearts"
    public String toString() {
        return getRank() + " of " + getSuit();
    }
    
    // Getter method: returns rank as an integer (for internal comparison)
    public int getRankValue() {return this.rank;}
    
    // compareTo method: required by Comparable interface; compares card by rank
    public int compareTo(Card otherCard){
        if (this.getRankValue() > otherCard.getRankValue()){return 1;}
        else if (this.getRankValue() < otherCard.getRankValue()){return -1;}
        else{return 0;}
    }
}

