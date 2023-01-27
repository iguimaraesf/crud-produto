package com.inavan.cadastro.helper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.beanutils.BeanUtils;

import java.lang.reflect.InvocationTargetException;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class CopiaHelper {
    public static void copiar(Object destino, Object fonte) {
        try {
            BeanUtils.copyProperties(destino, fonte);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

}
