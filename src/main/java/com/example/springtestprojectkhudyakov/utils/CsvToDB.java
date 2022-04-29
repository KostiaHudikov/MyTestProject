package com.example.springtestprojectkhudyakov.utils;

import com.example.springtestprojectkhudyakov.domain.Message;
import com.example.springtestprojectkhudyakov.repos.MessageRepo;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CsvToDB {

    public static void convertCSVtoDB(String path , MessageRepo messageRepo) throws IOException {
        CSVParser parser = new CSVParser(new FileReader(path), CSVFormat.newFormat(';'));
        List<CSVRecord> list = parser.getRecords();
        List<Message> listMessage = new ArrayList<>();
        int counter = 0;
        for (CSVRecord record : list) {
            String[] arr = new String[record.size()];
            int i = 0;
            for (String str : record) {
                arr[i++] = str;
            }

            if(counter > 0){
                listMessage.add(Message.builder()
                        .ssoid(list.get(counter).get(0))
                        .ts(list.get(counter).get(1))
                        .grp(list.get(counter).get(2))
                        .type(list.get(counter).get(3))
                        .subtype(list.get(counter).get(4))
                        .url(list.get(counter).get(5))
                        .orgid(list.get(counter).get(6))
                        .formid(list.get(counter).get(7))
                        .code(list.get(counter).get(8))
                        .ltpa(list.get(counter).get(9))
                        .sudirresponse(list.get(counter).get(10))
                        .ymdh(list.get(counter).get(11))
                        .build());
            }
            counter++;
        }
        parser.close();
        messageRepo.saveAll(listMessage);
    }
}
