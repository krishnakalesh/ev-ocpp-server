package ev.ocpp.config;

import java.util.UUID;

import org.apache.commons.collections4.BidiMap;
import org.apache.commons.collections4.bidimap.DualHashBidiMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import eu.chargetime.ocpp.AuthenticationException;
import eu.chargetime.ocpp.ServerEvents;
import eu.chargetime.ocpp.model.SessionInformation;

@Configuration
public class OCPPEventConfig {
	private static final Logger logger = LoggerFactory.getLogger(OCPPEventConfig.class);
	public static final BidiMap<String, UUID> sessionCache = new DualHashBidiMap<>();
	
	@Bean
	public ServerEvents createServerCoreImpl() {
		return getNewServerEventsImpl();
	}

	private ServerEvents getNewServerEventsImpl() {		  
		return new ServerEvents() {				
			public void authenticateSession(SessionInformation information, String username, String password)
					throws AuthenticationException {				
				logger.info("Authenticating user: {} identifier: {} address {}", username, 
						information.getIdentifier(), information.getAddress());			
			}

			public void newSession(UUID sessionIndex, SessionInformation information) {
				logger.info("Session {}: New Session {}", sessionIndex, information.getIdentifier());	
				sessionCache.put(information.getIdentifier().substring(1), sessionIndex);
			}

			@Override
			public void lostSession(UUID sessionIndex) {
				logger.info("Lost Session {}.", sessionIndex);
				sessionCache.removeValue(sessionIndex);
			}
		};
	}
	
	public UUID getChargePointSessionUUID(String key) {
		return  sessionCache.get(key);
	}
	
	public String getChargePointSessionKey(UUID id) {
		return  sessionCache.getKey(id);
	}
}
