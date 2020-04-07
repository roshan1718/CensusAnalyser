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
        this.checkValidCvsFile(csvFilePath);
        try ( Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));){
            Iterator<IndiaCensusCSV> censusCSVIterator = this.getCSVFileIterator(reader, IndiaCensusCSV.class);
            return this.getCount(censusCSVIterator);
        } catch (IOException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
             }
    }


    public int loadIndiaStateCode(String csvFilePath) throws CensusAnalyserException {
        this.checkValidCvsFile(csvFilePath);
        try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));) {
            Iterator<IndiaStateCode> stateCSVIterator = this.getCSVFileIterator(reader, IndiaStateCode.class);
            Iterable<IndiaStateCode> csvIterable = () -> stateCSVIterator;
            return getCount(stateCSVIterator);
        } catch (IOException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        }
    }
    private <E> int getCount(Iterator<E>iterator){
        Iterable<E> csvIterable = () -> iterator;
        int numberOfEnteries = (int)StreamSupport.stream(csvIterable.spliterator(),false).count();
        return numberOfEnteries;
    }


    public void checkValidCvsFile(String csvFilePath) throws CensusAnalyserException{
        if( !csvFilePath.contains(".csv")){
            throw new CensusAnalyserException("Invalid file type",
                    CensusAnalyserException.ExceptionType.INVALID_FILE_TYPE);
        }
    }

    public <E>Iterator<E> getCSVFileIterator(Reader reader,Class csvClass) throws CensusAnalyserException  {
        try {
            CsvToBeanBuilder<E> csvToBeanBuilder = new CsvToBeanBuilder<>(reader);
            csvToBeanBuilder.withType(csvClass);
            csvToBeanBuilder.withIgnoreLeadingWhiteSpace(true);
            CsvToBean<E> csvToBean = csvToBeanBuilder.build();
            return csvToBean.iterator();
        }catch (IllegalStateException e){
            throw new CensusAnalyserException(e.getMessage(),CensusAnalyserException.ExceptionType.INVALID_FILE_TYPE);
        }catch (RuntimeException e){
            throw new CensusAnalyserException("Invalid file type",CensusAnalyserException.ExceptionType.INVALID_FILE_HEADER);
        }
    }

}
