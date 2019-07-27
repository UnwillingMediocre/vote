package com.stodger.util;

import java.util.Random;
import java.util.UUID;
import java.util.Vector;

import static java.lang.System.currentTimeMillis;

/**
 * @author Stodger
 * @version V1.0
 * @date 2019-06-29 09:47
 */
public class IdUtil {

    private static final String ID_PREFIX = "voteid_";

    public static String getId(){
        Random random = new Random();
        String str = UUID.randomUUID().toString().replaceAll("-", "");
        int start = random.nextInt(str.length() / 2);
        return ID_PREFIX + str.substring(start, start + 8);
    }
}
