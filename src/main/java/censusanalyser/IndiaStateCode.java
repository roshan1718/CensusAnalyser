package censusanalyser;

import com.opencsv.bean.CsvBindByName;

public class IndiaStateCode {

    @CsvBindByName(column = "SrNo",required = true)
    public String srNo;

    @CsvBindByName(column = "State",required = true)
    public String state;

    @CsvBindByName(column = "Name",required = false)
    public String name;

    @CsvBindByName(column = "TIN",required = true)
    public String tin;

    @CsvBindByName(column = "StateCode",required = true)
    public String stateCode;

    @Override
    public String toString() {
        return "com.bridgelabz.censusanalyserproject.IndianStateCode {"+
                "SrNo='" + srNo + '\'' +
                " ,State='" + state + '\'' +
                " ,Name='" + name + '\'' +
                " ,TIN='" + tin + '\'' +
                " ,StateCode='" + stateCode + '\'' +
                '}';
    }
}
