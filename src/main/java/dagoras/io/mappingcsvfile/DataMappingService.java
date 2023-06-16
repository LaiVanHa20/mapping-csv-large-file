package dagoras.io.mappingcsvfile;

import com.opencsv.CSVWriter;
import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import dagoras.io.mappingcsvfile.dto.Contact;
import dagoras.io.mappingcsvfile.dto.Employee;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class DataMappingService {

    public void writeDataAfterMappingToFile(List<Contact> contacts, String outputPath) {
        try (Writer writer = Files.newBufferedWriter(Paths.get(outputPath));
             CSVWriter csvWriter = new CSVWriter(writer)) {
            csvWriter.writeNext(new String[]{"Phone Number", "Full Name", "Address", "Email", "Tax Code", "Social Insurance Code"});

            for (Contact contact : contacts) {
                String phoneNumber = contact.getPhoneNumber();
                String fullName = contact.getFullName();
                String address = contact.getAddress();
                String email = contact.getEmail();
                String taxCode = contact.getTaxCode();
                String socialInsuranceCode = contact.getSocialInsuranceCode();

                // Chỉ ghi những bản ghi đã mapping có dữ liệu về taxCode và socialInsuranceCode
                if (taxCode != null && socialInsuranceCode != null) {
                    csvWriter.writeNext(new String[]{phoneNumber, fullName, address, email, taxCode, socialInsuranceCode});
                }
                csvWriter.flush(); // Flush dữ liệu sau khi ghi từng phần tử
            }
        } catch (IOException e) {
            // Xử lý exception
        }
    }

    public List<Contact> mapData2(String contactsFilePath, String employeesFilePath) {
        List<Contact> contacts = readContactsFile(contactsFilePath);
        List<Employee> employees = readEmployeesFile(employeesFilePath);

        Map<String, Employee> employeeMap = employees.stream()
                .collect(Collectors.toMap(Employee::getPhoneNumber, Function.identity()));

        return contacts.stream()
                .peek(contact -> {
                    String phoneNumber = contact.getPhoneNumber();
                    Employee employee = employeeMap.get(phoneNumber);
                    if (employee != null) {
                        contact.setTaxCode(employee.getTaxCode());
                        contact.setSocialInsuranceCode(employee.getSocialInsuranceCode());
                    }
                })
                .collect(Collectors.toList());
    }


    public List<Contact> mapData(String contactsFilePath, String employeesFilePath) {
        try (Reader contactsReader = Files.newBufferedReader(Paths.get(contactsFilePath));
             Reader employeesReader = Files.newBufferedReader(Paths.get(employeesFilePath))) {
            ColumnPositionMappingStrategy<Contact> contactsMappingStrategy = new ColumnPositionMappingStrategy<>();
            contactsMappingStrategy.setType(Contact.class);
            String[] contactsColumns = new String[]{"phoneNumber", "fullName", "address", "email"};
            contactsMappingStrategy.setColumnMapping(contactsColumns);

            CsvToBean<Contact> contactsCsvToBean = new CsvToBeanBuilder<Contact>(contactsReader)
                    .withType(Contact.class)
                    .withMappingStrategy(contactsMappingStrategy)
                    .build();

            List<Contact> contacts = new ArrayList<>();
            for (Contact contact : contactsCsvToBean) {
                contacts.add(contact);
            }

            ColumnPositionMappingStrategy<Employee> employeesMappingStrategy = new ColumnPositionMappingStrategy<>();
            employeesMappingStrategy.setType(Employee.class);
            String[] employeesColumns = new String[]{"phoneNumber", "taxCode", "socialInsuranceCode"};
            employeesMappingStrategy.setColumnMapping(employeesColumns);

            CsvToBean<Employee> employeesCsvToBean = new CsvToBeanBuilder<Employee>(employeesReader)
                    .withType(Employee.class)
                    .withMappingStrategy(employeesMappingStrategy)
                    .build();

            Map<String, Employee> employeeMap = new HashMap<>();
            for (Employee employee : employeesCsvToBean) {
                employeeMap.put(employee.getPhoneNumber(), employee);
            }

            for (Contact contact : contacts) {
                String phoneNumber = contact.getPhoneNumber();
                Employee employee = employeeMap.get(phoneNumber);
                if (employee != null) {
                    contact.setTaxCode(employee.getTaxCode());
                    contact.setSocialInsuranceCode(employee.getSocialInsuranceCode());
                }
            }

            return contacts;
        } catch (IOException e) {
            // Xử lý exception
        }
        return Collections.emptyList();
    }


    private List<Contact> readContactsFile(String filePath) {
        try (Reader reader = Files.newBufferedReader(Paths.get(filePath))) {
            ColumnPositionMappingStrategy<Contact> mappingStrategy = new ColumnPositionMappingStrategy<>();
            mappingStrategy.setType(Contact.class);
            String[] columns = new String[]{"phoneNumber", "fullName", "address", "email"};
            mappingStrategy.setColumnMapping(columns);

            CsvToBean<Contact> csvToBean = new CsvToBeanBuilder<Contact>(reader)
                    .withType(Contact.class)
                    .withMappingStrategy(mappingStrategy)
                    .build();

            return csvToBean.parse();
        } catch (IOException e) {
            // Xử lý exception
        }
        return Collections.emptyList();
    }


    private List<Employee> readEmployeesFile(String filePath) {
        try (Reader reader = Files.newBufferedReader(Paths.get(filePath))) {
            ColumnPositionMappingStrategy<Employee> mappingStrategy = new ColumnPositionMappingStrategy<>();
            mappingStrategy.setType(Employee.class);
            String[] columns = new String[]{"phoneNumber", "taxCode", "socialInsuranceCode"};
            mappingStrategy.setColumnMapping(columns);
            CsvToBean<Employee> csvToBean = new CsvToBeanBuilder<Employee>(reader)
                    .withType(Employee.class)
                    .withMappingStrategy(mappingStrategy)
                    .build();
            return csvToBean.parse();
        } catch (IOException e) {
            // Xử lý exception
        }
        return Collections.emptyList();
    }
}
