package org.cryptobot;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ChartEntity {
    private String symbol;
    private String priceChange;
    private String priceChangePercent;
    private String weightedAvgPrice;
    private String lastPrice;
    private String lastQty;
    private String openPrice;
    private String highPrice;
    private String lowPrice;
    private String volume;
    private String quoteVolume;
    private Object openTime;
    private Object closeTime;
    private Object firstId;
    private Object lastId;
    private int count;

}
