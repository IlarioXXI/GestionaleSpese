package com.apuliadigitalmaker.gestionalespese.earning;

import com.apuliadigitalmaker.gestionalespese.account.Account;
import com.apuliadigitalmaker.gestionalespese.category.Category;
import jakarta.persistence.*;
import org.hibernate.annotations.ColumnDefault;
import java.time.Instant;


@Entity
@Table(name = "earnings",schema = "gestionale_spese")
public class Earning {
    @Id
    @GeneratedValue  (strategy = GenerationType.IDENTITY)
    @Column( name = "id", nullable = false)
    private Integer id;


    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "account_id",nullable = false)
    private Account account;


    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "category_id",nullable = false)
    private Category category;
    

    @Column(name = "amount", nullable = false)
    private Double amount;

    @Column(name = "earning_date", nullable = false)
    private Instant earningDate;

    @ColumnDefault("current_timestamp()")
    @Column(name = "created", nullable = false)
    private Instant created;

    @ColumnDefault("current_timestamp()")
    @Column(name = "updated")
    private Instant updated;

    @Column(name = "deleted")
    private Instant deleted;

}
