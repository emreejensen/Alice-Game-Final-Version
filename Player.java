package model;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Player {
    private Room currentRoom;
    private GameMap map;
    private List<Item> inventory; // Player's inventory
    private List<String> completedPuzzles; // List of completed puzzle descriptions and answers
    private List<String> defeatedMonsters; // List of defeated monster names
    private int health; // Player's health
    private Item equippedItem; // Currently equipped item
    public static final int MAX_PUZZLE_ATTEMPTS = 3;

    // Default constructor
    public Player() throws InvalidRoomException, FileNotFoundException, IOException {
        map = new GameMap();
        currentRoom = map.getRoom("1");
        inventory = new ArrayList<>();
        completedPuzzles = new ArrayList<>();
        defeatedMonsters = new ArrayList<>();
        health = 100;
    }

    // Constructor with file paths
    public Player(String mapFilePath, String itemsFilePath, String puzzlesFilePath, String monstersFilePath)
            throws InvalidRoomException, IOException {
        map = new GameMap(mapFilePath, itemsFilePath, puzzlesFilePath, monstersFilePath);
        currentRoom = map.getRoom("1");
        inventory = new ArrayList<>();
        completedPuzzles = new ArrayList<>();
        defeatedMonsters = new ArrayList<>();
        health = 100;
    }

    public String getCurrentRoomName() {
        return currentRoom.getName();
    }

    public String getCurrentRoomDescription() {
        return currentRoom.getDescription();
    }

    public boolean isCurrentRoomVisited() {
        return currentRoom.isVisited();
    }

    public void markCurrentRoomVisited() {
        currentRoom.setVisited(true);
    }

    public void moveTo(String nextRoomID) throws InvalidRoomException {
        if (nextRoomID == null || nextRoomID.equals("0")) {
            throw new InvalidRoomException("Oh dear! Alice wandered off the map and vanished into butterflies!");
        }
        currentRoom = map.getRoom(nextRoomID);
        if (!currentRoom.isVisited()) {
            currentRoom.setVisited(true);
        } else {
            System.out.println();
            System.out.println("This looks awfully familiar...");
        }
        currentRoom.resetPuzzleAttempts();
    }

    public String getNextRoomID(String direction) {
        switch (direction.toLowerCase()) {
            case "north":
            case "n":
                return currentRoom.getNorthRoomID();
            case "east":
            case "e":
                return currentRoom.getEastRoomID();
            case "south":
            case "s":
                return currentRoom.getSouthRoomID();
            case "west":
            case "w":
                return currentRoom.getWestRoomID();
            default:
                return null;
        }
    }

    public void explore() {
        List<Item> items = currentRoom.getItems();
        if (items.isEmpty()) {
            System.out.println("The room is empty. No items to explore.");
        } else {
            System.out.println("Items available in the room:");
            for (Item item : items) {
                System.out.println("- " + item.getName());
            }
        }
    }

    public void pickUpItem(String itemName) {
        List<Item> items = currentRoom.getItems();
        for (Item item : items) {
            if (item.getName().equalsIgnoreCase(itemName)) {
                inventory.add(item);
                currentRoom.removeItem(item);
                System.out.println("Picked up: " + item.getName() + " from " + currentRoom.getName() + " room.");
                return;
            }
        }
        System.out.println("Item not found in the room.");
    }

    public List<Item> getInventory() {
        return inventory;
    }

    public List<String> getCompletedPuzzles() {
        return completedPuzzles;
    }

    public List<String> getDefeatedMonsters() {
        return defeatedMonsters;
    }

    public Item getEquippedItem() {
        return equippedItem;
    }

    public void inspectItem(String itemName) {
        for (Item item : inventory) {
            if (item.getName().equalsIgnoreCase(itemName)) {
                System.out.println("Inspecting item: " + item.getName());
                System.out.println(item.getDescription());
                return;
            }
        }
        System.out.println("Item not found in inventory.");
    }

    public void dropItem(String itemName) {
        for (Item item : inventory) {
            if (item.getName().equalsIgnoreCase(itemName)) {
                inventory.remove(item);
                currentRoom.addItem(item);
                System.out.println("Dropped: " + item.getName() + " and placed in " + currentRoom.getName() + " room.");
                return;
            }
        }
        System.out.println("Item not found in inventory.");
    }

    public Room getCurrentRoom() {
        return currentRoom;
    }

    public void handlePuzzle(String answer) {
        if (currentRoom.hasPuzzle()) {
            if (currentRoom.solvePuzzle(answer)) {
                completedPuzzles.add(currentRoom.getPuzzle().getDescription() + " - " + answer);
                currentRoom.addPuzzle(null);
                System.out.println("Correct! The puzzle is solved.");
            } else {
                int remainingAttempts = MAX_PUZZLE_ATTEMPTS - currentRoom.getPuzzleAttempts();
                if (remainingAttempts <= 0) {
                    System.out.println("You have reached the maximum number of attempts for this puzzle.");
                } else {
                    System.out.println("Incorrect answer. " + remainingAttempts + " attempts remaining. Try again.");
                }
            }
        } else {
            System.out.println("There is no puzzle in this room.");
        }
    }

    public boolean isAlive() {
        return this.health > 0;
    }

    public void attack(Monster monster) {
        int damage = calculateDamage();
        System.out.println("You attack the monster and deal " + damage + " damage!");
        monster.takeDamage(damage);
    }

    private int calculateDamage() {
        if (equippedItem != null && equippedItem.getAttackPoints() > 0) {
            return equippedItem.getAttackPoints();
        }
        return 10;
    }

    public void takeDamage(int damage) {
        this.health -= damage;
        System.out.println("You took " + damage + " damage! Health is now " + this.health);
        if (this.health < 0) {
            this.health = 0;
        }
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int newHealth) {
        this.health = newHealth;
    }

    public void equipItem(String itemName) {
        for (Item item : inventory) {
            if (item.getName().equalsIgnoreCase(itemName) && item.getAttackPoints() > 0) {
                equippedItem = item;
                System.out.println("Equipped " + item.getName() + ". Attack now uses " + item.getAttackPoints() + " damage.");
                return;
            }
        }
        System.out.println("No such equippable item in your inventory.");
    }

    public void unequipItem() {
        if (equippedItem != null) {
            System.out.println("Unequipped " + equippedItem.getName() + ".");
            equippedItem = null;
        } else {
            System.out.println("No item is currently equipped.");
        }
    }

    public void heal() {
        Item bestHealingItem = null;

        for (Item item : inventory) {
            if (item.getHealPoints() > 0) {
                if (bestHealingItem == null || item.getHealPoints() > bestHealingItem.getHealPoints()) {
                    bestHealingItem = item;
                }
            }
        }

        if (bestHealingItem != null) {
            health += bestHealingItem.getHealPoints();
            System.out.println("Healed with " + bestHealingItem.getName() + " for " + bestHealingItem.getHealPoints() + " HP.");
            System.out.println("Current health: " + health);
            inventory.remove(bestHealingItem);
        } else {
            System.out.println("No healing items available in inventory.");
        }
    }

    public void addDefeatedMonster(String monsterName) {
        defeatedMonsters.add(monsterName);
    }
}
