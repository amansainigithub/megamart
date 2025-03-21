package com.coder.springjwt.payload.sellerPayloads;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ProductReviewPayload {

    private String id;

    private String reviewDecisionId;
}
