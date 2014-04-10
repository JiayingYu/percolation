package percolation;


import edu.princeton.cs.algs4.WeightedQuickUnionUF;;

public class Percolation {
	private boolean isOpen[];
	private WeightedQuickUnionUF uf;
	private int size;
	private int topIndex, bottomIndex; //indice of virtual top and bottom root in uf object
	
	//create N-by-N grid, with all sites blocked
	public Percolation(int N) {
		if (N <= 0) throw new IllegalArgumentException("N shall be > 0");
		size = N;
		topIndex = size * size;
		bottomIndex = topIndex + 1;
		isOpen = new boolean[topIndex];
		uf = new WeightedQuickUnionUF(topIndex + 2);
	}
	
	//open site if it is not already
	public void open(int i, int j) {
		validate (i, j);
		int curr = xyTo1D(i, j);
		if (isOpen[curr]) return; //if the location is already open, return
		
		isOpen[curr] = true;
		
		int left = (j > 1 ? xyTo1D(i, j - 1 ) : -1);
		int right = (j < size ? xyTo1D(i, j + 1) : -1);
		int top = (i > 1 ? xyTo1D(i - 1, j) : -1);
		int bottom = (i < size ? xyTo1D(i + 1, j) : -1);
		
		//if current location not at left most row and left site is open, connect current location to the left site
		if (left != -1 && isOpen[left]) { uf.union(curr,left); }
		
		//current location not at right most row and right site is open
		if (right != -1 && isOpen[right]) { uf.union(curr, right); }
		
		if (top != -1 && isOpen[top]) { uf.union(curr, top); }
		else if (top == -1) { uf.union(curr, topIndex); }
		
		if (bottom != -1 && isOpen[bottom]) { uf.union(curr, bottom); }
		else if (bottom == -1) { uf.union(curr, bottomIndex); }

	}
	
	//is the site open?
	public boolean isOpen(int i, int j) {
		validate(i, j);
		return isOpen[xyTo1D(i, j)];
	}
	
	//is site full?
	public boolean isFull(int i, int j) {
		return isOpen(i, j) && uf.connected(topIndex, xyTo1D(i, j));
	}
	
	//does the system percolate?
	public boolean percolates() {
		return uf.connected(topIndex, bottomIndex);
	}
	
	//mapping 2D coordinates to 1D coordinates
	private int xyTo1D(int i, int j) {
		return (i - 1) * size + j - 1;
	}
	
	private void validate(int i, int j) {
		if ((i >= 1) && (i <= size) && (j >= 1) && (j <= size))
			return;
		throw new IndexOutOfBoundsException("coordinates out of range, should be between 1 and "
				+ size);	
	}
	
	public static void main(String[] args) {
		Percolation p = new Percolation(5);
		p.open(1, 3); p.open(2, 3); p.open(3, 3); p.open(3, 2); p.open(4, 2); p.open(5, 2);
		System.out.println("Percolates? " + p.percolates());
		System.out.println("is (3, 2) full? " + p.isFull(3, 2));
	}
}
