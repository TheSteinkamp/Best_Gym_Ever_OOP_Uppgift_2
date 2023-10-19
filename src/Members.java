import java.time.LocalDate;

public class Members {
    // Members klass med all info om medlemmarna
    private long ssn;
    private String name;
    LocalDate lastPayment;

    public Members(long ssn, String name, LocalDate lastPayment) {
        this.ssn = ssn;
        this.name = name;
        this.lastPayment = lastPayment;
    }
    public Members() {
    }

    public LocalDate getLastPayment() {
        return lastPayment;
    }
    public void setLastPayment(LocalDate lastPayment) {
        this.lastPayment = lastPayment;
    }
    public long getSsn() {
        return ssn;
    }

    public void setSsn(long ssn) {
        this.ssn = ssn;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
