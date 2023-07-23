package com.sxia.shardingsphere.controller;

import com.sxia.model.sharding.TEntOrder;
import com.sxia.shardingsphere.service.TEntOrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

@RestController
public class OrderController {
    @Resource
    private TEntOrderService orderService;

    @PostMapping("/add")
    public ResponseEntity<TEntOrder> add() {
       return  ResponseEntity.ok(orderService.add());
    }
    @GetMapping("/queryOrder/{orderId}")
    public ResponseEntity queryOrder(@PathVariable ("orderId")  final  Long orderId) {
        TEntOrder orderItem = orderService.queryOrder(orderId);
        return ResponseEntity.ok(orderItem);
    }
}
