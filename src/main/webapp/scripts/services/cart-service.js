'use strict';

angular.module('letusgoApp')
    .service('CartService', function (localStorageService) {

        function setData(cartItems, amounts) {
            localStorageService.set('cartItems', cartItems);
            localStorageService.set('amounts', amounts);
        }

        function addCartItem(item) {
            var cartItemList = localStorageService.get('cartItems') || [];
            item = angular.copy(item);
            if (_.any(cartItemList, {'item': item})) {
                var index = _.findIndex(cartItemList, {'item': item});
                cartItemList[index].num++;
            } else {
                var cartItem = {'item': item, 'num': 1};
                cartItemList.push(cartItem);
            }

            setData(cartItemList, parseInt(localStorageService.get('amounts')) + 1);
        }

        function reduceCartItem(item) {
            var cartItemList = localStorageService.get('cartItems');

            var index = _.findIndex(cartItemList, {'item': item});
            cartItemList[index].num--;

            if (cartItemList[index].num <= 0) {
                _.remove(cartItemList, cartItemList[index]);
            }

            setData(cartItemList, parseInt(localStorageService.get('amounts')) - 1);
        }

        this.getCartItems = function () {
            return localStorageService.get('cartItems') || [];
        }

        this.getAmount = function () {
            if (localStorageService.get('amounts') === null) {
                localStorageService.set('amounts', 0);
            }
            return localStorageService.get('amounts');
        };

        this.categoryCartItem = function (cartItemList) {
            var cartItemGroup = _.map(_.groupBy(cartItemList, function (cartItem) {
                return cartItem.item.category.name;
            }));
            return cartItemGroup;
        };

        this.addCartItem = function (item) {
            addCartItem(item);
        };

        this.reduceCartItem = function (item) {
            reduceCartItem(item);
        };

        function toArray(cartItemList) {
            var array = _.map(cartItemList, function (cartItem) {
                return cartItem.item.price * cartItem.num;
            });
            return array;
        }

        this.totalPrice = function (cartItemList) {
            var sum = _.reduce(toArray(cartItemList), function(sum, num) {
                return sum + num;
            });

            return sum;
        };

        this.cleanCart = function () {
            setData([], 0)
        };
    });
