package com.bugbusters.api;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.jaxrs2.integration.resources.OpenApiResource;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.jackson.JacksonFeature;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.Set;

@OpenAPIDefinition(
        info = @Info(
                title       = "Cat Meme Generator API",
                version     = "1.0.0",
                description = "Upload an image, add text overlays, store in S3 & the DB."
        )
)
@ApplicationPath("/api")
public class CatMemeApplication extends Application {
    @Override
    public Set<Class<?>> getClasses() {
        return Set.of(
                CatMemeResource.class,   // your endpoints
                MultiPartFeature.class,   // enable multipart/form-data
                JacksonFeature.class,     // JSON ↔ POJO
                OpenApiResource.class     // generates /openapi.json
        );
    }
}
