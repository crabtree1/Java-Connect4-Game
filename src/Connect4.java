import javafx.application.Application;

/**
 * The begining class of the connect4 game. 
 * As per the project description:
 * 
 * "Connect 4 is a tic-tac-toe based two-player game. In the physical game, there are 
 * two colors of tokens, yellow for the first player and red for the second. Each player 
 * takes turns dropping a token into an upright board which only allows for dropping your 
 * token on top of column (as in a stack), reducing the freedom on each turn from that of 
 * traditional tic tac toe. As the name implies, you win when you create a connected line 
 * of 4 of your tokens, either horizontally, vertically, or diagonally. Games can end in a 
 * tie if neither player makes a line of 4 connected tokens."
 * 
 * @author Chris Crabtree
 * @author Luke Cernetic
 *
 */
public class Connect4  {
	
	public static void main(String[] args) {
		Application.launch(Connect4View.class, args);
	}
	
}
