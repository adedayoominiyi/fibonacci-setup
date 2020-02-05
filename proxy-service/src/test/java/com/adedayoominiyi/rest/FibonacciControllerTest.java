package com.adedayoominiyi.rest;

import io.micronaut.context.annotation.Property;
import io.micronaut.core.type.Argument;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.MediaType;
import io.micronaut.http.client.RxHttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.test.annotation.MicronautTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import java.util.Arrays;
import java.util.List;

@MicronautTest
@Property(name = "fibonacci.server.host", value = "localhost")
@Property(name = "fibonacci.server.port", value = "9091")
class FibonacciControllerTest {

    @Inject
    @Client("/")
    RxHttpClient client;

    @Test
    void testFibonacciEndpoint() {
        List<Long> numbersList = client.retrieve(
                HttpRequest.GET("/fibonacci/17")
                        .header("content-type", MediaType.APPLICATION_JSON),
                Argument.listOf(Long.class)).blockingFirst();
        Assertions.assertEquals(8, numbersList.size());
		Assertions.assertEquals(Arrays.asList(new Long[] {0L, 1L, 1L, 2L, 3L, 5L, 8L, 13L}), numbersList);
    }
}
