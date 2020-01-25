package playground.dto;


import java.time.LocalDate;
import java.util.Objects;


public class ModelYear {
    private final int year;

    public ModelYear(int year) {
            validate(year);
            this.year = year;
    }

    public int getYear() {
        return year;
    }

    private void validate(int value) {
        // Invariant validations
        if (value < 1884) {
            throw new DomainPrimitiveValidationException(null);
        }
        if (value > LocalDate.now().getYear() + 1) {
            //throw new BusinessFault(MODEL_YEAR_TOO_FAR_IN_THE_FUTURE, systemInfo, applicationData.getApplicationId(), "P&I");
            throw new DomainPrimitiveValidationException(null);
        }
    }

    @Override
    public String toString() {
        return Integer.toString(year);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null) {
            return false;
        }
        if (o.getClass() != getClass()) {
            return false;
        }
        return Objects.equals(year, ((ModelYear)o).getYear());
    }
    @Override
    public int hashCode() {
        return Objects.hashCode(year);
    }

}
