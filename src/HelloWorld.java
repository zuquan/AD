
public class HelloWorld {
	
	public static void test(int [] i) {
		int [] j = i;
		StdOut.println(j[0]);
	}
	
	public static void main(String[] args) {
		// System.out.println("Hello, World");
		int tt[] = { 6, 1, 2, 3, 4, 5 };
		test(tt);
		
		

		int arr1[] = { 6, 1, 2, 3, 4, 5 };
		int arr2[] = new int [5];
//		int arr2[] = { 0, 10, 20, 30, 40, 50 };
		
		// copies an array from the specified source array
		System.arraycopy(arr1, 0, arr2, 0, 5);
		System.out.print("array2 = ");
		System.out.print(arr2[0] + " ");
		System.out.print(arr2[1] + " ");
		System.out.print(arr2[2] + " ");
		System.out.print(arr2[3] + " ");
		System.out.print(arr2[4] + " ");

		double xlo = Double.parseDouble(args[0]);
		double xhi = Double.parseDouble(args[1]);
		double ylo = Double.parseDouble(args[2]);
		double yhi = Double.parseDouble(args[3]);
		int T = Integer.parseInt(args[4]);

		Interval1D xinterval = new Interval1D(xlo, xhi);
		Interval1D yinterval = new Interval1D(ylo, yhi);
		Interval2D box = new Interval2D(xinterval, yinterval);
		box.draw();

		Counter counter = new Counter("hits");
		for (int t = 0; t < T; t++) {
			double x = StdRandom.uniform(0.0, 1.0);
			double y = StdRandom.uniform(0.0, 1.0);
			Point2D p = new Point2D(x, y);

			if (box.contains(p))
				counter.increment();
			else
				p.draw();
		}

		StdOut.println(counter);
		StdOut.println(box.area());

		int x = 1;
		// StdOut.printf("test\n", b);
		// StdOut.printf("box area = %.2f\n", box.area());

	}
}