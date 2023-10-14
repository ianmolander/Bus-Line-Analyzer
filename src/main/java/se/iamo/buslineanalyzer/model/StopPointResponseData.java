package se.iamo.buslineanalyzer.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class StopPointResponseData {

    @JsonProperty("ExecutionTime")
    private int executionTime;
    @JsonProperty("StatusCode")
    private int statusCode;
    @JsonProperty("Message")
    private String message;
    @JsonProperty("ResponseData")
    private StopPointResponse responseData;

    @Data
    public class StopPointResponse {
        @JsonProperty("Type")
        private String type;
        @JsonProperty("Version")
        private String version;
        @JsonProperty("Result")
        private List<StopPoint> result;
    }
}
