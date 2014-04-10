package percolation;


import edu.princeton.cs.introcs.StdRandom;
import edu.princeton.cs.introcs.StdStats;

public class PercolationStats {
	private double[] thresholds;
	private double mean, stddev, lo, hi;

	//perform T independent computational experiments on an N-by-N grid
	public PercolationStats(int N, int T) {
		if (N <= 0 || T <= 0) throw new IllegalArgumentException ("N and T has to be > 0");
		thresholds = new double[T];
		
		for (int i = 0; i < T; i++) {
			Percolation p = new Percolation(N);
			int sitesOpened = 0;
			
			while (!p.percolates()) {
				int rowInx = StdRandom.uniform(N) + 1;
				int colInx = StdRandom.uniform(N) + 1;
				if (!p.isOpen(rowInx, colInx)) {
					p.open(rowInx, colInx);
					sitesOpened++;
				}
			}
			thresholds[i] = (double) sitesOpened / (N * N);
		}	
		
		mean = StdStats.mean(thresholds);
		stddev = StdStats.stddev(thresholds);
		lo = mean - 1.96 * stddev / Math.sqrt(T);
		hi = mean + 1.96 * stddev / Math.sqrt(T);
	}
	
	//sample mean of percolation threshold
	public double mean() {
		return mean;
	}
	
	//sample standard deviation of percolation threshold
	public double stddev() {
		return stddev;
	}
	
	//return lower bound of the 95% confidence interval
	public double confidenceLo() {
		return lo;
	}
	
	//return upper bound of the 95% confidence interval
	public double confidenceHi() {
		return hi;
	}
	
	public static void main(String[] args) {
//		int N = Integer.parseInt(args[0]);
//		int T = Integer.parseInt(args[1]);
		PercolationStats ps = new PercolationStats(40, 100);
		System.out.println("mean =                    " + ps.mean());
		System.out.println("stddev =                  " + ps.stddev());
		System.out.println("95% confidence interval = " + ps.confidenceLo() +
				", " + ps.confidenceHi());
	}
}


