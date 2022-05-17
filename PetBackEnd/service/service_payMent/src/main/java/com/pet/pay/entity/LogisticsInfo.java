package com.pet.pay.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import javax.persistence.Table;
import java.time.Instant;

@Data
@TableName("logistics_info")
public class LogisticsInfo {

    private String order_no;

    private Double locationX;

    private Double locationY;

    private String logisticsStatus;

    private String destination;

    private Instant logisticsTime;

}
