package com.yang.portal.wms.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Data
@TableName("PRODUCT")
@Document(indexName = "wms_product", createIndex = false)
@Accessors(chain = true)
public class Product extends BaseEntity {
    /**
     * 商品名称
     */
    @Field(name = "NAME", analyzer = "ik_max_word", searchAnalyzer = "ik_max_word", type = FieldType.Text)
    private String name;
    /**
     * 商品编码
     */
    @Field(name = "CODE", type = FieldType.Keyword)
    private String code;

}

