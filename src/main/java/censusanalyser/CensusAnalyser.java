package censusanalyser;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import sun.tools.jconsole.InternalDialog;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.stream.StreamSupport;

public class CensusAnalyser {
    public int loadIndiaCensusData(String csvFilePath) throws CensusAnalyserException {

        try {
            Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));
            Iterator<IndiaCensusCSV> censusCSVIterator=this.getIterator(reader,IndiaCensusCSV.class);
            int numOfEnteries = 0;

            Iterable<IndiaCensusCSV>indiaCensusCSVIterable= ()->censusCSVIterator;
            numOfEnteries= (int ) StreamSupport.stream(indiaCensusCSVIterable.spliterator(),false).count();
            return numOfEnteries;
        } catch (IOException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        } catch (RuntimeException e) {
            if(e.getMessage().contains("header!"))
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.INVALID_FILE_HEADER);

            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.INVALID_FILE_DATA);
        }

    }

    public int loadIndiaStateCode(String csvFilePath) throws CensusAnalyserException {

        try {
            Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));
           Iterator<IndiaStateCode>stateCodeCSVIterator=this.getIterator(reader,IndiaStateCode.class);
            int numOfEnteries = 0;

            Iterable<IndiaStateCode>indiaCensusCSVIterable= ()->stateCodeCSVIterator;
            numOfEnteries= (int ) StreamSupport.stream(indiaCensusCSVIterable.spliterator(),false).count();
            return numOfEnteries;
        } catch (IOException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        } catch (RuntimeException e) {
            if(e.getMessage().contains("header!"))
                throw new CensusAnalyserException(e.getMessage(),
                        CensusAnalyserException.ExceptionType.INVALID_FILE_HEADER);

            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.INVALID_FILE_DATA);
        }

    }

    public void checkValidCvsFile(String csvFilePath) throws CensusAnalyserException{
        if( !csvFilePath.contains(".csv")){
            throw new CensusAnalyserException("Invalid file type",
                    CensusAnalyserException.ExceptionType.INVALID_FILE_TYPE);
        }
    }

    public <E> Iterator getIterator(Reader reader, Class classFile ){
        CsvToBeanBuilder<E> csvToBeanBuilder = new CsvToBeanBuilder<>(reader);
        csvToBeanBuilder.withType(classFile);
        csvToBeanBuilder.withIgnoreLeadingWhiteSpace(true);
        CsvToBean<E> csvToBean = csvToBeanBuilder.build();
        Iterator<E> stateCodeCSVIterator = csvToBean.iterator();
       return stateCodeCSVIterator;
    }
}
