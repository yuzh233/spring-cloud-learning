package xyz.yuzh.learning.springcloud.inaction.comm.context;

public class HystrixThreadLocal {
    public static ThreadLocal<String> threadLocal = new ThreadLocal<>();
}