CREATE VIEW test1 AS SELECT * FROM t_zhuanlan;


SELECT * FROM test1


SELECT * FROM t_zhuanlan

DELETE  FROM t_zhuanlan

DELETE  FROM t_examquesjk

DELETE  FROM t_examdaansjk

drop view t_shouyexinxi

CREATE VIEW t_shouyexinxi AS 
select  row_number() over (order by a.lrsj) as id, a.* from
(
SELECT lrsj,
(SELECT szmc FROM t_sz t2 WHERE t2.id= t.szid) sz, 
szid,
t.id xinxiyuanid,
"zhuanlan" xinxiyuanleixing,
yxbz,
(SELECT neirong FROM t_examquezsd t2 WHERE t2.id= t.exzsdid)  zsd,
exzsdid zsdid,
biaoti
FROM t_zhuanlan t
WHERE btid = -1

UNION ALL 
SELECT MAX(lrsj) lrsj,
(SELECT szmc FROM t_sz t2 WHERE t2.id= t.szid) sz, 
szid,
t.id xinxiyuanid,
"exam" xinxiyuanleixing,
yxbz,
(SELECT neirong FROM t_examquezsd t2 WHERE t2.id= t.examquezsdid)  zsd,
examquezsdid zsdid,
biaoti
FROM t_examquesjk t
GROUP BY t.biaoti
) a
