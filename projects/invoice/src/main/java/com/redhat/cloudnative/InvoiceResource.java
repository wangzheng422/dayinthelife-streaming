package com.redhat.cloudnative;

import java.text.DecimalFormat;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import io.vertx.core.json.JsonObject;

@Path("/")
public class InvoiceResource {

/*  
    这里应该是order service调用的点，
    调用的参数是一个order object, 他是继承了序列化，我们猜测他通过json自动进行了转化
    @*** 是java里面的自定义annotation, 用@interface声明出来的
    他不会修改源代码，但是在支撑的底层框架里面，会有如下代码，检查annotation
    Class<?> clazz = object.getClass();
    if (!clazz.isAnnotationPresent(JsonSerializable.class)) {

    Map<String, String> jsonElementsMap = new HashMap<>();
    for (Field field : clazz.getDeclaredFields()) {
        field.setAccessible(true);
        if (field.isAnnotationPresent(JsonElement.class)) {

    可以看到，底层框架代码，可以通过检查 annotation 来判断是否需要自动转化，或者执行更多的业务逻辑。
*/
    @POST
    @Path("/notify/order")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String notify(Order order) throws Exception {

        Thread.sleep(30000);

        return processOrder(order);
    }

    protected String processOrder(Order order) {
        JsonObject output = new JsonObject();
        output.put("orderId", order.getOrderId());
        output.put("itemId", order.getItemId());
        output.put("department", "invoicing");
        output.put("datetime", System.currentTimeMillis());
        output.put("amount", order.getQuantity() * order.getPrice());
        output.put("currency", "USD");
        DecimalFormat numformat = new DecimalFormat("#");
        output.put("invoiceId", numformat.format(Math.floor(100000 + Math.random() * 999999)) );
        return output.encode();
    }
}