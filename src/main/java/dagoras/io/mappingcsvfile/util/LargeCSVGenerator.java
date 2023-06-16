package dagoras.io.mappingcsvfile.util;

import com.opencsv.CSVWriter;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class LargeCSVGenerator {
    public static void main(String[] args) {
        String csvFilePath = "large_data.csv";
        int numberOfRows = 3000000;

        generateLargeCSV(csvFilePath, numberOfRows);
    }

    private static void generateLargeCSV(String csvFilePath, int numberOfRows) {
        try (CSVWriter writer = new CSVWriter(new FileWriter(csvFilePath))) {
            String[] header = {"Số điện thoại", "Mã số thuế", "Mã số BHXH"};
            writer.writeNext(header);

            Random random = new Random();
            for (int i = 0; i < numberOfRows; i++) {
                String phoneNumber = generateRandomPhoneNumber();
                String taxCode = generateRandomTaxCode();
                String socialInsuranceNumber = generateRandomSocialInsuranceNumber();

                String[] data = {phoneNumber, taxCode, socialInsuranceNumber};
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

    private static String generateRandomTaxCode() {
        StringBuilder taxCode = new StringBuilder();
        Random random = new Random();

        for (int i = 0; i < 10; i++) {
            int digit = random.nextInt(10);
            taxCode.append(digit);
        }

        return taxCode.toString();
    }

    private static String generateRandomSocialInsuranceNumber() {
        StringBuilder socialInsuranceNumber = new StringBuilder();
        Random random = new Random();

        for (int i = 0; i < 12; i++) {
            int digit = random.nextInt(10);
            socialInsuranceNumber.append(digit);
        }

        return socialInsuranceNumber.toString();
    }
}

