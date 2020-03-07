package de.holarse.web.sitemap;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import java.io.IOException;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class W3cDateFormatter extends StdSerializer<OffsetDateTime> {
    static DateTimeFormatter dateTimeFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm'Z'");

    public W3cDateFormatter() {
        this(null);
    }

    public W3cDateFormatter(Class<OffsetDateTime> t) {
        super(t);
    }

    /**
     * siehe Datumsformat: https://www.w3.org/TR/NOTE-datetime
     *
     * <p>Complete date plus hours and minutes: YYYY-MM-DDThh:mmTZD (eg 1997-07-16T19:20+01:00)
     *
     * @param date
     * @param gen
     * @param arg2
     * @throws java.io.IOException
     */
    @Override
    public void serialize(
            final OffsetDateTime date, final JsonGenerator gen, final SerializerProvider arg2)
            throws IOException {
        gen.writeString(
                date.toInstant().atZone(ZoneId.of("UTC")).format(W3cDateFormatter.dateTimeFormat));
    }
}
