

public class StockQuote {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String name = "http://finance.yahoo.com/q?s=";
		String keyword = "goog";
		In in = new In(name+keyword);
		String text  = in.readAll();
		int start = text.indexOf("Alphabet Inc. (GOOG)", 0);
		int from = text.indexOf("goog", start);
		int to = text.indexOf("</span>", from);
		String price = text.substring(from + 6, to);
		StdOut.println(price);

	}

}
