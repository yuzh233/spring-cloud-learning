package xyz.yuzh.learning.springcloud.inaction.comm.context;

import xyz.yuzh.learning.springcloud.inaction.comm.vo.User;

/**
 * 用于在服务间存放用户上下文
 *
 * @author Harry
 */
public class UserContextHolder {

    public static ThreadLocal<User> context = new ThreadLocal<>();

    public static User currentUser() {
        return context.get();
    }

    public static void set(User user) {
        context.set(user);
    }

    public static void shutdown() {
        context.remove();
    }

}
