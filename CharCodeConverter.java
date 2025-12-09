import java.util.HashMap;
import java.util.Scanner;

public class CharCodeConverter {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        // HashMap for caching previous results
        HashMap<String, String> lookupCache = new HashMap<>();

        while (true) {
            System.out.print("Enter a single character or emoji (or type 'exit' to quit): ");
            String characterInput = input.nextLine();

            if (characterInput.equalsIgnoreCase("exit")) {
                break;
            }
            if (characterInput.length() == 0) {
                System.out.println("No input detected. Please try again.");
                continue;
            }

            // Get the first character as a String (to support emojis or multi-byte characters) 
            String symbol = characterInput;

            // Check if we've seen this before
            if (lookupCache.containsKey(symbol)) {
                System.out.println("Cached result: " + lookupCache.get(symbol));
                continue;
            }

            System.out.print("Type 'ascii' or 'unicode': ");
            String choice = input.nextLine().toLowerCase();

            String result;

            if (choice.equals("ascii")) {
                // ASCII applies only to single-byte character (0-127)
                char c = symbol.charAt(0);
                int asciiValue = (int) c;

                if (asciiValue >= 0 && asciiValue <= 127) {
                    result = "ASCII code for '" + symbol + "' is: " + asciiValue;
                } else {
                    result = "That character is not in the ASCII range (0-127).";
                }
            } else if (choice.equals("unicode")) {
                int[] codePoints = symbol.codePoints().toArray();
                StringBuilder sb = new StringBuilder();
                for (int codePoint : codePoints) {
                    sb.append(String.format("U+%04X", codePoint)).append("");
                }
                result = "Unicode code point(s) for '" + symbol + "': " + sb.toString().trim();
            } else {
                result = "Invalid choice. Please type 'ascii' or 'unicode'.";
            }

            // Save to Cache and show result
            lookupCache.put(symbol, result);
            System.out.println(result);
        }

        input.close();
        System.out.println("Program ended.");
    }
}
