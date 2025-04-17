package com.bugbusters.config;

import com.bugbusters.api.CatMemeResource;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.jaxrs2.integration.resources.OpenApiResource;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.server.ResourceConfig;

import javax.ws.rs.ApplicationPath;

@OpenAPIDefinition(
        info = @Info(
                title = "Cat Meme Generator API",
                version = "1.0.0",
                description = "API to generate cat memes by adding text overlays on images."
        )
)
@ApplicationPath("/api")
public class SwaggerConfig extends ResourceConfig {

    public SwaggerConfig() {
        // Register your REST resource class
        register(CatMemeResource.class);

        // Register Swagger OpenAPI spec generator
        register(OpenApiResource.class);

        // Enable multipart and JSON
        register(MultiPartFeature.class);
        register(JacksonFeature.class);

        // Optionally scan whole package
        // packages("com.bugbusters.api");
    }
}
