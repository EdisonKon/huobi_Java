package com.huobi.model.trade;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author dekai.kong
 * @difficult
 * @create 2021-05-10 20:18
 * @from
 **/
@Data
public class CostEntity {
    private String symbol;
    private BigDecimal cost;
    private BigDecimal nowprice;
    private BigDecimal has;
    private BigDecimal price;
    private BigDecimal curprice;
    private BigDecimal getmoney;
    private String donull;


}
