package com.example.demo.dao;
import java.sql.Connection;
import java.util.ArrayList;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import com.example.demo.entity.MyEntity;
import com.example.demo.repository.ConnectioinJdbc;
@Repository
@Service
public class addData {
    @Autowired
    private ConnectioinJdbc jdbc;
    public void insertData(MyEntity myEntity) {
        try (Connection con = jdbc.connect()) {
            PreparedStatement pt = con.prepareStatement("INSERT INTO pro (id, name, salary) VALUES (?, ?, ?)");
            pt.setInt(1, myEntity.getId());
            pt.setString(2,myEntity.getName());
            pt.setDouble(3, myEntity.getSalary());
            int update = pt.executeUpdate();
            if (update > 0) {
                System.out.println("Data Inserted");
                System.out.println(update);
            } else {
                System.out.println("Data Not Inserted"); 
            }
        } catch (SQLException e) {
            System.out.println("Failed to insert data: " + e.getMessage()); 
        }
    }
    public List<MyEntity> showData()
    {
    	
    	try (Connection con = jdbc.connect()) {
    	    String sql = "SELECT * FROM pro";
    	    PreparedStatement pstmt = con.prepareStatement(sql);
    	    ResultSet rs = pstmt.executeQuery();

    	    if (!rs.next()) {
    	        System.out.println("No rows found in the result set.");
    	    } else {
    	        do {
    	            System.out.println(rs.getString("id") + "\t" + rs.getString("name") + "\t" + rs.getDouble("salary"));
    	        } while (rs.next());
    	    }

    	    // Close the resources
    	    rs.close();
    	    pstmt.close();
    	} catch (SQLException e) {
    	    System.out.println(e.getMessage());
    	    e.printStackTrace();
    	}

    	return null;
    }
    public List<MyEntity> getAllData() {
        List<MyEntity> entities = new ArrayList<>();
        try (Connection con = jdbc.connect()) {
            String sql = "SELECT * FROM pro";
            PreparedStatement pstmt = con.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                double salary = rs.getDouble("salary");

                MyEntity entity = new MyEntity(id, name, salary);
                entities.add(entity);
            }
            rs.close();
            pstmt.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }


        return entities;
    }
    public void updateData(MyEntity myEntity)
    {
    
    	 try (Connection con = jdbc.connect()) {
    		    PreparedStatement pt = con.prepareStatement("UPDATE pro SET name=?, salary=? WHERE id=?");
    		    pt.setString(1, myEntity.getName());
    		    pt.setDouble(2, myEntity.getSalary());
    		    pt.setInt(3, myEntity.getId());

    		    int update = pt.executeUpdate();
    		    if (update > 0) {
    		        System.out.println("Data Updated");
    		    } else {
    		        System.out.println("Data Not Updated");
    		    }
    		} catch (Exception e) {
    		    System.out.println(e.getMessage());
    		    e.printStackTrace();
    		}
    }
    public void deleteData(int id) {
        try (Connection con = jdbc.connect()) {
            PreparedStatement pt = con.prepareStatement("DELETE FROM pro WHERE id = ?");
            pt.setInt(1, id);
            int update = pt.executeUpdate();
            if (update > 0) {
                System.out.println("Data Deleted");
            } else {
                System.out.println("Data Not Deleted");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
}

