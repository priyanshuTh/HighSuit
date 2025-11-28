// Class definition: tests creating and ranking 5 random cards
public class RankedCard {
    // Main method: program entry point 
    public static void main(String[] args){
        // Create an array to hold 5 Card objects
        Card myCards[] = new Card[5];
        // Variables to track the highest-ranked card and its position
        int biggestValue = -1, biggestPos = -1;
        // Temporary variable used later forr swapping cards
        Card tempCard;
        // Loop: generate 5 random cards and find the one with the highest rank
        for (int i = 0; i < 5; i++){
            myCards[i] = new Card(); //Create a new random card
            
            // Upadate biggest card info if current card has higher rank
            if (myCards[i].getRankValue() > biggestValue){
                biggestValue = myCards[i].getRankValue();
                biggestPos = i;
            }
        }
        //Output: display the 5 generated cards
        System.out.println("My cards are...");
        for (Card card : myCards) {System.out.println(card);}
        // Output: show the index of the highest-ranked card
        System.out.println("\nThe biggest rank value is at position "+biggestPos);
        // Swap: move the highest-ranked card to the last position is the array
        tempCard = myCards[4];
        myCards[4] = myCards[biggestPos];
        myCards[biggestPos] = tempCard;
        //Output: display cards after swapping
        System.out.println("\nAfter swapping...");
        for (Card card : myCards) {System.out.println(card);}
    }
}