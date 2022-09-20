# BeerPJ
REST application, where simple CRUD functions are realized.

Current functional:

-getting beerlist. Default qty per page - 5 pcs. Sorting still is not realized.

-getting beer by id

-adding beer to DB. If this item has already presented, amount will be increased on value from field "amount" from JSON
You can't add the same beer with different producer. There will be exception, that other producer already has such item.

-removing. If amount for removing will be greater than current amount, total amount after operation will be 0.
Else amount will be decreased on qty from url parametre(default - 1)

-getting producer list. Default qty per page - 5 pcs. Sorting still is not realized.

-getting beer list for producer by id. Default qty per page - 5 pcs. Sorting still is not realized.
