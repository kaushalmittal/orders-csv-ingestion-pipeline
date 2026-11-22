package com.example.orders.routing;

import com.example.orders.common.CsvSupport;
import com.example.orders.common.OutputPathSupport;
import com.example.orders.validation.OrderRowValidator;
import com.example.orders.validation.OrderRowValidator.RowValidationResult;
import com.example.orders.routing.BatchDeduplicationRouter.RejectedEntry;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class BatchRoutingApp {

    private record InvalidEntry(long lineNumber, String reason, String rawLine) {}

    public static void main(String[] args) throws IOException {
        //TODO (Recording concept 2): Implement a main method that invokes the BatchDeduplicationRouter and writes clean and rejected rows to separate CSV files
    }

    private static void writeClean(BufferedWriter writer, RowValidationResult result) throws IOException {
        var row = result.record();
        writer.write(CsvSupport.formatOrderRow(row.orderId(), row.customerEmail(), row.amount(), row.createdAt()));
        writer.newLine();
    }

    private static void writeRejected(
            BufferedWriter writer,
            long lineNumber,
            String category,
            String reason,
            String rawLine
    ) throws IOException {
        writer.write(lineNumber + "," + CsvSupport.escape(category) + ","
                + CsvSupport.escape(reason) + "," + CsvSupport.escape(rawLine));
        writer.newLine();
    }

}
