package com.kimp.calculatorbot.upbit;

import lombok.*;

@Getter @Setter
@Builder @ToString
public class UpbitCoin {

    private String market;
    private String korean_name;
    private String english_name;

}
