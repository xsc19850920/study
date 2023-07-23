package com.sxia.shardingsphere.service;

import cn.hutool.core.date.DateUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sxia.mapper.sharding.TEntOrderDetailMapper;
import com.sxia.mapper.sharding.TEntOrderItemMapper;
import com.sxia.mapper.sharding.TEntOrderMapper;
import com.sxia.model.sharding.TEntOrder;
import com.sxia.model.sharding.TEntOrderDetail;
import com.sxia.model.sharding.TEntOrderExample;
import com.sxia.model.sharding.TEntOrderItem;
import com.sxia.shardingsphere.sharding.RedisIdGeneratorService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Service
public class TEntOrderService {
    @Resource
    private TEntOrderMapper orderMapper;
    @Resource
    private TEntOrderItemMapper orderItemMapper;
    @Resource
    private TEntOrderDetailMapper orderDetailMapper;

    @Resource
    private RedisIdGeneratorService redisIdGeneratorService;
    @Transactional(rollbackFor = Exception.class)
    public TEntOrder add () {
        Long entId = 5L;
        String regionCode = "BJ";

        //保存订单基本信息
        TEntOrder tEntOrder = new TEntOrder();
        Long orderId = redisIdGeneratorService.createUniqueId(String.valueOf(entId));
        tEntOrder.setId(orderId);
        tEntOrder.setRegionCode(regionCode);
        tEntOrder.setAmount(new BigDecimal(12.0));
        tEntOrder.setMobile("150****9235");
        tEntOrder.setEntId(entId);
        tEntOrder.setCreateTime(DateUtil.date());
        tEntOrder.setUpdateTime(DateUtil.date());
        orderMapper.insert(tEntOrder);

        //保存订单详情
        TEntOrderDetail tEntOrderDetail = new TEntOrderDetail();
        Long detailId = redisIdGeneratorService.createUniqueId(String.valueOf(entId));
        tEntOrderDetail.setId(detailId);
        tEntOrderDetail.setAddress("湖北武汉东西湖区");
        tEntOrderDetail.setOrderId(orderId);
        tEntOrderDetail.setEntId(entId);
        tEntOrderDetail.setStatus(Byte.valueOf("1"));
        tEntOrderDetail.setRegionCode(regionCode);

        tEntOrderDetail.setCreateTime(DateUtil.date());
        tEntOrderDetail.setUpdateTime(DateUtil.date());
        orderDetailMapper.insert(tEntOrderDetail);

        //保存订单条目表
            //保存条目 1
        TEntOrderItem item1 = new TEntOrderItem();
        Long itemId = redisIdGeneratorService.createUniqueId(String.valueOf(entId));
        item1.setId(itemId);
        item1.setEntId(entId);
        item1.setOrderId(orderId);
        item1.setRegionCode("BG");
        item1.setGoodId("aaaaaaaaaaaa");
        item1.setGoodName("我的商品111111");

        item1.setCreateTime(DateUtil.date());
        item1.setUpdateTime(DateUtil.date());
        orderItemMapper.insert(item1);
        //保存条目 2
        TEntOrderItem item2 = new TEntOrderItem();
        Long itemId2 = redisIdGeneratorService.createUniqueId(String.valueOf(entId));
        item2.setId(itemId2);
        item2.setEntId(entId);
        item2.setRegionCode("BJ");
        item2.setOrderId(orderId);
        item2.setGoodId("bbbbbbbbbbbb");
        item2.setGoodName("我的商品22222");

        item2.setCreateTime(DateUtil.date());
        item2.setUpdateTime(DateUtil.date());
        orderItemMapper.insert(item2);
        return tEntOrder;
    }


//    public TEntOrder updateById(Point point) {
//        orderMapper.updateByPrimaryKeySelective(point);
//        return orderMapper.selectByPrimaryKey(point.getId());
//    }
//    public TEntOrder selectById(Integer id) {
//        return orderMapper.selectByPrimaryKey(id);
//    }


    public PageInfo<TEntOrder> selectByPage(TEntOrder order, int pageNum){
        TEntOrderExample example = new TEntOrderExample();
        example.createCriteria().andAmountGreaterThan(order.getAmount());
        PageHelper.startPage(pageNum, 5);
        List<TEntOrder> list = orderMapper.selectByExample(example);
        PageInfo<TEntOrder> result = new PageInfo<>(list,5);
        return result;
    }

    public TEntOrder queryOrder(Long valueOf) {
        TEntOrderExample example = new TEntOrderExample();
        example.createCriteria().andIdEqualTo(valueOf);

        return  orderMapper.selectByExample(example).get(0);
    }
}
