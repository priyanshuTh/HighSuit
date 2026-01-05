import java.util.*;

public class HighSuit {
    private ArrayList<Player> players;
    private Deck deck;
    private int numRounds;
    private Scanner scanner;
    private ScoreTable Scores;
    private GameReplay gameReplay;
    private String[] suitNames = {"Clubs", "Diamonds", "Hearts", "Spades"};
    
    public HighSuit() {
        scanner = new Scanner(System.in);
        Scores = new ScoreTable();
        gameReplay = new GameReplay();
    }
    
    public static void main(String[] args) {
        HighSuit game = new HighSuit();
        game.start();
    }
    
    public void start() {
        displayWelcome();
        
        boolean playAgain = true;
        while (playAgain) {
            setupGame();
            playGame();
            displayFinalResults();
            
            // Save to high scores
            for (Player player : players) {
                Scores.addScore(player.getName(), player.getTotalScore(), numRounds);
            }
            
            // Offer to view high scores
            System.out.print("\nWould you like to view the high score table? (y/n): ");
            if (scanner.nextLine().trim().equalsIgnoreCase("y")) {
                Scores.display();
            }
            
            // Offer replay
            System.out.print("\nWould you like to view the game replay? (y/n): ");
            if (scanner.nextLine().trim().equalsIgnoreCase("y")) {
                gameReplay.displayReplay();
            }
            
            System.out.print("\nPlay another game? (y/n): ");
            playAgain = scanner.nextLine().trim().equalsIgnoreCase("y");
            
            if (playAgain) {
                gameReplay = new GameReplay(); // Reset replay for new game
            }
        }
        
        System.out.println("\nThanks for playing HighSuit!");
        scanner.close();
    }
    
    private void displayWelcome() {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("WELCOME TO HIGHSUIT");
        System.out.println("=".repeat(60));
        System.out.println("A card game where you aim for the highest scoring suit!");
        System.out.println("- Picture cards (J, Q, K) score 10 points");
        System.out.println("- Aces score 11 points");
        System.out.println("- Number cards score their face value");
        System.out.println("- Choose a bonus suit for +5 points if it's your highest!");
        System.out.println("=".repeat(60) + "\n");
    }
    
    private void setupGame() {
        players = new ArrayList<>();
        
        // Level 1: Get number of players
        System.out.print("Enter number of players (1 or 2): ");
        int numPlayers = getValidInput(1, 2);
        
        // Get player names
        for (int i = 1; i <= numPlayers; i++) {
            System.out.print("Enter name for Player " + i + " (or 'Computer' for AI): ");
            String name = scanner.nextLine().trim();
            if (name.isEmpty()) {
                name = "Player " + i;
            }
            players.add(new Player(name));
        }
        
        // Level 5: Get number of rounds
        System.out.print("\nEnter number of rounds (1-3): ");
        numRounds = getValidInput(1, 3);
        
        System.out.println("\n" + "=".repeat(60));
        System.out.println("Starting game with " + numPlayers + " player(s) for " + numRounds + " round(s)");
        System.out.println("=".repeat(60));
    }
    
    private void playGame() {
        for (int round = 1; round <= numRounds; round++) {
            System.out.println("\n" + "=".repeat(60));
            System.out.println("ROUND " + round + " of " + numRounds);
            System.out.println("=".repeat(60));
            
            playRound(round);
            
            // Pause between rounds
            if (round < numRounds) {
                System.out.println("\nPress Enter to continue to next round...");
                scanner.nextLine();
            }
        }
    }
    
