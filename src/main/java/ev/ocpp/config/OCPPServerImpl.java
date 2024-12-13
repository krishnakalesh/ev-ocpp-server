package ev.ocpp.config;

import org.springframework.stereotype.Component;

import eu.chargetime.ocpp.JSONServer;
import eu.chargetime.ocpp.ServerEvents;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import lombok.Builder;

@Component
@AllArgsConstructor
@Builder
public class OCPPServerImpl {
    private final ServerEvents serverEvents;
    private final JSONServer server;

    @PostConstruct
    public void startServer() {
        server.open("localhost", 4000, serverEvents);
    }
}
