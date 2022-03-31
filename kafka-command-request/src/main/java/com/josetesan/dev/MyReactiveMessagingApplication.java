package com.josetesan.dev;

import com.josetesan.dev.avro.CommandRequest;
import io.smallrye.mutiny.Uni;
import io.smallrye.reactive.messaging.MutinyEmitter;
import org.eclipse.microprofile.reactive.messaging.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

@Path("/command")
@ApplicationScoped
public class MyReactiveMessagingApplication {

    private static final Logger LOGGER = LoggerFactory.getLogger(MyReactiveMessagingApplication.class);

    @Channel("requests")
    MutinyEmitter<CommandRequest> emitter;


    @POST
    public Uni<Void> sendCommand(CommandRequest request) {
        LOGGER.info("Sending request {}", request.getCommand());
        return emitter.send(request);
    }
}
