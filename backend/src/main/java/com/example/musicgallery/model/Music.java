package com.example.musicgallery.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * <p>
 * Music entity
 * </p>
 *
 * @author Lin Pang
 * @since 2023-02-17
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class Music extends Model {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @NotEmpty(message = "Title cannot be empty")
    private String title;

    private String genre;

    @NotNull(message = "Duration cannot be null")
    private Integer duration;

    @NotNull(message = "ArtistId cannot be null")
    private Integer artistId;

    @NotNull(message = "AlbumId cannot be null")
    private Integer albumId;


}
