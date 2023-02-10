package cn.whu.wy.osgov.service.qax.bean;

import lombok.Data;

import java.util.List;

/**
 * @Author WangYong
 * @Date 2022/09/27
 * @Time 19:17
 */
@Data
public class Project {
    String projectId;
    String projectName;
    Integer businessType;
    List<TaskInfo> taskInfos;

    @Data
    public static class TaskInfo {
        int taskId;
        String taskName;
        String taskBeginTime;
        String taskEndTime;

    }
}
