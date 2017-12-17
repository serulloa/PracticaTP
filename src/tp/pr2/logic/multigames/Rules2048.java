
package tp.pr2.logic.multigames;

import java.util.Random;

import tp.pr2.logic.Board;
import tp.pr2.logic.Cell;
import tp.pr2.logic.Position;

/**
 * @author Sergio Ulloa
 *
 */
public class Rules2048 implements GameRules {
	
	// ================================================================================
	// Atributos
	// ================================================================================
	
	public static final int STOP_VALUE = 2048;
	
	// ================================================================================
	// Métodos
	// ================================================================================

	@Override
	public void addNewCellAt(Board board, Position pos, Random rand) {
		int bound = 9;
		int value = rand.nextInt(bound) + 1;
		
		if(value > 1)
			value = 2;
		else
			value = 4;
		
		board.setCell(pos, value);
	}

	@Override
	public int merge(Cell self, Cell other) {
		int ret = 0;
		
		if(self.getValue() == other.getValue()) {
			ret = self.getValue() + other.getValue();
			
			self.setValue(ret);
			other.setValue(0);
		}
		
		return ret;
	}

	@Override
	public int getWinValue(Board board) {
		return board.getMaxValue();
	}

	@Override
	public boolean win(Board board) {
		return (board.getMaxValue() == STOP_VALUE);
	}

}
