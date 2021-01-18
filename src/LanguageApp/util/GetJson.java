package LanguageApp.util;

import LanguageApp.model.Item;
import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

/**
 *
 * @author Roberto Garrido Trillo
 */
public class GetJson
 {

  /**
   * Read a json file and return an Array of Items object
   *
   * @param fl The file return by filechooser
   * @return An array of item object
   */
  public Item[] getJson(File fl) throws Exception
   {

    Item[] is = null;

    Gson gson = new Gson();
    /*/*Reader reader = new FileReader(fl);*/

    // Convert JSON File to Java Object
    InputStream inputStream = new FileInputStream(fl.getAbsolutePath());
    BufferedReader br = new BufferedReader(
            new InputStreamReader(inputStream, "UTF-8"));
    is = gson.fromJson(new InputStreamReader(inputStream, "UTF-8"), Item[].class);

    // Put the id handly
    for (int i = 0; i < is.length; i++) {
      is[i].setId(i);
      is[i].setStart(is[i].getStart());
      is[i].setEnd(is[i].getEnd());
      is[i].setText(is[i].getText());
    }

    return is;
   }

 }
