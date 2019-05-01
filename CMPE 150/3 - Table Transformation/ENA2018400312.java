import java.util.*;
import java.io.*;

public class ENA2018400312 {

	/*
	 * In the main method, a file is taken and read in a Scanner. A new 2D array of
	 * Strings is created by the method setTable, which reads the first line of the
	 * file. After that, the entire table is read from table using readTable method.
	 * The original table is printed. After that, two new arrays are created, one is
	 * of Strings, one is of integers. The original table is split into these two
	 * arrays. One holds only the labels and the other holds only the integer
	 * values, so it becomes more convenient to process the table. After that, the
	 * method process is called and the entire table of cells is processed one by
	 * one. In the end, the final table of integers is printed.
	 */
	public static void main(String[] args) throws FileNotFoundException {
		File file = new File("input" + ".txt");
		Scanner s = new Scanner(file);
		String[][] table = setTable(s);
		table = readTable(s, table, 0);

		printTable(table, 0);
		System.out.println();

		int[][] tableI = new int[table.length][table[0].length];
		tableI = tableToInt(table, tableI, 0);

		String[][] tableS = new String[table.length][table[0].length];
		tableS = tableToS(table, tableS, 0);

		process(tableS, tableI, 0);
		printTable(tableI, 0);

	}

	/*
	 * This method takes 2 2D arrays, one of Strings, one of integers, which is
	 * empty. It examines the table and takes the integer value of each cell, and
	 * assigns it to the same cell of the empty array. That way, the empty array is
	 * filled only the integers of the original table. Starting from 0, i is
	 * increased in every iteration until it reaches the end of the table.
	 */
	public static int[][] tableToInt(String[][] table, int[][] output, int i) {
		if (i < table.length * table[0].length) {
			output[i / table[0].length][i % table[0].length] = Integer
					.parseInt(table[i / table[0].length][i % table[0].length].substring(1));
			tableToInt(table, output, i + 1);
		}
		return output;
	}

	/*
	 * This method takes 2 2D arrays of Strings of same size, one filled, one empty.
	 * It examines the table and takes the first character of each cell, and assigns
	 * it to the same cell of the empty array. That way, the empty array is filled
	 * only the labels of the original table. Starting from 0, i is increased in
	 * every iteration until it reaches the end of the table.
	 */
	public static String[][] tableToS(String[][] table, String[][] output, int i) {
		if (i < table.length * table[0].length) {
			output[i / table[0].length][i % table[0].length] = (table[i / table[0].length][i % table[0].length]
					.substring(0, 1));
			tableToS(table, output, i + 1);
		}
		return output;
	}

	/*
	 * This method reads the file in a Scanner to a 2D array of Strings, and returns
	 * the array. i is the coordinate value. Starting from 0, i is increased in each
	 * iteration until it reaches the end of the table. That way, the table is
	 * filled.
	 */
	public static String[][] readTable(Scanner s, String[][] table, int i) {
		if (i < table.length * table[0].length) {
			table[i / table[0].length][i % table[0].length] = s.next();
			readTable(s, table, i + 1);
		}
		return table;
	}

	/*
	 * This method creates a new table and returns it, by taking the first line of a
	 * scanner and reads the value in it. The numbers until the letter "x" is the a
	 * coordinate of the table, and the numbers after it are the b coordinate. A new
	 * 2D array of Strings is created, that has a size of a and b.
	 */
	public static String[][] setTable(Scanner s) {
		String dimension = s.nextLine();
		int a = Integer.parseInt(dimension.substring(0, dimension.indexOf("x")));
		int b = Integer.parseInt(dimension.substring(dimension.indexOf("x") + 1));

		String[][] table = new String[a][b];

		return table;
	}

