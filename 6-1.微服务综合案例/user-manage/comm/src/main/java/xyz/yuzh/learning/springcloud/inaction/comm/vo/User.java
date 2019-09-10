package xyz.yuzh.learning.springcloud.inaction.comm.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;


/**
 * 用户对象
 *
 * @author Harry
 */
@Data
public class User implements Serializable {

    private static final long serialVersionUID = -4083327605430665846L;

    public final static String CONTEXT_KEY_USERID = "x-customs-user";

    /**
     * 用户ID
     */
    private String userId;

    private String userName;

    public User() {

    }

    public User(Map<String, String> headers) {
        userId = headers.get(CONTEXT_KEY_USERID);
    }


    /**
     * 将user对象转换成为http对象头
     *
     * @return http头键值对
     */
    public Map<String, String> toHttpHeaders() {
        Map<String, String> headers = new HashMap<>();
        headers.put(CONTEXT_KEY_USERID, userId);
        return headers;
    }

}
