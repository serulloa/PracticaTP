package tp.pr2.logic.multigames;

import java.util.Random;

import tp.pr2.logic.Board;
import tp.pr2.logic.Cell;
import tp.pr2.logic.Position;
import tp.pr2.util.MyMathsUtil;

/**
 * @author Sergio Ulloa
 *
 */
public class RulesFib implements GameRules {
	
	// ================================================================================
	// Atributos
	// ================================================================================
	
	public static final int STOP_VALUE = 144;
	
	// ================================================================================
	// Métodos
	// ================================================================================

	@Override
	public void addNewCellAt(Board board, Position pos, Random rand) {
		int bound = 9;
		int value = rand.nextInt(bound) + 1;
		
		if(value > 1)
			value = 1;
		else
			value = 2;
		
		board.setCell(pos, value);
	}

	@Override
	public int merge(Cell self, Cell other) {
		int ret = 0;
		
		if(MyMathsUtil.nextFib(self.getValue()) == other.getValue() ||
				MyMathsUtil.nextFib(other.getValue()) == self.getValue()
				|| (self.getValue() == 1 && other.getValue() == 1)) {
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
