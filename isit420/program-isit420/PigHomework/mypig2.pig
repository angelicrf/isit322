-- export PATH=/home/bcuser/pigstuff/pig-0.16.0/bin:$PATH;

A = LOAD 'orders450DDTTSS-Head2.csv' using PigStorage(',') AS 
(Id:chararray, storeNumber:chararray, salesPersonId:int, itemNumber:int,pricePaid:int,timePurch:chararray );

B = FILTER A by pricePaid IN (6,7,8,9,10);
C = FILTER B by salesPersonId > 19;
D = GROUP C by pricePaid;

E = FOREACH D GENERATE (chararray) $0 as f1:int, C.(Id,storeNumber) as f2;
F = GROUP E ALL;
G = FOREACH F GENERATE (chararray) $0, E.f2, CONCAT('The Total Lines are: ',
                                             (chararray) COUNT(E.f1));
-- E alternative is H: 
-- H = FOREACH D GENERATE (int) $0 ,$1.($0, $1), COUNT($1.$0);

-- STORE G into 'output1';
DUMP G;

