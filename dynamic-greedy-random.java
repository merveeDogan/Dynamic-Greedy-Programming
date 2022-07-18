import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Merve_Doðan_2019510028 {
	public static void fileReading(String fileName, ArrayList<Hero> heros) throws Exception {
		// this function read csv file and add to hero list
		BufferedReader csvReader = new BufferedReader(new FileReader(fileName));
		String row = null;
		csvReader.readLine(); // for skip first info type line
		while ((row = csvReader.readLine()) != null) {
			String[] data = row.split(",");
			heros.add(new Hero(data[0], data[1], Integer.parseInt(data[2]), Integer.parseInt(data[3]), 0));
		}
		csvReader.close();
		findLevelOfPieces(heros);
	}

	public static void findLevelOfPieces(ArrayList<Hero> heros) {
		// this function assigned the level of the stones and the number of stones in
		// their own level
		int p = 1, r = 1, a = 1, k = 1, b = 1, w = 1, s = 1, q = 1, kk = 1;
		for (int i = 0; i < heros.size(); i++) {
			if (heros.get(i).getPieceType().equalsIgnoreCase("pawn")) {
				heros.get(i).setLevel(1);
				heros.get(i).setLevelOrder(p);
				p++;
			} else if (heros.get(i).getPieceType().equalsIgnoreCase("rook")) {
				heros.get(i).setLevel(2);
				heros.get(i).setLevelOrder(r);
				r++;
			} else if (heros.get(i).getPieceType().equalsIgnoreCase("archer")) {
				heros.get(i).setLevel(3);
				heros.get(i).setLevelOrder(a);
				a++;
			} else if (heros.get(i).getPieceType().equalsIgnoreCase("knight")) {
				heros.get(i).setLevel(4);
				heros.get(i).setLevelOrder(k);
				k++;
			} else if (heros.get(i).getPieceType().equalsIgnoreCase("bishop")) {
				heros.get(i).setLevel(5);
				heros.get(i).setLevelOrder(b);
				b++;
			} else if (heros.get(i).getPieceType().equalsIgnoreCase("war_ship")) {
				heros.get(i).setLevel(6);
				heros.get(i).setLevelOrder(w);
				w++;
			} else if (heros.get(i).getPieceType().equalsIgnoreCase("siege")) {
				heros.get(i).setLevel(7);
				heros.get(i).setLevelOrder(s);
				s++;
			} else if (heros.get(i).getPieceType().equalsIgnoreCase("queen")) {
				heros.get(i).setLevel(8);
				heros.get(i).setLevelOrder(q);
				q++;
			} else if (heros.get(i).getPieceType().equalsIgnoreCase("king")) {
				heros.get(i).setLevel(9);
				heros.get(i).setLevelOrder(kk);
				kk++;
			}

		}
	}

	public static ArrayList<Hero> takingAvailableHeros(ArrayList<Hero> heros, int maxLevel, int piecesNum) {
		// this function return a list of stones that can be used according to the level
		// and number of stones selected by the user
		ArrayList<Hero> availableHeros = new ArrayList<Hero>();
		for (Hero heros1 : heros) {
			if (heros1.getLevel() <= maxLevel) {
				if (heros1.getLevelOrder() <= piecesNum)
					availableHeros.add(heros1);
			} else
				break;
		}

		return availableHeros;
	}

	public static void main(String[] args) throws Exception {
		Scanner scan = new Scanner(System.in);
		int goldAmount = 0, maximumAllowedLevel = 0, numberOfPieces = 0;
		// input range controls
		while (goldAmount < 5 || goldAmount > 1200) {
			System.out.print(">>> Please enter the gold amount: ");
			try {
				goldAmount = scan.nextInt();
			} catch (Exception e) {
				System.out.println("Invalid entry!");
				System.exit(1);
			}
		}
		while (maximumAllowedLevel < 1 || maximumAllowedLevel > 9) {
			System.out.print(">>> please enter the max level for pieces: ");
			try {
				maximumAllowedLevel = scan.nextInt();
			} catch (Exception e) {
				System.out.println("Invalid entry!");
				System.exit(1);
			}
		}
		while (numberOfPieces < 1 || numberOfPieces > 25) {
			System.out.print(">>> please enter the number of pieces for each level ");
			try {
				numberOfPieces = scan.nextInt();
			} catch (Exception e) {
				System.out.println("Invalid entry!");
				System.exit(1);
			}
		}
		scan.close();

		// TODO Auto-generated method stub
		ArrayList<Hero> hero = new ArrayList<Hero>();
		fileReading("input_1.csv", hero);
		ArrayList<Hero> availableHeros = takingAvailableHeros(hero, maximumAllowedLevel, numberOfPieces);
		System.out.println("\n------------------------TRIAL #1 ------------------------");
		ApproachesTypes.greedyApproach(availableHeros, goldAmount, maximumAllowedLevel, numberOfPieces);
		availableHeros = takingAvailableHeros(hero, maximumAllowedLevel, numberOfPieces);
		ApproachesTypes.dynamicProgrammingApproach(availableHeros, goldAmount, maximumAllowedLevel, numberOfPieces);
		System.out.println("\n\n------------------------TRIAL #2 ------------------------");
		availableHeros = takingAvailableHeros(hero, maximumAllowedLevel, numberOfPieces);
		ApproachesTypes.randomApproach(availableHeros, goldAmount, maximumAllowedLevel, numberOfPieces);
		availableHeros = takingAvailableHeros(hero, maximumAllowedLevel, numberOfPieces);
		ApproachesTypes.dynamicProgrammingApproach(availableHeros, goldAmount, maximumAllowedLevel, numberOfPieces);

	}

}

