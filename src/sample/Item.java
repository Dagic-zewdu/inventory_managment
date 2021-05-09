package sample;

import com.sun.xml.internal.ws.wsdl.ActionBasedOperationSignature;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventType;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.beans.EventHandler;
import java.sql.*;
import java.time.LocalDate;
import java.util.Random;

public class Item {
    Db db=new Db();
    Statement statement=db.getStatement();
     Connection conn= db.getConn();
    String selectAll="select * from item";
    TableCell listItem=new TableCell();

    public Text progress=new Text("");
    public Text success=new Text("");
    public Text error=new Text();
    public Boolean AddItem(String id,String name,int quantity,float unit_price,float cost_price,String comment,Date date,Boolean tax){
     try{
         String query = "insert into item(id,name,quantity,unit_price,cost_price,add_tax,comment,expire_date)" +
                 " values(?,?,?,?,?,?,?,?) ";
       PreparedStatement  ps = this.conn.prepareStatement(query);
         ps.setString(1, id);
         ps.setString(2,name);
         ps.setInt(3,quantity);
         ps.setFloat(4,unit_price);
         ps.setFloat(5,cost_price);
         ps.setBoolean(6,tax);
         ps.setString(7,comment);
         ps.setDate(8,date);
         ps.executeUpdate();
         return true;
     }
     catch(Exception e){
         e.printStackTrace();
         return false;
     }
    }
    /*Check whether the id is registered before true-if the id found false-if not found*/
   public Boolean check_id (String id){
        try{
    ResultSet resultSet=this.statement.executeQuery("SELECT * FROM `item` WHERE id="+id+" ");
    Boolean proceed=false;
    while(resultSet.next()) {
        proceed = true;
    }
       return  proceed;
        }
        catch(Exception e){
            e.printStackTrace();
            return null;
        }
   }
   /**check Id and generate new id if the id is used before
    * @param check-if id generator is selected true
    * @param  id- id provide by the user
    * */
    public String checkGenerator(Boolean check,String id){
        String Id;
        if (check) Id = this.idGenerator();
        else Id = id;
        if(check) {
            while (this.check_id(Id)) {
                Id = this.idGenerator();
            }
            return Id;
        }
        else return id;
    }

