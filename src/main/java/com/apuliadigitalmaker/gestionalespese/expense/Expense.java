package com.apuliadigitalmaker.gestionalespese.expense;

import com.apuliadigitalmaker.gestionalespese.account.Account;
import com.apuliadigitalmaker.gestionalespese.category.Category;
import jakarta.persistence.*;
import org.hibernate.annotations.ColumnDefault;

import java.time.Instant;

@Entity
@Table(name= "expenses", schema = "gestionale_spese")
public class Expense {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "account_id",nullable = false)
    private Account account;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "category_id",nullable = false)
    private Category category;

    @Column(name= "amount", nullable = false)
    private Double amount;

    @Column(name= "expanse_date", nullable = false)
    private Instant expanseDate;

    @ColumnDefault("current_timestamp()")
    @Column(name = "created", nullable = false)
    private Instant created;

    @ColumnDefault("current_timestamp()")
    @Column(name = "updated")
    private Instant updated;

    @Column(name = "deleted")
    private Instant deleted;
}
