package com.lombardrisk.pojo;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lombardrisk.core.utils.PropHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by amy sheng on 4/3/2018.
 */
public class ServerManager {
    private static final Logger logger = LoggerFactory.getLogger(ServerManager.class);
    static final List<TestEnvironment> TEST_ENVIRONMENTS = getTestEnvironments(PropHelper.SERVER_INFO);

    private static List<TestEnvironment> getTestEnvironments(String json) {
        try {
            return (new ObjectMapper()).readValue(json, new TypeReference<List<TestEnvironment>>() {
            });
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
        return new ArrayList<>();
    }
    public static List<TestEnvironment> getTestEnvironments() {
        return TEST_ENVIRONMENTS;
    }
    public static TestEnvironment getTestEnvironment() throws Exception {
        logger.info("{} test environments is available before taking");
        ObjectMapper mapper = new ObjectMapper();
        TestEnvironment testEnvironment = mapper.readValue(PropHelper.SERVER_INFO,TestEnvironment[].class)[0];
        return testEnvironment;
    }
}
