package com.sxia.city.entity;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 
 * </p>
 *
 * @author sxia
 * @since 2023-07-23
 */
@Getter
@Setter
@TableName("city_cn")
public class CityCn implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    @ExcelIgnore
    private Integer id;

    /**
     * 城市名称
     */
    @TableField("name")
    @ExcelProperty("城市名称")
    private String name;

    /**
     * 国家编码
     */
    @TableField("country_code")
    @ExcelProperty("国家编码")
    private String countryCode;

    /**
     * 地区
     */
    @TableField("district")
    @ExcelProperty("地区")
    private String district;

    /**
     * 人口
     */
    @TableField("population")
    @ExcelProperty("人口")
    private Integer population;
}
