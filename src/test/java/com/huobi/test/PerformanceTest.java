package com.huobi.test;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import com.alibaba.fastjson.JSON;
import com.huobi.Constants;
import com.huobi.client.AccountClient;
import com.huobi.client.GenericClient;
import com.huobi.client.MarketClient;
import com.huobi.client.TradeClient;
import com.huobi.client.WalletClient;
import com.huobi.client.req.account.AccountBalanceRequest;
import com.huobi.client.req.account.AccountHistoryRequest;
import com.huobi.client.req.generic.CurrencyChainsRequest;
import com.huobi.client.req.market.CandlestickRequest;
import com.huobi.client.req.market.MarketDepthRequest;
import com.huobi.client.req.market.MarketDetailRequest;
import com.huobi.client.req.market.MarketTradeRequest;
import com.huobi.client.req.trade.CreateOrderRequest;
import com.huobi.client.req.trade.FeeRateRequest;
import com.huobi.client.req.trade.OrdersRequest;
import com.huobi.client.req.wallet.DepositAddressRequest;
import com.huobi.client.req.wallet.WithdrawAddressRequest;
import com.huobi.constant.HuobiOptions;
import com.huobi.constant.enums.*;
import com.huobi.model.account.AccountBalance;
import com.huobi.model.account.AccountHistory;
import com.huobi.model.account.Balance;
import com.huobi.model.generic.Symbol;
import com.huobi.model.market.Candlestick;
import com.huobi.model.trade.CostEntity;
import com.huobi.model.trade.Order;
import com.huobi.utils.ConnectionFactory;
import com.huobi.utils.ConnectionFactory.NetworkLatency;
import com.huobi.utils.ConsoleTable;
import com.huobi.utils.contable.enums.Align;
import com.huobi.utils.contable.table.Cell;
import org.junit.Test;

public class PerformanceTest {


  /**
   * 自选(需要手动增加,没接口)
   */
  static List<String> symbols = new ArrayList<>();
  static List<String> keys = new ArrayList<>();
  static {
    symbols.add("shibusdt");
    symbols.add("eos3lusdt");
    symbols.add("qunbtc");
    symbols.add("lhbusdt");
    symbols.add("dotusdt");
    symbols.add("dacusdt");
    symbols.add("enjusdt");
    symbols.add("htusdt");
    symbols.add("btcusdt");
    symbols.add("ethusdt");
    symbols.add("uniusdt");
    symbols.add("dogeusdt");
    //2021-5.11
    symbols.add("mtnbtc");
    symbols.add("achusdt");
    //2021-5.12
    symbols.add("pntbtc");
    symbols.add("csprusdt");
    //2021-5-13
    symbols.add("actusdt");
    symbols.add("bftbtc");
  }
  static{
    keys.add("usdt");
    keys.add("btc");
  }

  public static void main(String[] args) throws ParseException {

    ConnectionFactory.setLatencyDebug();

    for (int i = 0; i < 1; i++) {

      testCase();
      System.out.println("======================================");
    }

  }


