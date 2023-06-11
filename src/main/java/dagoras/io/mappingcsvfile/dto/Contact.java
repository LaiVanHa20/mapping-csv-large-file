package dagoras.io.mappingcsvfile.dto;

import lombok.Data;

@Data
public class Contact {
    private String phoneNumber;
    private String fullName;
    private String address;
    private String email;

    public void setTaxCode(String taxCode) {
    }

    public void setSocialInsuranceCode(String socialInsuranceCode) {
    }
}
