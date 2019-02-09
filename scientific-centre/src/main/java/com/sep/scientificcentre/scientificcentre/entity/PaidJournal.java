package com.sep.scientificcentre.scientificcentre.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
@Entity
@Builder
public class PaidJournal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Journal journal;

    @Column
    private Date activityDate;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PaidJournal that = (PaidJournal) o;
        return Objects.equals(journal.getId(), that.journal.getId());
    }


}
