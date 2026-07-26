import java.util.Scanner;
import java.util.ArrayList;

public class CountCharacters {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Read input string from the user
        System.out.print("Enter a string: ");
        String input = scanner.nextLine();

        // Lists to store character breakdowns
        ArrayList<Character> vowelList = new ArrayList<>();
        ArrayList<Character> consonantList = new ArrayList<>();
        ArrayList<Character> digitList = new ArrayList<>();
        ArrayList<Character> specialCharList = new ArrayList<>();

        int spacesCount = 0;

        // Traverse the string character by character
        for (int i = 0; i < input.length(); i++) {
            char ch = input.charAt(i);

            if (Character.isLetter(ch)) {
                char lowerCh = Character.toLowerCase(ch);
                if (lowerCh == 'a' || lowerCh == 'e' || lowerCh == 'i' || lowerCh == 'o' || lowerCh == 'u') {
                    vowelList.add(ch);
                } else {
                    consonantList.add(ch);
                }
            } else if (Character.isDigit(ch)) {
                digitList.add(ch);
            } else if (Character.isWhitespace(ch)) {
                spacesCount++;
            } else {
                specialCharList.add(ch);
            }
        }

        // Print Summary Counts
        System.out.println("Vowels: " + vowelList.size());
        System.out.println("Consonants: " + consonantList.size());
        System.out.println("Digits: " + digitList.size());
        System.out.println("Special Characters: " + specialCharList.size());

        // Print Detailed Breakdown
        System.out.println("\nBreakdown for \"" + input + "\":");
        System.out.println("● Vowels: " + formatList(vowelList) + " → " + vowelList.size());
        System.out.println("● Consonants: " + formatList(consonantList) + " → " + consonantList.size());
        System.out.println("● Digits: " + formatList(digitList) + " → " + digitList.size());

        // Special characters description with space note if present
        String specialDetails = formatList(specialCharList);
        if (spacesCount > 0) {
            System.out.println("● Special Characters: space (x" + spacesCount + "), " + specialDetails + " → " + specialCharList.size());
        } else {
            System.out.println("● Special Characters: " + specialDetails + " → " + specialCharList.size());
        }

        scanner.close();
    }

    // Helper method to format array lists as comma-separated strings
    private static String formatList(ArrayList<Character> list) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            sb.append(list.get(i));
            if (i < list.size() - 1) {
                sb.append(", ");
            }
        }
        return sb.toString();
    }
}