  public static void testCase() throws ParseException {

    String symbol = "dogeusdt";
    String currency = "usdt";
    Long accountId = 22097408L;

    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    SimpleDateFormat formattertime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    GenericClient genericClient = GenericClient.create(new HuobiOptions());
    MarketClient marketClient = MarketClient.create(new HuobiOptions());
    AccountClient accountClient = AccountClient.create(HuobiOptions.builder().apiKey(Constants.API_KEY).secretKey(Constants.SECRET_KEY).build());
    WalletClient walletClient = WalletClient.create(HuobiOptions.builder().apiKey(Constants.API_KEY).secretKey(Constants.SECRET_KEY).build());
    TradeClient tradeClient = TradeClient.create(HuobiOptions.builder().apiKey(Constants.API_KEY).secretKey(Constants.SECRET_KEY).build());
    Long startNano = null;
    Long endNano = null;
    NetworkLatency networkLatency = null;
//
//    // /v1/common/timestamp
//    startNano = System.nanoTime();
//    genericClient.getTimestamp();
//    endNano = System.nanoTime();
//    networkLatency = ConnectionFactory.getLatencyDebugQueue().poll();
//    print(networkLatency, startNano, endNano);
//
//    // currencies
//    startNano = System.nanoTime();
//    genericClient.getCurrencyChains(CurrencyChainsRequest.builder().currency(currency).build());
//    endNano = System.nanoTime();
//    networkLatency = ConnectionFactory.getLatencyDebugQueue().poll();
//    print(networkLatency, startNano, endNano);
//
//
//    // /market/trade
//    startNano = System.nanoTime();
//    marketClient.getMarketTrade(MarketTradeRequest.builder().symbol(symbol).build());
//    endNano = System.nanoTime();
//    networkLatency = ConnectionFactory.getLatencyDebugQueue().poll();
//    print(networkLatency, startNano, endNano);
//
//    // /market/depth
//    startNano = System.nanoTime();
//    marketClient.getMarketDepth(MarketDepthRequest.builder().symbol(symbol).step(DepthStepEnum.STEP0).depth(DepthSizeEnum.SIZE_5).build());
//    endNano = System.nanoTime();
//    networkLatency = ConnectionFactory.getLatencyDebugQueue().poll();
//    print(networkLatency, startNano, endNano);
//
//    // /market/kline
//    startNano = System.nanoTime();
//    marketClient.getCandlestick(CandlestickRequest.builder().symbol(symbol).interval(CandlestickIntervalEnum.MIN15).size(5).build());
//    endNano = System.nanoTime();
//    networkLatency = ConnectionFactory.getLatencyDebugQueue().poll();
//    print(networkLatency, startNano, endNano);
//
//    // /market/detail
//    startNano = System.nanoTime();
//    marketClient.getMarketDetail(MarketDetailRequest.builder().symbol(symbol).build());
//    endNano = System.nanoTime();
//    networkLatency = ConnectionFactory.getLatencyDebugQueue().poll();
//    print(networkLatency, startNano, endNano);

    // /v1/account/accounts
//    startNano = System.nanoTime();
//    accountClient.getAccounts();
//    endNano = System.nanoTime();
//    networkLatency = ConnectionFactory.getLatencyDebugQueue().poll();
//    print(networkLatency, startNano, endNano);

    // /v1/account/accounts/{account-id}/balance
    /**
     * 账户信息
     */
//    startNano = System.nanoTime();
//    AccountBalance accountBalance = accountClient.getAccountBalance(AccountBalanceRequest.builder().accountId(accountId).build());
//    endNano = System.nanoTime();
//    networkLatency = ConnectionFactory.getLatencyDebugQueue().poll();
//    print(networkLatency, startNano, endNano);
//    List<Balance> trueBal = new ArrayList<>();
//    for (int i = 0; i < accountBalance.getList().size(); i++) {
//      if(accountBalance.getList().get(i).getBalance().doubleValue()>0){
//        trueBal.add(accountBalance.getList().get(i));
//      }
//    }
//    System.out.println("accountBalance:"+ JSON.toJSONString(trueBal));

//    List<AccountHistory> historyList = accountClient.getAccountHistory(
//            AccountHistoryRequest.builder()
//                    .accountId(accountId)
//                    .startTime(formattertime.parse("2021-05-09 12:00:00").getTime())
//                    .endTime(formattertime.parse("2021-05-09 12:59:00").getTime())
//                    .build());
//    historyList.forEach(history->{
//      System.out.println(history);
//    });

    /**
     * 交易对获取
     */
//    List<Symbol> symbolList = genericClient.getSymbols();
//    symbolList.forEach(symb -> {
//      System.out.println(symb.toString());
//    });

    /**
     * 交易信息
     */
    List<OrderStateEnum> stateList = new ArrayList<>();
    stateList.add(OrderStateEnum.CANCELED);
    stateList.add(OrderStateEnum.FILLED);

    List<OrderTypeEnum> typeList = new ArrayList<>();
    typeList.add(OrderTypeEnum.BUY_LIMIT);
    typeList.add(OrderTypeEnum.SELL_LIMIT);
    typeList.add(OrderTypeEnum.BUY_MARKET);
    typeList.add(OrderTypeEnum.SELL_MARKET);

    boolean needExec = false;

    String sql = "INSERT INTO huobi.horder (id, symbol, accountId, amount, price, `type`, filledAmount, filledCashAmount, filledFees, source, state, createdAt, canceledAt, finishedAt, stopPrice) VALUES";



    Date startdate = null;
    Date enddate = new Date();
    try {
      startdate = formatter.parse("2021-05-08 00:00:00");
      enddate = formatter.parse("2021-05-10 00:00:00");
    } catch (ParseException e) {
      e.printStackTrace();
    }

    float dis = differentDaysByMillisecond(enddate,startdate);
//    List<String>
//    for (int i = 0; i < dis; i++) {
//
//    }

    List<Order> ordersList = tradeClient.getOrders(OrdersRequest.builder()
            .symbol(symbol)
//            .startDate(startdate)
//            .endDate(enddate)
            .startTime(startdate)
            .endTime(enddate)
            .states(stateList)
            .build());

        List<String> orderSql = new ArrayList<>();
    ordersList.forEach(order -> {
      System.out.println(formattertime.format(new Date(order.getCreatedAt())) + ":" + order.toString());
      orderSql.add(order.genSql());
    });
    if (orderSql.size()>0){
      needExec = true;
      for (int i = 0; i < orderSql.size(); i++) {
        sql += (orderSql.get(i)+"\n");
      }
    }


    if(needExec){
      System.out.println("待插入数据sql:"+sql);
      MysqlExec.doSql(sql);
    }

//    // /v2/account/deposit/address
//    startNano = System.nanoTime();
//    walletClient.getDepositAddress(DepositAddressRequest.builder().currency(currency).build());
//    endNano = System.nanoTime();
//    networkLatency = ConnectionFactory.getLatencyDebugQueue().poll();
//    print(networkLatency, startNano, endNano);
//
//    // /v2/account/withdraw/address
//    startNano = System.nanoTime();
//    walletClient.getWithdrawAddress(WithdrawAddressRequest.builder().currency(currency).build());
//    endNano = System.nanoTime();
//    networkLatency = ConnectionFactory.getLatencyDebugQueue().poll();
//    print(networkLatency, startNano, endNano);
//
//    // 下单
//    startNano = System.nanoTime();
//    Long orderId = tradeClient.createOrder(CreateOrderRequest.spotBuyLimit(accountId, symbol, new BigDecimal("3"), new BigDecimal("2")));
//    endNano = System.nanoTime();
//    networkLatency = ConnectionFactory.getLatencyDebugQueue().poll();
//    print(networkLatency, startNano, endNano);
//
//    // 撤单
//    startNano = System.nanoTime();
//    tradeClient.cancelOrder(orderId);
//    endNano = System.nanoTime();
//    networkLatency = ConnectionFactory.getLatencyDebugQueue().poll();
//    print(networkLatency, startNano, endNano);

    // 查询订单
//    startNano = System.nanoTime();
//    tradeClient.getOrder(orderId);
//    endNano = System.nanoTime();
//    networkLatency = ConnectionFactory.getLatencyDebugQueue().poll();
//    print(networkLatency, startNano, endNano);
//
//    // 查询成交明细
//    startNano = System.nanoTime();
//    tradeClient.getMatchResult(orderId);
//    endNano = System.nanoTime();
//    networkLatency = ConnectionFactory.getLatencyDebugQueue().poll();
//    print(networkLatency, startNano, endNano);
//
//    // 手续费率
//    startNano = System.nanoTime();
//    tradeClient.getFeeRate(FeeRateRequest.builder().symbols(symbol).build());
//    endNano = System.nanoTime();
//    networkLatency = ConnectionFactory.getLatencyDebugQueue().poll();
//    print(networkLatency, startNano, endNano);
  }

