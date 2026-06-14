package me.nuccio.chatgame.managers;

import me.nuccio.chatgame.Chatgame;
import java.util.*;

public class GameManager {

    private final Chatgame plugin;
    public enum GameType { NONE, MATH, GUESS, WORD }

    private GameType currentGame = GameType.NONE;
    private String currentAnswer = "";
    private long startTime;
    private final Random random = new Random();
    private List<String> randomWords = new ArrayList<>();

    public GameManager(Chatgame plugin) {
        this.plugin = plugin;
        loadWords();
    }

    public void loadWords() {
        if (plugin.getConfig().contains("random-words")) {
            this.randomWords = plugin.getConfig().getStringList("random-words");
        } else {
            this.randomWords = Arrays.asList("apple", "banana", "sword", "creeper");
        }
    }

    public GameType getCurrentGame() {
        return currentGame;
    }

    public String getCurrentAnswer() {
        return currentAnswer;
    }

    public long getStartTime() {
        return startTime;
    }

    public void stopGame() {
        this.currentGame = GameType.NONE;
        this.currentAnswer = "";
    }

    // Modificato per restituire la stringa visiva generata dal gioco (espressione o parola mescolata)
    public String startRandomGame() {
        int r = random.nextInt(3);
        if (r == 0) return startMath();
        else if (r == 1) return startGuess();
        else return startWord();
    }

    public String startMath() {
        int a = random.nextInt(90) + 10;
        int b = random.nextInt(9) + 1;
        String expression;
        int result;

        switch (random.nextInt(4)) {
            case 0:
                expression = a + " + " + b;
                result = a + b;
                break;
            case 1:
                expression = a + " - " + b;
                result = a - b;
                break;
            case 2:
                expression = a + " x " + b;
                result = a * b;
                break;
            default:
                result = a;
                expression = (a * b) + " / " + b;
                break;
        }

        this.currentGame = GameType.MATH;
        this.currentAnswer = String.valueOf(result);
        this.startTime = System.currentTimeMillis();
        return expression;
    }

    // Cambiato il ritorno in String per coerenza con gli altri metodi
    public String startGuess() {
        int number = random.nextInt(100) + 1;
        this.currentGame = GameType.GUESS;
        this.currentAnswer = String.valueOf(number);
        this.startTime = System.currentTimeMillis();
        return "";
    }

    public String startWord() {
        if (randomWords.isEmpty()) loadWords();

        String word = randomWords.get(random.nextInt(randomWords.size()));
        String scrambled = shuffleWord(word);

        this.currentGame = GameType.WORD;
        this.currentAnswer = word;
        this.startTime = System.currentTimeMillis();
        return scrambled;
    }

    private String shuffleWord(String word) {
        if (word.length() <= 1) return word; // Evita loop infiniti con parole di una sola lettera
        List<Character> chars = new ArrayList<>();
        for (char c : word.toCharArray()) chars.add(c);
        String shuffled;
        do {
            Collections.shuffle(chars);
            StringBuilder sb = new StringBuilder();
            for (char c : chars) sb.append(c);
            shuffled = sb.toString();
        } while (shuffled.equalsIgnoreCase(word));
        return shuffled;
    }
}