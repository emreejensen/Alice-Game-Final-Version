/*
 * The Item class is a simple data container for items in the game. 
 * It provides a constructor to initialize the item's name and description, 
 * and getter methods to retrieve these values.
 * */

package model;

public class Item {
    private String name;
    private String description;
    private int attackPoints; // Add attack points for equippable items
    private int healPoints;   // Add heal points for healing items

    // Constructor to initialize the item with name, description, attackPoints, and healPoints
    public Item(String name, String description, int attackPoints, int healPoints) {
        this.name = name;
        this.description = description;
        this.attackPoints = attackPoints;
        this.healPoints = healPoints;
    }

    // Getter methods
    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getAttackPoints() {
        return attackPoints;
    }

    public int getHealPoints() {
        return healPoints;
    }
}