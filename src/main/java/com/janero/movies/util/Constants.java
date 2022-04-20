package com.janero.movies.util;

import java.nio.charset.Charset;
import org.springframework.http.MediaType;

public final class Constants {
    public static final int DEFAULT_PAGE_SIZE = 10;
    public static final MediaType APPLICATION_JSON_UTF8 =
            new MediaType(MediaType.APPLICATION_JSON.getType(),
                    MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

    private Constants() {}
}
