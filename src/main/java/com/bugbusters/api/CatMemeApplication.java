package com.bugbusters.api;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.jaxrs2.integration.resources.OpenApiResource;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.jackson.JacksonFeature;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.Set;

/**
 * This class sets the base API path, registers resource classes,
 * and enables support for multipart requests, JSON serialization,
 * and OpenAPI documentation generation.
 *
 * @author Justin Gritton-Bell
 */
@OpenAPIDefinition(
        info = @Info(
                title = "Cat Meme Generator API",
                version = "1.0.0",
                description = "This service allows users to upload an image and add text overlays " +
                        "to create a meme image. The meme is stored in an AWS S3 bucket as well as " +
                        "in a MySql database. This service was created by Jared Doderer, Justin " +
                        "Gritton-Bell, Porter Taylor, and Alison Fait, Enterprise Java students at " +
                        "Madison Area Technical College."
        )
)
@ApplicationPath("/api")
public class CatMemeApplication extends Application {

    /**
     * Registers the classes used by the JAX-RS application, along with
     * resource classes, and addtional features (multipart support, JSON).
     * @return set of classes
     */
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