class Hero {
	private String name;
	private String pieceType;
	private int gold;
	private int attackPoints;
	private int level; // I added the variables level and levelOrder to make it easier for the add
						// availableHeros list
	private int levelOrder;
	private boolean available;

	public Hero(String name, String pieceType, int gold, int attackPoints, int level) {
		super();
		this.name = name;
		this.pieceType = pieceType;
		this.gold = gold;
		this.attackPoints = attackPoints;
		this.level = level;
		this.available = true;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPieceType() {
		return pieceType;
	}

	public void setPieceType(String pieceType) {
		this.pieceType = pieceType;
	}

	public int getGold() {
		return gold;
	}

	public void setGold(int gold) {
		this.gold = gold;
	}

	public int getAttackPoints() {
		return attackPoints;
	}

	public void setAttackPoints(int attackPoints) {
		this.attackPoints = attackPoints;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public int getLevelOrder() {
		return levelOrder;
	}

	public void setLevelOrder(int levelOrder) {
		this.levelOrder = levelOrder;
	}

	public int compareTo(Hero hero) {
		if ((float) this.attackPoints / this.gold < (float) hero.attackPoints / hero.gold)
			return 1;
		return -1;
	}

	public boolean isAvailable() {
		return available;
	}

	public void setAvailable(boolean available) {
		this.available = available;
	}

}

class ApproachesTypes {
	static long startTime = 0, elapsedTime = 0;

	public static void dynamicProgrammingApproach(ArrayList<Hero> heros, int goldAmount, int maxLevelAllowed,
			int numOfPieces) { // dynamic approach function
		startTime = System.nanoTime();
		int initialGold = goldAmount;
		int dynamic[][] = new int[heros.size() + 1][goldAmount + 1];
		// By filling the dynamic matrix with the best cases, we reach the stones that
		// can be obtained in the last step
		String direction[][] = new String[heros.size() + 1][goldAmount + 1];
		// In order to follow the order in the print process, I created a direction
		// array and saved the directions here
		for (int i = 1; i < dynamic.length; i++) {
			// I make a decision by comparing the possible stone situations with my
			// remaining money and
			// determine its position in direction array and place in the dynamic array.
			Hero hero = heros.get(i - 1);
			for (int j = 1; j < dynamic[i].length; j++) {

				if (hero.getGold() > j) {
					dynamic[i][j] = dynamic[i - 1][j];
					direction[i][j] = "North";
				} else if (hero.getGold() <= j) {
					dynamic[i][j] = Math.max(dynamic[i - 1][j],
							hero.getAttackPoints() + dynamic[i - hero.getLevelOrder()][j - hero.getGold()]);
					if (hero.getAttackPoints()
							+ dynamic[i - hero.getLevelOrder()][j - hero.getGold()] >= dynamic[i - 1][j]) {
						direction[i][j] = "NorthWest";
					} else {
						direction[i][j] = "North";
					}
				}

			}
		}
		// elapsed time calculation
		elapsedTime = System.nanoTime() - startTime;
		System.out.println("\n\n*******User's Dynamic Approach results*******");
		printDynamicSolution(heros, dynamic, direction, dynamic.length - 1, dynamic[0].length - 1, 0, 0, initialGold);
		System.out.println("\n> Dynamic approach elapsed time: " + (double) elapsedTime / (double) 1000000);
	}

	private static void printDynamicSolution(ArrayList<Hero> heros, int dynamic[][], String direction[][], int x, int y,
			int goldAmount, int attackPoints, int initialGold) {
		// print function for dynamic programming
		// I am printing the stones according to the order from the direction array
		// recursively
		if (dynamic[x][y] == 0) {
			System.out.println(" -->Initial gold : " + initialGold);
			System.out.println(" -->Amount Of Gold Spent : " + goldAmount);
			System.out.print(" -->Total Attack Points: " + attackPoints + "\n\n Players: Hero          "
					+ "                   Piece Type    Gold     Attack Points        ");
			return;
		}

		if (direction[x][y].equalsIgnoreCase("NorthWest")) {
			Hero hero = heros.get(x - 1);
			printDynamicSolution(heros, dynamic, direction, x - hero.getLevelOrder(), y - hero.getGold(),
					goldAmount + hero.getGold(), attackPoints + hero.getAttackPoints(), initialGold);
			System.out.println();
			System.out.print("       -->" + hero.getName());
			Alignment(hero.getName());
			System.out.print(String.format("%20s", hero.getPieceType()));
			System.out.print(String.format("%8d", hero.getGold()));
			System.out.print(String.format("%12d", hero.getAttackPoints()));

		} else if (direction[x][y].equalsIgnoreCase("North")) {
			printDynamicSolution(heros, dynamic, direction, x - 1, y, goldAmount, attackPoints, initialGold);
		}
	}

	public static void randomApproach(ArrayList<Hero> heros, int goldAmount, int maxLevelAllowed, int numOfPieces) {
		// random approach function
		// makes a random selection until a player who can't afford to get a stone
		// arrives and remainGold>0
		startTime = System.nanoTime();
		int totalAttack = 0;
		int initialGold = goldAmount;
		List<Hero> winners = new ArrayList<Hero>();
		Hero winnerHero = null;
		Random random = new Random();
		int randomElement = 0;
		int level = 0;
		if (numOfPieces > 10) {// In order not to select a null stone because there are 10 stones at the most.
			numOfPieces = 10;
		}

		while (goldAmount >= 0 && level < maxLevelAllowed) {
			randomElement = random.nextInt(numOfPieces);
			randomElement = randomElement + level * numOfPieces;
			winnerHero = heros.get(randomElement);
			goldAmount = goldAmount - winnerHero.getGold();
			if (winnerHero.getGold() <= goldAmount) {
				winners.add(winnerHero);
				totalAttack += winnerHero.getAttackPoints();
			} else {
				goldAmount = goldAmount + winnerHero.getGold();
			}
			level++;
		} // elapsed time calculation
		elapsedTime = System.nanoTime() - startTime;
		System.out.println("\n\n*******Computer's Random Approach results*******");
		print(initialGold, goldAmount, totalAttack, winners);
		System.out.println("\n> Random approach elapsed time: " + (double) elapsedTime / (double) 1000000);

	}

	public static void greedyApproach(ArrayList<Hero> heros, int goldAmount, int maxLevelAllowed, int numOfPieces) {
		// greedy approach function
		// It ranks the players who can get stones according to the attackPoint/gold
		// ratio,
		// and the player receives stones from different levels, respectively, until the
		// money runs out
		startTime = System.nanoTime();
		Collections.sort(heros, new Comparator<Hero>() {
			@Override
			public int compare(Hero hero1, Hero hero2) {
				return hero1.compareTo(hero2);
			}
		});
		List<Hero> winners = new ArrayList<Hero>();
		int initialGold = goldAmount;
		int totalAttack = 0;
		boolean[] availableLevels = new boolean[maxLevelAllowed];
		Arrays.fill(availableLevels, true);
		for (int i = 0; i < heros.size(); i++) {
			if (heros.get(i).getGold() <= goldAmount && availableLevels[heros.get(i).getLevel() - 1]) {
				winners.add(heros.get(i));
				goldAmount -= heros.get(i).getGold();
				totalAttack += heros.get(i).getAttackPoints();
				availableLevels[heros.get(i).getLevel() - 1] = false;
			}
		}
		// elapsed time calculation
		elapsedTime = System.nanoTime() - startTime;
		System.out.println("\n*******Computer's Greedy Approach results*******");
		print(initialGold, goldAmount, totalAttack, winners);
		System.out.println("\n> Greedy approach elapsed time: " + (double) elapsedTime / (double) 1000000);

	}

	private static void print(int initialGold, int goldAmount, int totalAttack, List<Hero> winners) {
		// print function for random and greedy approach
		System.out.println();
		System.out.println(" -->Initial gold : " + initialGold);
		System.out.println(" -->Amount Of Gold Spent  : " + (initialGold - goldAmount));
		System.out.print(" -->Total Attack Points: " + totalAttack + "\n\n Players: Hero          "
				+ "                   Piece Type    Gold     Attack Points        ");
		for (int i = 0; i < winners.size(); i++) {
			System.out.println();
			System.out.print("       -->" + winners.get(i).getName());
			Alignment(winners.get(i).getName());
			System.out.print(String.format("%20s", winners.get(i).getPieceType()));
			System.out.print(String.format("%8d", winners.get(i).getGold()));
			System.out.print(String.format("%12d", winners.get(i).getAttackPoints()));
		}
	}

	private static void Alignment(String name) {
		// alignment function to align names
		for (int i = 0; i < 23 - name.length(); i++) {
			System.out.print(" ");
		}
	}

}
