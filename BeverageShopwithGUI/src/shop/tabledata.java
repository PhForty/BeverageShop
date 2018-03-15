package shop;

public class tabledata {
	
	private String drink;
	private int showr;
	private int storer1;
	private int storer2;

	public tabledata (int drink, int showr, int storer1, int storer2) {
		switch (drink) {
			case 0:
				this.drink = "Mineralwasser (still)";
				break;
			case 1:
				this.drink = "Mineralwasser (mit K.)";
				break;
			case 2:
				this.drink = "Apfelsaft";
				break;
			case 3:
				this.drink = "Orangensaft";
				break;
			case 4:
				this.drink = "Limonade";
				break;
			case 5:
				this.drink = "Bier";
				break;
		}
		this.showr = showr;
		this.storer1 = storer1;
		this.storer2 = storer2;
	}

	public String getDrink() {
		return drink;
	}

	public void setDrink(String drink) {
		this.drink = drink;
	}
	
	public int getShowr() {
		return showr;
	}

	public void setShowr(int showr) {
		this.showr = showr;
	}

	public int getStorer1() {
		return storer1;
	}

	public void setStorer1(int storer1) {
		this.storer1 = storer1;
	}

	public int getStorer2() {
		return storer2;
	}

	public void setStorer2(int storer2) {
		this.storer2 = storer2;
	}
	
}
