package tp.pr1;

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
	 * Realiza el movimiento característico del 2048 hacia la derecha
	 * 
	 * @param auxBoard Es el tablero sobre el que se aplica el movimiento
	 * @return un objeto MoveResults con los resultados de aplicar el movimiento
	 */
	private MoveResults move(Board auxBoard) {
		boolean moved = false;
		int points = 0;
		int max = 0;
		
		for(int i = auxBoard.boardSize-1; i < auxBoard.boardSize; i++) {
			Position right = new Position(i, auxBoard.boardSize-1);
			Cell rightCell = auxBoard.board[right.getRow()][right.getColumn()];
					
			for(int j = auxBoard.boardSize-2; j >= 0; j--) {
				Position pos = new Position(i, j);
				Cell posCell = auxBoard.board[pos.getRow()][pos.getColumn()];
				
				if(max < rightCell.getValue())
					max = rightCell.getValue();
				else if(max < posCell.getValue())
					max = posCell.getValue();
				
				if(rightCell.isEmpty()) {
					setCell(right, posCell.getValue());
					setCell(pos, 0);
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
							if(right.neighbour(Direction.LEFT, auxBoard.boardSize) != pos) {
								setCell(right.neighbour(Direction.LEFT, auxBoard.boardSize), posCell.getValue());
								setCell(pos, 0);
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
		String space = " ";
		String vDelimiter = "|";
		String hDelimiter = "-";
		
		// Barra superior
		for(int i = 0; i < boardSize; i++)
			ret = MyStringUtils.repeat(hDelimiter, (cellSize+2)*boardSize);
		
		ret += "\n";
		
		for(int i = 0; i < boardSize; i++) {
			ret += vDelimiter;
			
			for(int j = 0; j < boardSize; j++) {
				ret += MyStringUtils.centre(String.valueOf(board[i][j].getValue()), cellSize);
				ret += vDelimiter;
			}
			
			ret += "\n";
			ret += MyStringUtils.repeat(hDelimiter, (cellSize+2)*boardSize);
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
		Position[] res = emptyCells();
		int index = 0;
		Position pos;
		
		index = random.nextInt(res.length);
		pos = res[index];
		
		setCell(pos, value);
	}
	
	/**
	 * Recorre el tablero buscando las posiciones vacías y almacenadolas en un array
	 * de Positions
	 * 
	 * @return Un array de Positions dónde las celdas están vacías
	 */
	private Position[] emptyCells(){
		Position[] res = null;
		
		for(int i = 0; i < boardSize; i++) {
			for(int j = 0; j < boardSize; j++) {
				if(board[i][j].isEmpty())
					res[res.length] = new Position(i, j);
			}
		}
		
		return res;
	}
}
