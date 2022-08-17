package dev.zheng.doas.complaintdao;

import dev.zheng.entities.Complaint;
import dev.zheng.entities.Priority;
import dev.zheng.utils.ConnectionUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ComplaintDaoPostgres implements ComplaintDao{
    @Override
    public Complaint createComplaint(Complaint complaint) {
        try(Connection conn = ConnectionUtil.createConnection()){
            String sql = "insert into complaint values(default, ?, default, default)";
            PreparedStatement ps = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setString(1, complaint.getDescription());
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
}
