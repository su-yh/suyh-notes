DROP TABLE teacher;
CREATE TABLE teacher (
    name string,
    friends array<string>,
    students map<string, int>,
    address struct<city:string, street:string, postal_code:int>
)
ROW FORMAT SERDE 'org.apache.hadoop.hive.serde2.JsonSerDe'
WITH SERDEPROPERTIES (
    "serialization.format" = ",",
    "field.delim" = ","
)
LOCATION '/user/hive/warehouse/teacher';

SELECT * FROM teacher;

SELECT friends[0], students['xiaohaihai'], address.city FROM teacher;



