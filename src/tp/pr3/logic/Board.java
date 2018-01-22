package tp.pr3.logic;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;

import tp.pr3.exceptions.SaveFormatException;
import tp.pr3.logic.multigames.GameRules;
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
	private int maxValue;	// Máximo valor del tablero
	private int minValue;	// Mínimo valor del tablero
	
	// ================================================================================
	// Constructores
	// ================================================================================
	
	public Board(int boardSize) {
		this.boardSize = boardSize;
		this.board = new Cell[boardSize][boardSize];
		this.full = false;
		
		for(int i = 0; i < boardSize; i++) {
			for(int j = 0; j < boardSize; j++) {
				this.board[i][j] = new Cell(0);
//				if((i+j)%2 == 0) this.board[i][j] = new Cell(2);
//				else this.board[i][j] = new Cell(4);
			}
		}
		
//		board[2][1].setValue(16);
//		board[2][2].setValue(16);
//		board[2][0].setValue(64);
		
//		board[0][1].setValue(1024);
//		board[1][0].setValue(512);
//		board[1][1].setValue(512);
	}
	
	// ================================================================================
	// Métodos
	// ================================================================================
	
	/**
	 * Modifica el valor de una celda en la posición pos con el valor value
	 * 
	 * @param pos Posición de la celda
	 * @param value Nuevo valor para la celda
	 */
	public void setCell(Position pos, int value) {
		int row = pos.getRow();
		int column = pos.getColumn();
		
		board[row][column].setValue(value);
		
		if(value > maxValue) maxValue = value;
		if((value < minValue && value != 0) || minValue == 0) minValue = value;
	}
	
	/**
	 * Dependiendo del movimiento realiza transposiciones o reflejos del tablero en
	 * auxBoard, para así reducir el código repetido. Luego llama al método move para
	 * después rellenar el objeto MoveResults con la información del movimiento
	 * 
	 * @param dir Dirección del movimiento
	 * @return Un objeto MoveResults que contiene el resultado del movimiento
	 */
	public MoveResults executeMove(Direction dir, GameRules rules) {
		Board auxBoard = new Board(boardSize);
		MoveResults ret = new MoveResults(false, 0);
		
		switch (dir) {
			case UP:
			{
				for(int i = 0; i < boardSize; i++) {
					for(int j = 0; j < boardSize; j++) {
						auxBoard.board[j][(boardSize-1)-i] = new Cell(board[i][j].getValue());
					}
				}
				ret = move(auxBoard, rules);
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
				ret = move(auxBoard, rules);
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
				ret = move(auxBoard, rules);
				for(int i = 0; i < boardSize; i++) {
					for(int j = 0; j < boardSize; j++) {
						board[i][(boardSize-1)-j] = auxBoard.board[i][j];
					}
				}
			}
				break;
			case RIGHT:
			{
				ret = move(this, rules);
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
	private MoveResults move(Board auxBoard, GameRules rules) {
		boolean moved = false;
		int points = 0;
		int movPoints = 0;
		
		for(int i = 0; i < auxBoard.boardSize; i++) {
			Position right = new Position(i, auxBoard.boardSize-1);
			Cell rightCell = auxBoard.board[right.getRow()][right.getColumn()];
					
			for(int j = auxBoard.boardSize-2; j >= 0; j--) {
				Position pos = new Position(i, j);
				Cell posCell = auxBoard.board[pos.getRow()][pos.getColumn()];
				
				if(rightCell.isEmpty() && posCell.getValue() != 0) {
					auxBoard.setCell(right, posCell.getValue());
					auxBoard.setCell(pos, 0);
					moved = true;
				}
				
				else {
					if(!posCell.isEmpty()) {
						movPoints = rightCell.doMerge(posCell, rules);
						if(movPoints != 0) {
							moved = true;
							points += movPoints;
							if(rightCell.getValue() > maxValue) maxValue = rightCell.getValue();
							if(rightCell.getValue() < minValue) minValue = rightCell.getValue();
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
		
		return new MoveResults(moved, points);
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
		else full = true;
		
		return cont;
	}

	/**
	 * Indica si el tablero está lleno.
	 * 
	 * @return Devuelve un booleano. Board.full
	 */
	private boolean isFull() {
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
	public boolean canMove(GameRules rules) {
		boolean ret = false;
		
		if(!isFull()) ret = true;
		else {
			for(int i = 0; i < boardSize && ret == false; i++) {
				for(int j = 0; j < boardSize && ret == false; j++) {
					if(j < boardSize-1) {
						Cell self = new Cell(board[i][j].getValue());
						Cell other = new Cell(board[i][j+1].getValue());
						if(self.doMerge(other, rules) != 0)
							ret = true;
					}
					
					if(i < boardSize-1) {
						Cell self = new Cell(board[i][j].getValue());
						Cell other = new Cell(board[i+1][j].getValue());
						if(self.doMerge(other, rules) != 0)
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
	
	/**
	 * Busca el máximo valor existente en el tablero.
	 * 
	 * @return Devuelve un int con el máximo valor.
	 */
	public int getMaxValue() {		
		return maxValue;
	}
	
	/**
	 * Busca el mínimo valor existente en el tablero.
	 * 
	 * @return Devuelve un int con el mínimo valor.
	 */
	public int getMinValue() {		
		return minValue;
	}
	
	/**
	 * Devuelve el tamaño actual del tablero
	 * 
	 * @return Devuelve Board.boardSize.
	 */
	public int getBoardSize() {
		return this.boardSize;
	}

	/**
	 * Método que almacena en archivo los valores que hay en el tablero
	 * 
	 * @param out Buffer donde se escribirán los datos
	 * @throws IOException En el caso de que se produzca un error en la escritura
	 */
	public void store(BufferedWriter out) throws IOException {
		for(int i = 0; i < boardSize; i++) {
			for(int j = 0; j < boardSize; j++) {
				out.write(board[i][j].toString());
				if(j < boardSize-1) out.write("\t");
			}
			
			out.newLine();
		}
	}

	/**
	 * Método que carga desde archivo los valores existentes
	 * 
	 * @param in Buffer desde donde se leen los datos
	 * @throws IOException En el caso de que se produzca un error en la lectura
	 * @throws SaveFormatException 
	 */
	public void load(BufferedReader in) throws IOException, SaveFormatException {
		String line = in.readLine();
		if (line == null) throw new SaveFormatException("Load failed: invalid file format");
		
		String cells[] = line.split("\t");
		
		minValue = Integer.valueOf(cells[0]);
		maxValue = minValue;
		
		boardSize = cells.length;
		
		for(int i = 0; i < boardSize; i++) {
			for(int j = 0; j < boardSize; j++) {
				try {
					board[i][j].setValue(Integer.valueOf(cells[j]));
				} catch (NumberFormatException e) {
					throw new SaveFormatException("Load failed: invalid file format");
				}
			}
			
			if(i < boardSize-1) {
				line = in.readLine();
				if (line == null) throw new SaveFormatException("Load failed: invalid file format");
				cells = line.split("\t");
			}
		}
		
		full = true;
		
		for(Cell[] row : board) {
			for(Cell cell : row) {
				if(cell.getValue() == 0) full = false;
				if(cell.getValue() < minValue) minValue = cell.getValue();
				if(cell.getValue() > maxValue) maxValue = cell.getValue();
			}
		}
	}
	
}
