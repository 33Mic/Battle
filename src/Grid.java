public class Grid
{
    // Write your Grid class here
    private Location[][] grid;
    
    // Constants for number of rows and columns.
    public static final int NUM_ROWS = 10;
    public static final int NUM_COLS = 10;
    public static final String[] alphabet = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J"};
    
    // Create a new Grid. Initialize each Location in the grid
    // to be a new Location object.
    public Grid()
    {
        grid = new Location[NUM_ROWS][NUM_COLS];
        
        for(int row = 0; row < grid.length; row++)
        {
            for(int col = 0; col < grid[0].length; col++)
            {
                grid[row][col] = new Location();
            }
        }
    }
    
    // Mark a hit in this location by calling the markHit method
    // on the Location object.
    public void markHit(int row, int col)
    {
        grid[row][col].markHit();
    }
    
    // Mark a miss on this location.    
    public void markMiss(int row, int col)
    {
        grid[row][col].markMiss();
    }
    
    // Set the status of this location object.
    public void setStatus(int row, int col, int status)
    {
        grid[row][col].setStatus(status);
    }
    
    // Get the status of this location in the grid  
    public int getStatus(int row, int col)
    {
        return grid[row][col].getStatus();
    }
    
    // Return whether or not this Location has already been guessed.
    public boolean alreadyGuessed(int row, int col)
    {
        if(grid[row][col].isUnguessed()) // If that location is unguessed
        {
            return false;
        }
        return true;
    }
    
    // Set whether or not there is a ship at this location to the val   
    public void setShip(int row, int col, boolean val)
    {
        grid[row][col].setShip(val);
    }
    
    // Return whether or not there is a ship here   
    public boolean hasShip(int row, int col)
    {
        return grid[row][col].hasShip();
    }
    
    
    // Get the Location object at this row and column position
    public Location get(int row, int col)
    {
        return grid[row][col];
    }
    
    // Return the number of rows in the Grid
    public int numRows()
    {
        return NUM_ROWS;
    }
    
    // Return the number of columns in the grid
    public int numCols()
    {
        return NUM_COLS;
    }
    
    
    // Print the Grid status including a header at the top
    // that shows the columns 1-10 as well as letters across
    // the side for A-J
    // If there is no guess print a -
    // If it was a miss print a O
    // If it was a hit, print an X
    // A sample print out would look something like this:
    // 
    //   1 2 3 4 5 6 7 8 9 10 
    // A - - - - - - - - - - 
    // B - - - - - - - - - - 
    // C - - - O - - - - - - 
    // D - O - - - - - - - - 
    // E - X - - - - - - - - 
    // F - X - - - - - - - - 
    // G - X - - - - - - - - 
    // H - O - - - - - - - - 
    // I - - - - - - - - - - 
    // J - - - - - - - - - - 
    public void printStatus()
    {
        // String[] alphabet = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J"};
        System.out.print(" ");
        for(int i = 1; i <= 10; i++)
        {
            System.out.print(" " + i);
        }
        System.out.println();
        
        for(int row = 0; row < grid.length; row++)
        {
            System.out.print(alphabet[row] + " ");
            for(Location point: grid[row])
            {
                if(point.isUnguessed())
                {
                    System.out.print("- ");
                }
                else
                {
                    if(point.checkHit())
                    {
                        System.out.print("X ");
                    }
                    else if(point.checkMiss())
                    {
                        System.out.print("O ");
                    }
                }
            }
            System.out.println();
        }
    }

    
    // Print the grid and whether there is a ship at each location.
    // If there is no ship, you will print a - and if there is a
    // ship you will print a X. You can find out if there was a ship
    // by calling the hasShip method.
    //
    //   1 2 3 4 5 6 7 8 9 10 
    // A - - - - - - - - - - 
    // B - X - - - - - - - - 
    // C - X - - - - - - - - 
    // D - - - - - - - - - - 
    // E X X X - - - - - - - 
    // F - - - - - - - - - - 
    // G - - - - - - - - - - 
    // H - - - X X X X - X - 
    // I - - - - - - - - X - 
    // J - - - - - - - - X - 
    public void printShips()
    {
        // String[] alphabet = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J"};
        System.out.print(" ");
        for(int i = 1; i <= 10; i++)
        {
            System.out.print(" " + i);
        }
        System.out.println();
        
        for(int row = 0; row < grid.length; row++)
        {
            System.out.print(alphabet[row] + " ");
            for(Location point: grid[row])
            {
                if(point.hasShip())
                {
                    System.out.print("X ");
                }
                else
                {
                    System.out.print("- ");
                }
            }
            System.out.println();
        }
    }
    
    /**
     * This method can be called on your own grid. To add a ship
     * we will go to the ships location and mark a true value
     * in every location that the ship takes up.
     */
    public void addShip(Ship s)
    {
        int HORIZONTAL = 0;
        int VERTICAL = 1;
        
        int row = s.getRow();
        int col = s.getCol();
        int length = s.getLength();
        int direction = s.getDirection();
        
        if(direction == HORIZONTAL)
        {
            for(int horizontalPoint = col; horizontalPoint < (col + length); horizontalPoint++)
            {
                setShip(row, horizontalPoint, true);
            }
        }
        
        if(direction == VERTICAL)
        {
            for(int verticalPoint = row; verticalPoint < (row + length); verticalPoint++)
            {
                setShip(verticalPoint, col, true);
            }
        }
    }
    
    public boolean checkWin()
    {
        int shipHitCount = 0;
        for(int row = 0; row < NUM_ROWS; row++)
        {
            for(int col = 0; col < NUM_COLS; col++)
            {
                Location gridPoint = get(row, col);
                if(gridPoint.checkHit()) // hasShip(row, col)
                {
                    shipHitCount++;
                }
            }
        }
        if(shipHitCount == 17)
        {
            return true;
        }
        return false;
    }
}