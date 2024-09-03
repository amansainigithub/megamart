package com.coder.springjwt.models.props;

import com.coder.springjwt.models.entities.baseEntity.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "OtpRequestResponse")
@ToString
public class OtpRequestResponse extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    private String request;

    private String response;

    private String status;

    private String statusCode;

    private String userRole;

    private String mobileNumber;

    private String message;


}
