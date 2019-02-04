/*

	Cal State Los Angeles
	CS 4220
	Sunday Feburary 3rd 2019
	Adan Constanzo
	Homework 1

*/

/* 

1. Write a function called replaceLetters and that takes a string and an array of letters.

The replaceLetters function accepts two arguments a string and an array 
This function replaces letters in the string with a dash (-) if they are contained in the array

*/

const replaceLetters = (word, array) => {
	const splitWord = word.split('');
	array.forEach(ele => {
		for(const w in splitWord) 
			if (splitWord[w] === ele) 
				splitWord[w] = '-'		
	});
	return splitWord.join('');
} 

const arr = ['a', 'e', 'i', 'o', 'u' ];
replaceLetters('noode.js', arr);
// n-d-.js


/*

2. Write a function called sumArray.

The sumArray function should sums all the numbers in an array of mixed primatives. 
The function should be able to sum both numbers and any string that can be converted to a number. 
HINT: Take a look at mozilla dev docs .... parseInt() Number.isNaN() etc

*/

ParseNumber = (acc, current) => Number.isNaN(parseInt(current)) ? 0+acc : acc+parseInt(current)
sumArray = (arr) => arr.reduce(ParseNumber, 0)


const arr_2 = [5, 2, 'a', 4, '7', true, 'b', 'c', 7, '8', false]
sumArray(arr_2)
// 33


/*

3. Write a function called countingWords and it counts the number of times they are present in the array.

The countingWords function accepts an array argument. 
The answer should be an object wit the key as the word and the count as the value.

*/

countingWords = (arr) => {
	const obj = {}
	arr.forEach(ele => {
		if (obj[ele] === undefined) {
			obj[ele] = 1
		} else {
			obj[ele] = obj[ele] +1;
		}
	});
	return obj;
}

const arr_3 = ['hi', 'hi', 'hello', 'world', 'hello', 'hi' , 'greetings'];
countingWords(arr_3);
// { hi: 3, hello: 2, world: 1, greetings: 1 }


/*


4. Write a function called createAnimals and is able to create an object.
The function createAnimals accepts a nested array of objects which contain a property and what that property should be assigned to. 
The function should create a nested object with the key as the index from the array and the value which is the object created from the property and assignment.

HINT: You may use the words 'property' and 'assign' to check whether it is a property key or an assigned value.

*/

// Setting every property and assigning them. 
const SetPetObject = (arr) => {
	const obj = {}
	arr.forEach(ele => {
		obj[ele.property] = ele.assign
	});
	return obj
}

createAnimals = (arr) => {
	const obj = {};
	for(const s in arr) {
		obj[s] = SetPetObject(arr[s])
	}
	return obj;
}

const arr_4 =
	[
		[
			{ property: 'name', assign: 'Garfield'},
			{ property: 'owner', assign: 'Jon Arbuckle'},
			{ property: 'type', assign: 'cat' }
		],
		[
			{ property: 'name',assign: 'Snoopy' },
			{ property: 'owner',assign: 'Charlie Brown' },
			{ property: 'type',assign: 'dog' }
		]
	]


createAnimals(arr_4);
/*
{
  '0': { name: 'Garfield', owner: 'Jon Arbuckle', typ: 'cat' },
  '1': { name: 'Snoopy', owner: 'Charlie Brown', type: 'dog' }
}
*/



