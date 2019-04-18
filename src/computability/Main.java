package computability;

public class Main {
    public static void main(String[] args) {
        WellFormedParenthesisAlgorithm algo = new WellFormedParenthesisAlgorithm();
        String[] inputs = {
                "()",
                "()[]",
                "([)]",
                "[([([()])])]",
                "([()([])()][()(())]())",
                "([()([])())[()(())]())",
                "[[(()])]",
        };

        for (String input : inputs) {
            System.out.println("input: " + input);
            System.out.println(algo.isAcceptedString(input));
        }
    }
}
