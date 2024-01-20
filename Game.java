package TicTacToe;

import java.util.Random;

public class Game {
	
	private Board board;
	private Random random;	//first move for computer always random
	
	public Game() {
		initializeGame();
		displayBoard();
		makeFirstMove();
		playGame();
		checkStatus();
	}
	
	
	private void initializeGame() {
		this.board = new Board();
		this.random = new Random();
		this.board.setUpBoard();
	}


	private void displayBoard() {
		this.board.displayBoard();
	}


	private void playGame() {
		while(board.isRunning()) {
			System.out.print("Enter row: ");
			int x = board.getScanner().nextInt();
			System.out.print("enter column: ");
			int y = board.getScanner().nextInt();

			Cell userCell = new Cell(x, y);	//get x and y kordinate
			
			board.move(userCell, CellState.USER);
			board.displayBoard();
			
			if(!board.isRunning()) {
				break;
			}
			board.callMinimax(0, CellState.COMPUTER);
			
			/*
			for(Cell cell : board.getRootValues()) {
				System.out.println("Cell values: " + cell + "minimaxValue: " + cell.getMinimaxValue());
			}
			*/
			board.move(board.getBestMove(), CellState.COMPUTER);
			board.displayBoard();
		}
	}
	
	private void checkStatus() {
		if(board.isWinning(CellState.COMPUTER)) {
			System.out.println("Computer won!");
		}
		else if(board.isWinning(CellState.USER)) {
			System.out.println("The user has won!");
		}
		else {
			System.out.println("Draw!");
		}
	}
	
	private void makeFirstMove() {
		System.out.println("Who starts?");
		System.out.println("Press 1 - for USER\nPress 2 - for COMPUTER");
		int choice = board.getScanner().nextInt();
		
		if(choice == 2) {
			Cell cell = new Cell(random.nextInt(Constants.BOARD_SIZE), random.nextInt(Constants.BOARD_SIZE));
			board.move(cell, CellState.COMPUTER);
			board.displayBoard();
		}
	}

}








