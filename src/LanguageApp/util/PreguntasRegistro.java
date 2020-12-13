package LanguageApp.util;

import java.util.HashMap;

/**
 *
 * @author Roberto Garrido Trillo
 */
public class PreguntasRegistro {

   private static HashMap<Integer, String> preguntasRegistro;

   // Constructor
   public PreguntasRegistro ()
   {
      // For the answers of control
      preguntasRegistro = new HashMap<>();
      preguntasRegistro.put(0, "¿Cuál es tu comida favorita?");
      preguntasRegistro.put(1, "¿Cuál es tu color favorito?");
      preguntasRegistro.put(2, "¿Cuál es tu ciudad favorita?");
      preguntasRegistro.put(3, "¿Cuál es tu ropa favorita?");
      preguntasRegistro.put(4, "¿Cuál es tu bebida favorita?");
   }

   /**
    * 
    * @return 
    */
   public static HashMap preguntas(){
      return preguntasRegistro;
   }
}