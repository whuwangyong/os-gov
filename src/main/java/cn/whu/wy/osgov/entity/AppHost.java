package cn.whu.wy.osgov.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

/**
 * @author WangYong
 * Date 2022/08/24
 * Time 19:47
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AppHost extends Entity {

    private int appId;
    private int hostId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AppHost appHost = (AppHost) o;
        return appId == appHost.appId && hostId == appHost.hostId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(appId, hostId);
    }
}
