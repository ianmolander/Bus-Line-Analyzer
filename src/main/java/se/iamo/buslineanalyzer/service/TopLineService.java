package se.iamo.buslineanalyzer.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.iamo.buslineanalyzer.model.JourneyPatternPointOnLine;
import se.iamo.buslineanalyzer.model.StopPoint;
import se.iamo.buslineanalyzer.trafiklab.TrafiklabCaller;


import java.util.List;


@Service
@Slf4j
public final class TopLineService {


    private final TrafiklabCaller caller;

    @Autowired
    private TopLineService(TrafiklabCaller caller) {
        this.caller = caller;
    }


    private void findBusLinesWithMostStops() {
        List<StopPoint> allStopPoints = caller.getAllStopPoints();
        List<JourneyPatternPointOnLine> allJourneyPatterns = caller.getAllJourneyPatternsOnLines();
    }

}