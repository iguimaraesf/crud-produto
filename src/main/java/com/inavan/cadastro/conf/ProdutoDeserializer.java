package com.inavan.cadastro.conf;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.inavan.cadastro.dto.ProdutoDto;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.serialization.Deserializer;

import java.nio.charset.StandardCharsets;

@Slf4j
public class ProdutoDeserializer implements Deserializer<ProdutoDto> {
    @Override
    public ProdutoDto deserialize(String arg0, byte[] arg1) {
        ObjectMapper mapper = new ObjectMapper();
        ProdutoDto dto = null;
        try {
            dto = mapper.readValue(new String(arg1, StandardCharsets.UTF_8), ProdutoDto.class);
        } catch (Exception e) {
            log.error("Desserialização", e);
        }
        return dto;
    }
}
