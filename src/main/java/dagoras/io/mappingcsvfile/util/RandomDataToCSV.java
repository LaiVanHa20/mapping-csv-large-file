package dagoras.io.mappingcsvfile.util;

import com.opencsv.CSVWriter;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class RandomDataToCSV {
    public static void main(String[] args) {
        String csvFilePath = "data.csv";

        try (CSVWriter writer = new CSVWriter(new FileWriter(csvFilePath))) {
            String[] header = {"Số điện thoại", "Họ tên", "Địa chỉ", "Email"};
            writer.writeNext(header);

            Random random = new Random();
            for (int i = 0; i < 10; i++) {
                String phoneNumber = generateRandomPhoneNumber();
                String fullName = generateRandomFullName();
                String address = generateRandomAddress();
                String email = generateRandomEmail();

                String[] data = {phoneNumber, fullName, address, email};
                writer.writeNext(data);
            }

            System.out.println("File CSV đã được tạo thành công!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String generateRandomPhoneNumber() {
        String[] prefixes = {"03", "05", "07", "08", "09"};
        Random random = new Random();

        String prefix = prefixes[random.nextInt(prefixes.length)];
        StringBuilder phoneNumber = new StringBuilder(prefix);

        for (int i = 0; i < 8; i++) {
            int digit = random.nextInt(10);
            phoneNumber.append(digit);
        }

        return phoneNumber.toString();
    }

    private static String generateRandomFullName() {
        String[] firstNames = {"John", "Jane", "David", "Linda", "Michael", "Emily"};
        String[] lastNames = {"Smith", "Johnson", "Williams", "Brown", "Jones", "Davis"};

        Random random = new Random();
        String firstName = firstNames[random.nextInt(firstNames.length)];
        String lastName = lastNames[random.nextInt(lastNames.length)];

        return firstName + " " + lastName;
    }

    private static String generateRandomAddress() {
        String[] streets = {"Main St", "Park Ave", "Oak St", "Elm St", "Cedar Rd"};
        String[] cities = {"New York", "Los Angeles", "Chicago", "Houston", "San Francisco"};

        Random random = new Random();
        String street = streets[random.nextInt(streets.length)];
        String city = cities[random.nextInt(cities.length)];

        return street + ", " + city;
    }

    private static String generateRandomEmail() {
        String[] domains = {"gmail.com", "yahoo.com", "hotmail.com", "outlook.com"};

        Random random = new Random();
        String firstName = generateRandomFullName().replaceAll(" ", "").toLowerCase();
        String domain = domains[random.nextInt(domains.length)];

        return firstName + "@" + domain;
    }
}
