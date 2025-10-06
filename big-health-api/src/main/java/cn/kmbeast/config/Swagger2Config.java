package cn.kmbeast.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * 注意：
 * 1. @EnableSwagger2 必须加，开启 Swagger2 功能
 * 2. 继承 WebMvcConfigurationSupport 或实现 WebMvcConfigurer，确保静态资源不被拦截
 */
@Configuration
@EnableSwagger2
public class Swagger2Config implements WebMvcConfigurer {

    /**
     * 1. 配置 Swagger2 核心对象 Docket（指定扫描的接口、文档信息）
     */
    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                // 配置文档基础信息
                .apiInfo(apiInfo())
                // 开启接口扫描（指定 Controller 所在包路径，必须准确！）
                .select()
                .apis(RequestHandlerSelectors.basePackage("cn.kmbeast.controller")) // 替换为你的Controller包路径
                .paths(PathSelectors.any()) // 扫描该包下所有接口（可自定义过滤，如 PathSelectors.ant("/api/**")）
                .build();
    }

    /**
     * 2. 自定义文档信息（标题、版本、作者等）
     */
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Spring Boot 2.x Swagger2 接口文档") // 文档标题
                .description("包含用户、订单模块接口，支持在线调试") // 接口描述
                .version("v1.0.0") // 接口版本
                // 作者信息（可选）
                .contact(new Contact("开发团队", "https://example.com", "dev@example.com"))
                .build();
    }

    /**
     * 3. 配置静态资源映射（关键！确保 Swagger UI 页面和依赖资源能被访问）
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 放行 Swagger UI 首页（swagger-ui.html）
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");
        // 放行 Swagger UI 依赖的 JS/CSS/图片等静态资源（webjars 目录）
        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }
}
