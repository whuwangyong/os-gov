package cn.whu.wy.osgov.entity;

import cn.whu.wy.osgov.service.qax.bean.TaskComponentWithMd5;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

/**
 * @author WangYong
 * Date 2022/08/16
 * Time 20:25
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class License extends Entity {

    private String name;
    private byte risk;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        License license = (License) o;
        return Objects.equals(name, license.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    public static License convertFrom(TaskComponentWithMd5.LicenseInfo qaxLicenseInfo) {
        return new License(qaxLicenseInfo.getLicenseName(), (byte) (16 / qaxLicenseInfo.getLicenseLevel()));
    }
}
