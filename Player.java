import java.util.*;

public class Player {
    String playerName;
    Tile[] playerTiles;
    int numberOfTiles;
    ArrayList<Tile> notDrawedTiles; // Added to discardTileForComputer()

    public Player(String name) {
        setName(name);
        playerTiles = new Tile[15]; // there are at most 15 tiles a player owns at any time
        numberOfTiles = 0; // currently this player owns 0 tiles, will pick tiles at the beggining of the
                           // game
        notDrawedTiles = new ArrayList<Tile>(); // Added to discardTileForComputer()
    }

    /*
     * TODO: removes and returns the tile in given index -------------------Enes*Done
     */
    public Tile getAndRemoveTile(int index) {
        if (index < 0 || index >= numberOfTiles) {
            return null;
        }
        Tile removedTile = playerTiles[index];
        for (int i = index; i < numberOfTiles - 1; i++) {
            playerTiles[i] = playerTiles[i + 1];
        }
        playerTiles[numberOfTiles - 1] = null;
        numberOfTiles--;

        return removedTile;
    }

    /*
     * TODO: adds the given tile to the playerTiles in order ------------Enes*Done
     * should also update numberOfTiles accordingly.
     * make sure playerTiles are not more than 15 at any time
     */
    public void addTile(Tile t) {
        if (numberOfTiles >= 15) {
            return;
        }

        int i = numberOfTiles - 1;
        while (i >= 0 && playerTiles[i].compareTo(t) > 0) {
            playerTiles[i + 1] = playerTiles[i];
            i--;
        }
        playerTiles[i + 1] = t;
        numberOfTiles++;
    }

    /*
     * TODO: checks if this player's hand satisfies the winning condition ------------Kaptan-- Done
     * to win this player should have 3 chains of length 4, extra tiles
     * does not disturb the winning condition
     *Chains can be made only using the different colors of the same number.
     * @return
     */
    public boolean isWinningHand() {
        Map<Integer, Set<Integer>> numberToColors = new HashMap<>();

        for (int i = 0; i < numberOfTiles; i++) {
            Tile t = playerTiles[i];

            if (t == null) continue; // Eğer null taş varsa

            numberToColors
                    .computeIfAbsent(t.getValue(), k -> new HashSet<>())
                    .add(t.colorNameToInt());
        }
        int chainCount = 0;
        for (Set<Integer> colors : numberToColors.values()) {
            if (colors.size() >= 4) {
                chainCount++;
            }
        }

        return chainCount >= 3;
    }


    public int findPositionOfTile(Tile t) {
        int tilePosition = -1;
        for (int i = 0; i < numberOfTiles; i++) {
            if (playerTiles[i].compareTo(t) == 0) {
                tilePosition = i;
            }
        }
        return tilePosition;
    }

    public void displayTiles() {
        System.out.println(playerName + "'s Tiles:");
        for (int i = 0; i < numberOfTiles; i++) {
            System.out.print(playerTiles[i].toString() + " ");
        }
        System.out.println();
    }

    // Added to discardTileForComputer()
    public void addToNotDrawedTiles(Tile aTile) {
        notDrawedTiles.add(aTile);
    }

    public int getATileIndex(int colorIndex, int number) {
        int index = 0;

        char color = Tile.COLORS[colorIndex];
        for (int i = 0; i < playerTiles.length; i++) {
            Tile aTile = playerTiles[i];
            if (aTile.getColor() == color && aTile.getValue() == number)
                index = i;
        }

        return index;
    }

    public ArrayList<Tile> getNotDrawedTiles() {
        return notDrawedTiles;
    }

    public ArrayList<Integer> getNotDrawedTileValues() {
        ArrayList<Integer> values = new ArrayList<Integer>();
        for (Tile aTile : notDrawedTiles) {
            if (!values.contains(aTile.getColor()))
                values.add(aTile.getValue());
        }

        return values;
    }

    public Tile[] getTiles() {
        return playerTiles;
    }

    public void setName(String name) {
        playerName = name;
    }

    public String getName() {
        return playerName;
    }
}
