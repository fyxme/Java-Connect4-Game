
public class Player implements Participant {
	private static final int ERROR = -1;
	
	private String name = null;
	private int player_id = ERROR;
	
	
	@Override
	public int getPid() {
		return player_id;
	}
	
	@Override
	public String getName() {
		return name;
	}

	@Override
	public Move makeMove(int row, int column) {
		Move ret = new Move(row, column);
		return ret;
	}

	@Override
	public Move chooseMove() {
		// TODO Auto-generated method stub
		return null;
	}
}
