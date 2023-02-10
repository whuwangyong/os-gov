package cn.whu.wy.osgov.controller.domain;

import cn.whu.wy.osgov.controller.RequestPath;
import cn.whu.wy.osgov.dto.ArtifactAskDto;
import cn.whu.wy.osgov.dto.response.ResponseEntity;
import cn.whu.wy.osgov.entity.Artifact;
import cn.whu.wy.osgov.entity.Ask;
import cn.whu.wy.osgov.repository.ArtifactRepository;
import cn.whu.wy.osgov.repository.AskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author WangYong
 * @Date 2022/09/23
 * @Time 16:43
 */
@RestController
@RequestMapping(RequestPath.ARTIFACT_ASK)
public class AskController {

    private static String build = "buildscript {\n" +
            "    repositories {\n" +
            "        maven { url \"https://maven.aliyun.com/repository/public/\" }\n" +
            "    }\n" +
            "    dependencies {\n" +
            "        classpath \"org.springframework.boot:spring-boot-gradle-plugin:2.7.0\"\n" +
            "    }\n" +
            "}\n" +
            "\n" +
            "apply plugin: \"org.springframework.boot\"\n" +
            "apply plugin: \"io.spring.dependency-management\"\n" +
            "apply plugin: \"java\"\n" +
            "apply plugin: \"idea\"\n" +
            "\n" +
            "group = 'cn.whu.wy'\n" +
            "version = '0.0.1-SNAPSHOT'\n" +
            "sourceCompatibility = '11'\n" +
            "\n" +
            "// for lombok\n" +
            "configurations {\n" +
            "    compileOnly {\n" +
            "        extendsFrom annotationProcessor\n" +
            "    }\n" +
            "}\n" +
            "\n" +
            "repositories {\n" +
            "    maven { url \"https://maven.aliyun.com/repository/public/\" }\n" +
            "}\n" +
            "\n" +
            "\n" +
            "dependencies {\n" +
            "    implementation 'org.springframework.boot:spring-boot-starter-jdbc:2.7.0'\n" +
            "    implementation 'org.springframework.boot:spring-boot-starter-web:2.7.0'\n" +
            "    runtimeOnly 'mysql:mysql-connector-java:8.0.11'\n" +
            "    testImplementation 'org.springframework.boot:spring-boot-starter-test:2.7.0'\n" +
            "\n" +
            "    implementation 'com.google.guava:guava:31.1-jre'\n" +
            "    implementation 'org.springdoc:springdoc-openapi-ui:1.6.9'\n" +
            "\n" +
            "    compileOnly 'org.projectlombok:lombok:1.18.12'\n" +
            "    annotationProcessor 'org.projectlombok:lombok:1.18.12'\n" +
            "    testCompileOnly 'org.projectlombok:lombok:1.18.12'\n" +
            "    testAnnotationProcessor 'org.projectlombok:lombok:1.18.12'\n" +
            "}\n" +
            "\n" +
            "tasks.named('test') {\n" +
            "    useJUnitPlatform()\n" +
            "}\n";

    @Autowired
    private AskRepository askRepository;

    @Autowired
    private ArtifactRepository artifactRepository;

    @PostMapping
    public ResponseEntity ask(@RequestBody Ask ask) {
        ask.setCommitTime(LocalDateTime.now());
        ask.setStatus("审核中");
        askRepository.add(ask);
        return ResponseEntity.success();
    }

    @GetMapping
    public ResponseEntity getAll() {
        List<ArtifactAskDto> artifactAskDtos = new ArrayList<>();

        List<Ask> askList = askRepository.getAll();
        askList.forEach(ask -> {
            ArtifactAskDto artifactAskDto = ArtifactAskDto.builder()
                    .askId(ask.getId())
                    .askProject(ask.getProject())
                    .askCommitTime(ask.getCommitTime())
                    .askUsername(ask.getUsername())
                    .askStatus(ask.getStatus())
                    .artifacts(new ArrayList<>())
                    .buildGradle(build)
                    .build();

            String[] ids = ask.getArtifactIds().split(",");
            for (String id : ids) {
                Artifact artifact = artifactRepository.findById(Integer.parseInt(id));
                artifactAskDto.getArtifacts().add(artifact);
            }
            artifactAskDtos.add(artifactAskDto);
        });

        return ResponseEntity.success(artifactAskDtos);
    }
}
