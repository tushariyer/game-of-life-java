import java.util.*;
import java.math.*;
import java.awt.*;
import Myro.*;
/**
 * Class main contains all the methods necessary to run my simplified version of Conway's Game of Life.
 * 
 * @author Tushar Iyer
 * @version Version 1, Started on 10/15/2014
 */
public class Main 
{
    //Integer counter to keep track of generation. Accessible by all methods.
    private static int genCount = 2;
    /**
     * Method main runs playGame();
     *
     */
    public static void main()
    {
        playGame();
    }
    
    /**
     * Method playGame runs all the methods and displays the program in the console window
     *
     */
    public static void playGame()
    {
        //Info for the user
        message();
        //MyroGUI.tellUser("This program will quit after x generations.");
        int nosGens = MyroGUI.inputInteger("How many generations would you like " + 
                        "to allow the program to calculate");
        
        //Generate the first grid
        boolean[][] grid = generation();
        displayGrid(grid);
        
        //Line break
        System.out.println("\n");
        
        //equate the grid to the next generation and then display it
        grid = nextGeneration(grid);
        displayGrid(grid);
        
        //Scan the grid for length
        Scanner s = new Scanner(System.in);
        
        while(s.nextLine().length() == 0)
        {
            //Increment the coutner which holds the Gen. Number
            genCount++;
            
            //check with killProgram whether it is time to quit or not
            killProgram(nosGens);
            
            //Print out which generation is about to be calculated.
            System.out.println("\n" + "Generation " + genCount);
            
            //print the next generation
            grid = nextGeneration(grid);
            displayGrid(grid);
        }
    }

    /**
     * Method killProgram is a method that will stop the program after a certain amount of time
     *
     * This method was only created because the program will not end on its own. 
     */
    public static void killProgram(int until)
    {
        if (until == (genCount - 1))
        {
            //Tell user that it is time to quit
            MyroGUI.tellUser(until + " generations have been calculated. " +
                            "The program will now quit.");
                            
            //Exit
            System.exit(0);
        }
    }

    /**
     * Method message concatonates several strings and displays it to the user prior to running
     *                the program.
     *
     */
    public static void message()
    {
        //Create the message for the user
        String str, str2, str3;
        str = "Welcome to my version of the Game of life!";
        str2 = "\nThis program will calculate the first two generations of the game of Life.";
        str3 = "\nTo load the next generation, press 'enter'. To view the entire life of the game";
        str3 += ", press and hold enter!";
        
        //Concatenate all Strings and display it to the user
        String total = (str + str2 + str3);
        MyroGUI.tellUser(total);
    }

    /**
     * Method generation is a method which creates the 2D array
     *
     * @return The return value is a 2D array called grid
     */
    public static boolean[][] generation()
    {
        //Create the grid as a 30X30 map
        boolean[][] grid = new boolean[30][30];
        
        //Up till 30 values, create a row
        for(int x = 0; x < 30; x++)
        {
            //Up till 30 values, create a column
            for(int y = 0; y < 30; y++)
            {
                //If the randomly generated figure is greater than 0.7 (Decided value)
                if( Math.random() > 0.7 )
                {
                    grid[x][y] = true;
                }
            }
        }
        return grid;
    }

    /**
     * Method displayGrid looks at each cell and then gives it a dead(.) or alive(*) value
     *
     * @param grid A parameter which is the 2D array on which the program is displayed
     */
    public static void displayGrid(boolean[][] grid)
    {
        //Initialise a blank string to hold the 30X30 matrix
        String matrix = "";
        
        //For each row in the grid
        for(boolean[] row : grid)
        {
            //For each point in a row
            for(boolean var : row)
            {
                //Decide dead or alive
                if(var)
                {
                    //Alive
                    matrix += "*";
                }
                else
                {
                    //Dead
                    matrix += ".";
                }
            }
            
            //Create the grid with either * or .
            matrix += "\n";
        }
        
        //Print out the grid
        System.out.println(matrix);
    }

