package com.huobi.model.trade;

import java.math.BigDecimal;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.huobi.constant.enums.StopOrderOperatorEnum;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Order {

  private Long id;

  private String symbol;

  private Long accountId;

  private BigDecimal amount;

  private BigDecimal price;

  private String type;

  private BigDecimal filledAmount;

  private BigDecimal filledCashAmount;

  private BigDecimal filledFees;

  private String source;

  private String state;

  private Long createdAt;

  private Long canceledAt;

  private Long finishedAt;

  private BigDecimal stopPrice;

  @JSONField(deserialize = false)
  private StopOrderOperatorEnum operator;

  /**
   * insert into persons
   * (id_p, lastname , firstName, city )
   *
   * values
   *
   * (200,'haha' , 'deng' , 'shenzhen'),
   *
   * (201,'haha2' , 'deng' , 'GD'),
   *
   * (202,'haha3' , 'deng' , 'Beijing');
   * @return
   */
  public String genSql(){
    String sql = "(";
    sql += ("\""+this.id+"\"");
    sql += (",\""+this.symbol+"\"");
    sql += (",\""+this.accountId+"\"");
    sql += (",\""+this.amount+"\"");
    sql += (",\""+this.price+"\"");
    sql += (",\""+this.type+"\"");
    sql += (",\""+this.filledAmount+"\"");
    sql += (",\""+this.filledCashAmount+"\"");
    sql += (",\""+this.filledFees+"\"");
    sql += (",\""+this.source+"\"");
    sql += (",\""+this.state+"\"");
    sql += (",\""+this.createdAt+"\"");
    sql += (",\""+this.canceledAt+"\"");
    sql += (",\""+this.finishedAt+"\"");
    sql += (","+(this.stopPrice == null?null:"0.0"));
    sql += ")";

    return sql;
  }

}
