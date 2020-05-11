package com.ntt.task.task.entity;

import javax.persistence.*;

@Entity
@NamedQuery(name = Task.FIND_ALL, query = "SELECT t FROM Task t")
@SequenceGenerator(name = "TaskIdGenerator", allocationSize = 5, sequenceName = "sq_task_id")
public class Task {

    public static final String FIND_ALL = "";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TaskIdGenerator")
    private Long id;
    @Column
    private String description;

    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }
}