  public static float differentDaysByMillisecond(Date date1,Date date2)
  {
    float days = ((date2.getTime() - date1.getTime()) / (1000*3600*24));
    return days;
  }


  /**
   * 获取交易对
   */
  @Test
  public void testGetSymbol(){
    GenericClient genericClient = GenericClient.create(new HuobiOptions());
    List<Symbol> symbolList = genericClient.getSymbols();
    System.out.println(JSON.toJSONString(symbolList));

  }
  /**
   * 获取账户余额(各种支持的币种,usdt btc ht eth 等)
   */
  @Test
  public void testGetAccount(){
    Long accountId = 22097408L;
    AccountClient accountClient = AccountClient.create(HuobiOptions.builder().apiKey(Constants.API_KEY).secretKey(Constants.SECRET_KEY).build());
    AccountBalance accountBalance = accountClient.getAccountBalance(AccountBalanceRequest.builder().accountId(accountId).build());
    List<Balance> trueBal = new ArrayList<>();
    for (int i = 0; i < accountBalance.getList().size(); i++) {
      if(accountBalance.getList().get(i).getBalance().doubleValue()>0){
        trueBal.add(accountBalance.getList().get(i));
      }
    }
    System.out.println("accountBalance:"+ JSON.toJSONString(trueBal));

  }


