package xyz.yuzh.learning.springcloud.zuul.zuulserver.filter;

import com.netflix.zuul.ZuulFilter;
import org.springframework.stereotype.Component;

import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.PRE_TYPE;

/**
 * 第一个「pre」类型的定制过滤器
 *
 * @author Harry Zhang
 * @since 2019-09-05 16:01
 */
@Component
public class FirstPreFilter extends ZuulFilter {

    /**
     * 指定 filter 类型
     */
    @Override
    public String filterType() {
        return PRE_TYPE;
    }

    /**
     * 指定 filter 顺序
     */
    @Override
    public int filterOrder() {
        return 0;
    }

    /**
     * 指定 filter 是否生效
     */
    @Override
    public boolean shouldFilter() {
        return true;
    }

    /**
     * 指定 filter 处理逻辑
     */
    @Override
    public Object run() {
        System.out.println("这是第一个自定义Zuul Filter！");
        return null;
    }

}
