package com.example.musicgallery.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.time.Year;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * <p>
 * Album entity
 * </p>
 *
 * @author Lin Pang
 * @since 2023-02-17
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class Album extends Model {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @NotEmpty(message = "Title cannot be empty")
    private String title;

    private String description;

    @NotNull(message = "ArtistId cannot be null")
    private Integer artistId;

    private String genre;

    private String coverImage;

    @NotNull(message = "Type cannot be null")
    private Integer type;

    @NotNull(message = "Release date cannot be null")
    private Date releaseDate;

    private String platform;

    private String venue;

    private Year year;


}
