package unionfind;

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.UF;

public class Percolation {

	private int N; // number of size of N*N board
	private boolean[][] status; // the status of a site, true for open, false for blocked
	private UF uf;
	private UF ufHeadOnly;
	private int start;
	private int end;

	// create N-by-N grid, with all sites blocked
	public Percolation(int N) {
		if (N < 0) throw new IllegalArgumentException();
		this.N = N;

		// the site [i,j] correspond to i*j
		// 0 for start, N*N+2 for end
		uf = new UF(N * N + 2);
		ufHeadOnly = new UF(N * N + 2);
		start = 0;
		end = N * N + 1;

		status = new boolean[N][N];
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				status[i][j] = false;
			}
		}

		// create virtual head and end, and set connection
		// each site in the first row connects with start, each site in the last
		// row connects with end
		// for (int i = 1; i <= N; i++) {
		// uf.union(start, i);
		// uf.union(end, N * N - i + 1);
		// }
	}

	public void open(int i, int j) {
//		StdOut.println("call open: i=" + i + ", j=" + j);
		_open(i, j);
	}

	// open site (row i, column j) if it is not open already
	private void _open(int i, int j) {
		validate(i, j);
		if (_isOpen(i, j)) return;

		// union to its four neighbors if possible
		if (i == 0){
			uf.union(start, fromArrIndex2UFIndex(i, j));
			ufHeadOnly.union(start, fromArrIndex2UFIndex(i, j));
		}
		if (i == N - 1) uf.union(end, fromArrIndex2UFIndex(i, j));

		if (isValidCoordinate(i - 1, j) && _isOpen(i - 1, j)) {
			uf.union(fromArrIndex2UFIndex(i, j), fromArrIndex2UFIndex((i - 1), j));
			ufHeadOnly.union(fromArrIndex2UFIndex(i, j), fromArrIndex2UFIndex((i - 1), j));
		}
		if (isValidCoordinate(i + 1, j) && _isOpen(i + 1, j)) {
			uf.union(fromArrIndex2UFIndex(i, j), fromArrIndex2UFIndex((i + 1), j));
			ufHeadOnly.union(fromArrIndex2UFIndex(i, j), fromArrIndex2UFIndex((i + 1), j));
		}
		if (isValidCoordinate(i, j - 1) && _isOpen(i, j - 1)) {
			uf.union(fromArrIndex2UFIndex(i, j), fromArrIndex2UFIndex(i, (j - 1)));
			ufHeadOnly.union(fromArrIndex2UFIndex(i, j), fromArrIndex2UFIndex(i, (j - 1)));
		}
		if (isValidCoordinate(i, j + 1) && _isOpen(i, j + 1)) {
			uf.union(fromArrIndex2UFIndex(i, j), fromArrIndex2UFIndex(i, (j + 1)));
			ufHeadOnly.union(fromArrIndex2UFIndex(i, j), fromArrIndex2UFIndex(i, (j + 1)));
		}

		// set status
		status[i][j] = true;
	}

	// is site (row i, column j) open?
	public boolean isOpen(int i, int j) {
		return _isOpen(i, j);
	}

	private boolean _isOpen(int i, int j) {
		validate(i, j);
		return status[i][j];
	}

	// is site (row i, column j) full?
	public boolean isFull(int i, int j) {
		return _isFull(i, j);
	}

	private boolean _isFull(int i, int j) {
		validate(i, j);
		// int a = fromArrIndex2UFIndex(i, j);
		return uf.connected(start, fromArrIndex2UFIndex(i, j)) && ufHeadOnly.connected(start, fromArrIndex2UFIndex(i, j));
	}

	// does the system percolate?
	public boolean percolates() {
		return uf.connected(start, end);
	}

	// arr id is start from 0 to N-1, union find start from 1 to N*N
	// case: [0,2] = 0 * 3 + 2 = 2, given N=3
	private int fromArrIndex2UFIndex(int i, int j) {
		return (i * N) + (j + 1);
	}

	// validate that p is a valid index
	private void validate(int p) {
		if (p < 0 || p >= N) {
			StdOut.println("error throw in validate() with p=" + p);
			throw new IndexOutOfBoundsException("index " + p + " is not between 0 and " + (N - 1));
		}
	}

	// validate that p is a valid index
	private void validate(int p1, int p2) {
		validate(p1);
		validate(p2);
	}

	// is valid coordinate
	private boolean isValidCoordinate(int i, int j) {
		if (i >= 0 && i < N && j >= 0 && j < N) {
			return true;
		} else {
			return false;
		}
	}

	// test client (optional)
	public static void main(String[] args) {
		int N = 3;
		Percolation pc = new Percolation(N);

		try {
			StdOut.println("isopen: " + pc.isOpen(1, 1));
			// StdOut.println("isopen: " + pc.isOpen(N, N));
			StdOut.println("isopen: " + pc.isOpen(0, 1));
			// StdOut.println("isopen: (" + pc.isOpen(1, 0));

			StdOut.println("is full: " + pc.isFull(1, 1));
			// StdOut.println("is full: " + pc.isFull(1, 0));

			StdOut.println("is percolate: " + pc.percolates());

			int count = 0;
			while (!pc.percolates()) {
				int i = StdRandom.uniform(N);
				int j = StdRandom.uniform(N);
				StdOut.println("random choose: (" + i + ", " + j + ")");
				if (!pc.isOpen(i, j)) {
					pc.open(i, j);
					StdOut.println("open site: (" + i + ", " + j + ") with pc=" + pc.percolates());
					count++;
				}
			}

			StdOut.println("open site size: " + count);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
