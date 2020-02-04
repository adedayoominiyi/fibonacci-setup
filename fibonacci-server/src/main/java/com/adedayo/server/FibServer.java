package com.adedayo.server;

import com.adedayoominiyi.fibonacci.FibServiceGrpc;
import com.adedayoominiyi.fibonacci.Fibonacci;
import io.grpc.stub.StreamObserver;

public class FibServer extends FibServiceGrpc.FibServiceImplBase {

    @Override
    public void fibSeq(Fibonacci.FibRequest request, StreamObserver<Fibonacci.FibResponse> responseObserver) {
        final long maxNumber = request.getMaxNumber();

        long a = 0;
        long b = 1;

        while (a < maxNumber) {
            long temp = a;
            a = b;
            b = temp + a;

            Fibonacci.FibResponse.Builder responseBuilder = Fibonacci.FibResponse.newBuilder().setNextNumber(temp);
            responseObserver.onNext(responseBuilder.build());
        }

        responseObserver.onCompleted();
    }
}
