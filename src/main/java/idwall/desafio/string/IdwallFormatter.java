package idwall.desafio.string;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by Rodrigo Catão Araujo on 06/02/2018.
 */
public class IdwallFormatter extends StringFormatter {

    public IdwallFormatter(Integer limit, Boolean justify) {
        setLimit(limit);
        setJustify(justify);
    }

    /**
     * Should format as described in the challenge
     *
     * @param text
     * @return
     */
    @Override
    public String format(String text) {
        StringBuilder lineWithMaxLength = new StringBuilder();
        String words[] = text.split(" ");
        breakLinesWithMaxLength(lineWithMaxLength, words);

        if(getJustify()){
            StringBuilder textJustified = new StringBuilder();
            for(String line:lineWithMaxLength.toString().split("\n")){
                if(line.equals("")){
                    textJustified.append("\n");
                }else{
                    int lineLength = line.length();

                    String ws[] = line.split(" ");
                    LinkedHashMap<Map<Integer, String>, Integer> wordSpaces = identifySpacesJustify(lineLength, ws);
                    reinsertSpacesJustify(textJustified, ws, wordSpaces);
                }
                textJustified.append("\n");
            }

            return textJustified.toString();
        }

        return lineWithMaxLength.toString();
    }

    private void breakLinesWithMaxLength(StringBuilder lineWithMaxLength, String[] words) {
        int sum = 0;
        for(String word:words){
            int appendWordLenght=0;

            String wordBreakline[] = null;
            if(word.contains("\n\n")){
                wordBreakline = word.split("\n\n");
                appendWordLenght = sum + wordBreakline[0].length();
            }else{
                appendWordLenght = sum+word.length();
            }

            if(appendWordLenght>=getLimit()){
                if(wordBreakline!=null){
                    lineWithMaxLength.append(word);

                    sum=wordBreakline[1].length();
                }else{
                    lineWithMaxLength.append("\n");
                    lineWithMaxLength.append(word);

                    sum=word.length();
                }

            }else{
                if(wordBreakline!=null){
                    lineWithMaxLength.append(" ");
                    lineWithMaxLength.append(word);

                    sum=wordBreakline[1].length();
                }else{
                    if(!words[0].equals(word)){
                        lineWithMaxLength.append(" ");
                        sum++;
                    }

                    lineWithMaxLength.append(word);
                    sum+=word.length();
                }
            }

        }
    }

    private void reinsertSpacesJustify(StringBuilder returnJust, String[] ws, LinkedHashMap<Map<Integer, String>, Integer> wordSpaces) {
        for (int i = 0; i < ws.length; i++) {
            String word = ws[i];
            returnJust.append(word);
            if (i == ws.length) {
                continue;
            }
            Map<Integer, String> keyPosition = getKeyPosition(wordSpaces, i);
            returnJust.append(spaces(wordSpaces.get(keyPosition)));
        }
    }

    private LinkedHashMap<Map<Integer, String>, Integer> identifySpacesJustify(int lineLength, String[] ws) {
        LinkedHashMap<Map<Integer, String>, Integer> wordSpaces = new LinkedHashMap<>();
        int cont = 0;
        for (String w : ws) {
            Map<Integer, String> positionWord = new HashMap<>();
            positionWord.put(cont, w);
            wordSpaces.put(positionWord, 1);
            cont++;
        }

        // Última palavra nunca tem espaço, sempre uma quebra de linha.
        Map<Integer, String> lastWordKey = wordSpaces.keySet().stream().reduce((first, second) -> second).orElse(null);
        wordSpaces.put(lastWordKey, 0);

        if(ws.length>1 || lineLength == getLimit()){
            while(lineLength < getLimit()){
                for (int i = 0, wsLength = ws.length; i < wsLength; i++) {
                    String word = ws[i];
                    Map<Integer, String> keyPosition = getKeyPosition(wordSpaces, i);

                    if (i != ws.length-1) {
                        int currentSpaces = wordSpaces.get(keyPosition);
                        currentSpaces++;
                        wordSpaces.put(keyPosition, currentSpaces);
                        lineLength++;
                        if (lineLength >= getLimit()) {
                            break;
                        }
                    }
                }

            }
        }

        return wordSpaces;
    }

    private Map<Integer, String> getKeyPosition(LinkedHashMap<Map<Integer, String>, Integer> wordSpaces, int i) {
        final int position = i;
        return wordSpaces.keySet().stream()
                .filter(ism -> ism.get(position)!=null)
                .findFirst()
                .orElse(null);
    }


    public String spaces(int qtde){
        StringBuilder sb = new StringBuilder();
        for(int cont=1;cont<=qtde;cont++){
            sb.append(" ");
        }

        return sb.toString();
    }
}
