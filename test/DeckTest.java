import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.HashSet;

/**
 * JUnit test class for Deck
 * Tests deck creation, shuffling, dealing, and card counting
 */
public class DeckTest {
    
    private Deck deck;
    
    @Before
    public void setUp() {
        deck = new Deck();
    }
    
    @Test
    public void testDeckCreation() {
        assertNotNull(deck);
    }
    
    @Test
    public void testDeckSize() {
        assertEquals(52, deck.cardsRemaining());
    }
    
    @Test
    public void testDeckNotEmpty() {
        assertFalse(deck.isEmpty());
    }
    
    @Test
    public void testDealCard() {
        Card card = deck.dealCard();
        assertNotNull(card);
        assertEquals(51, deck.cardsRemaining());
    }
    
    @Test
    public void testDealMultipleCards() {
        for (int i = 0; i < 10; i++) {
            Card card = deck.dealCard();
            assertNotNull(card);
        }
        assertEquals(42, deck.cardsRemaining());
    }
    
    @Test
    public void testDealAllCards() {
        for (int i = 0; i < 52; i++) {
            Card card = deck.dealCard();
            assertNotNull(card);
        }
        assertEquals(0, deck.cardsRemaining());
        assertTrue(deck.isEmpty());
    }
    
    @Test
    public void testDealFromEmptyDeck() {
        // Deal all cards
        for (int i = 0; i < 52; i++) {
            deck.dealCard();
        }
        
        // Try to deal from empty deck
        Card card = deck.dealCard();
        assertNull(card);
    }
    
    @Test
    public void testShuffleDoesNotLoseCards() {
        deck.shuffle();
        assertEquals(52, deck.cardsRemaining());
    }
    
    @Test
    public void testDeckContainsUniqueCards() {
        HashSet<String> uniqueCards = new HashSet<>();
        
        for (int i = 0; i < 52; i++) {
            Card card = deck.dealCard();
            String cardString = card.toString();
            assertFalse(uniqueCards.contains(cardString));
            uniqueCards.add(cardString);
        }
        
        assertEquals(52, uniqueCards.size());
    }
    
    @Test
    public void testDeckContainsAllRanks() {
        HashSet<String> ranks = new HashSet<>();
        
        for (int i = 0; i < 52; i++) {
            Card card = deck.dealCard();
            ranks.add(card.getRank());
        }
        
        assertEquals(13, ranks.size());
    }
    
    @Test
    public void testDeckContainsAllSuits() {
        HashSet<String> suits = new HashSet<>();
        
        for (int i = 0; i < 52; i++) {
            Card card = deck.dealCard();
            suits.add(card.getSuit());
        }
        
        assertEquals(4, suits.size());
    }
    
    @Test
    public void testShuffleChangesOrder() {
        // Deal first 5 cards from unshuffled deck
        Deck deck1 = new Deck();
        Card[] firstDeal = new Card[5];
        for (int i = 0; i < 5; i++) {
            firstDeal[i] = deck1.dealCard();
        }
        
        // Deal first 5 cards from shuffled deck
        Deck deck2 = new Deck();
        deck2.shuffle();
        Card[] secondDeal = new Card[5];
        for (int i = 0; i < 5; i++) {
            secondDeal[i] = deck2.dealCard();
        }
        
        // Check if at least one card is different
        boolean different = false;
        for (int i = 0; i < 5; i++) {
            if (!firstDeal[i].equals(secondDeal[i])) {
                different = true;
                break;
            }
        }
        
        // Note: This test has a very small chance of failing randomly
        // (if shuffle happens to produce same first 5 cards)
        assertTrue(different);
    }
    
    @Test
    public void testCardsRemainingDecreases() {
        assertEquals(52, deck.cardsRemaining());
        
        deck.dealCard();
        assertEquals(51, deck.cardsRemaining());
        
        deck.dealCard();
        assertEquals(50, deck.cardsRemaining());
        
        deck.dealCard();
        assertEquals(49, deck.cardsRemaining());
    }
    
    @Test
    public void testIsEmptyAfterDealingAll() {
        assertFalse(deck.isEmpty());
        
        for (int i = 0; i < 52; i++) {
            deck.dealCard();
        }
        
        assertTrue(deck.isEmpty());
    }
    
    @Test
    public void testMultipleShuffle() {
        deck.shuffle();
        assertEquals(52, deck.cardsRemaining());
        
        deck.shuffle();
        assertEquals(52, deck.cardsRemaining());
    }
}