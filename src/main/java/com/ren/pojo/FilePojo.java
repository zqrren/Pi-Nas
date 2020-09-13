package com.ren.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FilePojo {
    private boolean isDir;
    private String path;
    private String name;
    private long size;
    private Date date;
}
