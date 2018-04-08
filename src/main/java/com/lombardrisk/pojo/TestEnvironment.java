package com.lombardrisk.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

/**
 * Created by amy sheng on 4/3/2018.
 */
public class TestEnvironment {
    @JsonIgnore
    private static final Logger logger = LoggerFactory.getLogger(TestEnvironment.class);
    private List<ApplicationServer> applicationServers;
    private List<DatabaseServer> databaseServers;

    public List<DatabaseServer> getDatabaseServers() {
        return databaseServers;
    }

    public void setDatabaseServers(List<DatabaseServer> databaseServers) {
        this.databaseServers = databaseServers;
    }

    public List<ApplicationServer> getApplicationServers() {
        return applicationServers;
    }

    public void setApplicationServers(List<ApplicationServer> applicationServers) {
        this.applicationServers = applicationServers;
    }

    public DatabaseServer getDatabaseServer(int id) throws Exception {
        return (DatabaseServer) filterListById(databaseServers, id);
    }

    /**
     * get an generic object from a list by specified id
     *
     * @param list
     * @param id
     * @return an generic object
     */
    public static <T> Object filterListById(List<T> list, Object id) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        return filterListBy(list, "getId", id);
    }

    /**
     * get an generic object from a list by specified method
     *
     * @param list
     * @param by
     * @param value
     * @return an generic object
     */
    public static <T> Object filterListBy(List<T> list, String by, Object value) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        if (!list.isEmpty()) {
            Method method = list.get(0).getClass().getDeclaredMethod(by);
            for (T element : list) {
                if (method.invoke(element).equals(value))
                    return element;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        try {
            return (new ObjectMapper()).writeValueAsString(this);
        } catch (JsonProcessingException e) {
            logger.error(e.getMessage(), e);
            return "";
        }
    }
}
