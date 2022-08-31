package flashcards;

import java.io.*;
import java.util.*;

import static flashcards.Messages.*;

public class FlashCards {

    Map<String, String> cards = new LinkedHashMap<>();

    private int numberOfExistingCards = 0;

    private int numberOfCards;
    private boolean end = true;

    private void setNumberOfCards() {
        printAskNumber();
        this.numberOfCards = Integer.parseInt(inputScanner());
    }
    private String inputScanner() {
        return new Scanner(System.in).nextLine();
    }

    public void runFlashCards() {
        while (end) {
            menuChoice();
        }
    }

    private String setStringOfCard(Set<String> set, String type) {
        if (type.equals(TERM)) {
            printSetTerm();
        }
        else {
            printSetDefinition();
        }
        while (true) {
            String string = inputScanner();
            if (set.contains(string)) {
                printAlreadyExists(type, string);
                continue;
            }
            return string;
        }
    }

    private void createCards() {
        cards.put(setStringOfCard(cards.keySet(), TERM),
                setStringOfCard(new HashSet<>(cards.values()), DEFINITION));
        for (var entry : cards.entrySet()) {
            System.out.printf("The pair (\"%s\":\"%s\") has been added.%n"
                    , entry.getKey(), entry.getValue());
        }
        numberOfExistingCards++;
    }

    private String getTermOfDefinition(String definition) {
        for (var entry : cards.entrySet()) {
            if (entry.getValue().equals(definition)) {
                return entry.getKey();
            }
        }
        return "";
    }

    private void wrongAnswer(String rightDefinition, String definition) {
        if (cards.containsValue(definition)) {
            printWrongAlreadyExists(rightDefinition, getTermOfDefinition(definition));
        } else {
            printWrong(rightDefinition);
        }
    }

    private void getFlashCards() {
        for (var entry : cards.entrySet()) {
            printGetDefinition(entry.getKey());
            String definition = inputScanner();
            if (definition.equals(entry.getValue())) {
                printRightDefinition();
            } else {
                wrongAnswer(entry.getValue(), definition);
            }
        }
    }

    private void menuChoice() {
        printMenu();
        switch (inputScanner()) {
            case "add" -> add();
            case "remove" -> remove();
            case "import" -> importToFile();
            case "export" -> exportFromFile();
            case "ask" -> ask();
            case "exit" -> exit();
        }
    }

    private void add() {
        createCards();
    }

    private void remove() {
        System.out.println("Which card?");
        String cardName = inputScanner();

        for (var entry : cards.entrySet()) {
            if (cardName.equals(entry.getKey())) {
                cards.remove(entry.getKey(), entry.getValue());
                printRemoveMenu();
            } else printNoRemove(cardName);
        }
    }

    public Map<String, String> HashMapFromTextFile(String importTxt)
    {
        BufferedReader br = null;
        try {
            // create file object
            File file = new File(importTxt);
            // create BufferedReader object from the File
            br = new BufferedReader(new FileReader(file));
            String line;
            // read file line by line
            while ((line = br.readLine()) != null) {

                // split the line by :
                String[] parts = line.split(":");

                // first part is name, second is number
                String name = parts[0].trim();
                String number = parts[1].trim();
                // put name, number in HashMap if they are
                // not empty
                if (!name.equals("") && !number.equals("")) {
                    numberOfExistingCards++;
                    cards.put(name, number);
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            // Always close the BufferedReader
            if (br != null) {
                try {
                    br.close();
                }
                catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
        }
        return cards;
    }

    private void importToFile() {
        printFileName();
        String txtNameFile = inputScanner();
        cards = HashMapFromTextFile(txtNameFile);
        printNumberOfCards(numberOfExistingCards);
    }

    private void HashMapToTextFile(String outputTxtFile) {
        File file = new File(outputTxtFile);
        try (BufferedWriter bf = new BufferedWriter(new FileWriter(file))) {
            // iterate map entries
            for (var entry : cards.entrySet()) {
                // put key and value separated by a colon
                bf.write(entry.getKey() + ":"
                        + entry.getValue());
                // new line
                bf.newLine();
            }
            numberOfExistingCards++;
            bf.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void exportFromFile() {
        printFileName();
        String outputTxtFile = inputScanner();
        HashMapToTextFile(outputTxtFile);
        printNumberOfCards(numberOfExistingCards);
    }

    private void ask() {
        setNumberOfCards();
        int i = 0;
        while (i < (numberOfCards / 2)) {
            getFlashCards();
            i += 2;
        }
    }

    private void exit() {
        end = false;
        printExitMenu();
    }
}