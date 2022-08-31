package flashcards;

import java.io.*;
import java.util.*;

import static flashcards.Messages.*;
import static flashcards.Messages.printNumberOfCardsSaved;

public class FlashCards {

    Map<String, String> cards = new LinkedHashMap<>();
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
        String string = inputScanner();
        if (set.contains(string)) {
            printAlreadyExists(type, string);
            runFlashCards();
        }
        return string;

    }

    private void createCards() {
        int count = 1;
        cards.put(setStringOfCard(cards.keySet(), TERM),
                setStringOfCard(new HashSet<>(cards.values()), DEFINITION));
        for (var entry : cards.entrySet()) {
            if (count == cards.size()) {
                System.out.printf("The pair (\"%s\":\"%s\") has been added.%n"
                        , entry.getKey(), entry.getValue());
            } count++;
        }
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

    private void getFlashCards(int number) {
        int i = 0;
        for (var entry : cards.entrySet()) {
            if (i < number) {
                printGetDefinition(entry.getKey());
                String definition = inputScanner();
                if (definition.equals(entry.getValue())) {
                    printRightDefinition();
                } else {
                    wrongAnswer(entry.getValue(), definition);
                }
                i++;
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
            case "exit" -> exitApp();
        }
    }

    private void add() {
        createCards();
    }

    private void remove() {
//        boolean remove = true;
        System.out.println("Which card?");
        String cardName = inputScanner();
        Iterator<String> it = cards.keySet().iterator();
        while (it.hasNext())
            if (it.next().equalsIgnoreCase(cardName))
                break;
        while (it.hasNext()) {
            it.next();
            it.remove();
            printRemoveMenu();
        } printNoRemove(cardName);

//        for (var entry : cards.entrySet()) {
//            if (cardName.equals(entry.getKey())) {
//                cards.remove(entry.getKey(), entry.getValue());
//                printRemoveMenu();
//                remove = false;
//            }
//        } if (remove) {
//            printNoRemove(cardName);
//        }
    }

    public Map<String, String> HashMapFromTextFile(String importTxt)
    {
        BufferedReader br = null;
        try {
            File file = new File(importTxt);
            br = new BufferedReader(new FileReader(file));
            String line;
            while ((line = br.readLine()) != null) {

                String[] parts = line.split(":");
                String term = parts[0].trim();
                String definition = parts[1].trim();

                if (!term.equals("") && !definition.equals("")) {
                    cards.put(term, definition);
                }
            }
        }
        catch (Exception e) {
            System.out.println("File not found.");
        } finally {
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
        int cardsSize = cards.size();
        printFileName();
        String txtNameFile = inputScanner();
        cards = HashMapFromTextFile(txtNameFile);
        printNumberOfCards(cards.size() - cardsSize);
    }

    private void HashMapToTextFile(String outputTxtFile) {
        File file = new File(outputTxtFile);
        try (BufferedWriter bf = new BufferedWriter(new FileWriter(file, true))) {

            for (var entry : cards.entrySet()) {
                bf.write(entry.getKey() + ":" + entry.getValue());
                bf.newLine();
            }
            bf.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void exportFromFile() {
        printFileName();
        String outputTxtFile = inputScanner();
        HashMapToTextFile(outputTxtFile);
        printNumberOfCardsSaved(cards.size());
    }

    private void ask() {
        setNumberOfCards();
        getFlashCards(numberOfCards);
    }

    private void exitApp() {
        end = false;
        printExitMenu();
        System.exit(0);
    }
}