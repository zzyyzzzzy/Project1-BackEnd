package dev.zheng.doas.complaintdao;

import dev.zheng.entities.Complaint;
import dev.zheng.entities.Priority;
import dev.zheng.utils.ConnectionUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ComplaintDaoPostgres implements ComplaintDao{


    private Complaint complaintBuilder(ResultSet rs) throws SQLException{
        Complaint complaint = new Complaint(rs.getInt("id"),rs.getString("summary"),
                rs.getString("description"), Priority.valueOf(rs.getString("priority")),
                rs.getInt("meeting_id"));
        return complaint;
    }
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
    public Complaint getOneComplaint(int id) {
        try(Connection conn = ConnectionUtil.createConnection()){
            String sql = "select * from complaint where id=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            rs.next();
            Complaint complaint = complaintBuilder(rs);
            return complaint;
        }catch (SQLException err){
            System.out.println("Unable to get a user by Id");
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
                Complaint complaint = complaintBuilder(rs);
                complaintList.add(complaint);
            }
            return complaintList;
        }catch(SQLException err){
            System.out.println("Unable to get complaints list due to SQL exception occurred");
            return null;
        }
    }

    @Override
    public Map<String, String> updatePriority(int id, Priority priority) {
        try(Connection conn = ConnectionUtil.createConnection()){
            String sql = "update complaint set priority=? where id=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, priority.toString());
            ps.setInt(2, id);
            Map<String, String> resMap = new HashMap<>();
            if(ps.executeUpdate() > 0){
                resMap.put("id", Integer.toString(id));
                resMap.put("priority", priority.toString());
                return resMap;
            } else{
                System.out.println("Update Failed");
                return null;
            }
        }catch (SQLException err){
            System.out.println("Unable to update priority due to SQL exception occurred");
            return null;
        }
    }

    @Override
    public Complaint updateAttachedMeeting(int id, int meetId) {
        return null;
    }


}
