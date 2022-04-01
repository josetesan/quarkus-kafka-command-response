package com.josetesan.dev;

import com.josetesan.dev.avro.CommandResponse;
import io.smallrye.mutiny.Uni;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.eclipse.microprofile.reactive.messaging.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class Receiver {

    private static final Logger LOGGER = LoggerFactory.getLogger(Receiver.class);

    /**
     * Consume the uppercase channel (in-memory) and print the messages.
     **/
    @Incoming("responses")
    public Uni<Void> sink(Message<CommandResponse> response)
    {
        LOGGER.info("Received response {}", response.getPayload());
        return Uni.createFrom().completionStage(response.ack());
    }
}
