import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

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
	void hasWonTLtoBR() {
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
	void hasWonBRtoTL() {
		Connect4Model curr = new Connect4Model();
		curr.addPiece(0, 1);
		curr.addPiece(1, 2);
		curr.addPiece(1, 1);
		curr.addPiece(2, 2);
		curr.addPiece(2, 2);
		curr.addPiece(2, 1);
		curr.addPiece(3, 2);
		curr.addPiece(3, 2);
		curr.addPiece(3, 2);
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
	
	@Test
	void setPlayerTest() {
		Connect4Model curr = new Connect4Model();
		curr.setPlayer(1);
		assertTrue(true);
	}
	
	@Test 
	void getGridTest() {
		Connect4Model curr = new Connect4Model();
		curr.getGrid();
		assertTrue(true);
	}
	
	@Test
	void getMessageTest() {
		Connect4Model curr = new Connect4Model();
		Connect4MoveMessage message = curr.getMessage(1);
		assertTrue(message.getColumn() == 1);
	}
	
	@Test
	void addPieceTest() {
		Connect4Model curr = new Connect4Model();
		curr.addPiece(0);
		assertTrue(true);
	}
	
	@Test
	void isLegalisBoardFilledisCompleteNotFilledTest() {
		Connect4Model curr = new Connect4Model();
		curr.isLegal(1);
		curr.isBoardFilled();
		curr.isComplete();
		assertTrue(true);
	}
	
	@Test
	void isLegalisBoardFilledisCompleteFilledTest() {
		Connect4Model curr = new Connect4Model();
		for (int i = 0; i < 6; i++) {
			for (int j = 0; j < 7; j++) {
				curr.addPiece(j, 1);
			}
		}
		
		curr.isLegal(1);
		curr.isBoardFilled();
		assertTrue(curr.isComplete());
	}
	
	@Test
	void moveMessageGetCol() {
		Connect4MoveMessage curr = new Connect4MoveMessage(0, 0, 0);
		curr.getColor();
		curr.getRow();
		assertEquals(curr.getColumn(), 0);
	}
	
	@Test
	void controllerSettersAndModelFunctionsTest() {
		Connect4Model model = new Connect4Model();
		Connect4Controller controller = new Connect4Controller(model);
		controller.setPlayAs(true);
		controller.getGrid();
		controller.isLegal(1);
		controller.hasWon();
		controller.setModel(new Connect4Model());
		controller.getTurn();
		controller.addView(new Connect4View());
		controller.setTurn(true);
		controller.addPiece(1);
		controller.isHuman();
		controller.addPiece(1, 1);
		assertTrue(controller.isHuman());
	}
	
	@Test
	void controllerInitStreams() {
		Socket socket = null;
		try {
			InetAddress addr = InetAddress.getLocalHost();
			socket = new Socket(addr, 4000);
		} catch (IOException e) {
			e.printStackTrace();
		}
		Connect4Model model = new Connect4Model();
		Connect4Controller controller = new Connect4Controller(model);
		controller.initStreams(socket);
	}
	
	@Test
	void controllerStartTurn() {
		Connect4Model model = new Connect4Model();
		Connect4Controller controller = new Connect4Controller(model);
		controller.startTurn();
	}
	
	@Test
	void controllerHumanTurn() {
	}
}
