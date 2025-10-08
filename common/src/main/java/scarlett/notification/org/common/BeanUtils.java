package scarlett.notification.org.common;

import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

@Component
public class BeanUtils extends org.apache.commons.beanutils.BeanUtilsBean {
    @Override
    public void copyProperty(
            Object bean,
            String name,
            Object value) throws IllegalAccessException, InvocationTargetException {
        try {
            if (Objects.isNull(value)) {
                return;
            }
            if (value instanceof Collection) {
                Collection property = List.of(this.getArrayProperty(bean, name));
                for (Object item : (Collection) value) {
                    this.copyProperties(property, item);
                }
            } else {
                super.copyProperty(bean, name, value);
            }
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }
}
