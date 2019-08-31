package com.zhangxy;

import java.io.IOException;

import javax.servlet.MultipartConfigElement;

import org.beetl.core.resource.ClasspathResourceLoader;
import org.beetl.core.resource.WebAppResourceLoader;
import org.beetl.ext.spring.BeetlGroupUtilConfiguration;
import org.beetl.ext.spring.BeetlSpringViewResolver;
import org.beetl.sql.core.ClasspathLoader;
import org.beetl.sql.ext.spring4.BeetlSqlScannerConfigurer;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternUtils;
import org.springframework.util.ResourceUtils;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;

@SpringBootApplication
@MapperScan("com.zhangxy.mapper")
public class BlogApplication {

	
	public static void main(String[] args) {
		SpringApplication.run(BlogApplication.class, args);
	}
	
	/**
	     * 解决文件上传,临时文件夹被程序自动删除问题
	     *
	     * 文件上传时自定义临时路径
	     * @return
     */
    @Bean
    MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        //2.该处就是指定的路径(需要提前创建好目录，否则上传时会抛出异常)
        factory.setLocation("/data/uploadtmp");
        return factory.createMultipartConfig();
    }
	
	protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**").addResourceLocations(ResourceUtils.CLASSPATH_URL_PREFIX + "/static/");
    }
	
	@Bean(initMethod = "init", name = "beetlConfig")
    public BeetlGroupUtilConfiguration getBeetlGroupUtilConfiguration() {
        BeetlGroupUtilConfiguration beetlGroupUtilConfiguration = new BeetlGroupUtilConfiguration();
		/*
	     * ResourcePatternResolver patternResolver = ResourcePatternUtils.getResourcePatternResolver(new DefaultResourceLoader());
		 * try { // WebAppResourceLoader 配置root路径是关键 WebAppResourceLoader
		 * webAppResourceLoader = new
		 * WebAppResourceLoader(patternResolver.getResource("classpath:/").getFile().
		 * getPath());
		 * beetlGroupUtilConfiguration.setResourceLoader(webAppResourceLoader); } catch
		 * (IOException e) { e.printStackTrace(); } //读取配置文件信息
		 * beetlGroupUtilConfiguration.setConfigFileResource(patternResolver.getResource
		 * ("classpath:beetl.properties")); return beetlGroupUtilConfiguration;
		 */
        ClasspathResourceLoader classPathLoader = new ClasspathResourceLoader(this.getClass().getClassLoader(),
                "/");
        beetlGroupUtilConfiguration.setResourceLoader(classPathLoader);
        // 读取配置文件信息
        ResourcePatternResolver patternResolver = ResourcePatternUtils
                .getResourcePatternResolver(new DefaultResourceLoader());
        beetlGroupUtilConfiguration.setConfigFileResource(patternResolver.getResource("classpath:beetl.properties"));
        return beetlGroupUtilConfiguration;
    }

    @Bean(name = "beetlViewResolver")
    public BeetlSpringViewResolver getBeetlSpringViewResolver(@Qualifier("beetlConfig") BeetlGroupUtilConfiguration beetlGroupUtilConfiguration) {
        BeetlSpringViewResolver beetlSpringViewResolver = new BeetlSpringViewResolver();
        //beetlSpringViewResolver.setPrefix("templates/");
        beetlSpringViewResolver.setSuffix(".html");
        beetlSpringViewResolver.setContentType("text/html;charset=UTF-8");
        beetlSpringViewResolver.setOrder(0);
        beetlSpringViewResolver.setConfig(beetlGroupUtilConfiguration);
        return beetlSpringViewResolver;
    }
    
}
