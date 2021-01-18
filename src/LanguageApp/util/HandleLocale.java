package LanguageApp.util;

import static LanguageApp.util.Message.showException;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 *
 * @author Roberto Garrido Trillo
 */
public class HandleLocale
 {

  public static ResourceBundle resources;
  private static final String[] LANGUAGES = {"en", "es", "fr", "it", "ja"};


  /**
   *
   * @return 
   * @throws java.lang.Exception
   */
  public static ResourceBundle getResource() throws Exception
   {
    resources = ResourceBundle.getBundle(
            "LanguageApp.resources.bundles.LanguageApp", getLocaleDefault());
    return resources;
   }


  /**
   *
   * @return Locale. Return the local language. If it's different to the five languages of the variable LANGUAGES return "en
   */
  public static Locale getLocaleDefault()
   {
    // Locale.getDefault() -> en_GB
    // Locale.getDefault().getLanguage() -> en
    Locale locale = Locale.getDefault();
    try {

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
    } catch (Exception e) {
      showException(e);
    }
    return locale;
   }


  /**
   *
   * @return 
   */
  public static String[] getLANGUAGES()
   {
    return LANGUAGES;
   }

  /**
   *
   * @param s
   * @return
   */
  public static String toLocale(String s)
   {
    return resources.getString(s);
   }


 }
