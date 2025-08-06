package org.example.book_my_show.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Date;

@MappedSuperclass
@Getter
@Setter
@EntityListeners({AuditingEntityListener.class}) // missed
public class BaseModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @CreatedDate
//    @Temporal(TemporalType.TIME) missed
    private Date createdAt;
    @LastModifiedDate
//    @Temporal(TemporalType.TIME) missed
    private Date lastUpdatedAt;
}
