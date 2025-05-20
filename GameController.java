package controller;

import model.Player;
import model.Item;
import model.Monster;
import model.InvalidRoomException;
import view.GameView;

public class GameController {
    private Player player;
    private GameView view;

    public GameController(Player player, GameView view) {
        this.player = player;
        this.view = view;
    }

    public void startGame() {
        while (true) {
            view.displayMessage("");
            view.displayMessage("Alice wanders into the " + player.getCurrentRoomName() + "...");
            view.displayMessage(player.getCurrentRoomDescription());
            view.displayMessage("");

            if (player.getCurrentRoom().hasPuzzle() && player.getCurrentRoom().getPuzzleAttempts() < Player.MAX_PUZZLE_ATTEMPTS) {
                view.displayMessage("There is a puzzle in this room!");
                view.displayMessage(player.getCurrentRoom().getPuzzle().getDescription());
            } else if (player.getCurrentRoom().hasPuzzle()) {
                view.displayMessage("You have reached the maximum number of attempts for this puzzle.");
            }

            view.displayMessage("Type 'help' for a list of available commands.");
            String input = view.getUserInput();

            if (input.equalsIgnoreCase("exit")) {
                view.displayMessage("");
                view.displayMessage("Alice drifts out of Wonderland and returns to the real world...");
                view.displayMessage("(Exiting Game)");
                break;
            }

            if (input.equalsIgnoreCase("explore")) {
                player.explore();
                continue;
            }

            if (input.equalsIgnoreCase("inventory")) {
                view.displayInventoryPopup(player);
                continue;
            }

            if (input.equalsIgnoreCase("help")) {
                view.displayHelpWindow();
                continue;
            }

            if (input.startsWith("pickup ")) {
                String itemName = input.substring(7).trim();
                player.pickUpItem(itemName);
                continue;
            }

            if (input.startsWith("drop ")) {
                String itemName = input.substring(5).trim();
                player.dropItem(itemName);
                continue;
            }

            if (input.startsWith("inspect ")) {
                String itemName = input.substring(8).trim();
                player.inspectItem(itemName);
                continue;
            }

            if (input.startsWith("solve ")) {
                String answer = input.substring(6).trim();
                player.handlePuzzle(answer);
                continue;
            }

            if (input.startsWith("equip ")) {
                String itemName = input.substring(6).trim();
                player.equipItem(itemName);
                continue;
            }

            if (input.equalsIgnoreCase("unequip")) {
                player.unequipItem();
                continue;
            }

            if (input.equalsIgnoreCase("heal")) {
                player.heal();
                continue;
            }

            if (input.equalsIgnoreCase("examine monster")) {
                Monster monster = player.getCurrentRoom().getMonster();
                if (monster != null && !monster.isDead()) {
                    view.displayMessage("You examine the monster...");
                    view.displayMessage(monster.getDescription());

                    int roll = (int) (Math.random() * 100);
                    int previewDamage = (roll < monster.getThreshold())
                            ? monster.getAttackDamage() * 2
                            : monster.getAttackDamage();

                    view.displayMessage("It deals " + previewDamage + " damage!");
                    view.displayMessage("Do you want to 'attack' or 'ignore'?");
                    String nextAction = view.getUserInput();

                    if (nextAction.equalsIgnoreCase("attack")) {
                        fightMonster(monster);
                    } else if (nextAction.equalsIgnoreCase("ignore")) {
                        view.displayMessage("You choose to ignore the monster. It vanishes into the shadows.");
                        player.getCurrentRoom().removeMonster();
                    } else {
                        view.displayMessage("Invalid choice. The monster continues to watch you.");
                    }
                } else {
                    view.displayMessage("There is no monster here to examine.");
                }
                continue;
            }

            if (input.startsWith("fight")) {
                Monster monster = player.getCurrentRoom().getMonster();
                if (monster != null && !monster.isDead()) {
                    fightMonster(monster);
                } else {
                    view.displayMessage("There is no living monster here to fight.");
                }
                continue;
            }

            String nextRoomID = player.getNextRoomID(input);

            if (nextRoomID == null) {
                view.displayMessage("");
                view.displayMessage("Invalid direction. Please enter North, East, South, West, or exit.");
                continue;
            }

            try {
                player.moveTo(nextRoomID);
            } catch (InvalidRoomException e) {
                view.displayMessage(e.getMessage());
            }
        }
    }

    private void fightMonster(Monster monster) {
        view.displayMessage("You encountered a " + monster.getName() + "!");

        while (!monster.isDead() && player.isAlive()) {
            view.displayMessage("Your HP: " + player.getHealth() + " | Monster HP: " + monster.getHealth());
            view.displayMessage("Choose: attack | heal | equip [item] | unequip");
            String command = view.getUserInput();

            if (command.equalsIgnoreCase("attack")) {
                player.attack(monster);
                view.displayMessage("You strike the " + monster.getName() + "!");
            } else if (command.equalsIgnoreCase("heal")) {
                player.heal();
            } else if (command.startsWith("equip ")) {
                String itemName = command.substring(6).trim();
                player.equipItem(itemName);
            } else if (command.equalsIgnoreCase("unequip")) {
                player.unequipItem();
            } else {
                view.displayMessage("Invalid combat command.");
                continue;
            }

            if (monster.isDead()) {
                view.displayMessage("The " + monster.getName() + " is dead!");
                if (monster.getItemDrop() != null) {
                    String itemName = monster.getItemDrop();
                    view.displayMessage("You found a " + itemName + "!");
                    Item item = new Item(itemName, "Dropped by a monster", 0, 0);
                    player.getCurrentRoom().addItem(item);
                }
                player.getCurrentRoom().removeMonster();
                break;
            }

            int monsterDamage = monster.attack();
            player.takeDamage(monsterDamage);
            view.displayMessage("The " + monster.getName() + " hits you for " + monsterDamage + " damage!");
        }

        if (!player.isAlive()) {
            view.displayMessage("You have been defeated!");
            view.displayMessage("Type 'restart' to start over, or 'exit' to quit the game.");

            while (true) {
                String choice = view.getUserInput();

                if (choice.equalsIgnoreCase("exit")) {
                    view.displayMessage("Goodbye, Alice...");
                    System.exit(0);
                } else if (choice.equalsIgnoreCase("restart")) {
                    try {
                        this.player = new Player("map.txt", "item.txt", "puzzle.txt", "monsters.txt");
                        view.displayMessage("Game restarted. Welcome back to Wonderland.");
                    } catch (Exception e) {
                        view.displayMessage("Failed to restart the game.");
                        e.printStackTrace();
                        System.exit(1);
                    }
                    break;
                } else {
                    view.displayMessage("Invalid input. Type 'restart' or 'exit'.");
                }
            }
        }
    }
}
