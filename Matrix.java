import java.io.*;
import java.util.*;

class Matrix extends Object {
	
	private List<List<Integer>> matrix;  
	
	public Matrix(int size) {
        this.createEmptyMatrix(size);
	}
	
	public Matrix(String fileName) {
		boolean valid = true;
		if (valid) {
			this.createFromFile(fileName);
		} else {
        	this.createMatrix(8);
		}
	}
	
	public List<Integer> getRow(int row) {
		return this.matrix.get(row);
	}
	
	public void setRow(int row, List<Integer> values) {
		this.matrix.set(row, values);
	}
	
	public int length() {
		return this.matrix.get(0).size();
	}
	
	@Override
	public String toString() {
		StringBuffer buffer = new StringBuffer();
		for (int i = 0; i < this.matrix.size(); i++) {
			for (int j = 0; j < this.matrix.get(i).size(); j++) {
				buffer.append(this.matrix.get(i).get(j) + " ");
			}
			buffer.append("\n");
		}
		return buffer.toString();
	}
	
	public void createMatrix(int size) {
		this.createEmptyMatrix(size);
		
		for (int i = 0; i < this.matrix.size(); i++) {
			for (int j = 0; j < this.matrix.get(i).size(); j++) {
				int r = randint(0, 1);
				if (r == 1) {
					this.matrix.get(i).set(j, r);
					this.matrix.get(j).set(i, r);
				}
			}
		}
	}
	
	public void createEmptyMatrix(int size) {
		matrix = new ArrayList<List<Integer>>();
		for (int i = 0; i < size; i++) {
			List<Integer> row = new ArrayList<Integer>();
			for (int j = 0; j < size; j++) {
				row.add(j);
			}
			matrix.add(row);
		}
	}
	
	public int[] supergraph() {
		Random rand;
		int top = randint(10, 20);
		int bottom = randint(10, 20);
		int newRowLength = this.matrix.get(0).size() + top + bottom;
		for (int i = 0; i < this.matrix.size(); i++) {
			for (int j = 0; j <= top; j++) {
				this.matrix.get(i).add(i, 0);
			}
			for (int j = 0; j <= bottom; j++) {
				this.matrix.get(i).add(0);
			}
		}
		for (int i = 0; i <= top; i++) {
			List<Integer> row = new ArrayList<Integer>();
			for (int j = 0; j < newRowLength; j++) {
				row.add(j);
			}
			this.matrix.add(i, row);
		}
		for (int i = 0; i <= bottom; i++) {
			List<Integer> row = new ArrayList<Integer>();
			for (int j = 0; j < newRowLength; j++) {
				row.add(j);
			}
			this.matrix.add(row);
		}
		for (int i = 0; i <= top; i++) {
			for (int j = 0; j <= newRowLength; j++) {
				int r = randint(0, 1);
				if (r == 1) {
					this.matrix.get(i).set(j, r);
					this.matrix.get(j).set(i, r);
				}
			}
		}
		for (int i = 0; i <= bottom; i++) {
			for (int j = 0; j <= newRowLength; j++) {
				if (this.matrix.get(newRowLength - i - 1).get(j) != 1) {
					int r = randint(0, 1);
					if (r == 1) {
						this.matrix.get(newRowLength - i - 1).set(j, r);
						this.matrix.get(j).set(newRowLength - i - 1, r);
					}
				}
			}
		}
		return new int[] { top, bottom };
	}
	
	int randint(int min, int max) {
		Random rand = new Random();
		return rand.nextInt((max - min) + max) + min;
	}
	
	public Object[] isomorphism() {
		HashMap oriGraph = matrixToDict(this);
		HashMap isoFunction = new HashMap<Integer, Integer>();
		HashMap newGraph = new HashMap<Integer, Integer>();
		List<Integer> temp = new ArrayList<Integer>();
		for (int i = 0; i < this.matrix.size(); i++) {
			temp.add((Integer)i);
		}
		for (int i = 0; i < this.matrix.size(); i++) {
			int r = randint(0, temp.size());
			isoFunction.put(i, temp.remove(r));
		}
		for (Object key : oriGraph.keySet()) {
			for (Object value : ((HashMap)oriGraph.get(key)).keySet()) {
				((HashMap)oriGraph.get(key)).put(value, isoFunction.get(((HashMap)oriGraph.get(key)).get(value)));
			}
		}
		for (Object key : oriGraph.keySet()) {
			newGraph.put(isoFunction.get(key), oriGraph.get(key));
		}
		Matrix newMatrix = dictToMatrix(newGraph);
		return new Object[] { isoFunction, newMatrix };
	}
	
