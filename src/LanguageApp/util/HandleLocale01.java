package LanguageApp.util;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 *
 * @author Roberto Garrido Trillo
 */
public class HandleLocale01
 {

  public static ResourceBundle resources;
  private static final String[] LANGUAGES = {"en", "es", "fr", "it", "ja"};


  /**
   *
   * @return
   */
  public static ResourceBundle handleLocale01()
   {
    resources = ResourceBundle.getBundle(
            "LanguageApp.resources.bundles.LanguageApp", getLocaleDefault());
    return resources;
   }


  /**
   *  
   * @return Locale. Return the local language. If it's different to the five 
   * languages of the variable LANGUAGES return "en
   */
  static public Locale getLocaleDefault()
   {
    // Locale.getDefault() -> en_GB
    // Locale.getDefault().getLanguage() -> en

    Locale locale = Locale.getDefault();

    // if the local doesn't exit in the app, it uses "en"
    boolean salida = false;
    for (String language : LANGUAGES) {
      if (locale.getLanguage().equals(new Locale(language).getLanguage())) {
        salida = true;
        break;
      }
    }
    if (!salida) {
      locale = new Locale("en");
    }
    return locale;
   }


  static public String[] getLANGUAGES()
   {
    return LANGUAGES;
   }

 }
