package dev.zheng.handlers;

import com.google.gson.Gson;
import dev.zheng.entities.User;
import dev.zheng.services.userservice.UserService;
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
}
