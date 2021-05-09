package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.net.URL;
import java.time.LocalDate;
import java.util.Date;
import java.util.ResourceBundle;

public class controller {
    @FXML private TableView<Products>producTable;
    @FXML private TableColumn<Products,String>id;
    @FXML private TableColumn<Products,String>name;
    @FXML private TableColumn<Products,String>comment;
   @FXML private TableColumn<Products,Integer>quantity;
    @FXML private TableColumn<Products,Float>costPrice;
    @FXML private TableColumn<Products,Double>totalPrice;
    @FXML private TableColumn<Products,LocalDate>expiredDate;
    @FXML private TableColumn<Products,LocalDate>registeredDate;
    @FXML private TableColumn<Products, Button>delete;

    Item item=new Item();
    public void addItem(){
        item.addInterface(new Stage());
    }
    public void initialize(){
        //id
        id.setCellValueFactory(new PropertyValueFactory<Products,String>("id"));
        name.setCellValueFactory(new PropertyValueFactory<Products,String>("name"));
        quantity.setCellValueFactory(new PropertyValueFactory<Products,Integer>("quantity"));
        costPrice.setCellValueFactory(new PropertyValueFactory<Products,Float>("costPrice"));
        registeredDate.setCellValueFactory(new PropertyValueFactory<Products,LocalDate>("registeredDate"));
        totalPrice.setCellValueFactory(new PropertyValueFactory<Products,Double>("totalPrice"));
        delete.setCellValueFactory(new PropertyValueFactory<Products,Button>("delete"));

        producTable.setItems(item.getProducts());
        producTable.getColumns().addAll(id,name,quantity,costPrice,registeredDate,totalPrice,delete);
    }
    public void deleteProducts(String id){
        Item item=new Item();
      ObservableList<Products> products=producTable.getItems();
      Products product=products.get(1);
      products.get(1).getId();
     int n=products.size();
     for(int i=0;i<n;i++){
    if(products.get(i).getId()==id){
        product=products.get(i);
    }

     }
        System.out.println(producTable.getSelectionModel().getSelectedItem());
      producTable.getItems().removeAll(product);

    }

}