  /**
   * 获取赚钱数
   * 调用时候更改datex
   */
  static String datex = "2021-05-17 00:00:00";
  @Test
  public void getMoney(){
    //存在新交易
    testGetTrade();
    //获取k线当前1分钟开盘价
    testGetKline();
    //获取结果
    testGetCost();
  }
  @Test
  public void getFuMoney(){
    Double usdt = 0.0;
    usdt = (
            -3.467
                    -11.346
                    -29.635
                    -42.014
                    -72.96
                    -82.35
                    -100.9437
                    -146.350224
            );
    Double btcu = 0.0;
    btcu = (
            -0.0006
                    -0.00219538
                    -0.003861
    )*51018;
    System.out.println(usdt);
    System.out.println(btcu);
    System.out.println(usdt + btcu);
    System.out.println((usdt + btcu) * 6.6);
  }



  /**
   * 查询成本价
   */
  @Test
  public void testGetCost(){
    String sql = "select com.symbol,cost,(h.open*1) as nowprice, has,(cost*has) as price,(h.open*has) as totalprice ,(IF (cost > 0,(h.open*has) - (cost*has),ABS(cost))) as getmoney , IF (cost > 0,'No','Yes') as donull from\n" +
            "(\n" +
            "select buysum.symbol , IF((bpt-IFNULL(spt,0))>0,(bpt-IFNULL(spt,0))/(bc-IFNULL(sc,0)),(bpt-IFNULL(spt,0))) as cost,(bc-IFNULL(sc,0)) as has  from \n" +
            "\n" +
            "(\n" +
            "\tselect symbol,sum(filledAmount) as bc,sum(buytotalprice) as bpt from\n" +
            "\t(\n" +
            "\t\tselect symbol,price,filledAmount,filledAmount * price as buytotalprice\n" +
            "\t\tfrom\n" +
            "\t\thorder\n" +
            "\t\twhere state = 'filled'\n" +
            "\t\tand type = 'buy-limit'\n" +
            "\t\tORDER BY symbol\n" +
            "\t) buy\n" +
            "\tgroup by symbol\n" +
            ") buysum\n" +
            "\n" +
            "LEFT JOIN \n" +
            "(\n" +
            "\tselect symbol,sum(filledAmount) as sc,sum(selltotalprice) as spt from\n" +
            "\t(\n" +
            "\t\tselect symbol,price,filledAmount,filledAmount * price as selltotalprice\n" +
            "\t\tfrom\n" +
            "\t\thorder\n" +
            "\t\twhere state = 'filled'\n" +
            "\t\tand type = 'sell-limit'\n" +
            "\t\tORDER BY symbol\n" +
            "\t) sell\n" +
            "\tgroup by symbol\n" +
            ") sellsum\n" +
            "\n" +
            "ON buysum.symbol = sellsum.symbol\n" +
            "order by buysum.symbol\n" +
            ")\n" +
            "com\n" +
            "LEFT JOIN \n" +
            "hkline h \n" +
            "on com.symbol = h.symbol \n" +
            "\n" +
            "ORDER by donull desc ,getmoney desc";

    String json = MysqlExec.doSql(sql,CostEntity.class);
    MysqlExec.closeConn();
    System.out.println("=======================================================");
    Field[] fields = CostEntity.class.getDeclaredFields();
    //表格的header
    List<Cell> header = new ArrayList<Cell>(){{
      for (int i = 0; i < fields.length; i++) {
        add(new Cell(fields[i].getName()));
      }
    }};

    SimpleDateFormat formattertime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    System.out.println("当前时间:"+formattertime.format(new Date()));
    List<CostEntity> list = JSON.parseArray(json,CostEntity.class);
    //表格的内容
    List<List<Cell>> body = new ArrayList<List<Cell>>();

    Map<String,List<BigDecimal>> map = new HashMap<>();
    for (int i = 0; i < keys.size(); i++) {
      List<BigDecimal> totals = new ArrayList<>();
      map.put(keys.get(i),totals);
    }

    final BigDecimal[] btcusdt = {new BigDecimal(0),new BigDecimal(0)};

    list.forEach(costEntity -> {
      if ("btcusdt".equals(costEntity.getSymbol())){
        btcusdt[0] = btcusdt[0].add(costEntity.getNowprice());
        btcusdt[1] = btcusdt[1].add(costEntity.getCost());
      }else{
        List<Cell> cells = new ArrayList<Cell>();
        Object object=null;
        for (Field field: fields) {
          field.setAccessible(true);//可以获取到私有属性
          try {
            object=field.get(costEntity);
          } catch (IllegalAccessException e) {
            e.printStackTrace();
          }
          cells.add(new Cell(mappingString(object)));
        }
        body.add(cells);
        if(!"btcusdt".equals(costEntity.getSymbol())){
          double limit = 0.1;
          if(costEntity.getSymbol().contains("btc")){
            limit = 500;
          }
          System.out.println(costEntity.getSymbol()+"->赚了: "+mappingString(costEntity.getGetmoney())+("Yes".equals(costEntity.getDonull())?costEntity.getHas().doubleValue()>limit?"->净赚":"->清仓 ":""));
        }
        for (int i = 0; i < keys.size(); i++) {
          if(costEntity.getSymbol().endsWith(keys.get(i))){
            map.get(keys.get(i)).add(costEntity.getGetmoney());
          }
        }
      }
    });
    System.out.println("=======================================================");
    System.out.println("btcusdt成本价:"+mappingString(btcusdt[1]));
    System.out.println("=======================================================");
    //负数总数
    final BigDecimal[] bigfuusdt = {new BigDecimal(0)};
    final BigDecimal[] bigfubtc = {new BigDecimal(0)};
    map.forEach((k, v) -> {
              for (int i = 0; i < v.size(); i++) {
                if(v.get(i).doubleValue()<0){
                  if("btc".equals(k)){
                    bigfubtc[0] = bigfubtc[0].add(v.get(i));
                  }else if("usdt".equals(k)){
                    bigfuusdt[0] = bigfuusdt[0].add(v.get(i));
                  }
                }
              }
              BigDecimal reduce = v.stream().reduce(BigDecimal.ZERO, BigDecimal::add);
              System.out.println(k+"-->共赚-->"+mappingString(reduce));
//              if("btc".equals(k)){
//                reduce = reduce.multiply(btcusdt[0].multiply(new BigDecimal(6.6))).setScale(10,BigDecimal.ROUND_HALF_UP);
//              }else{
//                reduce = reduce.multiply(new BigDecimal(6.6)).setScale(10,BigDecimal.ROUND_HALF_UP);
//              }
//              System.out.println(k+"-->共赚rmb-->"+mappingString(reduce));
            }
            );
//    System.out.println(map.get("usdt"));

    System.out.println("=======================================================");
    System.out.println("当前亏损中->btc 有关-亏损:"+ bigfubtc[0].multiply(btcusdt[0]).setScale(10,BigDecimal.ROUND_HALF_UP));
    System.out.println("当前亏损中->usdt有关-亏损:"+ bigfuusdt[0].setScale(10,BigDecimal.ROUND_HALF_UP));
    System.out.println("=======================================================");
    new ConsoleTable.ConsoleTableBuilder().addHeaders(header).addRows(body).build().print();

  }

