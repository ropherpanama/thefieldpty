package com.otacm.thefieldpty.json;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class JSONUtils {

private static Gson gson;
private static final String DEFAULT_PATTERN_DATE = "yyyy-MM-dd";

public static Gson factoryGson(final String pattern) {
return builderGson(pattern);
}

public static Gson factoryGson() {
return builderGson(DEFAULT_PATTERN_DATE);
}

private static Gson builderGson(final String pattern) {
if (gson == null) {
gson = new GsonBuilder().serializeNulls().setDateFormat(pattern)
.setPrettyPrinting().setVersion(1.0).create();
}
return gson;
}
}