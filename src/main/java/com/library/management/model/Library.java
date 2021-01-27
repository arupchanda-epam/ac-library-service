package com.library.management.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name="library")
@Entity
public class Library {

    @Id
    private Long memberid;

    private Long userid;

    private Long bookid;

    public Long getMemberid() {
        return memberid;
    }

    public Long getUserid() {
        return userid;
    }

    public Long getBookid() {
        return bookid;
    }
}
