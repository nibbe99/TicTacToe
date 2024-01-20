package TicTacToe;

public enum CellState {

	COMPUTER("X"), USER("O"), EMPTY("-");
	
	private String text;
	private CellState(String text) {
		this.text = text;
	}
	
	public String toString() {
		return this.text;
	}

}