	/*
	 * This method takes an array of Strings and prints it. It also takes an integer
	 * i, that holds the x and y coordinate of the table. In every iteration, i is
	 * increased until it reaches the end of the table. If the current cell is the
	 * last cell of a row but not the end of the file, the method goes to the next
	 * line. The value in each cell is printed, by finding the x and y coordinates
	 * from i.
	 */
	public static void printTable(String[][] table, int i) {
		if (i % table[0].length == 0 && i != table.length * table[0].length && i != 0) {
			System.out.println();
		}
		if (i < table.length * table[0].length) {
			System.out.print(table[i / table[0].length][i % table[0].length] + " ");
			printTable(table, i + 1);
		}

	}

	/*
	 * This method takes an array of integers, and does the same thing the method
	 * above does.
	 */
	public static void printTable(int[][] table, int i) {
		if (i % table[0].length == 0 && i != table.length * table[0].length) {
			System.out.println();
		}
		if (i < table.length * table[0].length) {
			System.out.print(table[i / table[0].length][i % table[0].length] + " ");
			printTable(table, i + 1);
		}

	}

	/*
	 * This method takes two 2D arrays, one String, one integer, and takes an
	 * integer, it holds the coordinate value. In the method, each cell is checked.
	 * In every iteration, i is increased by 1, until the method reaches the end of
	 * the table. In every cell, the method checks which label it has. If the label
	 * is F, the method does nothing, so I didn't write that part. If the label is
	 * R, the method calls processR, and it returns the largest number in the
	 * specific row. If the label is D, processD is called. If the label is C, a new
	 * array called column is made, and it is filled by getColumn. After that,
	 * processC is called, and it returns the median of the column. After every cell
	 * is reevaluated, the method returns the new array of integers, tableI.
	 */
	public static int[][] process(String[][] tableS, int[][] tableI, int i) {

		if (i < tableS.length * tableS[0].length) {
			String letter = tableS[i / tableS[0].length][i % tableS[0].length];
			switch (letter) {
			case "N":
				processN(tableS, tableI, i);
				break;
			case "R":
				tableI[i / tableI[0].length][i % tableI[0].length] = processR(tableI[i / tableS[0].length]);
				break;
			case "D":
				processD(tableI, i);
				break;
			case "C":
				int[] column = new int[tableI.length];
				column = getColumn(tableI, column, 0, i % tableS[0].length);
				tableI[i / tableI[0].length][i % tableI[0].length] = processC(tableI, column);

			}
			process(tableS, tableI, i + 1);
		}
		return tableI;
	}

	/*
	 * This method processes R label. It returns the largest number in the row. If
	 * the array has a length of 1, it returns the only number in it. If there is
	 * more than 1 item, the method is called again, taking the largest of the
	 * values except the first one.
	 */
	public static int processR(int[] row) {
		if (row.length == 1)
			return row[0];

		else
			return Math.max(row[0], processR(Arrays.copyOfRange(row, 1, row.length)));

	}

	/*
	 * This method processes N label. It takes an i number, which holds the x and y
	 * coordinates of the specific cell. The method checks if the cells next to it,
	 * above and below are labeled N. If they are, the value they hold are changed
	 * the the number in the original cell. After that, the same process is done in
	 * the cells surrounding the original cell. In the end, all the N labeled cells
	 * that are neighbors have the same value. The changed table is returned.
	 */
	public static int[][] processN(String[][] tableS, int[][] tableI, int i) {

		int r = tableS.length;
		int c = tableS[0].length;
		if (i < r * c) {

			int y = i / c;
			int x = i % c;
			int co = tableI[y][x];

			if (y > 0) {
				if (tableS[y - 1][x].equals("N")) {
					tableI[y - 1][x] = co;
					tableS[y][x] = "n";
					processN(tableS, tableI, i - c);
				}
			}
			if (y + 1 != r) {
				if (tableS[y + 1][x].equals("N")) {
					tableI[y + 1][x] = co;
					tableS[y][x] = "n";
					processN(tableS, tableI, i + c);
				}
			}
			if (x + 1 != c) {
				if (tableS[y][x + 1].equals("N")) {
					tableI[y][x + 1] = co;
					tableS[y][x] = "n";
					tableI = processN(tableS, tableI, i + 1);
				}
			}
			if (x > 0) {
				if (tableS[y][x - 1].equals("N")) {
					tableI[y][x - 1] = co;
					tableS[y][x] = "n";
					tableI = processN(tableS, tableI, i - 1);
				}
			}

		}
		return tableI;
	}

