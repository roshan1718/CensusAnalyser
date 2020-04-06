package censusanalyser;

public class CensusAnalyserException extends Exception {

    public CensusAnalyserException(String invalid_file) {
    }

    enum ExceptionType {
        CENSUS_FILE_PROBLEM,INVALID_FILE_TYPE ,WRONG_CSV_FILE_TYPE ,CENSUS_FILE_DATA ;
    }

    ExceptionType type;

    public CensusAnalyserException(String message, ExceptionType type) {
        super(message);
        this.type = type;
    }

    public CensusAnalyserException(String message, ExceptionType type, Throwable cause) {
        super(message, cause);
        this.type = type;
    }
}
