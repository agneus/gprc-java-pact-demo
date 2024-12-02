package com.example.consumer;


import au.com.dius.pact.consumer.junit5.PactConsumerTestExt;
import au.com.dius.pact.consumer.junit5.PactTestFor;
import com.example.miku.Miku;
import com.example.miku.MikuServiceGrpc;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import au.com.dius.pact.consumer.MockServer;
import au.com.dius.pact.consumer.dsl.PactBuilder;
import au.com.dius.pact.consumer.junit.MockServerConfig;
import au.com.dius.pact.consumer.model.MockServerImplementation;
import au.com.dius.pact.core.model.PactSpecVersion;
import au.com.dius.pact.core.model.V4Interaction;
import au.com.dius.pact.core.model.V4Pact;
import au.com.dius.pact.core.model.annotations.Pact;
import com.google.protobuf.InvalidProtocolBufferException;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

import java.util.List;
import java.util.Map;

import static au.com.dius.pact.consumer.dsl.PactBuilder.filePath;

@ExtendWith(PactConsumerTestExt.class)
@PactTestFor(providerName = "grpc-hatsune-miku-provider", providerType = au.com.dius.pact.consumer.junit5.ProviderType.SYNCH_MESSAGE, pactVersion = PactSpecVersion.V4)
public class ConsumerContractTest {

    @Pact(consumer = "grpc-consumer-miku")
    V4Pact getMikuMessage(PactBuilder builder) {
        return builder
                // Specify that we're using the Protobuf plugin
                .usingPlugin("protobuf")
                // Define the synchronous gRPC interaction
                .expectsToReceive("get Miku message", "core/interaction/synchronous-message")
                .with(Map.of(
                        // Define the proto file, content type, and service method
                        "pact:proto", filePath("src/main/proto/miku.proto"),
                        "pact:content-type", "application/grpc",
                        "pact:proto-service", "MikuService/GetMessage",
                        // Define the expected request and response
                        "request", Map.of(
                                // No fields expected in the request
                        ),
                        "response", List.of(
                                Map.of(
                                        "message", "Hatsune Miku says hello!"
                                )
                        )
                ))
                .toPact();
    }

    @Test
    @PactTestFor(pactMethod = "getMikuMessage")
    @MockServerConfig(implementation = MockServerImplementation.Plugin, registryEntry = "protobuf/transport/grpc")
    void testGetMikuMessage(MockServer mockServer, V4Interaction.SynchronousMessages interaction) throws InvalidProtocolBufferException {
        ConsumerService consumerService = new ConsumerService("127.0.0.1", mockServer.getPort());
        consumerService.getMikuMessage();
    }
    /*

    @Pact(consumer = "grpc-consumer-miku")
    V4Pact getMikuPhoneNumber(PactBuilder builder) {
        return builder
                .usingPlugin("protobuf")
                .expectsToReceive("get Miku phone number", "core/interaction/synchronous-message")
                .with(Map.of(
                        "pact:proto", filePath("src/main/proto/miku.proto"),
                        "pact:content-type", "application/grpc",
                        "pact:proto-service", "MikuService/GetPhoneNumber",
                        "request", Map.of(),
                        "response", List.of(
                                Map.of(
                                        "phone_number", "+81-123-456-7890"
                                )
                        )
                ))
                .toPact();
    }

    @Test
    @PactTestFor(pactMethod = "getMikuPhoneNumber")
    @MockServerConfig(implementation = MockServerImplementation.Plugin, registryEntry = "protobuf/transport/grpc")
    void testGetMikuPhoneNumber(MockServer mockServer, V4Interaction.SynchronousMessages interaction) throws InvalidProtocolBufferException {
        ConsumerService consumerService = new ConsumerService("127.0.0.1", mockServer.getPort());
        consumerService.getMikuPhoneNumber();
    }
     */

}