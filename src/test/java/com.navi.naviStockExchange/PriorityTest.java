package com.navi.naviStockExchange;

import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

/**
 * Created by suyash.k
 * at 02/04/2021 12:43
 */

public class PriorityTest {
//  Confirms that lower selling price gets priority over time/order-id
    @Test
    public void priorityTest() throws Exception {
        String inputResource = "custom_input.txt";

        File inputFile = readFile(inputResource);
        List<String> result = GeekTrust.computeTransactionResultsFromOrdersFile(inputFile);

        List<String> answer =  new LinkedList<>();
        answer.add("#3 80.00 110 #2");
        Assert.assertEquals(answer, result);
    }

    private File readFile(String resourceName) {
        ClassLoader loader = IntegrationTest.class.getClassLoader();
        return new File(Objects.requireNonNull(loader.getResource(resourceName)).getFile());
    }
}
