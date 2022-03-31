package com.josetesan.dev;

import com.josetesan.dev.avro.CommandRequest;
import com.josetesan.dev.avro.CommandResponse;
import io.quarkus.runtime.StartupEvent;
import io.smallrye.reactive.messaging.MutinyEmitter;
import org.eclipse.microprofile.reactive.messaging.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import java.util.Random;
import java.util.stream.Stream;

@ApplicationScoped
public class MyReactiveMessagingApplication {

    private static final Logger LOGGER = LoggerFactory.getLogger(MyReactiveMessagingApplication.class);

    /**
     * Consume the message from the "words-in" channel, uppercase it and send it to the uppercase channel.
     * Messages come from the broker.
     **/
    @Incoming("request")
    @Outgoing("response")
    public Message<CommandResponse> toUpperCase(Message<CommandRequest> message) {
        LOGGER.info("Received request {}", message.getPayload());
        var response = CommandResponse.newBuilder()
                .setId(new Random().nextLong())
                .setRequestId(message.getPayload().getId())
                .setResult(message.getPayload().getCommand().concat(" world"))
                .setStatus(0);

        return Message.of(response.build());
    }


}
