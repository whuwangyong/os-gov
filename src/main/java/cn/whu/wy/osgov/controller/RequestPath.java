package cn.whu.wy.osgov.controller;

/**
 * @author WangYong
 * Date 2022/08/23
 * Time 15:34
 */
public class RequestPath {

    private static final String root = "/api/";

    public static final String ARTIFACT = root + "artifacts";
    public static final String VULNERABILITY = root + "vulnerabilities";
    public static final String APP = root + "apps";
    public static final String HOST = root + "hosts";
    public static final String TAG = root + "tags";
    public static final String LICENSE = root + "licenses";
    public static final String APP_HOST = root + "apps-hosts";
    public static final String ARTIFACT_APP = root + "artifacts-apps";
    public static final String ARTIFACT_LICENSE = root + "artifacts-licenses";
    public static final String ARTIFACT_TAG = root + "artifacts-tags";
    public static final String ARTIFACT_VULNERABILITY = root + "artifacts-vulnerabilities";

    // 统计数据
    public static final String STATE = root + "state";
}
