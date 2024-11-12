package com.apuliadigitalmaker.gestionalespese.earning;

import com.apuliadigitalmaker.gestionalespese.account.Account;
import com.apuliadigitalmaker.gestionalespese.category.Category;
import jakarta.persistence.*;
import org.hibernate.annotations.ColumnDefault;

import java.math.BigDecimal;
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

    @Column(name = "earning_name",nullable = false)
    private String earningName;

    @Column(name = "amount", nullable = false)
    private BigDecimal amount;

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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Instant getEarningDate() {
        return earningDate;
    }

    public void setEarningDate(Instant earningDate) {
        this.earningDate = earningDate;
    }

    public Instant getCreated() {
        return created;
    }

    public void setCreated(Instant created) {
        this.created = created;
    }

    public Instant getUpdated() {
        return updated;
    }

    public void setUpdated(Instant updated) {
        this.updated = updated;
    }

    public Instant getDeleted() {
        return deleted;
    }

    public void setDeleted(Instant deleted) {
        this.deleted = deleted;
    }

    public String getEarningName() {
        return earningName;
    }

    public void setEarningName(String earningName) {
        this.earningName = earningName;
    }
}
