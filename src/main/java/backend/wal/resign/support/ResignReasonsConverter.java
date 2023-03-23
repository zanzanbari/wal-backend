package backend.wal.resign.support;

import backend.wal.resign.domain.ResignReasons;
import backend.wal.resign.exception.InternalServerJsonException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class ResignReasonsConverter implements AttributeConverter<ResignReasons, String> {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(ResignReasons attribute) {
        try {
            return OBJECT_MAPPER.writeValueAsString(attribute);
        } catch (JsonProcessingException e) {
            throw InternalServerJsonException.writeException(attribute.toString(), e.getMessage());
        }
    }

    @Override
    public ResignReasons convertToEntityAttribute(String dbData) {
        try {
            return OBJECT_MAPPER.readValue(dbData, new TypeReference<>() {});
        } catch (JsonProcessingException e) {
            throw InternalServerJsonException.readException(dbData, e.getMessage());
        }
    }
}
