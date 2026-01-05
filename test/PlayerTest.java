import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;

/**
 * JUnit test class for Player
 * Tests player creation, hand management, scoring, and game logic
 */
public class PlayerTest {
    
    private Player player;
    private Player computerPlayer;
    
    @Before
    public void setUp() {
        player = new Player("Alice");
        computerPlayer = new Player("Computer");
    }
    
    @Test
    public void testPlayerCreation() {
        assertNotNull(player);
        assertEquals("Alice", player.getName());
    }
    
    @Test
    public void testComputerPlayerCreation() {
        assertTrue(computerPlayer.isComputer());
        assertFalse(player.isComputer());
    }
    
    @Test
    public void testGetName() {
        assertEquals("Alice", player.getName());
        assertEquals("Computer", computerPlayer.getName());
    }
    
    @Test
    public void testInitialHand() {
        assertNotNull(player.getHand());
        assertEquals(0, player.getHand().size());
    }
    
    @Test
    public void testAddCard() {
        Card card = new Card(0, 0);
        player.addCard(card);
        
        assertEquals(1, player.getHand().size());
        assertTrue(player.getHand().contains(card));
    }
    
    @Test
    public void testAddMultipleCards() {
        for (int i = 0; i < 5; i++) {
            player.addCard(new Card(i, 0));
        }
        
        assertEquals(5, player.getHand().size());
    }
    
    @Test
    public void testClearHand() {
        player.addCard(new Card(0, 0));
        player.addCard(new Card(1, 0));
        assertEquals(2, player.getHand().size());
        
        player.clearHand();
        assertEquals(0, player.getHand().size());
    }
    
    @Test
    public void testInitialTotalScore() {
        assertEquals(0, player.getTotalScore());
    }
    
    @Test
    public void testAddToTotalScore() {
        player.addToTotalScore(10);
        assertEquals(10, player.getTotalScore());
        
        player.addToTotalScore(15);
        assertEquals(25, player.getTotalScore());
    }
    
    @Test
    public void testCalculateSuitScores() {
        // Add cards: 2♣, 3♣, 4♦, 5♥, 6♠
        player.addCard(new Card(0, 0)); // 2 of Clubs = 2
        player.addCard(new Card(1, 0)); // 3 of Clubs = 3
        player.addCard(new Card(2, 1)); // 4 of Diamonds = 4
        player.addCard(new Card(3, 2)); // 5 of Hearts = 5
        player.addCard(new Card(4, 3)); // 6 of Spades = 6
        
        int[] suitScores = player.calculateSuitScores();
        
        assertEquals(5, suitScores[0]);
        assertEquals(4, suitScores[1]);
        assertEquals(5, suitScores[2]);
        assertEquals(6, suitScores[3]);
    }
    
    @Test
    public void testGetMaxSuitScore() {
        // Add cards with different suit distributions
        player.addCard(new Card(0, 0)); // 2 of Clubs = 2
        player.addCard(new Card(1, 0)); // 3 of Clubs = 3
        player.addCard(new Card(2, 0)); // 4 of Clubs = 4
        player.addCard(new Card(3, 1)); // 5 of Diamonds = 5
        player.addCard(new Card(4, 2)); // 6 of Hearts = 6
        
        assertEquals(9, player.getMaxSuitScore());
    }
    
    @Test
    public void testGetBestSuit() {
        // Add cards with Spades having highest score
        player.addCard(new Card(0, 0)); // 2 of Clubs = 2
        player.addCard(new Card(10, 3)); // Queen of Spades = 10
        player.addCard(new Card(11, 3)); // King of Spades = 10
        player.addCard(new Card(12, 3)); // Ace of Spades = 11
        player.addCard(new Card(3, 1)); // 5 of Diamonds = 5
        
        assertEquals(3, player.getBestSuit());
    }
    