	/*
	 * This method processes C label by taking an array called column is sorted from
	 * lower to higher value. processC returns the value in the middle.
	 */
	public static int processC(int[][] table, int[] column) {
		Arrays.sort(column);
		return column[(column.length + 1) / 2 - 1];
	}

	/*
	 * This method is called by processC, it returns the specific column wanted.
	 * Starting from 0, y coordinate is increased in every iteration and x
	 * coordinate is held constant. Each value is stored in an array called column.
	 * After y coordinate reaches the end of the table, column is returned.
	 */
	public static int[] getColumn(int[][] table, int[] column, int y, int x) {
		if (y < table.length) {
			column[y] = table[y][x];
			getColumn(table, column, y + 1, x);
		}
		return column;

	}

	/*
	 * This method processes D label, by calling 4 other methods, and they find the
	 * elements in 4 diagonal directions. The sum of the elements in diagonal
	 * directions are stored in the array sum. The number of them are stored in the
	 * array count. Since there are 4 directions in a 2D table, sum and count have a
	 * length of 4. After that, the integer in the corresponding cell of the table
	 * is changed to the average of these values.
	 */
	public static int[][] processD(int[][] tableI, int i) {
		int x = i % tableI[0].length;
		int y = i / tableI[0].length;
		int[] sum = new int[4];
		int[] count = new int[4];
		leftDownD(tableI, sum, y, x, count);
		rightDownD(tableI, sum, y, x, count);
		leftUpD(tableI, sum, y, x, count);
		rightUpD(tableI, sum, y, x, count);

		tableI[y][x] = (sum[0] + sum[1] + sum[2] + sum[3] - 3 * tableI[y][x])
				/ (count[0] + count[1] + count[2] + count[3] - 3);
		return tableI;
	}

	/*
	 * This method is one of the 4 methods that are called by processD, it checks
	 * the left down direction of the specific cell, and returns the sum of them in
	 * the array sum, and the number of them in the array count.
	 */
	public static int leftDownD(int[][] tableI, int[] sum, int y, int x, int[] count) {
		if (x < 0) {
			return 0;
		}
		if (y < tableI.length) {
			sum[0] += tableI[y][x];
			count[0]++;

		}
		return leftDownD(tableI, sum, y + 1, x - 1, count);
	}

	/*
	 * This method is one of the 4 methods that are called by processD, it checks
	 * the left up direction of the specific cell, and returns the sum of them in
	 * the array sum, and the number of them in the array count.
	 */

	public static int leftUpD(int[][] tableI, int[] sum, int y, int x, int[] count) {
		if (x < 0) {
			return 0;
		}
		if (y > -1) {
			sum[1] += tableI[y][x];
			count[1]++;

		}
		return leftUpD(tableI, sum, y - 1, x - 1, count);
	}

	/*
	 * This method is one of the 4 methods that are called by processD, it checks
	 * the right down direction of the specific cell, and returns the sum of them in
	 * the array sum, and the number of them in the array count.
	 */
	public static int rightDownD(int[][] tableI, int[] sum, int y, int x, int[] count) {
		if (x > tableI[0].length - 1) {
			return 0;
		}
		if (y < tableI.length) {
			sum[2] += tableI[y][x];
			count[2]++;

		}
		return rightDownD(tableI, sum, y + 1, x + 1, count);
	}

	/*
	 * This method is one of the 4 methods that are called by processD, it checks
	 * the right up direction of the specific cell, and returns the sum of them in
	 * the array sum, and the number of them in the array count.
	 */
	public static int rightUpD(int[][] tableI, int[] sum, int y, int x, int[] count) {
		if (x > tableI[0].length - 1) {
			return 0;
		}
		if (y > -1) {
			sum[3] += tableI[y][x];
			count[3]++;

		}
		return rightUpD(tableI, sum, y - 1, x + 1, count);
	}
}
