package cn.whu.wy.osgov.entity;

import lombok.*;

import java.time.LocalDateTime;

/**
 * @Author WangYong
 * @Date 2022/09/23
 * @Time 15:36
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Ask extends Entity {

    private String artifactIds;
    private String project;
    private String username;
    private LocalDateTime commitTime;
    private String status;
}
