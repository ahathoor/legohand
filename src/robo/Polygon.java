package robo;

public class Polygon {
	
	float[][] points;
	Polygon(float[]... coords) {
		this.points = coords;
	}
	public float[][] getPoints() {
		return points;
	}
	
}
