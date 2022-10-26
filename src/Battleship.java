/*
COMPLETEDD AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA
    
    For my extra features, here is one things you can do:

    -[COMPLETED] have a "select random ship placement" option for the player. This is literally the easiest thing to implement.

*/

import java.util.*;

public class Battleship extends ConsoleProgram
{
    private static final int[] SHIP_LENGTHS = {2, 3, 3, 4, 5};
    // create two players
    Player[] players = new Player[2];
    Player curTurn;
    public void run()
    {
        // setup
        players[0] = new Player(); // actual player
        players[1] = new Player(); // computer
        curTurn = players[0];
        Player playerThatWon;
        
        // This will setup all the ships
        placeAllShips(curTurn);
        
        curTurn.printMyShips();
        readLine("Here are your ships... enter to continue");
        clearScreen();
        
        decideTurn();
        placeAllShips(curTurn);
        clearScreen();
        
        curTurn.printMyShips(); // FOR DEBUGGING PURPOSES
        
        decideTurn(); // Player's turn
        System.out.println("\nPlayer gets first try!\n");
        
        
        // This will run the actual game
        while(true)
        {
            // printBoards();
            // askForGuess(curTurn);
            // clearScreen();
            
            // printBoards();
            // readLine("Enter to continue: ");
            // clearScreen();
            if(curTurn == players[0])
            {
                playerMove();
            }
            else if(curTurn == players[1])
            {
                opponentMove();
            }
            
            if(checkWin(curTurn))
            {
                // whatever happens if the opponent wins
                // System.out.println(checkWin(curTurn));
                // readLine("Someone won");
                playerThatWon = curTurn;
                break; // get out of that while loop!
            }
            
            decideTurn();
        }
        if(playerThatWon == players[1]) // If the user wins
        {
            System.out.println("Congrats! You win!");
        }
        else if(playerThatWon == players[0]) // If the computer wins
        {
            System.out.println("Game over! The computer won.");
        }
        
        // prompt to play again
        int playAgain;
        do
        {
            playAgain = readInt("Would you like to play again? (0 = No / 1 = Yes): ");
        } while(playAgain != 0 && playAgain != 1);
        if(playAgain == 1)
        {
            clearScreen();
            run();
        }
        System.out.println("Thank you for playing my game!");
    }
    
    private Player decideTurn()
    {
        if(curTurn == players[0])
        {
            curTurn = players[1];
        }
        else
        {
            curTurn = players[0];
        }
        return curTurn;
    }
    
    private void playerMove() // will use this after I fix the code
    {
        printBoards();
        askForGuess(curTurn);
        clearScreen();
        
        printBoards();
        //readLine("Enter to continue: "); // FOR DEBUGGING PURPOSES
        clearScreen();
        //decideTurn();
    }
    
    private void opponentMove()
    {
        askForGuess(curTurn);
        clearScreen();
        //decideTurn();
    }
    
    
    private void placeAllShips(Player player) // usually just use curTurn
    {   
        int autoPlacePrompt = -1;
        ArrayList<String> alpha = new ArrayList<String>(Arrays.asList("A", "B", "C", "D", "E", "F", "G", "H", "I", "J")); 
        System.out.println("Start placing your ships!");
        for(int shipNum = 0; shipNum < 5; shipNum++)
        {
            curTurn.printMyShips();
            System.out.println("Ship " + (shipNum + 1) + " of length " + SHIP_LENGTHS[shipNum] + " will be placed.\n\n");
            if(curTurn == players[0]) // if it's the player
            {
                while(autoPlacePrompt != 0 && autoPlacePrompt != 1)
                {
                    autoPlacePrompt = readInt("Would you like to have the ships automatically placed? (0 = No / 1 = Yes): ");
                    System.out.println(autoPlacePrompt);
                }
                
                if(autoPlacePrompt == 1)
                {
                    readLine("Ship " + (shipNum + 1) + " will be automatically placed. Press enter to continue... ");
                    autoPlaceShip(shipNum); // a single ship!
                    continue;
                }
                int askDirection = -1;
                do
                {
                    askDirection = readInt("Please enter a valid direction (Horizontal = 0/Vertical = 1): ");
                } while(askDirection != 0 && askDirection != 1);
                
                int askCol = -1;
                String askRow = "bruh";
                int askRowAsNum = -1;
                while((askCol <= -1 || askCol > Grid.NUM_COLS) || (askRowAsNum <= -1 || askRowAsNum > Grid.NUM_COLS))
                {    // if(askDirection == 0)
                    // {
                    askRow = readLine("Please enter a valid row (A-J): ").toUpperCase();
                    askRowAsNum = alpha.indexOf(askRow);
                    if(alpha.contains(askRow))
                    {
                        askRowAsNum = alpha.indexOf(askRow);
                    }
                    askCol = readInt("Please enter a valid column (1-10): ") - 1;
                    if(curTurn.verifyShipLocation(shipNum, askRowAsNum, askCol, askDirection))
                    {
                        // System.out.println("did you just skip me");
                        curTurn.chooseShipLocation(shipNum, askRowAsNum, askCol, askDirection);
                        clearScreen();
                    }
                    else // if we fail the verifyShipLocation check
                    {
                        askCol = -1;
                        askRow = "bruh";
                        askRowAsNum = -1;
                    }

                }
            }
            if(curTurn == players[1]) // first try
            { //autoplaceall
                // int askDirection = Randomizer.nextInt(2);
                // int askCol = -1;
                // int askRowAsNum = -1;
                
                // while((askCol <= -1 || askCol > Grid.NUM_COLS) || (askRowAsNum <= -1 || askRowAsNum > Grid.NUM_COLS))
                // {    
                //     askRowAsNum = Randomizer.nextInt(10);
                //     askCol = Randomizer.nextInt(10);
                //     if(curTurn.verifyShipLocation(shipNum, askRowAsNum, askCol, askDirection))
                //     {
                //         // System.out.println("did you just skip me");
                //         curTurn.chooseShipLocation(shipNum, askRowAsNum, askCol, askDirection);
                //         clearScreen();
                //     }
                //     else // if we fail the verifyShipLocation check
                //     {
                //         System.out.println("Please Re-enter all of your values, but correctly this time.");
                //         askCol = -1; // reset the values
                //         askRowAsNum = -1;
                //     }
                // }
                autoPlaceShip(shipNum);
            }
            System.out.println("Ship " + (shipNum + 1) + " has been placed.");
            
        }
    }
    
