package com.example.consumer;

import com.example.miku.MikuServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import com.example.miku.Miku;

public class ConsumerService {

    private final MikuServiceGrpc.MikuServiceBlockingStub blockingStub;

    public ConsumerService(String host, int port) {
        // Create a gRPC channel to the server
        ManagedChannel channel = ManagedChannelBuilder
                .forAddress(host, port)
                .usePlaintext() // Disable TLS for local testing
                .build();

        // Create a blocking stub for synchronous RPC calls
        this.blockingStub = MikuServiceGrpc.newBlockingStub(channel);
    }

    public String getMikuMessage() {
        // Call the GetMessage RPC
        Miku.EmptyRequest request = Miku.EmptyRequest.newBuilder().build();
        Miku.MessageResponse response = blockingStub.getMessage(request);
        return response.getMessage();
    }

    public String getMikuPhoneNumber() {
        // Call the GetPhoneNumber RPC
        Miku.EmptyRequest request = Miku.EmptyRequest.newBuilder().build();
        Miku.PhoneNumberResponse response = blockingStub.getPhoneNumber(request);
        return response.getPhoneNumber();
    }
}
