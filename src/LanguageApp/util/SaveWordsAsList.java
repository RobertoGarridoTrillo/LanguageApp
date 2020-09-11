package LanguageApp.util;

import LanguageApp.model.Item;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.scene.control.Alert;


/**
 *
 * @author Roberto Garrido Trillo
 */
public class SaveWordsAsList {

      
   // pop-up messages
   Message message = new Message();
   
   Pattern pattern = Pattern.compile(
           "\\[|\\]|\\º|\\ª|\\@|\\·|\\#|\\$|\\~|\\%|\\€|\\&|\\¬|\\/|\\(|\\)|\\=|\\?|\\¿|\\¡" +
           "|\\^|\\+|\\*|\\||\\{|\\}|\\_|\\-|\\.|\\:|\\,|\\;|\\<|\\>|\\«|\"|\\ |\\»|\\!");
   Matcher matcherFirst, matcherLast;


   /**
    * Save in a File a list of words
    *
    * @param items An item of item objects
    * @return
    */
   public Set<String> saveWordsAsList (Item[] items)
   {

      // Array with the words of one phrase
      String[] wordPhrase;
      // Set with all the words (no repeat words)
      Set<String> ws = new HashSet<>();
      // The "words set" above pased to String, to save in disk
      String wString = "";

      try {

         for (int i = 0; i < items.length - 1; i++) {

            String phrase = items[i].getText();

            wordPhrase = cleanWords(phrase);
            // Adding the words of this phrase to the List
            ws.addAll(Arrays.asList(wordPhrase));
         }

         // Sorting the Set (Set doesn´t sort, I put it in a list)
         List<String> wordList = new ArrayList<>(ws);
         Collections.sort(wordList);

         // Passing the List to String to save into a file
         for (Iterator<String> iterator = wordList.iterator();
                 iterator.hasNext();) {
            wString = wString.concat(iterator.next()).concat("\n");

         }
      } catch (Exception e) {
         message.message(Alert.AlertType.ERROR, "Error message", "SaveWordsAsList.java / handleSaveAsList()", e.toString(), e);
      }
      return ws;
   }


   public String[] cleanWords (String phrase)
   {

      String[] wordPhrase = null;
      String sub;
      String s;
      try {
         // I'm going to probe without the normalizer
         s = phrase;
         /*
            // Deleting accents
            s = Normalizer.normalize(phrase, Normalizer.Form.NFD);
            s = s.replaceAll("\\p{InCombiningDiacriticalMarks}+",
                    "");
            
            // Deleting the first blank space of the phrase
            pattern = Pattern.compile("[^a-zA-Z0-9]");
            for (int i = 0; i < s.length(); i++) {

                sub = s.substring(i, i + 1);
                matcher = pattern.matcher(sub);

                if (matcher.find()) {

                    s = s.substring(i + 1, s.length());
                    i = -1;
                } else {
                    break;
                }

            }

            // Deleting the last non normal character of th phrase
            pattern = Pattern.compile("[^a-zA-Z0-9]");
            for (int i = s.length(); i >= 0; i--) {

                sub = s.substring(s.length() - 1, s.length());
                matcher = pattern.matcher(sub);

                if (matcher.find()) {

                    s = s.substring(0, s.length() - 1);
                    i = s.length();

                } else {
                    break;
                }

            }
            
          */
         // To lowerCase
         phrase = s.toLowerCase();
         phrase = phrase.replace("  ", "");

         // Breaking down the phrase in words aput then in a Array
         wordPhrase = phrase.split(" ");
         // Deleting the first and last 'dot, comma, acent... of every
         // word of the phrase
         for (int k = 0; k < wordPhrase.length; k++) {


            for (int i = 0; i < 4; i++) {
               String first = wordPhrase[k].substring(0, 1);
               String last = wordPhrase[k].substring(wordPhrase[k].length() - 1);

               matcherFirst = pattern.matcher(first);
               matcherLast = pattern.matcher(last);
               if (matcherFirst.find()) {
                  wordPhrase[k] = wordPhrase[k].substring(1, wordPhrase[k].length());
               }
               if (matcherLast.find()) {
                  wordPhrase[k] = wordPhrase[k].substring(0, wordPhrase[k].length() - 1);
               }
            }
         }
      } catch (Exception e) {
         message.message(Alert.AlertType.ERROR, "Error message", "SaveWordsAsList.java / cleanWords()", e.toString(), e);
      }
      return wordPhrase;
   }
}
