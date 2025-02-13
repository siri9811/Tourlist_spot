package com.example.Tourlist_spot.service.data;

import lombok.Data;

@Data
public class Body{
    public Items items;
    public int numOfRows;
    public int pageNo;
    public int totalCount;
}
