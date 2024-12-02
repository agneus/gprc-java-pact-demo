package com.example.provider;

import au.com.dius.pact.provider.junit5.PactVerificationContext;
import au.com.dius.pact.provider.junit5.PactVerificationInvocationContextProvider;
import au.com.dius.pact.provider.junit5.PluginTestTarget;
import au.com.dius.pact.provider.junitsupport.Provider;
import au.com.dius.pact.provider.junitsupport.loader.PactBroker;
import au.com.dius.pact.provider.junitsupport.loader.PactFolder;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestTemplate;
import org.junit.jupiter.api.extension.ExtendWith;

import java.io.IOException;
import java.util.Map;

@Provider("grpc-hatsune-miku-provider")
@PactBroker(url = "http://localhost:9292/")
class PactVerificationTest {

    static Server server;

    /**
     * Start the gRPC Hatsune Miku server
     */
    @BeforeAll
    static void setup() throws IOException {
        server = ServerBuilder.forPort(50051)
                .addService(new MikuServiceImpl()) // Your gRPC service implementation
                .build();
        server.start();
    }

    /**
     * Shut the server down after the test
     */
    @AfterAll
    static void cleanup() {
        if (server != null) {
            server.shutdownNow();
        }
    }

    /**
     * Configure the test target to use the Protobuf plugin with gRPC transport
     */
    @BeforeEach
    void setupTest(PactVerificationContext context) {
        context.setTarget(new PluginTestTarget(
                Map.of(
                        "host", "localhost",
                        "port", 50051,
                        "transport", "grpc"
                )
        ));
    }

    /**
     * Execute the test for all interactions in the loaded Pact files
     */
    @TestTemplate
    @ExtendWith(PactVerificationInvocationContextProvider.class)
    void pactVerificationTestTemplate(PactVerificationContext context) {
        context.verifyInteraction();
    }
}

