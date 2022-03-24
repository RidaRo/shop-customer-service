package com.example.training.shop.customer.feign;

import com.example.training.shop.customer.dto.ItemDTO;
import com.example.training.shop.customer.dto.ItemDTOList;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "${keeper.service.name}", url = "${keeper.service.url}")
public interface KeeperClient {
    @GetMapping
    ItemDTOList getItemList();

    @GetMapping("/{itemCode}")
    ItemDTO getItemByCode(@PathVariable(value = "itemCode") String code);
}
