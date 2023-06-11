package dagoras.io.mappingcsvfile;

import com.opencsv.bean.CsvToBeanBuilder;
import dagoras.io.mappingcsvfile.dto.Contact;
import dagoras.io.mappingcsvfile.dto.Employee;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DataMappingService {

    public List<Contact> mapData(String contactsFilePath, String employeesFilePath) {
        List<Contact> contacts = readContactsFile(contactsFilePath);
        List<Employee> employees = readEmployeesFile(employeesFilePath);

        // Tiến hành map dữ liệu
        // Ví dụ, sử dụng HashMap để tối ưu việc tìm kiếm dữ liệu theo số điện thoại
        Map<String, Contact> contactMap = new HashMap<>();
        for (Contact contact : contacts) {
            contactMap.put(contact.getPhoneNumber(), contact);
        }

        for (Employee employee : employees) {
            Contact contact = contactMap.get(employee.getPhoneNumber());
            if (contact != null) {
                // Gán dữ liệu từ Employee vào Contact
                contact.setTaxCode(employee.getTaxCode());
                contact.setSocialInsuranceCode(employee.getSocialInsuranceCode());
            }
        }

        return contacts;
    }

    private List<Contact> readContactsFile(String filePath) {
        try (Reader reader = Files.newBufferedReader(Paths.get(filePath))) {
            return new CsvToBeanBuilder<Contact>(reader)
                    .withType(Contact.class)
                    .build()
                    .parse();
        } catch (IOException e) {
            // Xử lý exception
        }
        return Collections.emptyList();
    }

    private List<Employee> readEmployeesFile(String filePath) {
        try (Reader reader = Files.newBufferedReader(Paths.get(filePath))) {
            return new CsvToBeanBuilder<Employee>(reader)
                    .withType(Employee.class)
                    .build()
                    .parse();
        } catch (IOException e) {
            // Xử lý exception
        }
        return Collections.emptyList();
    }
}
