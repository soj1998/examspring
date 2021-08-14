CREATE VIEW test1 AS SELECT * FROM t_zhuanlan;


SELECT * FROM test1


SELECT * FROM t_zhuanlan

DELETE  FROM t_zhuanlan;

DELETE FROM t_examchoisjk;
DELETE FROM t_biaoti;
DELETE  FROM t_examquesjk;
DELETE  FROM t_examdaansjk;
DELETE  FROM t_examquezsd;
DELETE FROM t_examuser;

SELECT * FROM t_examquesjk;

SELECT DISTINCT biaotiid, t1.`biaoti` FROM t_examquesjk t,t_biaoti t1 WHERE t.biaotiid = t1.id;

drop view if exists t_shouyexinxi;

CREATE VIEW t_shouyexinxi AS 
SELECT  ROW_NUMBER() OVER (ORDER BY a.lrsj) AS id, a.* FROM
(
SELECT lrsj,
(SELECT szmc FROM t_sz t2 WHERE t2.id= t.szid) sz, 
szid,
t.id xinxiyuanid,
"zhuanlan" xinxiyuanleixing,
yxbz,
(SELECT neirong FROM t_examquezsd t2 WHERE t2.id= t.exzsdid)  zsd,
exzsdid zsdid,
biaoti,
0 biaotiid,
ROW_NUMBER() OVER (ORDER BY t.lrsj) AS rn 
FROM t_zhuanlan t
WHERE btid = -1

UNION ALL 
SELECT a.* FROM (
SELECT a.*,ROW_NUMBER() OVER(PARTITION BY sz,szid,xinxiyuanleixing,yxbz,zsd,
zsdid,biaoti,biaotiid ORDER BY lrsj DESC) rn
FROM (
SELECT 
(SELECT lrsj FROM t_biaoti a WHERE a.id = t.biaotiid ) lrsj,
(SELECT szmc FROM t_sz t2 WHERE t2.id= t.szid) sz, 
szid,
t.id xinxiyuanid,
"exam" xinxiyuanleixing,
yxbz,
(SELECT neirong FROM t_examquezsd t2 WHERE t2.id= t.examquezsdid)  zsd,
examquezsdid zsdid,
(SELECT biaoti FROM t_biaoti a WHERE a.id = t.biaotiid ) biaoti,
t.biaotiid
FROM t_examquesjk t) a ) a WHERE a.rn =1 ) a


