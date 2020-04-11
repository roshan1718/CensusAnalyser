package censusanalyser;
 
import com.bl.csvbuilder.CsvBuilder;
import com.bl.csvbuilder.CsvFileBuilderException;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;

import java.util.Iterator;
import java.util.Map;
import java.util.stream.StreamSupport;

public class IndianCensusAdapter<StateDataCSV> extends CensusAdapter {
    @Override
    public Map<String, CensusDAO> loadCensusData(String... csvFilePath) throws  CensusAnalyserException {
        Map<String, CensusDAO> censusDAOMap = super.loadCensusData(IndiaCensusCSV.class, csvFilePath[0]);
        if (csvFilePath.length == 1)
            return censusDAOMap;
        return loadStateCodeCSVData(censusDAOMap, csvFilePath[1]);
    }

    private Map<String, CensusDAO> loadStateCodeCSVData(Map<String, CensusDAO> censusDAOMap, String csvFilePath) 
            throws CensusAnalyserException
    {
        String extension = csvFilePath.substring(csvFilePath.lastIndexOf(".") + 1);
        if (!extension.equals("csv"))
        {
            throw new CensusAnalyserException("Given File Not Found ",CensusAnalyserException.ExceptionType.INVALID_FILE_TYPE);
        }
        try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath)))
        {
            CsvBuilder csvBuilder = (CsvBuilder) CSVBuilderFactory.createCSVBuilder();
            Iterator<IndiaStateCode> stateCodeIterator = csvBuilder.getCSVFileIterator(reader, IndiaStateCode .class);
            Iterable<IndiaStateCode> stateCodes = () -> stateCodeIterator;
            StreamSupport.stream(stateCodes.spliterator(), false)
                    .filter(StateDataCSV -> censusDAOMap.get(StateDataCSV.statename) != null)
                    .forEach(StateDataCSV -> censusDAOMap.get(StateDataCSV.statename).StateCode = StateDataCSV.stateCode);
        } catch (NoSuchFileException e) {
            throw new CensusAnalyserException("Given File Not Found ", 
                    CensusAnalyserException. ExceptionType. INVALID_FILE_TYPE);
        } catch (RuntimeException e) {
            throw new CensusAnalyserException("Check Delimiters Or Headers",
                    CensusAnalyserException. ExceptionType.WRONG_FILE_DELIMITER_AND_HEADER);
        } catch (IOException e) {
            e.getStackTrace();
        } catch (CsvFileBuilderException e) {
            e.printStackTrace();
        }
        return censusDAOMap;
    }
} 