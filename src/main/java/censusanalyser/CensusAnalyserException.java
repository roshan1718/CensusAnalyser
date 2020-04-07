package censusanalyser;

public class CensusAnalyserException extends Exception {

    public CensusAnalyserException(String invalid_file) {
    }

    enum ExceptionType {
        CENSUS_FILE_PROBLEM,INVALID_FILE_TYPE ,
        WRONG_FILE_DELIMITER,WRONG_FILE_HEADER,UNABLE_TO_PARSE ;
    }

    ExceptionType type;

    public CensusAnalyserException(String message, ExceptionType type) {
        super(message);
        this.type = type;
    }

}
