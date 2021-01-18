package LanguageApp.util;

import LanguageApp.model.Item;
import static LanguageApp.util.Message.showException;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;

/**
 *
 * @author Roberto Garrido Trillo
 */
public class FillListView
 {

  /**
   * Fill a listview with items
   *
   * @param listView Listview H o listView V
   * @param it An array of item objects
   * @return A String with the- name file
   * @throws java.lang.Exception
   */
  public String setListView(ListView listView, Item[] it) throws Exception
   {
    // Creating the Observable list
    ObservableList<String> listItem = FXCollections.observableArrayList();

    // Filling the list
    for (int i = 0; i < it.length - 1; i++) {

      listItem.add((it[i].getId() + 1) + " " + it[i].getText());
    }
    // 
    Platform.runLater(() -> {
      try {
        listView.setItems(listItem);
      } catch (Exception e) {
        showException(e);
      }
    });

    if (it.length <= 0) {
      return "";
    } else {
      return it[it.length - 1].getText();
    }
   }

 }
