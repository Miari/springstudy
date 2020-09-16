package lab2.practice;


import org.springframework.beans.BeansException;
import org.springframework.beans.PropertyValue;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.TypedStringValue;
import org.springframework.stereotype.Component;


/**
 * Класс должен содержать логику подмены значений филдов заданых по умолчанию в контексте.
 * Заменяет строковые значение в бинах типа
 *
 * @see Printer
 * на значения в
 * @see PropertyRepository
 * Использует изначальные значения как ключи для поиска в PropertyRepository
 */

@Component
public class PropertyPlaceholder implements BeanFactoryPostProcessor {

    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        for (String beanDefinitionName : beanFactory.getBeanDefinitionNames()) {
            BeanDefinition beanDefinition = beanFactory.getBeanDefinition(beanDefinitionName);
            String currentClassName = beanDefinition.getBeanClassName();
            if (currentClassName.equals("lab2.practice.MessagePrinter")) {
                if (beanDefinition.getPropertyValues().contains("message")) {
                    PropertyValue propertyValue = beanDefinition.getPropertyValues().getPropertyValue("message");
                    String newValue = PropertyRepository.get(((TypedStringValue) propertyValue.getValue()).getValue());
                    if (newValue != null) {
                        beanDefinition.getPropertyValues().add("message", newValue);
                    }
                }
            }
        }

    }

    /*public void print(){
        System.out.println(message1);
    }*/
}
