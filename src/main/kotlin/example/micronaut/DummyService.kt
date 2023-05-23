package example.micronaut

import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry
import io.github.resilience4j.core.registry.EntryAddedEvent
import io.micronaut.context.event.ApplicationEventListener
import io.micronaut.context.event.StartupEvent
import jakarta.inject.Singleton
import org.slf4j.Logger
import org.slf4j.LoggerFactory

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker
//import io.github.resilience4j.micronaut.annotation.CircuitBreaker

@Singleton
class DummyService(val circuitBreakerRegistry : CircuitBreakerRegistry) : ApplicationEventListener<StartupEvent> {
    val log: Logger = LoggerFactory.getLogger(this::class.java)

    override fun onApplicationEvent(event: StartupEvent?) {
        log.info("onApplicationEvent")

        circuitBreakerRegistry.eventPublisher.onEntryAdded { entryAddedEvent: EntryAddedEvent<io.github.resilience4j.circuitbreaker.CircuitBreaker?> ->
            log.info("circuitBreakerRegistry onEntryAdded")
        }
    }
}

class DummyCbClient {
    val log: Logger = LoggerFactory.getLogger(this::class.java)
    @CircuitBreaker(name = "dummy")
    fun someCircuitBreaker() {
        log.info("dummy Circuit breaker ran.")
    }

}