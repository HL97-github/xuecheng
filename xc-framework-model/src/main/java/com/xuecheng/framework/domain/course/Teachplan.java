package com.xuecheng.framework.domain.course;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by admin on 2018/2/7.
 */
@Data
@ToString
@Entity
@Table(name="teachplan")
@GenericGenerator(name = "jpa-uuid", strategy = "uuid")
public class Teachplan implements Serializable {
    private static final long serialVersionUID = -916357110051689485L;
    @Id
    @GeneratedValue(generator = "jpa-uuid")
    @Column(length = 32)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String id;
    //为空不返回该字段给前端，还需yml文件配置
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String pname;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String parentid;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String grade;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String ptype;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String description;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String courseid;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String status;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer orderby;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Double timelength;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String trylearn;

}
