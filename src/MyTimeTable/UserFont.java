package MyTimeTable;

//	This UserFont class has the main responsibility of storing information about each user's font preferences. 

public class UserFont {
	
	private String family;
	private String weight;
	private String posture;
	private double[] size;
	private String color;
	
	public UserFont(String family, String weight, String posture, double[] size, String color) {
		this.family = family;
		this.weight = weight;
		this.posture = posture;
		this.size = size;
		this.color = color;
	}
	
	public String getFamily() {
		return family;
	}

	public void setFamily(String family) {
		this.family = family;
	}

	public double[] getSize() {
		return size;
	}

	public void setSize(double[] size) {
		this.size = size;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getWeight() {
		return weight;
	}

	public void setWeight(String weight) {
		this.weight = weight;
	}

	public String getPosture() {
		return posture;
	}

	public void setPosture(String posture) {
		this.posture = posture;
	}


}
