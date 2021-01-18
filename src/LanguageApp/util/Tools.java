package LanguageApp.util;

import java.util.Arrays;
import java.util.Locale;

/**
 *
 * @author Roberto Garrido Trillo
 */
public class Tools
 {
  public static final Locale ENGLISH = new Locale("en");
  public static final Locale FRANCE = new Locale("fr");
  public static final Locale ITALIAN = new Locale("it");
  public static final Locale JAPANESE = new Locale("ja");
  public static final Locale SPANISH = new Locale("sp");


  /**
   * Returns the index of the first occurrence of the specified element in this list, or -1 if this list does not contain the element.
   *
   * @param source Array of String
   * @param s String
   * @return int Index of the element
   */
  public static int getIndex(String[] source, String s)
   {
    return Arrays.asList(source).indexOf(s);
   }



  public static String getLanguageDefault()
   {
    Locale locale = HandleLocale.getLocaleDefault();
    if (locale.getLanguage().equals(new Locale("en").getLanguage())) return "English";
    if (locale.getLanguage().equals(new Locale("es").getLanguage())) return "Spanish";
    if (locale.getLanguage().equals(new Locale("ja").getLanguage())) return "Japanese";
    if (locale.getLanguage().equals(new Locale("it").getLanguage())) return "Italian";
    if (locale.getLanguage().equals(new Locale("fr").getLanguage())) return "French";
    return null;
   }


  /**
   * Compare two doubles.TThe result is between the minimum and the maximum, including the maximum
   *
   * @param i The double to compare
   * @param minValueIncluded the minimum range inclusive
   * @param maxValueIncluded the maximum range inclusive
   * @return
   */
  public static boolean rangeInc(double i, double minValueIncluded, double maxValueIncluded)
   {
    return (i >= minValueIncluded && i <= maxValueIncluded);
   }


  /**
   * Compare two doubles.TThe result is between the minimum and the maximum, including the maximum
   *
   * @param i The double to compare
   * @param minValueIncluded the minimum range inclusive
   * @param maxValueExcluded the maximum range inclusive
   * @return
   */
  public static boolean rangeExc(double i, double minValueIncluded, double maxValueExcluded)
   {
    return (i >= minValueIncluded && i < maxValueExcluded);
   }


  private static void main(String args[])
   {
    Tools t = new Tools();
   }

 }
