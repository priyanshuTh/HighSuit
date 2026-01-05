import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;

/**
 * Integration test class for HighSuit game
 * Tests complete game scenarios and component interactions
 */
public class GameTest {
    
    private Deck deck;
    private Player player1;
    private Player player2;
    
    @Before
    public void setUp() {
        deck = new Deck();
        deck.shuffle();
        player1 = new Player("Alice");
        player2 = new Player("Bob");
    }
    
@Test
public void testCardSwapping() {

    for (int i = 0; i < 5; i++) {
        player1.addCard(deck.dealCard());
    }

    int deckBefore = deck.cardsRemaining();

    ArrayList<Card> originalHand = new ArrayList<>(player1.getHand());

    player1.getHand().remove(0);
    player1.getHand().remove(0);
    player1.addCard(deck.dealCard());
    player1.addCard(deck.dealCard());

    assertEquals(5, player1.getHand().size());
    assertEquals(deckBefore - 2, deck.cardsRemaining());

    // 🔥 Missing but essential checks
    assertNotEquals(originalHand.get(0), player1.getHand().get(0));
    assertNotEquals(originalHand.get(1), player1.getHand().get(1));
}

    
    @Test
    public void testMultipleRounds() {
        int totalRounds = 3;
        
        for (int round = 0; round < totalRounds; round++) {
            // Create new deck for each round
            Deck roundDeck = new Deck();
            roundDeck.shuffle();
            
            // Clear hands
            player1.clearHand();
            player2.clearHand();
            
            // Deal cards
            for (int i = 0; i < 5; i++) {
                player1.addCard(roundDeck.dealCard());
                player2.addCard(roundDeck.dealCard());
            }
            
            // Calculate and add scores
            int bonusSuit1 = player1.getBestSuit();
            int bonusSuit2 = player2.getBestSuit();
            
            int score1 = player1.calculateRoundScore(bonusSuit1);
            int score2 = player2.calculateRoundScore(bonusSuit2);
            
            player1.addToTotalScore(score1);
            player2.addToTotalScore(score2);
        }
        
        // Verify total scores accumulated
        assertTrue(player1.getTotalScore() > 0);
        assertTrue(player2.getTotalScore() > 0);
    }
    
    @Test
    public void testScoreWithBonusSuit() {
        // Create specific hand for testing
        player1.clearHand();
        player1.addCard(new Card(10, 0)); // Queen of Clubs = 10
        player1.addCard(new Card(11, 0)); // King of Clubs = 10
        player1.addCard(new Card(12, 0)); // Ace of Clubs = 11
        player1.addCard(new Card(0, 1));  // 2 of Diamonds = 2
        player1.addCard(new Card(1, 2));  // 3 of Hearts = 3
        
        // Best suit is Clubs (31 points)
        assertEquals(0, player1.getBestSuit());
        
        // Score with bonus (Clubs selected as bonus)
        int scoreWithBonus = player1.calculateRoundScore(0);
        assertEquals(36, scoreWithBonus);
        
        // Score without bonus (different suit selected)
        int scoreNoBonus = player1.calculateRoundScore(1);
        assertEquals(31, scoreNoBonus);
    }
    
    @Test
    public void testDeckExhaustion() {
        // Deal cards until deck is nearly empty
        while (deck.cardsRemaining() > 10) {
            deck.dealCard();
        }
        
        assertTrue(deck.cardsRemaining() <= 10);
        assertFalse(deck.isEmpty());
        
        // Deal remaining cards
        while (!deck.isEmpty()) {
            Card card = deck.dealCard();
            assertNotNull(card);
        }
        
        assertTrue(deck.isEmpty());
        assertNull(deck.dealCard());
    }
    
    @Test
    public void testComputerPlayerBehavior() {
        Player computer = new Player("Computer");
        assertTrue(computer.isComputer());
        
        // Deal cards
        for (int i = 0; i < 5; i++) {
            computer.addCard(deck.dealCard());
        }
        
        // Computer should be able to determine best suit
        int bestSuit = computer.getBestSuit();
        assertTrue(bestSuit >= 0 && bestSuit <= 3);
    }
    
