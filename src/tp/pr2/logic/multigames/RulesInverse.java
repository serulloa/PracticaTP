package tp.pr2.logic.multigames;

import java.util.Random;

import tp.pr2.logic.Board;
import tp.pr2.logic.Cell;
import tp.pr2.logic.Position;

/**
 * @author Sergio Ulloa
 *
 */
public class RulesInverse implements GameRules {
	
	// ================================================================================
	// Atributos
	// ================================================================================
	
	public static final int STOP_VALUE = 2;
	
	// ================================================================================
	// Métodos
	// ================================================================================

	@Override
	public void addNewCellAt(Board board, Position pos, Random rand) {
		int bound = 9;
		int value = rand.nextInt(bound) + 1;
		
		if(value > 1)
			value = 2048;
		else
			value = 1024;
		
		board.setCell(pos, value);
	}

	@Override
	public int merge(Cell self, Cell other) {
		int ret = 0;
		
		if(self.getValue() == other.getValue()) {
			ret = self.getValue();
			
			self.setValue(self.getValue()/2);
			other.setValue(0);
			ret = (2048/ret) * 2;
		}
		
		return ret;
	}

	@Override
	public int getWinValue(Board board) {
		return board.getMinValue();
	}

	@Override
	public boolean win(Board board) {
		return (board.getMinValue() == STOP_VALUE);
	}

}
