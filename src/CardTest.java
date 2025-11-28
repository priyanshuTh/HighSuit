// Class definition: used to test comparing two randomly generated cards
public class CardTest {

    // Main method: program entry point
    public static void main(String[] args) {

        // Create two random Card objects using the default constructor
        Card card1 = new Card();
        Card card2 = new Card();

        // Display both cards using toString()
        System.out.println("Card1 is the :"+ card1);
        System.out.println("Card1 is the :"+ card2);

        // Compare and print cards in descending order by rank
        if (card1.compareTo(card2) > 0){System.out.println("Card 1 is bigger.");}
        else if (card1.compareTo(card2) < 0){System.out.println("Card 2 is bigger.");}
        else {System.out.println("Card 1 and Card 2 are equal.");}
    }
}

