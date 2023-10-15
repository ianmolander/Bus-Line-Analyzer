# Challenge
The task is to write an application to find out which bus lines have the most bus stops on their route, 
and list the top 10 as clear text. Application should also list the names of every bus stop of the bus 
line that has the most stops. There is no requirement on how the bus stops are sorted.

## Prerequisites
To run Bus Line Analyzer, you'll need the following:

- Java (version 17 or higher).
- Maven for dependency management and the build process.

## Getting Started
Follow these steps to get the Bus Line Analyzer up and running:

1. Clone the project repository and run:

```bash
 cd bus-line-analyzer
 mvn clean package
 java -jar target/buslineanalyzer-1.0.jar