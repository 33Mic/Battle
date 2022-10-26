public class Player extends ConsoleProgram
{
    // These are the lengths of all of the ships.
    private static final int[] SHIP_LENGTHS = {2, 3, 3, 4, 5};
    
    private Ship[] fleet;
    
    
    // On the player’s grid, they will mark the location of their ships,
    // as well as record the guesses of their opponent.
    public Grid playerGrid;
    // On the opponents grid, the player will record their own guesses of
    // where they think the battleships are.
    public Grid opponentGrid; 
    private int numShips = 0; // counter for ships placed
    
    public Player()
    {
        fleet = new Ship[SHIP_LENGTHS.length];
        for(int i = 0; i < SHIP_LENGTHS.length; i++) // Set up all the ships in the fleet
        {
            fleet[i] = new Ship(SHIP_LENGTHS[i]);
        }
        
        playerGrid = new Grid(); // Create an instance of the Grid, where the player will mark the location of THEIR ships, as well as the guesses of their opponents
        opponentGrid = new Grid(); // The player will record their own guesses of where they think the battleships are.
    }
    
    //This will set a ship’s row, column and direction and add it to the current player’s grid.
    //You can use this to hard code in testing placement of battleships.
    
    public void chooseShipLocation(int shipnum, int row, int col, int direction)
    {
        Ship s = fleet[shipnum];
        if(numShips < 5)
        {
            // the ship's length is already set up
            // set up the ship's row
            // set up the ship's column
            s.setLocation(row, col);
            
            // set up the ship's direction
            s.setDirection(direction);
            // add the ship to the current player's grid
            playerGrid.addShip(s);
            numShips++;
        }
    }
    
    public boolean verifyShipLocation(int shipnum, int row, int col, int direction)
    {
        //we're going to check with horizontal and vertical
        int HORIZONTAL = 0;
        int VERTICAL = 1;
        
        int length = SHIP_LENGTHS[shipnum];
        try
        {
            if(direction == HORIZONTAL) // check horizontally
            {
                for(int horizontalPoint = col; horizontalPoint < (col + length); horizontalPoint++)
                {
                    playerGrid.get(row, horizontalPoint);
                    // readLine("hallo!!");
                    if(playerGrid.hasShip(row, horizontalPoint)) // If there's a ship, we can't place it there.
                    {
                        return false;
                    }
                }
            }
            
            if(direction == VERTICAL) // check vertically
            {
                for(int verticalPoint = row; verticalPoint < (row + length); verticalPoint++)
                {
                    playerGrid.get(verticalPoint, col);
                    // readLine("hallo!!");
                    if(playerGrid.hasShip(verticalPoint, col)) // If there's a ship, we can't place it there.
                    {
                        return false;
                    }
                }
            }
            
            
        }
        
        catch(Exception ArrayIndexOutOfBoundsException)
        {
            System.out.println("Returning false");
            //readLine("Enter to continue");
            return false;
        }
        System.out.println("Returning true");
        //readLine("Enter to continue");
        return true; // otherwise it'll just return true if all of it works
    }
    
    // Print your ships on the grid
    public void printMyShips()
    {
        playerGrid.printShips();
    }
    
    // Print opponent guesses
    public void printOpponentGuesses()
    {
        playerGrid.printStatus();
    }
    
    // Print your guesses
    public void printMyGuesses()
    {
        opponentGrid.printStatus();
    }
    
    // Record a guess from the opponent
    public boolean recordOpponentGuess(int row, int col)
    {
        boolean hasShipLocation = playerGrid.hasShip(row, col);
        if(hasShipLocation) // If it hit
        {
            playerGrid.markHit(row, col);
            return true;
        }
        else // If it missed
        {
            playerGrid.markMiss(row, col);
            return false;
        }
    }
    
    // Record a guess from the player
    public boolean recordPlayerGuess(int row, int col)
    {
        boolean hasShipLocation = opponentGrid.hasShip(row, col);
        if(hasShipLocation) // If it hit
        {
            opponentGrid.markHit(row, col);
            return true;
        }
        else // If it missed
        {
            opponentGrid.markMiss(row, col);
            return false;
        }
    }
    
    public boolean checkWin()
    {
        return playerGrid.checkWin();
    }
}