    /**
     * Method neighborCounter is a method which runs several conditions to count the number of
     *                        neighbors that exist for the selected cell
     *
     * @param grid A parameter which is the 2D array that we see in the console window
     * @param row A parameter which is an integer that takes us through each row
     * @param col A parameter which is an integer that takes us through each column. This int along
     *                        with the int row ends up going through each cell in the 30X30 array
     * @return The return value is an int which contains the number of neighbors. It is then passed on
     *                          to the method neighborCheck() to continue the process in determining
     *                          the next generation.
     */
    public static int neighborCounter(boolean[][] grid, int left, int down) 
    {
        //Ternary statement to check a particular cell
        int nCount = grid[left][down] ? -1 : 0;
        
        //For the surrounding rows
        for(int x = left - 1; x <= left + 1; x++)
        {
            //For the surrounding columns
            for(int y = down - 1; y <= down + 1; y++)
            {
                //Check to see if condition is true, then add to neighbor counter.
                if( boundsChecker(grid, x, y) && grid[x][y] )
                {
                    nCount++;
                }
            }
        }
        
        //Return the number of neighbors
        return nCount;
    }

    /**
     * Method neighborCheck is a method which selects each cell and goes through several conditions
     *                      to determine whether or not the cell should be dead or alive in the 
     *                      following generation
     *
     * @param nosNeighbors an integer which holds the number of neighbors for the selected cell
     * @param alive a boolean value which has two states: Alive and Dead
     * @return The return value is a boolean which decides the outcome of the selected cell in the
     *                          following generation
     */
    public static boolean neighborCheck(int nosNeighbors, boolean alive)
    {
        //If the cell is alive and has 2 or 3 neighbors
        if( alive && (nosNeighbors == 2 || nosNeighbors == 3))
        {
            //If this condition is met, let the point remain alive
            return true;
        }
        //If the cell is dead but has three neighbors
        else if (!alive && nosNeighbors == 3)
        {
            //If this condition is met, let the point remain alive
            return true;
        }
        //For any other condition that the cell is in:
        else
        {
            //Default to dead cell
            return false;
        }
    }

    /**
     * Method boundsChecker is a bounds checking method that makes sure that the program and all its
     *                      methods remain within the alotted boundaries. 
     *
     * @param map A parameter which is the 2D array printed in the console window
     * @param x A parameter which is an int which represents all the cells in the x-plane of the array
     * @param y A parameter which is an int that represents cells when going through the y-plane
     *                      of the array
     * @return The return value is a boolean that tells us the following:
     *                              - Whether or not x is greater or equal to 0
     *                              - Whether or not x is smaller than the length of the map (30)
     *                              - Whether or not y is greater or equal to 0
     *                              - Whether or not y is smaller than the map length at index 0
     */
    public static boolean boundsChecker(boolean[][] map, int x, int y) 
    {
        //Check for other cases.
        boolean check = ((x >= 0) && (x < map.length) && (y >= 0) && (y < map[0].length));
        return check;
    }

    /**
     * Method nextGeneration is a method that uses the data collected in neighborCounter, 
     *                          neighborCheck & boundsChecker to create the grid for the 
     *                          next generation
     *
     * @param map A parameter which is a 2D array which encompasses the whole map
     * @return The return value is the updated grid which will be displayed to the user upon 
     *                          pressing enter
     */
    public static boolean[][] nextGeneration(boolean[][] map)
    {
        //declarations
        int numSurround;
        boolean[][] nextGrid = new boolean[map.length][map[0].length];
        
        //For all the values in the x plane of the map
        for(int x = 0; x < map.length; x++)
        {
            //For all the values in the y plane of the map
            for(int y = 0; y < map[0].length; y++)
            {
                //retrieve the number of neighbors of the cell at coordinates (x,y)
                numSurround = neighborCounter(map, x, y);
                
                //Run the check to see how many neighbors exist
                if( neighborCheck(numSurround, map[x][y]) )
                {
                    nextGrid[x][y] = true;
                }
            }
        }
        
        //Update the grid
        return nextGrid;
    }
}