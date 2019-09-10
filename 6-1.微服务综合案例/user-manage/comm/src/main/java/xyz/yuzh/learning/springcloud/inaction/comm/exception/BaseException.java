package xyz.yuzh.learning.springcloud.inaction.comm.exception;

import lombok.Data;

@Data
public class BaseException extends RuntimeException {

    private static final long serialVersionUID = 1796731834836398434L;
    private Long businessId;
    private Integer code;
    private String codeEN;
    private String businessMessage;


    /**
     * 基础异常
     *
     * @param code       异常编码：数字
     * @param codeEN     异常编码：英文短语
     * @param message    异常信息
     * @param businessId 相关业务ID
     */
    public BaseException(Integer code, String codeEN, String message, Long businessId) {
        this(code, codeEN, message, businessId, null);
    }

    public BaseException(Integer code, String codeEN, String message, Long businessId, Throwable t) {
        super(message, t);
        this.businessId = businessId;
        this.code = code;
        this.codeEN = codeEN;
        this.businessMessage = message;
    }


}
