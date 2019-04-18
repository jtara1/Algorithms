package computability;

public class WellFormedParenthesisAlgorithm {
    /**
     * todo: invalid algo for input = input: [[(()])]
     * todo: fix by recursively validating inner string whenever there's a closed parenthesis or bracket
     * language = (, ), [, ]
     * @param string
     * @return
     */
    public boolean isAcceptedString(String string) {
        int parenCount = 0;
        int bracketCount = 0;
        int index = 0;

        for (char symbol : string.toCharArray()) {
            if (symbol == '(') ++parenCount;

            else if (symbol == ')') {
                if (index != 0 && string.charAt(index - 1) == '[') return false; // ([)] not allowed
                --parenCount;
            }

            else if (symbol == '[') ++bracketCount;

            else if (symbol == ']') {
                if (index != 0 && string.charAt(index - 1) == '(') return false; // [(]) // not allowed
                --bracketCount;
            }

            else return false; // symbol not in language

            if (parenCount < 0 || bracketCount < 0) return false; // ()) not allowed

            ++index;
        }

        return parenCount == 0 && bracketCount == 0;
    }
}
