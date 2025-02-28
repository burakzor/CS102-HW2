import java.util.ArrayList;

public class Player {
    String playerName;
    Tile[] playerTiles;
    int numberOfTiles;
    ArrayList<Tile> notDrawedTiles; // Added to discardTileForComputer()

    public Player(String name) {
        setName(name);
        playerTiles = new Tile[15]; // there are at most 15 tiles a player owns at any time
        numberOfTiles = 0; // currently this player owns 0 tiles, will pick tiles at the beggining of the game
        notDrawedTiles = new ArrayList<Tile>(); // Added to discardTileForComputer()
    }

    /*
     * TODO: removes and returns the tile in given index -------------------Enes
     */
    public Tile getAndRemoveTile(int index) {
        return null;
    }

    /*
     * TODO: adds the given tile to the playerTiles in order ------------Enes
     * should also update numberOfTiles accordingly.
     * make sure playerTiles are not more than 15 at any time
     */
    public void addTile(Tile t) {

    }

    /*
     * TODO: checks if this player's hand satisfies the winning condition ------------Kaptan
     * to win this player should have 3 chains of length 4, extra tiles
     * does not disturb the winning condition
     * @return
     */
    public boolean isWinningHand() {
        return false;
    }

    public int findPositionOfTile(Tile t) {
        int tilePosition = -1;
        for (int i = 0; i < numberOfTiles; i++) {
            if(playerTiles[i].compareTo(t) == 0) {
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
            if (aTile.getColor() == color && aTile.getValue() == number) index = i;
        }

        return index;
    }

    public ArrayList<Tile> getNotDrawedTiles() {
        return notDrawedTiles;
    }

    public ArrayList<Integer> getNotDrawedTileValues() {
        ArrayList<Integer> values = new ArrayList<Integer>();
        for (Tile aTile : notDrawedTiles) {
            if (!values.contains(aTile.getColor())) values.add(aTile.getValue());
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
