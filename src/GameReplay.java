import java.util.ArrayList;

public class GameReplay {
    private ArrayList<RoundReplay> rounds;
    
    public GameReplay() {
        rounds = new ArrayList<>();
    }
    
    public void addRound(RoundReplay round) {
        rounds.add(round);
    }
    
    public void displayReplay() {
        String[] suitNames = {"Clubs", "Diamonds", "Hearts", "Spades"};
        
        System.out.println("\n" + "=".repeat(70));
        System.out.println("GAME REPLAY");
        System.out.println("=".repeat(70));
        
        for (int i = 0; i < rounds.size(); i++) {
            RoundReplay round = rounds.get(i);
            System.out.println("\nROUND " + (i + 1) + ":");
            System.out.println("-".repeat(70));
            
            for (PlayerRoundData data : round.playerData) {
                System.out.println("\n" + data.playerName + ":");
                
                System.out.print("Initial Hand: ");
                for (Card card : data.initialHand) {
                    System.out.print(card.toSymbol() + " ");
                }
                System.out.println();
                
                System.out.println("Bonus Suit: " + suitNames[data.bonusSuit]);
                
                if (!data.swappedCards.isEmpty()) {
                    System.out.print("Cards Swapped: ");
                    for (int pos : data.swappedCards) {
                        System.out.print((pos + 1) + " ");
                    }
                    System.out.println();
                } else {
                    System.out.println("No cards swapped");
                }
                
                System.out.print("Final Hand: ");
                for (Card card : data.finalHand) {
                    System.out.print(card.toSymbol() + " ");
                }
                System.out.println();
                
                System.out.println("Round Score: " + data.roundScore);
            }
        }
        
        System.out.println("\n" + "=".repeat(70));
    }
    
    public static class RoundReplay {
        ArrayList<PlayerRoundData> playerData;
        
        public RoundReplay() {
            playerData = new ArrayList<>();
        }
        
        public void addPlayerData(PlayerRoundData data) {
            playerData.add(data);
        }
    }
    
    public static class PlayerRoundData {
        String playerName;
        ArrayList<Card> initialHand;
        int bonusSuit;
        ArrayList<Integer> swappedCards;
        ArrayList<Card> finalHand;
        int roundScore;
        
        public PlayerRoundData(String playerName, ArrayList<Card> initialHand, 
                               int bonusSuit, ArrayList<Integer> swappedCards,
                               ArrayList<Card> finalHand, int roundScore) {
            this.playerName = playerName;
            this.initialHand = new ArrayList<>(initialHand);
            this.bonusSuit = bonusSuit;
            this.swappedCards = new ArrayList<>(swappedCards);
            this.finalHand = new ArrayList<>(finalHand);
            this.roundScore = roundScore;
        }
    }
}