package com.coder.springjwt.payload.emailPayloads;


import com.coder.springjwt.models.entities.baseEntity.BaseEntity;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "emailSendContent")
public class EmailSendContent extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String user;

    private String role;

    private String content;

    private String requestJson;

    private String responseJson;

    private String mailArea;

    private String status;

    private String mailFrom;

    private String mailTo;

}
