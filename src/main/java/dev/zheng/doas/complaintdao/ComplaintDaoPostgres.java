package dev.zheng.doas.complaintdao;

import dev.zheng.entities.Complaint;
import dev.zheng.entities.Priority;
import dev.zheng.utils.ConnectionUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ComplaintDaoPostgres implements ComplaintDao{

    @Override
    public Complaint createComplaint(Complaint complaint) {
        try(Connection conn = ConnectionUtil.createConnection()){
            String sql = "insert into complaint values(default, ?, ?, default, default)";
            PreparedStatement ps = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setString(1, complaint.getSummary());
            ps.setString(2, complaint.getDescription());
            ps.execute();

            ResultSet rs = ps.getGeneratedKeys();
            rs.next();

            complaint.setId(rs.getInt("id"));
            complaint.setPriority(Priority.UNASSIGNED);
            return complaint;
        }catch (SQLException err){
            System.out.println("Unable to create a user due to SQL exception occurred");
            return null;
        }
    }

    @Override
    public List<Complaint> getAllComplaints() {
        try(Connection conn = ConnectionUtil.createConnection()){
            String sql = "select * from complaint";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            List <Complaint> complaintList = new ArrayList<>();
            while (rs.next()){
                Complaint complaint = new Complaint(rs.getInt("id"),rs.getString("summary"),
                        rs.getString("description"), Priority.valueOf(rs.getString("priority")),
                        rs.getInt("meeting_id"));
                complaintList.add(complaint);
            }
            return complaintList;
        }catch(SQLException err){
            System.out.println("Unable to get complaints list due to SQL exception occurred");
            return null;
        }
    }


}
