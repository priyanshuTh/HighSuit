import java.io.*;
import java.util.*;

public class ScoreTable {
    private static final String FILENAME = "highscores.txt";
    private ArrayList<ScoreEntry> scores;
    
    public ScoreTable() {
        scores = new ArrayList<>();
        loadScores();
    }
    
    public void addScore(String playerName, int totalScore, int rounds) {
        int averageScore = totalScore / rounds;
        scores.add(new ScoreEntry(playerName, averageScore, totalScore, rounds));
        Collections.sort(scores);
        
        // Keep only top 5
        if (scores.size() > 5) {
            scores = new ArrayList<>(scores.subList(0, 5));
        }
        
        saveScores();
    }
    
    public void display() {
        System.out.println("\n" + "=".repeat(50));
        System.out.println("HIGH SCORE TABLE");
        System.out.println("=".repeat(50));
        
        if (scores.isEmpty()) {
            System.out.println("No high scores yet!");
        } else {
            System.out.printf("%-5s %-20s %-15s %-10s%n", "Rank", "Player", "Avg Score", "Total");
            System.out.println("-".repeat(50));
            
            for (int i = 0; i < scores.size(); i++) {
                ScoreEntry entry = scores.get(i);
                System.out.printf("%-5d %-20s %-15d %-10d%n", 
                    (i + 1), entry.playerName, entry.averageScore, entry.totalScore);
            }
        }
        System.out.println("=".repeat(50));
    }
    
    private void loadScores() {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILENAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 4) {
                    scores.add(new ScoreEntry(
                        parts[0], 
                        Integer.parseInt(parts[1]),
                        Integer.parseInt(parts[2]),
                        Integer.parseInt(parts[3])
                    ));
                }
            }
            Collections.sort(scores);
        } catch (IOException e) {
            // File doesn't exist yet, that's okay
        }
    }
    
    private void saveScores() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(FILENAME))) {
            for (ScoreEntry entry : scores) {
                writer.println(entry.playerName + "," + entry.averageScore + 
                             "," + entry.totalScore + "," + entry.rounds);
            }
        } catch (IOException e) {
            System.out.println("Error saving high scores.");
        }
    }
    
    private class ScoreEntry implements Comparable<ScoreEntry> {
        String playerName;
        int averageScore;
        int totalScore;
        int rounds;
        
        ScoreEntry(String playerName, int averageScore, int totalScore, int rounds) {
            this.playerName = playerName;
            this.averageScore = averageScore;
            this.totalScore = totalScore;
            this.rounds = rounds;
        }
        
        @Override
        public int compareTo(ScoreEntry other) {
            // Sort in descending order
            return Integer.compare(other.averageScore, this.averageScore);
        }
    }
}