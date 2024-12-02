package com.example.provider;

import io.grpc.stub.StreamObserver;
import com.example.miku.Miku;
import com.example.miku.MikuServiceGrpc;

public class MikuServiceImpl extends MikuServiceGrpc.MikuServiceImplBase {

    @Override
    public void getMessage(Miku.EmptyRequest request, StreamObserver<Miku.MessageResponse> responseObserver) {
        Miku.MessageResponse response = Miku.MessageResponse.newBuilder()
                .setMessage("Hatsune Miku says hello!")
                .build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void getPhoneNumber(Miku.EmptyRequest request, StreamObserver<Miku.PhoneNumberResponse> responseObserver) {
        Miku.PhoneNumberResponse response = Miku.PhoneNumberResponse.newBuilder()
                .setPhoneNumber("+81-123-456-7890")
                .build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
