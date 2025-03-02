import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
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
        int tileIndexOfArray = 0;

        for (int i = 0; i < 15; i++) {
            players[0].getTiles()[i] = tiles[tileIndexOfArray];
            tileIndexOfArray ++;
        }
        players[0].numberOfTiles = 15;
        for (int i = 0; i < 14; i++) {
            players[1].getTiles()[i] = tiles[tileIndexOfArray];
            tileIndexOfArray ++;
        }
        players[1].numberOfTiles = 14;
        for (int i = 0; i < 14; i++) {
            players[2].getTiles()[i] = tiles[tileIndexOfArray];
            tileIndexOfArray ++;
        }
        players[2].numberOfTiles = 14;
        for (int i = 0; i < 14; i++) {
            players[3].getTiles()[i] = tiles[tileIndexOfArray];
            tileIndexOfArray ++;
        }
        players[3].numberOfTiles = 14;
    }

    /*
     * TODO: get the last discarded tile for the current player --------------------Yusuf
    * (this simulates picking up the tile discarded by the previous player)
      * it should return the toString method of the tile so that we can print what we picked
     */
    public String getLastDiscardedTile() {
        Player currentPlayer = players[currentPlayerIndex];

        currentPlayer.addTile(lastDiscardedTile);
        return lastDiscardedTile.toString();
    }

    /*
     * TODO: get the top tile from tiles array for the current player -----------Yusuf
     * that tile is no longer in the tiles array (this simulates picking up the top tile)
     * it should return the toString method of the tile so that we can print what we picked
     */
    public String getTopTile() {
        if (tiles == null || tiles.length == 0) {
            System.out.println("Error: No more tiles left in the stack!");
            return "No more tiles"; // Alternatif olarak bir mesaj dönebiliriz
        }

        Player currentPlayer = players[currentPlayerIndex];
        Tile pickedTile = tiles[tiles.length - 1]; // En üstteki taşı al

        if (pickedTile == null) {
            System.out.println("Error: Picked tile is null!");
            return "Invalid tile";
        }

        currentPlayer.addToNotDrawedTiles(lastDiscardedTile);

        // Yeni taş dizisini bir eksilterek oluştur
        Tile[] nTiles = Arrays.copyOf(tiles, tiles.length - 1);
        tiles = nTiles;

        currentPlayer.addTile(pickedTile); // Oyuncuya taşı ekle

        return pickedTile.toString();
    }


    /*
     * TODO: should randomly shuffle the tiles array before game starts -------------- Yakup
     */
    public void shuffleTiles() {
        Random random = new Random();
        for (int i = tiles.length - 1; i > 0; i--)
        {
            int j = random.nextInt(i + 1);
            Tile temp = tiles[i];
            tiles[i] = tiles[j];
            tiles[j] = temp;
        }
    }

    /*
     * TODO: check if game still continues, should return true if current player -------------------Kaptan-- Done
     * finished the game, use isWinningHand() method of Player to decide
     */
    public boolean didGameFinish() {
        if (players[currentPlayerIndex].isWinningHand()) {
            System.out.println(players[currentPlayerIndex].getName() + " has won the game!");
            return true;
        }
        else if(players[(currentPlayerIndex + 1) % 4].isWinningHand()) {
            System.out.println(players[(currentPlayerIndex + 1) % 4].getName() + " has won the game!");
            return true;
        }
        else if(players[(currentPlayerIndex + 2) % 4].isWinningHand()) {
            System.out.println(players[(currentPlayerIndex + 2) % 4].getName() + " has won the game!");
            return true;
        }
        else if(players[(currentPlayerIndex + 3) % 4].isWinningHand()) {
            System.out.println(players[(currentPlayerIndex + 3) % 4].getName() + " has won the game!");
            return true;
        }
        //no tile left in the stack
        else if (tiles.length == 0) {
            System.out.println("No tiles left in the stack!");
            System.out.println("It is a draw!");
            return true;
        }
        else {
            return false;
        }

    }

    /*
     * TODO: Pick a tile for the current computer player using one of the following: ---------Kaptan -- Done
     * - picking from the tiles array using getTopTile()
     * - picking from the lastDiscardedTile using getLastDiscardedTile()
     * You should consider if the discarded tile is useful for the computer in
     * the current status. Print whether computer picks from tiles or discarded ones.
     * +++ Eğer lastDiscardedTile çekilmediyse (getTopFile() kullandıysan) player.addToNotDrawedTiles(Tile aTile) methodunu çağırıp çekmediğin
     *     tile'ı parametreye yazar mısın (ekler misin)? !!!!! "Tile" reference yüklenmesi lazım !!!!!  --------Buraktan özel istek
     */
    public void pickTileForComputer() {
        Player currentPlayer = players[currentPlayerIndex];

        Tile pickedTile = null; // Bilgisayarın aldığı taş

        // Önce, atılan taşı değerlendirelim
        if (lastDiscardedTile != null) {
            for (Tile t : currentPlayer.getTiles()) {
                if (t != null && t.canFormChainWith(lastDiscardedTile)) { // Null kontrolü yapalım
                    pickedTile = lastDiscardedTile; // Eğer işe yarıyorsa al
                    break;
                }
            }
        }

        // Eğer atılan taş işe yaramıyorsa desteden taş çekelim
        if (pickedTile == null) {
            if (tiles.length > 0) { // Deste boş değilse en üstten taş al
                pickedTile = tiles[tiles.length - 1];

                // Desteden taş çektikten sonra diziyi güncelleyelim
                tiles = Arrays.copyOf(tiles, tiles.length - 1);
            } else {
                System.out.println("No tiles left in the stack!");
                return;
            }
        }
        // Eğer bilgisayar bir taş aldıysa ekleyelim
        if (pickedTile != null) {
            currentPlayer.addTile(pickedTile);
            System.out.println(currentPlayer.getName() + " picked: " + pickedTile);
            currentPlayer.addToNotDrawedTiles(pickedTile);
        }
    }




    /*
     * TODO: Current computer player will discard the least useful tile. ---------------Burak ***Done
     * this method should print what tile is discarded since it should be
     * known by other players. You may first discard duplicates and then
     * the single tiles and tiles that contribute to the smallest chains.
     */

    public static int decideWhichTileToDiscard(ArrayList<Integer> indexesToDiscard, Tile[] playerTiles,
                                               ArrayList<Tile> notDrawedTiles, ArrayList<Integer> tileValues) {
        int discardIndex = 0;
        boolean found = false;

        for (int index : indexesToDiscard) {
            if (notDrawedTiles.contains(playerTiles[index])){
                found = true;
                discardIndex = index;
            } else if (tileValues.contains(playerTiles[index].getValue())){
                found = true;
                discardIndex = index;
            }
        }

        if (!found) discardIndex = (int)(Math.random() * indexesToDiscard.size());

        return discardIndex;
    }
    public void discardTileForComputer() {
        Player currentPlayer = players[currentPlayerIndex];
        Player nextPlayer = players[(currentPlayerIndex + 1) % 4];
        int[][] diffTiles = new int[4][7]; //[K-R-B-Y][1-2-3-4-5-6-7]
        int[] diffValueTiles = new int[7]; //[1-2-3-4-5-6-7]
        ArrayList<Integer> duplicateTiles = new ArrayList<Integer>();
        ArrayList<Integer> otherTiles = new ArrayList<Integer>();


        for (Tile aTile : currentPlayer.getTiles()) {
            if (aTile == null) continue;
            int colorIndex;
            if (aTile.getColor() == 'K') colorIndex = 0;
            else if (aTile.getColor() == 'R') colorIndex = 1;
            else if (aTile.getColor() == 'B') colorIndex = 2;
            else colorIndex = 3;
            diffTiles[colorIndex][aTile.getValue() - 1] += 1;
            diffValueTiles[aTile.getValue() - 1] += 1;
        }

        int maxCount = 0;
        for (int[] tiles : diffTiles) {
            for (int count : tiles) maxCount = Math.max(count, maxCount);
        }

        for (int colorIndex = 0; colorIndex < diffTiles.length; colorIndex++) {
            for (int number = 0; number < diffTiles[colorIndex].length; number++) {
                if (diffTiles[colorIndex][number] == maxCount) {
                    duplicateTiles.add(currentPlayer.getATileIndex(colorIndex, number + 1));
                }
            }
        }

        maxCount = 0;
        for (int count : diffValueTiles) {
            maxCount = Math.max(maxCount, count);
        }

        for (int number = 0; number < diffValueTiles.length; number++) {
            if (diffValueTiles[number] == maxCount) {
                int random = (int)(Math.random() * 4);
                otherTiles.add(currentPlayer.getATileIndex(random, number + 1));
            }
        }

        int tileIndex;


        if (duplicateTiles.size() == 0) {
            int index = decideWhichTileToDiscard(otherTiles, currentPlayer.getTiles(),
                    nextPlayer.getNotDrawedTiles(), nextPlayer.getNotDrawedTileValues());
            tileIndex = index;

            currentPlayer.getAndRemoveTile(tileIndex);
        } else if (duplicateTiles.size() == 1) {
            tileIndex = duplicateTiles.get(0);

        } else {
            int index = decideWhichTileToDiscard(duplicateTiles, currentPlayer.getTiles(),
                    nextPlayer.getNotDrawedTiles(), nextPlayer.getNotDrawedTileValues());
            tileIndex = index;
        }

        lastDiscardedTile = currentPlayer.getTiles()[tileIndex];
        currentPlayer.getAndRemoveTile(tileIndex);
        System.out.println(currentPlayer.getName() + " discarded tile " + currentPlayer.getTiles()[tileIndex]);



    }

    /*
     * TODO: discards the current player's tile at given index. -----------------Yakup
     * this should set lastDiscardedTile variable and remove that tile from
     * that player's tiles
     */
    public void discardTile(int tileIndex) {
        Player currentPlayer = players[currentPlayerIndex];
        lastDiscardedTile = currentPlayer.getTiles()[tileIndex];
        currentPlayer.getAndRemoveTile(tileIndex);
        players[currentPlayerIndex].numberOfTiles --;
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
