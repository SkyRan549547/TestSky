package comThread.Study.BTree;

public class Book implements Comparable<Book> {
	public StringBuffer buf;
	public String name;
	public int price;

	public Book(String name, int price) {
		this.name = name;
		this.price = price;
	}

	public int compareTo(Book book) {
		if (this.price > book.price) {
			return 1;
		} else if (this.price < book.price) {
			return -1;
		} else {
			return 0;
		}
	}

	public String getName() {
		return name;
	}

	public int getPrice() {
		return price;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	@Override
	public String toString() {
		return "Book Ãû×Ö=" + getName() + ", ¼Û¸ñ=" + getPrice() + "] \n";
	}

	@Override
	public boolean equals(Object oj) {
		Book book = (Book) oj;
		System.out.println("1111---" + "name---" + this.name + " price---"
				+ this.price);
		System.out.println("1111---"
				+ (this.name.equals(book.name) && this.price == book.price));
		if (this.name.equals(book.name) && this.price == book.price) {
			return true;
		} else {
			return false;
		}
	}
}
