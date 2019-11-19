import java.io.Serializable;

/**
 * Connect4MoveMessage contains the data to be sent and received to the server
 * and client to be used for the game and its updates. Each instance contains a row,
 * column, and color value to be used by the board when a move is made as well as
 * some static variables to be used by all instances of the class. 
 * 
 * @author Chris Crabtree
 * @author Luke Cernetic
 *
 */

public class Connect4MoveMessage implements Serializable {
	
       public static int YELLOW = 1;
       public static int RED = 2;

       private static final long serialVersionUID = 1L;

       private int row;
       private int col;
       private int color;

       /**
        * Constructor for the Connect4MoveMessage. Simply sets its own row, col,
        * and color values to the values given as parameters. 
        * 
        * @param row
        * @param col
        * @param color
        */
       public Connect4MoveMessage(int row, int col, int color) {
    	   this.row = row;
    	   this.col = col;
    	   this.color = color;
       }

       /**
        * Returns the row field of the  instance.
        * 
        * @return an int describing the row to be changed.
        */
       public int getRow() {
    	   return row;
       }

       /**
        * Returns the column of the  instance.
        * 
        * @return an int describing the column to be changed.
        */
       public int getColumn() {
    	   return col;
       }

       /**
        * Returns the color of the instance.
        * 
        * @return an int describing the color of the move being made.
        */
       public int getColor() {
    	   return color;
       }

}