package comThread.Study.design.order.inter;

public interface Order {
	public boolean valited(Integer... orderNums);

	public boolean inValited(Integer... orderNums);

	public boolean inValited(String... orderNums);
}
