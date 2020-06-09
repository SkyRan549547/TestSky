package study.java8.lambda.apple;

public class Apple {

	private String color;
	private long weight;

	@Override
	public String toString() {
		return "Apple [color=" + color + ", weight=" + weight + "]";
	}

	public Apple(String color, long weight) {
		super();
		this.color = color;
		this.weight = weight;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((color == null) ? 0 : color.hashCode());
		result = prime * result + (int) (weight ^ (weight >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Apple other = (Apple) obj;
		if (color == null) {
			if (other.color != null)
				return false;
		} else if (!color.equals(other.color))
			return false;
		if (weight != other.weight)
			return false;
		return true;
	}

	public String getColor() {
		return color;
	}

	public long getWeight() {
		return weight;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public void setWeight(long weight) {
		this.weight = weight;
	}

}
