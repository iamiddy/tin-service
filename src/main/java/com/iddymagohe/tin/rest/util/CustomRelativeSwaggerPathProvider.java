package com.iddymagohe.tin.rest.util;

import com.mangofactory.swagger.paths.RelativeSwaggerPathProvider;

/**
 * Created by iddymagohe on 8/9/14.
 */
public class CustomRelativeSwaggerPathProvider extends RelativeSwaggerPathProvider {

    public static final String ROOT = "/tin-service/";

    @Override
    protected String applicationPath() {
        return ROOT;
    }


}
