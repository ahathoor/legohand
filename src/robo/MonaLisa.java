package robo;

public class MonaLisa {
	
	static Polygon[] parts = {
	new Polygon(new float[] {21,42},new float[] {14,40},new float[] {11,32},new float[] {12,26},new float[] {13,23},new float[] {12,21},new float[] {12,18},new float[] {14,12},new float[] {19,8},new float[] {22,7},new float[] {24,6},new float[] {27,6},new float[] {30,6},new float[] {33,7},new float[] {36,9},new float[] {39,11},new float[] {39,12},new float[] {39,14},new float[] {38,18},new float[] {38,22},new float[] {38,26},new float[] {37,29},new float[] {36,30},new float[] {36,33},new float[] {37,36},new float[] {35,39},new float[] {32,41},new float[] {28,43})
	,new Polygon(new float[] {14,30},new float[] {16,31},new float[] {18,31},new float[] {20,29},new float[] {17,29},new float[] {15,29})
	,new Polygon(new float[] {26,30},new float[] {28,31},new float[] {30,31},new float[] {31,29},new float[] {29,29},new float[] {27,29})
	,new Polygon(new float[] {22,29},new float[] {21,24},new float[] {20,21},new float[] {20,19},new float[] {22,19},new float[] {23,19},new float[] {25,19},new float[] {26,20},new float[] {25,22},new float[] {24,23},new float[] {24,25},new float[] {24,27},new float[] {24,29})
	,new Polygon(new float[] {20,12},new float[] {22,11},new float[] {27,11},new float[] {29,11},new float[] {31,12},new float[] {31,13},new float[] {29,13},new float[] {26,13},new float[] {24,13},new float[] {22,13},new float[] {21,13},new float[] {20,13})
	,new Polygon(new float[] {22,11},new float[] {24,10},new float[] {27,10},new float[] {28,11})
	,new Polygon(new float[] {25,43},new float[] {25,47},new float[] {32,46},new float[] {37,45},new float[] {40,40},new float[] {42,35},new float[] {43,31},new float[] {44,25},new float[] {44,22},new float[] {44,17},new float[] {44,13},new float[] {44,10},new float[] {45,6},new float[] {46,4},new float[] {47,2},new float[] {48,0},new float[] {38,0},new float[] {39,11},new float[] {39,15},new float[] {38,18},new float[] {38,27},new float[] {36,30},new float[] {37,36},new float[] {35,39},new float[] {28,43})
	,new Polygon(new float[] {24,43},new float[] {24,46},new float[] {20,47},new float[] {16,45},new float[] {13,43},new float[] {12,40},new float[] {12,37},new float[] {11,35},new float[] {10,32},new float[] {10,30},new float[] {10,27},new float[] {9,23},new float[] {9,20},new float[] {9,16},new float[] {8,12},new float[] {8,10},new float[] {8,8},new float[] {7,6},new float[] {7,3},new float[] {7,2},new float[] {7,1},new float[] {13,1},new float[] {14,5},new float[] {16,7},new float[] {16,9},new float[] {17,10},new float[] {14,12},new float[] {12,18},new float[] {12,22},new float[] {13,23},new float[] {11,32},new float[] {14,40})
	,new Polygon(new float[] {21,7},new float[] {21,3},new float[] {14,0},new float[] {38,0},new float[] {37,10},new float[] {33,7},new float[] {30,6},new float[] {24,6})
	};
	public static void draw(MotorControl m, float x, float y) {
		for (Polygon p : parts) {
			m.drawPolygon(p, x, y);
		}
	}
}
