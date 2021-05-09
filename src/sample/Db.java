package sample;
import java.sql.*;
public class Db {
    public Statement statement;
    public Connection conn;
     public Db(){
         try {
             Class.forName("com.mysql.jdbc.Driver");
             Connection conn= DriverManager.getConnection("jdbc:mysql://localhost/inventory_system?characterEncoding=utf8&useSSL=false&useUnicode=true","root","");
              this.conn=conn;
             this.statement=conn.createStatement();
             System.out.println("Db connection success");
         }
         catch (Exception e){
             e.printStackTrace();
         }
     }
    public Statement getStatement() {
        return this.statement;
    }

    public Connection getConn() {
        return this.conn;
    }
}
