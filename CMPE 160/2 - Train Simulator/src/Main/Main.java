package Main;

import java.util.*;
import java.io.*;
import CargoTrain.*;
import Util.*;

public class Main {
	public static void main(String[] args) throws FileNotFoundException {
		readAndInitialize(args[0], args[1]);
	}

	public static void readAndInitialize(String input, String output) throws FileNotFoundException {
		Scanner l = new Scanner(new File(input));
		PrintStream p = new PrintStream(output);
		String line = l.nextLine();
		Scanner s = new Scanner(line);
		int length = s.nextInt();
		int carCapacity = s.nextInt();
		int stationCount = s.nextInt();

		Station[] stations = new Station[stationCount];
		for (int i = 0; i < stationCount; i++) {
			stations[i] = new Station(i, p);
		}

		Train t = new Train(length, carCapacity);

		while (l.hasNextLine()) {
			line = l.nextLine();
			s = new Scanner(line);
			int id = s.nextInt();
			int departureID = s.nextInt();
			int arrivalID = s.nextInt();
			int size = s.nextInt();

			Cargo c = new Cargo(id, size, stations[departureID], stations[arrivalID]);

			stations[departureID].add(c);

		}
		s.close();
		l.close();
		execute(t, stations);

	}

	public static void execute(Train train, Station[] stations) throws FileNotFoundException {
		for (int i = 0; i < stations.length; i++) {
			stations[i].process(train);
		}
	}
}