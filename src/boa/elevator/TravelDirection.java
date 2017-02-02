package boa.elevator;

public enum TravelDirection {
	Up(1), Down(-1), NoDirection(0);
	private final int id;

	TravelDirection(int id) {
		this.id = id;
	}

	public int getValue() {
		return id;
	}
}
