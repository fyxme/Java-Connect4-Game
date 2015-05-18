
public class AI implements Participant {
	private static final int ERROR = -1;
	
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Move makeMove(int row, int column) {
		Move ret = new Move(row,column);
		return ret;
	}

	@Override
	public Move chooseMove(Board bd) {
		// find the best row and best column for the AI
		int row = ERROR;
		int column = ERROR;
		
		Move ret = new Move(row,column);
		return ret;
	}

	@Override
	public int getPid() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Move chooseMove() {
		// unused for the AI
		return null;
	}

}
