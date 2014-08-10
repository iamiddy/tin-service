package com.iddymagohe.tin.rest.util;

import com.mangofactory.swagger.configuration.SpringSwaggerConfig;
import com.mangofactory.swagger.paths.RelativeSwaggerPathProvider;
import com.mangofactory.swagger.plugin.EnableSwagger;
import com.mangofactory.swagger.plugin.SwaggerSpringMvcPlugin;
import com.wordnik.swagger.model.ApiInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by iddymagohe on 8/9/14.
 */

@Configuration
@EnableSwagger //Loads the spring beans required by the framework
public class APIDocumentationConfiguration {
    private SpringSwaggerConfig springSwaggerConfig;

    /**
     * Required to autowire SpringSwaggerConfig
     */
    @Autowired
    public void setSpringSwaggerConfig(SpringSwaggerConfig springSwaggerConfig) {
        this.springSwaggerConfig = springSwaggerConfig;
    }

    /**
     * Every SwaggerSpringMvcPlugin bean is picked up by the swagger-mvc framework - allowing for multiple
     * swagger groups i.e. same code base multiple swagger resource listings.
     */
    @Bean
    public SwaggerSpringMvcPlugin swaggerSpringMvcPlugin() {
        return new SwaggerSpringMvcPlugin(springSwaggerConfig)
                .includePatterns(
                        "/business.*",
                        "/api-docs.*"

                )
                .pathProvider(new CustomRelativeSwaggerPathProvider()     )
                .apiInfo(apiInfo())
                .build();
    }

    private ApiInfo apiInfo() {
        ApiInfo apiInfo = new ApiInfo(
                "TIN Number service",
                "Verify Tanzania businesses by TIN numbers",
                "Free",
                "tinservice@tinservice.info",
                "My Apps API Licence Type",
                "My Apps API License URL"
        );
        return apiInfo;
    }

   // @Bean
    public CustomRelativeSwaggerPathProvider pathProvider(){
        return new CustomRelativeSwaggerPathProvider();
    }

}