	public Matrix applyIsomorphism(HashMap isoFunction) {
		HashMap oriGraph = matrixToDict(this);
		HashMap newGraph = new HashMap<Integer, Integer>(){};
		for (Object key : oriGraph.keySet()) {
			for(Object index : ((HashMap)oriGraph.get(key)).keySet()) {
				for (Object key2 : isoFunction.keySet()) {
					if (((HashMap)oriGraph.get(key)).get(index) == key2) {
						((HashMap)oriGraph.get(key)).put(index, isoFunction.get(key2));
						break;
					}
				}
			}
		}
		for (Object key : oriGraph.keySet()) {
			newGraph.put(isoFunction.get(key), oriGraph.get(key));
		}
		return dictToMatrix(newGraph);
	}
	
	public void createFromFile(String filename) {
		this.matrix = new ArrayList<List<Integer>>();
		try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
		    String line;
		    while ((line = br.readLine()) != null) {
				String[] comps = line.split(" ");
				List<Integer> compsInt = new ArrayList<Integer>();
				for (String comp : comps) {
					compsInt.add(Integer.parseInt(comp));
				}
		       	this.matrix.add(compsInt);
		    }
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public List<Integer> getCol(int col) {
		List<Integer> cols = new ArrayList<Integer>();
		for (int i = 0; i < this.matrix.size(); i++) {
			cols.add((Integer)((HashMap)this.matrix.get(i)).get(col));
		}
		return cols;
	}
	
	public void setCol(int col, List<Integer> values) {
		for (int i = 0; i < this.matrix.size(); i++) {
			((HashMap)this.matrix.get(i)).put(col, values.get(i));
		}
	}
	
	public boolean equals(Matrix matrix) {
		for (int i = 0; i < this.matrix.size(); i++) {
			if (this.matrix.get(i) != matrix.getRow(i)) {
				return false;
			}
		}
		return true;
	}
	
	public void writeToFile(String filename) {
		try {
			PrintWriter writer = new PrintWriter(filename, "UTF-8");
			for (int i = 0; i < this.matrix.size(); i++) {
				for (int j = 0; j < this.matrix.get(i).size(); j++) {
					if (this.matrix.get(i).get(j) == 1) {
						writer.print("1 ");
					} else if (this.matrix.get(i).get(j) == 0) {
						writer.print("0 ");
					} else {
						writer.print("x ");
					}
				}
				writer.print("\n");
			}
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public List<List<Integer>> getMatrix() {
		return this.matrix;
	}
	
	public HashMap matrixToDict(Matrix m) {
		HashMap graphDict = new HashMap<Integer, Integer>();
		List<List<Integer>> matrix = m.getMatrix();
		for (int i = 0; i < matrix.size(); i++) {
			for (int j = 0; j < matrix.get(i).size(); i++) {
				List<Integer> x = new ArrayList<Integer>();
				if (matrix.get(i).get(j) == 1) {
					x.add((Integer)j);
				}
				graphDict.put(i, x);
			}
		}
		return graphDict;
	}
	
	public Matrix dictToMatrix(HashMap newGraph) {
		Matrix newMatrix = new Matrix(newGraph.size());
		for (Object key : newGraph.keySet()) {
			for (int i = 0; i < ((HashMap)newGraph.get(key)).size(); i++) {
				newMatrix.getRow((Integer)key).set((Integer)((HashMap)newGraph.get(key)).get(i), 1);
			}
		}
		return newMatrix;
	}

	public Matrix dictToMatrixX(HashMap newGraph, int size) {
		Matrix newMatrix = new Matrix(newGraph.size());
		/*for (Object key : newGraph.keySet()) {
			if (newGraph.get(key) != 1 && newGraph.get(key) != 0) {
				List<Integer> x = new ArrayList<Integer>();
				for (int i = 0; i < size; i++) {
					x.add(120); // 'x'
				}
				newMatrix.setCol((int)key, x);
				newMatrix.setRow((int)key, x);
			} else {
				for (int i = 0; i < ((HashMap)newGraph.get(key)).size(); i++) {
					newMatrix.getRow((int)key).set(i, 1);
				}
			}
		}*/
		return newMatrix;
	}
	
	public HashMap qPrime(Matrix q, Matrix g2qiso, int top, int bottom) {
		HashMap qp = matrixToDict(q);
		List<Object> toDelete = new ArrayList<Object>();
		for (int i = 0; i < top; i++) {
			toDelete.add(g2qiso.getRow(i));
		}
		for (int i = 0; i < bottom; i++) {
			toDelete.add(g2qiso.getRow(g2qiso.length() - i - 1));
		}
		for (Object key : qp.keySet()) {
			List<Object> x = new ArrayList<Object>();
			for(int i = 0; i < ((HashMap)qp.get(key)).size(); i++) {
				x.add(((HashMap)qp.get(key)).get(i));
			}
			for(int i = 0; i < toDelete.size(); i++) {
				x.remove(toDelete.get(i));
			}
			qp.put(key, x);
		}
		for (Object x : toDelete) {
			qp.put((Integer)x, new HashMap());
		}
		return qp;
	}
	
	public HashMap genPi(HashMap phi, HashMap alpha, int top, int bottom) {
		phi = new HashMap(phi);
		HashMap alphaPrime = new HashMap(alpha);
		HashMap pi = new HashMap<Integer, Integer>();
		for (int i = 0; i < top; i++) {
			alphaPrime.remove(i);
		}
		for (int i = 0; i < bottom; i++) {
			alphaPrime.remove(alphaPrime.size() + top - 1);
		}
		for (Object key : phi.keySet()) {
			phi.put(key, phi.get(key) + Integer.toString(top));
		}
		for (Object key : phi.keySet()) {
			pi.put(key, alphaPrime.get(phi.get(key)));
		}
		return pi;
	}
	
	public HashMap translate(HashMap dict, HashMap iso, int size) {
		HashMap copy = new HashMap();
		for (Object key : iso.keySet()) {
			copy.put(iso.get(key), dict.get(key));
		}
		for (Object key : copy.keySet()) {
			List<Integer> array = new ArrayList<Integer>();
			for (int i = 0 ; i < ((HashMap)copy.get(key)).size(); i++) {
				int x = (int)((HashMap)copy.get(key)).get(i);
				array.add((int)iso.get(x));
			}
			Collections.sort(array);
			copy.put(key, array);
		}
		for (int i = 0; i < size; i++) {
			if (copy.get(i) == null) {
				copy.put(i, new HashMap());
			}
		}
		return copy;
	}
	
	public void isoToFile(HashMap iso, String filename, int top, int bottom) {
		try {
			PrintWriter writer = new PrintWriter(filename, "UTF-8");
			writer.print(Integer.toString(top) + "\n" + Integer.toString(bottom) + "\n");
			for (Object key : iso.keySet()) {
				writer.print(key.toString() + " " + iso.get(key).toString() + "\n");
			}
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public Object[] isoFromFile(String fileName) {
		int index = 0;
		int top = 0, bottom = 0;
		HashMap iso = new HashMap<Integer, Integer>();
		try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
		    String line;
		    while ((line = br.readLine()) != null) {
				if (index == 0) {
					top = Integer.parseInt(line);
				} else if (index == 1) {
					bottom = Integer.parseInt(line);
				} else {
					String[] a  = line.split(" ");
					iso.put(Integer.parseInt(a[0]), Integer.parseInt(a[1]));
				}
				index++;
		    }
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new Object[] { iso, (Integer)top, (Integer)bottom };
	}
	
	public static void main(String[] args) {
		Matrix matrix = new Matrix(64);
	}
}

/**/