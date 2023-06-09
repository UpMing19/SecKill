package com.xxxx.seckill.config;


import com.rabbitmq.client.AMQP;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.listener.Topic;

@Configuration
public class RabbitMQConfig {

    private  static  final  String QUEUE = "seckillQueue";
    private  static  final String EXCHANGE = "seckillExchange";


    @Bean
    public Queue queue(){
        return new Queue(QUEUE);
    }
    @Bean
    public TopicExchange topicExchange(){return new TopicExchange(EXCHANGE);}
    @Bean
    public Binding binding(){return BindingBuilder.bind(queue()).to(topicExchange()).with("seckill.#");}
}
