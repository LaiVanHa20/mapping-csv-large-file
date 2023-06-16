package dagoras.io.mappingcsvfile;

import dagoras.io.mappingcsvfile.dto.Contact;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class DataMappingRunner implements CommandLineRunner {

    private final DataMappingService dataMappingService;

    @Override
    public void run(String... args) throws Exception {
        String contactsFilePath = "D:\\DagorasCompany\\Varnish_Cache\\mapping-csv-file\\data.csv";
        String employeesFilePath = "D:\\DagorasCompany\\Varnish_Cache\\mapping-csv-file\\large_data.csv";
        String outputFilePath = "D:\\DagorasCompany\\Varnish_Cache\\mapping-csv-file\\output.csv";

        List<Contact> contacts = dataMappingService.mapData(contactsFilePath, employeesFilePath);

        // Ghi danh sách contacts vào file CSV mới
        dataMappingService.writeDataAfterMappingToFile(contacts, outputFilePath);
    }
}
