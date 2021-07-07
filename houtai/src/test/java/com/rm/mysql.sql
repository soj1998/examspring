CREATE VIEW test1 AS SELECT * FROM t_zhuanlan;


SELECT * FROM test1


SELECT * FROM t_zhuanlan

DELETE  FROM t_zhuanlan


CREATE VIEW t_shouyexinxi2 AS 

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