    private void playRound(int roundNumber) {
        // Initialize deck and deal cards
        deck = new Deck();
        deck.shuffle();
        
        GameReplay.RoundReplay roundReplay = new GameReplay.RoundReplay();
        
        // Deal 5 cards to each player
        for (Player player : players) {
            player.clearHand();
            for (int i = 0; i < 5; i++) {
                player.addCard(deck.dealCard());
            }
        }
        
        // Each player's turn
        for (Player player : players) {
            System.out.println();
            
            // Store initial hand for replay
            ArrayList<Card> initialHand = new ArrayList<>(player.getHand());
            
            // Level 2: Display hand and select bonus suit
            int bonusSuit = player.selectBonusSuit(scanner);
            
            // Level 3: Swap cards
            System.out.println();
            ArrayList<Integer> swappedPositions = player.selectCardsToSwap(scanner, deck);
            
            // Perform swaps
            if (!swappedPositions.isEmpty()) {
                // Sort in descending order to maintain correct indices
                Collections.sort(swappedPositions, Collections.reverseOrder());
                
                for (int pos : swappedPositions) {
                    if (pos >= 0 && pos < player.getHand().size() && !deck.isEmpty()) {
                        player.getHand().remove(pos);
                        player.addCard(deck.dealCard());
                    }
                }
                
                System.out.println("\nUpdated hand:");
                player.displayHand();
            }
            
            // Level 4: Calculate and display score
            int roundScore = player.calculateRoundScore(bonusSuit);
            player.addToTotalScore(roundScore);
            
            int[] suitScores = player.calculateSuitScores();
            int maxScore = 0;
            int maxSuit = 0;
            for (int i = 0; i < 4; i++) {
                if (suitScores[i] > maxScore) {
                    maxScore = suitScores[i];
                    maxSuit = i;
                }
            }
            
            System.out.println("\nHighest scoring suit: " + suitNames[maxSuit] + " (" + maxScore + " points)");
            if (maxSuit == bonusSuit) {
                System.out.println("Bonus applied! (+5 points)");
            }
            System.out.println(player.getName() + "'s score this round: " + roundScore);
            System.out.println(player.getName() + "'s total score: " + player.getTotalScore());
            
            // Store replay data
            roundReplay.addPlayerData(new GameReplay.PlayerRoundData(
                player.getName(),
                initialHand,
                bonusSuit,
                swappedPositions,
                new ArrayList<>(player.getHand()),
                roundScore
            ));
            
            System.out.println("-".repeat(60));
        }
        
        gameReplay.addRound(roundReplay);
    }
    
    private void displayFinalResults() {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("FINAL RESULTS");
        System.out.println("=".repeat(60));
        
        // Sort players by score (descending)
        ArrayList<Player> sortedPlayers = new ArrayList<>(players);
        sortedPlayers.sort((p1, p2) -> Integer.compare(p2.getTotalScore(), p1.getTotalScore()));
        
        System.out.printf("%-20s %s%n", "Player", "Total Score");
        System.out.println("-".repeat(60));
        
        for (Player player : sortedPlayers) {
            System.out.printf("%-20s %d%n", player.getName(), player.getTotalScore());
        }
        
        System.out.println("=".repeat(60));
        
        // Declare winner
        Player winner = sortedPlayers.get(0);
        if (sortedPlayers.size() > 1 && sortedPlayers.get(0).getTotalScore() == sortedPlayers.get(1).getTotalScore()) {
            System.out.println("\nIt's a tie!");
        } else {
            System.out.println("\n" + winner.getName() + " wins with " + winner.getTotalScore() + " points!");
        }
        
        // Display average score for high score table
        System.out.println("\nAverage scores per round:");
        for (Player player : players) {
            int avgScore = player.getTotalScore() / numRounds;
            System.out.println(player.getName() + ": " + avgScore);
        }
    }
    
    private int getValidInput(int min, int max) {
        int input = -1;
        while (input < min || input > max) {
            try {
                input = Integer.parseInt(scanner.nextLine().trim());
                if (input < min || input > max) {
                    System.out.print("Invalid input. Enter a number between " + min + " and " + max + ": ");
                }
            } catch (NumberFormatException e) {
                System.out.print("Invalid input. Enter a number between " + min + " and " + max + ": ");
            }
        }
        return input;
    }
}