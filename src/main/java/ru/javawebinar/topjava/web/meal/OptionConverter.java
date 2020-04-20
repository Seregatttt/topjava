package ru.javawebinar.topjava.web.meal;
import org.springframework.core.convert.converter.Converter;
import ru.javawebinar.topjava.util.DateTimeUtil;

import java.time.LocalDate;
import java.time.LocalTime;

public class OptionConverter  {

    public static class OptionConverterLocalDate implements Converter<String, LocalDate>{
        @Override
        public LocalDate convert(String source) {
            return DateTimeUtil.parseLocalDate(source);
        }
    }

    public static class OptionConverterLocalTime implements Converter<String, LocalTime> {
        @Override
        public LocalTime convert(String source) {
            return DateTimeUtil.parseLocalTime(source);
        }
    }
}

