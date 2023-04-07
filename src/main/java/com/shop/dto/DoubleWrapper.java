package com.shop.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@AllArgsConstructor
@RequiredArgsConstructor
@Getter
public class DoubleWrapper{
    FirmShortDto first;
    FirmShortDto second;
}
