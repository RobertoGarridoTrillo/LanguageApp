package LanguageApp.util;

import LanguageApp.model.Item;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;

/**
 *
 * @author Roberto Garrido Trillo
 */
public class FillListView {

   // pop-up messages
   Message message = new Message(HandleLocale01.handleLocale01());

   /**
    * Fill a listview with items
    *
    * @param listView Listview H o listView V
    * @param it An array of item objects
    * @return A String with the name file
    */
   public String setListView (ListView listView, Item[] it)
   {

      try {
         // Creating the Observable list
         ObservableList<String> listItem = FXCollections.observableArrayList();

         // Filling the list
         for (int i = 0; i < it.length - 1; i++) {

            listItem.add((it[i].getId() + 1) + " " + it[i].getText());
         }
         // 
         //Platform.runLater(() -> {
         listView.setItems(listItem);
         //});

      } catch (Exception e) {
         Platform.runLater(() -> {
         message.message(Alert.AlertType.ERROR, "Error message", "FillListView.java / setListView()", e.toString(), e); 
         });
         return "";
      }
      
      if (it.length <= 0) {
         return "";
      } else {
         return it[it.length - 1].getText();
      }
   }

}
