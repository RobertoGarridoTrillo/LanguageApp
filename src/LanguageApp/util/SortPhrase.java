package LanguageApp.util;

import LanguageApp.model.Item;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 *
 * @author Roberto Garrido Trillo
 */
public class SortPhrase {

   // Fields Class
   Pattern pattern = Pattern.compile(
           "\\[|\\]|\\º|\\ª|\\@|\\·|\\#|\\$|\\~|\\%|\\€|\\&|\\¬|\\/|\\(|\\)|\\=|\\?|\\¿|\\¡" +
           "|\\^|\\+|\\*|\\||\\{|\\}|\\_|\\-|\\.|\\:|\\,|\\;|\\<|\\>|\\«|\"|\\ |\\»|\\!");
   Matcher matcherFirst, matcherLast;


   /**
    *
    */
   public Set<String> sortPhrases (Item[] items)
   {

      String[] tempOriginal = new String[items.length - 1];
      // Set with all the words (no repeat words)
      Set<String> ws = new HashSet<>();

      // Adding the words of this phrase to the List
      for (int i = 0; i < items.length - 1; i++) {
         tempOriginal[i] = items[i].getText();
      }

      // Delete the repeats
      int lonjitud = tempOriginal.length;

      for (int i = 0; i < lonjitud - 1; i++) {

         for (int k = i + 1; k < lonjitud; k++) {

            if (tempOriginal[i].equals(tempOriginal[k])) {

               if (k + 1 < lonjitud) {
                  for (int l = k + 1; l < lonjitud; l++) {
                     tempOriginal[l - 1] = tempOriginal[l];
                  }
                  lonjitud--;
               }
               k = i;
            }
         }
         // clean and compact the phrases
         tempOriginal[i] = ((pattern.matcher(tempOriginal[i])).replaceAll(""));
      }
      lonjitud--;

      // Delete the repeats again
      //lonjitud = tempOriginalSort.length;

      for (int i = 0; i < lonjitud; i++) {

         for (int k = i + 1; k < lonjitud; k++) {

            if (tempOriginal[i].equals(tempOriginal[k])) {

               if (k + 1 < lonjitud) {
                  for (int l = k + 1; l < lonjitud; l++) {
                     tempOriginal[l - 1] = tempOriginal[l];
                  }
                  lonjitud--;
               }
               k = i;
            }
         }
      }

      // Paso las frases a un array   
      for (int i = 0; i < lonjitud; i++) {
         ws.add(tempOriginal[i]);

      }
      return ws;
   }
}
