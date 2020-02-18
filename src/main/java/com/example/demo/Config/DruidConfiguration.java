package com.example.demo.Config;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class DruidConfiguration {

    /*ConfigurationProperties的作用：
     //吧yml中前缀spring.datasource中的数据与DruidDataSource实例中的绑定到一起
          initial-size: 8
          min-idle: 1
          max-active: 20
          max-wait: 60000
     */
    @ConfigurationProperties(prefix = "spring.datasource")
    @Bean
    public DataSource getDataSource(){return  new DruidDataSource( );}
    //配置Druid监控

    /**
     * 注册一个web监控的filter
     * @return
     */

        @Bean
        public FilterRegistrationBean webStatFilter() {
            FilterRegistrationBean bean = new FilterRegistrationBean();
            bean.setFilter(new WebStatFilter());

            Map<String, String> initParams = new HashMap<>();
            initParams.put("exclusions", "*.js,*.css,/druid/*");

            bean.setInitParameters(initParams);

            bean.setUrlPatterns(Arrays.asList("/*"));

            return bean;
        }




    /**
     * 配置一个管理后台的Servlet
     * @return
     */
    @Bean
    public ServletRegistrationBean druidServlet() {
        ServletRegistrationBean bean = new ServletRegistrationBean(new StatViewServlet(),"/druid/*");
        HashMap<String, String> reg = new HashMap<>();
      //  reg.puter("resetEnable", "false");
        reg.put("loginUsername", "admin");
        reg.put("loginPassword", "123456");
        reg.put("allow", "");
        reg.put("deny", "192.168.31.111");
        return bean;
    }


}
