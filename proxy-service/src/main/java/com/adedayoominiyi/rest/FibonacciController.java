package com.adedayoominiyi.rest;

import com.adedayoominiyi.fibonacci.FibServiceGrpc;
import com.adedayoominiyi.fibonacci.Fibonacci;

import io.grpc.ManagedChannel;
import io.grpc.stub.StreamObserver;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.*;
import io.reactivex.Flowable;
import org.reactivestreams.Publisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.Objects;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

@Controller("/")
public class FibonacciController {

    private static final Logger log = LoggerFactory.getLogger(FibonacciController.class);

    private final ManagedChannel fibonnaciServerChannel;

    public FibonacciController(ManagedChannel fibonnaciServerChannel) {
        this.fibonnaciServerChannel = Objects.requireNonNull(fibonnaciServerChannel);
    }

    @Get(uri = "/fibonacci/{maxNumber}", produces = MediaType.APPLICATION_JSON)
    public Publisher<Long> fibonacci(
            @Min(value = 1, message = "{maxNumber} cannot be less than 1")
            @Max(value = Integer.MAX_VALUE, message = "{maxNumber} cannot be greater than 2147483647")
            final long maxNumber) {
        final LinkedBlockingQueue<Long> blockingQueue = new LinkedBlockingQueue<>();
        final AtomicBoolean isFinished = new AtomicBoolean(false);
        final Fibonacci.FibRequest.Builder reqBuilder = Fibonacci.FibRequest.newBuilder().setMaxNumber((int) maxNumber);
        FibServiceGrpc.FibServiceStub stub = FibServiceGrpc.newStub(fibonnaciServerChannel);
        stub.fibSeq(reqBuilder.build(), new StreamObserver<Fibonacci.FibResponse>() {

            @Override
            public void onNext(Fibonacci.FibResponse fibResponse) {
                log.info("fibResponse: {}", fibResponse);
                try {
                    blockingQueue.put(fibResponse.getNextNumber());
                } catch (InterruptedException ex) {
                    log.error("Error occurred onNext", ex);
                }
            }

            @Override
            public void onError(Throwable throwable) {
                log.error("Error occurred onError", throwable);
            }

            @Override
            public void onCompleted() {
                log.info("onCompleted called");
                isFinished.set(true);
            }
        });

        return Flowable.generate(emitter -> {
            Long nextValue = blockingQueue.poll(10L, TimeUnit.MILLISECONDS);
            while (nextValue == null) {
                if (isFinished.get()) {
                    emitter.onComplete();
                    break;
                } else {
                    nextValue = blockingQueue.poll(10L, TimeUnit.MILLISECONDS);
                }
            }
            if (nextValue != null) {
                emitter.onNext(nextValue);
            }
        });
    }
}
