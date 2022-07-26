package com.legacy.ordersservice;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestOperations;

@Repository
public class OrdersRepository {
    private final RestOperations restOperations;

    public OrdersRepository(RestTemplateBuilder restTemplateBuilder) {
        this.restOperations = restTemplateBuilder.build();
    }
/*
 *  这里调用了inventory, invoice service，注意是同步调用，会阻塞。
 */
    public String placeOrder(Order order) {
        String res1 = this.restOperations.postForObject("http://inventory:8080/notify/order",order, String.class);
        String res2 = this.restOperations.postForObject("http://invoice:8080/notify/order", order, String.class);
        return createObject(res1, res2);
    }

    private String createObject(String o1, String o2) {
        return "{\"inventory\":"+ o1 + ",\"invoice\":" + o2 + "}";
    }
}