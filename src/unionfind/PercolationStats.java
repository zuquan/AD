package unionfind;

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {

	private double[] arrPC;
	private int T;
	private double u;
	private double sigma;

	// perform T independent experiments on an N-by-N grid
	public PercolationStats(int N, int T) {
		arrPC = new double[T];
		this.T = T;

		for (int i = 0; i < T; i++) {
			arrPC[i] = experiment(N);
			// StdOut.println("")
		}

		u = mean();
		sigma = stddev();

		StdOut.println("mean = " + u);
		StdOut.println("stddev = " + sigma);
		StdOut.println("confidence inteval = " + confidenceLo() + ", " + confidenceHi());

	}

	// sample mean of percolation threshold
	public double mean() {
		return StdStats.mean(arrPC);
	}

	// sample standard deviation of percolation threshold
	public double stddev() {
		return StdStats.stddev(arrPC);
	}

	// low endpoint of 95% confidence interval
	public double confidenceLo() {
		return u - 1.96 * Math.sqrt(sigma) / Math.sqrt(T);
	}

	// high endpoint of 95% confidence interval
	public double confidenceHi() {
		return u + 1.96 * Math.sqrt(sigma) / Math.sqrt(T);
	}

	private double experiment(int N) {
		Percolation pc = new Percolation(N);
		int count = 0;
		double fa = 0;

		try {
			while (!pc.percolates()) {
				int i = StdRandom.uniform(N);
				int j = StdRandom.uniform(N);
				// StdOut.println("random choose: (" + i + ", " + j + ")");
				if (!pc.isOpen(i, j)) {
					pc.open(i, j);
					// StdOut.println("open site: (" + i + ", " + j +
					// ") with pc=" + pc.percolates());
					count++;
				}
			}

			fa = (double) count / (N * N);
			StdOut.println("open site size " + count + ", fraction = " + fa);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return fa;
	}

	// test client (described below)
	public static void main(String[] args) {

		int N = 200;
		int T = 100;
		PercolationStats ps = new PercolationStats(N, T);

	}
}
