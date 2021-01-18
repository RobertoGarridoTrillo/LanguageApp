package LanguageApp.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 *
 * @author Roberto Garrido Trillo 
 */
public class CheckJava {
  
  // Field class
  private final String[] REG = {"reg:32", "reg:64"};
  
  
  /**
   *
   * @param kp
   * @param kn
   * @param skn
   * @param reString
   * @return the value of the register, or ""
   * @throws IOException
   * @throws InterruptedException
   */
  public String getValue(String kp, String kn, String skn)
          throws IOException, InterruptedException
   {
    String command;
    String temp = "", sb = "";
    String[] value = null;
    for (String reg : REG) {

      command = "reg query \"" + kp + "\\" + kn + "\" /v " + skn + " /" + reg;
      temp = "";
      value = null;

      Process keyReader = Runtime.getRuntime().exec(command);
      BufferedReader br = new BufferedReader(
              new InputStreamReader(keyReader.getInputStream()));

      StringBuilder builder = new StringBuilder();
      while ((temp = br.readLine()) != null) {
        builder.append(temp);
      }

      if (builder.length() > 0) {
        value = builder.toString().split("    ");
        sb = value[value.length-1];
      }
      
      keyReader.waitFor();
    }

    return sb;
   }  
  
}
