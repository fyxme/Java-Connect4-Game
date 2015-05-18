
public class Tile {
	private Participant occupant = null;
	
	public Tile() {
	}
	
	/**
	 * @return Returns the occupant of the current tile
	 */
	public Participant getOccupant() {
		return this.occupant;
	}
	
	/**
	 * Set the occupant of the Tile
	 * @param participant New occupant of this Tile
	 */
	public void setOccupant(Participant participant) {
		this.occupant = participant;
	}
	
	/**
	 * Checks if the tile is occupied.
	 * @return Returns true if it is Free else returns false
	 */
	public boolean isFree() {
		if (occupant == null)
			return true;
		return false;
	}
	
}
