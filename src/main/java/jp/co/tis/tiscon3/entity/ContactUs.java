package jp.co.tis.tiscon3.entity;

import org.seasar.doma.*;

import javax.annotation.Generated;

@Table(name = "CONTACT_US")
@Entity
public class ContactUs {

    /** 主キー */
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long Id;

    @Column
    String mail;


    @Column
    String Genre;

    @Column
    String content;


}
