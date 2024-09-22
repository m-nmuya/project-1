/**
* CommandProcessor class
*
* @author Maanasa Ramakrishnan (maanasar)
*         Marie Muya (mariem26)
* @version 2024.09.20
*/

public class CommandProcessor {
   private final Controller controller; // The Controller instance used to process commands

   // Constructor that initializes the CommandProcessor with a Controller
   public CommandProcessor(Controller controller) {
       this.controller = controller;
   }

   // Method to process a given command string
   public void processCommand(String command) {
       // Split the command into action and data, separated by the first space
       String[] parts = command.split(" ", 2);
       
       // If the command does not contain at least action and data, it's invalid
       if (parts.length < 2) {
           System.out.println("Invalid command format: " + command);
           return;
       }

       String action = parts[0]; // The action part of the command (e.g., "insert", "remove", "print")
       String data = parts[1]; // The data part of the command

       // Handle the command based on the action
       switch (action) {
           case "insert":
               // Split the data into artist and song using ":" as the separator
               String[] insertParts = data.split("<SEP>", 2);
               // Check if the data is correctly formatted (artist:song)
               if (insertParts.length == 2) {
                //System.out.println("INSERT: ARTIST: " + insertParts[0] + "\tSONG:" + insertParts[1]);
                   // Call the controller's insert method with artist and song
                   controller.insert(insertParts[0], insertParts[1]);
               } else {
                   System.out.println("Invalid insert command format: " + command);
               }
               break;

           case "remove":
               // Split the data to determine whether to remove an artist or a song
               String[] removeParts = data.split(" ", 2);
               if (removeParts.length == 2) {
                   // Check if the user wants to remove a song
                   if (removeParts[0].equalsIgnoreCase("song")) {
                       // Call the controller's removeSong method with the song name
                       controller.removeSong(removeParts[1]);
                   } 
                   // Check if the user wants to remove an artist
                   else if (removeParts[0].equalsIgnoreCase("artist")) {
                       // Call the controller's removeArtist method with the artist name
                       controller.removeArtist(removeParts[1]);
                   } 
                   // Invalid remove command format
                   else {
                       System.out.println("Invalid remove command format: " + command);
                   }
               } else {
                   System.out.println("Invalid remove command format: " + command);
               }
               break;

           case "print":
               // Handle different print commands (print song, artist, or graph)
               if (data.equalsIgnoreCase("song")) {
                   controller.printSongs(); // Print all songs
               } else if (data.equalsIgnoreCase("artist")) {
                   controller.printArtists(); // Print all artists
               } else if (data.equalsIgnoreCase("graph")) {
                   controller.printGraph(); // Print the entire graph (relationships)
               } 
               // Invalid print command format
               else {
                   System.out.println("Invalid print command format: " + command);
               }
               break;

           // Handle unknown commands
           default:
               System.out.println("Unknown command: " + command);
               break;
       }
   }
}
