package LanguageApp.util;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 *
 * @author Roberto Garrido Trillo
 */
public class HandleLocale01 {

   public static ResourceBundle resources;
   private static String[] languages = {"en", "es", "fr", "it", "ja"};

   /**
    *
    * @return
    */
   public static ResourceBundle handleLocale01 ()
   {
      // Create an array with the idioms of the app

      // Get the local language
      Locale locale = new Locale(Locale.getDefault().getLanguage());

      // if the local doesn't exit in the app, it uses "en"
      boolean salida = false;
      for (String language : languages) {
         if (locale.getLanguage().equals(new Locale(language).getLanguage())) {
            salida = true;
            break;
         }
      }
      if (!salida) {
         locale = new Locale("en");
      }

      resources = ResourceBundle.getBundle("LanguageApp.resources.bundles.LanguageApp", locale);
      return resources;
   }

   static public String[] getLanguages ()
   {
      return languages;
   }
}
