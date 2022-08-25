package dev.zheng.handlers;

import com.google.gson.Gson;
import dev.zheng.dtos.LoginCredentials;
import dev.zheng.entities.User;
import dev.zheng.services.userservice.UserService;
import dev.zheng.services.userservice.exceptions.PwdNotMatchException;
import dev.zheng.services.userservice.exceptions.UserNotFoundException;
import io.javalin.http.Handler;

public class UserHandler {
    private Gson gson = new Gson();
    public UserService userService;

    public UserHandler(UserService userService){this.userService = userService;}

    public Handler createUser = ctx -> {
        User user = gson.fromJson(ctx.body(), User.class);
        User registeredUser = userService.registerUser(user);
        if (registeredUser == null){
            ctx.status(500);
            ctx.result("Something went wrong");
            return;
        }
        ctx.status(201);
        ctx.result(gson.toJson(registeredUser));
    };
    public Handler loginUser = ctx -> {
        LoginCredentials credentials = gson.fromJson(ctx.body(), LoginCredentials.class);
        try {
            User user = userService.validateUser(credentials.getUsername(), credentials.getPassword());
            ctx.result(gson.toJson(user));
        } catch (PwdNotMatchException err){
            ctx.status(403);
            ctx.result("Password mismatch");
        } catch (UserNotFoundException err){
            ctx.status(404);
            ctx.result("User not found");
        }
    };

}
