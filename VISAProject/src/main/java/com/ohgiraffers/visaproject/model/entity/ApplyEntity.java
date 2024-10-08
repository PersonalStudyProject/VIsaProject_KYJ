package com.ohgiraffers.visaproject.model.entity;

import lombok.*;
import jakarta.persistence.*;
import java.util.Date;

@Table(name="apply")
@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ApplyEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "apply_no")
    private Long applyNo;

//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="applicant")
    private String applicant;

    @Column(name="document")
    public String document;

    @Column(name ="reason")
    public String reason;

    @Column(name="apply_Date")
    public Date applyDate;

}
