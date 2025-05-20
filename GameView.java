/*
 * The GameView class is responsible for handling user interactions 
 * and displaying information in the game. It provides methods to display messages, 
 * the player's inventory, and completed puzzles. It also prompts the user
 *  for input and displays a help message containing the available commands.

Here's a quick overview of the methods:

displayMessage(String message): Prints a message to the console.
displayInventory(Player player): Displays the contents of the player's inventory and completed puzzles.
getUserInput(): Prompts the user for input and returns the entered command.
displayHelp(): Displays a list of available commands to the player.

 * */

package view;

import model.Player;
import model.Item;
import javax.swing.*;
import java.awt.*;

import java.util.Scanner;

public class GameView {
    private Scanner scanner;

    public GameView() {
        scanner = new Scanner(System.in);
    }

    public void displayMessage(String message) {
        System.out.println(message);
    }

    public void displayInventoryPopup(Player player) {
        JTextArea textArea = new JTextArea();
        StringBuilder sb = new StringBuilder();

        sb.append("Inventory:\n");
        if (player.getInventory().isEmpty()) {
            sb.append("  - No items collected.\n");
        } else {
            for (Item item : player.getInventory()) {
                sb.append("  - ").append(item.getName()).append("\n");
            }
        }

        sb.append("\nCompleted Puzzles:\n");
        if (player.getCompletedPuzzles().isEmpty()) {
            sb.append("  - None\n");
        } else {
            for (String puzzle : player.getCompletedPuzzles()) {
                sb.append("  - ").append(puzzle).append("\n");
            }
        }

        sb.append("\nDefeated Monsters:\n");
        if (player.getDefeatedMonsters().isEmpty()) {
            sb.append("  - None\n");
        } else {
            for (String monster : player.getDefeatedMonsters()) {
                sb.append("  - ").append(monster).append("\n");
            }
        }

        sb.append("\nPlayer Stats:\n");
        sb.append("  - Health: ").append(player.getHealth()).append("\n");
        Item equipped = player.getEquippedItem();
        sb.append("  - Equipped: ").append(equipped != null ? equipped.getName() : "None").append("\n");

        textArea.setText(sb.toString());
        textArea.setEditable(false);
        textArea.setFont(new Font("Georgia", Font.PLAIN, 15));
        textArea.setBackground(new Color(122, 95, 140)); // Dark background
        textArea.setForeground(new Color(220, 220, 220)); // Light text

        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(500, 400));

        JFrame frame = new JFrame("Alice's Inventory");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.getContentPane().add(scrollPane);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public String getUserInput() {
        System.out.print("Enter command: ");
        return scanner.nextLine().trim();
    }

    public void displayHelpWindow() {
        JTextPane textPane = new JTextPane();
        textPane.setContentType("text/html");

        textPane.setText(
            "<html><body style='font-family:Georgia,serif; font-size:13px; background-color:#111; color:#eee; padding:10px;'>" +
            "<h2 style='color:#ba55d3; text-align:center;'>🖤 Wonderland Commands 🖤</h2>" +
            "<p style='color:#ccc; text-align:center;'>Welcome, Alice ... Here is how you navigate the madness:</p>" +
            "<ul style='list-style-type:square;'>" +
            "<li><b style='color:#7fffd4;'>explore</b>: Search the shadows of the current room.</li>" +
            "<li><b style='color:#7fffd4;'>pickup [item]</b>: Claim an item hidden in the dark.</li>" +
            "<li><b style='color:#7fffd4;'>drop [item]</b>: Leave an item behind...perhaps forever.</li>" +
            "<li><b style='color:#7fffd4;'>inspect [item]</b>: Reveal the secrets of a twisted artifact.</li>" +
            "<li><b style='color:#7fffd4;'>inventory</b>: View what you’ve gathered from Wonderland’s wreckage.</li>" +
            "<li><b style='color:#7fffd4;'>equip [item]</b>: Arm yourself for battle.</li>" +
            "<li><b style='color:#7fffd4;'>unequip</b>: Set your weapon aside...for now.</li>" +
            "<li><b style='color:#7fffd4;'>heal</b>: Use a magical item to regain your strength.</li>" +
            "<li><b style='color:#7fffd4;'>examine monster</b>: Confront a creature of nightmares.</li>" +
            "<li><b style='color:#7fffd4;'>solve [answer]</b>: Attempt to crack a riddle of madness.</li>" +
            "<li><b style='color:#ff4500;'>n / e / s / w</b>: Navigate through twisted paths.</li>" +
            "<li><b style='color:#ff6347;'>exit</b>: Wake up...if you still can.</li>" +
            "</ul>" +
            "<p style='text-align:center; color:#999;'>\"Wonderland has become quite strange. How is one to find her way?\"</p>" +
            "</body></html>"
        );

        textPane.setEditable(false);

        JScrollPane scrollPane = new JScrollPane(textPane);
        scrollPane.setPreferredSize(new Dimension(540, 360));
        scrollPane.getViewport().setBackground(Color.decode("#111"));

        JFrame frame = new JFrame("Wonderland Help");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.getContentPane().add(scrollPane);
        frame.getContentPane().setBackground(Color.decode("#111"));
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

}