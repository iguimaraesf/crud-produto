package com.inavan.cadastro.conf;

import com.inavan.cadastro.dto.ProdutoDto;
import com.inavan.cadastro.service.ProdutoService;
import io.smallrye.reactive.messaging.kafka.Record;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.reactive.messaging.Incoming;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
@Slf4j
public class ProdutoConsumer {

    ProdutoService service;

    public ProdutoConsumer(ProdutoService service) {
        this.service = service;
    }

//    @Incoming("produtos-in")
//    public void receberProduto(Record<String, ProdutoDto> registro) {
//        ProdutoDto p = registro.value();
//        if (p == null) {
//            log.warn("Registro veio com valor nulo: {}.", registro.key());
//        } else {
//            log.info("Recebi o produto {}, com valor {}.", registro.key(), p.getNomesAlternativos());
//            service.incluirOuAlterar(p);
//        }
//    }
}
