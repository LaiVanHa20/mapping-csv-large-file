package dagoras.io.mappingcsvfile.dto;

import lombok.Data;

@Data
public class Contact {
    private String phoneNumber;
    private String fullName;
    private String address;
    private String email;
    private String taxCode;
    private String socialInsuranceCode;
}
