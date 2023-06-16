package dagoras.io.mappingcsvfile;

import dagoras.io.mappingcsvfile.dto.Contact;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/data-mapping")
@RequiredArgsConstructor
public class DataMappingController {

    private final DataMappingService dataMappingService;

    @GetMapping
    public List<Contact> mapData() {
        String contactsFilePath = "D:\\DagorasCompany\\Varnish_Cache\\mapping-csv-file\\data.csv";
        String employeesFilePath = "D:\\DagorasCompany\\Varnish_Cache\\mapping-csv-file\\large_data.csv";
        return dataMappingService.mapData(contactsFilePath, employeesFilePath);
    }
}

