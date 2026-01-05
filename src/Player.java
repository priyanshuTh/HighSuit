import java.util.ArrayList;
import java.util.Scanner;

public class Player {
    private String name;
    private ArrayList<Card> hand;
    private int totalScore;
    private boolean isComputer;
    
    public Player(String name) {
        this.name = name;
        this.hand = new ArrayList<>();
        this.totalScore = 0;
        this.isComputer = name.equalsIgnoreCase("Computer");
    }
    
    public String getName() {
        return name;
    }
    
    public ArrayList<Card> getHand() {
        return hand;
    }
    
    public void addCard(Card card) {
        hand.add(card);
    }
    
    public void clearHand() {
        hand.clear();
    }
    
    public int getTotalScore() {
        return totalScore;
    }
    
    public void addToTotalScore(int score) {
        totalScore += score;
    }
    
    public boolean isComputer() {
        return isComputer;
    }
    
    public void displayHand() {
        System.out.print(name + "'s hand: ");
        for (int i = 0; i < hand.size(); i++) {
            System.out.print("[" + (i + 1) + "] " + hand.get(i).toSymbol() + "  ");
        }
        System.out.println();
    }
    
    // Calculate maximum score for each suit
    public int[] calculateSuitScores() {
        int[] suitScores = new int[4]; // Clubs, Diamonds, Hearts, Spades
        
        for (Card card : hand) {
            suitScores[card.getSuitValue()] += card.getScore();
        }
        
        return suitScores;
    }
    
    // Get the best suit (highest scoring)
    public int getBestSuit() {
        int[] suitScores = calculateSuitScores();
        int maxScore = 0;
        int bestSuit = 0;
        
        for (int i = 0; i < 4; i++) {
            if (suitScores[i] > maxScore) {
                maxScore = suitScores[i];
                bestSuit = i;
            }
        }
        
        return bestSuit;
    }
    
    // Get maximum possible score from a single suit
    public int getMaxSuitScore() {
        int[] suitScores = calculateSuitScores();
        int maxScore = 0;
        
        for (int score : suitScores) {
            if (score > maxScore) {
                maxScore = score;
            }
        }
        
        return maxScore;
    }
    
    // Select bonus suit (human or computer)
    public int selectBonusSuit(Scanner scanner) {
        String[] suitNames = {"Clubs", "Diamonds", "Hearts", "Spades"};
        int[] suitScores = calculateSuitScores();
        
        System.out.println("\n" + name + "'s turn:");
        displayHand();
        
        System.out.println("\nSuit scores:");
        for (int i = 0; i < 4; i++) {
            System.out.println((i + 1) + ". " + suitNames[i] + ": " + suitScores[i]);
        }
        System.out.println("Maximum score possible: " + getMaxSuitScore());
        
        if (isComputer) {
            int bestSuit = getBestSuit();
            System.out.println("\nComputer selects bonus suit: " + suitNames[bestSuit]);
            return bestSuit;
        } else {
            System.out.print("\nSelect your bonus suit (1-4): ");
            int choice = getValidInput(scanner, 1, 4);
            return choice - 1;
        }
    }
    
    // Select cards to swap
    public ArrayList<Integer> selectCardsToSwap(Scanner scanner, Deck deck) {
        ArrayList<Integer> cardsToSwap = new ArrayList<>();
        
        if (isComputer) {
            cardsToSwap = computerSelectCards();
            if (!cardsToSwap.isEmpty()) {
                System.out.print("Computer swaps cards at positions: ");
                for (int pos : cardsToSwap) {
                    System.out.print((pos + 1) + " ");
                }
                System.out.println();
            } else {
                System.out.println("Computer keeps all cards.");
            }
        } else {
            System.out.println("\nSelect cards to swap (up to 4):");
            displayHand();
            System.out.println("Enter card positions (1-5) separated by spaces, or '0' to keep all:");
            
            String input = scanner.nextLine().trim();
            if (!input.equals("0") && !input.isEmpty()) {
                String[] positions = input.split("\\s+");
                for (String pos : positions) {
                    try {
                        int position = Integer.parseInt(pos);
                        if (position >= 1 && position <= 5 && !cardsToSwap.contains(position - 1)) {
                            cardsToSwap.add(position - 1);
                            if (cardsToSwap.size() >= 4) break;
                        }
                    } catch (NumberFormatException e) {
                        // Ignore invalid input
                    }
                }
            }
        }
        
        return cardsToSwap;
    }
    
    // Computer AI for selecting cards to swap
    private ArrayList<Integer> computerSelectCards() {
        ArrayList<Integer> toSwap = new ArrayList<>();
        int bestSuit = getBestSuit();
        int[] suitScores = calculateSuitScores();
        
        // Strategy: Keep cards of the best suit, swap others
        // But only swap if we have enough cards in deck and it makes sense
        for (int i = 0; i < hand.size() && toSwap.size() < 4; i++) {
            Card card = hand.get(i);
            
            // Swap if card is not of the best suit and best suit has at least 2 cards
            if (card.getSuitValue() != bestSuit) {
                int cardsInBestSuit = 0;
                for (Card c : hand) {
                    if (c.getSuitValue() == bestSuit) cardsInBestSuit++;
                }
                
                // Only swap if we have at least 2 cards in best suit
                if (cardsInBestSuit >= 2) {
                    toSwap.add(i);
                }
            }
        }
        
        return toSwap;
    }
    
    // Calculate final score for the round
    public int calculateRoundScore(int bonusSuit) {
        int[] suitScores = calculateSuitScores();
        int maxScore = 0;
        int maxSuit = 0;
        
        for (int i = 0; i < 4; i++) {
            if (suitScores[i] > maxScore) {
                maxScore = suitScores[i];
                maxSuit = i;
            }
        }
        
        // Add bonus if highest suit matches bonus suit
        if (maxSuit == bonusSuit) {
            maxScore += 5;
        }
        
        return maxScore;
    }
    
    private int getValidInput(Scanner scanner, int min, int max) {
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