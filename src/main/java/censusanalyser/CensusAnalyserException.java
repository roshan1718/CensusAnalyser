package censusanalyser;
public class CensusAnalyserException extends Exception {

    public CensusAnalyserException(String message, String name) {
        super(message);
        this.type = ExceptionType.valueOf(name);
    }


    enum ExceptionType {
        CENSUS_FILE_PROBLEM,INVALID_FILE_TYPE ,WRONG_FILE_DELIMITER_AND_HEADER,
        UNABLE_TO_PARSE ,NO_CENSUS_DATA,INVALID_COUNTRY_EXCEPTION;
    }

    ExceptionType type;

    public CensusAnalyserException(String message, ExceptionType type) {
        super(message);
        this.type = type;
    }

}
