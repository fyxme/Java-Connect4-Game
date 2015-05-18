
public class Player implements Participant {
	private static final int ERROR = -1;
	
	private String name = null;
	private int id = ERROR;
	
	public Player(int id) {
		this.id = id;
	}
	
	@Override
	public int getPid() {
		return id;
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
}