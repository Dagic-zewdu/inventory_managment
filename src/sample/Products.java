package sample;

import javafx.scene.control.Button;

import javax.swing.*;
import java.sql.Date;
import java.time.LocalDate;

public class Products {
    private String id,name,comment;
    private Float unitPrice;
    private Float costPrice;
    private double totalPrice;
    private int quantity;
    private Boolean addTax;
    private LocalDate expiredDate,registeredDate;
    private Button delete=new Button("Delete");
    Item item=new Item();
    controller conn=new controller();
    public Products(String id, String name, String comment,int quantity ,Float unitPrice, Float costPrice, Boolean addTax, LocalDate expiredDate, LocalDate registeredDate) {
        this.id = id;
        this.name = name;
        this.comment = comment;
        this.unitPrice = unitPrice;
        this.costPrice = costPrice;
        this.quantity=quantity;
        this.addTax = addTax;
        this.expiredDate = expiredDate;
        this.registeredDate = registeredDate;
        if (addTax) this.totalPrice = (costPrice +(costPrice* 0.15));
        else this.totalPrice = costPrice ;
        this.delete.setOnAction(e->{
           Boolean deleted= item.deleteItem(id);
            if(deleted){

                JOptionPane.showMessageDialog(new JOptionPane(),"Deleted successfully");
                conn.deleteProducts(id);
            }
            else{
                JOptionPane.showMessageDialog(new JOptionPane(),"unable to delete item");
            }
        });
     }

    public LocalDate getRegisteredDate() {
        return registeredDate;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public Float getUnitPrice() {
        return unitPrice;
    }

    public String getComment() {
        return comment;
    }

    public Float getCostPrice() {
        return costPrice;
    }

    public LocalDate getExpiredDate() {
        return expiredDate;
    }

    public Boolean getAddTax() {
        return addTax;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Button getDelete() {
        return delete;
    }

}
