package com.josetesan.dev;

import com.josetesan.dev.avro.CommandRequest;
import com.josetesan.dev.avro.CommandResponse;
import io.smallrye.common.vertx.ContextLocals;
import io.smallrye.reactive.messaging.kafka.IncomingKafkaRecordBatch;
import io.smallrye.reactive.messaging.kafka.KafkaRecordBatch;
import org.eclipse.microprofile.reactive.messaging.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import java.util.Random;

@ApplicationScoped
public class MyReactiveMessagingApplication {

    private static final Logger LOGGER = LoggerFactory.getLogger(MyReactiveMessagingApplication.class);


    private static final Random rand = new Random() ;

    /**
     * Consume the message from the "words-in" channel, uppercase it and send it to the uppercase channel.
     * Messages come from the broker.
     **/
    @Incoming("request")
    @Outgoing("response")
    public Message<CommandResponse> response(Message<CommandRequest> message) {

        LOGGER.info("Received request {}", message.getPayload());
        message.ack();
        var response = CommandResponse.newBuilder()
                .setId(rand.nextLong())
                .setRequestId(message.getPayload().getId())
                .setResult(message.getPayload().getCommand().concat(" world"))
                .setStatus(0);

        return Message.of(response.build());
    }


}
