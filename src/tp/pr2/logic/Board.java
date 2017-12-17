package tp.pr2.logic;

import java.util.Random;

import tp.util.MyStringUtils;

/**
 * @author Sergio Ulloa
 */
public class Board {
	
	// ================================================================================
	// Atributos
	// ================================================================================

	private Cell[][] board; // Array bidimensional de celdas
	private int boardSize; 	// Tamaño del tablero (dimensión)
	private boolean full;	// Indica si el tablero está lleno
	
	// ================================================================================
	// Constructores
	// ================================================================================
	
	public Board(int boardSize) {
		this.boardSize = boardSize;
		this.board = new Cell[boardSize][boardSize];
		this.full = false;
		
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
		MoveResults ret = new MoveResults(false, 0, 0);
		
		switch (dir) {
			case UP:
			{
				for(int i = 0; i < boardSize; i++) {
					for(int j = 0; j < boardSize; j++) {
						auxBoard.board[j][(boardSize-1)-i] = new Cell(board[i][j].getValue());
					}
				}
				ret = move(auxBoard);
				for(int i = 0; i < boardSize; i++) {
					for(int j = 0; j < boardSize; j++) {
						board[(boardSize-1)-j][i] = auxBoard.board[i][j];
					}
				}
			}
				break;
			case DOWN:
			{
				for(int i = 0; i < boardSize; i++) {
					for(int j = 0; j < boardSize; j++) {
						auxBoard.board[(boardSize-1)-j][i] = new Cell(board[i][j].getValue());
					}
				}
				ret = move(auxBoard);
				for(int i = 0; i < boardSize; i++) {
					for(int j = 0; j < boardSize; j++) {
						board[j][(boardSize-1)-i] = auxBoard.board[i][j];
					}
				}
			}
				break;
			case LEFT:
			{
				for(int i = 0; i < boardSize; i++) {
					for(int j = 0; j < boardSize; j++) {
						auxBoard.board[i][(boardSize-1)-j] = new Cell(board[i][j].getValue());
					}
				}
				ret = move(auxBoard);
				for(int i = 0; i < boardSize; i++) {
					for(int j = 0; j < boardSize; j++) {
						board[i][(boardSize-1)-j] = auxBoard.board[i][j];
					}
				}
			}
				break;
			case RIGHT:
			{
				ret = move(this);
			}
				break;	
		}
		
		return ret;
	}
	
	/**
	 * Realiza el movimiento característico del 2048 hacia la derecha
	 * 
	 * @param auxBoard Es el tablero sobre el que se aplica el movimiento
	 * @return un objeto MoveResults con los resultados de aplicar el movimiento
	 */
	private MoveResults move(Board auxBoard) {
		boolean moved = false;
		int points = 0;
		int max = 0;
		
		for(int i = 0; i < auxBoard.boardSize; i++) {
			Position right = new Position(i, auxBoard.boardSize-1);
			Cell rightCell = auxBoard.board[right.getRow()][right.getColumn()];
					
			for(int j = auxBoard.boardSize-2; j >= 0; j--) {
				Position pos = new Position(i, j);
				Cell posCell = auxBoard.board[pos.getRow()][pos.getColumn()];
				
				if(max < rightCell.getValue())
					max = rightCell.getValue();
				else if(max < posCell.getValue())
					max = posCell.getValue();
				
				if(rightCell.isEmpty() && posCell.getValue() != 0) {
					auxBoard.setCell(right, posCell.getValue());
					auxBoard.setCell(pos, 0);
					moved = true;
				}
				
				else {
					if(!posCell.isEmpty()) {
						if(rightCell.doMerge(posCell)) {
							moved = true;
							points += rightCell.getValue();
							if(max < rightCell.getValue())
								max = rightCell.getValue();
						}
						
						else {
							Position auxPos = right.neighbour(Direction.LEFT, auxBoard.boardSize);
							if(!auxPos.equals(pos)) {
								auxBoard.setCell(right.neighbour(Direction.LEFT, auxBoard.boardSize), posCell.getValue());
								auxBoard.setCell(pos, 0);
								moved = true;
							}
						}
													
						right = right.neighbour(Direction.LEFT, auxBoard.boardSize);
						rightCell = auxBoard.board[right.getRow()][right.getColumn()];
					}
				}
			}
		}
		
		return new MoveResults(moved, points, max);
	}
	
