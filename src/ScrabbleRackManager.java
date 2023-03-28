import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

/**
 * @author 26prakash
 * @version 10.28.2023
 * EXTRA: BLANK TILES
 * Lines 16, 19, 84, 101
 */

public class ScrabbleRackManager {
    private ArrayList<ArrayList<String>> database;
    private final String alpha = "abcdefghijklmnopqrstuvwxyz ";
    private ArrayList<String> gameTiles;
    private ArrayList<String> myTiles;
    private int[] frequencies = {9, 2, 2, 4, 12, 2, 3, 2, 9, 1, 1, 4, 2, 6, 8, 2, 1, 6, 4, 6, 4, 2, 2, 1, 2};


    /**
     * ScrabbleRackManager constructor
     * populates buckets
     * sets tiles
     */
    public ScrabbleRackManager() {

        gameTiles = new ArrayList<>();
        myTiles = new ArrayList<>();

        database = new ArrayList<>();

        for(int i = 0; i < 26; i++) {
            database.add(new ArrayList<String>());
        }

        try {
            Scanner in = new Scanner(new File("new_scrabble.txt"));
            while(in.hasNext()) {
                String word = in.nextLine();
                database.get(alpha.indexOf(word.substring(0,1))).add(word);
            }

            in.close();
            for(int i = 0; i < database.size(); i++) {
                Collections.sort(database.get(i));
            }


            for(int i = 0; i < frequencies.length; i++) {
                for(int t = 0; t < frequencies[i]; t++) {
                    gameTiles.add(alpha.substring(i, i+1));
                }
            }

            setTiles();

        } catch (FileNotFoundException e) {
            System.out.println(e);;
        }
    }


    private void setTiles(){
        for(int i = 0; i < 7; i++) {
            int temp = (int)(gameTiles.size() * Math.random());
            System.out.println(gameTiles.get(temp));
            myTiles.add(gameTiles.get(temp));
            gameTiles.remove(gameTiles.get(temp));
        }
    }
    /**
     * printRack method of the ScrabbleRackManager class
     * displays the contents of the player's tile rack
     */
    public void printRack() {
        System.out.println("Letters in the tile rack " + myTiles);
    }

    private boolean isPlayable(String word) {
        ArrayList<String> copy = new ArrayList<>(myTiles);
        for(int i = 0; i <  word.length(); i++) {
            if(!copy.remove(word.substring(i, i+1)) && !copy.remove(" ")){
                return false;
            }
        }

        return true;
    }

    /**
     * getPlaylist method of the ScrabbleRackManager class
     * builds and returns an ArrayList of String objects that are values pulled
     * from the dictionary/database based on the available letters in the user's tile rack
     */
    public ArrayList<String> getPlaylist() {
        ArrayList<String> goodthings = new ArrayList<>();

        for(ArrayList<String> bucket: database) {
            if (myTiles.contains(bucket.get(0).substring(0, 1)) || myTiles.contains(" ")) {
                for(int i = 0 ; i < bucket.size(); i++) {
                    if (isPlayable(bucket.get(i))) {
                        goodthings.add(bucket.get(i));
                    }
                }
            }

        }
        return goodthings;
    }

    /**
     * printMatches method of ScrabbleRackManager class
     * prints all of the playable words based on the letters in the tile rack
     */
    public void printMatches(){
        ArrayList<String> matches = getPlaylist();
        for(int i = 0; i < matches.size(); i++) {
            if((i) % 10 == 0)
                System.out.println();
            System.out.printf("%-14s", matches.get(i));
        }
    }




    /**
     * main method of the ScrabbleRackManager class
     * main method for the class; use only 3 command lines in main
     * @param args command line arguments: if needed
     *
     */
    public static void main(String[] args){
        ScrabbleRackManager app = new ScrabbleRackManager();
        app.printRack();
        app.printMatches();
    }


}