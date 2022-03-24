package com.example.training.shop.customer.models;

import com.example.training.shop.customer.dto.ItemDTO;
import java.math.BigDecimal;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.Setting;

@Setter
@Getter
@NoArgsConstructor
@Document(indexName = "index")
@Setting(settingPath = "static/es-settings.json")
public class Item {

    @Id
    @Field(type = FieldType.Keyword)
    private String code;
    @Field(type = FieldType.Text)
    private String name;
    @Field(type = FieldType.Double)
    private BigDecimal price;
    @Field(type = FieldType.Long)
    private Long quantity;

    public Item(ItemDTO itemDTO) {
        this.code = itemDTO.getCode();
        this.name = itemDTO.getName();
        this.price = itemDTO.getPrice();
        this.quantity = itemDTO.getQuantity();
    }
}
