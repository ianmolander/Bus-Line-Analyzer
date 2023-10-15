package se.iamo.buslineanalyzer.service;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import se.iamo.buslineanalyzer.integration.TrafiklabCaller;
import se.iamo.buslineanalyzer.model.JourneyPatternPointOnLine;
import se.iamo.buslineanalyzer.model.StopPoint;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TopLineServiceTest {

    private static final String DIRECTION_NUMBER = "1";

    @Test
    public void testGetStopPointsForBusLine() {
        String busLine = "BusLine1";

        JourneyPatternPointOnLine journeyPattern1 = Mockito.mock(JourneyPatternPointOnLine.class);
        Mockito.when(journeyPattern1.getLineNumber()).thenReturn(busLine);
        Mockito.when(journeyPattern1.getDirectionCode()).thenReturn(DIRECTION_NUMBER);
        Mockito.when(journeyPattern1.getJourneyPatternPointNumber()).thenReturn("11111");

        JourneyPatternPointOnLine journeyPattern2 = Mockito.mock(JourneyPatternPointOnLine.class);
        Mockito.when(journeyPattern2.getLineNumber()).thenReturn(busLine);
        Mockito.when(journeyPattern2.getDirectionCode()).thenReturn(DIRECTION_NUMBER);
        Mockito.when(journeyPattern2.getJourneyPatternPointNumber()).thenReturn("22222");

        JourneyPatternPointOnLine journeyPattern3 = Mockito.mock(JourneyPatternPointOnLine.class);
        Mockito.when(journeyPattern3.getLineNumber()).thenReturn("OtherLine");
        Mockito.when(journeyPattern3.getDirectionCode()).thenReturn(DIRECTION_NUMBER);
        Mockito.when(journeyPattern3.getJourneyPatternPointNumber()).thenReturn("333");

        List<JourneyPatternPointOnLine> allJourneyPatterns = Arrays.asList(journeyPattern1, journeyPattern2, journeyPattern3);


        StopPoint stopPoint1 = Mockito.mock(StopPoint.class);
        Mockito.when(stopPoint1.getStopPointNumber()).thenReturn("11111");
        Mockito.when(stopPoint1.getStopPointName()).thenReturn("Odenplan");
        Mockito.when(stopPoint1.getStopAreaNumber()).thenReturn("101");

        StopPoint stopPoint2 = Mockito.mock(StopPoint.class);
        Mockito.when(stopPoint2.getStopPointNumber()).thenReturn("22222");
        Mockito.when(stopPoint2.getStopPointName()).thenReturn("Fridhemsplan");
        Mockito.when(stopPoint2.getStopAreaNumber()).thenReturn("102");

        StopPoint stopPoint3 = Mockito.mock(StopPoint.class);
        Mockito.when(stopPoint3.getStopPointNumber()).thenReturn("333");
        Mockito.when(stopPoint3.getStopPointName()).thenReturn("Rådmansgatan");
        Mockito.when(stopPoint3.getStopAreaNumber()).thenReturn("103");

        List<StopPoint> allStopPoints = Arrays.asList(stopPoint1, stopPoint2, stopPoint3);

        TopLineService topLineService = new TopLineService(Mockito.mock(TrafiklabCaller.class));


        List<StopPoint> result = topLineService.getStopPointsForBusLine(busLine, allJourneyPatterns, allStopPoints);


        assertEquals(2, result.size());
        assertTrue(result.contains(stopPoint1));
        assertTrue(result.contains(stopPoint2));
        assertFalse(result.contains(stopPoint3));
    }
}