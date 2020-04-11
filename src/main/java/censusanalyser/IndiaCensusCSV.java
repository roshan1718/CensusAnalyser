package censusanalyser;

import com.opencsv.bean.CsvBindByName;

public class IndiaCensusCSV {


    @CsvBindByName(column = "State", required = true)
    public static String StateName;

    @CsvBindByName(column = "Population", required = true)
    public long population;

    @CsvBindByName(column = "AreaInSqKm", required = true)
    public long areaInSqKm;

    @CsvBindByName(column = "DensityPerSqKm", required = true)
    public long densityPerSqKm;


    public IndiaCensusCSV(String state, long population, long area, long density ){
        StateName=state;
        population=population;
        areaInSqKm=area;
        densityPerSqKm= density;
    }

    @Override
    public String toString() {
        return "IndiaCensusCSV{" +
                "State='" + StateName + '\'' +
                ", Population='" + population + '\'' +
                ", AreaInSqKm='" + areaInSqKm + '\'' +
                ", DensityPerSqKm='" + densityPerSqKm + '\'' +
                '}';
    }
}