package com.apuliadigitalmaker.gestionalespese.account;

import com.apuliadigitalmaker.gestionalespese.earning.Earning;
import com.apuliadigitalmaker.gestionalespese.expense.Expense;
import com.apuliadigitalmaker.gestionalespese.user.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import org.hibernate.annotations.ColumnDefault;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "accounts",schema = "gestionale_spese")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "account_name",nullable = false)
    private String accountName;

    @ColumnDefault("0.00")
    @Column(name = "initial_balance",nullable = false)
    private BigDecimal initialBalance;

    @Column(name = "actual_balance",nullable = false)
    private BigDecimal actualBalance;


    @JsonIgnore
    @ColumnDefault("current_timestamp()")
    @Column(name = "created", updatable = false)
    private Instant created;

    @JsonIgnore
    @ColumnDefault("current_timestamp()")
    @Column(name = "updated",nullable = false)
    private Instant updated;

    @JsonIgnore
    @Column(name = "deleted")
    private Instant deleted;

    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "user_id",nullable = false)
    private User user;

    @OneToMany(mappedBy = "account")
    private List<Expense> expenses = new ArrayList<>();

    @OneToMany(mappedBy = "account")
    private List<Earning> earnings = new ArrayList<>();
    

    @PrePersist
    protected void onCreate() {
        actualBalance = initialBalance;
        created = Instant.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updated = Instant.now();
    }

    public void softDelete() {
        this.deleted = Instant.now();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public BigDecimal getActualBalance() {
        return actualBalance;
    }

    public void setActualBalance(BigDecimal actualBalance) {
        this.actualBalance = actualBalance;
    }

    public Instant getCreated() {
        return created;
    }

    public void setCreated(Instant created) {
        this.created = created;
    }

    public BigDecimal getInitialBalance() {
        return initialBalance;
    }

    public void setInitialBalance(BigDecimal initialBalance) {
        this.initialBalance = initialBalance;
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
}
