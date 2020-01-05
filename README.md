# StockExchange

App Simulating StockExchange, meaning you can create your account as either company or physcial person. 
Only companies may introduce new Shares, but both can buy and sell owned ones by creating offers. Data, including transcation history,
is stored in postgres by use of hibernate and spring data jpa. Needs maven JBoss metamodel creation plugin.

I also implemented type-safe way to easly use multiple conditions in queries and chain joins with them.
For example you need to get transcations that occured in between some date, but also had value in some range
, but also traders involved had wealth in some range. This could be implemented as (in pseudocode)
build(transcation.dateBetween(x,y),transcation.valueBetween(x,y),join(trader.wealthBetween(x,y))). There's also class that can be send as 
JSON that creaties these queries.

Security coming soon.

Frontend coming someday.
