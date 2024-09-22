// -------------------------------------------------------------------------
/**
 * Main for Graph project (CS3114/CS5040 Fall 2023 Project 4).
 * Usage: java GraphProject <init-hash-size> <command-file>
 *
 * @author Maanasa Ramakrishnan (maanasar) & Marie Muya
 * @version 2024
 *
 */

// On my honor:
// - I have not used source code obtained from another current or
// former student, or any other unauthorized source, either
// modified or unmodified.
//
// - All source code and documentation used in my program is
// either my original work, or was derived by me from the
// source code published in the textbook for this course.
//
// - I have not discussed coding details about this project with
// anyone other than my partner (in the case of a joint
// submission), instructor, ACM/UPE tutors or the TAs assigned
// to this course. I understand that I may discuss the concepts
// of this program with others students, and that another student
// may help me debug my program so long as neither of us writes
// anything during the discussion or modifies any computer file
// during the discussion. I have violated neither the spirit nor
// letter of this restriction.

/**
 * GraphProject class
 *
 * @author Maanasa Ramakrishnan (maanasar)
 *         Marie Muya (mariem26)
 * @version 2024.09.20
*/

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class GraphProject {
    public static void main(String[] args) {
        // Check if the program received exactly two command-line arguments
        if (args.length != 2) {
            System.out.println("Usage: java GraphProject <initHashSize> <commandFile>");
            return; // Exit if the arguments are incorrect
        }
        // Parse the initial hash size from the first argument
        int initHashSize = Integer.parseInt(args[0]);
        // Get the path to the command file from the second argument
        String commandFile = args[1];

        // Initialize the Controller with the graph and initial hash size
        Controller controller = new Controller(new Graph(initHashSize), initHashSize);
        // Create a CommandProcessor instance to handle commands
        CommandProcessor commandProcessor = new CommandProcessor(controller);

        // Try to read the commands from the provided file
        try (BufferedReader br = new BufferedReader(new FileReader(commandFile))) {
            String line;
            // Read each line from the command file and process it
            while ((line = br.readLine()) != null) {
                commandProcessor.processCommand(line); // Process the command
            }
        } catch (IOException e) {
            // Handle any errors that occur while reading the file
            System.err.println("Error reading command file: " + e.getMessage());
        }
    }
}
