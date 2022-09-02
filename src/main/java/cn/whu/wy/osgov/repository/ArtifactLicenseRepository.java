package cn.whu.wy.osgov.repository;

import cn.whu.wy.osgov.entity.ArtifactLicense;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

/**
 * @author WangYong
 * Date 2022/08/24
 * Time 19:50
 */
@Repository
public class ArtifactLicenseRepository extends BaseRepository<ArtifactLicense> {

    public ArtifactLicenseRepository(DataSource dataSource) {
        super(dataSource, ArtifactLicense.class);
    }

    @Override
    String tableName() {
        return "artifact_license";
    }
}
