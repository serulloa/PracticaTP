package tp.pr2.logic.multigames;

import java.util.Random;

import tp.pr2.logic.Board;
import tp.pr2.logic.Cell;
import tp.pr2.logic.Position;

public class RulesInverse implements GameRules {

	@Override
	public void addNewCellAt(Board board, Position pos, Random rand) {
		// TODO Auto-generated method stub

	}

	@Override
	public int merge(Cell self, Cell other) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getWinValue(Board board) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean win(Board board) {
		// TODO Auto-generated method stub
		return false;
	}

}