    @Test
    public void testCalculateRoundScoreWithoutBonus() {
        // Create hand where best suit doesn't match bonus
        player.addCard(new Card(10, 0)); // Queen of Clubs = 10
        player.addCard(new Card(11, 0)); // King of Clubs = 10
        player.addCard(new Card(0, 1));  // 2 of Diamonds = 2
        player.addCard(new Card(1, 2));  // 3 of Hearts = 3
        player.addCard(new Card(2, 3));  // 4 of Spades = 4
        
        int bonusSuit = 1; // Diamonds
        int score = player.calculateRoundScore(bonusSuit);
        
        assertEquals(20, score);
    }
    
    @Test
    public void testCalculateRoundScoreWithBonus() {
        // Create hand where best suit matches bonus
        player.addCard(new Card(10, 0)); // Queen of Clubs = 10
        player.addCard(new Card(11, 0)); // King of Clubs = 10
        player.addCard(new Card(0, 1));  // 2 of Diamonds = 2
        player.addCard(new Card(1, 2));  // 3 of Hearts = 3
        player.addCard(new Card(2, 3));  // 4 of Spades = 4
        
        int bonusSuit = 0; // Clubs (best suit)
        int score = player.calculateRoundScore(bonusSuit);
        
        assertEquals(25, score);
    }
    
    @Test
    public void testScoreWithAllSameSuit() {
        // All cards same suit
        player.addCard(new Card(0, 2));  // 2 of Hearts = 2
        player.addCard(new Card(1, 2));  // 3 of Hearts = 3
        player.addCard(new Card(2, 2));  // 4 of Hearts = 4
        player.addCard(new Card(3, 2));  // 5 of Hearts = 5
        player.addCard(new Card(4, 2));  // 6 of Hearts = 6
        
        int[] suitScores = player.calculateSuitScores();
        assertEquals(20, suitScores[2]);
        assertEquals(0, suitScores[0]);
        assertEquals(0, suitScores[1]);
        assertEquals(0, suitScores[3]);
    }
    
    @Test
    public void testScoreWithPictureCards() {
        // Test picture card scoring
        player.addCard(new Card(9, 0));  // Jack of Clubs = 10
        player.addCard(new Card(10, 0)); // Queen of Clubs = 10
        player.addCard(new Card(11, 0)); // King of Clubs = 10
        player.addCard(new Card(12, 1)); // Ace of Diamonds = 11
        player.addCard(new Card(0, 2));  // 2 of Hearts = 2
        
        int[] suitScores = player.calculateSuitScores();
        assertEquals(30, suitScores[0]);
        assertEquals(11, suitScores[1]);
    }
    
    @Test
    public void testEmptyHandScoring() {
        int[] suitScores = player.calculateSuitScores();
        
        for (int score : suitScores) {
            assertEquals(0, score);
        }
    }
    
    @Test
    public void testGetBestSuitWithTie() {
        // Two suits with same score - should return first one
        player.addCard(new Card(0, 0)); // 2 of Clubs = 2
        player.addCard(new Card(1, 0)); // 3 of Clubs = 3
        player.addCard(new Card(0, 1)); // 2 of Diamonds = 2
        player.addCard(new Card(1, 1)); // 3 of Diamonds = 3
        player.addCard(new Card(0, 2)); // 2 of Hearts = 2
        
        int bestSuit = player.getBestSuit();
        assertTrue(bestSuit == 0 || bestSuit == 1);
    }
    
    @Test
    public void testMultipleRoundScores() {
        // Round 1
        player.addCard(new Card(10, 0)); // Queen = 10
        player.addCard(new Card(11, 0)); // King = 10
        int score1 = player.calculateRoundScore(0);
        player.addToTotalScore(score1);
        
        // Round 2
        player.clearHand();
        player.addCard(new Card(12, 1)); // Ace = 11
        player.addCard(new Card(11, 1)); // King = 10
        int score2 = player.calculateRoundScore(1);
        player.addToTotalScore(score2);
        
        assertEquals(25 + 26, player.getTotalScore());
    }
}