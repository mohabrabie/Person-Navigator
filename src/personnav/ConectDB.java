/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package personnav;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Mohab
 */
class ConnectDB {
    Connection con = null;
    List<Person> Data = new ArrayList();
    private Connection connect() {
        // SQLite connection string
        String url = "jdbc:sqlite:C:\\Users\\Mohab\\Documents\\NetBeansProjects\\PersonNav\\DB\\sqlite1.db";
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }
    public List<Person> selectAll() throws ClassNotFoundException{
        String sql = "SELECT * FROM Person";
        
        try (Connection conn = this.connect();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)){
            
            // loop through the result set
            while (rs.next()) {
                Person p = new Person(rs.getInt("id"),rs.getString("FirstName")
                , rs.getString("MiddleName"),rs.getString("LastName")
                ,rs.getString("Email"),rs.getString("Phone"));
                Data.add(p);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return Data;
    }
    public void Delete(int id){
        String sql = "DELETE FROM Person WHERE id = ?";
        
         try (Connection conn = this.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public void Insert(int id,String fName,
        String mName,String lName,String email,String phone) throws ClassNotFoundException{
        String sql = "insert into Person values(?,?,?,?,?,?)";
        
         try (Connection conn = this.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.setString(2, fName);
            pstmt.setString(3, mName);
            pstmt.setString(4, lName);
            pstmt.setString(5, email);
            pstmt.setString(6, phone);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public void UpDate(int id,String fName,
        String mName,String lName,String email,String phone) throws ClassNotFoundException{
        String sql = "UPDATE Person SET FirstName = ? , " + 
                " MiddleName = ? ," +
                " LastName = ? ," +
                " Email = ? ," +
                " Phone = ? " +
                " WHERE ID = ? ";
        //pst3 = con.prepareStatement("update empdata set firstname=? , middlename=?, lastname=? , email=?, phone=? where id=?", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);

         try (Connection conn = this.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, fName);
            pstmt.setString(2, mName);
            pstmt.setString(3, lName);
            pstmt.setString(4, email);
            pstmt.setString(5, phone);
            pstmt.setInt(6, id);
            pstmt.executeUpdate();
            System.out.print(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}