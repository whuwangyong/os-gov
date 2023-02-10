package cn.whu.wy.osgov.entity.qax;

import cn.whu.wy.osgov.entity.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

/**
 * @Author WangYong
 * @Date 2022/09/28
 * @Time 17:30
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProjectTask extends Entity {

    String projectId;
    String projectName;
    int taskId;
    String taskName;
    String taskBeginTime;
    String taskEndTime;
    // 该task的漏洞是否导入
    boolean vulnerabilityImported;
    // 该task的组件、协议是否导入
    boolean componentAndLicenseImported;
    // 该task的组件-漏洞关系是否导入
    boolean componentVulRelationshipImported;
    // 该task的组件-协议关系是否导入
    boolean componentLicenseRelationshipImported;
    // 该task的组件-应用关系是否导入
    boolean componentProjectRelationshipImported;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProjectTask that = (ProjectTask) o;
        return taskId == that.taskId &&
                Objects.equals(projectId, that.projectId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(projectId, taskId);
    }
}
