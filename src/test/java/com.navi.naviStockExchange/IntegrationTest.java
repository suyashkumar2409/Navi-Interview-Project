package com.navi.naviStockExchange;

import com.navi.naviStockExchange.services.FileParser;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.InputStream;
import java.util.List;
import java.util.Objects;

/**
 * Created by suyash.k
 * at 01/04/2021 18:55
 */

public class IntegrationTest {
    @Test
    public void integrationTest() {
        String inputResource = "input.txt";
        String answerResource = "answer.txt";

        File inputFile = readFile(inputResource);
        List<String> result = GeekTrust.run(inputFile);

        List<String> answer =  new FileParser().parse(readFile(answerResource));
        Assert.assertEquals(answer, result);
    }

    private File readFile(String resourceName) {
        ClassLoader loader = IntegrationTest.class.getClassLoader();
        return new File(Objects.requireNonNull(loader.getResource(resourceName)).getFile());
    }
}
