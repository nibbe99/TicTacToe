package TicTacToe;
import java.util.*;
public class Board {
	
	private List<Cell> emptyCells;
	private Scanner scanner;
	private CellState [][] board;
	private List<Cell> rootValues;
	
	public Board() {
		initializeBoard();
	}

	private void initializeBoard() {
		this.rootValues = new ArrayList<>();
		this.scanner = new Scanner(System.in);
		this.board = new CellState[Constants.BOARD_SIZE][Constants.BOARD_SIZE];		// 3 * 3
	}
	
	public void setUpBoard() {
		System.out.println("ROW AND COLUMN");
		System.out.println("--------------");
		for(int i = 0; i < Constants.BOARD_SIZE; ++i) {
			System.out.println(i + " ");
			for(int j = 0; j < Constants.BOARD_SIZE; ++j) {
				board[i][j] = CellState.EMPTY;
			}
			
		}
		System.out.println();
		System.out.println("0 1 2");
	}

	
	public Scanner getScanner() {
		return scanner;
	}

	public void setScanner(Scanner scanner) {
		this.scanner = scanner;
	}

	public List<Cell> getRootValues() {
		return rootValues;
	}

	public void setRootValues(List<Cell> rootValues) {
		this.rootValues = rootValues;
	}

	public void setEmptyCells(List<Cell> emptyCells) {
		this.emptyCells = emptyCells;
	}

	public boolean isRunning() {
		if(isWinning(CellState.COMPUTER)) return false;
		if(isWinning(CellState.USER)) return false;
		if(getEmptyCells().isEmpty()) return false;
		return true;
	}
	
	public void makeUserInput() {
		System.out.println("User's move:");
		int x = scanner.nextInt();
		int y = scanner.nextInt();
		
		Cell cell = new Cell(x, y);
		move(cell, CellState.USER);
	}

	public boolean isWinning(CellState player) {
		if(board[0][0] == player && board[1][1] == player && board[2][2] == player) { 	   // diagonal
			return true;
		}
		else if(board[0][2] == player && board[1][1] == player && board[2][0] == player) { // diagonal
			return true;
		}
		
	
		for(int i = 0; i < Constants.BOARD_SIZE; ++i) {
			
			//CHECK ROWS
			if(board[i][0] == player && board[i][1] == player && board[i][2] == player) {
				return true;
			}
			//Check columns
			if(board[0][i] == player && board[1][i] == player && board[2][i] == player) {
				return true;
			}
			
		}
	
		return false;
	}

	private List<Cell> getEmptyCells() {
		emptyCells = new ArrayList<>();
		for(int i = 0; i < Constants.BOARD_SIZE; ++i) {
			for(int j = 0; j < Constants.BOARD_SIZE; ++j) {
				if(board[i][j] == CellState.EMPTY) {
					emptyCells.add(new Cell(i, j));
				}
			}
		}
		return emptyCells;
	}
	
	//find minimi item in a list (minimax algorithm)
	public int returnMin(List<Integer> list) {
		
		int min = Integer.MAX_VALUE;
		int index = Integer.MIN_VALUE;
		
		for(int i = 0; i < list.size(); i++) {
			if(list.get(i) < min) {
				min = list.get(i);
				index = i;
			}
		}
		return list.get(index);
	}
	
	//find maximum item in a list (minimax algorithm)
	public int returnMax(List<Integer> list) {
		
		int max = Integer.MIN_VALUE;
		int index = Integer.MIN_VALUE;
		
		for(int i = 0; i < list.size(); i++) {
			if(list.get(i) > max) {
				max = list.get(i);
				index = i;
			}
		}
		return list.get(index);	
	}
	
	
	public void move(Cell cell, CellState player) {
		this.board[cell.getX()][cell.getY()] = player;
	}

	public void displayBoard() {
		System.out.println();
		
		for(int i = 0; i < Constants.BOARD_SIZE; i++) {
			for(int j = 0; j < Constants.BOARD_SIZE; j++) {
				System.out.print(this.board[i][j] + " ");
			}
			System.out.println();
		}
		
	}

	public Cell getBestMove() {
		int max = Integer.MIN_VALUE;	//min max algo. Alpha	
		int best = Integer.MIN_VALUE;   //min max algo. Beta
		
		for(int i = 0; i < rootValues.size(); i++) {
			
			if(max < rootValues.get(i).getMinimaxValue()) {
				
				max = rootValues.get(i).getMinimaxValue();
				best = i;
			}
		}
		
		return rootValues.get(best);
	}

	public void callMinimax(int depth, CellState player) {
		rootValues.clear();
		minimax(depth, player);	//depth is how deep the algo goes till the root nodes
		
	}
	
	private int minimax(int depth, CellState player) {
		
		if(isWinning(CellState.COMPUTER)) {
			return +1;
		}
		if(isWinning(CellState.USER)) {
			return -1;
		}
		
		List<Cell> availableCells = getEmptyCells();
		
		if(availableCells.isEmpty()) {
			return 0;	//draw
		}
		
		List<Integer> scores = new ArrayList<>(); //the scores from a row (that belongs to same parent)
		
		for(int i = 0; i < availableCells.size(); i++) {
			
			Cell point = availableCells.get(i);
			
			// IF PLAYER IS COMPPUTER.  We move for computer and calculate next move for user
			if(player == CellState.COMPUTER) {
				move(point, CellState.COMPUTER);
				int currentScore = minimax(depth+1, CellState.USER);	//recursion.
				scores.add(currentScore);
				
				if(depth == 0) {
					point.setMinimaxValue(currentScore);
					rootValues.add(point);
				}
				
			}
			
			// IF PLAYER IS USER.  We move for user and calculate next move for computer
			else if(player == CellState.USER) {
				move(point, CellState.USER);
				scores.add(minimax(depth+1, CellState.COMPUTER));
			}
			
			board[point.getX()][point.getY()] = CellState.EMPTY;

		}
		//return value from internal nodes. Must find MAX value for the computer's turn and MIN value for the user's turn
		if(player == CellState.COMPUTER) {
			return returnMax(scores);
		}
		
		return returnMin(scores);

	}
}















































