package tp.pr1;

/**
 * @author Sergio Ulloa
 */
public class Board {
	
	// ================================================================================
	// Atributos
	// ================================================================================

	private Cell[][] board; // Array bidimensional de celdas
	private int boardSize; 	// Tamaño del tablero (dimensión)
	
	// ================================================================================
	// Constructores
	// ================================================================================
	
	public Board(int boardSize) {
		this.boardSize = boardSize;
		
		for(int i = 0; i < boardSize; i++) {
			for(int j = 0; j < boardSize; j++) 
				this.board[i][j] = new Cell(0);
		}
	}
	
	// ================================================================================
	// Métodos
	// ================================================================================
	
	/** Modifica el valor de una celda en la posición pos con el valor value
	 * 
	 * @param pos Posición de la celda
	 * @param value Nuevo valor para la celda
	 */
	public void setCell(Position pos, int value) {
		int row = pos.getRow();
		int column = pos.getColumn();
		
		board[row][column].setValue(value);
	}
	
	/**Dependiendo del movimiento realiza transposiciones o reflejos del tablero en
	 * auxBoard, para así reducir el código repetido. Luego llama al método move para
	 * después rellenar el objeto MoveResults con la información del movimiento
	 * 
	 * @param dir Dirección del movimiento
	 * @return Un objeto MoveResults que contiene el resultado del movimiento
	 */
	public MoveResults executeMove(Direction dir) {
		Board auxBoard = new Board(boardSize);
		boolean moved = false;
		
		switch (dir) {
			case UP:
			{
				for(int i = 0; i < boardSize; i++) {
					for(int j = 0; j < boardSize; i++) {
						auxBoard.board[(boardSize-1)-j][i] = new Cell(board[i][j].getValue());
					}
				}
			}
				break;
			case DOWN:
			{
				for(int i = 0; i < boardSize; i++) {
					for(int j = 0; j < boardSize; i++) {
						auxBoard.board[j][(boardSize-1)-i] = new Cell(board[i][j].getValue());
					}
				}
			}
				break;
			case LEFT:
			{
				for(int i = 0; i < boardSize; i++) {
					for(int j = 0; j < boardSize; i++) {
						auxBoard.board[(boardSize-1)-i][j] = new Cell(board[i][j].getValue());
					}
				}
			}
				break;
			case RIGHT:
				break;	
		}
		
		MoveResults ret = move(auxBoard);
		return ret;
	}
	
	/**
	 * @param auxBoard
	 * @return
	 */
	private MoveResults move(Board auxBoard) {
		MoveResults moved = new MoveResults(false, 0, 0);
		
		
		
		return moved;
	}
}
