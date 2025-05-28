package com.jeunesse.pojo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
public class Account {
    private int id;
    private String name;
    private double money;
}
