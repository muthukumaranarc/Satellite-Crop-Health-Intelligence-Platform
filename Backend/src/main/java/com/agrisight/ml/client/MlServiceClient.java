package com.agrisight.ml.client;

import com.agrisight.ml.dto.MlProcessingRequest;
import com.agrisight.ml.dto.MlProcessingResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.Optional;

@Component
public class MlServiceClient {

    private static final Logger log = LoggerFactory.getLogger(MlServiceClient.class);

    private final WebClient webClient;
    private final Duration readTimeout;

    public MlServiceClient(
            WebClient.Builder webClientBuilder,
            @Value("${agrisight.ml-service.url:http://localhost:8000}") String mlServiceUrl,
            @Value("${agrisight.ml-service.read-timeout-ms:10000}") long readTimeoutMs) {
        this.webClient = webClientBuilder.baseUrl(mlServiceUrl).build();
        this.readTimeout = Duration.ofMillis(readTimeoutMs);
    }

    /**
     * Sends satellite processing request to external Python ML / remote-sensing service.
     * Returns Optional.empty() if service is unavailable, triggering local rule-based fallback.
     */
    public Optional<MlProcessingResponse> processField(MlProcessingRequest request) {
        try {
            MlProcessingResponse response = webClient.post()
                    .uri("/api/v1/ml/process-field")
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(request)
                    .retrieve()
                    .bodyToMono(MlProcessingResponse.class)
                    .timeout(readTimeout)
                    .onErrorResume(ex -> {
                        log.warn("External ML service unavailable or returned error ({}). Falling back to internal engine.",
                                ex.getMessage());
                        return Mono.empty();
                    })
                    .block();

            return Optional.ofNullable(response);
        } catch (Exception ex) {
            log.warn("Failed to reach Python ML service: {}. Local engine will process observation data.", ex.getMessage());
            return Optional.empty();
        }
    }
}
