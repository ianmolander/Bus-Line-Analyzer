package se.iamo.buslineanalyzer.trafiklab;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import se.iamo.buslineanalyzer.model.JourneyPatternPointOnLine;
import se.iamo.buslineanalyzer.model.StopPoint;

import java.util.Collections;
import java.util.List;

@Service
public class TrafiklabCaller {

    @Value("${api.url}")
    private String apiUrl;

    @Value("${api.key}")
    private String apiKey;

    private final RestTemplate restTemplate;

    public TrafiklabCaller(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<StopPoint> getAllStopPoints() {
        return Collections.emptyList();
    }

    public List<JourneyPatternPointOnLine> getAllJourneyPatternsOnLines() {

        return Collections.emptyList();

    }

    private UriComponentsBuilder createUriBuilder(String model) {
        return UriComponentsBuilder.fromHttpUrl(apiUrl)
                .queryParam("key", apiKey)
                .queryParam("model", model)
                .queryParam("DefaultTransportModeCode", "BUS");
    }
}