  /**
   * 截取000000
   * @param object
   * @return
   */
  public String mappingString(Object object){
    String val = object+"";
    if("Yes".equals(val)||"No".equals(val)){
      return val;
    }
    for (int i = val.length()-1; i >0 ; i--) {
      if(val.charAt(i)!='0'){
        if(val.charAt(i)=='.'){
          val = (val.substring(0,i+2));
        }else{
          val = (val.substring(0,i+1));
        }
        break;
      }
    }
    return val;
  }
  @Test
  public void testGetKline(){
    for (int i = 0; i < symbols.size(); i++) {
      getKline(symbols.get(i));
    }
  }

  public void getKline(String symbol){
    MarketClient marketClient = MarketClient.create(new HuobiOptions());
    List<Candlestick> list = marketClient.getCandlestick(CandlestickRequest.builder()
            .symbol(symbol)
            .interval(CandlestickIntervalEnum.MIN1)
            .size(1)
            .build());
    list.forEach(candlestick -> {
      candlestick.setSymbol(symbol);
      System.out.println(candlestick.toString());
      MysqlExec.doSql("REPLACE INTO huobi.hkline\n" +
              "(id, symbol, amount, count, `open`, high, low, `close`, vol)\n" +
              "VALUES("
              +candlestick.getId()+","
              +"'"+candlestick.getSymbol()+"',"
              +candlestick.getAmount()+","
              +candlestick.getCount()+","
              +candlestick.getOpen()+","
              +candlestick.getHigh()+","
              +candlestick.getLow()+","
              +candlestick.getClose()+","
              +candlestick.getVol()+""+
              ");\n");
    });
    MysqlExec.closeConn();
    try {
      Thread.sleep(300);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }
  /**
   * 获取交易信息
   */
  @Test
  public void testGetTrade(){
    /**
     * 交易信息
     */
    TradeClient tradeClient = TradeClient.create(HuobiOptions.builder().apiKey(Constants.API_KEY).secretKey(Constants.SECRET_KEY).build());
    String startDatex = datex;


    List<OrderStateEnum> stateList = new ArrayList<>();
    stateList.add(OrderStateEnum.CANCELED);
    stateList.add(OrderStateEnum.FILLED);

    List<OrderTypeEnum> typeList = new ArrayList<>();
    typeList.add(OrderTypeEnum.BUY_LIMIT);
    typeList.add(OrderTypeEnum.SELL_LIMIT);
    typeList.add(OrderTypeEnum.BUY_MARKET);
    typeList.add(OrderTypeEnum.SELL_MARKET);

    boolean needExec = false;

    String sqlbase = "REPLACE INTO huobi.horder (id, symbol, accountId, amount, price, `type`, filledAmount, filledCashAmount, filledFees, source, state, createdAt, canceledAt, finishedAt, stopPrice) VALUES\n";

    String sql = sqlbase;
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    SimpleDateFormat formattertime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    Date startdate = null;
    Date enddate = new Date();
    try {
      startdate = formatter.parse(startDatex);
//      enddate = formatter.parse("2021-05-10 00:00:00");
    } catch (ParseException e) {
      e.printStackTrace();
    }

    List<Date> dateList = findDates(startdate, enddate);
    for (int i = 0; i < dateList.size()-1; i++) {
      startdate = dateList.get(i);
      enddate = dateList.get(i+1);

      String symbol = "";
      for (int isy = 0; isy < symbols.size(); isy++) {
        symbol = symbols.get(isy);
        List<Order> ordersList = tradeClient.getOrders(OrdersRequest.builder()
                .symbol(symbol)
//            .startDate(startdate)
//            .endDate(enddate)
                .startTime(startdate)
                .endTime(enddate)
                .states(stateList)
                .build());

        List<String> orderSql = new ArrayList<>();
        ordersList.forEach(order -> {
          System.out.println(formattertime.format(new Date(order.getCreatedAt())) + ":" + order.toString());
          orderSql.add(order.genSql());
        });
        if (orderSql.size()>0){
          needExec = true;
          for (int ix = 0; ix < orderSql.size(); ix++) {
            sql += (orderSql.get(ix)+"\n");
            if(ix<orderSql.size()-1){
              sql+=",";
            }
          }
        }
        if(needExec){
          System.out.println("待插入数据sql:"+sql);
          MysqlExec.doSql(sql);
          sql = sqlbase;
          needExec = false;
        }
        try {
          Thread.sleep(1000);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }

    }

    MysqlExec.closeConn();
  }




  /**
   * 获取某段时这里写代码片间内的所有日期
   * @param dBegin
   * @param dEnd
   * @return
   */
  public static List<Date> findDates(Date dBegin, Date dEnd) {
    List<Date> lDate = new ArrayList<Date>();
    lDate.add(dBegin);
    Calendar calBegin = Calendar.getInstance();
    // 使用给定的 Date 设置此 Calendar 的时间
    calBegin.setTime(dBegin);
    Calendar calEnd = Calendar.getInstance();
    // 使用给定的 Date 设置此 Calendar 的时间
    calEnd.setTime(dEnd);
    // 测试此日期是否在指定日期之后
    while (dEnd.after(calBegin.getTime()))  {
      // 根据日历的规则，为给定的日历字段添加或减去指定的时间量
      calBegin.add(Calendar.DAY_OF_MONTH, 1);
      lDate.add(calBegin.getTime());
    }
    return lDate;
  }



  public static void print(NetworkLatency networkLatency, Long startNanoTime, Long endNanoTime) {

    long nanoToMicrosecond = 1000;

    Long prepareCost = (networkLatency.getStartNanoTime() - startNanoTime) / nanoToMicrosecond;
    Long deserializationCost = (endNanoTime - networkLatency.getEndNanoTime()) / nanoToMicrosecond;
    Long networkCost = (networkLatency.getEndNanoTime() - networkLatency.getStartNanoTime()) / nanoToMicrosecond;
    Long totalCost = (endNanoTime - startNanoTime) / nanoToMicrosecond;
    Long innerCost = (totalCost - networkCost);

    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("path:").append(networkLatency.getPath())
        .append(" prepare:").append(prepareCost)
        .append(" deserializtion:").append(deserializationCost)
        .append(" network:").append(networkCost)
        .append(" inner:").append(innerCost)
        .append(" total:").append(totalCost);

//    stringBuilder.append("|").append(networkLatency.getPath())
//        .append(" |").append(prepareCost)
//        .append(" |").append(deserializationCost)
//        .append(" |").append(networkCost)
//        .append(" |").append(innerCost)
//        .append(" |").append(totalCost);

    System.out.println(stringBuilder.toString());


  }

}
