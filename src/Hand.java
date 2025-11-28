import java.util.Arrays;

public class Hand {
    private Card[] cards;
    
    
    public Hand (){
        cards = new Card[5];
        
        for (int i = 0; i < 5; i++){
            cards[i] = new Card();
        }
        Arrays.sort(cards);
    }
    
    public boolean inHand(Card searchCard) {
        
        boolean found = false;
        int index = 0;
        while (!found && index < cards.length){
            if (cards[index].equals(searchCard)){
                found = true; // we found  the card 
            } else {
                index ++; // move to next position
            } 
        }
        return false;
    }
    
    public String toString() {
        String result = "";
        for (int i = 0; i < 5; i++) {
            result += cards[i].toString() + " ";
        }
        return result;
    }
    
    public static void main (String[] args) {
        Hand hand = new Hand();
        
        System.out.printf("Hand of 5 cards (sorted by rank):  %s%n%n", hand);
        
        for (int i=1; i<=10; i++) {
            Card randomCard = new Card();
            String message;
            if (hand.inHand(randomCard)) {
                message = " - Positive, the card is contained in the hand";}
            else { message = " - Negative, the card is not contained in the hand";}
            
            System.out.printf("Random card %d: %s %s%n", i, randomCard, message);
        }
    }
    
}
