package com.example.springboot03.vo;

import lombok.*;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class FormVo implements Serializable {

    private static final long serialVersionUID = 1L;

    String name;
    Integer result;
    Integer examKey;
}
