package se.iamo.buslineanalyzer.service;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.iamo.buslineanalyzer.exception.DataNotFoundException;
import se.iamo.buslineanalyzer.model.JourneyPatternPointOnLine;
import se.iamo.buslineanalyzer.model.StopPoint;
import se.iamo.buslineanalyzer.trafiklab.TrafiklabCaller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
public final class TopLineService {

    public static final String DIRECTION_NUMBER = "1"; // Line direction, 2 = the other way
    private final TrafiklabCaller caller;

    @Autowired
    private TopLineService(TrafiklabCaller caller) {
        this.caller = caller;
    }


    @PostConstruct
    private void findBusLinesWithMostStops() {
        List<StopPoint> allStopPoints = caller.getAllStopPoints();
        List<JourneyPatternPointOnLine> allJourneyPatternsOnLines = caller.getAllJourneyPatternsOnLines();
        if (allStopPoints.isEmpty() || allJourneyPatternsOnLines.isEmpty()) {
            log.error("No stop points or journey patterns data found");
            throw new DataNotFoundException("No stop points or journey patterns data found");
        } else {
            Map<String, Integer> lineStopCounts = getBusLinesWithCountedStopPoints(allJourneyPatternsOnLines);
            List<Map.Entry<String, Integer>> sortedLines = printStopPointsForTopBusLine(lineStopCounts, allJourneyPatternsOnLines, allStopPoints);
            logTop10BusLines(sortedLines);
        }
    }

    private Map<String, Integer> getBusLinesWithCountedStopPoints(List<JourneyPatternPointOnLine> journeyPatternPointOnLines) {
        Map<String, Integer> lineStopCounts = new HashMap<>();

        for (JourneyPatternPointOnLine journeyPattern : journeyPatternPointOnLines) {
            String lineNumber = journeyPattern.getLineNumber();
            lineStopCounts.put(lineNumber, lineStopCounts.getOrDefault(lineNumber, 0) + 1);
        }

        return lineStopCounts;
    }

    private List<Map.Entry<String, Integer>> printStopPointsForTopBusLine(Map<String, Integer> lineStopCounts, List<JourneyPatternPointOnLine> allJourneyPatterns, List<StopPoint> allStopPoints) {
        List<Map.Entry<String, Integer>> sortedLines = lineStopCounts.entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .toList();

        if (!sortedLines.isEmpty()) {
            String topLine = sortedLines.get(0).getKey();
            if (topLine != null) {
                List<StopPoint> stopPointsForTopLine = getStopPointsForBusLine(topLine, allJourneyPatterns, allStopPoints);
                log.info("{} stops for bus: {} - stops listed below", stopPointsForTopLine.size(), topLine);
                stopPointsForTopLine.forEach(stopPoint -> log.info(stopPoint.getStopPointName(), stopPoint.getStopPointNumber(), stopPoint.getStopAreaNumber()));
            }
        }

        return sortedLines;
    }

    private List<StopPoint> getStopPointsForBusLine(String busLine, List<JourneyPatternPointOnLine> allJourneyPatterns, List<StopPoint> allStopPoints) {
        return allJourneyPatterns.stream()
                .filter(journeyPattern -> busLine.equals(journeyPattern.getLineNumber()) && journeyPattern.getDirectionCode().equals(DIRECTION_NUMBER))
                .map(JourneyPatternPointOnLine::getJourneyPatternPointNumber)
                .flatMap(journeyPatternStopPointNumber -> allStopPoints.stream()
                        .filter(stopPoint -> journeyPatternStopPointNumber.equals(stopPoint.getStopPointNumber())))
                .collect(Collectors.toList());
    }

    private void logTop10BusLines(List<Map.Entry<String, Integer>> sortedLines) {
        List<String> top10Lines = sortedLines.stream()
                .limit(10)
                .map(Map.Entry::getKey)
                .toList();

        log.info("Top 10 Bus Lines with Most Stops:");
        top10Lines.forEach(log::info);
    }
}