   public String idGenerator(){
       Random random = new Random(); //instance of random class
       int upperbound = 10000;
       //generate random values from 0-1000
       String rand = ""+Math.abs(random.nextInt(upperbound));
       return rand;
   }
   public void addInterface(Stage stage){
       //text
       Text text1=new Text("item_id");
       Text text2= new Text("Name");
       Text text3= new Text("quantity");
       Text text4= new Text("unit_price");
       Text text5= new Text("cost_price");
       Text text6= new Text("add_tax");
       Text text7= new Text("comment");
       Text text8= new Text("Expired_date");
       Text text9= new Text("add_tax");
       Text text10=new Text("Total cost:");
       Text text11=new Text("unit profit:");
       Text text12=new Text("Total Profit:");
       Text text13=new Text("unit cost");
       Text unit_cost=new Text("0");
       Text Totall_cost=new Text("0");
       Text  unit_profit=new Text("0");
       Text  Total_profit=new Text("0");

       //input fields
       TextField id=new TextField();
       TextField name=new TextField();
       TextField quantity=new TextField();
       TextField unit_price=new TextField();
       TextField cost_price=new TextField();
       CheckBox addTax=new CheckBox("Add tax 15%");
       CheckBox autoId=new CheckBox("Auto generate id");
       autoId.setSelected(true);
       autoId.setOnMouseClicked(e->{
           if (autoId.isSelected()) {
               id.setVisible(false);
           } else {
               id.setVisible(true);
           }
       });
        id.setVisible(false);

        TextArea comment=new TextArea();
       DatePicker expired_date=new DatePicker();
        //Button
       Button button1=new Button("Clear");
       Button button2=new Button("Save");
       addTax.setSelected(true);
      //formatter to accept only numbers
       Formater formater=new Formater();
       quantity.setText("1");
       unit_price.setText("0");
       cost_price.setText("0");
       quantity.textProperty().addListener(formater.numberFormat(quantity));
       unit_price.textProperty().addListener(formater.numberFormat(unit_price));
       cost_price.textProperty().addListener(formater.numberFormat(cost_price));
       unit_price.textProperty().addListener(formater.numberFormat(unit_price));

       //listener

       unit_price.setOnKeyReleased(e->{
         unit_cost.setText(""+this.unit_cost(Double.parseDouble(cost_price.getText().isEmpty()?"0":cost_price.getText()),addTax.isSelected()?0.15:0.0));
         Totall_cost.setText(""+this.totall_cost(Double.parseDouble(cost_price.getText().isEmpty()?"0":cost_price.getText()),Double.parseDouble(quantity.getText().isEmpty()?"0":quantity.getText()),
                 addTax.isSelected()?0.15:0.0));
         unit_profit.setText(""+this.unit_profit(Double.parseDouble(cost_price.getText().isEmpty()?"0":cost_price.getText()),Double.parseDouble(unit_price.getText().isEmpty()?"0":unit_price.getText())));
         Total_profit.setText(""+this.totall_profit(Double.parseDouble(cost_price.getText().isEmpty()?"0":cost_price.getText()),Double.parseDouble(unit_price.getText().isEmpty()?"0":unit_price.getText()),
                 Double.parseDouble(quantity.getText().isEmpty()?"0":quantity.getText())));
       });
       cost_price.setOnKeyReleased(e->{

           unit_cost.setText(""+this.unit_cost(Double.parseDouble(cost_price.getText().isEmpty()?"0":cost_price.getText()),addTax.isSelected()?0.15:0.0));
           Totall_cost.setText(""+this.totall_cost(Double.parseDouble(cost_price.getText().isEmpty()?"0":cost_price.getText()),Double.parseDouble(quantity.getText().isEmpty()?"0":quantity.getText()),addTax.isSelected()?0.15:0.0));
           unit_profit.setText(""+this.unit_profit(Double.parseDouble(cost_price.getText().isEmpty()?"0":cost_price.getText()),Double.parseDouble(unit_price.getText().isEmpty()?"0":unit_price.getText())));
           Total_profit.setText(""+this.totall_profit(Double.parseDouble(cost_price.getText().isEmpty()?"0":cost_price.getText()),Double.parseDouble(unit_price.getText().isEmpty()?"0":unit_price.getText()),
                   Double.parseDouble(quantity.getText().isEmpty()?"0":quantity.getText())));
       });
       quantity.setOnKeyReleased(e->{
  unit_cost.setText(""+this.unit_cost(Double.parseDouble(cost_price.getText().isEmpty()?"0":cost_price.getText()),addTax.isSelected()?0.15:0.0));
           Totall_cost.setText(""+this.totall_cost(Double.parseDouble(cost_price.getText().isEmpty()?"0":cost_price.getText()),Double.parseDouble(quantity.getText().isEmpty()?"0":quantity.getText()),addTax.isSelected()?0.15:0.0));
           unit_profit.setText(""+this.unit_profit(Double.parseDouble(cost_price.getText().isEmpty()?"0":cost_price.getText()),Double.parseDouble(unit_price.getText().isEmpty()?"0":unit_price.getText())));
           Total_profit.setText(""+this.totall_profit(Double.parseDouble(cost_price.getText().isEmpty()?"0":cost_price.getText()),Double.parseDouble(unit_price.getText().isEmpty()?"0":unit_price.getText()),
                   Double.parseDouble(quantity.getText().isEmpty()?"0":quantity.getText())));
       });
       addTax.setOnMouseClicked(e->{
       unit_cost.setText(""+this.unit_cost(Double.parseDouble(cost_price.getText().isEmpty()?"0":cost_price.getText()),addTax.isSelected()?0.15:0.0));
           Totall_cost.setText(""+this.totall_cost(Double.parseDouble(cost_price.getText().isEmpty()?"0":cost_price.getText()),
                   Double.parseDouble(quantity.getText().isEmpty()?"0":quantity.getText()),addTax.isSelected()?0.15:0.0));
           unit_profit.setText(""+this.unit_profit(Double.parseDouble(cost_price.getText().isEmpty()?"0":cost_price.getText()),Double.parseDouble(unit_price.getText().isEmpty()?"0":unit_price.getText())));
           Total_profit.setText(""+this.totall_profit(Double.parseDouble(cost_price.getText().isEmpty()?"0":cost_price.getText()),Double.parseDouble(unit_price.getText().isEmpty()?"0":unit_price.getText()),
                   Double.parseDouble(quantity.getText().isEmpty()?"0":quantity.getText())));
       });

       GridPane gridPane=new GridPane();
       GridPane gridPane1=new GridPane();

       button1.setStyle("-fx-background-color: darkslateblue; -fx-text-fill: white;");
       button2.setStyle("-fx-background-color: darkslateblue; -fx-text-fill: white;");

       text1.setStyle("-fx-font: normal bold 17px 'serif' ");
       text2.setStyle("-fx-font: normal bold 17px 'serif' ");
       text3.setStyle("-fx-font: normal bold 17px 'serif' ");
       text4.setStyle("-fx-font: normal bold 17px 'serif' ");
       text5.setStyle("-fx-font: normal bold 17px 'serif' ");
       text6.setStyle("-fx-font: normal bold 17px 'serif' ");
       text7.setStyle("-fx-font: normal bold 17px 'serif' ");
       text8.setStyle("-fx-font: normal bold 17px 'serif' ");
       text9.setStyle("-fx-font: normal bold 17px 'serif' ");
       text10.setStyle("-fx-font: normal bold 15px 'serif'");
       text11.setStyle("-fx-font: normal bold 15px 'serif' ");
       text12.setStyle("-fx-font: normal bold 15px 'serif'");
       text13.setStyle("-fx-font: normal bold 15px 'serif' ");
       gridPane.setStyle("-fx-background-color:#CCE3E3,-fx-padding:15");
       //aligment
       gridPane1.setAlignment(Pos.CENTER);
      gridPane.setAlignment(Pos.CENTER);
      gridPane.setPadding(new Insets(20,20,20,20));
    gridPane.setHgap(20);
    gridPane.setVgap(20);
      //add
       gridPane.add(text1,0,0);
       gridPane.add(autoId,1,0);
       gridPane.add(id,2,0);
       gridPane.add(text13,5,0);
       gridPane.add(unit_cost,6,0);
       gridPane.add(text10,5,1);
       gridPane.add(text2,0,1);
       gridPane.add(name,1,1);
       gridPane.add(text4,0,2);
       gridPane.add(unit_price,1,2);
       gridPane.add(text5,0,3);
       gridPane.add(cost_price,1,3);
       gridPane.add(text3,0,4);
       gridPane.add(quantity,1,4);
       gridPane.add(text9,0,5);
       gridPane.add(addTax,1,5);
       gridPane.add(text7,0,6);
       gridPane.add(comment,1,6);
       gridPane.add(text8,0,7);
       gridPane.add(expired_date,1,7);
       gridPane.add(button1,0,8);
       gridPane.add(button2,1,8);
       gridPane.add(Totall_cost,6,1);
       gridPane.add(text11,5,2);
       gridPane.add(unit_profit,6,2);
       gridPane.add(text12,5,3);
       gridPane.add(Total_profit,6,3);
    TextField[] txtClear=new TextField[]{id,name}; //texts we want to clear
    TextField[] txtTo1=new TextField[]{cost_price,unit_price,quantity}; //texts we want to set to oone
       /**clear*/
       button1.setOnAction(e->{
   formater.clear(txtClear);
   formater.setTo1(txtTo1);
   comment.setText("");
  });
   //save

       button2.setOnAction((e->{
           String ID=this.checkGenerator(autoId.isSelected(),id.getText());
        Boolean saved=this.AddItem(ID,
                   name.getText(),Integer.parseInt(quantity.getText()),
                   Float.parseFloat(unit_price.getText()),
                   Float.parseFloat(cost_price.getText()),
                   comment.getText(),java.sql.Date.valueOf(expired_date.getValue()),addTax.isSelected()
                   );
       if(saved){
           controller cont=new controller();
           JOptionPane.showMessageDialog(new JOptionPane(),"item save successfully with id of: "+ID);
          cont.initialize();
       }
       else{
           JOptionPane.showMessageDialog(new JOptionPane(),"can not save the item please try later");
       }
       }));
       gridPane.setAlignment(Pos.CENTER);
       gridPane.setStyle("-fx-background-color: BEIGE");
       stage.setTitle("Register Item");
       stage.setScene(new Scene(gridPane));
       stage.show();
   }
   public Double unit_cost(Double costPrice,Double tax){
       return (costPrice+(costPrice*tax));
   }

