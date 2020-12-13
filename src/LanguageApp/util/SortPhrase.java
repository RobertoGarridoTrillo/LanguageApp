package LanguageApp.util;

import LanguageApp.model.Item;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 *
 * @author Roberto Garrido Trillo
 */
public class SortPhrase
 {

  // Fields Class  
  // There is another simil pattern in SaveWordAsList.java
  Pattern pattern = Pattern.compile(
          "\\[|\\]|\\º|\\ª|\\@|\\·|\\#|\\$|\\~|\\%|\\€|\\&|\\¬|\\/|\\(|\\)|\\=|\\?|\\¿|\\¡" +
          "|\\^|\\+|\\*|\\||\\{|\\}|\\_|\\-|\\.|\\:|\\,|\\;|\\<|\\>|\\«|\"|\\ |\\»|\\!|\\。");
  Matcher matcherFirst, matcherLast;


  /**
   *
   * @param items
   * @return
   */
  public String[] sortPhrases(Item[] items)
   {

    //List<String> tempOriginal = new ArrayList<>();

    // Set with all the words 
    // no repeat words preserves the order of the original collection 
    // while removing duplicate elements:)
    // Set<String> ws = new LinkedHashSet<>(tempOriginal);   

    // Set with all the words (no  repeat words no preserv order)
    // Set<String> ws = new HashSet<>(tempOriginal);


    /*/*
    // Delete the repeats
    int lonjitud = tempOriginal.length;

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
      // clean and compact the phrases
      tempOriginal[i] = ((pattern.matcher(tempOriginal[i])).replaceAll(""));
    }
    //lonjitud--;

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
     */
 /*/* Another way
    //Convierte a List ambos arreglos        
    List<String> list01 = new ArrayList<String>(Arrays.asList(tempOriginal));
    List<String> list02 = new ArrayList<String>(Arrays.asList(tempOriginal));
    List<String> listFinal = Stream.concat(list01.stream(), list02.stream())
            .distinct()
            .collect(Collectors.toList());

    int lonjitud = listFinal.size();
    ws.addAll(listFinal); */
    
    String[] tempOriginal = new String[items.length - 1]; // Out the last row
    
    int lonjitud = items.length - 1;
    
    // Adding the words of this phrase to the List
    for (int i = 0; i < items.length - 1; i++) {
      tempOriginal[i] = ((pattern.matcher(items[i].getText())).replaceAll(""));
    }

    return tempOriginal;
   }

 }
