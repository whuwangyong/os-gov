SELECT a.id AS artifact_id, a.name AS artifact_name, v.id AS vulnerability_id, v.name AS vulnerability_name
FROM artifact AS a,
     vulnerability AS v,
     artifact_vulnerability AS av
WHERE a.id = av.artifact_id
  AND v.id = av.vulnerability_id
  AND a.name LIKE '%xx%';



SELECT app.id   AS appId,
       app.name AS appName,
       app.env  AS appEnv,
       host.id  AS hostId,
       ip,
       hardware,
       network
FROM app,
     host,
     app_host
WHERE app.id = app_host.app_id
  AND host.id = app_host.host_id
  AND app.name LIKE '%应用4%';


SELECT artifact.id   AS artifact_id,
       artifact.name AS artifact_name,
       app.name      AS app_name,
       re_dev,
       support
FROM artifact,
     app,
     artifact_app
WHERE artifact.id = artifact_app.artifact_id
  AND app.id = artifact_app.app_id;



SELECT artifact.id   AS artifact_id,
       artifact.name AS artifact_name,
       license.name  AS license_name,
       risk          AS license_risk
FROM artifact,
     license,
     artifact_license
WHERE artifact.id = artifact_license.artifact_id
  AND license.id = artifact_license.license_id;


SELECT artifact.id   AS artifact_id,
       artifact.name AS artifact_name,
       tag.name      AS tag_name
FROM artifact,
     tag,
     artifact_tag
WHERE artifact.id = artifact_tag.artifact_id
  AND tag.id = artifact_tag.tag_id;


SELECT av.id                  AS artifact_id,

#        a.name                 AS artifact_name,
#        a.version              AS artifact_version,
#        v.name                 AS vulnerability_name,
#        v.level                AS vulnerability_level,
#        v.difficulty           AS vulnerability_difficulity,
       v.level * v.difficulty AS risk
FROM
#     artifact AS a,
vulnerability AS v,
artifact_vulnerability AS av
WHERE
#     a.id = av.artifact_id
v.id = av.vulnerability_id;

SELECT a.id                        AS artifact_id,
       a.org                       AS artifact_org,
       a.name                      AS artifact_name,
       a.version                   AS artifact_version,
       SUM(v.level * v.difficulty) AS risk
FROM artifact AS a,
     vulnerability AS v,
     artifact_vulnerability AS av
WHERE a.id = av.artifact_id
  AND v.id = av.vulnerability_id
GROUP BY artifact_id
ORDER BY risk DESC;

SELECT COUNT(DISTINCT artifact_id)
FROM artifact_vulnerability AS av,
     vulnerability AS v
WHERE v.id = av.vulnerability_id
  AND level = 4;


SELECT COUNT(artifact_id)
FROM artifact_license AS a,
     license AS l
WHERE a.license_id = l.id
  AND risk = 0;