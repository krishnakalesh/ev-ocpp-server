package ev.ocpp.config;

import java.io.IOException;
import java.security.GeneralSecurityException;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import eu.chargetime.ocpp.JSONServer;
import eu.chargetime.ocpp.feature.profile.ServerCoreProfile;

@Component
@Configuration
public class OCPPServerConfig {
	@Bean
	public JSONServer jsonServer(ServerCoreProfile core) throws GeneralSecurityException, IOException {
		JSONServer jsonServer = new JSONServer(core);
		return jsonServer;
	}
}


