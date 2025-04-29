package org.acme;

import static org.junit.jupiter.api.Assertions.assertEquals;

import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.TestProfile;

import java.util.Map;

import jakarta.inject.Inject;

import org.eclipse.microprofile.reactive.messaging.Message;
import org.junit.jupiter.api.Test;

@QuarkusTest
@TestProfile(CopyOfMyMessagingApplicationTest.DummyProfile.class)
class CopyOfMyMessagingApplicationTest {
    public static class DummyProfile implements io.quarkus.test.junit.QuarkusTestProfile {
        @Override
        public Map<String, String> getConfigOverrides() {
            return Map.of("foo", "bar");
        }
    }

    @Inject
    MyMessagingApplication application;

    @Test
    void test() {
        assertEquals("bar", application.getFoo());
        assertEquals("HELLO", application.toUpperCase(Message.of("Hello")).getPayload());
        assertEquals("BONJOUR", application.toUpperCase(Message.of("bonjour")).getPayload());
    }
}
