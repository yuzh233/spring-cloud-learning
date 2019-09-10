package xyz.yuzh.learning.springcloud.inaction.comm.exception;

import java.io.Serializable;

public class BaseExceptionBody implements Serializable {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = -1270478894426234738L;

    /**
     * 相关业务ID
     */
    private Long businessId;

    /**
     * 异常编码：数字
     */
    private Integer code;

    /**
     * 异常编码：英文短语
     */
    private String codeEN;

    /**
     * 异常信息
     */
    private String businessMessage;

    /**
     * 异常类型
     */
    private String exceptionType;


    public BaseExceptionBody() {

    }

    public BaseExceptionBody(BaseException exception) {
        this.businessId = exception.getBusinessId();
        this.code = exception.getCode();
        this.codeEN = exception.getCodeEN();
        this.businessMessage = exception.getMessage();
        this.exceptionType = exception.getClass().getName();
    }

}
