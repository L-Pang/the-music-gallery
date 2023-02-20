package com.example.musicgallery.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotEmpty;

/**
 * <p>
 * Artist entity
 * </p>
 *
 * @author Lin Pang
 * @since 2023-02-17
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class Artist extends Model {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @NotEmpty(message = "Name cannot be empty")
    private String name;

    private String country;

    private String description;


}
