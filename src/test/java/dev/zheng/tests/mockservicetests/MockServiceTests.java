package dev.zheng.tests.mockservicetests;

import dev.zheng.doas.complaintdao.ComplaintDao;
import dev.zheng.doas.userdao.UserDao;
import dev.zheng.entities.Complaint;
import dev.zheng.entities.Priority;
import dev.zheng.entities.User;
import dev.zheng.entities.UserTitle;
import dev.zheng.services.complaintservice.ComplaintService;
import dev.zheng.services.complaintservice.ComplaintServiceImpl;
import dev.zheng.services.complaintservice.exceptions.InvalidPriorityException;
import dev.zheng.services.complaintservice.exceptions.NullComplaintBodyException;
import dev.zheng.services.userservice.UserService;
import dev.zheng.services.userservice.UserServiceImpl;
import dev.zheng.services.userservice.exceptions.InvalidUserStatus;
import dev.zheng.services.userservice.exceptions.PwdNotMatchException;
import dev.zheng.services.userservice.exceptions.UserNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.HashMap;
import java.util.Map;

public class MockServiceTests {
    UserDao userDao = Mockito.mock(UserDao.class);
    UserService userService = new UserServiceImpl(userDao);

    ComplaintDao complaintDao = Mockito.mock(ComplaintDao.class);
    ComplaintService complaintService = new ComplaintServiceImpl(complaintDao);

    @Test
    void user_login_failed_test(){
        User user = new User(0, "raiden", "shogun", "raiden123", "raiden123", UserTitle.CONSTITUENT, false);
        Mockito.when(userDao.getUserByUsername("raiden123")).thenReturn(user);
        Mockito.when(userDao.getUserByUsername("raiden1")).thenReturn(null);
        Assertions.assertThrows(PwdNotMatchException.class, ()-> {
            userService.validateUser("raiden123", "wrong pwd");
        });
        Assertions.assertThrows(UserNotFoundException.class, ()-> {
            userService.validateUser("raiden1", "raiden123");
        });
    }
    @Test
    void user_status_change_failed_test(){
        User user = new User(0, "raiden", "shogun", "raiden123", "raiden123", UserTitle.CONSTITUENT, false);
        Map<String, String> resMap = new HashMap<>();
        resMap.put("id", Integer.toString(user.getId()));
        resMap.put("approved", String.valueOf(true));
        Mockito.when(userDao.updateUserStatus(user.getId(), true)).thenReturn(resMap);
        Assertions.assertThrows(InvalidUserStatus.class, ()-> {
            userService.patchUserStatus(user.getId(),"nice");
        });
    }
    @Test
    void empty_complaint_body_test(){
        Complaint complaint = new Complaint(1, null,null, Priority.HIGH, -1);
        Mockito.when(complaintDao.createComplaint(complaint)).thenReturn(complaint);
        Assertions.assertThrows(NullComplaintBodyException.class, ()-> {
            complaintService.addComplaint(complaint);
        });
    }
    @Test
    void update_complaint_failed_test(){
        Complaint complaint = new Complaint(1, "emergency","Clear the road", Priority.LOW, -1);
        Map<String, String> resMap = new HashMap<>();
        resMap.put("id", Integer.toString(complaint.getId()));
        resMap.put("priority", Priority.HIGH.toString());
        Mockito.when(complaintDao.updatePriority(complaint.getId(),Priority.HIGH)).thenReturn(resMap);
        Mockito.when(complaintDao.getOneComplaint(complaint.getId())).thenReturn(complaint);
        Assertions.assertThrows(InvalidPriorityException.class, ()-> {
            complaintService.updateComplaintPriority(complaint.getId(), "lllow");
        });
    }
}
