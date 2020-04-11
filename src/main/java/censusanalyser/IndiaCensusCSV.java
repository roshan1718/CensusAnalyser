package censusanalyser;

import com.opencsv.bean.CsvBindByName;

public class IndiaCensusCSV {


    @CsvBindByName(column = "State", required = true)
    public static String state;

    @CsvBindByName(column = "Population", required = true)
    public String population;

    @CsvBindByName(column = "AreaInSqKm", required = true)
    public String areaInSqKm;

    @CsvBindByName(column = "DensityPerSqKm", required = true)
    public String densityPerSqKm;


    public IndiaCensusCSV(String state, String population, String area, String density ){
        state=state;
        population=population;
        areaInSqKm=area;
        densityPerSqKm= density;
    }

    @Override
    public String toString() {
        return "IndiaCensusCSV{" +
                "State='" + state + '\'' +
                ", Population='" + population + '\'' +
                ", AreaInSqKm='" + areaInSqKm + '\'' +
                ", DensityPerSqKm='" + densityPerSqKm + '\'' +
                '}';
    }
}
