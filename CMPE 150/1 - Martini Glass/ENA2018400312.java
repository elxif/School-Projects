public class ENA2018400312 {
	public static void main(String args[]) {

		int glassSize = Integer.parseInt(args[0]);
		int strawPos = Integer.parseInt(args[1]);

		glass(glassSize, strawPos);

	}

	/*
	 * I created a method which prints as many spaces as I want. That way, I won't
	 * need to write many for loops to print spaces in other methods.
	 */
	public static void spaces(int spc) {
		for (int i = 1; i <= spc; i++) // spc is the count of the spaces I want printed
			System.out.print(" ");
	}

	/*
	 * This method prints the outside part of the straw according to the position
	 * determined by strawPos. It leaves a space which increases by one every line.
	 * Then it prints an "o".
	 */
	public static void strawOut(int strawPos) {
		for (int level = 1; level <= strawPos; level++) { // 'level' is the level of the straw outside of the glass
			spaces(level - 1);
			System.out.println("o");
		}
	}

	/*
	 * The method below prints the glass and the inside of it. The variable 'step'
	 * determines how many times the output (i.e. the glass) is going to be
	 * produced.
	 */
	public static void glass(int glassSize, int strawPos) {
		for (int step = 0; step <= glassSize - (strawPos + 1) / 2 + 1; step++) { //step is the stage of fullness of the glass
			strawOut(strawPos);

			/*
			 * Below, firstly the outer part of the straw is printed. Then the rules for the
			 * upper part of the glass is created. The variable 'level' determines how many
			 * lines are going to be printed. The if statement determines whether that level
			 * of glass is full with liquid or empty. If empty, the right combination of
			 * spaces and "o" letter and spaces again will be printed. If full, the right
			 * amount of liquid will be printed with the else statement.
			 */

			for (int level = 1; level <= glassSize; level++) { // 'level' is the level of the glass starting from the brim														
				spaces(level - 1);
				System.out.print("\\");
				if (step >= level) {
					spaces(strawPos - 1);
					System.out.print("o");
					spaces(2 * (glassSize - level + 1) - strawPos);
				} else
					for (int i = 1; i <= glassSize - level + 1; i++) //"*" is printed i times, which changes every level according to glassSize and level
						System.out.print("**");

				System.out.println("/");

			}
			/*
			 * These couple of lines of code at the bottom prints the stem of the glass. It
			 * leaves as many spaces as glassSize every line, then prints "--" one time,
			 * then prints "||" as many times as glassSize.
			 */
			spaces(glassSize);
			System.out.println("--");
			for (int level = 1; level <= glassSize; level++) { //'level' is the level of the stem of the glass
				spaces(glassSize);
				System.out.println("||");
			}
		}
	}
}