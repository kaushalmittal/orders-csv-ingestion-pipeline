package com.example.orders.validation;

import com.example.orders.common.CsvSupport;
import com.example.orders.common.OutputPathSupport;
import com.example.orders.validation.OrderRowValidator.RowValidationResult;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class OrderValidationApp {

    public static void main(String[] args) throws IOException {
        //TODO (Recording concept 1): Implement a main method that invokes the OrderRowValidator and writes valid and invalid rows to separate CSV files
    }

    private static void writeValid(BufferedWriter writer, RowValidationResult result) throws IOException {
        var row = result.record();
        writer.write(CsvSupport.formatOrderRow(row.orderId(), row.customerEmail(), row.amount(), row.createdAt()));
        writer.newLine();
    }

    private static void writeIssues(BufferedWriter writer, RowValidationResult result) throws IOException {
        for (var issue : result.issues()) {
            writer.write(issue.lineNumber() + ","
                    + CsvSupport.escape(issue.fieldName()) + ","
                    + CsvSupport.escape(issue.ruleCode()) + ","
                    + CsvSupport.escape(issue.detail()) + ","
                    + CsvSupport.escape(issue.rawValue()));
            writer.newLine();
        }
    }

}
