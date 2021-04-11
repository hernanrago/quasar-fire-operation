package org.rebelalliance;

import java.util.Arrays;
import java.util.Scanner;

public class Operation {
	static Scanner input;
	static final String KENOBI = "Kenobi";
	static final String SKYWALKER = "Skywalker";
	static final String SATO = "Sato";

	public static void main(String[] args) throws InterruptedException {
		input = new Scanner(System.in);
		String cont = "";

		System.out.println("*********************************************");
		System.out.println("***Welcome to Quasar Fire Operation System***");
		System.out.println("*********************************************");
		Thread.sleep(2000);

		do {
			String option = "";
			boolean wrongOption = true;
			while (wrongOption) {
				System.out.println("Enter 1 to obtain the position of the imperial load carrier.");
				System.out.println("Enter 2 to get the secret message.");
				option = input.nextLine();

				if (option.equals("1") || option.equals("2"))
					wrongOption = false;
				else
					System.out.println("You entered a wrong option.");
				Thread.sleep(1000);
			}

			switch (option) {
			case "1":
				float kenobiDist, skywalkerDist, satoDist = 0;
				
				System.out.println("You selected to obtain imperial load carrier position.");
				Thread.sleep(2000);

				kenobiDist = getSatelliteDistance(KENOBI);

				skywalkerDist = getSatelliteDistance(SKYWALKER);

				satoDist = getSatelliteDistance(SATO);

				float[] coordinates = getLocation(kenobiDist, skywalkerDist, satoDist);

				System.out.println(
						"Imperial load carrier coordinates are: " + "x: " + coordinates[0] + " y: " + coordinates[1]);
				Thread.sleep(1000);
				break;

			case "2":
				String[] kenobiMess, skywalkerMess, satoMess;
				
				System.out.println("You selected to get the secret message.");
				Thread.sleep(2000);
				
				kenobiMess = getSatelliteMessage(KENOBI);
				skywalkerMess = getSatelliteMessage(SKYWALKER);
				satoMess = getSatelliteMessage(SATO);
				
				try {
					String mess = getMessage(kenobiMess, skywalkerMess, satoMess);
					System.out.printf("The secret message is: %s%n", mess);
				}catch(Exception e) {
					System.out.println(e.getMessage());
				}

			default:
				break;
			}

			System.out.println("Press any key to continue or q to quit.");
			cont = input.nextLine();
		} while (!cont.equals("q"));

		System.out.println("Goodbye brave rebel! May the Force be with you!");
		System.exit(0);
	}

	private static float getSatelliteDistance(String satelliteName) {
		float value = 0;
		boolean ok = false;

		while (!ok) {
			System.out.printf("Enter %s distance: ", satelliteName);
			try {
				value = Float.parseFloat(input.nextLine());
				ok = true;
			} catch (NumberFormatException nfe) {
				System.out.println("You entered an invalid value.");
			}
		}
		return value;
	}

	private static float[] getLocation(float... distances) {
		float kenobiX, kenobiY, skywalkerX, skaywalkerY, satoX, satoY, carrierX, carrierY, kenobiDistance,
				skywalkerDistance, satoDistance;

		kenobiX = -500.0f;
		kenobiY = -200f;
		skywalkerX = 100f;
		skaywalkerY = -100f;
		satoX = 500f;
		satoY = 100f;

		kenobiDistance = distances[0];
		skywalkerDistance = distances[1];
		satoDistance = distances[2];

		float a1Sq = kenobiX * kenobiX, a2Sq = skywalkerX * skywalkerX, a3Sq = satoX * satoX, b1Sq = kenobiY * kenobiY,
				b2Sq = skaywalkerY * skaywalkerY, b3Sq = satoY * satoY, r1Sq = kenobiDistance * kenobiDistance,
				r2Sq = skywalkerDistance * skywalkerDistance, r3Sq = satoDistance * satoDistance;

		float numerator1 = (skywalkerX - kenobiX) * (a3Sq + b3Sq - r3Sq) + (kenobiX - satoX) * (a2Sq + b2Sq - r2Sq)
				+ (satoX - skywalkerX) * (a1Sq + b1Sq - r1Sq);

		float denominator1 = 2
				* (satoY * (skywalkerX - kenobiX) + skaywalkerY * (kenobiX - satoX) + kenobiY * (satoX - skywalkerX));

		carrierY = numerator1 / denominator1;

		float numerator2 = r2Sq - r1Sq + a1Sq - a2Sq + b1Sq - b2Sq - 2 * (kenobiY - skaywalkerY) * carrierY;

		float denominator2 = 2 * (kenobiX - skywalkerX);

		carrierX = numerator2 / denominator2;

		float[] coordinates = { carrierX, carrierY };

		return coordinates;
	}

	private static String[] getSatelliteMessage(String satelliteName) {
		int messageLength = 0;
		boolean ok = false;

		while (!ok) {
			System.out.printf("Enter %s message length: ", satelliteName);
			try {
				messageLength = Integer.parseInt(input.nextLine());
				ok = true;

			} catch (NumberFormatException nfe) {
				System.out.println("You entered an invalid value.");
			}
		}

		String[] message = new String[messageLength];

		for (int i = 0; i < message.length; i++) {
			System.out.printf("Enter word #%s. Whitespace if none: ", i + 1);
			message[i] = input.nextLine();
		}

		return message;
	}

	private static String getMessage(String[]... messages) throws Exception {
		String[] messArr = new String[getSize(messages)];

		for (int i = 0; i < messArr.length; i++) {
			for (String[] arr : messages) {
				int messageDelay = arr.length - messArr.length;
				if (!arr[i + messageDelay].isBlank() || !arr[i + messageDelay].isEmpty())
					messArr[i] = arr[i + messageDelay];
			}
		}
		
		if(Arrays.stream(messArr).anyMatch(s -> s == null || s.isEmpty()))
			throw new Exception("Secret message could not be obtained.");

		return String.join(" ", messArr);
	}

	private static int getSize(String[]... messages) {
		if (messages.length == 1)
			return messages[0].length;
		else {
			int min = messages[0].length < messages[1].length ? messages[0].length : messages[1].length;
			for (String[] m : messages) {
				if (m.length < min)
					min = m.length;
			}
			return min;
		}
	}
}
