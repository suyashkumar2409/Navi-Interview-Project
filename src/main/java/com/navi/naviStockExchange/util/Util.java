package com.navi.naviStockExchange.util;

import java.util.StringTokenizer;

/**
 * Created by suyash.k
 * at 02/04/2021 11:29
 */

public class Util {
    public static String[] getTokens(String input, String delimiter, int numTokens) throws Exception {
        StringTokenizer tokenizer = new StringTokenizer(input, delimiter);
        String[] tokens = new String[numTokens];
        int i=0;
        while(tokenizer.hasMoreTokens()) {
            tokens[i++] = tokenizer.nextToken();
        }

        if(i!=numTokens) {
            throw new Exception(String.format("Input columns don't match with expected columns::%s", input));
        }
        return tokens;
    }
}
