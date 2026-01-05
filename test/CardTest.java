import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

/**
 * JUnit test class for Card
 * Tests card creation, comparison, scoring, and string representations
 */
public class CardTest {
    
    private Card card1;
    private Card card2;
    private Card card3;
    
    @Before
    public void setUp() {
        // Create specific cards for testing
        card1 = new Card(0, 0);  // 2 of Clubs
        card2 = new Card(12, 3); // Ace of Spades
        card3 = new Card(0, 0);  // 2 of Clubs (duplicate)
    }
    
    @Test
    public void testCardCreation() {
        assertNotNull(card1);
        assertNotNull(card2);
    }
    
    @Test
    public void testGetRank() {
        assertEquals("2", card1.getRank());
        assertEquals("Ace", card2.getRank());
    }
    
    @Test
    public void testGetSuit() {
        assertEquals("Clubs", card1.getSuit());
        assertEquals("Spades", card2.getSuit());
    }
    
    @Test
    public void testGetSuitValue() {
        assertEquals(0, card1.getSuitValue());
        assertEquals(3, card2.getSuitValue());
    }
    
    @Test
    public void testGetRankValue() {
        assertEquals(0, card1.getRankValue());
        assertEquals(12, card2.getRankValue());
    }
    
    @Test
    public void testGetScore() {
        assertEquals(2, card1.getScore());
        assertEquals(11, card2.getScore());
        
        Card jack = new Card(9, 0);   // Jack
        Card queen = new Card(10, 0); // Queen
        Card king = new Card(11, 0);  // King
        
        assertEquals(10, jack.getScore());
        assertEquals(10, queen.getScore());
        assertEquals(10, king.getScore());
    }
    
    @Test
    public void testToString() {
        assertEquals("2 of Clubs", card1.toString());
        assertEquals("Ace of Spades", card2.toString());
    }
    
    @Test
    public void testToSymbol() {
        assertEquals("2\u2663", card1.toSymbol());
        assertEquals("A\u2660", card2.toSymbol());
        
        Card ten = new Card(8, 1); // 10 of Diamonds
        assertEquals("10\u2666", ten.toSymbol());
    }
    
    @Test
    public void testIsBiggerThan() {
        assertTrue(card2.isBiggerThan(card1));
        assertFalse(card1.isBiggerThan(card2));
        assertFalse(card1.isBiggerThan(card3));
    }
    
    @Test
    public void testCompareTo() {
        assertTrue(card2.compareTo(card1) > 0);
        assertTrue(card1.compareTo(card2) < 0);
        assertEquals(0, card1.compareTo(card3));
    }
    
    @Test
    public void testEquals() {
        assertTrue(card1.equals(card3));
        assertFalse(card1.equals(card2));
        assertFalse(card1.equals(null));
        assertFalse(card1.equals("Not a card"));
    }
    
    @Test
    public void testDefaultConstructor() {
        Card randomCard = new Card();
        assertNotNull(randomCard);
        assertNotNull(randomCard.getRank());
        assertNotNull(randomCard.getSuit());
    }
    
    @Test
    public void testAllRanks() {
        String[] expectedRanks = {"2", "3", "4", "5", "6", "7", "8", "9", "10",
                                  "Jack", "Queen", "King", "Ace"};
        
        for (int i = 0; i < expectedRanks.length; i++) {
            Card card = new Card(i, 0);
            assertEquals(expectedRanks[i], card.getRank());
        }
    }
    
    @Test
    public void testAllSuits() {
        String[] expectedSuits = {"Clubs", "Diamonds", "Hearts", "Spades"};
        
        for (int i = 0; i < expectedSuits.length; i++) {
            Card card = new Card(0, i);
            assertEquals(expectedSuits[i], card.getSuit());
        }
    }
    
    @Test
    public void testPictureCardScores() {
        Card jack = new Card(9, 0);
        Card queen = new Card(10, 0);
        Card king = new Card(11, 0);
        
        assertEquals(10, jack.getScore());
        assertEquals(10, queen.getScore());
        assertEquals(10, king.getScore());
    }
    
    @Test
    public void testNumberCardScores() {
        for (int rank = 0; rank <= 8; rank++) {
            Card card = new Card(rank, 0);
            assertEquals(rank + 2, card.getScore());
        }
    }
}