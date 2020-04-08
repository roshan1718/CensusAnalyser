package censusanalyser;

import com.bl.csvbuilder.CsvBuilder;
import com.bl.csvbuilder.CsvFileBuilderException;
import com.bl.csvbuilder.IcsvBuilder;
import com.google.gson.Gson;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import sun.tools.jconsole.InternalDialog;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.stream.StreamSupport;

public class CensusAnalyser {
    List<IndiaCensusCSV> csvFileList = null;

    public int loadIndiaCensusData(String csvFilePath) throws CsvFileBuilderException {
        this.checkValidCvsFile(csvFilePath);
        try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath))) {
            CsvBuilder csvBuilder = ( CsvBuilder) CSVBuilderFactory.createCSVBuilder();
            csvFileList = csvBuilder.getCSVFileList(reader, IndiaCensusCSV.class);
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

    public String getStateWiseSortedCensusData(String csvFilePath) throws CsvFileBuilderException {
        loadIndiaCensusData(csvFilePath);
        if (csvFileList == null || csvFileList.size() == 0) {
            throw new CsvFileBuilderException("NO_CENSUS_DATA", CsvFileBuilderException.ExceptionType.NO_CENSUS_DATA);
        }

        Comparator<IndiaCensusCSV> censusComparator = Comparator.comparing(census -> census.state);
        this.sort(csvFileList, censusComparator);
        String toJson = new Gson().toJson(csvFileList);
        return toJson;
    }

    private void sort(List<IndiaCensusCSV> csvFileList, Comparator<IndiaCensusCSV> censusComparator) {
        for (int i = 0; i < csvFileList.size(); i++) {
            for (int j = 0; j < csvFileList.size() - i - 1; j++) {
                IndiaCensusCSV census1 = csvFileList.get(j);
                IndiaCensusCSV census2 = csvFileList.get(j + 1);
                if (censusComparator.compare(census1, census2) > 0) {
                    csvFileList.set(j, census2);
                    csvFileList.set(j + 1, census1);
                }

            }

        }
    }

}


