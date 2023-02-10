package cn.whu.wy.osgov.service.qax.bean;

import lombok.Data;

import java.util.List;
import java.util.Objects;

/**
 * @Author WangYong
 * @Date 2022/09/28
 * @Time 09:43
 */
@Data
public class TaskComponentWithMd5 {
    int pageIndex;
    int pageSize;
    int total;
    List<Component> content;


    @Data
    public static class Component {
        String componentId;
        String componentName;
        String componentVersion;
        int componentLevel;
        String updateDate;
        String componentDescription;
        int componentSourceCount;
        List<SourceInfo> componentSourceInfos;
        String recommendVersion;
        String latestVersion;
        List<String> otherVersion;
        String communityAddress;
        List<LicenseInfo> licenseInfos;
        int vulnerabilityCount;
        List<VulnerabilityInfo> vulnerabilityInfos;
        List<ImportInfo> importInfos;
        int componentType; // 0 开源组件 1 私有组件
        List<String> coordinates;
        String markStatus; // 误报 遗留 白名单 其他
        String markDescription;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Component component = (Component) o;
            return Objects.equals(componentId, component.componentId);
        }

        @Override
        public int hashCode() {
            return Objects.hash(componentId);
        }
    }

    @Data
    public static class SourceInfo {
        String componentPath;
        String md5;
        String coordinate;
        int modify;
    }

    @Data
    public static class LicenseInfo {
        String licenseId;
        String licenseShortName;
        String licenseName;
        int licenseLevel;
        String licenseContent;
        int fsf;
        int osi;
    }

    @Data
    public static class VulnerabilityInfo {
        String vulnerabilityId;
        String qaxOssId;
        int level;
    }

    @Data
    public static class ImportInfo {
        int importType;
        String matchPath;
    }

}
