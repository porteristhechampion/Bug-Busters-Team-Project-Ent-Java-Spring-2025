package com.bugbusters.api;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import java.util.HashSet;
import java.util.Set;

import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.jackson.JacksonFeature;

@ApplicationPath("/api")
public class CatMemeApplication extends Application {
    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> classes = new HashSet<>();
        // your resource(s)
        classes.add(CatMemeResource.class);
        // enable multipart/form-data
        classes.add(MultiPartFeature.class);
        // enable JSON <-> POJO marshalling
        classes.add(JacksonFeature.class);
        return classes;
    }
}