    private void autoPlaceShip(int shipNum)
    {
        int askDirection = Randomizer.nextInt(2);
        int askCol = -1;
        int askRowAsNum = -1;
        
        while((askCol <= -1 || askCol > Grid.NUM_COLS) || (askRowAsNum <= -1 || askRowAsNum > Grid.NUM_COLS))
        {    
            askRowAsNum = Randomizer.nextInt(10);
            askCol = Randomizer.nextInt(10);
            if(curTurn.verifyShipLocation(shipNum, askRowAsNum, askCol, askDirection))
            {
                // System.out.println("did you just skip me");
                curTurn.chooseShipLocation(shipNum, askRowAsNum, askCol, askDirection);
                clearScreen();
            }
            else // if we fail the verifyShipLocation check
            {
                System.out.println("Please Re-enter all of your values, but correctly this time.");
                askCol = -1; // reset the values
                askRowAsNum = -1;
            }
        }
    }
    
    private void printBoards()
    {
        if(curTurn == players[0])
        {
            System.out.println("Player's perspective");
        }
        else
        {
            System.out.println("Computer's perspective");
        }
        // print out the current board status that they have
        System.out.println("Player's Board: ");
        curTurn.printOpponentGuesses(); // print the opponent's guesses on the player board but I might need to print my own ships too
        System.out.println("\n");
        System.out.println("Opponent's Board: ");
        //curTurn.printMyGuesses(); // print the player's guesses on the opponent's board
        decideTurn(); // switch to see opponent's board
        curTurn.printOpponentGuesses();  // an alternative to the above comment.
        decideTurn(); // switch back to your thing
    }
    
    private void askForGuess(Player player)
    {
        while(true)
        {
            ArrayList<String> alpha = new ArrayList<String>(Arrays.asList("A", "B", "C", "D", "E", "F", "G", "H", "I", "J")); 
            
            int askCol = -1;
            String askRow = "bruh";
            int askRowAsNum = -1;
            if(curTurn == players[0])
            {
                askRow = readLine("Please enter a valid row (A-J): ").toUpperCase();
                // System.out.println(alpha.contains(askRow)); // this is unneeded, I just check stuff
                while(!alpha.contains(askRow)) // If it doesn't contain a value from the alphabet
                {
                    askRow = readLine("Please enter a valid row (A-J): ").toUpperCase();
                }
                askRowAsNum = alpha.indexOf(askRow);
                
                askCol = readInt("Please enter a valid column (1-10): ") - 1;
                while(askCol < -1 || askCol > Grid.NUM_COLS)
                {
                    askCol = readInt("Please make sure you enter a valid column (1-10): ");
                }
            }        
            else if(curTurn == players[1])
            {
                askRowAsNum = Randomizer.nextInt(10);
                askCol = Randomizer.nextInt(10);
            }
            try
            {
            player.recordPlayerGuess(askRowAsNum, askCol);
            decideTurn(); // switch out in order to print the result on the opponent's side
            curTurn.recordOpponentGuess(askRowAsNum, askCol);
            decideTurn(); // switch back to original, it's as if nothing actually happened.
            System.out.println("\n");
            }
            catch(Exception ArrayIndexOutOfBoundsException)
            {
                continue;
            }
            break;
        }
    }
    
    public boolean checkWin(Player player)
    {
        if(player.checkWin())  // if the player wins
        {
            return true;
        }
        return false; // player does not win
    }
    
    public static void clearScreen() 
    {  
        System.out.print("\033[H\033[2J");  
        System.out.flush();  
    }  
}