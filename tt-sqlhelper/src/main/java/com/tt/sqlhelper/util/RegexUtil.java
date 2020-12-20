package com.tt.sqlhelper.util;

import java.util.regex.Pattern;

/**
 * @author: guoyitao
 * @date: 2020/11/24 19:58
 * @version: 1.0
 */
public class RegexUtil {
    /** #{}正则匹配 */
    public static final Pattern param_pattern = Pattern.compile("#\\{([^\\{\\}]*)\\}");
}
