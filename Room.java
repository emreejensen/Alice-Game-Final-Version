/*
 * The Room class represents a room in the game and manages its attributes, 
 * including connected rooms, items, and puzzles. It provides methods to add, remove, 
 * and retrieve items and puzzles, as well as methods to attempt solving puzzles 
 * and track the number of attempts. This class is essential for maintaining the
 * state and interactions of each room in the game.
 * */

package model;
import java.util.ArrayList;
import java.util.List;

public class Room {
    private String roomID;
    private String name;
    private boolean isVisited;
    private String description;
    private String northRoomID;
    private String eastRoomID;
    private String southRoomID;
    private String westRoomID;
    private List<Item> items;
    private Puzzle puzzle;
    private int puzzleAttempts;
    private Monster monster;  // Monster in the room, if any

    public Room(String roomID, String name, boolean isVisited, String description, String northRoomID, String eastRoomID, String southRoomID, String westRoomID) {
        this.roomID = roomID;
        this.name = name;
        this.isVisited = isVisited;
        this.description = description;
        this.northRoomID = northRoomID;
        this.eastRoomID = eastRoomID;
        this.southRoomID = southRoomID;
        this.westRoomID = westRoomID;
        this.items = new ArrayList<>();
        this.puzzleAttempts = 0;
    }

    // Getters and setters for roomID, name, isVisited, description, etc.
    public String getRoomID() {
        return roomID;
    }

    public void setRoomID(String roomID) {
        this.roomID = roomID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isVisited() {
        return isVisited;
    }

    public void setVisited(boolean isVisited) {
        this.isVisited = isVisited;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getNorthRoomID() {
        return northRoomID;
    }

    public void setNorthRoomID(String northRoomID) {
        this.northRoomID = northRoomID;
    }

    public String getEastRoomID() {
        return eastRoomID;
    }

    public void setEastRoomID(String eastRoomID) {
        this.eastRoomID = eastRoomID;
    }

    public String getSouthRoomID() {
        return southRoomID;
    }

    public void setSouthRoomID(String southRoomID) {
        this.southRoomID = southRoomID;
    }

    public String getWestRoomID() {
        return westRoomID;
    }

    public void setWestRoomID(String westRoomID) {
        this.westRoomID = westRoomID;
    }

    public void addItem(Item item) {
        items.add(item);
    }

    public List<Item> getItems() {
        return items;
    }

    // Method to remove an item from the room
    public void removeItem(Item item) {
        items.remove(item);
    }

    // Method to add a puzzle to the room
    public void addPuzzle(Puzzle puzzle) {
        this.puzzle = puzzle;
    }

    // Method to get the puzzle in the room
    public Puzzle getPuzzle() {
        return puzzle;
    }

    // Method to check if a puzzle is present
    public boolean hasPuzzle() {
        return puzzle != null;
    }

    // Method to solve the puzzle
    public boolean solvePuzzle(String answer) {
        if (puzzle != null && puzzle.getSolution().equalsIgnoreCase(answer)) {
            return true;
        } else {
            puzzleAttempts++;
            return false;
        }
    }

    // Method to reset puzzle attempts
    public void resetPuzzleAttempts() {
        puzzleAttempts = 0;
    }

    // Method to get the number of puzzle attempts
    public int getPuzzleAttempts() {
        return puzzleAttempts;
    }
    
 // Method to add a monster to the room
    public void addMonster(Monster monster) {
        this.monster = monster;
    }

    // Method to get the monster in the room
 // Method to get the monster in the room
    public Monster getMonster() {
        return monster; // Simply return the monster in the room
    }
    
    public void removeMonster() {
        this.monster = null;
    }

}