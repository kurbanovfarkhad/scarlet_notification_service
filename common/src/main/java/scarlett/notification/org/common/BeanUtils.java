package scarlett.notification.org.common;

import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.util.Objects;

@Component
public class BeanUtils extends org.apache.commons.beanutils.BeanUtilsBean {
    @Override
    public void copyProperty(
            Object bean,
            String name,
            Object value) throws IllegalAccessException, InvocationTargetException {
        if (Objects.isNull(value)) return;
        super.copyProperty(bean, name, value);
    }
}
