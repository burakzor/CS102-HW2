import java.util.Random;

public class OkeyGame {

    Player[] players;
    Tile[] tiles;

    Tile lastDiscardedTile;

    int currentPlayerIndex = 0;

    public OkeyGame() {
        players = new Player[4];
    }

    public void createTiles() {
        tiles = new Tile[112];
        int currentTile = 0;

        // two copies of each color-value combination, no jokers
        for (int i = 1; i <= 7; i++) {
            for (int j = 0; j < 4; j++) {
                tiles[currentTile++] = new Tile(i,'Y');
                tiles[currentTile++] = new Tile(i,'B');
                tiles[currentTile++] = new Tile(i,'R');
                tiles[currentTile++] = new Tile(i,'K');
            }
        }
    }

    /*
     * TODO: distributes the starting tiles to the players --------------------Yakup
     * player at index 0 gets 15 tiles and starts first
     * other players get 14 tiles
     * this method assumes the tiles are already shuffled
     */
    public void distributeTilesToPlayers() {
        for(int j=1; j<=3; j++)
        {
            for(int i=0; i<15; i++)
            {
                players[0].getTiles()[i] = tiles[i];
                players[j].getTiles()[i] = tiles[i+1+(14*j)];
            }   
        } 
    }

    /*
     * TODO: get the last discarded tile for the current player --------------------Yusuf
     * (this simulates picking up the tile discarded by the previous player)
     * it should return the toString method of the tile so that we can print what we picked
     */
    public String getLastDiscardedTile() {
        return null;
    }

    /*
     * TODO: get the top tile from tiles array for the current player -----------Yusuf
     * that tile is no longer in the tiles array (this simulates picking up the top tile)
     * it should return the toString method of the tile so that we can print what we picked
     */
    public String getTopTile() {
        return null;
    }

    /*
     * TODO: should randomly shuffle the tiles array before game starts -------------- Yakup
     */
    public void shuffleTiles() {
        Random rand = new Random();
        for (int i = tiles.length - 1; i > 0; i--)
        {
            int j = rand.nextInt(i + 1);
            Tile temp = tiles[i];
            tiles[i] = tiles[j];
            tiles[j] = temp;
        }
    }

    /*
     * TODO: check if game still continues, should return true if current player -------------------Kaptan
     * finished the game, use isWinningHand() method of Player to decide
     */
    public boolean didGameFinish() {
        return false;
    }

    /*
     * TODO: Pick a tile for the current computer player using one of the following: ---------Kaptan
     * - picking from the tiles array using getTopTile()
     * - picking from the lastDiscardedTile using getLastDiscardedTile()
     * You should consider if the discarded tile is useful for the computer in
     * the current status. Print whether computer picks from tiles or discarded ones.
     */
    public void pickTileForComputer() {

    }

    /*
     * TODO: Current computer player will discard the least useful tile. ---------------Burak
     * this method should print what tile is discarded since it should be
     * known by other players. You may first discard duplicates and then
     * the single tiles and tiles that contribute to the smallest chains.
     */
    public void discardTileForComputer() {

    }

    /*
     * TODO: discards the current player's tile at given index. -----------------Burak
     * this should set lastDiscardedTile variable and remove that tile from
     * that player's tiles
     */
    public void discardTile(int tileIndex) {
        lastDiscardedTile = players[currentPlayerIndex].getTiles()[tileIndex];
        players[currentPlayerIndex].getTiles()[tileIndex] = null;
        for(int i=tileIndex; i<players[currentPlayerIndex].getTiles().length - 1; i++)
        {
            players[currentPlayerIndex].getTiles()[i] = players[currentPlayerIndex].getTiles()[i+1];
        }
        players[currentPlayerIndex].getTiles()[players[currentPlayerIndex].getTiles().length - 1] = null;
    }

    public void displayDiscardInformation() {
        if(lastDiscardedTile != null) {
            System.out.println("Last Discarded: " + lastDiscardedTile.toString());
        }
    }

    public void displayCurrentPlayersTiles() {
        players[currentPlayerIndex].displayTiles();
    }

    public int getCurrentPlayerIndex() {
        return currentPlayerIndex;
    }

      public String getCurrentPlayerName() {
        return players[currentPlayerIndex].getName();
    }

    public void passTurnToNextPlayer() {
        currentPlayerIndex = (currentPlayerIndex + 1) % 4;
    }

    public void setPlayerName(int index, String name) {
        if(index >= 0 && index <= 3) {
            players[index] = new Player(name);
        }
    }

}
