package com.example.springtestprojectkhudyakov.domain;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(schema = "public", name = "service")
public final class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "ssoid")
    private String ssoid;

    @Column(name = "ts")
    private String ts;

    @Column(name = "grp")
    private String grp;

    @Column(name = "type")
    private String type;

    @Column(name = "subtype")
    private String subtype;

    @Column(name = "url")
    private String url;

    @Column(name = "orgid")
    private String orgid;

    @Column(name = "formid")
    private String formid;

    @Column(name = "code")
    private String code;

    @Column(name = "ltpa")
    private String ltpa;

    @Column(name = "sudirresponse")
    private String sudirresponse;

    @Column(name = "ymdh")
    private String ymdh;
}
