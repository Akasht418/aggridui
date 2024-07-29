package com.example.demo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@CrossOrigin(origins = "*")  // Allows CORS for all origins
public class JsonController {

    @Autowired
    private ObjectMapper objectMapper;

    @GetMapping("/api/json")
    public List<JsonNode> getJson(
            @RequestParam(required = false) Integer start,
            @RequestParam(required = false) Integer end) throws IOException {
        // Load the JSON file from the resources folder
        ClassPathResource resource = new ClassPathResource("data.json");
        // Read the JSON file and convert it to an array of JsonNode objects
        JsonNode jsonArray = objectMapper.readTree(resource.getInputStream());

        // Default start and end if not provided
        int defaultStart = (start != null) ? start : 0;
        int defaultEnd = (end != null) ? end : jsonArray.size();

        // Create a sublist based on start and end
        List<JsonNode> sublist = new ArrayList<>();
        for (int i = defaultStart; i < defaultEnd && i < jsonArray.size(); i++) {
            sublist.add(jsonArray.get(i));
        }

        return sublist;
    }
}