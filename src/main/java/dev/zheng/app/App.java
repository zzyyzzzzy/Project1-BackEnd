package dev.zheng.app;

import dev.zheng.doas.complaintdao.ComplaintDaoPostgres;
import dev.zheng.handlers.ComplaintHandler;
import dev.zheng.services.complaintservice.ComplaintService;
import dev.zheng.services.complaintservice.ComplaintServiceImpl;
import io.javalin.Javalin;

public class App {
    public static void main(String[] args) {
        Javalin app = Javalin.create( config -> {
            config.enableCorsForAllOrigins();
        });
        ComplaintService complaintService = new ComplaintServiceImpl(new ComplaintDaoPostgres());
        ComplaintHandler complaintHandler = new ComplaintHandler(complaintService);

        app.post("complaints", complaintHandler.createComplaint);

        app.start();
    }
}
