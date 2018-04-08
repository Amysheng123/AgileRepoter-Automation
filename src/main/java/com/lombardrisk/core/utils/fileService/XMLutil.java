package com.lombardrisk.core.utils.fileService;

import java.io.File;
import java.util.List;

/**
 * Created by amy sheng on 3/8/2018.
 */
public class XMLutil {
    private static List l;

    public  static List getXmlData() {
        ParseXml p = new ParseXml();
        l = p.parse3Xml((new File("src/test/resources/TestData.xml")).getAbsolutePath());
        return l;

    }


}
