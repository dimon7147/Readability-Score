package readability;

public class ReadabilityCalculator {

    private final String text;

    ReadabilityCalculator(String text){
        this.text = text;
    }

    public void printTextInfo(){
        var temp = getSyllablesAndPolysyllables();
        System.out.println(
                "Words: " + getWordsAmount() + "\n" +
                "Sentences: " + getSentAmount() + "\n" +
                "Characters: " + getCharAmount() + "\n" +
                "Syllables: " + temp[0] + "\n" +
                "Polysyllables: " + temp[1] + "\n"
        );
    }

    private int getWordsAmount(){
        var sent = text
                .replace("! ", " ")
                .replace("? ", " ")
                .replace(". ", " ")
                .replace("  ", " ");
        return sent.split(" ").length;
    }

    private int getCharAmount(){
        return text.replace(" ","").length();
    }

    private int getSentAmount(){
        var sent = text.split("[.]|[!]|[?]");
        return sent.length;
    }

    private int[] getSyllablesAndPolysyllables() {
        String[] sentences = text.split("[!?.]\\s");
        int syllables = 0;
        int polysyllables = 0;
        String vowels = "aeiouy";
        for (String sentence : sentences) {
            for (String word : sentence.split("\\s")) {
                String temp = word.toLowerCase();
                int syllablesInWord = word.endsWith("e") ? -1 : 0;
                for (int i = 1; i <= temp.length(); i++) {
                    boolean contains = vowels.contains(String.valueOf(temp.charAt(i - 1)));
                    if (i == temp.length() && contains) {
                        syllablesInWord++;
                    }
                    if (contains && i != temp.length()) {
                        if (!vowels.contains(String.valueOf(temp.charAt(i)))) {
                            syllablesInWord++;
                        }
                    }
                }
                if (syllablesInWord <= 0) {syllablesInWord = 1;}
                syllables += syllablesInWord;
                if (syllablesInWord > 2) {
                    polysyllables++;
                }
            }
        }
        return new int[]{syllables, polysyllables};
    }

    public double calculateARI(){
        return 4.71 * ((double) getCharAmount()/ (double) getWordsAmount()) + 0.5 * ((double) getWordsAmount() / (double) getSentAmount()) - 21.43;
    }

    public double calculateFK(){
        return 0.39 * ((double) getWordsAmount()/ (double) getSentAmount()) + 11.8 * ((double) getSyllablesAndPolysyllables()[0]/ (double) getWordsAmount()) - 15.59;
    }

    public double calculateSMOG(){
        return 1.043 * Math.sqrt(getSyllablesAndPolysyllables()[1] * (30 / (double) getSentAmount())) + 3.1291;
    }

    public double calculateCL(){
        return 0.0588 * (double) getCharAmount() / (double) getWordsAmount() * 100 - 0.296 * (double) getSentAmount() / (double) getWordsAmount() * 100 - 15.8;
    }
}
