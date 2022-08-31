package flashcards;

public class Messages {

    public static final String TERM = "card";
    public static final String DEFINITION = "definition";

    public static void printAskNumber() {
        System.out.println("How many times to ask?");
    }
    public static void printSetTerm() {
        System.out.println("The card:");
    }

    public static void printSetDefinition() {
        System.out.println("The definition of the card:");
    }

    public static void printGetDefinition(String term) {
        System.out.printf("Print the definition of \"%s\":%n", term);
    }

    public static void printRightDefinition() {
        System.out.println("Correct!");
    }

    public static void printWrongAlreadyExists(String definition, String term) {
        System.out.printf("Wrong. The right answer is \"%s\", but " +
                "your definition is correct for \"%s\".%n", definition, term);
    }

    public static void printWrong(String definition) {
        System.out.printf("Wrong. The right answer is \"%s\".%n", definition);
    }

    public static void printAlreadyExists(String type, String string) {
        System.out.printf("The %s \"%s\" already exists.%n", type, string);
    }

    public static void printMenu() {
        System.out.println("Input the action (add, remove, import, export, ask, exit):");
    }


    public static void printRemoveMenu() {
        System.out.println("The card has been removed.");
    }

    public static void printNoRemove(String term) {
        System.out.printf("Can't remove \"%s\": there is no such card.%n", term);
    }

    public static void printExitMenu() {
        System.out.println("Bye bye!");
    }

    public static void printFileName() {
        System.out.println("File name:");
    }

    public static void printNumberOfCards(int numberOfSavedCards) {
        System.out.printf("%d cards have been loaded.%n", numberOfSavedCards);
    }

    public static void  printNumberOfCardsSaved (int numberOfSavedCards) {
        System.out.printf("%d cards have been saved.%n", numberOfSavedCards);
    }

}