   public Double totall_cost(Double cost_price,Double quantity,Double tax){
       return (this.unit_cost(cost_price,tax)*quantity);
   }
   public Double unit_profit(Double cost_price,Double unitPrice){
       return cost_price-unitPrice;
   }
   public Double totall_profit(Double cost_price,Double unitPrice,Double quantity){
       return (this.unit_profit(cost_price,unitPrice)*quantity);
   }
    public ResultSet getResultItem(){
        try {
           ResultSet rs=this.statement.executeQuery("Select * from item");
           return rs;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return null;
        }
    }
    public int itemLength(){
        try{
       ResultSet rs=this.statement.executeQuery(selectAll);
       int i=0;
       while(rs.next()){
           i++;
       }
       return i;
        }
        catch(Exception err){
            err.printStackTrace();
        return 0;
        }
    }

   public Products[] ProductList(){
        Products[]products = new Products[this.itemLength()];
        try{
            int i=0;
            ResultSet rs=this.getResultItem();

            while(rs.next()) {
                products[i]=new Products(rs.getString(1),
                        rs.getString(2),rs.getString(7),rs.getInt(3),
                        rs.getFloat(4),rs.getFloat(5),
                        rs.getBoolean(6),rs.getDate(8).toLocalDate(),rs.getDate(9).toLocalDate()
                        );
                        i++;
            }
            return products;
        }
        catch(Exception e){
            e.printStackTrace();
          return products;
        }

   }

  public ObservableList<Products> getProducts(){
      ObservableList<Products> products= FXCollections.observableArrayList();
        try{
           ResultSet rs=this.getResultItem();
           for(int i=0;i<this.ProductList().length;i++)
               products.add(this.ProductList()[i]);
           return products;
       }
       catch (Exception e){
         e.printStackTrace();
         return products;
       }

  }
  public Boolean deleteItem(String id){
        try{
      PreparedStatement ps=this.conn.prepareStatement("Delete from item WHERE id=?");
      ps.setString(1,id);
      ps.executeUpdate();
            return true;
        }
        catch(Exception e){
            e.printStackTrace();
           return false;
      }
  }


}
