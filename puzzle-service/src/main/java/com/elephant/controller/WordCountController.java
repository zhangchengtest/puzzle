package com.elephant.controller;

import com.hankcs.hanlp.HanLP;
import com.hankcs.hanlp.seg.common.Term;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

@RestController
@RequestMapping("/wordCount")
@Slf4j
public class WordCountController {

    private static final int CHUNK_SIZE = 1024 * 1024; // 1MB

    @PostMapping("/count")
    public List<Map.Entry<String, Integer>> countWords(@RequestParam("file") MultipartFile file) throws IOException {
        Map<String, Integer> wordMap = new HashMap<>();
        BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()));
        char[] chunk = new char[CHUNK_SIZE];
        int chunkSize;
        while ((chunkSize = reader.read(chunk, 0, CHUNK_SIZE)) != -1) {
            String text = new String(chunk, 0, chunkSize);
            char[] words = text.toCharArray();
            for (char word : words) {
                if (wordMap.containsKey(word+"")) {
                    int count = wordMap.get(word+"");
                    wordMap.put(word+"", count + 1);
                } else {
                    wordMap.put(word+"", 1);
                }
            }
        }
        List<Map.Entry<String, Integer>> wordList = new ArrayList<>(wordMap.entrySet());
        Collections.sort(wordList, new Comparator<Map.Entry<String, Integer>>() {
            @Override
            public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                return o2.getValue() - o1.getValue();
            }
        });
        log.info("size [{}] ", wordList.size());
        return wordList;
    }

    public static void main(String[] args) {


        String text = "今天天气不错";

        List<Term> wordList = HanLP.segment(text);
        StringBuilder sb = new StringBuilder();
        for (Term term : wordList) {
            sb.append(term.word).append(" ");
        }

        String result = sb.toString().trim();
        System.out.println(result);
    }
}
