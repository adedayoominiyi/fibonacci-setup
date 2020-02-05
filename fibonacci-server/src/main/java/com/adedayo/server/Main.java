package com.adedayo.server;

import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

public class Main {

    private static final int PORT = 9091;
    private static final Logger logger = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) throws IOException, InterruptedException {
        final Server server = ServerBuilder.forPort(PORT).addService(new FibServer()).build();

        logger.info("Server started, listening on " + PORT);
        server.start();
        logger.info("Server started, listening on " + PORT);

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            // Use stderr here since the logger may have been reset by its JVM shutdown hook.
            System.err.println("====Shutting down gRPC server since JVM is shutting down====");
            try {
                server.shutdown().awaitTermination(5L, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                e.printStackTrace(System.err);
            }
            System.err.println("====gRPC server shut down====");
        }));

        // Block server until shutdown
        server.awaitTermination();
    }
}
