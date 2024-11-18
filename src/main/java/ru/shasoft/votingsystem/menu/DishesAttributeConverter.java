package ru.shasoft.votingsystem.menu;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import ru.shasoft.votingsystem.menu.model.Dish;

import java.util.List;

@Converter
public class DishesAttributeConverter implements AttributeConverter<List<Dish>, String> {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(List<Dish> items) {
        try {
            return objectMapper.writeValueAsString(items);
        } catch (JsonProcessingException jpe) {
            return null;
        }
    }

    @Override
    public List<Dish> convertToEntityAttribute(String value) {
        try {
            return objectMapper.readValue(value, new TypeReference<List<Dish>>() {
            });
        } catch (JsonProcessingException e) {
            return null;
        }
    }
}
