package censusanalyser;

import com.bl.csvbuilder.CsvBuilder;
import com.bl.csvbuilder.CsvFileBuilderException;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.stream.StreamSupport;

import static java.nio.file.Files.newBufferedReader;

public abstract class CensusAdapter {


    public abstract Map<String, CensusDAO> loadCensusData(String... csvFilePath) throws CensusAnalyserException ;

    public <E> Map<String, CensusDAO> loadCensusData(Class<E> censusCSVClass, String csvFilePath)
            throws CensusAnalyserException {
        Map<String, CensusDAO> censusDAOMap = new HashMap<>();
        try (Reader reader = newBufferedReader(Paths.get(csvFilePath));)
        {
            CsvBuilder csvBuilder = (CsvBuilder) CSVBuilderFactory.createCSVBuilder();
            Iterator<E> stateCensusIterator = csvBuilder.getCSVFileIterator(reader, censusCSVClass);
            Iterable<E> stateCensuses = () -> stateCensusIterator;
            if (censusCSVClass.getName().contains("IndiaCensusCSV"))
            {
                StreamSupport.stream(stateCensuses.spliterator(), false)
                        .map(IndiaCensusCSV.class::cast)
                        .forEach(censusCSV -> censusDAOMap.put(censusCSV.StateName, new CensusDAO(censusCSV)));
            } else if (censusCSVClass.getName().contains("USCensusCSV"))
            {
                StreamSupport.stream(stateCensuses.spliterator(), false)
                        .map(USCensusCSV.class::cast)
                        .forEach(censusCSV -> censusDAOMap.put(censusCSV.StateName, new CensusDAO(censusCSV)));
            }
            return censusDAOMap;
        } catch (RuntimeException e) {
            throw new CensusAnalyserException("Check Delimiters Or Headers",
                    CensusAnalyserException.ExceptionType.WRONG_FILE_DELIMITER_AND_HEADER);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (CsvFileBuilderException e) {
            e.printStackTrace();
        }
        return censusDAOMap;
    }
}