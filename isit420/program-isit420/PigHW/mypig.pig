-- export PATH=/home/bcuser/pigstuff/pig-0.16.0/bin:$PATH;

A = LOAD 'orders450DDTTSS-Head2.csv' using PigStorage(',') AS 
(Id:int, storeNumber:chararray, salesPersonId:int, itemNumber:int,pricePaid:int,timePurch:chararray ); 

B = FILTER A by storeNumber  == '98046' OR storeNumber == '98077';
C = FILTER B by itemNumber == 543216;
D = GROUP C by storeNumber;
E = FOREACH D GENERATE $0 as f1, AVG($1.$4) as avp:chararray, (chararray) COUNT(C) as f2;
F = GROUP E ALL;
G = FOREACH F GENERATE E.avp, CONCAT('are the average sales price of these ',(chararray) COUNT(E.f1), ' regions '), E.f1;

STORE G into 'output';
-- DUMP G;

