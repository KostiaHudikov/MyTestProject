package com.example.springtestprojectkhudyakov.controllers;

import com.example.springtestprojectkhudyakov.utils.CsvToDB;
import com.example.springtestprojectkhudyakov.domain.Message;
import com.example.springtestprojectkhudyakov.repos.MessageRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Controller
public class MainController {
    @Autowired
    private MessageRepo messageRepo;
    private CsvToDB csvToDB;

    @Value("${upload.path}")
    private String uploadPath;

    @GetMapping("/")
    public String main(Map<String, Object> model) {
        return "main.html";
    }

    @GetMapping("addtodb")
    public String addToDBget(Map<String, Object> model) {
        return "addtodb.html";
    }

    @PostMapping("addtodb")
    public String addToDB(@RequestParam("file") MultipartFile file, Map<String, Object> model) throws IOException {
        String uuidFile = UUID.randomUUID().toString();
        String newFilePath = uploadPath + "/" + uuidFile + "." + file.getOriginalFilename();
        file.transferTo(new File(newFilePath));

        csvToDB.convertCSVtoDB(newFilePath, messageRepo);

        return "addtodb.html";
    }


    @GetMapping("type2")
    public String testQuestion2(Map<String, Object> model) {
        List<Message> messages = messageRepo.findAllBySubtype("start");
        List<Message> finalList = new ArrayList<>();
        int visibleContent;
        if (messages.size() > 100) {
            visibleContent = 100;
        } else {
            visibleContent = messages.size();
        }
        for (int i = 0; i < visibleContent; i++) {
            Message message = messages.get(i);
            List<Message> m = messageRepo.findByFormidAndSsoidAndSubtypeNotSendNotStartNotUnauthorized(
                    message.getFormid(), message.getSsoid());
            for (Message message2 : m) {
                finalList.add(message2);
            }
        }
        model.put("messages", finalList);
        return "type2.html";
    }

    @GetMapping("type3")
    public String testQuestion3(Map<String, Object> model) {
        HashSet<String> formIdSet = messageRepo.findUniqueFormid();
        HashMap<String, Integer> map = new HashMap<>();
        for (String formId : formIdSet) {
            int count = messageRepo.countByFormId(formId);
            map.put(formId, count);
        }

        LinkedHashMap<String, Integer> sortedMap = map.entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .collect(Collectors
                        .toMap(Map.Entry::getKey,
                                Map.Entry::getValue,
                                (e1, e2) -> e1,
                                LinkedHashMap::new));

        LinkedHashMap<String, Integer> topFive = new LinkedHashMap<>();
        for (int i = 0; i < 5; i++) {
            topFive.put(sortedMap.entrySet().iterator().next().getKey(),
                    sortedMap.entrySet().iterator().next().getValue());
            sortedMap.remove(sortedMap.entrySet().iterator().next().getKey());
        }
        model.put("messages", topFive);
        return "type3.html";
    }
}
