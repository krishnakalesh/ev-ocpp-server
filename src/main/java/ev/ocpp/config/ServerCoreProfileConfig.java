package ev.ocpp.config;

import java.time.ZonedDateTime;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import eu.chargetime.ocpp.feature.profile.ServerCoreEventHandler;
import eu.chargetime.ocpp.feature.profile.ServerCoreProfile;
import eu.chargetime.ocpp.model.core.AuthorizationStatus;
import eu.chargetime.ocpp.model.core.AuthorizeConfirmation;
import eu.chargetime.ocpp.model.core.AuthorizeRequest;
import eu.chargetime.ocpp.model.core.BootNotificationConfirmation;
import eu.chargetime.ocpp.model.core.BootNotificationRequest;
import eu.chargetime.ocpp.model.core.DataTransferConfirmation;
import eu.chargetime.ocpp.model.core.DataTransferRequest;
import eu.chargetime.ocpp.model.core.DataTransferStatus;
import eu.chargetime.ocpp.model.core.HeartbeatConfirmation;
import eu.chargetime.ocpp.model.core.HeartbeatRequest;
import eu.chargetime.ocpp.model.core.IdTagInfo;
import eu.chargetime.ocpp.model.core.MeterValuesConfirmation;
import eu.chargetime.ocpp.model.core.MeterValuesRequest;
import eu.chargetime.ocpp.model.core.RegistrationStatus;
import eu.chargetime.ocpp.model.core.StartTransactionConfirmation;
import eu.chargetime.ocpp.model.core.StartTransactionRequest;
import eu.chargetime.ocpp.model.core.StatusNotificationConfirmation;
import eu.chargetime.ocpp.model.core.StatusNotificationRequest;
import eu.chargetime.ocpp.model.core.StopTransactionConfirmation;
import eu.chargetime.ocpp.model.core.StopTransactionRequest;
import lombok.Getter;

@Configuration
@Getter
public class ServerCoreProfileConfig {
	private static final Logger log = LoggerFactory.getLogger(ServerCoreProfileConfig.class);	
	@Autowired
	OCPPEventConfig serverEventConfig;
	
	@Bean
	public ServerCoreProfile createCore(ServerCoreEventHandler serverCoreEventHandler) {
		return new ServerCoreProfile(serverCoreEventHandler);
	}

	@Bean
	public ServerCoreEventHandler getCoreEventHandler() {
		return new ServerCoreEventHandler() {
			@Override
			public AuthorizeConfirmation handleAuthorizeRequest(UUID sessionIndex, AuthorizeRequest request) {
				log.info("Session {}: Authorize Request Received from: {}  Request: {}",sessionIndex, serverEventConfig.getChargePointSessionKey(sessionIndex), request);				
				return new AuthorizeConfirmation(new IdTagInfo(AuthorizationStatus.Accepted));
			}

			@Override
			public BootNotificationConfirmation handleBootNotificationRequest(UUID sessionIndex,
					BootNotificationRequest request) {
				log.info("Session {}: Request Received from:{}  Request: {}",sessionIndex, serverEventConfig.getChargePointSessionKey(sessionIndex), request);
				return new BootNotificationConfirmation(ZonedDateTime.now(),
						1000, RegistrationStatus.Accepted);
			}

			@Override
			public DataTransferConfirmation handleDataTransferRequest(UUID sessionIndex, DataTransferRequest request) {
				log.info("Session {}: during data transfer", sessionIndex);
				return new DataTransferConfirmation(
						DataTransferStatus.Accepted);
			}

			@Override
			public HeartbeatConfirmation handleHeartbeatRequest(UUID sessionIndex, HeartbeatRequest request) {
				log.info("Session {}: during heart beat", sessionIndex);
				return new HeartbeatConfirmation(ZonedDateTime.now());
			}

			@Override
			public MeterValuesConfirmation handleMeterValuesRequest(UUID sessionIndex, MeterValuesRequest request) {
				log.info("Session {}: during meter values", sessionIndex);
				return new MeterValuesConfirmation();
			}

			@Override
			public StartTransactionConfirmation handleStartTransactionRequest(UUID sessionIndex,
					StartTransactionRequest request) {
				log.info("Session {}: during Start transaction.", sessionIndex);
				return new StartTransactionConfirmation(
						new IdTagInfo(AuthorizationStatus.Accepted), null);
			}

			@Override
			public StatusNotificationConfirmation handleStatusNotificationRequest(UUID sessionIndex,
					StatusNotificationRequest request) {
				return new StatusNotificationConfirmation();
			}

			@Override
			public StopTransactionConfirmation handleStopTransactionRequest(UUID sessionIndex,
					StopTransactionRequest request) {
				log.info("Session {}: during Stop transaction", sessionIndex);
				return new StopTransactionConfirmation();
			}

		};
	}


}