	public String toString() {
		String ret = "";
		int cellSize = 7;
		String vDelimiter = "|";
		String hDelimiter = "-";
		
		// Barra superior
		for(int i = 0; i < boardSize; i++)
			ret = MyStringUtils.repeat(" " + MyStringUtils.repeat(hDelimiter, cellSize), boardSize);
		
		ret += "\n";
		
		for(int i = 0; i < boardSize; i++) {
			ret += vDelimiter;
			
			for(int j = 0; j < boardSize; j++) {
				ret += MyStringUtils.centre(String.valueOf(board[i][j].getValue()), cellSize);
				ret += vDelimiter;
			}
			
			ret += "\n";
			ret += MyStringUtils.repeat(" " + MyStringUtils.repeat(hDelimiter, cellSize), boardSize);
			ret += "\n";
		}
		
		return ret;
	}
	
	/**
	 * Busca aleatoriamente y establece una nueva celda con el valor dado.
	 * Sirve para crear la nueva ficha al final de un movimiento
	 * 
	 * @param value Valor de la nueva celda
	 * @param random Aleatoriedad
	 */
	public void newCell(int value, Random random) {
		Position[] positions = new Position[boardSize*boardSize]; 
		int cont = emptyCells(positions);
		int index = 0;
		Position pos;
		
		if(cont <= 0) full = true;
		else {
			index = random.nextInt(cont);
			pos = positions[index];
			setCell(pos, value);
		}
	}
	
	/**
	 * Recorre el tablero buscando las posiciones vacías y almacenadolas en un array
	 * de Positions
	 * 
	 * @param positions Array donde se almacenarán las posiciones libres.
	 * @return Devuelve un int con el número de posiciones libres.
	 */
	public int emptyCells(Position[] positions){
		int cont = 0;
		
		for(int i = 0; i < boardSize; i++) {
			for(int j = 0; j < boardSize; j++) {
				if(board[i][j].isEmpty()) {
					positions[cont] = new Position(i, j);
					cont++;
				}
			}
		}
		
		if(cont > 1) full = false;
		
		return cont;
	}

	/**
	 * Indica si el tablero está lleno.
	 * 
	 * @return Devuelve un booleano. Board.full
	 */
	public boolean isFull() {
		return full;
	}
	
	/**
	 * Hace una copia del tablero que en lugar de estar formada por Cell, está
	 * formado por int.
	 * 
	 * @return Devuelve el estado actual del tablero.
	 */
	public int[][] getState() {
		int boardState[][] = new int[boardSize][boardSize];
		
		for(int i = 0; i < boardSize; i++) {
			for(int j = 0; j < boardSize; j++) {
				boardState[i][j] = board[i][j].getValue();
			}
		}
		
		return boardState;
	}
	
	/**
	 * Pone el tablero en el estado definido por aState.
	 * 
	 * @param aState Estado del tablero que se quiere recuperar.
	 */
	public void setState(int[][] aState) {
		for(int i = 0; i < boardSize; i++) {
			for(int j = 0; j < boardSize; j++) {
				board[i][j].setValue(aState[i][j]);
			}
		}
	}
	
	/**
	 * Comprueba si se puede realizar algún movimiento en el tablero.
	 * 
	 * @return 	En caso de que se pueda realizar movimiento devuelve true. Devuelve 
	 * 			false en caso contrario.
	 */
	public boolean canMove() {
		boolean ret = false;
		
		if(!isFull()) ret = true;
		else {
			for(int i = 0; i < boardSize && ret == false; i++) {
				for(int j = 0; j < boardSize && ret == false; j++) {
					if(j < boardSize-1) {
						if(board[i][j].getValue() == board[i][j+1].getValue())
							ret = true;
					}
					
					if(i < boardSize-1) {
						if(board[i][j].getValue() == board[i+1][j].getValue())
							ret = true;
					}
				}
			}
		}
		
		return ret;
	}
	
	// ================================================================================
	// Getters y Setters
	// ================================================================================
	
	public int getBoardSize() {
		return this.boardSize;
	}
	
}
