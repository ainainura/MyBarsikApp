package nu.edu.kz;

public class Meal {
	private String name;
	private int price;
	private int iconID;
	private String condition;
	
	public Meal (String name, int price, int iconID){
		super();
		this.name = name;
		this.price = price;
		this.iconID = iconID;
	}
	
	public String getName() {
		return name;
	}
	public int getPrice(){
		return price;
	}
	public int getIconID(){
		return iconID;
	}
}
