package lab3.practice;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Date;

@Configuration
public class Config {
    @Bean
    public Printer printer(){
        MessagePrinter messagePrinter = new MessagePrinter();
        messagePrinter.setMessage(String.valueOf(new Date()));
        return messagePrinter;
    }

    @Bean
    public PerformanceLoggerBeanPostProcessor performanceLoggerBeanPostProcessor(){
        return new PerformanceLoggerBeanPostProcessor();
    };

}
