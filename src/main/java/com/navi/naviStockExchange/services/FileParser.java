package com.navi.naviStockExchange.services;

import java.io.File;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by suyash.k
 * at 01/04/2021 18:58
 */

public class FileParser {
    public List<String> parse(File file) {
        List<String> input = new LinkedList<>();
        try {
            Scanner sc = new Scanner(file);
            while(sc.hasNextLine()) {
                input.add(sc.nextLine());
            }
            sc.close();
        } catch (Exception e) {
        }
        return input;
    }
}
