package game;

public abstract class Character {

	protected String id;
    protected int pointsVie;
    protected int pointsForce;
    protected Room salle;
    
    public  String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public int getPointsVie() {
		return pointsVie;
	}
	public void setPointsVie(int pointsVie) {
		this.pointsVie = pointsVie;
	}
	public Room getSalle() {
		return salle;
	}
	public void setSalle(Room salle) {
		this.salle = salle;
	}
    
	public void setPointsForce(int p) {
		pointsForce+=p;
	}
    
	public int getPointsForce() {
		return pointsForce;
	}
	
	public void attack(Character c) {
    	c.setPointsVie(c.getPointsVie()-this.pointsForce);
    }
	
}
