import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class Connect4Tests {

	@Test
	void PlaceManyPieces() {
		Connect4Model curr = new Connect4Model();
		curr.addPiece(0, 1);
		curr.addPiece(1, 1);
		curr.addPiece(1, 1);
		curr.addPiece(2, 1);
		curr.addPiece(2, 1);
		curr.addPiece(2, 1);
		curr.addPiece(0, 2);
		curr.addPiece(1, 2);
		curr.addPiece(1, 2);
		curr.addPiece(2, 2);
		curr.addPiece(2, 2);
		curr.addPiece(2, 2);
		assertEquals(curr.toString(), 
				"-------------------------\n" + 
				"|  0  0  2  0  0  0  0  |\n" + 
				"|  0  0  2  0  0  0  0  |\n" + 
				"|  0  2  2  0  0  0  0  |\n" + 
				"|  0  2  1  0  0  0  0  |\n" + 
				"|  2  1  1  0  0  0  0  |\n" + 
				"|  1  1  1  0  0  0  0  |\n" + 
				"-------------------------");
	}
	
	@Test
	void hasWonVertical() {
		Connect4Model curr = new Connect4Model();
		curr.addPiece(1, 1);
		curr.addPiece(1, 1);
		curr.addPiece(1, 1);
		curr.addPiece(1, 1);
		assertEquals(curr.hasWon(), 1);
	}
	
	@Test
	void hasWonVertical2() {
		Connect4Model curr = new Connect4Model();
		curr.addPiece(1, 2);
		curr.addPiece(1, 1);
		curr.addPiece(1, 1);
		curr.addPiece(1, 1);
		curr.addPiece(1, 1);
		assertEquals(curr.hasWon(), 1);
	}
	
	@Test
	void hasWonHorizontal() {
		Connect4Model curr = new Connect4Model();
		curr.addPiece(0, 1);
		curr.addPiece(1, 1);
		curr.addPiece(2, 1);
		curr.addPiece(3, 1);
		assertEquals(curr.hasWon(), 1);
	}
	
	@Test
	void hasWonHorizontal2() {
		Connect4Model curr = new Connect4Model();
		curr.addPiece(0, 2);
		curr.addPiece(1, 1);
		curr.addPiece(2, 1);
		curr.addPiece(3, 1);
		curr.addPiece(4, 1);
		assertEquals(curr.hasWon(), 1);
	}
	
	@Test
	void hasWonBLtoTR() {
		Connect4Model curr = new Connect4Model();
		curr.addPiece(0, 2);
		curr.addPiece(0, 2);
		curr.addPiece(0, 2);
		curr.addPiece(1, 2);
		curr.addPiece(1, 2);
		curr.addPiece(2, 2);
		curr.addPiece(0, 1);
		curr.addPiece(1, 1);
		curr.addPiece(2, 1);
		curr.addPiece(3, 1);
		assertEquals(curr.hasWon(), 1);
	}
	
	@Test
	void noWins() {
		Connect4Model curr = new Connect4Model();
		curr.addPiece(0, 2);
		curr.addPiece(0, 2);
		curr.addPiece(0, 2);
		curr.addPiece(1, 2);
		curr.addPiece(1, 2);
		curr.addPiece(2, 2);
		curr.addPiece(0, 1);
		curr.addPiece(1, 1);
		curr.addPiece(2, 1);
		assertEquals(curr.hasWon(), 0);
	}
}
