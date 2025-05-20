/*
 * InvalidRoomException.java - Emree Jensen
 * 1st Individual Assignment - Text Based Adventure Game
 * 
 * Custom exception class for handling invalid room access in the game.
 *
 * Purpose:
 * - This exception is thrown when a player tries to access a room
 *   that does not exist or is not defined in the game's map.
 *
 * Key Features:
 * - Inherits from Java's built-in Exception class.
 * - Allows developers to provide a descriptive error message when the exception is thrown. 
 * 
*/
package model;

public class InvalidRoomException extends Exception {
    /*
     * Constructor for InvalidRoomException.
     * 
     * @param message - A descriptive message explaining why the exception was thrown.
     *                  This message is passed to the superclass (Exception).
     */
    public InvalidRoomException(String message) {
        super(message); // Call the Exception class constructor with the provided message
    }
} // End InvalidRoomException Class