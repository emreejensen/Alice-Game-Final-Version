/*
 * The GameMap class is responsible for loading the game map, items, 
 * puzzles, and monsters from files and providing access to the rooms. 
 * It uses an ArrayList for storing rooms and HashMap for storing items, puzzles, 
 * and monsters, allowing for efficient lookups and management of game elements. 
 * The class includes methods for loading data from files, retrieving rooms by ID, 
 * and associating items, puzzles, and monsters with rooms.
 */

package model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GameMap {
    private ArrayList<Room> rooms;
    private Map<String, Item> items;
    private Map<String, Puzzle> puzzles;
    private Map<String, Monster> monsters;

    // Default constructor
    public GameMap() throws IOException, InvalidRoomException {
        this("Map.txt", "Item.txt", "Puzzle.txt", "Monsters.txt");
    }

    // Constructor that accepts all file paths
    public GameMap(String roomFilePath, String itemFilePath, String puzzleFilePath, String monsterFilePath) throws IOException, InvalidRoomException {
        rooms = new ArrayList<>();
        items = new HashMap<>();
        puzzles = new HashMap<>();
        monsters = new HashMap<>();

        loadItems(itemFilePath);
        loadPuzzles(puzzleFilePath);
        loadMonsters(monsterFilePath);
        readRooms(roomFilePath);
    }

    public Room getRoom(String roomID) throws InvalidRoomException {
        for (Room room : rooms) {
            if (roomID.equals(room.getRoomID())) {
                return room;
            }
        }
        throw new InvalidRoomException("Room not found: " + roomID);
    }

    private void loadItems(String fileName) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("~");
                if (parts.length >= 2) {
                    String itemName = parts[0];
                    String itemDescription = parts[1];

                    int attackPoints = 0;
                    int healPoints = 0;

                    if (parts.length >= 4) {
                        try {
                            attackPoints = Integer.parseInt(parts[2]);
                        } catch (NumberFormatException e) {
                            System.err.println("Invalid attack points for item: " + itemName);
                        }

                        try {
                            healPoints = Integer.parseInt(parts[3]);
                        } catch (NumberFormatException e) {
                            System.err.println("Invalid heal points for item: " + itemName);
                        }
                    }

                    Item item = new Item(itemName, itemDescription, attackPoints, healPoints);
                    items.put(itemName, item);
                } else {
                    System.err.println("Invalid item data: " + line);
                }
            }
        }
    }

    private void loadPuzzles(String fileName) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("~");
                if (parts.length >= 3) {
                    String puzzleID = parts[0];
                    String description = parts[1];
                    String solution = parts[2];
                    Puzzle puzzle = new Puzzle(puzzleID, description, solution);
                    puzzles.put(puzzleID, puzzle);
                } else {
                    System.err.println("Invalid puzzle data: " + line);
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading puzzles file: " + fileName);
        }
    }

    private void loadMonsters(String fileName) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("~");
                if (parts.length >= 6) {
                    String name = parts[0];
                    String description = parts[1];
                    int health = Integer.parseInt(parts[2]);
                    int attackPower = Integer.parseInt(parts[3]);
                    int defense = Integer.parseInt(parts[4]);
                    String itemDrop = parts[5];
                    Monster monster = new Monster(name, description, health, attackPower, defense, itemDrop);
                    monsters.put(name, monster);
                } else {
                    System.err.println("Invalid monster data: " + line);
                }
            }
        }
    }

    private void readRooms(String fileName) throws IOException, InvalidRoomException {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("~");
                if (parts.length < 8) {
                    throw new InvalidRoomException("Invalid room data: " + line);
                }

                String roomID = parts[0];
                boolean isVisited = Boolean.parseBoolean(parts[1]);
                String roomName = parts[2];
                String description = parts[3];
                String itemID = parts[4];
                String puzzleID = parts[5];
                String[] connections = parts[6].split(",");
                String monsterID = parts[7];

                if (connections.length < 4) {
                    throw new InvalidRoomException("Invalid connections data: " + parts[6]);
                }

                String north = connections[0];
                String east = connections[1];
                String south = connections[2];
                String west = connections[3];

                Room room = new Room(roomID, roomName, isVisited, description, north, east, south, west);

                if (!itemID.equals("0")) {
                    Item item = items.get(itemID);
                    if (item != null) {
                        room.addItem(item);
                    } else {
                        System.err.println("Item ID not found: " + itemID);
                    }
                }

                if (!puzzleID.equals("0")) {
                    Puzzle puzzle = puzzles.get(puzzleID);
                    if (puzzle != null) {
                        room.addPuzzle(puzzle);
                    } else {
                        System.err.println("Puzzle ID not found: " + puzzleID);
                    }
                }

                if (!monsterID.equals("0")) {
                    Monster monster = monsters.get(monsterID);
                    if (monster != null) {
                        room.addMonster(monster);
                    } else {
                        System.err.println("Monster ID not found: " + monsterID);
                    }
                }

                rooms.add(room);
            }
        }
    }
}
