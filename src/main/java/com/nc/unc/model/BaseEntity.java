package com.nc.unc.model;

import com.nc.unc.dao.annotation.*;
import com.nc.unc.dao.annotation.enums.EnumType;
import com.nc.unc.model.enums.Status;
import lombok.Data;

import java.util.Date;

@Data
@MappedSuperclass
public class BaseEntity {
    @PrimaryKey("id")
    private Long id;

    @Attribute("created")
    @CreatedDate
    private Date created;

    @Attribute("updated")
    @UpdatedDate
    private Date updated;

    @Attribute("status")
    @Enumerated(value = EnumType.STRING)
    private Status status;
}
