package censusanalyser;

import com.bl.csvbuilder.CsvBuilder;
import com.bl.csvbuilder.CsvFileBuilderException;
import com.bl.csvbuilder.IcsvBuilder;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import sun.tools.jconsole.InternalDialog;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.List;
import java.util.stream.StreamSupport;

public class CensusAnalyser {
    public int loadIndiaCensusData(String csvFilePath) throws CsvFileBuilderException {
        this.checkValidCvsFile(csvFilePath);
        try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath))) {
            CsvBuilder csvBuilder = ( CsvBuilder) CSVBuilderFactory.createCSVBuilder();
            List<IndiaCensusCSV> csvFileList = csvBuilder.getCSVFileList(reader, IndiaCensusCSV.class);
            return csvFileList.size();
        } catch (IOException e) {
            throw new CsvFileBuilderException(e.getMessage(),
                    CsvFileBuilderException.ExceptionType.CENSUS_FILE_PROBLEM);
        } catch (CsvFileBuilderException e) {
            throw new CsvFileBuilderException(e.getMessage(),e.type.name());
        }
    }

    public int loadStateCode(String csvFilePath) throws CsvFileBuilderException {
        this.checkValidCvsFile(csvFilePath);

        try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath))) {
            IcsvBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
            List<IndiaStateCode> csvFileList = csvBuilder.getCSVFileList(reader, IndiaStateCode.class);
            return csvFileList.size();
        } catch (IOException e) {
            throw new CsvFileBuilderException(e.getMessage(),
                    CsvFileBuilderException.ExceptionType.CENSUS_FILE_PROBLEM);
        } catch (CsvFileBuilderException e) {
            throw new CsvFileBuilderException(e.getMessage(),e.type.name());
        }
    }

    public void checkValidCvsFile(String csvFilePath) throws CsvFileBuilderException{
        if( !csvFilePath.contains(".csv")){
            throw new CsvFileBuilderException("Invalid file type",
                    CsvFileBuilderException.ExceptionType.INVALID_FILE_TYPE);
        }
    }

    private <E> int getCount(Iterator<E> iterator) {
        Iterable<E> iterable = () -> iterator;
        int namOfEateries = (int) StreamSupport.stream(iterable.spliterator(), false).count();
        return namOfEateries;
    }

}


