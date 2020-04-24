-- export PATH=/home/bcuser/pigstuff/pig-0.16.0/bin:$PATH;

A = LOAD 'orders450DDTTSS-Head2.csv' using PigStorage(',') AS 
(Id:int, storeNumber:chararray, salesPersonId:int, itemNumber:int,pricePaid:int,timePurch:chararray ); 

B = FILTER A by storeNumber  == '98046' OR storeNumber == '98077';
C = FILTER B by itemNumber == 543216;
D = GROUP C by storeNumber;
E = FOREACH D GENERATE $0 as f1, (chararray) COUNT(C) as f2;
F = GROUP E ALL;
G = FOREACH F GENERATE E.(f1,f2) , CONCAT('Total Lines are: ',(chararray) COUNT(E.f1));

-- STORE G into 'output';
DUMP G;

