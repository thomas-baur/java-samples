package com.example;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;


import io.opentelemetry.api.GlobalOpenTelemetry;
import io.opentelemetry.api.trace.Span;
import io.opentelemetry.api.trace.SpanKind;
import io.opentelemetry.api.trace.Tracer;
import io.opentelemetry.semconv.trace.attributes.SemanticAttributes;
import io.quarkus.logging.Log;
import org.eclipse.microprofile.rest.client.RestClientBuilder;
import org.jboss.logging.Logger;

import java.net.URL;


@Path("/")
public class TracedResource {

    private static final Logger LOG = Logger.getLogger(TracedResource.class);

    @Context
    private UriInfo uriInfo;

    private GlobalOpenTelemetry openTelemetry;
    private Tracer tracer = openTelemetry.getTracer(
            "instrumentation-library-name", "1.0.0"
    );


    @GET
    @Path("service1")
    @Produces(MediaType.TEXT_PLAIN)
    public String service1() {

        Span span = Span.current();
        span.setAttribute(SemanticAttributes.THREAD_ID, 125L);
        span.setAttribute(SemanticAttributes.HTTP_URL, "http://localhost:8080/service1");
        span.setAttribute("tag", "service-thread");

        LOG.info("service 1 was called");
        //performLogentry();
        return "this is service 1";
    }

    @GET
    @Path("service1/{name}")
    @Produces(MediaType.TEXT_PLAIN)
    public String service1(@PathParam("name") String name) {

        LOG.info("service 1 was called with param: " + name);
        //performLogentry();

        return "service 1 was called with param: " + name;
    }

    @GET
    @Path("customers")
    @Produces(MediaType.TEXT_PLAIN)
    public String customerService() {

        LOG.info("customer service was called");
        String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
        return TracedResource.class.getName() + " - " + methodName + ": customer service was called";
    }

    @GET
    @Path("chain")
    @Produces(MediaType.TEXT_PLAIN)
    public String chain() {
        LOG.info("Uri: " + uriInfo.getBaseUri());
        ResourceClient resourceClient = RestClientBuilder.newBuilder()
                .baseUri(uriInfo.getBaseUri())
                .build(ResourceClient.class);
        return "chain -> " + resourceClient.service1();
    }

    private void performLogentry() {
        String trace_id = Span.current().getSpanContext().getTraceId();
        String span_id = Span.current().getSpanContext().getSpanId();

        LOG.info("/ Trace-id: " + trace_id + " / Span-id: " + span_id);
    }
}