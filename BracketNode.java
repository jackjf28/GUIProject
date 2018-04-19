package fProject;

public class BracketNode {
	Challenger cOne; 
	Challenger cTwo;
	
	public BracketNode() {
		cOne = null;
		cTwo = null;
	}
	
	public void setCOne(Challenger cOne) {
		this.cOne = cOne;
	}
	public void setCTwo(Challenger cTwo) {
		this.cTwo = cTwo;
	}
	
	public Challenger getCOne() {
		return cOne;
	}
	public Challenger getCTwo() {
		return cTwo;
	}
	
	public Challenger getWinner() {
		if (cOne.getCurrScore() > cTwo.getCurrScore()) {
			return cOne;
		}
		return cTwo;
	}
	public Challenger getLoser() {
		if (cOne.getCurrScore() < cTwo.getCurrScore()) {
			return cOne;
		}
		return cTwo;
	}
}
