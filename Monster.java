package model;

public class Monster {
    private String name;
    private String description;
    private int health;
    private int attackPower;
    private int defense;
    private String itemDrop;  // Optional item that the monster might drop

    // Constructor
    public Monster(String name, String description, int health, int attackPower, int defense, String itemDrop) {
        this.name = name;
        this.description = description;
        this.health = health;
        this.attackPower = attackPower;
        this.defense = defense;
        this.itemDrop = itemDrop;
    }

    // Getters
    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getHealth() {
        return health;
    }

    public int getAttackPower() {
        return attackPower;
    }

    public int getDefense() {
        return defense;
    }

    public String getItemDrop() {
        return itemDrop;
    }

    // Setters (optional, in case you want to modify a monster's properties)
    public void setHealth(int health) {
        this.health = health;
    }

    public void setAttackPower(int attackPower) {
        this.attackPower = attackPower;
    }

    public void setDefense(int defense) {
        this.defense = defense;
    }

    public void setItemDrop(String itemDrop) {
        this.itemDrop = itemDrop;
    }

    // Monster attack method (simplified)
    public int attack() {
        return this.attackPower;
    }

    // Method to reduce health when the monster takes damage
    public void takeDamage(int damage) {
        int actualDamage = Math.max(damage - this.defense, 1); // Always deal at least 1
        this.health -= actualDamage;
    }

    // Check if the monster is dead
    public boolean isDead() {
        return this.health <= 0;
    }
    
    // Threshold getter (used for preview damage logic in examine)
    public int getThreshold() {
        return this.defense; // Using 'defense' as the threshold field
    }

    // Attack damage getter
    public int getAttackDamage() {
        return this.attackPower;
    }

}