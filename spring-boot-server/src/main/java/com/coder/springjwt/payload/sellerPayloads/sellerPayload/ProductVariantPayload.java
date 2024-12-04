package com.coder.springjwt.payload.sellerPayloads.sellerPayload;

import com.coder.springjwt.models.sellerModels.sellerStore.ProductSizes;
import jakarta.persistence.CascadeType;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Data
@ToString
public class ProductVariantPayload {
    private Long id;

    private String productName;

    private String productSubTitle;

    private String productFrontFile;

    private String productThumbnail;

    private String file_1;

    private String file_2;

    private String file_3;

    private String file_4;

    private String productStatus;

    private String gst;

    private String hsnCode;

    private String size;

    private String weight;

    private String styleCode;

    private String netQuantity;

    private String productLength;

    private String productBreath;

    private String productHeight;

    private String material;

    private String productType;

    private String color;

    private String manufactureDetails;

    private String packerDetails;

    private String tags;

    private String description;

    private String sku;

    private String searchKey;

    private String sellActualPrice;

    private String defectiveReturnPrice;

    private String mrp;

    private String inventory;

    private String productDate;

    private String productTime;

    //Action Status Data for submit Catalog
    private String actionStatus;

    private String actionDebug;

    private String other;

    private List<ProductSizesPayload> productSizesPayloads = new ArrayList<>();
}
