package se.iamo.buslineanalyzer.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class JourneyPatternOnLineResponseData {

    @JsonProperty("ExecutionTime")
    private int executionTime;
    @JsonProperty("StatusCode")
    private int statusCode;
    @JsonProperty("Message")
    private String message;
    @JsonProperty("ResponseData")
    private JourneyPatternOnLineResponse responseData;


    @Data
    public class JourneyPatternOnLineResponse {
        @JsonProperty("Type")
        private String type;
        @JsonProperty("Version")
        private String version;
        @JsonProperty("Result")
        private List<JourneyPatternPointOnLine> result;
    }
}

