package com.adedayoominiyi.factory;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.micronaut.context.annotation.Factory;
import io.micronaut.context.annotation.Value;

import javax.inject.Singleton;

@Factory
public class IocFactory {

    @Singleton
    public ManagedChannel fibonnaciServerChannel(@Value("${fibonacci.server.host:localhost}") String grpcServerHost,
                                                 @Value("${fibonacci.server.port:9091}") int grpcServerPort) {
        return ManagedChannelBuilder.forAddress(grpcServerHost, grpcServerPort)
                .usePlaintext().build();
    }
}
