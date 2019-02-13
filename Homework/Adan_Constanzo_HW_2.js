/*

	Cal State Los Angeles
	CS 4220
	Sunday Feburary 10th 2019
	Adan Constanzo
	Homework 2

*/

/*

1. Write a function called print which accepts an object and prints to the console.
For full credit the function should use destructing in the argument and use empty default values. 
It should additionally use template literals syntax when printing to the console.

*/

const group1 = {
	name: 'Justice League',
	leader: 'Wonder Woman',
	members: ['Batman', 'Superman']
}

const group2 = {
	name: 'Avengers',
	members: ['Hulk', 'Thor', 'Captain America']
}

const FormatMembers = (arr) =>{
	let str = "";
	arr.forEach((ele, i) => {
		if (i === arr.length -1 && arr.length > 1) {
			str += `and ${ele}`
		} else if (i === arr.length - 2 || arr.length === 1) {
			str += `${ele} `
		} else {
			str += `${ele}, `
		}
	})
	return str;
}

const print = ({name = "", leader = "", members = []}) => {
	console.log(`Team: ${name}`);
	console.log(`Leader: ${leader}`);
	console.log(`Members: ${FormatMembers(members)}\n`)
}

print(group1)
print(group2)

//  Team: Justice League
//  Leader: Wonder Woman
//  Members: Batman and Superman

//  Team: Avengers
//  Leader:
//  Members: Hulk, Thor and Captain America

/*

2. Write a class that allows you to build a grocery list and track items, quantity and optional price.

	Each grocery entry will look like this: 
	{ item: 'item name' quantity: 1, price: 1.99 // optional }

	The class should have the following methods:

	1.Class should be written to allow for dot chaining notation.
	2.constructor
		Accepts an array of objects or if nothing is passed - defaults to an empty array 
	3.addItem(object):
		Accepts an object and adds a grocery to the list. Uses destructuring for the grocery object 
	4.removeItem(string):
		Accepts a string. Removes a grocery item by name if the quantity is 1. Else it decreases the quantity by 1. 
	5.addPrice(string, number):
		Accepts a string and number. Adds the price to the specified grocery item. 
	6.addTotal():
		Accepts no arguments. Calculates the total of all known grocery prices. Takes into account quantity. 
	7.print:
		Prints the details about the Groceries as formatted in the example below. Print should be called without ()
*/

class Cart {
	constructor(items = []) {
			this.items = items
			this.total = 0
	}

	addItem({item = "", quantity = 0, price ="n/a"}) {
		this.items.push({ item: item.toLowerCase(), quantity, price });
		return this;
	}

	addPrice(ItemName, Price) {
		for (const i in this.items) {
			const { item } = this.items[i];
			if (item === ItemName.toLowerCase()) {
				this.items[i].price = Price;
				break;
			}
		}

		return this;
	}
	
	removeItem(ItemName) {
		for (const i in this.items) {
			const { item, quantity} = this.items[i];
			if (item === ItemName.toLowerCase()) {
				const quant = Number.parseInt(quantity);
				if (quant === 1) {
					this.items.splice(i, 1)
				} else {
					this.items[i].quantity = quant - 1; 
				}
				break;
			}
		}
		return this;
	}

	addTotal() {
		this.total = 0;
		this.items.forEach(ele => {
			if (!isNaN(Number.parseInt(ele.price))) {
				this.total += Number.parseInt(ele.quantity) * ele.price
			}
		})
		// do this to return proper decimal. 
		this.total = this.total.toFixed(2);
		return this;
	}

	get print() {
		this.items.forEach(ele =>{
			console.log(`Item: ${ele.item} | Quantity: ${ele.quantity} | Price: ${ele.price}`)
		})
		console.log(`Total: ${this.total}`);
	}
}

const cart = new Cart();


cart
.addItem({ item: 'bread', quantity: '1'})
.addItem({ item: 'soup', quantity: '3'})
.addItem({ item: 'chips', quantity: '4'})
.addItem({ item: 'soda', quantity: '1'})
.addPrice('chiPs', 5.99)
.removeItem('Chips')
.addPrice('soda', 1.04)
.addTotal()
.print



// Item: bread | Quantity: 1 | Price: n/a
// Item: soup | Quantity: 3 | Price: n/a
// Item: chips | Quantity: 3 | Price: 5.99
// Item: soda | Quantity: 1 | Price: 1.04
// Total: 19.01