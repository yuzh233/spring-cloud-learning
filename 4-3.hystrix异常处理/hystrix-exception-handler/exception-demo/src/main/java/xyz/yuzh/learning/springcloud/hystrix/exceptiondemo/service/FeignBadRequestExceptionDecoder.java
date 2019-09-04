package xyz.yuzh.learning.springcloud.hystrix.exceptiondemo.service;

import com.netflix.hystrix.exception.HystrixBadRequestException;
import feign.Response;
import feign.Util;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * 使用 ErrorDecoder 实现对 Feign 调用时产生 BadRequest 这种无法被 fallback 的异常的封装。
 *
 * @author Harry
 */
@Component
public class FeignBadRequestExceptionDecoder implements feign.codec.ErrorDecoder {

    @Override
    public Exception decode(String methodKey, Response response) {
        try {
            if (response.status() >= 400 && response.status() <= 499) {
                String error = Util.toString(response.body().asReader());
                return new HystrixBadRequestException(error);
            }
        } catch (IOException e) {
            System.out.println(e);
        }
        return feign.FeignException.errorStatus(methodKey, response);
    }

}