    @Test
    public void testGameWithOnlyNumberCards() {
        // Create hand with only number cards
        player1.clearHand();
        player1.addCard(new Card(0, 0)); // 2 of Clubs
        player1.addCard(new Card(1, 0)); // 3 of Clubs
        player1.addCard(new Card(2, 0)); // 4 of Clubs
        player1.addCard(new Card(3, 0)); // 5 of Clubs
        player1.addCard(new Card(4, 0)); // 6 of Clubs
        
        int score = player1.calculateRoundScore(0);
        assertEquals(25, score);
    }
    
    @Test
    public void testGameWithAllPictureCards() {
        // Create hand with all picture cards
        player1.clearHand();
        player1.addCard(new Card(9, 0));  // Jack = 10
        player1.addCard(new Card(10, 0)); // Queen = 10
        player1.addCard(new Card(11, 0)); // King = 10
        player1.addCard(new Card(12, 0)); // Ace = 11
        player1.addCard(new Card(9, 1));  // Jack = 10
        
        int[] suitScores = player1.calculateSuitScores();
        assertEquals(41, suitScores[0]);
    }
    
    @Test
    public void testTwoPlayerGame() {
        // Simulate a simple two-player game
        for (int i = 0; i < 5; i++) {
            player1.addCard(deck.dealCard());
            player2.addCard(deck.dealCard());
        }
        
        int score1 = player1.calculateRoundScore(player1.getBestSuit());
        int score2 = player2.calculateRoundScore(player2.getBestSuit());
        
        player1.addToTotalScore(score1);
        player2.addToTotalScore(score2);
        
        // Determine winner
        boolean player1Wins = player1.getTotalScore() > player2.getTotalScore();
        boolean player2Wins = player2.getTotalScore() > player1.getTotalScore();
        boolean tie = player1.getTotalScore() == player2.getTotalScore();
        
        assertTrue(player1Wins || player2Wins || tie);
    }
    
    @Test
    public void testSinglePlayerGame() {
        // Deal cards
        for (int i = 0; i < 5; i++) {
            player1.addCard(deck.dealCard());
        }
        
        // Play round
        int score = player1.calculateRoundScore(player1.getBestSuit());
        player1.addToTotalScore(score);
        
        assertTrue(player1.getTotalScore() > 0);
    }
    
    @Test
    public void testHandWithAllSameSuit() {
        // Create perfect hand - all same suit
        player1.clearHand();
        player1.addCard(new Card(12, 2)); // Ace of Hearts = 11
        player1.addCard(new Card(11, 2)); // King of Hearts = 10
        player1.addCard(new Card(10, 2)); // Queen of Hearts = 10
        player1.addCard(new Card(9, 2));  // Jack of Hearts = 10
        player1.addCard(new Card(8, 2));  // 10 of Hearts = 10
        
        assertEquals(2, player1.getBestSuit());
        assertEquals(51, player1.getMaxSuitScore());
        
        int scoreWithBonus = player1.calculateRoundScore(2);
        assertEquals(56, scoreWithBonus);
    }
    
    @Test
    public void testWorstCaseHand() {
        // Create worst possible hand - all 2s of different suits
        player1.clearHand();
        player1.addCard(new Card(0, 0)); // 2 of Clubs
        player1.addCard(new Card(0, 1)); // 2 of Diamonds
        player1.addCard(new Card(0, 2)); // 2 of Hearts
        player1.addCard(new Card(0, 3)); // 2 of Spades
        player1.addCard(new Card(1, 0)); // 3 of Clubs
        
        int maxScore = player1.getMaxSuitScore();
        assertEquals(5, maxScore);
    }
    
    @Test
    public void testReplayDataCollection() {
        GameReplay replay = new GameReplay();
        GameReplay.RoundReplay round = new GameReplay.RoundReplay();
        
        // Deal and store initial hand
        for (int i = 0; i < 5; i++) {
            player1.addCard(deck.dealCard());
        }
        
        ArrayList<Card> initialHand = new ArrayList<>(player1.getHand());
        int bonusSuit = player1.getBestSuit();
        ArrayList<Integer> swapped = new ArrayList<>();
        int score = player1.calculateRoundScore(bonusSuit);
        
        GameReplay.PlayerRoundData data = new GameReplay.PlayerRoundData(
            player1.getName(), initialHand, bonusSuit, swapped,
            new ArrayList<>(player1.getHand()), score
        );
        
        round.addPlayerData(data);
        replay.addRound(round);
        
        assertNotNull(replay);
    }
}