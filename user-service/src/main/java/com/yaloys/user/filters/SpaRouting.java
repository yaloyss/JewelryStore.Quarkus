package com.yaloys.user.filters;

import io.quarkus.runtime.StartupEvent;
import io.vertx.ext.web.Router;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import java.util.regex.Pattern;
import java.util.stream.Stream;

@ApplicationScoped
public class SpaRouting {

    private static final String[] API_PREFIXES = {"/api/", "/q/"};
    private static final Pattern HAS_FILE_EXTENSION = Pattern.compile(".+\\.[a-zA-Z0-9]+$");

    void register(@Observes StartupEvent event, Router router) {
        router.get("/*").order(Integer.MAX_VALUE).handler(ctx -> {
            String path = ctx.normalizedPath();
            if (path.equals("/")
                    || Stream.of(API_PREFIXES).anyMatch(path::startsWith)
                    || HAS_FILE_EXTENSION.matcher(path).matches()) {
                ctx.next();
            } else {
                ctx.reroute("/index.html");
            }
        });
    }
}
