import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import org.junit.After;
import java.io.File;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

/**
 * JUnit test class for ScoreTable
 * Tests high score storage, retrieval, and persistence
 */
public class ScoreTableTest {
    
    private ScoreTable ScoreTable;
    private static final String TEST_FILENAME = "highscores.txt";
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    
    @Before
    public void setUp() {
        // Delete existing high score file before each test
        File file = new File(TEST_FILENAME);
        if (file.exists()) {
            file.delete();
        }
        
        ScoreTable = new ScoreTable();
        System.setOut(new PrintStream(outContent));
    }
    
    @After
    public void tearDown() {
        System.setOut(originalOut);
        
        // Clean up test file
        File file = new File(TEST_FILENAME);
        if (file.exists()) {
            file.delete();
        }
    }
    
    @Test
    public void testScoreTableCreation() {
        assertNotNull(ScoreTable);
    }
    
    @Test
    public void testAddSingleScore() {
        ScoreTable.addScore("Alice", 100, 2);
        
        ScoreTable.display();
        String output = outContent.toString();
        
        assertTrue(output.contains("Alice"));
        assertTrue(output.contains("50"));
    }
    
    @Test
    public void testAddMultipleScores() {
        ScoreTable.addScore("Alice", 100, 2);
        ScoreTable.addScore("Bob", 90, 3);
        ScoreTable.addScore("Charlie", 120, 2);
        
        ScoreTable.display();
        String output = outContent.toString();
        
        assertTrue(output.contains("Alice"));
        assertTrue(output.contains("Bob"));
        assertTrue(output.contains("Charlie"));
    }
    
    @Test
    public void testScoresSortedDescending() {
        ScoreTable.addScore("Low", 60, 3);    // avg: 20
        ScoreTable.addScore("High", 150, 3);  // avg: 50
        ScoreTable.addScore("Mid", 90, 3);    // avg: 30
        
        ScoreTable.display();
        String output = outContent.toString();
        
        int highPos = output.indexOf("High");
        int midPos = output.indexOf("Mid");
        int lowPos = output.indexOf("Low");
        
        assertTrue(highPos < midPos);
        assertTrue(midPos < lowPos);
    }
    
    @Test
    public void testTop5ScoresOnly() {
        // Add 7 scores
        ScoreTable.addScore("Player1", 100, 2); // avg: 50
        ScoreTable.addScore("Player2", 90, 2);  // avg: 45
        ScoreTable.addScore("Player3", 80, 2);  // avg: 40
        ScoreTable.addScore("Player4", 70, 2);  // avg: 35
        ScoreTable.addScore("Player5", 60, 2);  // avg: 30
        ScoreTable.addScore("Player6", 50, 2);  // avg: 25
        ScoreTable.addScore("Player7", 40, 2);  // avg: 20
        
        ScoreTable.display();
        String output = outContent.toString();
        
        assertTrue(output.contains("Player1"));
        assertTrue(output.contains("Player5"));
        assertFalse(output.contains("Player6"));
        assertFalse(output.contains("Player7"));
    }
    
    @Test
    public void testAverageScoreCalculation() {
        ScoreTable.addScore("Alice", 90, 3);
        
        ScoreTable.display();
        String output = outContent.toString();
        
        assertTrue(output.contains("30"));
    }
    
    @Test
    public void testEmptyScoreTable() {
        ScoreTable.display();
        String output = outContent.toString();
        
        assertTrue(output.contains("No high scores yet"));
    }
    
    @Test
    public void testScorePersistence() {
        // Add scores and save
        ScoreTable.addScore("Alice", 100, 2);
        ScoreTable.addScore("Bob", 90, 3);
        
        // Create new table (should load from file)
        ScoreTable newTable = new ScoreTable();
        
        outContent.reset();
        newTable.display();
        String output = outContent.toString();
        
        assertTrue(output.contains("Alice"));
        assertTrue(output.contains("Bob"));
    }
    
    @Test
    public void testDisplayFormat() {
        ScoreTable.addScore("TestPlayer", 100, 2);
        
        ScoreTable.display();
        String output = outContent.toString();
        
        assertTrue(output.contains("HIGH SCORE TABLE"));
        assertTrue(output.contains("Rank"));
        assertTrue(output.contains("Player"));
        assertTrue(output.contains("Avg Score"));
    }
    
    @Test
    public void testSingleRoundScore() {
        ScoreTable.addScore("Alice", 50, 1);
        
        ScoreTable.display();
        String output = outContent.toString();
        
        assertTrue(output.contains("50"));
    }
    
    @Test
    public void testMultipleRoundScore() {
        ScoreTable.addScore("Alice", 150, 3);
        
        ScoreTable.display();
        String output = outContent.toString();
        
        assertTrue(output.contains("50"));
    }
    
    @Test
    public void testRankingDisplay() {
        ScoreTable.addScore("First", 100, 1);
        ScoreTable.addScore("Second", 90, 1);
        ScoreTable.addScore("Third", 80, 1);
        
        ScoreTable.display();
        String output = outContent.toString();
        
        // Check that rankings are displayed
        assertTrue(output.contains("1"));
        assertTrue(output.contains("2"));
        assertTrue(output.contains("3"));
    }
    
    @Test
    public void testEqualScores() {
        ScoreTable.addScore("Alice", 100, 2);
        ScoreTable.addScore("Bob", 100, 2);
        
        ScoreTable.display();
        String output = outContent.toString();
        
        assertTrue(output.contains("Alice"));
        assertTrue(output.contains("Bob"));
    }
    
    @Test
    public void testLongPlayerName() {
        ScoreTable.addScore("VeryLongPlayerNameThatExceedsNormalLength", 100, 2);
        
        ScoreTable.display();
        String output = outContent.toString();
        
        assertTrue(output.contains("VeryLongPlayerNameThatExceedsNormalLength"));
    }
    
    @Test
    public void testHighScore() {
        ScoreTable.addScore("HighScorer", 999, 3);
        
        ScoreTable.display();
        String output = outContent.toString();
        
        assertTrue(output.contains("333"));
    }
    
    @Test
    public void testLowScore() {
        ScoreTable.addScore("LowScorer", 15, 3);
        
        ScoreTable.display();
        String output = outContent.toString();
        
        assertTrue(output.contains("5"));
    }
}