package LanguageApp.util;

import LanguageApp.model.Item;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;

/**
 *
 * @author Roberto Garrido Trillo 
 */
public class FillListView {

    /**
     * Fill a listview with items
     *
     * @param listView Listview H o listView V
     * @param it An array of item objects
     * @return A String with the name file
     */
    public String setListView (ListView listView, Item[] it) {

        try {
            // Creating the Observable list
            ObservableList<String> listItem = FXCollections.
                    observableArrayList();

            // Filling the list
            for (int i = 0; i < it.length - 1; i++) {

                listItem.add((it[i].getId() + 1) + " " + it[i].getText());
            }
            // 
            listView.setItems(listItem);

        } catch (Exception e) {
            /*new MainController().message(Alert.AlertType.ERROR, "Error message", 
                    e.getMessage(), "FillListView.java / setListView()");*/
        }
        return it[it.length - 1].getText();
    }
}
